package com.newco.marketplace.api.mobile.beans.forgetUNamePwd;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.forgetUnamePwd.SecurityQuestion;
import com.newco.marketplace.api.beans.forgetUnamePwd.UserDetails;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing response information for the Mobile
 * ForgetUnamePwdService1Response
 * 
 * @author Infosys
 * 
 */
@XSD(name = "validateSecQuestAnsResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "validateSecQuestAnsResponse")
@XStreamAlias("validateSecQuestAnsResponse")
public class ValidateSecQuestAnsResponse implements IAPIResponse {

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
