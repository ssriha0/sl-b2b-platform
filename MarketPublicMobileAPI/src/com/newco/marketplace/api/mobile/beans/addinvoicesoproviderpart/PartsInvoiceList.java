package com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of parts.
 * @author Infosys
 *
 */

@XStreamAlias("providerPartsList")
public class PartsInvoiceList {
	
	@XStreamImplicit(itemFieldName="providerPart")
	private List<PartInvoice> providerPart;

	@XmlElement (name = "providerPart")	
	public List<PartInvoice> getProviderPart() {
		return providerPart;
	}

	public void setProviderPart(List<PartInvoice> providerPart) {
		this.providerPart = providerPart;
	}


	


	
	
}
