package com.newco.marketplace.api.mobile.beans.so.addEstimate;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("estimateTask")
public class EstimateTask {
	
	@XStreamAlias("taskSeqNumber")
	private String taskSeqNumber;
	
    @XStreamAlias("taskName")
	private String taskName;
	
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

	public String getTaskSeqNumber() {
		return taskSeqNumber;
	}

	public void setTaskSeqNumber(String taskSeqNumber) {
		this.taskSeqNumber = taskSeqNumber;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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
