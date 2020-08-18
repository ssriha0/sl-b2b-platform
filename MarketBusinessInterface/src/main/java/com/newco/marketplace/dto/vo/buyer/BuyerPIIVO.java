package com.newco.marketplace.dto.vo.buyer;



import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class BuyerPIIVO extends CommonVO{
	
	private static final long serialVersionUID = 1L;
	
	private Integer piiHistoryId;
	private String businessName;
	private String idType;
	private String idNo;
	private String idNoEnc;
	private String country;
	private String dob;
	private Date createdDate;
	private String modifiedBy;
	private Integer buyerId;
	
	public Integer getPiiHistoryId() {
		return piiHistoryId;
	}
	public void setPiiHistoryId(Integer piiHistoryId) {
		this.piiHistoryId = piiHistoryId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getIdNoEnc() {
		return idNoEnc;
	}
	public void setIdNoEnc(String idNoEnc) {
		this.idNoEnc = idNoEnc;
	}
	
	
	

}
