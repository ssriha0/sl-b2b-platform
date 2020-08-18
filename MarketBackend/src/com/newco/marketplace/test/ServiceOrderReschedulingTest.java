package com.newco.marketplace.test;

import java.sql.Timestamp;
import java.util.Date;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class ServiceOrderReschedulingTest {
	
	public static void main(String [] args){
		String soId = "001-7618-9501-3023";
		int buyerId = 2;
		Timestamp newDate = new Timestamp(System.currentTimeMillis()+ 60 * 60 * 1000*48);
		IServiceOrderBO serviceOrderBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
		
		Timestamp t1 = new Timestamp(new Date().getTime());
		Timestamp t2 = new Timestamp(new Date().getTime());
		Timestamp t3 = new Timestamp(new Date().getTime());
		Timestamp t4 = new Timestamp(new Date().getTime());
		
		SecurityContext securityContext = null;
		ProcessResponse processResponse = serviceOrderBO.updateSOSchedule("001-1973-0532-9917", t1, t2, "1","2", 2, false,securityContext);
		
		System.out.println("updated!!");
	}

}
