package com.newco.marketplace.api;

import java.io.Serializable;

import com.newco.marketplace.search.types.ServiceTypes;

public class SearchFilter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServiceTypes type;
	private String value;
	private long count;
	
	public SearchFilter(ServiceTypes type, String value, long count) {	
		this.type = type;
		this.value = value;
		this.count = count;
	}	
	
	public ServiceTypes getType() {
		return type;
	}
	public void setType(ServiceTypes type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return type.getValue() + ":" + value + "(" + count + ")";
	}

}
