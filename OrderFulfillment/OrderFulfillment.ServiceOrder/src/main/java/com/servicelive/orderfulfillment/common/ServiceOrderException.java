package com.servicelive.orderfulfillment.common;

import java.util.ArrayList;
import java.util.List;

public class ServiceOrderException extends RuntimeException {

	private static final long serialVersionUID = -7483359657592468297L;
	
	private List<String> errors = new ArrayList<String>();
	
	public ServiceOrderException() {
		super();
	}
	public ServiceOrderException(Throwable cause) {
		super(cause);
	}
	public ServiceOrderException(String message) {
		super(message);
		errors.add(message);
	}
	public ServiceOrderException(List<String> errors) {
		super(errors.get(0));
		this.errors.addAll(errors);
	}

	public ServiceOrderException(String message, Throwable cause) {
		super(message, cause);
		errors.add(message);
	}
	public ServiceOrderException(List<String> errors, Throwable cause) {
		super(errors.get(0), cause);
		this.errors.addAll(errors);
	}
    
	public ServiceOrderException addError( String error ) {
		this.errors.add(error);
		return this;
	}
	
	public List<String> getErrors() {
		return this.errors;
	}
	
	@Override
	public String getMessage(){
		StringBuilder sb = new StringBuilder();
		for(String s : errors)
			sb.append(s).append("\n");
		return sb.toString();
	}	
}
