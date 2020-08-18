package com.newco.marketplace.web.dto;

import java.util.List;

public class SkuDetail {
	private String sku;
	private String skuid;
	private String skuName;
	private String offeringId;
	private List<SkuPrices> skuPrices;

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	public List<SkuPrices> getSkuPrices() {
		return skuPrices;
	}

	public void setSkuPrices(List<SkuPrices> skuPrices) {
		this.skuPrices = skuPrices;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}
