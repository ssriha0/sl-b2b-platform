package com.servicelive.orderfulfillment.decision;

import java.util.List;




import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.marketplatform.common.vo.RoutingRuleHdrVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleVendorVO;
import com.servicelive.orderfulfillment.command.util.AutoRouteHelper;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

import org.apache.log4j.Logger;

public class MajorChangesCheck extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7258373364727565809L;
	/**
	 * @return the autoRouteHelper
	 */
	
	private static final Logger logger = Logger.getLogger(MajorChangesCheck.class);

	public AutoRouteHelper getAutoRouteHelper() {
		return autoRouteHelper;
	}
	/**
	 * @param autoRouteHelper the autoRouteHelper to set
	 */
	public void setAutoRouteHelper(AutoRouteHelper autoRouteHelper) {
		this.autoRouteHelper = autoRouteHelper;
	}
	AutoRouteHelper autoRouteHelper;

	@SuppressWarnings("unchecked")
	public String decide(OpenExecution execution) {
		
		//new
		/*if(execution.getVariable(ProcessVariableUtil.CAR_RULE_CHANGED).equals("true")){
			return "otherMajorChanges";
		}*/
		
		List<SOFieldsChangedType> changes = (List<SOFieldsChangedType>) execution.getVariable(ProcessVariableUtil.SERVICE_ORDER_CHANGES);
		ServiceOrder serviceOrder = getServiceOrder(execution);
		
		String routingPriorityInd="";
		if(null!=execution.getVariable(OrderfulfillmentConstants.ROUTING_PRIORITY_IND)){
			routingPriorityInd=(String)execution.getVariable(OrderfulfillmentConstants.ROUTING_PRIORITY_IND);
			logger.info("routingPriorityInd ="+routingPriorityInd);
		}
		
		// SL-18226
		Integer currentSpnId = serviceOrder.getSpnId();
		String spnId = (String)execution.getVariable(ProcessVariableUtil.OLD_SPN_ID);
		String isUpdate = (String)execution.getVariable(ProcessVariableUtil.ISUPDATE);
		String postFromFrontEndAction=(String)execution.getVariable(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION);
		Integer oldSpnId = null;
		if(null!=spnId){
		oldSpnId = Integer.parseInt(spnId);
		}
		logger.info("currentSpnId"+currentSpnId);
		logger.info("oldSpnId"+oldSpnId);
		logger.info("isUpdate"+isUpdate);
		logger.info("postFromFrontEndAction"+postFromFrontEndAction);
		
		// changes need to be done.
		if(null!=changes && changes.size()>0){
			
			for(SOFieldsChangedType change:changes)
			{
				logger.info(" changes "+change);
			}
		}
		// repost if there is spn....             
		if(null!=postFromFrontEndAction && postFromFrontEndAction.equals("true")){
			if((null!=currentSpnId || null!=oldSpnId)){
				if(null!=changes && (changes.contains(SOFieldsChangedType.SPEND_LIMIT_LABOR_CHANGED) || 
						changes.contains(SOFieldsChangedType.SPEND_LIMIT_PARTS_CHANGED))){         
				execution.setVariable(ProcessVariableUtil.REPOST,"true");
				logger.info("ISREPOST>>>>"+(String)execution.getVariable(ProcessVariableUtil.REPOST));
				execution.setVariable(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES,"true");
				return "otherMajorChanges";	
				}
			}
		}           
		  
		
		if(null!=postFromFrontEndAction && postFromFrontEndAction.equals("true") &&  
				 routingPriorityInd!=null && routingPriorityInd.equals("true")){
		if((null==currentSpnId && null!=oldSpnId)||(null!=currentSpnId && null==oldSpnId)||(null!=currentSpnId&&null!=oldSpnId&&(!currentSpnId.equals(oldSpnId)))
				|| (null!=changes && changes.contains(SOFieldsChangedType.TIER_ROUTED_RESOURCES))){
			execution.setVariable(ProcessVariableUtil.REPOST,"true");
			logger.info("ISREPOST>>>>"+(String)execution.getVariable(ProcessVariableUtil.REPOST));
			execution.setVariable(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES,"true");
			return "otherMajorChanges";
		}
		Object repostForTierFrontend = execution.getVariable(OrderfulfillmentConstants.REPOST_FOR_TIER_FRONTEND);
		if(null!=repostForTierFrontend && repostForTierFrontend.toString().equals("true")){
			execution.setVariable(ProcessVariableUtil.REPOST,"true");
			logger.info("ISREPOST>>>>"+(String)execution.getVariable(ProcessVariableUtil.REPOST));
			execution.setVariable(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES,"true");
			return "otherMajorChanges";
			
		}
		}
		if (null == changes){
			execution.setVariable(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES,"false");
			return "noMajorChanges";
		}
		if (changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)){
			execution.setVariable(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES,"false");
			return "skillCategoryChanged";
		}
	
				
		//new
		if(changes.contains(SOFieldsChangedType.TASKS_ADDED)||changes.contains(SOFieldsChangedType.TASKS_DELETED)){
			execution.setVariable(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES,"true");
			return "otherMajorChanges";
		}
		
		if (changes.contains(SOFieldsChangedType.SERVICE_LOCATION)
				|| changes.contains(SOFieldsChangedType.SCHEDULE)){
			logger.info("SL 18533 Inside major changes check with changed date");
			execution.setVariable(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES,"true");
			 //SL-20189 setting repost as true for service location Update
			logger.info("SL-20189 Setting isRepost as true for service location Update");
			execution.setVariable(ProcessVariableUtil.REPOST,"true");
			return "otherMajorChanges";
		}
		logger.info("SL 18533 After adding date changes to the map");
		
		logger.info("startserviceOrderRule");
		if(null!=serviceOrder.getCondAutoRoutingRule())
		{
			logger.info("SL 18553 Before checking for eligible provider for auto acceptance");
			RoutingRuleHdrVO routingRuleHdrVO=new RoutingRuleHdrVO() ;
			//Checking the condition for auto acceptance to happen for a provider in a rule there should be only one vendor in the rule
			Integer ruleId=serviceOrder.getCondAutoRoutingRule();
			logger.info("serviceOrder.getCondAutoRouteRuleId"+ruleId);

			routingRuleHdrVO=autoRouteHelper.getProvidersListOfRule(ruleId);
			if(null!=routingRuleHdrVO && null!=routingRuleHdrVO.getRoutingRuleVendor() && routingRuleHdrVO.getRoutingRuleVendor().size() == 1)
			{
				logger.info("routingRuleHdrVO.getRoutingRuleVendor()size");

				//Checking the condition for auto acceptance to happen for the only provider in a rule ,it has auto accept feature ON or not
 			  if(null!=routingRuleHdrVO.getRoutingRuleVendor().get(0).getAutoAcceptStatus() && routingRuleHdrVO.getRoutingRuleVendor().get(0).getAutoAcceptStatus().equalsIgnoreCase("ON"))
 			  	{
 				 //SL-19177 setting repost as true if auto accept feature ON 
					logger.info("SL-19177 Setting isRepost as true if auto accept feature is ON");
					execution.setVariable(ProcessVariableUtil.REPOST,"true");
 					logger.info("otherMajorChanges");
 					logger.info("SL 18533 Before returning otherMajorChanges from major changes check");
 				//Return otherMajorChanges if the so can be auto accepted by the provider if it satisfies all the conditions
 					execution.setVariable(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES,"true");
 					return "otherMajorChanges";
 			  	}
			}
		}

		execution.setVariable(ProcessVariableUtil.IS_OTHER_MAJOR_CHANGES,"false");
		return "noMajorChanges";
	}

}
