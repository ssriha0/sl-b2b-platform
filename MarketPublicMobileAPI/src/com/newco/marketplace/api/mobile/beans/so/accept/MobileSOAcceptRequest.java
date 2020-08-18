package com.newco.marketplace.api.mobile.beans.so.accept;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the 
 * MobileSOAccept Request
 * 
 * @author Infosys
 * 
 */
@XSD(name = "mobileSOAcceptRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "mobileSOAcceptRequest")
@XStreamAlias("mobileSOAcceptRequest")
public class MobileSOAcceptRequest {
	
	@XStreamAlias("acceptedResource")   
	private Integer acceptedResource;
	
	@XStreamAlias("acceptByFirmInd")
	private Boolean acceptByFirmInd;
	
	@XStreamAlias("preferenceInd")
	private Integer preferenceInd;
    public Integer getAcceptedResource() {
		return acceptedResource;
	}

	public void setAcceptedResource(final Integer acceptedResource) {
		this.acceptedResource = acceptedResource;
	}

	public Boolean getAcceptByFirmInd() {
		return acceptByFirmInd;
	}

	public void setAcceptByFirmInd(final Boolean acceptByFirmInd) {
		this.acceptByFirmInd = acceptByFirmInd;
	}

	public Integer getPreferenceInd() {
		return preferenceInd;
	}

	public void setPreferenceInd(Integer preferenceInd) {
		this.preferenceInd = preferenceInd;
	}

}
