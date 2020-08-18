package com.newco.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegatesImpl.SODetailsDelegateImpl;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class WithdrawConditionalOfferTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx;

	    ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	    SODetailsDelegateImpl soBO = (SODetailsDelegateImpl)ctx.getBean("soDetailsManager");
	   // SODetailsDelegateImpl soBO = (SODetailsDelegateImpl)ctx.getBean("soDetailsManager");
	    RoutedProvider rp =new RoutedProvider();
	    rp.setSoId("001-6434041185-11");
	    rp.setResourceId(12);
	    rp.setProviderRespId(2);
	    rp.setResourceId(12);
	    ProcessResponse pr = null;
		try {
			pr = soBO.withDrawCondAcceptance(rp);
			System.out.println("pr value:" + pr.getMessages().get(0));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	    
	}

}
