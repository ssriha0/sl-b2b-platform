package com.newco.marketplace.dto.vo.provider;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.dto.vo.BuyerSkuVO;

/**
 * This class would act as the VO class for search Firms API for standard service offerings
 *
 */
public class SearchFirmsVO {
	
	private String zip;
	private Integer mainCategory;
	private Date serviceDate1;
	private Date serviceDate2;
	private String serviceTimeWindow;
	private Integer timeWindow;
	private String radius;
	private List<String> zipcodeList;
	private List<BuyerSkuVO> skuList;
	private List<Integer> vendorIdList;
	private Integer buyerId;
	private List<String> serviceDays;
	private List<Integer> offeringIdList;
	private Integer skuId;
	private String sku;
	private Date serviceDate1GMT;
	private Date serviceDate2GMT;
	private List<Integer> skuIdList;
	
	public List<Integer> getSkuIdList() {
		return skuIdList;
	}
	public void setSkuIdList(List<Integer> skuIdList) {
		this.skuIdList = skuIdList;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Integer getMainCategory() {
		return mainCategory;
	}
	public void setMainCategory(Integer mainCategory) {
		this.mainCategory = mainCategory;
	}
	public Date getServiceDate1() {
		return serviceDate1;
	}
	public void setServiceDate1(Date serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}
	public Date getServiceDate2() {
		return serviceDate2;
	}
	public void setServiceDate2(Date serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	public List<String> getZipcodeList() {
		return zipcodeList;
	}
	public void setZipcodeList(List<String> zipcodeList) {
		this.zipcodeList = zipcodeList;
	}
	public List<BuyerSkuVO> getSkuList() {
		return skuList;
	}
	public void setSkuList(List<BuyerSkuVO> skuList) {
		this.skuList = skuList;
	}
	public List<Integer> getVendorIdList() {
		return vendorIdList;
	}
	public void setVendorIdList(List<Integer> vendorIdList) {
		this.vendorIdList = vendorIdList;
	}
	public String getServiceTimeWindow() {
		return serviceTimeWindow;
	}
	public void setServiceTimeWindow(String serviceTimeWindow) {
		this.serviceTimeWindow = serviceTimeWindow;
	}
	public Integer getTimeWindow() {
		return timeWindow;
	}
	public void setTimeWindow(Integer timeWindow) {
		this.timeWindow = timeWindow;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public List<String> getServiceDays() {
		return serviceDays;
	}
	public void setServiceDays(List<String> serviceDays) {
		this.serviceDays = serviceDays;
	}
	public List<Integer> getOfferingIdList() {
		return offeringIdList;
	}
	public void setOfferingIdList(List<Integer> offeringIdList) {
		this.offeringIdList = offeringIdList;
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
	public Date getServiceDate1GMT() {
		return serviceDate1GMT;
	}
	public void setServiceDate1GMT(Date serviceDate1GMT) {
		this.serviceDate1GMT = serviceDate1GMT;
	}
	public Date getServiceDate2GMT() {
		return serviceDate2GMT;
	}
	public void setServiceDate2GMT(Date serviceDate2GMT) {
		this.serviceDate2GMT = serviceDate2GMT;
	}


	

	
}
