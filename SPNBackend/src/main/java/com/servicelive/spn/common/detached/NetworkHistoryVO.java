package com.servicelive.spn.common.detached;

import java.util.Date;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * 
 * @author svanloon
 *
 */
public class NetworkHistoryVO extends LoggableBaseDomain {

	private static final long serialVersionUID = 20090303L;

	private Integer spnId;
	private String spnName;
	//create_date
	//modified_date
	//modified_by
	private String firstName;
	private String middleName;
	private String lastName;
	private Integer buyerId;
	private Integer buyerResourceId;
	private String status;
	private String statusDescription;
	private String comments;
	private Date archiveDate;
	private Date effectiveDate;

	private String credential;
	public String getCredential() {
		return credential;
	}
	public void setCredential(String credential) {
		this.credential = credential;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the statusDescription
	 */
	public String getStatusDescription() {
		return statusDescription;
	}
	/**
	 * @param statusDescription the statusDescription to set
	 */
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the archiveDate
	 */
	public Date getArchiveDate() {
		return archiveDate;
	}
	/**
	 * @param archiveDate the archiveDate to set
	 */
	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}
	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * @return the buyerResourceId
	 */
	public Integer getBuyerResourceId() {
		return buyerResourceId;
	}
	/**
	 * @param buyerResourceId the buyerResourceId to set
	 */
	public void setBuyerResourceId(Integer buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}
}
