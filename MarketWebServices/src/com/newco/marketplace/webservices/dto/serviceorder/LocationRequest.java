package com.newco.marketplace.webservices.dto.serviceorder;

import java.sql.Timestamp;

import org.codehaus.xfire.aegis.type.java5.XmlElement;

import com.newco.marketplace.dto.vo.LocationVO;

public class LocationRequest extends LocationVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9063288378623160265L;
	private String soId;
	private Integer locnTypeId;
	private Integer locnClassId;
	private String locnClassDesc;
	private String locnName;
	private String locnNotes;
	private Timestamp modifiedDate;
	private Timestamp createdDate;
	private String modifiedBy;


	

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
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
	
	@Override
	@XmlElement(minOccurs="1", nillable=false)
	public String getZip() {
		return super.getZip();
	}

}
