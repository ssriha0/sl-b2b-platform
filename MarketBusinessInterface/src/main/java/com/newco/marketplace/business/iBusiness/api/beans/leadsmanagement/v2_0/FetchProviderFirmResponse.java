package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XSD(name="providerFirmsResponse.xsd", path="/resources/schemas/b2c/")
@XmlRootElement(name = "MatchProviderResponse")
@XStreamAlias("MatchProviderResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class FetchProviderFirmResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("LeadId")
	@XmlElement(name="LeadId")
	private String leadId;

	@XStreamOmitField
	private String slLeadId;
	
	@XStreamAlias("FirmDetailList")
	@XmlElement(name="FirmDetailList")
	private ProviderFirms firmDetailsList;

	public String getSlLeadId() {
		return slLeadId;
	}

	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}

	public ProviderFirms getFirmDetailsList() {
		return firmDetailsList;
	}

	public void setFirmDetailsList(ProviderFirms firmDetailsList) {
		this.firmDetailsList = firmDetailsList;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}


	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public void setVersion(String version) {
	}

	public void setSchemaLocation(String schemaLocation) {
	}

	public void setNamespace(String namespace) {
	}

	public void setSchemaInstance(String schemaInstance) {
	}
}
