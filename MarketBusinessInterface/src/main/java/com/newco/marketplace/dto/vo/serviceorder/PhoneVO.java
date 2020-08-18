package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class PhoneVO extends SerializableBaseVO implements Comparable<PhoneVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -49100993851115552L;
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
	
	private Integer entityTypeId;
	
	
	
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
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
	
	public String getComparableString() {
		StringBuilder sb = new StringBuilder();
		sb.append((getPhoneNo() != null ? getPhoneNo() : ""));
		sb.append((getPhoneExt() != null ? getPhoneExt() : ""));
		return sb.toString();
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
	public int compareTo(PhoneVO o) {
		if (getComparableString().equals(o.getComparableString())) {
			return 0;
		}
		return 1;
	}
	
	
	

}
