package com.newco.marketplace.api.mobile.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("laborTask")
@XmlAccessorType(XmlAccessType.FIELD)
public class LaborTask {
	
	@XStreamAlias("itemId")
	private Integer itemId;
	
	@XStreamAlias("taskSeqNumber")
	private Integer taskSeqNumber;
	
	@XStreamAlias("taskName")
	private String taskName;
	
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
	
	

	public Integer getItemId() {
		return itemId;
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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


    


	public Integer getTaskSeqNumber() {
		return taskSeqNumber;
	}


	public void setTaskSeqNumber(Integer taskSeqNumber) {
		this.taskSeqNumber = taskSeqNumber;
	}


	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getAdditionalDetails() {
		return additionalDetails;
	}


	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
			
}
