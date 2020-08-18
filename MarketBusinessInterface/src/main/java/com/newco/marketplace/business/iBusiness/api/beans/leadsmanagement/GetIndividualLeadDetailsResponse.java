package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XmlRootElement(name = "getIndividualLeadDetailsResponse")
@XStreamAlias("getIndividualLeadDetailsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetIndividualLeadDetailsResponse {
	
	@XStreamAlias("results")
	private Results results;
	@XmlElement(name="leadDetails")
	@XStreamAlias("leadDetails")
	private IndividualLeadDetail individualLeadDetail;
	
	public IndividualLeadDetail getIndividualLeadDetail() {
		return individualLeadDetail;
	}
	public void setIndividualLeadDetail(IndividualLeadDetail individualLeadDetail) {
		this.individualLeadDetail = individualLeadDetail;
	}
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
}
