package com.newco.marketplace.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.business.iBusiness.so.order.ServiceOrderRoleBO;
import com.newco.marketplace.dto.vo.RoleVO;

public class ServiceOrderByRoleTest {

	/**
	 * @param args
	 */
	public void doTest() {
		try {
			//ContextValue.setContextFile("resources/spring/applicationContext.xml");
			//System.out.println("ServiceOrderRoleTest-->Loaded applicationContext.xml");

		    ApplicationContext ctx;

		    ctx = new ClassPathXmlApplicationContext("/resources/spring/applicationContext.xml");

			ServiceOrderRoleBO soBO = (ServiceOrderRoleBO)ctx.getBean("soRoleBo");
			RoleVO role = new RoleVO();
			
			role.setRoleIdInt(2);
			role.setRoleName("buyer");
			System.out.println("\n\n********* Buyer SOs ***********");
			soBO.process(role);
			
			role.setRoleIdInt(13);
			role.setRoleName("provider");
			System.out.println("\n\n******** Provider SOs ***********");
			soBO.process(role);
			
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
		}	

	}

}
