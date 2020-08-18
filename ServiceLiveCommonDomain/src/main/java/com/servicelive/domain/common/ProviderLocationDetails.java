package com.servicelive.domain.common;


import java.sql.Timestamp;

//SL-19381
//Java Class added to hold the information needed to display contact information of a Firm

public class ProviderLocationDetails  {

	private Integer locnId;
	private String locnName = "";
	private String street1 = "";
	private String street2 = "";
	private String city = "";
	private String stateCd = null;
	private String zip = "";
	private String zip4 = "";
	private String country = "";
	private Integer locnTypeId = new Integer(-1);
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy = "";
	private String aptNo = "";
	
	public String getCity() {
		return city;
	}
	public String getAptNo() {
		return aptNo;
	}
	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getLocnId() {
		return locnId;
	}
	public void setLocnId(Integer locnId) {
		this.locnId = locnId;
	}
	public String getLocnName() {
		return locnName;
	}
	public void setLocnName(String locnName) {
		this.locnName = locnName;
	}
	public Integer getLocnTypeId() {
		return locnTypeId;
	}
	public void setLocnTypeId(Integer locnTypeId) {
		this.locnTypeId = locnTypeId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	
	
}