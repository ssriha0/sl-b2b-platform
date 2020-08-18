package com.newco.marketplace.api.mobile.beans.so.addEstimate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="addSOEstimateResponse.xsd",  path="/resources/schemas/mobile/")
@XStreamAlias("addEstimateResponse")
@XmlRootElement(name = "addEstimateResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddEstimateResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("estimationId")
	private Integer estimationId;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public void setVersion(String version) {}
    public void setSchemaLocation(String schemaLocation) {}
    public void setNamespace(String namespace) {}
    public void setSchemaInstance(String schemaInstance) {}

	public Integer getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(Integer estimationId) {
		this.estimationId = estimationId;
	}

}
