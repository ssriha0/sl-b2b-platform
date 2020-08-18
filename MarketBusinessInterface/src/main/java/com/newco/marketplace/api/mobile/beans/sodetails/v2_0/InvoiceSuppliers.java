package com.newco.marketplace.api.mobile.beans.sodetails.v2_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("invoiceSuppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceSuppliers {

	@XStreamImplicit(itemFieldName = "invoiceSupplier")
	private List<InvoiceSupplier> invoiceSupplier;

	/**
	 * @return the invoiceSupplier
	 */
	public List<InvoiceSupplier> getInvoiceSupplier() {
		return invoiceSupplier;
	}

	/**
	 * @param invoiceSupplier the invoiceSupplier to set
	 */
	public void setInvoiceSupplier(List<InvoiceSupplier> invoiceSupplier) {
		this.invoiceSupplier = invoiceSupplier;
	}


}
