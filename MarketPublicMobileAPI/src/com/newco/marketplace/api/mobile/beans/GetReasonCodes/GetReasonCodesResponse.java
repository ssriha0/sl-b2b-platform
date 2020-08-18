package com.newco.marketplace.api.mobile.beans.GetReasonCodes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.ReasonCodesList;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "reasonCodesResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "reasonCodesResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XStreamAlias("reasonCodesResponse")
public class GetReasonCodesResponse implements IAPIResponse{
	
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("reasonCodesList")
	private ReasonCodesList reasonCodesList;
	
	public Results getResults() {
		return results;
	}
	
	

	public void setResults(Results results) {
		this.results = results;
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
		
	}

	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
		
	}



	public ReasonCodesList getReasonCodesList() {
		return reasonCodesList;
	}



	public void setReasonCodesList(ReasonCodesList reasonCodesList) {
		this.reasonCodesList = reasonCodesList;
	}
	
}
