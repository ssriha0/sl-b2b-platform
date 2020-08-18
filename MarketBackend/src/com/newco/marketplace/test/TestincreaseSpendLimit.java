package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.IncreaseSpendLimitRequestVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class TestincreaseSpendLimit {
	
	

	public static void main(String args[])
	{
		IServiceOrderBO ichangeBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
		try {
			IncreaseSpendLimitRequestVO req =new IncreaseSpendLimitRequestVO();
			int buyerId = 2;
			Double increaseLimit = 500.00;
			Double currentSpendLimit = 2000.00;
			String soId =  "001-6434041234-31";
			Double totalSpendLimit = 400.00;
			Double totalSpendLimitParts = 100.00;
			String increasedSpendLimitComment = "increased the work";
			SecurityContext securityContext = null;
			ProcessResponse pr = ichangeBO.updateSOSpendLimit(soId,  totalSpendLimit,  totalSpendLimitParts, increasedSpendLimitComment, buyerId, true,false,securityContext);
			System.out.println("error messages: " + pr.getMessages().size());
			System.out.println("Message: "+pr.getMessages().get(0));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
