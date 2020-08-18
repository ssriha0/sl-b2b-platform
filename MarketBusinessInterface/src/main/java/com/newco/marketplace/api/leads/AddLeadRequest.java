package com.newco.marketplace.api.leads;

import java.util.List;



/**
 * This is a bean class for storing request information for 
 * the Inside Sales Lead API
 * @author Infosys
 *
 */


public class AddLeadRequest{
	
	private String operation;
	

	private List<Parameters> parameters;


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public List<Parameters> getParameters() {
		return parameters;
	}


	public void setParameters(List<Parameters> parameters) {
		this.parameters = parameters;
	}


	
	
}
