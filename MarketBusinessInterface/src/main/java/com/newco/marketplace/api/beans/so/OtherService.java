package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("otherService")
public class OtherService {
	
	@XStreamAlias("itemId") 
	private Integer itemId;

	@XStreamAlias("otherServiceSeqNumber")
	private Integer otherServiceSeqNumber;
	
	@XStreamAlias("otherServiceType")
	private String otherServiceType;
	
	@XStreamAlias("otherServiceName")
	private String otherServiceName;

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

	public Integer getOtherServiceSeqNumber() {
		return otherServiceSeqNumber;
	}

	public void setOtherServiceSeqNumber(Integer otherServiceSeqNumber) {
		this.otherServiceSeqNumber = otherServiceSeqNumber;
	}

	public String getOtherServiceType() {
		return otherServiceType;
	}

	public void setOtherServiceType(String otherServiceType) {
		this.otherServiceType = otherServiceType;
	}

	public String getOtherServiceName() {
		return otherServiceName;
	}

	public void setOtherServiceName(String otherServiceName) {
		this.otherServiceName = otherServiceName;
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