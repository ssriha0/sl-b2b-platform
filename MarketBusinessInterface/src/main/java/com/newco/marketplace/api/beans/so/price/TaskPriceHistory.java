package com.newco.marketplace.api.beans.so.price;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("priceHistory")
public class TaskPriceHistory {
	
	@XStreamAlias("taskPrice")
	private Double taskPrice;
	
	@XStreamAlias("changedDate")
	private String changedDate;
	
	@XStreamAlias("changedByUserName")
	private String changedByUserName;  
	
	@XStreamAlias("changedByUserId")
	private String changedByUserId;

	public Double getTaskPrice() {
		return taskPrice;
	}

	public void setTaskPrice(Double taskPrice) {
		this.taskPrice = taskPrice;
	}

	public String getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(String changedDate) {
		this.changedDate = changedDate;
	}

	public String getChangedByUserName() {
		return changedByUserName;
	}

	public void setChangedByUserName(String changedByUserName) {
		this.changedByUserName = changedByUserName;
	}

	public String getChangedByUserId() {
		return changedByUserId;
	}

	public void setChangedByUserId(String changedByUserId) {
		this.changedByUserId = changedByUserId;
	}
}
