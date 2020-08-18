/**
 * 
 */
package com.servicelive.orderfulfillment.decision;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.orderfulfillment.command.util.AutoRouteHelper;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.processor.common.CommonCondAutoRouteBasedEnhancer;

/**
 * @author madhup_chand
 *
 */
public class ProviderForAutoAcceptanceEligibilityCheck extends AbstractServiceOrderDecision {
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
		
				List<ProviderIdVO>  prVO=new ArrayList<ProviderIdVO>();
				logger.info("inside the updateso hdr");
				logger.info("after the so udr updating tge db");
				prVO=autoRouteHelper.getProviderForAutoAcceptance(serviceOrder);
				logger.info("Before setting the resource id for a so"+prVO.size());
				  if(null!=prVO && prVO.size()>0)
		    	   {
		    		 logger.info("Total Eligible Provider  found under the rule"+serviceOrder.getCondAutoRouteRuleId()+"is :-->"+prVO.size());
		    		 return "YES";
		    	   }
				  else
				  {
					  logger.info("No Eligible Provider found under the rule"+serviceOrder.getCondAutoRouteRuleId());
			    		 return "NO";  
				  }
		

	}
}
