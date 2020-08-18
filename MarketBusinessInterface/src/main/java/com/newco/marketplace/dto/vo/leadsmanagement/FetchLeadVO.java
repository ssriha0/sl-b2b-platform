package com.newco.marketplace.dto.vo.leadsmanagement;

//Vo class which holds sl_lead_hdr values.
public class FetchLeadVO {
	private String firmId;
	private String status;
    private Integer count;
	private String staleAfter;
	
   	
	public FetchLeadVO() {

	}


	public String getFirmId() {
		return firmId;
	}


	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public String getStaleAfter() {
		return staleAfter;
	}


	public void setStaleAfter(String staleAfter) {
		this.staleAfter = staleAfter;
	}
	
	
}
