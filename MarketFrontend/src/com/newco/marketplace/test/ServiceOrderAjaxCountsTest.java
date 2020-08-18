package com.newco.marketplace.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.business.businessImpl.so.ajax.ServiceOrderMonitorAjaxBO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;

public class ServiceOrderAjaxCountsTest {

	/**
	 * @param args
	 */
	public void doTest() {
		try {
			//ContextValue.setContextFile("resources/spring/applicationContext.xml");
			//System.out.println("ServiceOrderRoleTest-->Loaded applicationContext.xml");

		    ApplicationContext ctx;

		    ctx = new ClassPathXmlApplicationContext("/resources/spring/applicationContext.xml");

		    ServiceOrderMonitorAjaxBO soAjax = (ServiceOrderMonitorAjaxBO)ctx.getBean("somAjaxBo");

		    AjaxCacheVO soVo = new AjaxCacheVO();
		    
		    soVo.setUserName("blars002");
		    
			soAjax.getSummaryCounts(soVo);
			
			
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
		}	

	}

}
