package com.newco.marketplace.business.businessImpl.so.order;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;

public class ProBuyerBOFactory {

	private static final int ASSURANT = 1;
	private static final int OMS = 2;
	private static final int HSR = 3;
	
	public IProBuyerUpdateBO getProBuyerUpdateBOByClientId(Integer clientId) {
		switch(clientId) {
			case OMS: return (IProBuyerUpdateBO)MPSpringLoaderPlugIn.getCtx().getBean("omsUpdateBO");
			case ASSURANT: return (IProBuyerUpdateBO)MPSpringLoaderPlugIn.getCtx().getBean("assurantUpdateBO");
			case HSR: return (IProBuyerUpdateBO)MPSpringLoaderPlugIn.getCtx().getBean("hsrUpdateBO");
		}
		return null;
	}
	
}
