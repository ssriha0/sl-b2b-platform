package com.newco.marketplace.vo.ordermanagement.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ScheduleStatus {

	@XStreamAlias("status") 
	private String status;
	@XStreamAlias("preCallDate") 
	private String preCallDate;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPreCallDate() {
		return preCallDate;
	}
	public void setPreCallDate(String preCallDate) {
		this.preCallDate = preCallDate;
	}
	
}
