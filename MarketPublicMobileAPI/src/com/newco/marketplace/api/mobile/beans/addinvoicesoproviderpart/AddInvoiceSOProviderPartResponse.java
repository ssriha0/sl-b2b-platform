package com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing response information for the Mobile
 * TimeOnSite Response
 * 
 * @author Infosys
 * 
 */
@XSD(name = "addInvoiceSOProviderPartResponse.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "addInvoiceSOProviderPartResponse")
@XStreamAlias("addInvoiceSOProviderPartResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddInvoiceSOProviderPartResponse implements IAPIResponse {

	
	@XStreamAlias("results")
	private Results results; 
	
	@XStreamAlias("soId")
	private String soId;
	
	

/*	@XStreamAlias("invoicePartsList")
	private InvoicePartsList invoicePartsList;*/
	
	// default constructor
	public AddInvoiceSOProviderPartResponse() {
	}
	
	public AddInvoiceSOProviderPartResponse(String soId, Results results) {		
		this.soId = soId;
		this.results = results;
		
	}
	
	/*public AddInvoiceSOProviderPartResponse(String soId, Results results,
			InvoicePartsList invoicePartsList) {		
		this.soId = soId;
		this.results = results;
		this.invoicePartsList = invoicePartsList;
	}*/

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public Results getResults() {
		return results;
	}


	public void setResults(Results results) {
		this.results = results;
	}

/*	public InvoicePartsList getInvoicePartsList() {
		return invoicePartsList;
	}

	public void setInvoicePartsList(InvoicePartsList invoicePartsList) {
		this.invoicePartsList = invoicePartsList;
	}
*/
	public void setVersion(String version) {
		// Auto-generated method stub
	}

	public void setSchemaLocation(String schemaLocation) {
		// Auto-generated method stub
	}

	public void setSchemaInstance(String schemaInstance) {
		// Auto-generated method stub
	}

	public void setNamespace(String namespace) {
		// Auto-generated method stub
	}
}
