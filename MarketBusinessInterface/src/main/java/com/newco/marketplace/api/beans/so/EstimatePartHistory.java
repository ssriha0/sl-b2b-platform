package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("part")
public class EstimatePartHistory {
	
	
	@XStreamAlias("itemId") 
	private Integer itemId;

	@XStreamAlias("partSeqNo")
	private Integer partSeqNo;

	@XStreamAlias("partNo")
	private String partNo;

	@XStreamAlias("partName")
	private String partName;

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
	
	@XStreamAlias("action")
	private String action;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getPartSeqNo() {
		return partSeqNo;
	}

	public void setPartSeqNo(Integer partSeqNo) {
		this.partSeqNo = partSeqNo;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
	


}
