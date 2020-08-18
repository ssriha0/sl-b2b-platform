/**
 * 
 */
package com.servicelive.orderfulfillment.command;

import java.util.ArrayList;
import java.util.List; 
import java.util.Map;

import com.servicelive.orderfulfillment.command.util.AutoRouteHelper;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author madhup_chand
 *
 */
public class UpdateProvidersForConditionalAutoRoutingCmd extends SOCommand{
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
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder so = getServiceOrder(processVariables);
		String isSaveandAutoPost = (String)processVariables.get(ProcessVariableUtil.AUTO_POST_IND);
		String isRepost=(String)processVariables.get(ProcessVariableUtil.REPOST);
		logger.info("Entry into UpdateProvidersForConditionalAutoRoutingCmd with so id" +so.getSoId());
		if(null!=so)
		{
			logger.info("before updating so routed provider table for the so for which we had found a rule");
			 //SL-19177 Checking  Isrepost value for handling  auto acceptance
			if(null!=so.getRoutedResources() && so.getRoutedResources().size()>0 
			&& ((null!=isSaveandAutoPost && !isSaveandAutoPost.equals("isAutoPost"))||(null!=isRepost && isRepost.equals("true"))))
			{
			logger.info("Checking  Isrepost value for handling  auto acceptance" +so.getSoId());
			serviceOrderDao.deleteSoRoutedProviders(so.getSoId());
			List<RoutedProvider> routedProviders = new ArrayList<RoutedProvider>();
    		so.setRoutedResources(routedProviders);
			}
			autoRouteHelper.UpdateProvidersForConditionalAutoRouting(so);
			logger.info("After updating so routed providers column");
		}
		
	}

}
