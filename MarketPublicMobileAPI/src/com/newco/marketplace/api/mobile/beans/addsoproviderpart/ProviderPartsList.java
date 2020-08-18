package com.newco.marketplace.api.mobile.beans.addsoproviderpart;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("providerPartsList")
public class ProviderPartsList {

	@XStreamImplicit(itemFieldName = "providerPart")
	private List<ProviderPartDetails> providerPart;

	@XmlElement (name = "providerPart")	
	public List<ProviderPartDetails> getProviderPart() {
		return providerPart;
	}

	public void setProviderPart(List<ProviderPartDetails> providerPart) {
		this.providerPart = providerPart;
	}

}
