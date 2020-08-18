package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author ccarle5
 *
 */
public class SPNAuditorSearchResultVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 20090202L;

	private String membershipStatusId;
	private String membershipStatusName;
	private Integer providerFirmId;
	private String spnName;
	private Integer numEmployees;
	private String lockedByName;
	private Integer lockedById;
	private Integer spnId;
	private Date reviewedDt;
	private String providerFirmName;
	private Integer providerFirmStatusId;
	private String providerFirmSLStatus;
	private Boolean lockedRecord;
	private String lockedByLastName;
	private String lockedByFirstName;
	private Date modifiedDt;
	private Integer numSPNEmployees;
	private String memberStatus;
	
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	/**
	 * @return the membershipStatusId
	 */
	public String getMembershipStatusId() {
		return membershipStatusId;
	}
	/**
	 * @param membershipStatusId the membershipStatusId to set
	 */
	public void setMembershipStatusId(String membershipStatusId) {
		this.membershipStatusId = membershipStatusId;
	}
	/**
	 * @return the membershipStatusName
	 */
	public String getMembershipStatusName() {
		return membershipStatusName;
	}
	/**
	 * @param membershipStatusName the membershipStatusName to set
	 */
	public void setMembershipStatusName(String membershipStatusName) {
		this.membershipStatusName = membershipStatusName;
	}
	
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
	 * @return the spnName
	 */
	public String getSpnName() {
		return spnName;
	}
	/**
	 * @param spnName the spnName to set
	 */
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	/**
	 * @return the numEmployees
	 */
	public Integer getNumEmployees() {
		return numEmployees;
	}
	/**
	 * @param numEmployees the numEmployees to set
	 */
	public void setNumEmployees(Integer numEmployees) {
		this.numEmployees = numEmployees;
	}

	/**
	 * @return the lockedByName
	 */
	public String getLockedByName() {
		return lockedByName;
	}
	/**
	 * @param lockedByName the lockedByName to set
	 */
	public void setLockedByName(String lockedByName) {
		this.lockedByName = lockedByName;
	}
	/**
	 * @return the lockedById
	 */
	public Integer getLockedById() {
		return lockedById;
	}
	/**
	 * @param lockedById the lockedById to set
	 */
	public void setLockedById(Integer lockedById) {
		this.lockedById = lockedById;
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
	 * @return the reviewedDt
	 */
	public Date getReviewedDt() {
		return reviewedDt;
	}
	/**
	 * @param reviewedDt the reviewedDt to set
	 */
	public void setReviewedDt(Date reviewedDt) {
		this.reviewedDt = reviewedDt;
	}
	/**
	 * @return the providerFirmName
	 */
	public String getProviderFirmName() {
		return providerFirmName;
	}
	/**
	 * @param providerFirmName the providerFirmName to set
	 */
	public void setProviderFirmName(String providerFirmName) {
		this.providerFirmName = providerFirmName;
	}
	/**
	 * @return the providerFirmStatusId
	 */
	public Integer getProviderFirmStatusId() {
		return providerFirmStatusId;
	}
	/**
	 * @param providerFirmStatusId the providerFirmStatusId to set
	 */
	public void setProviderFirmStatusId(Integer providerFirmStatusId) {
		this.providerFirmStatusId = providerFirmStatusId;
	}
	/**
	 * @return the providerFirmSLStatus
	 */
	public String getProviderFirmSLStatus() {
		return providerFirmSLStatus;
	}
	/**
	 * @param providerFirmSLStatus the providerFirmSLStatus to set
	 */
	public void setProviderFirmSLStatus(String providerFirmSLStatus) {
		this.providerFirmSLStatus = providerFirmSLStatus;
	}
	/**
	 * @return the lockedRecord
	 */
	public Boolean getLockedRecord() {
		return lockedRecord;
	}
	/**
	 * @param lockedRecord the lockedRecord to set
	 */
	public void setLockedRecord(Boolean lockedRecord) {
		this.lockedRecord = lockedRecord;
	}
	/**
	 * @return the lockedByLastName
	 */
	public String getLockedByLastName() {
		return lockedByLastName;
	}
	/**
	 * @param lockedByLastName the lockedByLastName to set
	 */
	public void setLockedByLastName(String lockedByLastName) {
		this.lockedByLastName = lockedByLastName;
	}
	/**
	 * @return the lockedByFirstName
	 */
	public String getLockedByFirstName() {
		return lockedByFirstName;
	}
	/**
	 * @param lockedByFirstName the lockedByFirstName to set
	 */
	public void setLockedByFirstName(String lockedByFirstName) {
		this.lockedByFirstName = lockedByFirstName;
	}
	/**
	 * @return the modifiedDt
	 */
	public Date getModifiedDt() {
		return modifiedDt;
	}
	/**
	 * @param modifiedDt the modifiedDt to set
	 */
	public void setModifiedDt(Date modifiedDt) {
		this.modifiedDt = modifiedDt;
	}
	public Integer getNumSPNEmployees() {
		return numSPNEmployees;
	}
	public void setNumSPNEmployees(Integer numSPNEmployees) {
		this.numSPNEmployees = numSPNEmployees;
	}

}