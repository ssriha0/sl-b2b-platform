package com.newco.marketplace.api.beans.hi.account.firm.approve;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ApprovedFirms;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ApprovedProviders;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.InvalidFirms;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.InvalidProviders;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="approveProvidersResponse.xsd", path="/resources/schemas/ums/")
@XmlRootElement(name = "approveProvidersResponse")
@XStreamAlias("approveProvidersResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApproveProvidersResponse implements IAPIResponse {
	
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("approvedProviders")
	private ApprovedProviders approvedProviders;
	
	@XStreamAlias("invalidProviders")
	private InvalidProviders invalidProviders;
	
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	public ApprovedProviders getApprovedProviders() {
		return approvedProviders;
	}
	public void setApprovedProviders(ApprovedProviders approvedProviders) {
		this.approvedProviders = approvedProviders;
	}
	public InvalidProviders getInvalidProviders() {
		return invalidProviders;
	}
	public void setInvalidProviders(InvalidProviders invalidProviders) {
		this.invalidProviders = invalidProviders;
	}
	public void setNamespace(String namespace) {}
    public void setSchemaInstance(String schemaInstance) {}
    public void setSchemaLocation(String schemaLocation) {}
    public void setVersion(String version) {}
}
