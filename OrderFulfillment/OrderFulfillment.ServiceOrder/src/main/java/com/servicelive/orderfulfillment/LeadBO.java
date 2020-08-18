package com.servicelive.orderfulfillment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.LeadException;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.common.TransitionNotAvailableException;
import com.servicelive.orderfulfillment.domain.LeadElement;
import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.LeadProcess;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.jbpm.TransientVariable;
import com.servicelive.orderfulfillment.jbpm.TransitionContext;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.signal.ISignal;
import com.servicelive.orderfulfillment.signal.ProcessSignalStep;
import com.servicelive.orderfulfillment.signal.Signal;

/**
 * This class will be used to 
 *  1. Persist the lead object  
 *  2. jBPM
 *  3. signal dispatch (configuration and calling)
 * @author akurian
 *
 */

public class LeadBO extends BaseleadBO {
	private Logger logger = Logger.getLogger(getClass());
	
	public String createLeadObject(Identification idfn, LeadHdr lead,Map<String,Object> pvars){
		
		//Step1 : Validate the lead object		
		if(null==lead)
			throw new LeadException("Undefined lead object");
		
		// Step 2: Save the lead object
		leadDao.save(lead);
		//leadDao.saveLead(lead);
		// Step 3: save the history??? TODO 
		
		// Step 4: Create the WF instance and get the pid
		String wfName = "leadProcess";
		String pid = workflowManager.createNewWFInstance(lead.getLeadId(), wfName, pvars);
		logger.debug("created jbpm process for lead (" + lead.getLeadId() + ") and Process Id = " + pid);
			
		// Step 5: Persist the pid to lead_process_map
		LeadProcess leadProcess = new LeadProcess(lead.getLeadId(), pid);
		leadProcessDao.save(leadProcess);
		return lead.getLeadId();		
	}
	
	public void	processLeadSignal(
			String leadId, 
			SignalType signalType,
			Identification callerId,
			LeadElement leadElement,
			Map<String,Serializable> miscParams
		){
		String pss="";
		
		try {
			long startTime = System.currentTimeMillis();
			// get process record from the database
			pss = ProcessSignalStep.PSS_GETPROCESSRECORD;
			LeadProcess lp = leadProcessDao.getLeadProcessWithLock(leadId);
			if (lp == null) 
				throw new ServiceOrderException("Order record isn't found in lead process table");
			
		    logger.info(String.format("processOrderSignal>>> %1$s for leadId %2$s. Got LeadProcess with lock. Time elapsed %3$d", signalType.name(), leadId, System.currentTimeMillis() - startTime));
		
			// get process id for the order
			pss = ProcessSignalStep.PSS_GETWFID;
			String pid = lp.getProcessId();
			if (pid == null) 
				throw new LeadException("Process id isn't found for the lead");
		
			// get signal bean from the map of signals
			pss = ProcessSignalStep.PSS_GETSIGNAL;
			ISignal signalObj = Signal.getLeadSignal(signalType, leadElement);
			if (signalObj==null) 
				throw new LeadException("Signal wasn't found in the signal map");
		    logger.info(String.format("processOrderSignal>>> %1$s for leadId %2$s. Got Process map Id and Signal Object. Time elapsed %3$d", signalType.name(), leadId, System.currentTimeMillis() - startTime));
		
			// get service order from the DB
			pss = ProcessSignalStep.PSS_GETSO;
			LeadHdr lead = leadDao.getLeadObject(leadId);
			if (lead==null) 
				throw new LeadException("Lead record isn't found in the database");
		    logger.info(String.format("processOrderSignal>>> %1$s for soId %2$s. Got Lead . Time elapsed %3$d", signalType.name(), leadId, System.currentTimeMillis() - startTime));
		
			// is the caller authorized to send this signal?
			// Authorization logic depends on user type and id from Identification and
			// ServiceOrder because many rules use order info. I.e., ROUTED_PROVIDER, ACCEPTED_PROVIDER, BUYER, ...
			//pss = ProcessSignalStep.PSS_AUTHORIZE;
			//signalObj.authorize(callerId, so);
		
			// can this signal be processed? ask jBPM 
			pss = ProcessSignalStep.PSS_TRANSITIONCHECK;
			String transitionName = signalObj.getTransitionName();
			if (signalObj.isStateDependent()){
		        if(!workflowManager.waitForTransition(pid, transitionName, 1))
						throw new TransitionNotAvailableException("Lead workflow is in a state where "+	signalObj.getName()+" signal is prohibited");
			}
		    logger.info(String.format("processOrderSignal>>> %1$s for leadId %2$s. Done with checking if Transition is available. Time elapsed %3$d", signalType.name(), leadId, System.currentTimeMillis() - startTime));
		
			// SO logging
			pss = ProcessSignalStep.PSS_SOLOG;
			//signalObj.logServiceOrder(so, soElement, callerId, miscParams);
		    logger.info(String.format("processOrderSignal>>> %1$s for leadId %2$s. Done with creating logging. Time elapsed %3$d", signalType.name(), leadId, System.currentTimeMillis() - startTime));
		    
		    // give access to misc parameters
		    //signalObj.accessMiscParams(miscParams, callerId, so);
		    
			// process signal
			pss = ProcessSignalStep.PSS_SIGNALPROCESS;
		    //the process method calls validate internally. If validation fails an ServiceOrderException should be thrown.
			signalObj.processLead(leadElement,lead);
		    logger.info(String.format("processOrderSignal>>> %1$s for leadId %2$s. Done with signal.process. Time elapsed %3$d", signalType.name(), leadId, System.currentTimeMillis() - startTime));
		
			// now it's time to transition jBPM
			// this will fire all commands that are associated with the transition
		    Map<String, TransientVariable> transientVars = new HashMap<String,TransientVariable>();
		    transientVars.put(ProcessVariableUtil.PVKEY_IDENTIFICATION, new TransientVariable(callerId));
			pss = ProcessSignalStep.PSS_WFTRANSITION;
			if (signalObj.isStateDependent())
				workflowManager.takeTransition(new TransitionContext(pid, transitionName, miscParams,transientVars));
		    logger.info(String.format("processOrderSignal>>> %1$s for leadId %2$s. Done with jBPM transition. Time elapsed %3$d", signalType.name(), leadId, System.currentTimeMillis() - startTime));
		
			// save order
			pss = ProcessSignalStep.PSS_SAVESO;
			//logger.info("getSoProviderInvoiceParts size: "+so.getSoProviderInvoiceParts().size());
			leadDao.updateLead(lead);
			// testing of transactions. will both SO domain and jBPM roll back?
			//throw new ServiceOrderException("We've got a problem");
		    logger.info(String.format("Done with processOrderSignal>>> %1$s for soId %2$s. Time elapsed %3$d", signalType.name(), leadId, System.currentTimeMillis() - startTime));
		}
		catch (ServiceOrderException soe) {
			soe.addError(">> "+signalType.name()+" signal processing failed performing "+pss+". leadId="+leadId);
			throw soe;
		}
		catch (Exception e)	{
			LeadException le = new LeadException();
			le.addError(">> "+signalType.name()+" signal processing failed performing "+pss+". leadId="+leadId);
			le.addError(">> General exception. Message:"+e.getMessage()+" Cause:"+e.getCause());
			logger.error(">> General exception stack trace:", e);
			logger.error(">> End of stack trace:");
			throw le;
		}
		}
	
}
