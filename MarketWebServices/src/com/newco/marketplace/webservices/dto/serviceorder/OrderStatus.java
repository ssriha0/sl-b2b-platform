package com.newco.marketplace.webservices.dto.serviceorder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/*Used for create response*/

@XStreamAlias("orderStatus")
public class OrderStatus {
	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("createdDate")
	private String createdDate;
	
	@XStreamAlias("postedDate")
	private String postedDate;
	
	@XStreamAlias("activeDate")
	private String activeDate;
	
	@XStreamAlias("completedDate")
	private String completedDate;
	
	@XStreamAlias("closedDate")
	private String closedDate;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}
	public String getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}
	public String getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	public String getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}
}
