package com.newco.marketplace.api.mobile.beans.rejectServiceOrder;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing response information for the
 * MobileSORejectService 
 * @author Infosys
 * @version 1.0
 * @Date 2015/04/16
 */
@XSD(name="mobileRejectSOResponse.xsd", path="/resources/schemas/mobile/v3_0/")
@XStreamAlias("mobileRejectSOResponse")
@XmlRootElement(name="mobileRejectSOResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileRejectSOResponse implements IAPIResponse {
	
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
