package com.newco.marketplace.dto.vo.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SoContact extends Contact{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1162567638806236899L;
	//Do not remove individual phone parameters as 
	//it is needed for population of so related contact tables.
	private String soId;
	private String phoneNo="";
	private String phoneNoExt="";
	private String phoneClassId;
	private String faxNo="";
	private String cellNo="";
	private String homeNo="";
	private Integer locationId = null;
    private String street_1 = "";
    private String street_2 = "";
    private String city = "";
    private String stateCd = "";
    private String zip = "";
    private String zip4 = "";
    private String country = "";
    private Integer locnTypeId = null;
    private String locName = "";
    private Integer contactLocTypeId = null;
    private Integer soLocnId = null;
	    
	@Override
	public String getSoId() {
		return soId;
	}



	@Override
	public void setSoId(String soId) {
		this.soId = soId;
	}



	@Override
	public String getPhoneNo() {
		return phoneNo;
	}



	@Override
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}



	@Override
	public String getPhoneNoExt() {
		return phoneNoExt;
	}



	@Override
	public void setPhoneNoExt(String phoneNoExt) {
		this.phoneNoExt = phoneNoExt;
	}



	@Override
	public String getPhoneClassId() {
		return phoneClassId;
	}



	@Override
	public void setPhoneClassId(String phoneClassId) {
		this.phoneClassId = phoneClassId;
	}



	@Override
	public String getFaxNo() {
		return faxNo;
	}



	@Override
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}



	@Override
	public String getCellNo() {
		return cellNo;
	}



	@Override
	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}



	@Override
	public String getHomeNo() {
		return homeNo;
	}



	@Override
	public void setHomeNo(String homeNo) {
		this.homeNo = homeNo;
	}



	@Override
	public Integer getLocationId() {
		return locationId;
	}



	@Override
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}



	@Override
	public String getStreet_1() {
		return street_1;
	}



	@Override
	public void setStreet_1(String street_1) {
		this.street_1 = street_1;
	}



	@Override
	public String getStreet_2() {
		return street_2;
	}



	@Override
	public void setStreet_2(String street_2) {
		this.street_2 = street_2;
	}



	@Override
	public String getCity() {
		return city;
	}



	@Override
	public void setCity(String city) {
		this.city = city;
	}



	@Override
	public String getStateCd() {
		return stateCd;
	}



	@Override
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}



	@Override
	public String getZip() {
		return zip;
	}



	@Override
	public void setZip(String zip) {
		this.zip = zip;
	}



	@Override
	public String getZip4() {
		return zip4;
	}



	@Override
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}



	@Override
	public String getCountry() {
		return country;
	}



	@Override
	public void setCountry(String country) {
		this.country = country;
	}



	@Override
	public Integer getLocnTypeId() {
		return locnTypeId;
	}



	@Override
	public void setLocnTypeId(Integer locnTypeId) {
		this.locnTypeId = locnTypeId;
	}



	@Override
	public String getLocName() {
		return locName;
	}



	@Override
	public void setLocName(String locName) {
		this.locName = locName;
	}



	@Override
	public Integer getContactLocTypeId() {
		return contactLocTypeId;
	}



	@Override
	public void setContactLocTypeId(Integer contactLocTypeId) {
		this.contactLocTypeId = contactLocTypeId;
	}



	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("soId", getSoId())
			.append("contactId", getContactId())
			.append("businessName", getBusinessName())
			.append("lastName", getLastName())	
			.append("firstName", getFirstName())
			.append("mi", getMi())
			.append("suffix", getSuffix())
			.append("title", getTitle())
			.append("phoneNo", getPhoneNo())
			.append("phoneNoExt", getPhoneNoExt())
			.append("faxNo", getFaxNo())
			.append("cellNo", getCellNo())
			.append("email", getEmail())
			.append("honorific", getHonorific())
			.append("locationId", getLocationId()) 
			.append("street_1", getStreet_1())
			.append("street_2", getStreet_2())
			.append("city", getCity())
			.append("stateCd", getStateCd())
			.append("zip", getZip())
			.append("zip4", getZip4())
			.append("country", getCountry())
			.append("locnTypeId", getLocnTypeId())
			.append("locName", getLocName())
			.toString();
	}



	public Integer getSoLocnId() {
		return soLocnId;
	}



	public void setSoLocnId(Integer soLocnId) {
		this.soLocnId = soLocnId;
	}
		
}
