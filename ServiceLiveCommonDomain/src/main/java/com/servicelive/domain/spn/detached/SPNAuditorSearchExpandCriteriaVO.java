package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author ccarle5
 *
 */
public class SPNAuditorSearchExpandCriteriaVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 20090202L;

	private Integer providerFirmId;
	private Integer spnId;
	private Integer fromSearch;
	private String originalModifiedDate;
	private Date orginalModifiedDate_Date;
	private Boolean lockedRecord;
	private Boolean lockedByMe;
	private Integer networkId;
	private Integer requirementType;
	
	
	/**
	 * @return the providerFirmId
	 */
	public Integer getProviderFirmId() {
		return providerFirmId;
	}
	/**
	 * @param providerFirmId the providerFirmId to set
	 */
	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getFromSearch() {
		return fromSearch;
	}
	/**
	 * 
	 * @param fromSearch
	 */
	public void setFromSearch(Integer fromSearch) {
		this.fromSearch = fromSearch;
	}
	/**
	 * 
	 * @return String
	 */
	public String getOriginalModifiedDate() {
		return originalModifiedDate;
	}
	/**
	 * 
	 * @param originalModifiedDate
	 */
	public void setOriginalModifiedDate(String originalModifiedDate) {
		this.originalModifiedDate = originalModifiedDate;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getLockedRecord() {
		return lockedRecord;
	}
	/**
	 * 
	 * @param lockedRecord
	 */
	public void setLockedRecord(Boolean lockedRecord) {
		this.lockedRecord = lockedRecord;
	}
	/**
	 * 
	 * @return Date
	 */
	public Date getOrginalModifiedDate_Date() {
		return orginalModifiedDate_Date;
	}
	/**
	 * 
	 * @param orginalModifiedDate_Date
	 */
	public void setOrginalModifiedDate_Date(Date orginalModifiedDate_Date) {
		this.orginalModifiedDate_Date = orginalModifiedDate_Date;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getLockedByMe() {
		return lockedByMe;
	}
	/**
	 * 
	 * @param lockedByMe
	 */
	public void setLockedByMe(Boolean lockedByMe) {
		this.lockedByMe = lockedByMe;
	}
	
	public Integer getRequirementType() {
		return requirementType;
	}
	public void setRequirementType(Integer requirementType) {
		this.requirementType = requirementType;
	}
	public Integer getNetworkId() {
		return networkId;
	}
	public void setNetworkId(Integer networkId) {
		this.networkId = networkId;
	}
}