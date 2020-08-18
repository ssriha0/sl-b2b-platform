package com.servicelive.marketplatform.dao;

import com.servicelive.marketplatform.serviceorder.domain.ServiceOrder;

public interface IServiceOrderDao {
	ServiceOrder findById(String serviceOrderId);
}
