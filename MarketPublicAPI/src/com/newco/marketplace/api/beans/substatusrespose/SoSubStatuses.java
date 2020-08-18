package com.newco.marketplace.api.beans.substatusrespose;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Status")
public class SoSubStatuses {
	
	@XStreamAsAttribute 
    @XStreamAlias("value")
	private String statusValue;

	@XStreamImplicit(itemFieldName = "SubStatus")
	private List<SubStatus> subStatus;

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public List<SubStatus> getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(List<SubStatus> subStatus) {
		this.subStatus = subStatus;
	}
	
}
