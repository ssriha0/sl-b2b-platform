package com.newco.marketplace.api.beans.so.reschedule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("acceptRescheduleRequest")
public class SOAcceptRescheduleRequest {
	
	@XStreamAlias("type")
	private String  type;
	
	@XStreamAlias("rescheduleResponse")
	private String rescheduleResponse;
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRescheduleResponse() {
		return rescheduleResponse;
	}

	public void setRescheduleResponse(String rescheduleResponse) {
		this.rescheduleResponse = rescheduleResponse;
	}
}
