package com.newco.marketplace.vo.mobile.v2_0;

import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class SOTripDetailsVO extends CommonVO{
	
	private static final long serialVersionUID = 1L;
	public SOTripDetailsVO() {
		
	}
	
	private Integer soTripDetailsId;
	private Integer soTripId;
	private String changeType;
	private String changeComment;
	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
	private String modifiedBy;
	/**
	 * @return the soTripDetailsId
	 */
	public Integer getSoTripDetailsId() {
		return soTripDetailsId;
	}
	/**
	 * @param soTripDetailsId the soTripDetailsId to set
	 */
	public void setSoTripDetailsId(Integer soTripDetailsId) {
		this.soTripDetailsId = soTripDetailsId;
	}
	/**
	 * @return the soTripId
	 */
	public Integer getSoTripId() {
		return soTripId;
	}
	/**
	 * @param soTripId the soTripId to set
	 */
	public void setSoTripId(Integer soTripId) {
		this.soTripId = soTripId;
	}
	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}
	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	/**
	 * @return the changeComment
	 */
	public String getChangeComment() {
		return changeComment;
	}
	/**
	 * @param changeComment the changeComment to set
	 */
	public void setChangeComment(String changeComment) {
		this.changeComment = changeComment;
	}
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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
}
