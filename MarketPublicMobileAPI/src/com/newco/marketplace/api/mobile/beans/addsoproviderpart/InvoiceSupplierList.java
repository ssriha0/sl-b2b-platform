package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("partList")
public class InvoiceSupplierList {

	@XStreamImplicit(itemFieldName = "partSupplier")
	private List<InvoiceSupplier> partSupplier;

	public List<InvoiceSupplier> getPartSupplier() {
		return partSupplier;
	}

	public void setPartSupplier(List<InvoiceSupplier> partSupplier) {
		this.partSupplier = partSupplier;
	}

	
	
}
