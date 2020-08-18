package com.newco.marketplace.api.beans.so.retrieve;

import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing response filter information for 
 * the SORetrieveService
 * @author Infosys
 *
 */
@XStreamAlias("responsefilter")
public class ResponseFilter {
	@XStreamImplicit(itemFieldName="showSection")
	private List<String> showSection;

	public List<String> getShowSection() {
		return showSection;
	}

	public void setShowSection(List<String> showSection) {
		this.showSection = showSection;
	}
}
