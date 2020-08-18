package com.newco.marketplace.dto.vo.serviceOfferings;

import java.io.Serializable;

import java.sql.Date;
import java.util.List;

public class ServiceOfferingsVO implements Serializable
{
	private static final long serialVersionUID = -1527770441184997995L;
	
    private String title;
    private String image;
    private String serviceDescription;
    private String zipcode;
    private Double price;
    private Integer skuId;
    private Integer vendorId;
    private String createdBy;
    private String modifiedBy;
    private Integer dailyLimit;
    private String offeringStatus;
    private Integer offeringId;
    private Date createdDate;
    private Date modifiedDate;
    private List<AvailabilityVO> availabilityList;
    private List<PricingVO> priceList;
    private Double providerRevenue;
    private Integer serviceRadius; 
    //Added for History
    private String action;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(Integer dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public String getOfferingStatus() {
		return offeringStatus;
	}
	public void setOfferingStatus(String offeringStatus) {
		this.offeringStatus = offeringStatus;
	}
	public Integer getOfferingId() {
		return offeringId;
	}
	public void setOfferingId(Integer offeringId) {
		this.offeringId = offeringId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public List<AvailabilityVO> getAvailabilityList() {
		return availabilityList;
	}
	public void setAvailabilityList(List<AvailabilityVO> availabilityList) {
		this.availabilityList = availabilityList;
	}
	public List<PricingVO> getPriceList() {
		return priceList;
	}
	public void setPriceList(List<PricingVO> priceList) {
		this.priceList = priceList;
	}
	public Double getProviderRevenue() {
		return providerRevenue;
	}
	public void setProviderRevenue(Double providerRevenue) {
		this.providerRevenue = providerRevenue;
	}
	public Integer getServiceRadius() {
		return serviceRadius;
	}
	public void setServiceRadius(Integer serviceRadius) {
		this.serviceRadius = serviceRadius;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
    
	
}
