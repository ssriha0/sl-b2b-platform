package com.newco.marketplace.api.beans.hi.account.create.provider;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("licenseAndCertifications")
public class LicenseAndCertifications {

	@XStreamAlias("licenseCertificationInd")
    private String licenseCertificationInd;
   
	@XStreamAlias("credentials")
    private Credentials credentials;

    public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String getLicenseCertificationInd() {
		return licenseCertificationInd;
	}

	public void setLicenseCertificationInd(String licenseCertificationInd) {
		this.licenseCertificationInd = licenseCertificationInd;
	}

    /**
     * Gets the value of the credentials property.
     * 
     * @return
     *     possible object is
     *     {@link CredentialsType }
     *     
     */
   

}
