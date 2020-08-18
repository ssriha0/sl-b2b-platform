package com.newco.marketplace.api.beans.so.soDetails.vo;

import java.util.List;

public class InvoicePartsListVO {

	private String noPartsRequiredInd;
	private List<InvoiceVO> invoicePart;
	
	public String getNoPartsRequiredInd() {
		return noPartsRequiredInd;
	}
	public void setNoPartsRequiredInd(String noPartsRequiredInd) {
		this.noPartsRequiredInd = noPartsRequiredInd;
	}
	public List<InvoiceVO> getInvoicePart() {
		return invoicePart;
	}
	public void setInvoicePart(List<InvoiceVO> invoicePart) {
		this.invoicePart = invoicePart;
	}
	
	

}
