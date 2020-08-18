package com.newco.marketplace.business.businessImpl.vibePostAPI;

public class PushAlertStatusVo {
	private String alertTaskId;
	private String soId;
	private String status;
	
	public String getAlertTaskId() {
		return alertTaskId;
	}
	public void setAlertTaskId(String alertTaskId) {
		this.alertTaskId = alertTaskId;
	}
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
}
