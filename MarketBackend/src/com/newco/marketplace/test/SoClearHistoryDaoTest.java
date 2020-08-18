package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;

public class SoClearHistoryDaoTest {

	public static void main(String[] args) {
		ServiceOrderDao serviceOrderDao = (ServiceOrderDao)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderDao");
		try {
			int x=serviceOrderDao.resetResponseHistory("001-7123847398-16");
			System.out.println("X:"+ x);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
	}
}
