package com.newco.marketplace.vo.provider;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CredentialProfile  extends BaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1927890496128646182L;
	private int credentialId;
	private int credentialTypeId=-1;
	private String credentialCategoryIdName;
	private int vendorCredId;
    private int credentialCategoryId=-1;
	private String credentialSource;
	private String credentialName;
	private String credAmount;
	private Timestamp credentialIssueDate;
	private Timestamp credentialExpirationDate;
    private String credentialNumber;
	private String credentialCity;
	private String credentialCounty;
	private String credentialState;
	private int vendorId = -1;
	private int wfStateId=-1;
	private String credCategory;
	private String combinedId;
	private String CredentialTypeIdName;
	private int currentDocumentID;
	private Timestamp currentDocumentTS;
    private String auditClaimedBy = "";
    private List insuranceTypeList;
    private List stateList;
    private HashMap<Integer, String> mapCredentialType;
    private String credentialCategoryDesc;
    private String credentialTypeDesc;
    private String marketplaceInd;
	
	//SL-21233: Document Retrieval Code Starts
	
    private String status;
    private Timestamp createdDate;
	
	//SL-21233: Document Retrieval Code Ends
    
    /**
     * Variables for the Document Upload
     */
    protected byte[] credentialDocumentBytes;
	protected String credentialDocumentFileName;
	protected String credentialDocumentExtention;
	protected String credentialDocumentReference;
	protected long credentialDocumentFileSize;
	protected String roleID;
	
	private Date issueDate;
	private Date expirationDate;
	
	private List additionalInsuranceList;
	
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public long getCredentialDocumentFileSize() {
		return credentialDocumentFileSize;
	}
	public void setCredentialDocumentFileSize(long credentialDocumentFileSize) {
		this.credentialDocumentFileSize = credentialDocumentFileSize;
	}
	/**
	 * @return the auditClaimedBy
	 */
	public String getAuditClaimedBy() {
		return auditClaimedBy;
	}
	/**
	 * @param auditClaimedBy the auditClaimedBy to set
	 */
	public void setAuditClaimedBy(String auditClaimedBy) {
		this.auditClaimedBy = auditClaimedBy;
	}
	public String getCredentialTypeIdName() {
		return CredentialTypeIdName;
	}
	public void setCredentialTypeIdName(String credentialTypeIdName) {
		CredentialTypeIdName = credentialTypeIdName;
	}
	public String getCredCategory()
		{
			return credCategory;
		}
		public void setCredCategory(String credCategory) {
			this.credCategory = credCategory;
		}
		/**
		 * @return the credentialCategoryId
		 */
		public int getCredentialCategoryId() {
			return credentialCategoryId;
		}
		/**
		 * @param credentialCategoryId the credentialCategoryId to set
		 */
		public void setCredentialCategoryId(int credentialCategoryId) {
			this.credentialCategoryId = credentialCategoryId;
		}
		/**
		 * @return the credentialCategoryIdName
		 */
		public String getCredentialCategoryIdName() {
			return credentialCategoryIdName;
		}
		/**
		 * @param credentialCategoryIdName the credentialCategoryIdName to set
		 */
		public void setCredentialCategoryIdName(String credentialCategoryIdName) {
			this.credentialCategoryIdName = credentialCategoryIdName;
		}
		/**
		 * @return the credentialCity
		 */
		public String getCredentialCity() {
			return credentialCity;
		}
		/**
		 * @param credentialCity the credentialCity to set
		 */
		public void setCredentialCity(String credentialCity) {
			this.credentialCity = credentialCity;
		}
		/**
		 * @return the credentialCounty
		 */
		public String getCredentialCounty() {
			return credentialCounty;
		}
		/**
		 * @param credentialCounty the credentialCounty to set
		 */
		public void setCredentialCounty(String credentialCounty) {
			this.credentialCounty = credentialCounty;
		}
		/**
		 * @return the credentialExpirationDate
		 */
		public Timestamp getCredentialExpirationDate() {
			return credentialExpirationDate;
		}
		/**
		 * @param credentialExpirationDate the credentialExpirationDate to set
		 */
		
		public Timestamp getCredentialIssueDate() {
			return credentialIssueDate;
		}
		/**
		 * @param credentialIssueDate the credentialIssueDate to set
		 */
		
		public String getCredentialName() {
			return credentialName;
		}
		/**
		 * @param credentialName the credentialName to set
		 */
		public void setCredentialName(String credentialName) {
			this.credentialName = credentialName;
		}
		/**
		 * @return the credentialNumber
		 */
		public String getCredentialNumber() {
			return credentialNumber;
		}
		/**
		 * @param credentialNumber the credentialNumber to set
		 */
		public void setCredentialNumber(String credentialNumber) {
			this.credentialNumber = credentialNumber;
		}
		/**
		 * @return the credentialSource
		 */
		public String getCredentialSource() {
			return credentialSource;
		}
		/**
		 * @param credentialSource the credentialSource to set
		 */
		public void setCredentialSource(String credentialSource) {
			this.credentialSource = credentialSource;
		}
		/**
		 * @return the credentialState
		 */
		public String getCredentialState() {
			return credentialState;
		}
		/**
		 * @param credentialState the credentialState to set
		 */
		public void setCredentialState(String credentialState) {
			this.credentialState = credentialState;
		}
		/**
		 * @return the credentialTypeId
		 */
		public int getCredentialTypeId() {
			return credentialTypeId;
		}
		/**
		 * @param credentialTypeId the credentialTypeId to set
		 */
		public void setCredentialTypeId(int credentialTypeId) {
			this.credentialTypeId = credentialTypeId;
		}
		/**
		 * @return the vendorId
		 */
		public int getVendorId() {
			return vendorId;
		}
		/**
		 * @param vendorId the vendorId to set
		 */
		public void setVendorId(int vendorId) {
			this.vendorId = vendorId;
		}
		public int getVendorCredId() {
			return vendorCredId;
		}
		public void setVendorCredId(int vendorCredId) {
			this.vendorCredId = vendorCredId;
		}
		public String getCombinedId() {
			return credentialTypeId+"-"+credentialCategoryId;
		}
		public void setCombinedId(String combinedId) {
			this.combinedId = combinedId;
		}
		public void setCredentialExpirationDate(Timestamp credentialExpirationDate) {
			this.credentialExpirationDate = credentialExpirationDate;
		}
		public void setCredentialIssueDate(Timestamp credentialIssueDate) {
			this.credentialIssueDate = credentialIssueDate;
		}
		public int getCredentialId() {
			return credentialId;
		}
		public void setCredentialId(int credentialId) {
			this.credentialId = credentialId;
		}
		public int getCurrentDocumentID() {
			return currentDocumentID;
		}
		public void setCurrentDocumentID(int currentDocumentID) {
			this.currentDocumentID = currentDocumentID;
		}
		public Timestamp getCurrentDocumentTS() {
			return currentDocumentTS;
		}
		public void setCurrentDocumentTS(Timestamp currentDocumentTS) {
			this.currentDocumentTS = currentDocumentTS;
		}
		/**
		 * @return the credAmount
		 */
		public String getCredAmount() {
			return credAmount;
		}
		/**
		 * @param credAmount the credAmount to set
		 */
		public void setCredAmount(String credAmount) {
			this.credAmount = credAmount;
		}
		public List getInsuranceTypeList() {
			return insuranceTypeList;
		}
		public List getStateList() {
			return stateList;
		}
		public void setInsuranceTypeList(List insuranceTypeList) {
			this.insuranceTypeList = insuranceTypeList;
		}
		public void setStateList(List stateList) {
			this.stateList = stateList;
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
		public int getWfStateId() {
			return wfStateId;
		}
		public void setWfStateId(int wfStateId) {
			this.wfStateId = wfStateId;
		}
		public String getCredentialCategoryDesc() {
			return credentialCategoryDesc;
		}
		public void setCredentialCategoryDesc(String credentialCategoryDesc) {
			this.credentialCategoryDesc = credentialCategoryDesc;
		}
		public String getCredentialTypeDesc() {
			return credentialTypeDesc;
		}
		public void setCredentialTypeDesc(String credentialTypeDesc) {
			this.credentialTypeDesc = credentialTypeDesc;
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
		public Date getIssueDate() {
			return issueDate;
		}
		public void setIssueDate(Date issueDate) {
			this.issueDate = issueDate;
		}
		public Date getExpirationDate() {
			return expirationDate;
		}
		public void setExpirationDate(Date expirationDate) {
			this.expirationDate = expirationDate;
		}
		public HashMap<Integer, String> getMapCredentialType() {
			return mapCredentialType;
		}
		public void setMapCredentialType(HashMap<Integer, String> mapCredentialType) {
			this.mapCredentialType = mapCredentialType;
		}
		
		//SL-21233: Document Retrieval Code Starts
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Timestamp getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Timestamp createdDate) {
			this.createdDate = createdDate;
		}
		
		//SL-21233: Document Retrieval Code Ends
		
		
 
}
