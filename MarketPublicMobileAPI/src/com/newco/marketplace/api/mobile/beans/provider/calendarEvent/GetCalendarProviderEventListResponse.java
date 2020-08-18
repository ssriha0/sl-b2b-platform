package com.newco.marketplace.api.mobile.beans.provider.calendarEvent;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XSD(name = "getProviderCalendarEventListResponse.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "getProviderCalendarEventListResponse")
@XStreamAlias("getProviderCalendarEventListResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetCalendarProviderEventListResponse implements IAPIResponse{
	

	
	@XmlElement(name="results")
	private Results results;

	
	@XmlElement(name="providerCalendarEventList")
	private ProviderCalendarEventList providerCalendarEventList;
	
	

	/**
	 * @return the providerCalendarEventList
	 */ 
	public ProviderCalendarEventList getProviderCalendarEventList() {
		return providerCalendarEventList;
	}

	/**
	 * @param providerCalendarEventList the providerCalendarEventList to set
	 */
	public void setProviderCalendarEventList(
			ProviderCalendarEventList providerCalendarEventList) {
		this.providerCalendarEventList = providerCalendarEventList;
	}

	
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
