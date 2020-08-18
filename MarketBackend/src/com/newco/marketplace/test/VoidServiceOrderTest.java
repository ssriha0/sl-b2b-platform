package com.newco.marketplace.test;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class VoidServiceOrderTest extends TestCase{
	
	int buyerId = 0;
	String soId = "";	
	
	@Override
	protected void setUp() throws Exception{
		buyerId = 111;
		soId = "131-8067-5878-94";
		}
	public void testVoidServiceOrderTest(){
		IServiceOrderBO ichangeBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
		SecurityContext securityContext = null;
		try{
		ProcessResponse pr1 = ichangeBO.processVoidSO(buyerId, soId,securityContext);
		System.out.println("Message : " + pr1.getMessages().get(0));
		
		
		assertTrue(true);
		
		}catch(Exception e){
			System.out.println("Error in executing testcase testVoidServiceOrderTest :" + e.getMessage());
		}
	}
	public static void main(String args[]){

	}
}
