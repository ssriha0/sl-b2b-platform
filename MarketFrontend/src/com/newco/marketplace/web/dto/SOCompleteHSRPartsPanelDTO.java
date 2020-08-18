package com.newco.marketplace.web.dto;

public class SOCompleteHSRPartsPanelDTO extends SerializedBaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	private String partCoverage;
	private String Source;
	private String partNumber;
	private String invoiceNumber;
	private float unitCost;
	private float retailPrice;
	private float finalPrice;
	private Integer qty;
	private String description;
	private float estNetProviderPayment;
	private String sourceNonSears;

	public SOCompleteHSRPartsPanelDTO(String partCoverage, String Source,
			String partNumber, String invoiceNumber, float unitCost,
			float retailPrice, Integer qty, String description,
			float estNetProviderPayment, float finalPrice, String sourceNonSears) {
		super();
		this.partCoverage = partCoverage;
		this.Source = Source;
		this.partNumber = partNumber;
		this.invoiceNumber = invoiceNumber;
		this.unitCost = unitCost;
		this.retailPrice = retailPrice;
		this.qty = qty;
		this.description = description;
		this.estNetProviderPayment = estNetProviderPayment;
		this.finalPrice = finalPrice;
		this.sourceNonSears = sourceNonSears;
	}

	public String getPartCoverage() {
		return partCoverage;
	}

	public void setPartCoverage(String partCoverage) {
		this.partCoverage = partCoverage;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
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

	public float getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}

	public float getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(float retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getEstNetProviderPayment() {
		return estNetProviderPayment;
	}

	public void setEstNetProviderPayment(float estNetProviderPayment) {
		this.estNetProviderPayment = estNetProviderPayment;
	}

	public float getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(float finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getSourceNonSears() {
		return sourceNonSears;
	}

	public void setSourceNonSears(String sourceNonSears) {
		this.sourceNonSears = sourceNonSears;
	}

}
