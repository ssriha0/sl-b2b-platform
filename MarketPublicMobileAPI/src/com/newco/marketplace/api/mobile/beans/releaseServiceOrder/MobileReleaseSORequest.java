package com.newco.marketplace.api.mobile.beans.releaseServiceOrder;

/**
 * Class to hold the request variables for the Release SO API
 * 
 * 
 * @author Infosys
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "mobileReleaseSORequest.xsd", path = "/resources/schemas/mobile/v3_1/")
@XmlRootElement(name = "mobileReleaseSORequest")
@XStreamAlias("mobileReleaseSORequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileReleaseSORequest {
	
	@XStreamAlias("reason")
	private String reason;
	
	@XStreamAlias("comments")
	private String comments;	
	
	@XStreamAlias("releaseByFirmInd")
	private Boolean releaseByFirmInd;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getReleaseByFirmInd() {
		return releaseByFirmInd;
	}

	public void setReleaseByFirmInd(Boolean releaseByFirmInd) {
		this.releaseByFirmInd = releaseByFirmInd;
	}

	

	
	
}
