

package com.newco.marketplace.api.beans.hi.account.provider.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.hi.account.update.provider.ProviderRegistration;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XSD(name="updateProviderRegistrationRequest.xsd", path="/resources/schemas/ums/")
@XmlRootElement(name = "updateProviderRegistrationRequest")
@XStreamAlias("updateProviderRegistrationRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateProviderRegistrationRequest {

	@XStreamAlias("provider")
	@XmlElement(name="provider")
	private ProviderRegistration provider;

	public ProviderRegistration getProvider() {
		return provider;
	}

	public void setProvider(ProviderRegistration provider) {
		this.provider = provider;
	}
	
}
