package com.newco.marketplace.web.dto.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("TeamCredential")
public class TeamCredentialsDto extends BaseDto {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4365837082999791499L;
	@XStreamAlias("CategoryId")
	private int categoryId;
	@XStreamAlias("City")
    private String city = "";
	@XStreamAlias("County")
    private String county = "";
	@XStreamAlias("CredentialCatList")
    private List credentialCatList;
	@XStreamAlias("CredentialDocument")
    private FormFile credentialDocument;
	@XStreamAlias("CredentialDocumentId")
    private int credentialDocumentId;
	@XStreamAlias("CredentialNumber")
    private String credentialNumber = "";
	@XStreamAlias("cCredentialsList")
    private List credentialsList;
	@XStreamAlias("CredentialTypeList")
    private List credentialTypeList;
	@XStreamAlias("ExpirationDate")
    private String expirationDate;
	@XStreamAlias("IssueDate")
    private String issueDate = "";
	@XStreamAlias("IssuerName")
    private String issuerName = "";
	@XStreamAlias("LicenseName")
    private String licenseName = "";
	@XStreamAlias("No")
    private String no = "";
	@XStreamAlias("Notes")
    private String notes = "";
	@XStreamAlias("PassCredentials")
    private boolean passCredentials;
	@XStreamAlias("ResourceCredId")
    private int resourceCredId;
	@XStreamAlias("ResourceId")
    private int resourceId;
	@XStreamAlias("State")
    private String state = "";
	@XStreamAlias("TypeId")
    private int typeId = -1;
	@XStreamAlias("Size")
    private int size=0;
	@XStreamAlias("VendorId")
    private int vendorId;
	@XStreamAlias("ResourceName")
    private String resourceName;

	@XStreamAlias("WfStatusList")
    private List wfStatusList;
	@XStreamAlias("WfStatusId")
    private int wfStatusId = -1;
	@XStreamAlias("AuditReasonCodesList")
    private List auditReasonCodesList;
	@XStreamAlias("AuditReasonCodeId")
    private int auditReasonCodeId = -1;
	@XStreamAlias("DisableFields")
    private boolean disableFields =  true;
	@XStreamAlias("HideFields")
    private boolean hideFields = true;
	@XStreamAlias("AuditorName")
    private String auditorName = "";
	@XStreamAlias("AuditCredId")
    private int auditCredId = -1;
	@XStreamAlias("ReasonCodeIds")
    private String[] reasonCodeIds;
    
	//SL-20645 : adding new variable to hold the auditTimeLoggingId
	@XStreamAlias("AuditTimeLoggingIdNew")
    private String auditTimeLoggingIdNew;
	
    public String getAuditTimeLoggingIdNew() {
		return auditTimeLoggingIdNew;
	}

	public void setAuditTimeLoggingIdNew(String auditTimeLoggingIdNew) {
		this.auditTimeLoggingIdNew = auditTimeLoggingIdNew;
	}

	//SL-21142
		@XStreamAlias("isFileUploaded")
		private Boolean isFileUploaded=false;
		
		public Boolean getIsFileUploaded() {
			return isFileUploaded;
		}

		public void setIsFileUploaded(Boolean isFileUploaded) {
			this.isFileUploaded = isFileUploaded;
		}
		//SL-21142
	/**
     * Variable added for Document upload 
     */
	@XStreamAlias("File")
    File file;
	@XStreamAlias("CredentialDocumentBytes")
    protected byte[]  credentialDocumentBytes;
	@XStreamAlias("CredentialDocumentFileName")
	protected String credentialDocumentFileName;
	@XStreamAlias("CredentialDocumentExtention")
	protected String credentialDocumentExtention;
	@XStreamAlias("CredentialDocumentReference")
	protected String credentialDocumentReference;
	@XStreamAlias("CredentialDocumentFileSize")
	protected long credentialDocumentFileSize;
	@XStreamAlias("FileFormat")
	protected List fileFormat = new ArrayList();
	
	String action;
	
	
    
    public List getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(List fileFormat) {
		this.fileFormat = fileFormat;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public byte[] getCredentialDocumentBytes() {
		return credentialDocumentBytes;
	}

	public void setCredentialDocumentBytes(byte[] credentialDocumentBytes) {
		this.credentialDocumentBytes = credentialDocumentBytes;
	}

	public String getCredentialDocumentFileName() {
		return credentialDocumentFileName;
	}

	public void setCredentialDocumentFileName(String credentialDocumentFileName) {
		this.credentialDocumentFileName = credentialDocumentFileName;
	}

	public String getCredentialDocumentExtention() {
		return credentialDocumentExtention;
	}

	public void setCredentialDocumentExtention(String credentialDocumentExtention) {
		this.credentialDocumentExtention = credentialDocumentExtention;
	}

	public String getCredentialDocumentReference() {
		return credentialDocumentReference;
	}

	public void setCredentialDocumentReference(String credentialDocumentReference) {
		this.credentialDocumentReference = credentialDocumentReference;
	}

	public long getCredentialDocumentFileSize() {
		return credentialDocumentFileSize;
	}

	public void setCredentialDocumentFileSize(long credentialDocumentFileSize) {
		this.credentialDocumentFileSize = credentialDocumentFileSize;
	}

	/**
	 * @return the auditCredId
	 */
	public int getAuditCredId() {
		return auditCredId;
	}

	/**
	 * @param auditCredId the auditCredId to set
	 */
	public void setAuditCredId(int auditCredId) {
		this.auditCredId = auditCredId;
	}

	/**
	 * @return the auditorName
	 */
	public String getAuditorName() {
		return auditorName;
	}

	/**
	 * @param auditorName the auditorName to set
	 */
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	/**
	 * @return the auditReasonCodeId
	 */
	public int getAuditReasonCodeId() {
		return auditReasonCodeId;
	}

	/**
	 * @param auditReasonCodeId the auditReasonCodeId to set
	 */
	public void setAuditReasonCodeId(int auditReasonCodeId) {
		this.auditReasonCodeId = auditReasonCodeId;
	}

	/**
	 * @return the auditReasonCodesList
	 */
	public List getAuditReasonCodesList() {
		return auditReasonCodesList;
	}

	/**
	 * @param auditReasonCodesList the auditReasonCodesList to set
	 */
	public void setAuditReasonCodesList(List auditReasonCodesList) {
		this.auditReasonCodesList = auditReasonCodesList;
	}

	/**
	 * @return the disableFields
	 */
	public boolean isDisableFields() {
		return disableFields;
	}

	/**
	 * @param disableFields the disableFields to set
	 */
	public void setDisableFields(boolean disableFields) {
		this.disableFields = disableFields;
	}

	/**
	 * @return the hideFields
	 */
	public boolean isHideFields() {
		return hideFields;
	}

	/**
	 * @param hideFields the hideFields to set
	 */
	public void setHideFields(boolean hideFields) {
		this.hideFields = hideFields;
	}

	/**
	 * @return the wfStatusId
	 */
	public int getWfStatusId() {
		return wfStatusId;
	}

	/**
	 * @param wfStatusId the wfStatusId to set
	 */
	public void setWfStatusId(int wfStatusId) {
		this.wfStatusId = wfStatusId;
	}

	/**
	 * @return the wfStatusList
	 */
	public List getWfStatusList() {
		return wfStatusList;
	}

	/**
	 * @param wfStatusList the wfStatusList to set
	 */
	public void setWfStatusList(List wfStatusList) {
		this.wfStatusList = wfStatusList;
	}

	public int getCategoryId() {

        return categoryId;
    }

    public String getCity() {

        return city;
    }

    public String getCounty() {

        return county;
    }

    public List getCredentialCatList() {

        return credentialCatList;
    }

    public FormFile getCredentialDocument() {

        return credentialDocument;
    }

    public int getCredentialDocumentId() {

        return credentialDocumentId;
    }

    public String getCredentialNumber() {

        return credentialNumber;
    }

    public List getCredentialsList() {

        return credentialsList;
    }

    public List getCredentialTypeList() {

        return credentialTypeList;
    }

    public String getExpirationDate() {

        return expirationDate;
    }

    public String getIssueDate() {

        return issueDate;
    }

    public String getIssuerName() {

        return issuerName;
    }

    public String getLicenseName() {

        return licenseName;
    }

    public String getNo() {

        return no;
    }

    public String getNotes() {

        return notes;
    }

    public int getResourceCredId() {

        return resourceCredId;
    }

    public int getResourceId() {

        return resourceId;
    }

    public String getState() {

        return state;
    }

    public int getTypeId() {

        return typeId;
    }

    public boolean isPassCredentials() {

        return passCredentials;
    }

    public void setPassCredentials(boolean passCredentials) {
    
        this.passCredentials = passCredentials;
    }

    public void setResourceCredId(int resourceCredId) {
    
        this.resourceCredId = resourceCredId;
    }

    public void setResourceId(int resourceId) {
    
        this.resourceId = resourceId;
    }

    public void setState(String state) {
    
        this.state = state;
    }

    public void setTypeId(int typeId) {
    
        this.typeId = typeId;
    }

    public void setCategoryId(int categoryId) {
    
        this.categoryId = categoryId;
    }

    public void setCity(String city) {
    
        this.city = city;
    }

    public void setCounty(String county) {
    
        this.county = county;
    }

    public void setCredentialCatList(List credentialCatList) {
    
        this.credentialCatList = credentialCatList;
    }

    public void setCredentialDocument(FormFile credentialDocument) {
    
        this.credentialDocument = credentialDocument;
    }

    public void setCredentialDocumentId(int credentialDocumentId) {
    
        this.credentialDocumentId = credentialDocumentId;
    }

    public void setCredentialNumber(String credentialNumber) {
    
        this.credentialNumber = credentialNumber;
    }

    public void setCredentialsList(List credentialsList) {
    
        this.credentialsList = credentialsList;
    }

    public void setCredentialTypeList(List credentialTypeList) {
    
        this.credentialTypeList = credentialTypeList;
    }

    public void setExpirationDate(String expirationDate) {
    
        this.expirationDate = expirationDate;
    }

    public void setIssueDate(String issueDate) {
    
        this.issueDate = issueDate;
    }

    public void setIssuerName(String issuerName) {
    
        this.issuerName = issuerName;
    }

    public void setLicenseName(String licenseName) {
    
        this.licenseName = licenseName;
    }

    public void setNo(String no) {
    
        this.no = no;
    }

    public void setNotes(String notes) {
    
        this.notes = notes;
    }

    public String[] getReasonCodeIds() {
    
        return reasonCodeIds;
    }

    public void setReasonCodeIds(String[] reasonCodeIds) {
    
        this.reasonCodeIds = reasonCodeIds;
    }

	public int getSize() {
		if(credentialsList!=null&&credentialsList.size()>0){
			return credentialsList.size();
		}
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	

}
