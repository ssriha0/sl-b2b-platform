package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

import com.newco.marketplace.webservices.base.CommonVO;

public class ServiceOrderRole extends CommonVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5280125047889314403L;
	private String soId;
	private String soStatus; 
	private String soCity;
	private String soState; 
	private String soZip;
	private String soTitle;
	private String soSubStatus;
	private Timestamp soServiceDate;
	
	public String getSoCity() {
		return soCity;
	}
	public void setSoCity(String soCity) {
		this.soCity = soCity;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Timestamp getSoServiceDate() {
		return soServiceDate;
	}
	public void setSoServiceDate(Timestamp soServiceDate) {
		this.soServiceDate = soServiceDate;
	}
	public String getSoState() {
		return soState;
	}
	public void setSoState(String soState) {
		this.soState = soState;
	}
	public String getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}
	public String getSoSubStatus() {
		return soSubStatus;
	}
	public void setSoSubStatus(String soSubStatus) {
		this.soSubStatus = soSubStatus;
	}
	public String getSoTitle() {
		return soTitle;
	}
	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}
	public String getSoZip() {
		return soZip;
	}
	public void setSoZip(String soZip) {
		this.soZip = soZip;
	}
	
}
