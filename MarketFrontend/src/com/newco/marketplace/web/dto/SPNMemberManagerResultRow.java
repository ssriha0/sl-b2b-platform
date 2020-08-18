package com.newco.marketplace.web.dto;

import java.util.List;

public class SPNMemberManagerResultRow extends SerializedBaseDTO{

	private Integer resourceID;
	private String resourceName;
	private Integer companyID;
	private Double rating;
	private String status;
	private Integer totalOrders;
	private List<String> attachments;
	private String selected;
	
	
	public SPNMemberManagerResultRow(
								Integer resourceID,
								String resourceName,
								Integer companyID,
								Double rating,
								String status,
								Integer totalOrders
								)
	{
		this.resourceID = resourceID;
		this.resourceName = resourceName;
		this.companyID = companyID;
		this.rating = rating;
		this.status = status;
		this.totalOrders = totalOrders;
		
	}
	
	
	public Integer getResourceID() {
		return resourceID;
	}
	public void setResourceID(Integer resourceID) {
		this.resourceID = resourceID;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Integer getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
	}
	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
}
