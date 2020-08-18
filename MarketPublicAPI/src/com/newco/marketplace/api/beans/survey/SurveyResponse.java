package com.newco.marketplace.api.beans.survey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

//SLT-1649
@XSD(name="surveyResponse.xsd", path="/resources/schemas/survey/")
@XStreamAlias("surveyResponse")
@XmlRootElement(name = "surveyResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyResponse implements IAPIResponse{
	
	@XmlElement(name="results")
	private Results results;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	@Override
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
		
	}
}
