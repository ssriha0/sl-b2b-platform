package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class SoContactPhone extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7176645363425504568L;
	private Integer soId;
	private Integer contactId;
	private Integer contactPhoneId;
	private Integer phoneTypeId;
	private Integer phoneClassId;
	private String lastName;
	private String phoneNumber;
	private String phoneNumberExt;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	public Integer getSoId() {
		return soId;
	}
	public void setSoId(Integer soId) {
		this.soId = soId;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Integer getContactPhoneId() {
		return contactPhoneId;
	}
	public void setContactPhoneId(Integer contactPhoneId) {
		this.contactPhoneId = contactPhoneId;
	}
	public Integer getPhoneTypeId() {
		return phoneTypeId;
	}
	public void setPhoneTypeId(Integer phoneTypeId) {
		this.phoneTypeId = phoneTypeId;
	}
	public Integer getPhoneClassId() {
		return phoneClassId;
	}
	public void setPhoneClassId(Integer phoneClassId) {
		this.phoneClassId = phoneClassId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumberExt() {
		return phoneNumberExt;
	}
	public void setPhoneNumberExt(String phoneNumberExt) {
		this.phoneNumberExt = phoneNumberExt;
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
	
	
	

}
