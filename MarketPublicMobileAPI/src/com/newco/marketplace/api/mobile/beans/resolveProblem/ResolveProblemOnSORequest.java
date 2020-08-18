package com.newco.marketplace.api.mobile.beans.resolveProblem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "resolveProblemOnSORequest.xsd", path = "/resources/schemas/mobile/v3_1/")
@XmlRootElement(name = "resolveProblemOnSORequest")
@XStreamAlias("resolveProblemOnSORequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResolveProblemOnSORequest {

	@XStreamAlias("resolutionComments")
	private String resolutionComments;
	
	public String getResolutionComments() {
		return resolutionComments;
	}
	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}
}
