/**
 * 
 */
package com.newco.marketplace.business.businessImpl.alert;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.interfaces.ProviderConstants;


/**
 * @author sahmad7
 *
 */
public class AlertServiceOrderAction implements StateTransitionRouter{

	public boolean generateAlert(Object stateTransitionVO)
	{
		AlertBusinessBean sobb = (AlertBusinessBean) MPSpringLoaderPlugIn.getCtx().getBean(ProviderConstants.SERVICE_ORDER_BUSINESS_BEAN);
		return sobb.routeServiceOrder(stateTransitionVO);
	}
}
