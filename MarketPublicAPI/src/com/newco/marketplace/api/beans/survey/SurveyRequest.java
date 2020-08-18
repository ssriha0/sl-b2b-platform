package com.newco.marketplace.api.beans.survey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.so.Identification;
import com.newco.marketplace.api.common.IAPIRequest;
import com.newco.marketplace.dto.vo.survey.CSATSurvey;
import com.newco.marketplace.dto.vo.survey.NPSSurvey;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

//SLT-1649
// xsd and path should be added
@XSD(name = "surveyRequest.xsd", path = "/resources/schemas/survey/")
@XmlRootElement(name = "surveyRequest")
@XStreamAlias("surveyRequest")
@Namespace("http://www.servicelive.com/namespaces/surveyRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyRequest implements IAPIRequest{
	
	@XStreamAlias("identification")
	private Identification identification;
	
	@XStreamAlias("version")
	@XStreamAsAttribute()
	private String version;
 
	@XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation;

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance;
	
	@XStreamAlias("key")
	String key;
	
	@XStreamAlias("CSAT")
	CSATSurvey csat;
	
	@XStreamAlias("NPS")
	NPSSurvey nps;
	
	@XStreamAlias("agreed")
	boolean agreed;
	
	@XStreamAlias("completed")
	boolean submit;
	
	
	
	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSchemaInstance() {
		return schemaInstance;
	}

	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public CSATSurvey getCsat() {
		return csat;
	}

	public void setCsat(CSATSurvey csat) {
		this.csat = csat;
	}

	public NPSSurvey getNps() {
		return nps;
	}

	public void setNps(NPSSurvey nps) {
		this.nps = nps;
	}

	public boolean isAgreed() {
		return agreed;
	}

	public void setAgreed(boolean agreed) {
		this.agreed = agreed;
	}

	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
	
}
