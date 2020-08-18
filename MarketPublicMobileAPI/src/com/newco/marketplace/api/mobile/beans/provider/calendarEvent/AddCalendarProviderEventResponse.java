package com.newco.marketplace.api.mobile.beans.provider.calendarEvent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing response information for the provider
 * calendar event response
 * 
 * @author rtiwari
 * 
 */
@XSD(name = "addCalendarProviderEventResponse.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "addCalendarProviderEventResponse")
@XStreamAlias("addCalendarProviderEventResponse")
public class AddCalendarProviderEventResponse implements IAPIResponse {

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
