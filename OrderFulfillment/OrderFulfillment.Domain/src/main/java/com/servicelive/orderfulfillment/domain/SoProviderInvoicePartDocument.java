package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_provider_invoice_doc")
@XmlRootElement()
public class SoProviderInvoicePartDocument extends InvoicePartChild{
	
	private static final long serialVersionUID = 8982059016154609586L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "document_part_id")
	private Integer documentPartId;
	
	@Column(name = "invoice_document_id")
	private Integer invoiceDocumentId;

    public Integer getDocumentPartId() {
		return documentPartId;
	}
	
	public void setDocumentPartId(Integer documentPartId) {
		this.documentPartId = documentPartId;
	}

	public Integer getInvoiceDocumentId() {
		return invoiceDocumentId;
	}
	
	public void setInvoiceDocumentId(Integer invoiceDocumentId) {
		this.invoiceDocumentId = invoiceDocumentId;
	}
}