package com.servicelive.marketplatform.serviceinterface;

import com.servicelive.marketplatform.serviceorder.domain.ServiceOrder;

public interface IMarketPlatformServiceOrderBO {
	ServiceOrder retrieveServiceOrder(String serviceOrderId);
}
