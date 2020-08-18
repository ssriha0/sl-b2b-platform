package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("credential")
@XmlAccessorType(XmlAccessType.FIELD)
public class Credential {

@XStreamAlias("credentialType")
private String credentialType;

@XStreamAlias("licenseCertName")
private String licenseCertName;

@XStreamAlias("credentialExpirationDate")
private String credentialExpirationDate;

@XStreamAlias("serviceLiveVerificationStatus")
private String serviceLiveVerificationStatus;

public String getCredentialType() {
	return credentialType;
}
public void setCredentialType(String credentialType) {
	this.credentialType = credentialType;
}
public String getLicenseCertName() {
	return licenseCertName;
}
public void setLicenseCertName(String licenseCertName) {
	this.licenseCertName = licenseCertName;
}
public String getCredentialExpirationDate() {
	return credentialExpirationDate;
}
public void setCredentialExpirationDate(String credentialExpirationDate) {
	this.credentialExpirationDate = credentialExpirationDate;
}
public String getServiceLiveVerificationStatus() {
	return serviceLiveVerificationStatus;
}
public void setServiceLiveVerificationStatus(
		String serviceLiveVerificationStatus) {
	this.serviceLiveVerificationStatus = serviceLiveVerificationStatus;
}

	
}
