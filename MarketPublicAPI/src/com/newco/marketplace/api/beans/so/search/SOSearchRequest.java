package com.newco.marketplace.api.beans.so.search;

import com.newco.marketplace.api.beans.so.Identification;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the SOASearchService
 * @author Infosys
 *
 */
@XStreamAlias("searchRequest")
public class SOSearchRequest {

	@XStreamAlias("identification")
	private Identification identification;
	
	@XStreamAlias("soSearch")
	private SOSearch soSearch ;

	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

	public SOSearch getSosearch() {
		return soSearch;
	}

	public void setSosearch(SOSearch soSearch) {
		this.soSearch = soSearch;
	}
}
