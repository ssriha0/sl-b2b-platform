package com.newco.marketplace.webservices.dto.serviceorder;

import java.sql.Timestamp;

public class PhoneRequest {
	
	private String soId;
	private Integer soContactId;
	private Integer soContactPhoneId;
	
	private String phoneNo;
	private String phoneExt;
	private Integer phoneType;
	private Integer classId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	private Integer primaryContactInd;
	
	
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPhoneExt() {
		return phoneExt;
	}
	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}
	public Integer getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(Integer phoneType) {
		this.phoneType = phoneType;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getSoContactId() {
		return soContactId;
	}
	public void setSoContactId(Integer soContactId) {
		this.soContactId = soContactId;
	}
	public Integer getSoContactPhoneId() {
		return soContactPhoneId;
	}
	public void setSoContactPhoneId(Integer soContactPhoneId) {
		this.soContactPhoneId = soContactPhoneId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getPrimaryContactInd() {
		return primaryContactInd;
	}
	public void setPrimaryContactInd(Integer primaryContactInd) {
		this.primaryContactInd = primaryContactInd;
	}
	
	
	


}
