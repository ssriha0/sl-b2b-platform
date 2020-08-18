package com.newco.marketplace.api.leads;

import java.util.List;



/**
 * This is a bean class for storing request information for 
 * the Inside Sales Lead API
 * @author Infosys
 *
 */

public class GetInsideSalesRequest{

	private List<GetISDataParameters> parameters;

	public List<GetISDataParameters> getParameters() {
		return parameters;
	}
	public void setParameters(List<GetISDataParameters> parameters) {
		this.parameters = parameters;
	}	
}
