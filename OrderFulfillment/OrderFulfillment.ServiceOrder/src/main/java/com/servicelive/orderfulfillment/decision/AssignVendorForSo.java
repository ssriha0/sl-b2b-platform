/**
 * 
 */
package com.servicelive.orderfulfillment.decision;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleVendor;
import com.servicelive.marketplatform.common.vo.RoutingRuleHdrVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleVendorVO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;
import com.servicelive.orderfulfillment.command.util.AutoRouteHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.processor.common.CommonCondAutoRouteBasedEnhancer;

/**
 * @author madhup_chand
 *
 */
public class AssignVendorForSo extends AbstractServiceOrderDecision 
{
	private static final long serialVersionUID = 9146711749919429739L;
	
	/**
	 * @return the autoRouteHelper
	 */
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
	private static final Logger logger = Logger.getLogger(CommonCondAutoRouteBasedEnhancer.class);
	public String decide(OpenExecution execution) 
	{
		ServiceOrder serviceOrder = getServiceOrder(execution);
	      
	    	   //For auto acceptance to happen for a provider in a rule if buyer has feature set ON (1st condition)
	    	   //and a rule id has been associated with the SO (2nd condition)  then
	    	   //it will make entry inside this class. If it has entered inside this that means it fulfills the first 2 conditions
		 if(null!=serviceOrder.getCondAutoRouteRuleId())
	       {
	    	  RoutingRuleHdrVO routingRuleHdrVO=new RoutingRuleHdrVO() ;
	    	   //Checking 3rd condition for auto acceptance to happen for a provider in a rule there should be only one vendor in the rule
			  logger.info("before ruleid in assign provider for sO");
	    	   Integer ruleId=serviceOrder.getCondAutoRouteRuleId();
	    	   routingRuleHdrVO=autoRouteHelper.getProvidersListOfRule(ruleId);
	    	  // .getProvidersListOfRule(serviceOrder.getCondAutoRouteRuleId());
	    	   if(null!=routingRuleHdrVO && routingRuleHdrVO.getRoutingRuleVendor().size() == 1)
	    	   {
	    		   List<RoutingRuleVendorVO> rrvList = routingRuleHdrVO.getRoutingRuleVendor();
	    		   Integer providerFirmId=null;
	    		   for(RoutingRuleVendorVO rrv1:rrvList)
	    		   {
	    			    providerFirmId=rrv1.getRoutingRuleVendorId();
	    		   }
	    		   logger.info("Provider Firm Id belonging to rule is" +providerFirmId);	    		  
	    		   logger.info("Size of result vo after setting value"+routingRuleHdrVO.getRoutingRuleVendor().size());
	    		   logger.info("Size of service order routed resources"+serviceOrder.getRoutedResources().size());
	    		   boolean commonProviderFirmInd=false;
	    		   if (serviceOrder.getRoutedResources()!= null && serviceOrder.getRoutedResources().size()>0){
	    			   logger.info("Inside the loop with more than one routed resource");
	    			   List<RoutedProvider> listRP = serviceOrder.getRoutedResources();
	    			   for (RoutedProvider rp:  listRP){
	    				   logger.info("provider firm is :"+rp.getVendorId());
	    				   if (providerFirmId.equals(rp.getVendorId())){
	    					   commonProviderFirmInd=true;
	    					   logger.info("Common Provider Firm indicator from loop"+commonProviderFirmInd);
	    					   
	    				   }
	    			   }
	    			   logger.info("Final Common Provider Firm indicator value"+commonProviderFirmInd);
	    		   }
	    		   //SL-19021 Added condition to return ToDraftState signal when no service provider found between intersection of SPN and Rule 
	    		   //It will send the service order in draft status.
	    		   else if(serviceOrder.getRoutedResources()!= null && serviceOrder.getRoutedResources().size()==0 && null!=serviceOrder.getSpnId())
	    		   {
	    			   logger.info("Directing the service order to be in draft status if no service provider found between intersection of SPN and Rule");
	    			   return "TODRAFT";
	    		   }
	    		   else {
	    				 logger.info("Directly returning NO if no eligible serivce provider found under the Rule");
	    			   return "NO";
	    		   }
	    		   if(commonProviderFirmInd==true)
	    		   {
	    		  for(RoutingRuleVendorVO rrv: routingRuleHdrVO.getRoutingRuleVendor())
	    		  {
	    	//Checking 4th condition for auto acceptance to happen for the only provider in a rule ,it has auto accept feature ON or not
	    			  if(null!=rrv.getAutoAcceptStatus() && rrv.getAutoAcceptStatus().equalsIgnoreCase("ON"))
	    			  {
	    				  if(null!=serviceOrder.getSoGroup())
	    				  {
	    					  return "NO";
	    				  }
	    				  logger.info("Info of selected providers"+rrv.getAutoAcceptStatus()+"Vendorid"+rrv.getRoutingRuleVendorId());
	    				//Return YES if the so can be auto accepted by the provider if it satisfies all four condition
	    				  logger.info("simply returning yes without calling auto helper object");
	    				  return "SINGLE";
	    			  }
	    			  else
	    			  {
	    				  logger.info("Now inside the NO statement with no provider for auto accept:>>>>");
	    				  return "NO";
	    			  }
	    		  	}
	    		   }
	    	    }
	    	   	//Checking if there are zero or more than one vendor under the rule
		    	   else if(null!=routingRuleHdrVO && routingRuleHdrVO.getRoutingRuleVendor().size() == 0)
					   	   {
		    		   			
		    		   		   // If it is OFF then to override AutoRoutingBehaviorCheckForPost decision, setting autoRoutingBehavior as conditional
		    		   		   execution.setVariable(ProcessVariableUtil.PVKEY_AUTO_ROUTING_BEHAVIOR,"Conditional"); 
					    	   logger.info("Now inside the NO providers as there are no vendors under the rule--:>>>>");
					    	   //Returning MANYVENDORS if there are no vendor or more than one vendor under the rule.
					    	   return "NOVENDORS";
					   	   }
				    		   else
				    		   {
				    			   // If it is OFF then to override AutoRoutingBehaviorCheckForPost decision, setting autoRoutingBehavior as conditional
				    			   execution.setVariable(ProcessVariableUtil.PVKEY_AUTO_ROUTING_BEHAVIOR,"Conditional");
				    			   logger.info("Now inside the MANYPROVIDERS statement with many provider.--:>>>>");
						    	   return "MANYVENDORS";
				    		   }
	   	   
	      }
		//Return NO if the so cannot  be auto accepted by the provider if it doesn't satisfies all four condition
	       return "NO";
	}
	
}
