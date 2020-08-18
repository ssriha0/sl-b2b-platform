

package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("licenseAndCertifications")
@XmlRootElement(name = "businessAddress")
@XmlAccessorType(XmlAccessType.FIELD)
public class LicenseAndCertifications {

	
	
	@XStreamAlias("licenseCertificationInd")
    private Integer licenseCertificationInd;
	
	@XStreamAlias("credentials")
    private Credentials credentials;


    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials value) {
        this.credentials = value;
    }

	public Integer getLicenseCertificationInd() {
		return licenseCertificationInd;
	}

	public void setLicenseCertificationInd(Integer licenseCertificationInd) {
		this.licenseCertificationInd = licenseCertificationInd;
	}
    
    

}
