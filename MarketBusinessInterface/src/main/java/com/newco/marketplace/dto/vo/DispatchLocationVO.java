package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author rranja1
 *
 */
public class DispatchLocationVO extends SerializableBaseVO implements Comparable<DispatchLocationVO>{
	private static final long serialVersionUID = 1L;
	private Integer locnId;
	private String zip = "";
	private String city = "";
	private String state = "";
	private String street1 = "";
	private String street2 = "";
	private String country = "";
	private String zip4 = "";
	private String aptNo="";
	private String locName="";
	private String latitude;
	private String longitude;
	private String timeZone;
	private Integer defaultLocn;
	private int spnId;
	private int slNodeId;
	private String wfStatus;
	private String vendorId;
	private String spnJoin;
	private String skillJoin;
	private boolean updateDeleteFlag;
	
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getWfStatus() {
		return wfStatus;
	}
	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip == null ? "" : zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city ==null ? "" : city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state ==null ? "" : state;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country == null ? "" : country;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1 == null ? "" : street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2 == null ? "" :  street2;
	}
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4 == null ? "" : zip4;
	}
	public String getAptNo() {
		return aptNo;
	}
	public void setAptNo(String aptNo) {
		this.aptNo = aptNo== null ? "" :  aptNo;
	}
	public Integer getLocnId() {
		return locnId;
	}
	public void setLocnId(Integer locnId) {
		this.locnId = locnId;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Integer getDefaultLocn() {
		return defaultLocn;
	}
	public void setDefaultLocn(Integer defaultLocn) {
		this.defaultLocn = defaultLocn;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public int getSpnId() {
		return spnId;
	}
	public void setSpnId(int spnId) {
		this.spnId = spnId;
	}
	public int getSlNodeId() {
		return slNodeId;
	}
	public void setSlNodeId(int slNodeId) {
		this.slNodeId = slNodeId;
	}
	public String getSpnJoin() {
		return spnJoin;
	}
	public void setSpnJoin(String spnJoin) {
		this.spnJoin = spnJoin;
	}
	public String getSkillJoin() {
		return skillJoin;
	}
	public void setSkillJoin(String skillJoin) {
		this.skillJoin = skillJoin;
	}
	public boolean isUpdateDeleteFlag() {
		return updateDeleteFlag;
	}
	public void setUpdateDeleteFlag(boolean updateDeleteFlag) {
		this.updateDeleteFlag = updateDeleteFlag;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(DispatchLocationVO comparewith) {
		if( this.getComparableString().equals(comparewith.getComparableString()) ) {
			return 0 ;
		}
		return 1;
	}

	public Object getComparableString() {
		StringBuilder sb = new StringBuilder();
		sb.append((getAptNo() != null) ? getAptNo() : "");
		sb.append((getCity() != null) ? getCity() : "");
		//sb.append((getCountry() != null) ? getCountry() : "");
		//sb.append((getLocName() != null) ? getLocName() : "");
		//sb.append((getLocnClassDesc() != null) ? getLocnClassDesc() : "");
		//sb.append((getLocnClassId() != null) ? Integer.toString(getLocnClassId().intValue()) : "");
		//sb.append((getLocnName() != null) ? getLocnName() : "");
		/*sb.append((getLocnNotes() != null) ? getLocnNotes() : "");
		sb.append((getLocnTypeId() != null) ? Integer.toString(getLocnTypeId().intValue()) : "");*/
		sb.append((getState() != null) ? getState() : "");
		sb.append((getStreet1() != null) ? getStreet1() : "");
		sb.append((getStreet2() != null) ? getStreet2() : "");
		sb.append((getTimeZone() != null) ? getTimeZone() : "");
		sb.append((getZip() != null) ? getZip() : "");
		sb.append((getZip4() != null) ? getZip4() : "");
		return sb.toString();
	}
	
	/**
	 * 
	 */
	public DispatchLocationVO() {
		super();
	}
	/**
	 * @param locnId
	 * @param zip
	 * @param city
	 * @param state
	 * @param street1
	 * @param street2
	 * @param country
	 * @param locName
	 * @param latitude
	 * @param longitude
	 * @param timeZone
	 * @param defaultLocn
	 * @param vendorId
	 */
	public DispatchLocationVO(Integer locnId, String zip, String city, String state, String street1, String street2, String country,
			String locName, String latitude, String longitude, String timeZone, Integer defaultLocn, String vendorId) {
		super();
		this.locnId = locnId;
		this.zip = zip;
		this.city = city;
		this.state = state;
		this.street1 = street1;
		this.street2 = street2;
		this.country = country;
		this.locName = locName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeZone = timeZone;
		this.defaultLocn = defaultLocn;
		this.vendorId = vendorId;
	}
}
