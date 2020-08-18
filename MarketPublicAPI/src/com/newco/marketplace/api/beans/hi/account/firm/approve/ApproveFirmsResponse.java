package com.newco.marketplace.api.beans.hi.account.firm.approve;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ApprovedFirms;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.InvalidFirms;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="approveFirmsResponse.xsd", path="/resources/schemas/ums/")
@XmlRootElement(name = "approveFirmsResponse")
@XStreamAlias("approveFirmsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApproveFirmsResponse implements IAPIResponse {
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("approvedFirms")
	private ApprovedFirms approvedFirms;
	
	@XStreamAlias("invalidFirms")
	private InvalidFirms invalidFirms;
	
	public Results getResults() {
		return results;
	}
    public void setResults(Results results) {
		this.results = results;
	}
	public ApprovedFirms getApprovedFirms() {
		return approvedFirms;
	}
	public void setApprovedFirms(ApprovedFirms approvedFirms) {
		this.approvedFirms = approvedFirms;
	}
	public InvalidFirms getInvalidFirms() {
		return invalidFirms;
	}
	public void setInvalidFirms(InvalidFirms invalidFirms) {
		this.invalidFirms = invalidFirms;
	}
	public void setNamespace(String namespace) {}
    public void setSchemaInstance(String schemaInstance) {}
    public void setSchemaLocation(String schemaLocation) {}
    public void setVersion(String version) {}
}
