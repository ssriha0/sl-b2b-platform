package com.newco.marketplace.test;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;

public class SODaoTest extends TestCase {
	ServiceOrder soObj = new ServiceOrder();
	public SODaoTest(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
	}
	public void testOfferCheck(){
		
		RoutedProvider rp = new RoutedProvider();
		ServiceOrderDao serviceOrderDao = (ServiceOrderDao)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderDao");
		rp.setSoId("001-6436333046-11");
		rp.setResourceId(12);
		try{
			Integer i = (Integer)serviceOrderDao.checkConditionalOfferResp(rp);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Works well ..!"+soObj.getRoutedResources().get(0).getProviderContact().getFirstName());
	
	}
}
	
	

	
