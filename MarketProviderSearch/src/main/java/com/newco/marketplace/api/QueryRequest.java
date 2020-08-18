package com.newco.marketplace.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.search.types.ServiceTypes;

public class QueryRequest {
	private String zipCode;
	private String query;
	private int radius;
	
	private List<ServiceTypes> requestedFilters;
	private Map<ServiceTypes, String> appliedFilters;
	private ServiceTypes sortBy;
	
	public QueryRequest () {
		requestedFilters = new ArrayList<ServiceTypes>();
		appliedFilters  = new HashMap<ServiceTypes, String>();
	}
	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		if (query != null)
		  this.query = query.trim();
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {		
		this.radius = radius;
	}
	
	public List<ServiceTypes> getRequestedFilters() {
		return requestedFilters;
	}
	
	public void setRequestedFilters(List<ServiceTypes> requestedFilters) {
		this.requestedFilters = requestedFilters;
	}
	
	public Map<ServiceTypes, String> getAppliedFilters() {
		return appliedFilters;
	}
	
	public void setAppliedFilters(Map<ServiceTypes, String> appliedFilters) {
		this.appliedFilters = appliedFilters;
	}

	public ServiceTypes getSortBy() {
		return sortBy;
	}

	public void setSortBy(ServiceTypes sortBy) {
		this.sortBy = sortBy;
	}
	
}
