package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.ledger.IGLProcessor;

public class LedgerSummaryTest {
	public static void main(String[] args) {
		System.out.println("loading applicationContext.xml");
		try {
			IGLProcessor glProcesor = (IGLProcessor)MPSpringLoaderPlugIn.getCtx().getBean("glProcessor");
			boolean flag = glProcesor.writeGLFeed(null, null);
			System.out.println("summary="+flag);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
