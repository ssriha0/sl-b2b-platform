package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("labortask")
public class Labortask {
	
	
	@XStreamAlias("itemId") 
	private Integer itemId;
	
	@XStreamAlias("taskSeqNo") 
	private Integer taskSeqNo;
	
	@XStreamAlias("taskName")
	private String taskName;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("unitPrice")
	private String unitPrice;
	
	@XStreamAlias("quantity")
	private Integer quantity;
	
	@XStreamAlias("totalPrice")
	private String totalPrice;
	
	@XStreamAlias("additionalDetails")
	private String additionalDetails;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getTaskSeqNo() {
		return taskSeqNo;
	}

	public void setTaskSeqNo(Integer taskSeqNo) {
		this.taskSeqNo = taskSeqNo;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
