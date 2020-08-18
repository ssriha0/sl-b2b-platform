package com.newco.marketplace.api.beans.hi.account.create.provider;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("credential")
public class Credential {

    @XStreamAlias("credentialType")
    private String credentialType;
    
    @XStreamAlias("credentialCategory")
    private String credentialCategory;
 
    @XStreamAlias("licenseCertName")
    private String licenseCertName;
 
    @XStreamAlias("credentialIssuer")
    private String credentialIssuer;
    
    @XStreamAlias("credentialNumber")
    private String credentialNumber;
 
    @XStreamAlias("credentialCity")
    private String credentialCity;
 
    @XStreamAlias("credentialState")
    private String credentialState;
 
    @XStreamAlias("credentialCounty")
    private String credentialCounty;
 
    @XStreamAlias("credentialIssueDate")
    private String credentialIssueDate;
 
    @XStreamAlias("credentialExpirationDate")
    private String credentialExpirationDate;

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getCredentialCategory() {
		return credentialCategory;
	}

	public void setCredentialCategory(String credentialCategory) {
		this.credentialCategory = credentialCategory;
	}

	public String getLicenseCertName() {
		return licenseCertName;
	}

	public void setLicenseCertName(String licenseCertName) {
		this.licenseCertName = licenseCertName;
	}

	public String getCredentialIssuer() {
		return credentialIssuer;
	}

	public void setCredentialIssuer(String credentialIssuer) {
		this.credentialIssuer = credentialIssuer;
	}

	public String getCredentialNumber() {
		return credentialNumber;
	}

	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}

	public String getCredentialCity() {
		return credentialCity;
	}

	public void setCredentialCity(String credentialCity) {
		this.credentialCity = credentialCity;
	}

	public String getCredentialState() {
		return credentialState;
	}

	public void setCredentialState(String credentialState) {
		this.credentialState = credentialState;
	}

	public String getCredentialCounty() {
		return credentialCounty;
	}

	public void setCredentialCounty(String credentialCounty) {
		this.credentialCounty = credentialCounty;
	}

	public String getCredentialIssueDate() {
		return credentialIssueDate;
	}

	public void setCredentialIssueDate(String credentialIssueDate) {
		this.credentialIssueDate = credentialIssueDate;
	}

	public String getCredentialExpirationDate() {
		return credentialExpirationDate;
	}

	public void setCredentialExpirationDate(String credentialExpirationDate) {
		this.credentialExpirationDate = credentialExpirationDate;
	}

}
