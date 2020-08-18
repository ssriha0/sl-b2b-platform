package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;

public class ProviderSearchResponseDetails extends SerializableBaseVO{

	private static final long serialVersionUID = -7907781571754029772L;
	
	private int vendorId;
	private int providerWfStatus;
	private String providerCity;
	private String providerState;
	private String providerWfStatusDesc;
	
	public String getProviderWfStatusDesc() {
		return providerWfStatusDesc;
	}
	public void setProviderWfStatusDesc(String providerWfStatusDesc) {
		this.providerWfStatusDesc = providerWfStatusDesc;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getProviderWfStatus() {
		return providerWfStatus;
	}
	public void setProviderWfStatus(int providerWfStatus) {
		this.providerWfStatus = providerWfStatus;
	}
	public String getProviderCity() {
		return providerCity;
	}
	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
	}
	public String getProviderState() {
		return providerState;
	}
	public void setProviderState(String providerState) {
		this.providerState = providerState;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}