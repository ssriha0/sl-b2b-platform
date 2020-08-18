package com.newco.marketplace.inhomeautoclose.vo;

import java.sql.Date;




public class InHomeAutoCloseProcessVO {

	
	private String soId;
	private Integer noOfRetries;
	private String status;
	private Integer autoCloseId;
	private String soSubstatus;
	
	
	
	 
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getNoOfRetries() {
		return noOfRetries;
	}
	public void setNoOfRetries(Integer noOfRetries) {
		this.noOfRetries = noOfRetries;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getAutoCloseId() {
		return autoCloseId;
	}
	public void setAutoCloseId(Integer autoCloseId) {
		this.autoCloseId = autoCloseId;
	}
	public String getSoSubstatus() {
		return soSubstatus;
	}
	public void setSoSubstatus(String soSubstatus) {
		this.soSubstatus = soSubstatus;
	}
	
	

	
}
