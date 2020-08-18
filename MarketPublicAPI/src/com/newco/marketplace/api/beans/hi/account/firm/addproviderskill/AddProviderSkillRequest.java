package com.newco.marketplace.api.beans.hi.account.firm.addproviderskill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ProviderSkillDetails;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="addProviderSkillRequest.xsd", path="/resources/schemas/ums/")
@XmlRootElement(name="addProviderSkillRequest")
@XStreamAlias("addProviderSkillRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddProviderSkillRequest {
	
	@XStreamAlias("provider")
	@XmlElement(name="provider")
    private ProviderSkillDetails provider;

	public ProviderSkillDetails getProvider() {
		return provider;	}

	public void setProvider(ProviderSkillDetails provider) {
		this.provider = provider;
	}

}