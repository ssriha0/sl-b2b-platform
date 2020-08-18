package com.newco.marketplace.api.beans.so.addEstimate;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("estimatePart")
public class EstimatePart {
	
	
	@XStreamAlias("partSeqNumber")
	private String partSeqNumber;
	
	@XStreamAlias("partNumber")
	private String partNumber;
	
	@XStreamAlias("partName")
	private String partName;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("unitPrice")
	private String unitPrice;
	
	@XStreamAlias("quantity")
	private String quantity;
	
	@XStreamAlias("discount")
	private String discount;
	
	@XStreamAlias("totalPrice")
	private String totalPrice;
	
	@XStreamAlias("additionalDetails")
	private String additionalDetails;


	public String getPartSeqNumber() {
		return partSeqNumber;
	}

	public void setPartSeqNumber(String partSeqNumber) {
		this.partSeqNumber = partSeqNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	
}
