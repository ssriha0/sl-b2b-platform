package com.newco.marketplace.api.beans.hi.account.firm.approve;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ProviderFirms;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "approveFirmsRequest.xsd", path = "/resources/schemas/ums/")
@XmlRootElement(name = "approveFirmsRequest")
@XStreamAlias("approveFirmsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApproveFirmsRequest {
	
	@XStreamAlias("adminResourceId")
	private Integer adminResourceId;
	
	@XStreamAlias("providerFirms")
	private ProviderFirms providerFirms;

	public ProviderFirms getProviderFirms() {
		return providerFirms;
	}

	public void setProviderFirms(ProviderFirms providerFirms) {
		this.providerFirms = providerFirms;
	}

	public Integer getAdminResourceId() {
		return adminResourceId;
	}

	public void setAdminResourceId(Integer adminResourceId) {
		this.adminResourceId = adminResourceId;
	}

	
}
