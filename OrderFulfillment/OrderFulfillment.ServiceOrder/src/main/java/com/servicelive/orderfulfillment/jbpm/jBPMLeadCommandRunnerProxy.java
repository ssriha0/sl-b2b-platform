package com.servicelive.orderfulfillment.jbpm;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.jbpm.pvm.internal.model.ExecutionImpl;

import com.servicelive.orderfulfillment.command.util.LeadCommandRunner;

//
// This class serves as a bridge between jBPM and Spring beans
//
// We register this class as a universal EventListener for the following process events:
// - start: fired when jBPM process enters a node
// - transition: fired when jBPM takes a transition
//
// In jBPM we have to configure two fields for the listener:
// - jBPM entity type field: can be NODE or TRANSITION
// - list of LeadCommands to invoke: a list of semi-colon separated command names
//
// jBPMSOCommandRunnerProxy uses Spring BeanFactoryLocator to find Spring context
// and forward the call to the real Command Runner
//
public class jBPMLeadCommandRunnerProxy implements EventListener 
{
	private static final long serialVersionUID = 1L;
	protected final Logger logger = Logger.getLogger(getClass());
	
	String commandList;
	
	public void notify(EventListenerExecution execution) throws Exception 
	{
		logger.debug("entering: command list="+commandList);
				
		String[] commandNames = commandList.split(";");		
		if (commandNames.length>0) 
		{		
			// assemble arguments for the call			
			ExecutionImpl execImpl = (ExecutionImpl)execution;
			String entityType="";
			String entityName=""; 
			if (execImpl.getActivity()!=null){
				entityType="NODE";
				entityName=execImpl.getActivityName();
			}
			else
				if (execImpl.getTransition()!=null)	{
					entityType="TRANSITION";
					entityName=execImpl.getTransition().getName();
				}
			
			logger.info("entityType="+entityType+" entityName="+entityName);

			logger.debug("calling leadCommandRunner");
			Map<String,Object> actaulVariables = execution.getVariables();
			Map<String,Object> processVariables = new HashMap<String, Object>(actaulVariables);
			
			processVariables.put("entityType", entityType);
			processVariables.put("entityName", entityName);			
			// calling command runner
			LeadCommandRunner cmdRunner = LeadCommandRunner.getRunnerInstance();
			cmdRunner.runCommands(commandNames, processVariables);
			
			//lets remove the variables introduced earlier
			processVariables.remove("entityType");
			processVariables.remove("entityName");	
			
			//save variables in the database
			setVariables(execution, actaulVariables, processVariables);
		}
		else
			logger.warn("no commands are specified");
	}
	
	
	private void setVariables(EventListenerExecution execution, Map<String, Object> oldVar, Map<String, Object> newVar){
		for(String key : newVar.keySet()){
			if(newVar.get(key) instanceof TransientVariable) // skip saving since this is a transient variable
                continue;
            if(oldVar.containsKey(key)){
				if(newVar.get(key)==null){//remove key
					logger.debug(">>>>>>>>>>>>>>removing the process variable " + key);
					execution.removeVariable(key);
				}else if(!oldVar.get(key).equals(newVar.get(key))){
					logger.debug(">>>>>>>>>>>>>>updating the process variable " + key + " old value = " + oldVar.get(key) + " >> new value =" + newVar.get(key));
					execution.setVariable(key, newVar.get(key)); //update value
				}
			}else{ //add key
				logger.debug(">>>>>>>>>>>>>>adding the process variable " + key);
				execution.setVariable(key, newVar.get(key));
			}
		}
	}
}