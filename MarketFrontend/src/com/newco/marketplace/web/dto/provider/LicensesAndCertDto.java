package com.newco.marketplace.web.dto.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.web.dto.SerializedBaseDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("LicensesAndCert")
public class LicensesAndCertDto extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5047814864795346219L;
	@XStreamAlias("VendorCredId")
	int vendorCredId;
	@XStreamAlias("CredentialTypeId")
	int credentialTypeId;
	@XStreamAlias("CredTypeDesc")
	String credTypeDesc;
	@XStreamAlias("VendorId")
	int vendorId;
	@XStreamAlias("CategoryId")
	int categoryId;
	@XStreamAlias("LicenseName")
	String licenseName;
	@XStreamAlias("IssuerOfCredential")
	String issuerOfCredential;
	@XStreamAlias("CredentialNum")
	String credentialNum;
	@XStreamAlias("City")
	String city;
	@XStreamAlias("StateId")
	String stateId;
	@XStreamAlias("County")
	String county;
	@XStreamAlias("IssueDate")
	String issueDate ;
	@XStreamAlias("ExpirationDate")
	String expirationDate ;
	// private Date issueDate;
	 //private Date expirationDate;
	@XStreamOmitField
	HashMap mapCredentialType;
	@XStreamOmitField
	HashMap mapCategory;
	@XStreamOmitField
	List mapState = new ArrayList();
	@XStreamOmitField
	Map dataList;
	@XStreamOmitField
	List credentials;
	
	@XStreamAlias("ResourceName")
	private String resourceName;
	@XStreamAlias("ResourceId")
	private long resourceId;
	@XStreamAlias("CredentialDocumentId")
	private int credentialDocumentId;
	 
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
	
	//SL-21142
		@XStreamAlias("isFileUploaded")
		private Boolean isFileUploaded;

		public Boolean getIsFileUploaded() {
			return isFileUploaded;
		}

		public void setIsFileUploaded(Boolean isFileUploaded) {
			this.isFileUploaded = isFileUploaded;
		}
		//SL-21142
	//added by chandru to get the Size
	@XStreamOmitField
	private int size;
	@XStreamOmitField
	private int addCredentialToFile;
	//SL-20645 : adding new variable to hold the auditTimeLoggingId
	@XStreamAlias("AuditTimeLoggingIdNew")
    private String auditTimeLoggingIdNew;
	
	public String getAuditTimeLoggingIdNew() {
		return auditTimeLoggingIdNew;
	}
	public void setAuditTimeLoggingIdNew(String auditTimeLoggingIdNew) {
		this.auditTimeLoggingIdNew = auditTimeLoggingIdNew;
	}
	private int credSize;
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

	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	/**
	 * @return the issueDate
	 */
	public String getIssueDate() {
		return issueDate;
	}
	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
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
	public int getSize() {
		if(credentials!=null){
			size=credentials.size();
		}
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getAddCredentialToFile() {
		return addCredentialToFile;
	}
	public void setAddCredentialToFile(int addCredentialToFile) {
		this.addCredentialToFile = addCredentialToFile;
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
	public int getCredentialDocumentId() {
		return credentialDocumentId;
	}
	public void setCredentialDocumentId(int credentialDocumentId) {
		this.credentialDocumentId = credentialDocumentId;
	}
	public List getMapState() {
		return mapState;
	}
	public void setMapState(List mapState) {
		this.mapState = mapState;
	}
	public int getCredSize() {
		return credSize;
	}
	public void setCredSize(int credSize) {
		this.credSize = credSize;
	}

}
