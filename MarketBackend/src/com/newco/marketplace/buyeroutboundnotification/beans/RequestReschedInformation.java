package com.newco.marketplace.buyeroutboundnotification.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("RESCHEDINFORMATION")
public class RequestReschedInformation {
	
	@XStreamImplicit(itemFieldName="RESCHEDINFO")
	private List<RequestRescheduleInfo> requestRescheduleInf;

	public List<RequestRescheduleInfo> getRequestRescheduleInf() {
		return requestRescheduleInf;
	}

	public void setRequestRescheduleInf(
			List<RequestRescheduleInfo> requestRescheduleInf) {
		this.requestRescheduleInf = requestRescheduleInf;
	}
	
	

}
