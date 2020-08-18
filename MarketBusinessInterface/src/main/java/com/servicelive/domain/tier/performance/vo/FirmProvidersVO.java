package com.servicelive.domain.tier.performance.vo;

import java.util.List;

public class FirmProvidersVO {
	private Integer vendorId;
	private List<Integer> providerIds;
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public List<Integer> getProviderIds() {
		return providerIds;
	}
	public void setProviderIds(List<Integer> providerIds) {
		this.providerIds = providerIds;
	}

}
