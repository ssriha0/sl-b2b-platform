package com.newco.marketplace.dto.vo.serviceOfferings;

import java.io.Serializable;
import java.sql.Date;

public class PricingVO implements Serializable
{
	private static final long serialVersionUID = -1527770441184997995L; 
	
    private Integer offeringId;
    private Double price;
    private String zipcode;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private Boolean isCenter;
    //added for History
    private String action;
    private Boolean deleteInd;
    
    
	public Integer getOfferingId() {
		return offeringId;
	}
	public void setOfferingId(Integer offeringId) {
		this.offeringId = offeringId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Boolean getIsCenter() {
		return isCenter;
	}
	public void setIsCenter(Boolean isCenter) {
		this.isCenter = isCenter;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Boolean getDeleteInd() {
		return deleteInd;
	}
	public void setDeleteInd(Boolean deleteInd) {
		this.deleteInd = deleteInd;
	}
    
     
	
}
