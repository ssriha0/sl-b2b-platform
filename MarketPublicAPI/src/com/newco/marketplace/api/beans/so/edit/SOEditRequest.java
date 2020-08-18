package com.newco.marketplace.api.beans.so.edit;

import com.newco.marketplace.api.beans.so.create.ServiceOrderBean;
import com.newco.marketplace.api.beans.so.post.ProviderRouteInfo;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("soRequest")
public class SOEditRequest {

	@XStreamAlias("serviceorder")
	private ServiceOrderBean serviceOrder;
	
	@XStreamAlias("providerRouteInfo")
	private ProviderRouteInfo ProviderRouteInfo;

	public ServiceOrderBean getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrderBean serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	public ProviderRouteInfo getProviderRouteInfo() {
		return ProviderRouteInfo;
	}

	public void setProviderRouteInfo(ProviderRouteInfo providerRouteInfo) {
		ProviderRouteInfo = providerRouteInfo;
	}

}
