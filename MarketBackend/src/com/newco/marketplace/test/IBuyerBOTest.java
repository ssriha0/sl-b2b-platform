package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;

public class IBuyerBOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IBuyerBO bbo = (IBuyerBO)MPSpringLoaderPlugIn.getCtx().getBean("buyerBo");
		bbo.saveBuyerBlackoutNotification(110, null, 20, "66666", "test");
	}
}
