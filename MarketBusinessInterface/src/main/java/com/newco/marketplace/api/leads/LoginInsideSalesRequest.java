package com.newco.marketplace.api.leads;

import java.util.List;



/**
 * This is a bean class for storing request information for 
 * the Inside Sales Lead API
 * @author Infosys
 *
 */

public class LoginInsideSalesRequest{
	
	private String operation;
	

	private List<String> parameters;


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public List<String> getParameters() {
		return parameters;
	}


	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}


	

	
	
}
