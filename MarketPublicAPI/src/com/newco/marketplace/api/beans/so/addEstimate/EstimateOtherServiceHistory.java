package com.newco.marketplace.api.beans.so.addEstimate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("otherService")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimateOtherServiceHistory {
	
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
	private Double unitPrice;

	@XStreamAlias("quantity")
	private Integer quantity;

	@XStreamAlias("totalPrice")
	private Double totalPrice;

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

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
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
