package com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.Document;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing invoice part information.
 * @author Infosys
 *
 */
@XStreamAlias("providerPart")
public class PartInvoice {
	

	@XStreamAlias("invoiceId")
	private String invoiceId;
	
	@XStreamAlias("partCoverage")
	private String partCoverage;
	
	@XStreamAlias("partSource")
	private String partSource;	
	
	@XStreamAlias("nonSearsSource")
	private String nonSearsSource;
		
	@XStreamAlias("invoiceNumber")
	private String invoiceNumber;

	@XStreamAlias("unitCost")
	private String unitCost;
	
	@XStreamAlias("retailPrice")
	private String retailPrice;

	@XStreamAlias("partStatus")
	private String partStatus;
	
	@XStreamAlias("qty")
	private Integer qty;
	
	@XStreamAlias("document")
	private Document document;

	
	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getPartCoverage() {
		return partCoverage;
	}

	public void setPartCoverage(String partCoverage) {
		this.partCoverage = partCoverage;
	}

	public String getPartSource() {
		return partSource;
	}

	public void setPartSource(String partSource) {
		this.partSource = partSource;
	}

	public String getNonSearsSource() {
		return nonSearsSource;
	}

	public void setNonSearsSource(String nonSearsSource) {
		this.nonSearsSource = nonSearsSource;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	public String getPartStatus() {
		return partStatus;
	}

	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
}
