package com.servicelive.orderfulfillment.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CacheRefreshResponse {
	
	private String message;
	private List<String> errors = new ArrayList<String>();
	
	@XmlElement()
	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@XmlElementWrapper(name = "errors")
	@XmlElement(name = "error")
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}
	
	public CacheRefreshResponse addError(String error) {
		errors.add(error);
		return this;
	}
	public CacheRefreshResponse addErrors( List<String> newerrors) {
		errors.addAll(newerrors);
		return this;
	}
	
}
