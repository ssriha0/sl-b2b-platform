package com.newco.marketplace.api.beans.so.cancel.v1_2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("soStatusInfo")
public class SOStatusInfo {
	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("substatus")
	private String substatus;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubstatus() {
		return substatus;
	}

	public void setSubstatus(String substatus) {
		this.substatus = substatus;
	}
	
	
}
