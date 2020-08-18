package com.newco.marketplace.dto.vo.survey;

import java.sql.Timestamp;

public class SODetailVO {
	public String soId;
	public Integer buyerId;
	public Integer soStatus;
	private Timestamp completedDate;
	private Timestamp closedDate;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soID) {
		this.soId = soID;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}	
	public Integer getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(Integer soStatus) {
		this.soStatus = soStatus;
	}
	public Timestamp getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Timestamp completedDate) {
		this.completedDate = completedDate;
	}
	public Timestamp getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(Timestamp closedDate) {
		this.closedDate = closedDate;
	}	
}
