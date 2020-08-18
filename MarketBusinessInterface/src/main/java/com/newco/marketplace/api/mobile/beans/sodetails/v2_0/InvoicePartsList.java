package com.newco.marketplace.api.mobile.beans.sodetails.v2_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of invoice parts.
 * @author Infosys
 *
 */

@XStreamAlias("invoicePartsList")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoicePartsList {
	@XStreamAlias("noPartsRequiredInd")
	private String noPartsRequiredInd;
	
	@XStreamImplicit(itemFieldName="invoicePart")
	private List<InvoicePart> invoicePart;

	
	
	public String getNoPartsRequiredInd() {
		return noPartsRequiredInd;
	}

	public void setNoPartsRequiredInd(String noPartsRequiredInd) {
		this.noPartsRequiredInd = noPartsRequiredInd;
	}

	public List<InvoicePart> getInvoicePart() {
		return invoicePart;
	}

	public void setInvoicePart(List<InvoicePart> invoicePart) {
		this.invoicePart = invoicePart;
	}
	

}
