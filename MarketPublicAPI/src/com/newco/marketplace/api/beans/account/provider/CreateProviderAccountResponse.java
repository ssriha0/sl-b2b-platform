package com.newco.marketplace.api.beans.account.provider;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "createProviderAccountResponse")
@XStreamAlias("createProviderAccountResponse")
public class CreateProviderAccountResponse{	

	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("vendorId")
	private Integer vendorId;
	
	@XStreamAlias("vendorResourceId")
	private Integer vendorResourceId;
	
		
	
		public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getVendorResourceId() {
		return vendorResourceId;
	}

	public void setVendorResourceId(Integer vendorResourceId) {
		this.vendorResourceId = vendorResourceId;
	}

	

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		return "CreateProviderAccountResponse [results=" + results
				+ ", vendorId=" + vendorId + ", vendorResourceId="
				+ vendorResourceId + "]";
	}

	
}
