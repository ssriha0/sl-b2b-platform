package com.servicelive.marketplatform.service;

import com.servicelive.marketplatform.dao.IServiceOrderDao;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformServiceOrderBO;
import com.servicelive.marketplatform.serviceorder.domain.ServiceOrder;

public class MarketPlatformServiceOrderBO implements
		IMarketPlatformServiceOrderBO {
	
	private IServiceOrderDao serviceOrderDao;

	public ServiceOrder retrieveServiceOrder(String serviceOrderId) {
		return serviceOrderDao.findById(serviceOrderId);
	}

	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

	public IServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

}
