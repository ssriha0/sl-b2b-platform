

package com.newco.marketplace.api.beans.hi.account.update.provider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("credential")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourceCredential {

	@XStreamAlias("resourceCredentialId")
    private String resourceCredentialId;
	
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



    public String getResourceCredentialId() {
		return resourceCredentialId;
	}

	public void setResourceCredentialId(String resourceCredentialId) {
		this.resourceCredentialId = resourceCredentialId;
	}

	public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String value) {
        this.credentialType = value;
    }


    public String getCredentialCategory() {
        return credentialCategory;
    }


    public void setCredentialCategory(String value) {
        this.credentialCategory = value;
    }


    public String getLicenseCertName() {
        return licenseCertName;
    }


    public void setLicenseCertName(String value) {
        this.licenseCertName = value;
    }


    public String getCredentialIssuer() {
        return credentialIssuer;
    }


    public void setCredentialIssuer(String value) {
        this.credentialIssuer = value;
    }

    

    public String getCredentialNumber() {
        return credentialNumber;
    }


    public void setCredentialNumber(String value) {
        this.credentialNumber = value;
    }


    public String getCredentialCity() {
        return credentialCity;
    }


    public void setCredentialCity(String value) {
        this.credentialCity = value;
    }


    public String getCredentialState() {
        return credentialState;
    }

 
    public void setCredentialState(String value) {
        this.credentialState = value;
    }


    public String getCredentialCounty() {
        return credentialCounty;
    }


    public void setCredentialCounty(String value) {
        this.credentialCounty = value;
    }


    public String getCredentialIssueDate() {
        return credentialIssueDate;
    }


    public void setCredentialIssueDate(String value) {
        this.credentialIssueDate = value;
    }


    public String getCredentialExpirationDate() {
        return credentialExpirationDate;
    }


    public void setCredentialExpirationDate(String value) {
        this.credentialExpirationDate = value;
    }

}
