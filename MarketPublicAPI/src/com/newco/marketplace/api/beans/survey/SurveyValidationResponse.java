package com.newco.marketplace.api.beans.survey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "SurveyValidationResponse.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "surveyValidation")
@XStreamAlias("surveyValidation")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyValidationResponse {
	String isSubmitted;

	public String isSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(String isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
}
