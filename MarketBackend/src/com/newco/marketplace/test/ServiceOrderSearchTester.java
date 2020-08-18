package com.newco.marketplace.test;


import java.util.List;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class ServiceOrderSearchTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IServiceOrderSearchBO soBO = (IServiceOrderSearchBO)MPSpringLoaderPlugIn.getCtx().getBean("soSearchBO");
			
			ServiceOrderSearchVO request = new ServiceOrderSearchVO();
			List<ServiceOrderSearchResultsVO> searchList = null;
			ProcessResponse response = new ProcessResponse();
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
		}	

	}

}
