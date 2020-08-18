package com.newco.marketplace.api.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("eventCallbackResponse")
public class EventCallbackResponse {
	
	@XStreamAlias("results")
	private Results results;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}
	
}
