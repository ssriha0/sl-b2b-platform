package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

import com.newco.marketplace.dto.vo.LocationVO;

public class SoLocation extends LocationVO implements Comparable<LocationVO> {
	/**
	 *
	 */
	private static final long serialVersionUID = 4020273533733141746L;
	private String soId;
	private Integer locationId;
	private Integer locnTypeId;
	private Integer locnClassId;
	private String locnClassDesc;
	private String locnName;
	private String locnNotes;
	private Timestamp modifiedDate;
	private Timestamp createdDate;
	private String modifiedBy;
	private Integer contactLocTypeId;




	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}






	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getLocnTypeId() {
		return locnTypeId;
	}

	public void setLocnTypeId(Integer locnTypeId) {
		this.locnTypeId = locnTypeId;
	}

	public String getLocnName() {
		return locnName;
	}

	public void setLocnName(String locnName) {
		this.locnName = locnName;
	}

	public String getLocnNotes() {
		return locnNotes;
	}

	public void setLocnNotes(String locnNotes) {
		this.locnNotes = locnNotes;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the locnClassDesc
	 */
	public String getLocnClassDesc() {
		return locnClassDesc;
	}

	/**
	 * @param locnClassDesc the locnClassDesc to set
	 */
	public void setLocnClassDesc(String locnClassDesc) {
		this.locnClassDesc = locnClassDesc;
	}

	/**
	 * @return the locnClassId
	 */
	public Integer getLocnClassId() {
		return locnClassId;
	}

	/**
	 * @param locnClassId the locnClassId to set
	 */
	public void setLocnClassId(Integer locnClassId) {
		this.locnClassId = locnClassId;
	}

	public Integer getContactLocTypeId() {
		return contactLocTypeId;
	}

	public void setContactLocTypeId(Integer contactLocTypeId) {
		this.contactLocTypeId = contactLocTypeId;
	}

	@Override
	public Object getComparableString() {
		StringBuilder sb = new StringBuilder();
		sb.append((getAptNo() != null) ? getAptNo() : "");
		sb.append((getCity() != null) ? getCity() : "");
		//sb.append((getCountry() != null) ? getCountry() : "");
		//sb.append((getLocName() != null) ? getLocName() : "");
		//sb.append((getLocnClassDesc() != null) ? getLocnClassDesc() : "");
		//sb.append((getLocnClassId() != null) ? Integer.toString(getLocnClassId().intValue()) : "");
		//sb.append((getLocnName() != null) ? getLocnName() : "");
		sb.append((getLocnNotes() != null) ? getLocnNotes() : "");
		sb.append((getLocnTypeId() != null) ? Integer.toString(getLocnTypeId().intValue()) : "");
		sb.append((getState() != null) ? getState() : "");
		sb.append((getStreet1() != null) ? getStreet1() : "");
		sb.append((getStreet2() != null) ? getStreet2() : "");
		sb.append((getTimeZone() != null) ? getTimeZone() : "");
		sb.append((getZip() != null) ? getZip() : "");
		sb.append((getZip4() != null) ? getZip4() : "");
		return sb.toString();
	}

	@Override
	public int compareTo(LocationVO o) {
		if (!o.getComparableString().equals(this.getComparableString())) {
			return 1;
		}
		return 0;
	}

}
