package com.newco.marketplace.vo.provider;

public class CompanyCredentialsProfile extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3348674748810255475L;
	private String credentialTypeName;
	private String credentialCategoryName;
	private String source;
	private String name;
	private java.sql.Date credentialExpirationDate;
	private int vendorCredentialId; // key of the vendor_cred table
	private int vendorId;
	/**
	 * @return the vendorCredId
	 */
	public int getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorCredId the vendorCredId to set
	 */
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	/**
	 * @return the vendorCredentialId
	 */
	public int getVendorCredentialId() {
		return vendorCredentialId;
	}
	/**
	 * @param vendorCredentialId the vendorCredentialId to set
	 */
	public void setVendorCredentialId(int vendorCredentialId) {
		this.vendorCredentialId = vendorCredentialId;
	}
	/**
	 * @return the credentialCategoryName
	 */
	public String getCredentialCategoryName() {
		return credentialCategoryName;
	}
	/**
	 * @param credentialCategoryName the credentialCategoryName to set
	 */
	public void setCredentialCategoryName(String credentialCategoryName) {
		this.credentialCategoryName = credentialCategoryName;
	}
	/**
	 * @return the credentialTypeName
	 */
	public String getCredentialTypeName() {
		return credentialTypeName;
	}
	/**
	 * @param credentialTypeName the credentialTypeName to set
	 */
	public void setCredentialTypeName(String credentialTypeName) {
		this.credentialTypeName = credentialTypeName;
	}
	/**
	 * @return the credentialExpirationDate
	 */
	public java.sql.Date getCredentialExpirationDate() {
		return credentialExpirationDate;
	}
	/**
	 * @param credentialExpirationDate the credentialExpirationDate to set
	 */
	public void setCredentialExpirationDate(java.sql.Date credentialExpirationDate) {
		this.credentialExpirationDate = credentialExpirationDate;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
}
