package com.newco.marketplace.vo.mobile;

import java.util.Date;


public class CustomReferenceVO {


	private String soId;
	
	private Integer buyerRefTypeId;
	
	private String buyerRefType;

	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private String modifiedBy;
	

	private String buyerRefValue;

	private Integer custRefId;

	
	public Integer getCustRefId() {
		return custRefId;
	}


	public void setCustRefId(Integer custRefId) {
		this.custRefId = custRefId;
	}


	public String getSoId() {
		return soId;
	}


	public void setSoId(String soId) {
		this.soId = soId;
	}


	public Integer getBuyerRefTypeId() {
		return buyerRefTypeId;
	}


	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}


	public String getBuyerRefType() {
		return buyerRefType;
	}


	public void setBuyerRefType(String buyerRefType) {
		this.buyerRefType = buyerRefType;
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


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getBuyerRefValue() {
		return buyerRefValue;
	}


	public void setBuyerRefValue(String buyerRefValue) {
		this.buyerRefValue = buyerRefValue;
	}
	
	

}
