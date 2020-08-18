package com.newco.marketplace.vo.provider;
import java.util.Date;

public class ResourceLicense extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2998687985607517690L;
	private int resourceLicId = -1;
	private int resourceId = -1;
	private String licNo = null;
	private Date licExpDate;
	private String licCity = null;
	private String licCounty = null;
	private String licDesc = null;
	private Date createdDate;
	private Date modifiedDate;
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the licCity
	 */
	public String getLicCity() {
		return licCity;
	}
	/**
	 * @param licCity the licCity to set
	 */
	public void setLicCity(String licCity) {
		this.licCity = licCity;
	}
	/**
	 * @return the licCounty
	 */
	public String getLicCounty() {
		return licCounty;
	}
	/**
	 * @param licCounty the licCounty to set
	 */
	public void setLicCounty(String licCounty) {
		this.licCounty = licCounty;
	}
	/**
	 * @return the licDesc
	 */
	public String getLicDesc() {
		return licDesc;
	}
	/**
	 * @param licDesc the licDesc to set
	 */
	public void setLicDesc(String licDesc) {
		this.licDesc = licDesc;
	}
	/**
	 * @return the licExpDate
	 */
	public Date getLicExpDate() {
		return licExpDate;
	}
	/**
	 * @param licExpDate the licExpDate to set
	 */
	public void setLicExpDate(Date licExpDate) {
		this.licExpDate = licExpDate;
	}
	/**
	 * @return the licNo
	 */
	public String getLicNo() {
		return licNo;
	}
	/**
	 * @param licNo the licNo to set
	 */
	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the resourceId
	 */
	public int getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the resourceLicId
	 */
	public int getResourceLicId() {
		return resourceLicId;
	}
	/**
	 * @param resourceLicId the resourceLicId to set
	 */
	public void setResourceLicId(int resourceLicId) {
		this.resourceLicId = resourceLicId;
	}
	
	
	
	
	
}
