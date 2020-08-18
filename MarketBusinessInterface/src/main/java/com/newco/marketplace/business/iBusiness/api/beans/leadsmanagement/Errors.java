package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of results.
 * @author Infosys
 *
 */
@XStreamAlias("errors")
public class Errors {
	
	@XStreamImplicit(itemFieldName="error")
	private List<String> error;

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

}
