package com.newco.marketplace.vo.audit;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;


public class AuditVO extends SerializableBaseVO{

	/**
	 *
	 */
	private static final long serialVersionUID = 1841530717142902895L;
	private int auditTaskId = -1;
    private String wfState = null;
    private String wfEntity = null;
    private Integer resourceId = null;
    private Integer vendorId = null;
    private Integer wfStateId = null;
    private Integer documentId = null;
    private Integer auditLinkId = null;
    private Integer auditKeyId = null;
    private Integer reasonId = null;
    private String reviewedBy = null;
    private boolean sendEmailNotice = true;
    private Integer wfStatusId = null;
    private String [] reasonCodeIds = null;

    private Integer vendorCredentialId = null;
    private Integer resourceCredentialId = null;
    private Date reviewedDate;
    private Date modifiedDate;
    private Date createdDate;
    private Integer vendorCredentialTypeId = null;
    private Integer vendorCredentialCategoryTypeId = null;
    private Integer resourceCredentialTypeId = null;
    private Integer resourceCredentialCategoryTypeId = null;

    private String auditBusinessName;
    private Integer vendorPrimaryIndustryId;
    private String  reviewComments;




    /**
	 * @return the reviewedBy
	 */
	public String getReviewedBy() {
		return reviewedBy;
	}
	/**
	 * @param reviewedBy the reviewedBy to set
	 */
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}
	public AuditVO() {

        super();
    }
    public AuditVO(int vendorId, int resourceId, String wfState) {
       super();
        this.setVendorId(vendorId);
        this.setResourceId(resourceId);
        this.setWfState(wfState);
    }

    public AuditVO(int vendorId, int resourceId, String wfEntity, String wfState) {
        super();
         this.setVendorId(vendorId);
         this.setResourceId(resourceId);
         this.setWfEntity(wfEntity);
         this.setWfState(wfState);
     }

    public AuditVO(int resourceId, String wfState) {

        super();
        this.setResourceId(resourceId);
        this.setWfState(wfState);
    }

    public Integer getDocumentId() {

        return documentId;
    }

    public void setDocumentId(Integer documentId) {

        this.documentId = documentId;
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

    public String getWfEntity() {

        return wfEntity;
    }

    public void setWfEntity(String wfEntity) {

        this.wfEntity = wfEntity;
    }

    public String getWfState() {

        return wfState;
    }

    public void setWfState(String wfState) {

        this.wfState = wfState;
    }

    public Integer getWfStateId() {

        return wfStateId;
    }

    public void setWfStateId(Integer wfStateId) {

        this.wfStateId = wfStateId;
    }

    public String toString() {
        return ("wfState: " + getWfState() +
				" | wfEntity: " + getWfEntity() +
				" | resourceId: " + getResourceId() +
				" | vendorId: " + getVendorId() +
				" | wfStateId:" + getWfStateId() +
				" | auditKeyId: "+ getAuditKeyId() +
				" | documentId: "+ getDocumentId());
    }//toString

    public Integer getAuditLinkId() {

        return auditLinkId;
    }

    public void setAuditLinkId(Integer auditLinkId) {

        this.auditLinkId = auditLinkId;
    }
    public Integer getReasonId() {

        return reasonId;
    }
    public void setReasonId(Integer reasonId) {

        this.reasonId = reasonId;
    }
    public Integer getAuditKeyId() {

        return auditKeyId;
    }
    public void setAuditKeyId(Integer auditKeyId) {

        this.auditKeyId = auditKeyId;
    }
	/**
	 * @return the auditTaskId
	 */
	public int getAuditTaskId() {
		return auditTaskId;
	}
	/**
	 * @param auditTaskId the auditTaskId to set
	 */
	public void setAuditTaskId(int auditTaskId) {
		this.auditTaskId = auditTaskId;
	}
	/**
	 * @return the reasonCodeIds
	 */
	public String[] getReasonCodeIds() {
		return reasonCodeIds;
	}
	/**
	 * @param reasonCodeIds the reasonCodeIds to set
	 */
	public void setReasonCodeIds(String[] reasonCodeIds) {
		this.reasonCodeIds = reasonCodeIds;
	}
	/**
	 * @return the wfStatusId
	 */
	public Integer getWfStatusId() {
		return wfStatusId;
	}
	/**
	 * @param wfStatusId the wfStatusId to set
	 */
	public void setWfStatusId(Integer wfStatusId) {
		this.wfStatusId = wfStatusId;
	}
	/**
	 * @return the vendorCredentialId
	 */
	public Integer getVendorCredentialId() {
		return vendorCredentialId;
	}
	/**
	 * @param vendorCredentialId the vendorCredentialId to set
	 */
	public void setVendorCredentialId(Integer vendorCredentialId) {
		this.vendorCredentialId = vendorCredentialId;
	}
	/**
	 * @return the resourceCredentialId
	 */
	public Integer getResourceCredentialId() {
		return resourceCredentialId;
	}
	/**
	 * @param resourceCredentialId the resourceCredentialId to set
	 */
	public void setResourceCredentialId(Integer resourceCredentialId) {
		this.resourceCredentialId = resourceCredentialId;
	}
	public boolean isSendEmailNotice() {
		return sendEmailNotice;
	}
	public void setSendEmailNotice(boolean sendEmailNotice) {
		this.sendEmailNotice = sendEmailNotice;
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
	 * @return the vendorCredentialTypeId
	 */
	public Integer getVendorCredentialTypeId() {
		return vendorCredentialTypeId;
	}
	/**
	 * @param vendorCredentialTypeId the vendorCredentialTypeId to set
	 */
	public void setVendorCredentialTypeId(Integer vendorCredentialTypeId) {
		this.vendorCredentialTypeId = vendorCredentialTypeId;
	}
	/**
	 * @return the vendorCredentialCategoryTypeId
	 */
	public Integer getVendorCredentialCategoryTypeId() {
		return vendorCredentialCategoryTypeId;
	}
	/**
	 * @param vendorCredentialCategoryTypeId the vendorCredentialCategoryTypeId to set
	 */
	public void setVendorCredentialCategoryTypeId(
			Integer vendorCredentialCategoryTypeId) {
		this.vendorCredentialCategoryTypeId = vendorCredentialCategoryTypeId;
	}
	/**
	 * @return the auditBusinessName
	 */
	public String getAuditBusinessName() {
		return auditBusinessName;
	}
	/**
	 * @param auditBusinessName the auditBusinessName to set
	 */
	public void setAuditBusinessName(String auditBusinessName) {
		this.auditBusinessName = auditBusinessName;
	}
	/**
	 * @return the vendorPrimaryIndustryId
	 */
	public Integer getVendorPrimaryIndustryId() {
		return vendorPrimaryIndustryId;
	}
	/**
	 * @param vendorPrimaryIndustryId the vendorPrimaryIndustryId to set
	 */
	public void setVendorPrimaryIndustryId(Integer vendorPrimaryIndustryId) {
		this.vendorPrimaryIndustryId = vendorPrimaryIndustryId;
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
	/**
	 * @return the resourceCredentialTypeId
	 */
	public Integer getResourceCredentialTypeId() {
		return resourceCredentialTypeId;
	}
	/**
	 * @param resourceCredentialTypeId the resourceCredentialTypeId to set
	 */
	public void setResourceCredentialTypeId(Integer resourceCredentialTypeId) {
		this.resourceCredentialTypeId = resourceCredentialTypeId;
	}
	/**
	 * @return the resourceCredentialCategoryTypeId
	 */
	public Integer getResourceCredentialCategoryTypeId() {
		return resourceCredentialCategoryTypeId;
	}
	/**
	 * @param resourceCredentialCategoryTypeId the resourceCredentialCategoryTypeId to set
	 */
	public void setResourceCredentialCategoryTypeId(
			Integer resourceCredentialCategoryTypeId) {
		this.resourceCredentialCategoryTypeId = resourceCredentialCategoryTypeId;
	}


}//AuditVO
