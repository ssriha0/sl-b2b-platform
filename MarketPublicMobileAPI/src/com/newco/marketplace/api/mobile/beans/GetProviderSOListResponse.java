package com.newco.marketplace.api.mobile.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="getProviderSOListResponse.xsd", path="/resources/schemas/mobile/")
@XStreamAlias("getProviderSOListResponse")
@XmlRootElement(name = "getProviderSOListResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetProviderSOListResponse implements IAPIResponse{
	

	@XStreamAlias("results")
	@XmlElement(name="results")
	private Results results;
	
	@XStreamAlias("serviceOrderList")
	@XmlElement(name="serviceOrderList")
	private ProviderSOList serviceOrderList;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}
	
	public ProviderSOList getServiceOrderList() {
		return serviceOrderList;
	}

	public void setServiceOrderList(ProviderSOList serviceOrderList) {
		this.serviceOrderList = serviceOrderList;
	}

	private ResultsCode validationCode;

	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
		
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
