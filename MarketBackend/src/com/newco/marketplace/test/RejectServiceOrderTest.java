package com.newco.marketplace.test;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class RejectServiceOrderTest extends TestCase{
	
	int responseId = 0;
	int reasonId = 0;
	int resourceId = 0;
	String soId = "";
	String modifiedBy = "";
	String reasonDesc = "";
	
	@Override
	protected void setUp() throws Exception{
		responseId = 3;
		reasonId = 4;
		resourceId = 16;
		soId = "001-6434041185-11";
		modifiedBy = "12";
		reasonDesc = "my own reason test";
		}
	public void testRejectServiceOrderTest(){
		IServiceOrderBO ichangeBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
		SecurityContext securityContext = null;
		try{
		ProcessResponse pr = ichangeBO.rejectServiceOrder(resourceId, soId, reasonId, responseId, modifiedBy, reasonDesc,securityContext);
		System.out.println("Messages: " + pr.getMessages().get(0));
		assertTrue(true);
		}catch(Exception e){
			System.out.println("Error in executing testcase testRejectServiceOrderTest :" + e.getMessage());
		}
	}
	public static void main(String args[]){

	}
}
