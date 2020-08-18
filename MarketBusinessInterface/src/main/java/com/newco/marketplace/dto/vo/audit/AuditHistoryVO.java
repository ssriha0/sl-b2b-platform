/**
 *
 */
package com.newco.marketplace.dto.vo.audit;

import java.util.Date;

import com.newco.marketplace.vo.MPBaseVO;

/**
 * @author hoza
 *
 */
public class AuditHistoryVO extends MPBaseVO {

	private static final long serialVersionUID = 1L;
	private Date reviewedDate;
	private Date modifiedDate;
	private String type;
	private String status;
	private String reasonCode;
	private String description;
	private String auditor;
	private Integer vendorId;
	private Integer id;
	private Integer resourceId;
	private String reviewComments;
	private Integer statusId;
	private Integer reasonCodeId;
	private Integer documentId;
	private Integer auditTaskId;
	private String firmName;
	private Integer auditLinkId;
	private Integer auditKeyId;
	private String tableName;
	private String keyName;
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public Integer getReasonCodeId() {
		return reasonCodeId;
	}
	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public Integer getAuditTaskId() {
		return auditTaskId;
	}
	public void setAuditTaskId(Integer auditTaskId) {
		this.auditTaskId = auditTaskId;
	}
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public Integer getAuditLinkId() {
		return auditLinkId;
	}
	public void setAuditLinkId(Integer auditLinkId) {
		this.auditLinkId = auditLinkId;
	}
	public Integer getAuditKeyId() {
		return auditKeyId;
	}
	public void setAuditKeyId(Integer auditKeyId) {
		this.auditKeyId = auditKeyId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/* (non-Javadoc)
	 * @see com.sears.os.vo.ABaseVO#toString()
	 */
	@Override
	public String toString() {

		 return ("<AuditHistoryVO>"+"   vendorId: " + vendorId +
				 "	id : 	" + id +
                 "   auditor: " + auditor +
                 "   description: " + description +
                 "   reasonCode: " + reasonCode +
                 "   status:" + status +
                 "   type: "+ type +
                 "   modifiedDate: "+ modifiedDate +
                 "	 reviewedDate: " + reviewedDate +
                 "</AuditHistoryVO>");
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}
	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the auditor
	 */
	public String getAuditor() {
		return auditor;
	}
	/**
	 * @param auditor the auditor to set
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the reviewComments
	 */
	public String getReviewComments() {
		return reviewComments;
	}
	/**
	 * @param reviewComments the reviewComments to set
	 */
	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}

}
