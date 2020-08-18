package com.servicelive.orderfulfillment.jbpm;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.pvm.internal.svc.ExecutionServiceImpl;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderDeadLockException;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.common.TransitionNotAvailableException;

/*
 * This class implements IServiceOrderWFManager by using jBPM engine
 * 
 */
public class LeadWFManager implements ILeadWFManager {
	protected final Logger logger = Logger.getLogger(getClass());
    private static final int DFLT_MAX_TRANSITION_WAIT_ATTEMPTS = 3;
    private int maxTransitionWaitAttempts = DFLT_MAX_TRANSITION_WAIT_ATTEMPTS;

	ExecutionService executionService;
	
	LeadWFManager(ExecutionService es) {
		executionService=es;
	}
	
	 /**
	  * new WF instance is created when lead is saved in unmatched state for the first time
	  * uses jBPM executionService API
	  */
	public String createNewWFInstance(String leadId, String type, Map<String, Object> variables) {	
		Map<String,Object> pvars = new HashMap<String,Object>();
		ProcessVariableUtil.addLeadId(pvars, leadId);
		if (variables!=null) {
			pvars.putAll(variables);
		}
		logger.info("creating WF instance. process definition=" + type + " lead#="+leadId);		
		ProcessInstance pi = executionService.startProcessInstanceByKey(type, pvars, leadId);
		return pi.getId();
	}

	public String createNewWFInstance(String processName, Map<String, Object> variables){
		return executionService.startProcessInstanceByKey(processName, variables).getId();
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.orderfulfillment.jbpm.IServiceOrderWFManager#processExists(java.lang.String)
	 * uses jBPM executionService API to check for process instance
	 */
	public boolean processExists(String processId) {
		logger.debug("check WF process existance for: " + processId);
		ProcessInstance pi = executionService.findProcessInstanceById(processId);
		return pi != null && pi.getId() != null && pi.getId().length() > 0;
	}

	/**
	 *  for a given WF process instance in its current state - is transition available?
	 *  uses jBPM command service
	 *  command service is necessary, because this functionality wasn't provided by jBPM via APIs
	 *  jBPM APIs internally use commands, since command service provides a lot of important services
	 */
	public boolean isTransitionAvailable(String pid, String transitionName)	{
		// in order for us to execute this functionality we need to use jBPM command pattern
		// command service will instantiate the proper environment
		// the unfortunate part here is that access to command service is via an implementation class
		// this is fixed in later versions of jbpm
		ExecutionServiceImpl esi = (ExecutionServiceImpl)executionService;
		Object res = 
			esi.getCommandService()
				.execute(
					new IsTransitionAvailableCommand(pid,transitionName)
				);
		return (Boolean)res;
	}

    public boolean waitForTransition(String pid, String transitionName, int timeoutSeconds){
        boolean returnVal = isTransitionAvailable(pid, transitionName);
        if(!returnVal){
            for(int attempts = maxTransitionWaitAttempts; attempts>0; attempts--){
                try{
                    logger.info(String.format("Waiting for %s Seconds.",timeoutSeconds));
                    Thread.sleep(timeoutSeconds * 1000);
                }catch(InterruptedException ignored){}//Ignore exception. Wake-up and try again.

                if(isTransitionAvailable(pid, transitionName)){
                    returnVal = true;
                    break;
                }
            }
        }
        return returnVal;
    }
	// signal to jBPM process instance with a given transition name
	public boolean takeTransition(String pid, String transitionName) {
		return takeTransition(new TransitionContext(pid, transitionName));
	}
	
	public boolean takeTransition(TransitionContext transitionCtx) {
		logger.debug("taking transition "+transitionCtx.getTransitionName());

		// doing the check again. 
		// it's cheaper to check than to fire all actions tied to transition itself and then rollback
		if (!isTransitionAvailable(
				transitionCtx.getSoProcessId(), 
				transitionCtx.getTransitionName())) 
			throw new TransitionNotAvailableException("Transition " + transitionCtx.getTransitionName() + " is not available for process " + transitionCtx.getSoProcessId());
		
		ProcessInstance processInstance=executionService.findProcessInstanceById(transitionCtx.getSoProcessId());
		Set<String> activeActivities = processInstance.findActiveActivityNames();
		
		for (String activeActivity : activeActivities) {
			Execution executionInstance = processInstance.findActiveExecutionIn(activeActivity);
			if (executionInstance != null) {
				updateProcessInstanceVariables(executionInstance.getId(), transitionCtx);
				try {
					executionService.signalExecutionById(executionInstance.getId(), transitionCtx.getTransitionName());
					return true;    // only transition from first active activity
				}catch(ServiceOrderException e){
                    //No need to do anything special here.
                    throw e; 
                }catch (Exception e) {
                    // we check if transition is available before actually taking it
					// failure is still possible because of concurrency
					// it is possible that another thread (offline jobs for example) changes WF state
                    Throwable cause = e.getCause();

                    String causeMessage = (cause==null)?"":cause.getLocalizedMessage();
                    if(causeMessage.matches(".*try restarting transaction$")){
                        logger.debug("Deadlock detection kicked in.");
                        throw new ServiceOrderDeadLockException(causeMessage);
                    }
					ServiceOrderException soe = new ServiceOrderException();
					soe.addError("Order workflow was not able to take transition "+transitionCtx.getTransitionName());
					if(e.getMessage() != null)
						soe.addError(e.getMessage());
					
					soe.addError(causeMessage);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					soe.addError(sw.toString());
					throw soe;
				}
			}
		}

		return false;
	}
	
	private void updateProcessInstanceVariables(String executionId, TransitionContext transitionCtx) {
        // add process variables from transition context
		if(transitionCtx.getProcessVariables() != null){
			for (Map.Entry<String, Serializable> entry : transitionCtx.getProcessVariables().entrySet()){
	            executionService.setVariable(executionId, entry.getKey(), entry.getValue());
	        }
		}
        // add transient variables from transition context
		if (transitionCtx.getTransientVariables() != null){
	        for (Map.Entry<String, TransientVariable> entry :  transitionCtx.getTransientVariables().entrySet()) {
	            executionService.setVariable(executionId, entry.getKey(), entry.getValue());
	        }
		}
	}

    public void setMaxTransitionWaitAttempts(int maxTransitionWaitAttempts) {
        this.maxTransitionWaitAttempts = maxTransitionWaitAttempts;
    }
}
