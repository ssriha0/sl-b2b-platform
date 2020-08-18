package com.servicelive.integrationtest.domain;

import java.util.ArrayList;
import java.util.List;

public class QueryResults {
	private List<QueryRow> results = new ArrayList<QueryRow>();
	private String queryName;

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public List<QueryRow> getResults() {
		return results;
	}

	public void setResults(List<QueryRow> results) {
		this.results = results;
	}
	
	public void addResult(QueryRow row) {
		results.add(row);
	}
	
}
