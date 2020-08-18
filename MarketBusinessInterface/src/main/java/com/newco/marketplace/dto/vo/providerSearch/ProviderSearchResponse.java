package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchResponseDetails;

public class ProviderSearchResponse extends SerializableBaseVO{
	
	private static final long serialVersionUID = -6058268867449492648L;
	private int providerId;
	private ProviderSearchResponseDetails providerSearchResponseDetails;
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
	public int getProviderId() {
		return providerId;
	}
	public void setProviderId(int providerId) {
		this.providerId = providerId;
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
	public ProviderSearchResponseDetails getProviderSearchResponseDetails() {
		return providerSearchResponseDetails;
	}
	public void setProviderSearchResponseDetails(
			ProviderSearchResponseDetails providerSearchResponseDetails) {
		this.providerSearchResponseDetails = providerSearchResponseDetails;
	}
	
}