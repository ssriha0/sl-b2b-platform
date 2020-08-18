package com.servicelive.orderfulfillment.decision;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class AutoRouteBehaviorCheck extends AbstractServiceOrderDecision {
	private static final long serialVersionUID = 3668523176218339810L;

    protected final Logger logger = Logger.getLogger(getClass());

	public String decide(OpenExecution openExecution) {
		String postFromFrontend = (String)openExecution.getVariable(ProcessVariableUtil.FE_POST_ORDER);
		String routingPriorityApplied = (String)openExecution.getVariable(ProcessVariableUtil.ROUTING_PRIORITY_IND);
		String postFromFrontEndAction=(String)openExecution.getVariable(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION);
		String routingBehaviour = (String)openExecution.getVariable(ProcessVariableUtil.ROUTING_BEHAVIOUR);
		String saveAutoPostInd = (String)openExecution.getVariable(ProcessVariableUtil.SAVE_AND_AUTOPOST);
		logger.info("saveAutoPostInd in AutoRouteBehaviorCheck"+saveAutoPostInd);
		logger.info("SL 18410 Value of postFromFrontend "+postFromFrontend);
		logger.info("SL 18226 Value of routingPriorityApplied "+routingPriorityApplied);
		if(null!=postFromFrontend && postFromFrontend.equals("true")){
			openExecution.setVariable(ProcessVariableUtil.FE_POST_ORDER,"false");
			openExecution.setVariable(ProcessVariableUtil.FRONTEND_POSTING,"true");
		}
		 String post = (String)openExecution.getVariable(ProcessVariableUtil.PROVIDER_POST);
		 logger.info("SL 18410 Value of post "+post);
		 
		  AutoRoutingBehavior autoRoutingBehavior = ProcessVariableUtil.getAutoRoutingBehavior(openExecution.getVariables());
        logger.info("AutoRouteBehaviorCheck returning " + autoRoutingBehavior.name());
        //return Conditional if if and autopost and car rule is picked
        if(saveAutoPostInd!= null && saveAutoPostInd.toString().equals("true") && null!= autoRoutingBehavior.name() && autoRoutingBehavior.name().equals("Conditional")){
        	return "Conditional";
        }
        
        if((post!=null && post.equals("post") && autoRoutingBehavior.name().equals("None")) || (null==postFromFrontend && null!=routingPriorityApplied && routingPriorityApplied.equals("false") && autoRoutingBehavior.name().equals("Tier")) )
        {
        	logger.info("AutoRouteBehaviorCheck returning Basic" );
         return "Basic";	
        }
        
        if(saveAutoPostInd!= null && saveAutoPostInd.toString().equals("true") && null!=routingPriorityApplied && routingPriorityApplied.equals("false") && !autoRoutingBehavior.name().equals("Conditional")){
        	 logger.info("Save and Auto Post - routingpriority turned OFF");
        	return "Basic";	
        }
        
        String autoRoutingBehaviorName = autoRoutingBehavior.name();
        logger.info("SL 18410 Value of autoRoutingBehaviorName "+autoRoutingBehaviorName);
        
        //returning behaviour as tier whenever order is from frontend and routing priority is true
        if(null!=postFromFrontend && postFromFrontend.equals("true") && routingPriorityApplied.equals("true") && null!=routingBehaviour && routingBehaviour.equals("Tier")){
        	return "Tier";
        }
        
        logger.info(" for update Tier routing ");
        if(null!=openExecution.getVariable("isUpdate")){
          String isUpdate = (String)openExecution.getVariable("isUpdate");
        logger.info("isUpdate "+isUpdate);
        logger.info("routingBehaviour "+routingBehaviour);
        logger.info("routingPriorityApplied"+routingPriorityApplied); 
       
        
        if(null!=routingBehaviour && routingBehaviour.equals("Tier") && routingPriorityApplied.equals("true")){
        	 ServiceOrder so = getServiceOrder(openExecution);
        	logger.info(" so.getWfStateId() "+ so.getWfStateId());
        	if(null!=so.getWfStateId() && so.getWfStateId().intValue()==100)
        	{
        	return "Tier";
        	}
        }
        else if(null!=routingBehaviour && routingBehaviour.equals("Tier") && routingPriorityApplied.equals("false") && !autoRoutingBehavior.name().equals("Conditional")){
        	return "Basic";
        }
        }
        
        if((null!=postFromFrontend && postFromFrontend.equals("true")||(null!=postFromFrontEndAction && postFromFrontEndAction.equals("true"))) && routingPriorityApplied.equals("false") &&(null!=autoRoutingBehaviorName && (autoRoutingBehaviorName.equals("Conditional") || autoRoutingBehaviorName.equals("Basic") || autoRoutingBehaviorName.equals("Tier")))){
        	logger.info("AutoRouteBehaviorCheck returning None" );
        	return "None";
        }
   
        return autoRoutingBehavior.name();
    }
}       
