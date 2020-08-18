package com.newco.marketplace.api.beans.searchCriteria;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/07/03
 * the response for SOGetSearchCriteria
 *
 */
@XSD(name="soGetSearchCriteriaResponse.xsd", path="/resources/schemas/mobile/v3_0/")
@XStreamAlias("searchCriteriaResponse")
@XmlRootElement(name = "searchCriteriaResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOGetSearchCriteriaResponse implements IAPIResponse{

	@XStreamAlias("results")
	@XmlElement(name="results")
	private Results results;
	
	@XStreamAlias("searchCriterias")
	@XmlElement(name="searchCriterias")
	private SearchCriterias searchCriterias;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public SearchCriterias getSearchCriterias() {
		return searchCriterias;
	}

	public void setSearchCriterias(SearchCriterias searchCriterias) {
		this.searchCriterias = searchCriterias;
	}

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
