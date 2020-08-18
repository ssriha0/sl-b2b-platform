package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "updateSOProviderPartResponse.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "updateSOProviderPartResponse")
@XStreamAlias("updateSOProviderPartResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateSOProviderPartResponse implements IAPIResponse{

	@XStreamAlias("results")
	private Results results;
	
	

	
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

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

}
