package com.servicelive.orderfulfillment.jbpm;

import org.apache.log4j.Logger;
import org.jbpm.api.ExecutionService;
import org.jbpm.pvm.internal.cmd.Command;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.model.ExecutionImpl;

import com.servicelive.orderfulfillment.common.JBPMProcessEndedException;

public class IsTransitionAvailableCommand implements Command<Object> {

	protected final Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	
	private String processId;
	private String transitionName;
	
	public IsTransitionAvailableCommand(String pid, String tname)
	{
		processId=pid;
		transitionName=tname;
	}
	
	public Object execute(Environment environment) throws Exception {
		// We shouldn't really be using implementation class ExecutionImpl
		// But there are no APIs to accomplish the same thing
		// Is it reasonable that there is no access to available transitions in jBPM API?
		ExecutionService execSvc =  environment.get(ExecutionService.class);
		ExecutionImpl ei = (ExecutionImpl)execSvc.findProcessInstanceById(processId);
		//check if activity is present or not and throw error that process does not exist in the JBPM database
		if(ei == null || ei.getActivity() == null)
			throw new JBPMProcessEndedException(processId);
			
		logger.debug("current activity="+ei.getActivity().getName());
		if (ei.getActivity().hasOutgoingTransition(transitionName)) {
			logger.debug("Transition "+transitionName+" is available");
			return true;
		}
		else {
			logger.info("Transition "+transitionName+" is not available from the state " + ei.getActivity().getName());
			return false;
		}
	}

}