package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class ServiceOrderAcceptTest {
	public static void main(String[] args) throws Exception {
		IServiceOrderBO soBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
		SecurityContext securityContext = null;
		ProcessResponse resp = soBO.processAcceptServiceOrder("001-6436333046-11", 13, 3, null, true, false, true, securityContext);
		if (resp.isError()) {
			throw new Exception("Error in Response: " + resp.getMessages().get(0));
		}
	}
}
