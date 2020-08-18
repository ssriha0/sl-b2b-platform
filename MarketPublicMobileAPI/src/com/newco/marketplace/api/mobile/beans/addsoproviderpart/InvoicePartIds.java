package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias("invoiceIdList")
public class InvoicePartIds {
	
	@XStreamImplicit(itemFieldName = "invoiceId")
	private List<Integer> invoiceId;

	public List<Integer> getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(List<Integer> invoiceId) {
		this.invoiceId = invoiceId;
	}



}
