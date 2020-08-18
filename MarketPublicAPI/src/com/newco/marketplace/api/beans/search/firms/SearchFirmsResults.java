package com.newco.marketplace.api.beans.search.firms;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "searchFirmsResponse.xsd", path = "/resources/schemas/search/")
@XmlRootElement(name = "searchFirmsResponse")
@XStreamAlias("searchFirmsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchFirmsResults implements IAPIResponse {
	
	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("firms")
	private SearchFirmsResponse firms;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public SearchFirmsResponse getFirms() {
		return firms;
	}

	public void setFirms(SearchFirmsResponse firms) {
		this.firms = firms;
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
