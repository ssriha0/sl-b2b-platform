package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("licenseAndCertificationsFile")
@XmlAccessorType(XmlAccessType.FIELD)
public class LicenseAndCertificationsFile {

	@XStreamAlias("licenseCertificationInd")
	private Boolean licenseCertificationInd;

	@XStreamAlias("credentials")
	private Credentials credentials;
	
	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Boolean getLicenseCertificationInd() {
		return licenseCertificationInd;
	}

	public void setLicenseCertificationInd(Boolean licenseCertificationInd) {
		this.licenseCertificationInd = licenseCertificationInd;
	}

	
}
