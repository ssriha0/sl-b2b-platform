package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author ccarle5
 *
 */
public class SPNAuditorSearchCriteriaVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 20090202L;

	private Integer buyerId;
	private String searchByType;
	private String providerFirmName;
	private String providerFirmNumber;
	private Integer marketId;
	private String zipCode;
	private Integer spnId;
	private String stateCd;
	private Date lockReferenceTime;
	private Date reviewedDate;
	private Integer districtId;
	private String providerFirmStatus;
	private Boolean viewAll;
	private String memberStatus;
	
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	/**
	 * @return the searchByType
	 */
	public String getSearchByType() {
		return searchByType;
	}
	/**
	 * @param searchByType the searchByType to set
	 */
	public void setSearchByType(String searchByType) {
		this.searchByType = searchByType;
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
	 * @return the providerFirmNumber
	 */
	public String getProviderFirmNumber() {
		return providerFirmNumber;
	}
	/**
	 * @param providerFirmNumber the providerFirmNumber to set
	 */
	public void setProviderFirmNumber(String providerFirmNumber) {
		this.providerFirmNumber = providerFirmNumber;
	}
	/**
	 * @return the marketId
	 */
	public Integer getMarketId() {
		return marketId;
	}
	/**
	 * @param marketId the marketId to set
	 */
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	 * @return the stateCd
	 */
	public String getStateCd() {
		return stateCd;
	}
	/**
	 * @param stateCd the stateCd to set
	 */
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	/**
	 * @return the lockReferenceTime
	 */
	public Date getLockReferenceTime() {
		return lockReferenceTime;
	}
	/**
	 * @param lockReferenceTime the lockReferenceTime to set
	 */
	public void setLockReferenceTime(Date lockReferenceTime) {
		this.lockReferenceTime = lockReferenceTime;
	}
	/**
	 * @return the reviewedDate
	 */
	public Date getReviewedDate() {
		return reviewedDate;
	}
	/**
	 * @param reviewedDate the reviewedDate to set
	 */
	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}
	/**
	 * @return the districtId
	 */
	public Integer getDistrictId() {
		return districtId;
	}
	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	/**
	 * @return the providerFirmStatus
	 */
	public String getProviderFirmStatus() {
		return providerFirmStatus;
	}
	/**
	 * @param providerFirmStatus the providerFirmStatus to set
	 */
	public void setProviderFirmStatus(String providerFirmStatus) {
		this.providerFirmStatus = providerFirmStatus;
	}
	public Boolean getViewAll() {
		return viewAll;
	}
	public void setViewAll(Boolean viewAll) {
		this.viewAll = viewAll;
	}
	

	
	
}