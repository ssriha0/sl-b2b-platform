package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

public class ServiceOrderFilterExtractDTO extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3465410706112337809L;
	private ArrayList<String> status;
	private ArrayList<String> subStatus;
	
	public ArrayList<String> getStatus() {
		return status;
	}
	public void setStatus(List status) {
		this.status = (ArrayList<String>)status;
	}
	public ArrayList<String> getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(List subStatus) {
		this.subStatus = (ArrayList<String>) subStatus;
	}
	
	

}
