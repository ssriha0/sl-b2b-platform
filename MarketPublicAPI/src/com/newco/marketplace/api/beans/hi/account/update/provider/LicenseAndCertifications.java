

package com.newco.marketplace.api.beans.hi.account.update.provider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("licenseAndCertifications")
@XmlRootElement(name = "licenseAndCertifications")
@XmlAccessorType(XmlAccessType.FIELD)
public class LicenseAndCertifications {
	
	@XStreamAlias("credentials")
    private Credentials credentials;


    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials value) {
        this.credentials = value;
    }


}
