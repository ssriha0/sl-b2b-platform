package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing list of PriceChangeReason information.
 * @author Infosys
 *
 */

@XStreamAlias("reasons")
public class PriceChangeReason {
	
	@XStreamImplicit(itemFieldName="reason")
	private List<Reason> reason;

	public List<Reason> getReason() {
		return reason;
	}

	public void setReason(List<Reason> reason) {
		this.reason = reason;
	}

	
}
