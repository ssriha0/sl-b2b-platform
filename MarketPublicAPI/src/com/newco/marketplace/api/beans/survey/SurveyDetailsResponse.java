package com.newco.marketplace.api.beans.survey;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XSD(name="surveyDetailsResponse.xsd", path="/resources/schemas/survey/")
@XmlRootElement(name = "surveyDetailsResponse")
@XStreamAlias("surveyDetailsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyDetailsResponse{


}
