/**
 * 
 */
package com.newco.marketplace.api.beans.buyerskus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Infosys
 *
 */
@XSD(name = "serviceCategoryResponse.xsd", path = "/resources/schemas/so/v1_1/")
@XmlRootElement(name = "serviceCategoryResponse")
@XStreamAlias("serviceCategoryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceCategoryResponse implements IAPIResponse{
    
	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("serviceCategories")
	private ServiceCategories serviceCategories;
	
   
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	public void setVersion(String version) {}
	public void setSchemaLocation(String schemaLocation) {}
	public void setNamespace(String namespace) {}
	public void setSchemaInstance(String schemaInstance) {}
	
	public ServiceCategories getServiceCategories() {
		return serviceCategories;
	}
	public void setServiceCategories(ServiceCategories serviceCategories) {
		this.serviceCategories = serviceCategories;
	}
	
	
}
