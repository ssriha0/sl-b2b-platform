package com.newco.marketplace.api.mobile.beans.rescheduleRespond;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "rescheduleResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "rescheduleResponse")
@XStreamAlias("rescheduleResponse")
public class RescheduleResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;
    
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

	public Results getResults() {
		return results;
	}

    
}
