package com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for AddOnList.
 * @author Infosys
 *
 */

@XStreamAlias("invoicePartsList")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoicePartsList {
	
	@XStreamImplicit(itemFieldName = "invoicePart")
	private List<InvoicePart> invoicePart;

	public List<InvoicePart> getInvoicePart() {
		return invoicePart;
	}

	public void setInvoicePart(List<InvoicePart> invoicePart) {
		this.invoicePart = invoicePart;
	}


}
