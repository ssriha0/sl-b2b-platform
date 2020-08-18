package com.newco.marketplace.web.dto.provider;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import com.newco.marketplace.vo.provider.CredentialProfile;




public class InsurancePolicyDto extends BaseDto  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3833029619209204719L;
	private boolean adminClaimedWork = false;
    private String agencyCountry = "";
    private String agencyName = "";
    private String agencyState = "";
    private String amount = "";
    private String auditClaimedBy = "";
    private String auditorName = "";
    private int auditReasonCodeId = -1;
    private List auditReasonCodesList;
    private String carrierName = "";
    private String categoryId = "";
    private int catId;
    private String combinedKey = "";
    private List combinedKeyList;
    private int credentialCategoryId;
    private int credentialDocumentId;
    private String credentialDocumentUploadTime = "";
    private int credentialTypeId;
    private boolean disableFields = true;
    private String expirationDate = "";
    private boolean hideFields = true;
    private String insuranceType = "";
    private List insuranceTypeList;
    private String issueDate = "";
    private String key = "";
    private String policyExpirationDate;
    private String policyIssueDate;
	
	//SL-21233: Document Retrieval Code Starts
	
    private String policyCreatedDate;
	
	//SL-21233: Document Retrieval Code Ends
	
    private String policyNumber = "";
    private String roleType = "";
    private int TypeId;
    private int vendorCredentialId;
    private int wfStatusId;
    private List wfStatusList;
    String [] reasonCodeIds;
    private int lastUploadedDocumentId;
    private int updateDocumentInd;
    private String credentialCategoryDesc;
    private List additionalInsuranceList;
    private int insuranceListSize=0;
    private String marketplaceInd;
    private Date policyIssDate;
    private Date policyExpDate;
	
	
	//SL-21233: Document Retrieval Code Starts
	
    private Date policyCreDate;
    private String status = "";
	
	//SL-21233: Document Retrieval Code Ends
	
	
    private boolean displayOtherInsurance=false;
    
    /**
     * Variable added for Document upload 
     */
    File file;
    protected byte[]  credentialDocumentBytes;
	protected String credentialDocumentFileName;
	protected String credentialDocumentExtention;
	protected String credentialDocumentReference;
	protected long credentialDocumentFileSize;
    
    
    //new Fields
    String userId;
    int vendorId;
    CredentialProfile credentialProfile;
    private String category;
    private String buttonType;
    private boolean isProviderAdmin;
	private HashMap<Integer, String> mapCredentialType;
	//SL-20645 : adding new variable to hold the auditTimeLoggingId
	private String auditTimeLoggingIdNew;
	

    public String getAuditTimeLoggingIdNew() {
		return auditTimeLoggingIdNew;
	}

	public void setAuditTimeLoggingIdNew(String auditTimeLoggingIdNew) {
		this.auditTimeLoggingIdNew = auditTimeLoggingIdNew;
	}

	private static final Logger logger = Logger.getLogger(InsurancePolicyDto.class.getName());

    public boolean getAdminClaimedWork() {

        return adminClaimedWork;
    }

    public String getAgencyCountry() {

        return agencyCountry;
    }

    public String getAgencyName() {

        return agencyName;
    }

    public String getAgencyState() {

        return agencyState;
    }

    public String getAmount() {

        return amount;
    }

    public String getAuditClaimedBy() {

        return auditClaimedBy;
    }

    public String getAuditorName() {

        return auditorName;
    }

    public int getAuditReasonCodeId() {

        return auditReasonCodeId;
    }

    public List getAuditReasonCodesList() {

        return auditReasonCodesList;
    }

    public String getCarrierName() {

        return carrierName;
    }

    public String getCategoryId() {

        return categoryId;
    }

    public int getCatId() {

        return catId;
    }

    public String getCombinedKey() {

        return combinedKey;
    }

    public List getCombinedKeyList() {

        return combinedKeyList;
    }

    public int getCredentialCategoryId() {

        return credentialCategoryId;
    }

    public int getCredentialDocumentId() {

        return credentialDocumentId;
    }

    public String getCredentialDocumentUploadTime() {

        return credentialDocumentUploadTime;
    }

    public int getCredentialTypeId() {

        return credentialTypeId;
    }

    public String getExpirationDate() {

        return expirationDate;
    }

    public String getInsuranceType() {

        return insuranceType;
    }

    public List getInsuranceTypeList() {

        return insuranceTypeList;
    }

    public String getIssueDate() {

        return issueDate;
    }

    public String getKey() {

        return key;
    }    

    public String getPolicyNumber() {

        return policyNumber;
    }

    public String getRoleType() {

        return roleType;
    }

    public int getTypeId() {

        return TypeId;
    }

    public int getVendorCredentialId() {

        return vendorCredentialId;
    }

    public int getWfStatusId() {

        return wfStatusId;
    }

    public List getWfStatusList() {

        return wfStatusList;
    }

    public boolean isDisableFields() {

        return disableFields;
    }

    public boolean isHideFields() {

        return hideFields;
    }

    public void setAdminClaimedWork(boolean adminClaimedWork) {

        this.adminClaimedWork = adminClaimedWork;
    }

    public void setAgencyCountry(String agencyCountry) {

        this.agencyCountry = agencyCountry;
    }

    public void setAgencyName(String agencyName) {

        this.agencyName = agencyName;
    }

    public void setAgencyState(String agencyState) {

        this.agencyState = agencyState;
    }

    public void setAmount(String amount) {

        this.amount = amount;
    }

    public void setAuditClaimedBy(String auditClaimedBy) {

        this.auditClaimedBy = auditClaimedBy;
    }

    public void setAuditorName(String auditorName) {

        this.auditorName = auditorName;
    }

    public void setAuditReasonCodeId(int auditReasonCodeId) {

        this.auditReasonCodeId = auditReasonCodeId;
    }

    public void setAuditReasonCodesList(List auditReasonCodesList) {

        this.auditReasonCodesList = auditReasonCodesList;
    }

    public void setCarrierName(String carrierName) {

        this.carrierName = carrierName;
    }

    public void setCategoryId(String categoryId) {

        this.categoryId = categoryId;
    }

    public void setCatId(int catId) {

        this.catId = catId;
    }

    public void setCombinedKey(String combinedKey) {

        this.combinedKey = combinedKey;
    }

    public void setCombinedKeyList(List combinedKeyList) {

        this.combinedKeyList = combinedKeyList;
    }

    public void setCredentialCategoryId(int credentialCategoryId) {

        logger.info("credentialCategoryId Work value: ------- " + this.credentialCategoryId + "----" + credentialCategoryId);
        this.credentialCategoryId = credentialCategoryId;
    }

    public static Logger getLogger() {
    
        return logger;
    }

    public void setCredentialDocumentId(int credentialDocumentId) {
    
        this.credentialDocumentId = credentialDocumentId;
    }

    public void setCredentialDocumentUploadTime(String credentialDocumentUploadTime) {
    
        this.credentialDocumentUploadTime = credentialDocumentUploadTime;
    }

    public void setCredentialTypeId(int credentialTypeId) {
    
        this.credentialTypeId = credentialTypeId;
    }

    public void setDisableFields(boolean disableFields) {
    
        this.disableFields = disableFields;
    }

    public void setExpirationDate(String expirationDate) {
    
        this.expirationDate = expirationDate;
    }

    public void setHideFields(boolean hideFields) {
    
        this.hideFields = hideFields;
    }

    public void setInsuranceType(String insuranceType) {
    
        this.insuranceType = insuranceType;
    }

    public void setInsuranceTypeList(List insuranceTypeList) {
    
        this.insuranceTypeList = insuranceTypeList;
    }

    public void setIssueDate(String issueDate) {
    
        this.issueDate = issueDate;
    }

    public void setKey(String key) {
    
        this.key = key;
    }    

    public void setPolicyNumber(String policyNumber) {
    
        this.policyNumber = policyNumber;
    }

    public void setRoleType(String roleType) {
    
        this.roleType = roleType;
    }

    public void setTypeId(int typeId) {
    
        TypeId = typeId;
    }

    public void setVendorCredentialId(int vendorCredentialId) {
    
        this.vendorCredentialId = vendorCredentialId;
    }

    public void setWfStatusId(int wfStatusId) {
    
        this.wfStatusId = wfStatusId;
    }

    public void setWfStatusList(List wfStatusList) {
    
        this.wfStatusList = wfStatusList;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public CredentialProfile getCredentialProfile() {
		return credentialProfile;
	}

	public void setCredentialProfile(CredentialProfile credentialProfile) {
		this.credentialProfile = credentialProfile;
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
	
	public void setPolicyExpirationDate(String policyExpirationDate) {
		this.policyExpirationDate = policyExpirationDate;
	}

	public void setPolicyIssueDate(String policyIssueDate) {
		this.policyIssueDate = policyIssueDate;
	}

	public String getPolicyExpirationDate() {
		return policyExpirationDate;
	}

	public String getPolicyIssueDate() {
		return policyIssueDate;
	}

	public int getLastUploadedDocumentId() {
		return lastUploadedDocumentId;
	}

	public void setLastUploadedDocumentId(int lastUploadedDocumentId) {
		this.lastUploadedDocumentId = lastUploadedDocumentId;
	}

	public int getUpdateDocumentInd() {
		return updateDocumentInd;
	}

	public void setUpdateDocumentInd(int updateDocumentInd) {
		this.updateDocumentInd = updateDocumentInd;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public boolean isProviderAdmin() {
		return isProviderAdmin;
	}

	public void setProviderAdmin(boolean isProviderAdmin) {
		this.isProviderAdmin = isProviderAdmin;
	}
	public String getCredentialCategoryDesc() {
		return credentialCategoryDesc;
	}

	public void setCredentialCategoryDesc(String credentialCategoryDesc) {
		this.credentialCategoryDesc = credentialCategoryDesc;
	}
	public String getMarketplaceInd() {
		return marketplaceInd;
	}

	public void setMarketplaceInd(String marketplaceInd) {
		this.marketplaceInd = marketplaceInd;
	}

	public List getAdditionalInsuranceList() {
		return additionalInsuranceList;
	}

	public void setAdditionalInsuranceList(List additionalInsuranceList) {
		this.additionalInsuranceList = additionalInsuranceList;
	}

	public int getInsuranceListSize() {
		return insuranceListSize;
	}

	public void setInsuranceListSize(int insuranceListSize) {
		this.insuranceListSize = insuranceListSize;
	}

	public Date getPolicyIssDate() {
		return policyIssDate;
	}

	public void setPolicyIssDate(Date policyIssDate) {
		this.policyIssDate = policyIssDate;
	}

	public Date getPolicyExpDate() {
		return policyExpDate;
	}

	public void setPolicyExpDate(Date policyExpDate) {
		this.policyExpDate = policyExpDate;
	}

	public boolean isDisplayOtherInsurance() {
		return displayOtherInsurance;
	}

	public void setDisplayOtherInsurance(boolean displayOtherInsurance) {
		this.displayOtherInsurance = displayOtherInsurance;
	}

	public HashMap<Integer, String> getMapCredentialType() {
		return mapCredentialType;
	}

	public void setMapCredentialType(HashMap<Integer, String> mapCredentialType) {
		this.mapCredentialType = mapCredentialType;
	}

	//SL-21233: Document Retrieval
	public String getPolicyCreatedDate() {
		return policyCreatedDate;
	}

	public void setPolicyCreatedDate(String policyCreatedDate) {
		this.policyCreatedDate = policyCreatedDate;
	}

	public Date getPolicyCreDate() {
		return policyCreDate;
	}

	public void setPolicyCreDate(Date policyCreDate) {
		this.policyCreDate = policyCreDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	//SL-21233: Document Retrieval
	

	
	
	
}
