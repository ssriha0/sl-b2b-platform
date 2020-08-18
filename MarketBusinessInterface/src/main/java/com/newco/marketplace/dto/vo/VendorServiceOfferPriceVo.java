package com.newco.marketplace.dto.vo;


public class VendorServiceOfferPriceVo {

	
	private Integer id;
	private Integer vendorServiceAreaId;
	private Double price;
	private Integer dailyLimit;
	private String sku;
	private String skuId;
	private Integer vendorOfferingId;
	
	
	public Integer getVendorOfferingId() {
		return vendorOfferingId;
	}
	public void setVendorOfferingId(Integer vendorOfferingId) {
		this.vendorOfferingId = vendorOfferingId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVendorServiceAreaId() {
		return vendorServiceAreaId;
	}
	public void setVendorServiceAreaId(Integer vendorServiceAreaId) {
		this.vendorServiceAreaId = vendorServiceAreaId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(Integer dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
}
