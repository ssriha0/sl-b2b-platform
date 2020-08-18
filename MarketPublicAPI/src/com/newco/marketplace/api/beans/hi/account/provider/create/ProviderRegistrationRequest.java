package com.newco.marketplace.api.beans.hi.account.provider.create;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.hi.account.create.provider.Provider;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XSD(name = "providerRegistrationRequest.xsd", path = "/resources/schemas/ums/")
@XStreamAlias("providerRegistrationRequest")
@XmlRootElement(name = "providerRegistrationRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderRegistrationRequest {

	@XStreamAlias("firmId")
	private String firmId;
	
	@XStreamAlias("provider")
    private Provider provider;
	
	
    

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link Provider }
     *     
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link Provider }
     *     
     */
    public void setProvider(Provider value) {
        this.provider = value;
    }

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

    

}
