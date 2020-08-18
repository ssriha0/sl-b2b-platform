package com.newco.marketplace.api.exceptions;

import java.util.List;

public class ValidationException extends Exception {

	private List<String> errors;
	
	public ValidationException(List<String> validationErrors) {
		errors = validationErrors;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3772177002555032367L;

	public String getErrorMessages(){
		StringBuilder sb = new StringBuilder();
		for(String s: errors){
			sb.append(s).append("\n");
		}
		return sb.toString();
	}
}
