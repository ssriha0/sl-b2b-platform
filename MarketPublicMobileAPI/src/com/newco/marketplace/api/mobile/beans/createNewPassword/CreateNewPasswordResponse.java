package com.newco.marketplace.api.mobile.beans.createNewPassword;

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
 * the CreateNewPassword
 * @author Infosys
 *
 */
@XSD(name="createNewPasswordResponse.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name = "createNewPasswordResponse")
@XStreamAlias("createNewPasswordResponse")
public class CreateNewPasswordResponse implements IAPIResponse{
	
	@XStreamAlias("results")
	private Results results;
	
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
