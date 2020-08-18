package com.newco.marketplace.vo.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sears.os.vo.SerializableBaseVO;

public class LicensesAndCertVO extends SerializableBaseVO{

	/**
	 *
	 */
	private static final long serialVersionUID = 286305461900739947L;
	int vendorCredId;
	int credentialTypeId;
	String credTypeDesc=null;
	int vendorId;
	int categoryId;
	String licenseName = null;
	String issuerOfCredential=null;
	String credentialNum=null;
	String city=null;
	String stateId=null;
	String updateStateId=null;
	String county=null;
//	String issueDate =null;
//	String expirationDate =null;
	 private Date issueDate;
	 private Date expirationDate;
	HashMap mapCategory;
	HashMap mapCredentialType;
	List mapState = new ArrayList();
	Map dataList;
	List credentials;
	private String marketPlaceInd;
	private int addCredentialToFile = 0;
	private String resourceName;
	private long resourceId;
	private String source;
	private int wfStateId;
	private int credSize;
	private boolean isVerified = false;
	private Date insModifiedDate;

	private int currentDocumentID;
	String categoryTypeDesc=null;
	//SL-21142
	private Boolean isFileUploaded;

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
    protected byte[]  credentialDocumentBytes;
	protected String credentialDocumentFileName;
	protected String credentialDocumentExtention;
	protected String credentialDocumentReference;
	protected long credentialDocumentFileSize;
	Double amount =null;
	
	String wfStatus =null;
	private String firmId;
	public int getCurrentDocumentID() {
		return currentDocumentID;
	}
	public void setCurrentDocumentID(int currentDocumentID) {
		this.currentDocumentID = currentDocumentID;
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
	public int getAddCredentialToFile() {
		return addCredentialToFile;
	}
	public void setAddCredentialToFile(int addCredentialToFile) {
		this.addCredentialToFile = addCredentialToFile;
	}
	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the credTypeDesc
	 */
	public String getCredTypeDesc() {
		if(isNull(credTypeDesc))
			credTypeDesc= "";
		return credTypeDesc;
	}
	/**
	 * @param credTypeDesc the credTypeDesc to set
	 */
	public void setCredTypeDesc(String credTypeDesc) {
		this.credTypeDesc = credTypeDesc;
	}
	/**
	 * @return the licenseName
	 */
	public String getLicenseName() {
		if(isNull(licenseName))
			licenseName= "";
		return licenseName;
	}
	/**
	 * @param licenseName the licenseName to set
	 */
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}
	/**
	 * @return the issuerOfCredential
	 */
	public String getIssuerOfCredential() {
		if(isNull(issuerOfCredential))
			issuerOfCredential= "";
		return issuerOfCredential;
	}
	/**
	 * @param issuerOfCredential the issuerOfCredential to set
	 */
	public void setIssuerOfCredential(String issuerOfCredential) {
		this.issuerOfCredential = issuerOfCredential;
	}
	/**
	 * @return the credentialNum
	 */
	public String getCredentialNum() {
		if(isNull(credentialNum))
			credentialNum= "";
		return credentialNum;
	}
	/**
	 * @param credentialNum the credentialNum to set
	 */
	public void setCredentialNum(String credentialNum) {
		this.credentialNum = credentialNum;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		if(isNull(city))
			city= "";
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the stateId
	 */
	public String getStateId() {
		if(isNull(stateId))
			stateId= "";
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return the county
	 */
	public String getCounty() {
		if(isNull(county))
			county= "";
		return county;
	}
	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}
	/**
	 * @return the mapCredentialType
	 */
	public HashMap getMapCredentialType() {
		return mapCredentialType;
	}
	/**
	 * @param mapCredentialType the mapCredentialType to set
	 */
	public void setMapCredentialType(HashMap mapCredentialType) {
		this.mapCredentialType = mapCredentialType;
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
	/**
	 * @return the mapCategory
	 */
	public HashMap getMapCategory() {
		return mapCategory;
	}
	/**
	 * @param mapCategory the mapCategory to set
	 */
	public void setMapCategory(HashMap mapCategory) {
		this.mapCategory = mapCategory;
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
	 * @return the vendorCredId
	 */
	public int getVendorCredId() {
		return vendorCredId;
	}
	/**
	 * @param vendorCredId the vendorCredId to set
	 */
	public void setVendorCredId(int vendorCredId) {
		this.vendorCredId = vendorCredId;
	}

	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	/**
	 * @return the issueDate
	 */
	public Date getIssueDate() {
		return issueDate;
	}
	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}
	public Map getDataList() {
		return dataList;
	}
	public void setDataList(Map dataList) {
		this.dataList = dataList;
	}
	public List getCredentials() {
		return credentials;
	}
	public void setCredentials(List credentials) {
		this.credentials = credentials;
	}
	/**
	 * @param value
	 * @return
	 */
	private boolean isNull(String value) {
		return (value == null);
	}
	public String getMarketPlaceInd() {
		return marketPlaceInd;
	}
	public void setMarketPlaceInd(String marketPlaceInd) {
		this.marketPlaceInd = marketPlaceInd;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public long getResourceId() {
		return resourceId;
	}
	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}
	public List getMapState() {
		return mapState;
	}
	public void setMapState(List mapState) {
		this.mapState = mapState;
	}
	public int getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(int wfStateId) {
		this.wfStateId = wfStateId;
	}
	public int getCredSize() {
		return credSize;
	}
	public void setCredSize(int credSize) {
		this.credSize = credSize;
	}
	/**
	 * @return the isVerified
	 */
	public boolean isVerified() {
		return isVerified;
	}
	/**
	 * @param isVerified the isVerified to set
	 */
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	public Date getInsModifiedDate() {
		return insModifiedDate;
	}
	public void setInsModifiedDate(Date insModifiedDate) {
		this.insModifiedDate = insModifiedDate;
	}
	public String getCategoryTypeDesc() {
		return categoryTypeDesc;
	}
	public void setCategoryTypeDesc(String categoryTypeDesc) {
		this.categoryTypeDesc = categoryTypeDesc;
	}
	public String getUpdateStateId() {
		return updateStateId;
	}
	public void setUpdateStateId(String updateStateId) {
		this.updateStateId = updateStateId;
	}	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getWfStatus() {
		return wfStatus;
	}
	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}	
	
	
	
}
