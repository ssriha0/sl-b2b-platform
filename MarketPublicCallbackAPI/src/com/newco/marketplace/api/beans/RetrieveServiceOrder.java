package com.newco.marketplace.api.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("serviceOrder")
public class RetrieveServiceOrder {

	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("orderstatus")
	private OrderStatus orderstatus;

	@XStreamAlias("jobCodes")
	private JobCodes jobCodes;

	@XStreamAlias("customReferences")
	private CustomReferences customReferences;

	@XStreamAlias("invoiceParts")
	private InvoiceParts invoiceParts;

	@XStreamAlias("schedule")
	private Schedule schedule;
	
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public OrderStatus getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(OrderStatus orderstatus) {
		this.orderstatus = orderstatus;
	}

	public CustomReferences getCustomReferences() {
		return customReferences;
	}

	public void setCustomReferences(CustomReferences customReferences) {
		this.customReferences = customReferences;
	}

	public InvoiceParts getInvoiceParts() {
		return invoiceParts;
	}

	public void setInvoiceParts(InvoiceParts invoiceParts) {
		this.invoiceParts = invoiceParts;
	}

	public JobCodes getJobCodes() {
		return jobCodes;
	}

	public void setJobCodes(JobCodes jobCodes) {
		this.jobCodes = jobCodes;
	}

}
