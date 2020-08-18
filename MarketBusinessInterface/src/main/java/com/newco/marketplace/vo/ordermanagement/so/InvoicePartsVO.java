package com.newco.marketplace.vo.ordermanagement.so;

import com.sears.os.vo.SerializableBaseVO;

public class InvoicePartsVO extends SerializableBaseVO {


	private String soId;
	
	private String partCoverage;

	private String partSource;

	private String partNumber;

	private String partDescription;

	private String invoiceNumber;

	private String unitCost;

	private String retailPrice;

	private Integer qty;

	private String nonSearsSource;

	private String divisionNumber;

	private String sourceNumber;

	private String category;

	private String partStatus;

	
	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
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

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPartStatus() {
		return partStatus;
	}

	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}
	
	
}