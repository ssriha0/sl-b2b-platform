package com.newco.marketplace.api.mobile.beans.Filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="getFilterResponse.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "getFilterResponse")
@XStreamAlias("getFilterResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetFilterResponse implements IAPIResponse  {


	@XStreamAlias("results")
	private Results results;
	@XStreamAlias("filters")
	private Filters filters;

	public Filters getFilters() {
		return filters;
	}
	public void setFilters(Filters filters) {
		this.filters = filters;
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
