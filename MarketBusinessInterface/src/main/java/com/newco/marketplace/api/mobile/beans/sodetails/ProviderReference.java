package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing Provider Reference  information.
 * @author Infosys
 *
 */

@XStreamAlias("providerReference")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderReference {
		
	@XStreamAlias("refName")
	private String refName;
	
	@XStreamAlias("refValue")
	private String refValue;
	
	@XStreamAlias("mandatoryInd")
	private String mandatoryInd;
	
	@XStreamAlias("scanEnabled")
	private String scanEnabled;


	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getRefValue() {
		return refValue;
	}

	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	public String getMandatoryInd() {
		return mandatoryInd;
	}

	public void setMandatoryInd(String mandatoryInd) {
		this.mandatoryInd = mandatoryInd;
	}

	public String getScanEnabled() {
		return scanEnabled;
	}

	public void setScanEnabled(String scanEnabled) {
		this.scanEnabled = scanEnabled;
	}


}
