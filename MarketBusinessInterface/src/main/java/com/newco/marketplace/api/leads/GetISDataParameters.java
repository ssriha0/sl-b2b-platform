package com.newco.marketplace.api.leads;

import java.util.List;



/**
 * This is a bean class for storing request information for 
 * the Inside Sales Lead API
 * @author Infosys
 *
 */


public class GetISDataParameters{
	
	private String field;
	private List<String> values;
	private String operator;

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
