/**
 * 
 */
package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;

import java.util.Date;


//R11.0
//Value Object class for fetching Background Check History details
public class BackgroundCheckHistoryVO implements Serializable {

	private static final long serialVersionUID = 20090317;
	
	
	private Integer resourceId;
	private Integer vendorId;
	private Integer bgCheckId;
	private Date verificationDate;
	private Date reverificationDate;
	private Date displayDate;
	private String recertificationStatus;
	private String changingComments;
	private String backgroundStatus;
	private String fmtDisplayDate;
	private String fmtVerificationDate;
	private String fmtReverificationDate;
	private Integer criteriaIdCount; 


	public Integer getCriteriaIdCount() {
		return criteriaIdCount;
	}
	public void setCriteriaIdCount(Integer criteriaIdCount) {
		this.criteriaIdCount = criteriaIdCount;
	}
	public String getFmtDisplayDate() {
		return fmtDisplayDate;
	}
	public void setFmtDisplayDate(String fmtDisplayDate) {
		this.fmtDisplayDate = fmtDisplayDate;
	}
	public String getFmtVerificationDate() {
		return fmtVerificationDate;
	}
	public void setFmtVerificationDate(String fmtVerificationDate) {
		this.fmtVerificationDate = fmtVerificationDate;
	}
	public String getFmtReverificationDate() {
		return fmtReverificationDate;
	}
	public void setFmtReverificationDate(String fmtReverificationDate) {
		this.fmtReverificationDate = fmtReverificationDate;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getBgCheckId() {
		return bgCheckId;
	}
	public void setBgCheckId(Integer bgCheckId) {
		this.bgCheckId = bgCheckId;
	}
	public Date getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(Date verificationDate) {
		this.verificationDate = verificationDate;
	}
	public Date getReverificationDate() {
		return reverificationDate;
	}
	public void setReverificationDate(Date reverificationDate) {
		this.reverificationDate = reverificationDate;
	}
	public String getRecertificationStatus() {
		return recertificationStatus;
	}
	public void setRecertificationStatus(String recertificationStatus) {
		this.recertificationStatus = recertificationStatus;
	}
	public String getChangingComments() {
		return changingComments;
	}
	public void setChangingComments(String changingComments) {
		this.changingComments = changingComments;
	}
	public String getBackgroundStatus() {
		return backgroundStatus;
	}
	public void setBackgroundStatus(String backgroundStatus) {
		this.backgroundStatus = backgroundStatus;
	}
	public Date getDisplayDate() {
		return displayDate;
	}
	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}
	
	

	
}
