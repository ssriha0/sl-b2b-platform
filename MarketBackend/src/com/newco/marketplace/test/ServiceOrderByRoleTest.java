package com.newco.marketplace.test;


import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.so.order.ServiceOrderRoleBO;
import com.newco.marketplace.dto.vo.RoleVO;

public class ServiceOrderByRoleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServiceOrderRoleBO soBO = (ServiceOrderRoleBO)MPSpringLoaderPlugIn.getCtx().getBean("soRoleBo");
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
