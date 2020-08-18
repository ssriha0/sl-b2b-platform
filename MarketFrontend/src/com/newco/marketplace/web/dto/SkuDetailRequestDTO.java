package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

public class SkuDetailRequestDTO {
	private List<SkuDetail> skuDetails = new ArrayList<SkuDetail>();
	private String primaryIndustryId;
	 

	public List<SkuDetail> getSkuDetails() {
		return skuDetails;
	}

	public void setSkuDetails(List<SkuDetail> skuDetails) {
		this.skuDetails = skuDetails;
	}

	public String getPrimaryIndustryId() {
		return primaryIndustryId;
	}

	public void setPrimaryIndustryId(String primaryIndustryId) {
		this.primaryIndustryId = primaryIndustryId;
	}
	
}
