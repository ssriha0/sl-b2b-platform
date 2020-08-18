package com.newco.marketplace.vo.provider;


/**
 * @author KSudhanshu
 *
 */
public class AuditVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 579445604855404224L;
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
    private Boolean recertificationInd;
    
    private boolean viaAPI=false;
    
    
	public Boolean getRecertificationInd() {
		return recertificationInd;
	}
	public void setRecertificationInd(Boolean recertificationInd) {
		this.recertificationInd = recertificationInd;
	}
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

    @Override
	public String toString() {
        return ("<AUDITVO>"+"   wfState: " + getWfState() +
                            "   wfEntity: " + getWfEntity() +
                            "   resourceId: " + getResourceId() +
                            "   vendorId: " + getVendorId() +
                            "   wfStateId:" + getWfStateId() +
                            "   auditKeyId: "+ getAuditKeyId() +
                            "   documentId: "+ getDocumentId() +
                            "</AUDITVO>");
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
	public boolean isViaAPI() {
		return viaAPI;
	}
	public void setViaAPI(boolean viaAPI) {
		this.viaAPI = viaAPI;
	}
	
	
	

}//AuditVO
