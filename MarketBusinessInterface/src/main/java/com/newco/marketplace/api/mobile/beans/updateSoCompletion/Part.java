package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part information.
 * @author Infosys
 *
 */
@XStreamAlias("part")
public class Part {
	
	@XStreamAlias("partCoverage")
	private String partCoverage;
	
	@XStreamAlias("partSource")
	private String partSource;
	
	@XStreamAlias("nonSearsSource")
	private String nonSearsSource;
	
	@XStreamAlias("partDescription")
	private String partDescription;
	
	
	@XStreamAlias("partNumber")
	private String partNumber;
	
	@XStreamAlias("invoiceNumber")
	private String invoiceNumber;
	
	@XStreamAlias("divisionNumber")
	private String divisionNumber;
	
	
	@XStreamAlias("sourceNumber")
	private String sourceNumber;
	
	
	@XStreamAlias("unitCost")
	private String unitCost;

	@XStreamAlias("retailPrice")
	private String retailPrice;
	
	@XStreamAlias("qty")
	private Integer qty;
	
	
	
	private  String claimStatus;
	
	private String partInvoiceSource;
	
	
	

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	public String getPartInvoiceSource() {
		return partInvoiceSource;
	}

	public void setPartInvoiceSource(String partInvoiceSource) {
		this.partInvoiceSource = partInvoiceSource;
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

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
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

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getNonSearsSource() {
		return nonSearsSource;
	}

	public void setNonSearsSource(String nonSearsSource) {
		this.nonSearsSource = nonSearsSource;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getDivisionNumber() {
		return divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}

	public String getSourceNumber() {
		return sourceNumber;
	}

	public void setSourceNumber(String sourceNumber) {
		this.sourceNumber = sourceNumber;
	}
	
	
	
}
