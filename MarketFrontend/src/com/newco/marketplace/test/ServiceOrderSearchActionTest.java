package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.web.action.sosearch.ServiceOrderSearchAction;


public class ServiceOrderSearchActionTest {
	
	public static void main(String[] args)
	{
		ServiceOrderSearchAction soSearchObj = (ServiceOrderSearchAction)MPSpringLoaderPlugIn.getCtx().getBean("soSearchActionBean");
		try {
			String result = soSearchObj.soSearch();
			
			System.out.println("Result is "+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
