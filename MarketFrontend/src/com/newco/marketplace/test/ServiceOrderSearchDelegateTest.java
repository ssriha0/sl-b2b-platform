package com.newco.marketplace.test;

import java.util.ArrayList;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;
import com.newco.marketplace.web.dto.ServiceOrderDTO;



public class ServiceOrderSearchDelegateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SOMonitorDelegateImpl soSearchObj = (SOMonitorDelegateImpl)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderManager");
			    
		ArrayList<ServiceOrderDTO> searchList = null;
		System.out.println("The Result for 1st search is ");
		System.out.println(searchList.get(0).getId()+" "+searchList.get(0).getId()
				+" "+searchList.get(0).getBuyerName()+" "+searchList.get(0).getProviderName());
	}

}
