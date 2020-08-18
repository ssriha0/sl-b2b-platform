package com.newco.marketplace.api.mobile.beans.addNotes;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
//import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
//import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
//import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a bean class for storing response information for 
 * the SOAddNoteService
 * @author Infosys
 *
 */
@XSD(name="addNoteResponse.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name = "addNoteResponse")
@XStreamAlias("addNoteResponse")
public class AddNoteResponse implements IAPIResponse{
	
	/*@XStreamAlias("version")
	@XStreamAsAttribute()
	private String version;

	@XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation;

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance;*/
	
	
	@XStreamAlias("soId")
	private String soId;
	
	
	@XStreamAlias("results")
	private Results results;

	/*public AddNoteResponse() {
		this.schemaInstance = PublicMobileAPIConstant.SCHEMA_INSTANCE;
		this.namespace = PublicMobileAPIConstant.MOBILE_SERVICES_NAMESPACE;
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
	/*@XmlAttribute(name = "version")
	public String getVersion() {
		return version;
	}*/

	public void setVersion(String version) {
		//this.version = version;
	}
	/*@XmlAttribute(name = "xsi:schemaLocation")
	public String getSchemaLocation() {
		return schemaLocation;
	}*/

	public void setSchemaLocation(String schemaLocation) {
		//this.schemaLocation = schemaLocation;
	}
	/*@XmlAttribute(name = "xmlns")
	public String getNamespace() {
		return namespace;
	}*/

	public void setNamespace(String namespace) {
		//this.namespace = namespace;
	}
	/*@XmlAttribute(name = "xmlns:xsi")
	public String getSchemaInstance() {
		return schemaInstance;
	}*/

	public void setSchemaInstance(String schemaInstance) {
		//this.schemaInstance = schemaInstance;
	}
	

}
