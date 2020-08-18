package com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part information.
 * @author Infosys
 *
 */
@XStreamAlias("invoicePart")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoicePart {
	
	@XStreamAlias("invoicePartId")
	private String invoicePartId;		
		
	@XStreamAlias("estProviderPayement")
	private Double estProviderPayement;
	
	@XStreamAlias("finalPrice")
	private Double finalPrice ;


	public Double getEstProviderPayement() {
		return estProviderPayement;
	}

	public void setEstProviderPayement(Double estProviderPayement) {
		this.estProviderPayement = estProviderPayement;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getInvoicePartId() {
		return invoicePartId;
	}

	public void setInvoicePartId(String invoicePartId) {
		this.invoicePartId = invoicePartId;
	}

}
