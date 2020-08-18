/**
 * 
 */
package com.newco.marketplace.test;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * @author schavda
 *
 */
public class AOPAdviceJUnitTest extends TestCase {

	public AOPAdviceJUnitTest(String name){
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {

	}
	
	public void testAdvice(){
		try{
			IServiceOrderBO serviceOrderBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
			SecurityContext securityContext = null;					
			ProcessResponse pr = serviceOrderBO.updateSOSpendLimit("001-6436333046-11",  150.00, 150.00, "increased the work", 2, true,false,securityContext);
			/*
			if(pr.isError())
				System.out.println("Message: "+pr.getMessages().get(0));
			else
				System.out.println("Result: "+pr.getObj().toString());
			*/
			/*
			Timestamp newDate = new Timestamp(System.currentTimeMillis()+ 60 * 60 * 1000*48);
			ProcessResponse processResponse = serviceOrderBO.updateSOSchedule("001-6436333046-11", newDate, 2);
			*/
		}catch(Exception e){
			System.out.println("AOPAdviceJUnitTest-->testAdvice()-->EXCEPTION-->");
			e.printStackTrace();
		}
	}
}
