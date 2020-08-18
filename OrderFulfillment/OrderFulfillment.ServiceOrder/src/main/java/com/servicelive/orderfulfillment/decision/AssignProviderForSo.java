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
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.processor.common.CommonCondAutoRouteBasedEnhancer;

/**
 * @author madhup_chand
 *
 */
public class AssignProviderForSo extends AbstractServiceOrderDecision 
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
	    		   logger.info("Size of result vo after setting value"+routingRuleHdrVO.getRoutingRuleVendor().size());
	    		  for(RoutingRuleVendorVO rrv: routingRuleHdrVO.getRoutingRuleVendor())
	    		  {
	    	//Checking 4th condition for auto acceptance to happen for the only provider in a rule ,it has auto accept feature ON or not
	    			  if(null!=rrv.getAutoAcceptStatus() && rrv.getAutoAcceptStatus().equalsIgnoreCase("ON"))
	    			  {
	    				  logger.info("Info of selected providers"+rrv.getAutoAcceptStatus()+"Vendorid"+rrv.getRoutingRuleVendorId());
	    				//Return YES if the so can be auto accepted by the provider if it satisfies all four condition
	    				  logger.info("simply returning yes without calling auto helper object");
	    				  return "YES";
	    			  }
	    			  else
	    			  {
	    				  logger.info("Now inside the NO statement with no provider for auto accept:>>>>");
	    				  return "NO";
	    			  }
	    		  	}
		  	   }
		    	   else
		   	   {
		    		   logger.info("Now inside the MANYPROVIDERS statement with many provider so it will go to posted state--:>>>>");
		   		   return "MANYPROVIDERS";
		   	   }
	   	   
	      }
		//Return NO if the so cannot  be auto accepted by the provider if it doesn't satisfies all four condition
	       return "NO";
	}
	
}
