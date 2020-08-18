package com.newco.marketplace.api.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("invoicePartsList")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoicePartsList {

	@XStreamImplicit(itemFieldName = "partInvoice")
	private List<PartInvoice> partInvoice;

	public List<PartInvoice> getPartInvoice() {
		return partInvoice;
	}

	public void setPartInvoice(List<PartInvoice> partInvoice) {
		this.partInvoice = partInvoice;
	}

}
