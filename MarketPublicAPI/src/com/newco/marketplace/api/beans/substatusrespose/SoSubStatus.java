package com.newco.marketplace.api.beans.substatusrespose;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("soSubStatus")
public class SoSubStatus {

	@XStreamAlias("status")
	private String status;
	
	@XStreamImplicit(itemFieldName = "subStatus")
	private List<SubStatus> subStatus;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SubStatus> getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(List<SubStatus> subStatus) {
		this.subStatus = subStatus;
	}

}
