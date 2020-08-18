package com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="updateMobileAppVersionResponse.xsd", path="/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "updateMobileAppVersionResponse")
@XStreamAlias("updateMobileAppVersionResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateMobileAppVersionResponse implements IAPIResponse {

	
	
	@XStreamAlias("results")
	private Results results;
	
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
	
}