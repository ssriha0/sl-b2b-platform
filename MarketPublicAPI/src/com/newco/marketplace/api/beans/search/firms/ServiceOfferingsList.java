package com.newco.marketplace.api.beans.search.firms;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("serviceOfferingsList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceOfferingsList {
	
	@XStreamAlias("serviceOffering")
	@XStreamImplicit(itemFieldName="serviceOffering")
	private List<ServiceOffering> serviceOffering;

	public List<ServiceOffering> getServiceOffering() {
		return serviceOffering;
	}

	public void setServiceOffering(List<ServiceOffering> serviceOffering) {
		this.serviceOffering = serviceOffering;
	}

}
