package com.newco.marketplace.web.dto.provider;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.vo.provider.CredentialProfile;

/*Changes related to SL-20301: Details based on LastUploadedDocument*/
public class AdditionalInsurancePolicyDTO implements Serializable {

	 	private String agencyCountry = "";
	    private String agencyName = "";
	    private String agencyState = "";
	    private String carrierName = "";
		private String policyExpirationDate;
	    private String policyIssueDate;

	    private boolean adminClaimedWork = false;
	    private String amount = "";
	    private String auditClaimedBy = "";
	    private String auditorName = "";
	    private int auditReasonCodeId = -1;
	    private List auditReasonCodesList;
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
		public String getAgencyCountry() {
			return agencyCountry;
		}
		public void setAgencyCountry(String agencyCountry) {
			this.agencyCountry = agencyCountry;
		}
		public String getAgencyName() {
			return agencyName;
		}
		public void setAgencyName(String agencyName) {
			this.agencyName = agencyName;
		}
		public String getAgencyState() {
			return agencyState;
		}
		public void setAgencyState(String agencyState) {
			this.agencyState = agencyState;
		}
		public String getCarrierName() {
			return carrierName;
		}
		public void setCarrierName(String carrierName) {
			this.carrierName = carrierName;
		}
		public String getPolicyExpirationDate() {
			return policyExpirationDate;
		}
		public void setPolicyExpirationDate(String policyExpirationDate) {
			this.policyExpirationDate = policyExpirationDate;
		}
		public String getPolicyIssueDate() {
			return policyIssueDate;
		}
		public void setPolicyIssueDate(String policyIssueDate) {
			this.policyIssueDate = policyIssueDate;
		}
		public boolean isAdminClaimedWork() {
			return adminClaimedWork;
		}
		public void setAdminClaimedWork(boolean adminClaimedWork) {
			this.adminClaimedWork = adminClaimedWork;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getAuditClaimedBy() {
			return auditClaimedBy;
		}
		public void setAuditClaimedBy(String auditClaimedBy) {
			this.auditClaimedBy = auditClaimedBy;
		}
		public String getAuditorName() {
			return auditorName;
		}
		public void setAuditorName(String auditorName) {
			this.auditorName = auditorName;
		}
		public int getAuditReasonCodeId() {
			return auditReasonCodeId;
		}
		public void setAuditReasonCodeId(int auditReasonCodeId) {
			this.auditReasonCodeId = auditReasonCodeId;
		}
		public List getAuditReasonCodesList() {
			return auditReasonCodesList;
		}
		public void setAuditReasonCodesList(List auditReasonCodesList) {
			this.auditReasonCodesList = auditReasonCodesList;
		}
		public String getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}
		public int getCatId() {
			return catId;
		}
		public void setCatId(int catId) {
			this.catId = catId;
		}
		public String getCombinedKey() {
			return combinedKey;
		}
		public void setCombinedKey(String combinedKey) {
			this.combinedKey = combinedKey;
		}
		public List getCombinedKeyList() {
			return combinedKeyList;
		}
		public void setCombinedKeyList(List combinedKeyList) {
			this.combinedKeyList = combinedKeyList;
		}
		public int getCredentialCategoryId() {
			return credentialCategoryId;
		}
		public void setCredentialCategoryId(int credentialCategoryId) {
			this.credentialCategoryId = credentialCategoryId;
		}
		public int getCredentialDocumentId() {
			return credentialDocumentId;
		}
		public void setCredentialDocumentId(int credentialDocumentId) {
			this.credentialDocumentId = credentialDocumentId;
		}
		public String getCredentialDocumentUploadTime() {
			return credentialDocumentUploadTime;
		}
		public void setCredentialDocumentUploadTime(String credentialDocumentUploadTime) {
			this.credentialDocumentUploadTime = credentialDocumentUploadTime;
		}
		public int getCredentialTypeId() {
			return credentialTypeId;
		}
		public void setCredentialTypeId(int credentialTypeId) {
			this.credentialTypeId = credentialTypeId;
		}
		public boolean isDisableFields() {
			return disableFields;
		}
		public void setDisableFields(boolean disableFields) {
			this.disableFields = disableFields;
		}
		public String getExpirationDate() {
			return expirationDate;
		}
		public void setExpirationDate(String expirationDate) {
			this.expirationDate = expirationDate;
		}
		public boolean isHideFields() {
			return hideFields;
		}
		public void setHideFields(boolean hideFields) {
			this.hideFields = hideFields;
		}
		public String getInsuranceType() {
			return insuranceType;
		}
		public void setInsuranceType(String insuranceType) {
			this.insuranceType = insuranceType;
		}
		public List getInsuranceTypeList() {
			return insuranceTypeList;
		}
		public void setInsuranceTypeList(List insuranceTypeList) {
			this.insuranceTypeList = insuranceTypeList;
		}
		public String getIssueDate() {
			return issueDate;
		}
		public void setIssueDate(String issueDate) {
			this.issueDate = issueDate;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getPolicyNumber() {
			return policyNumber;
		}
		public void setPolicyNumber(String policyNumber) {
			this.policyNumber = policyNumber;
		}
		public String getRoleType() {
			return roleType;
		}
		public void setRoleType(String roleType) {
			this.roleType = roleType;
		}
		public int getTypeId() {
			return TypeId;
		}
		public void setTypeId(int typeId) {
			TypeId = typeId;
		}
		public int getVendorCredentialId() {
			return vendorCredentialId;
		}
		public void setVendorCredentialId(int vendorCredentialId) {
			this.vendorCredentialId = vendorCredentialId;
		}
		public int getWfStatusId() {
			return wfStatusId;
		}
		public void setWfStatusId(int wfStatusId) {
			this.wfStatusId = wfStatusId;
		}
		public List getWfStatusList() {
			return wfStatusList;
		}
		public void setWfStatusList(List wfStatusList) {
			this.wfStatusList = wfStatusList;
		}
		public String[] getReasonCodeIds() {
			return reasonCodeIds;
		}
		public void setReasonCodeIds(String[] reasonCodeIds) {
			this.reasonCodeIds = reasonCodeIds;
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
		public String getCredentialCategoryDesc() {
			return credentialCategoryDesc;
		}
		public void setCredentialCategoryDesc(String credentialCategoryDesc) {
			this.credentialCategoryDesc = credentialCategoryDesc;
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
		public String getMarketplaceInd() {
			return marketplaceInd;
		}
		public void setMarketplaceInd(String marketplaceInd) {
			this.marketplaceInd = marketplaceInd;
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
		public HashMap<Integer, String> getMapCredentialType() {
			return mapCredentialType;
		}
		public void setMapCredentialType(HashMap<Integer, String> mapCredentialType) {
			this.mapCredentialType = mapCredentialType;
		}
		
}