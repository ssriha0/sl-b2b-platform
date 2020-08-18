package com.newco.marketplace.dto.vo.provider;

import java.util.List;


/**
 * This class would act as the VO class for search Firms API for standard service offerings
 *
 */
public class ServiceOfferingsVO {
	
	private Integer vendorId;
	private Integer serviceOfferingId;
	private Integer skuId;
	private String sku;
	private Double price;
	private String zip;
	private Integer dailyLimit;
	private Integer soCount;
	private String day;
	private Integer timeWindow;
	private List<ServiceOfferingPriceVO> serviceOfferingPrice; 
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	public Integer getTimeWindow() {
		return timeWindow;
	}
	public void setTimeWindow(Integer timeWindow) {
		this.timeWindow = timeWindow;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getServiceOfferingId() {
		return serviceOfferingId;
	}
	public void setServiceOfferingId(Integer serviceOfferingId) {
		this.serviceOfferingId = serviceOfferingId;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Integer getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(Integer dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public Integer getSoCount() {
		return soCount;
	}
	public void setSoCount(Integer soCount) {
		this.soCount = soCount;
	}
	public List<ServiceOfferingPriceVO> getServiceOfferingPrice() {
		return serviceOfferingPrice;
	}
	public void setServiceOfferingPrice(
			List<ServiceOfferingPriceVO> serviceOfferingPrice) {
		this.serviceOfferingPrice = serviceOfferingPrice;
	}
	
	
}
