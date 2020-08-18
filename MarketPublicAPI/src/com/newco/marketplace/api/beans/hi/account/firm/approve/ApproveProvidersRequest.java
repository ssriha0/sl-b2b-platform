package com.newco.marketplace.api.beans.hi.account.firm.approve;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.Providers;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "approveProvidersRequest.xsd", path = "/resources/schemas/ums/")
@XmlRootElement(name = "approveProvidersRequest")
@XStreamAlias("approveProvidersRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApproveProvidersRequest {
	
	@XStreamAlias("adminResourceId")
	private Integer adminResourceId;
	
	@XStreamAlias("providers")
	private Providers providers;

	public Integer getAdminResourceId() {
		return adminResourceId;
	}

	public void setAdminResourceId(Integer adminResourceId) {
		this.adminResourceId = adminResourceId;
	}

	public Providers getProviders() {
		return providers;
	}

	public void setProviders(Providers providers) {
		this.providers = providers;
	}

	

	
}
