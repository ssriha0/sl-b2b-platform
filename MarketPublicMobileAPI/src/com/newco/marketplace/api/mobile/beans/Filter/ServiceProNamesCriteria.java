package com.newco.marketplace.api.mobile.beans.Filter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("searchCriteria")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceProNamesCriteria {

	@XStreamImplicit(itemFieldName="filterSearchCriteriaValue")
	private List<ServiceProName> serviceProName;

	public List<ServiceProName> getServiceProName() {
		return serviceProName;
	}

	public void setServiceProName(List<ServiceProName> serviceProName) {
		this.serviceProName = serviceProName;
	}
}
