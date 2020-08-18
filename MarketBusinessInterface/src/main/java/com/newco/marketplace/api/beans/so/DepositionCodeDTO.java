package com.newco.marketplace.api.beans.so;

import java.io.Serializable;

public class DepositionCodeDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String depositionCode;
	private String descr;
	private Boolean clientChargedInd;
	
	public String getDepositionCode() {
		return depositionCode;
	}
	public void setDepositionCode(String depositionCode) {
		this.depositionCode = depositionCode;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Boolean isClientChargedInd() {
		return clientChargedInd;
	}
	public void setClientChargedInd(Boolean clientChargedInd) {
		this.clientChargedInd = clientChargedInd;
	}
	
	
	
}
