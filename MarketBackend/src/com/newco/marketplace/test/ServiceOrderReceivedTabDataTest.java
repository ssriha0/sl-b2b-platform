package com.newco.marketplace.test;


import java.util.ArrayList;
import java.util.Iterator;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.so.order.ServiceOrderRoleBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTabResultsVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;

public class ServiceOrderReceivedTabDataTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServiceOrderRoleBO soBO = (ServiceOrderRoleBO)MPSpringLoaderPlugIn.getCtx().getBean("soRoleBo");
			ArrayList<ServiceOrderTabResultsVO> soReceived = new ArrayList<ServiceOrderTabResultsVO>();
			LoginCredentialVO lvo = new LoginCredentialVO();
			lvo.setCompanyId(1);
			lvo.setRoleName("provider");
			System.out.println("\n\n********* Received Tab for Vendor 1 ***********");
			soReceived = soBO.getReceivedTabData(lvo);
			Iterator<ServiceOrderTabResultsVO> i = soReceived.iterator();
			ServiceOrderTabResultsVO sovo = new ServiceOrderTabResultsVO();
			while(i.hasNext()){
				sovo = i.next();
				System.out.println(sovo);
			}
		
			
			
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
		}	

	}

}
