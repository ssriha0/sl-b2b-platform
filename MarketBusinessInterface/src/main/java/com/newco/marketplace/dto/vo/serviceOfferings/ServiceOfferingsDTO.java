package com.newco.marketplace.dto.vo.serviceOfferings;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ServiceOfferingsDTO implements Serializable
{
	private static final long serialVersionUID = -1527770441184997995L;
	
    private String title;
    private String image;
    private String serviceDescription;
    private String zipcode;
    private String price;
    private String dailyLimit;
    private String serviceRadius;
    private List<AvailabilityDTO> availabilityList;  
    private String providerRevenue;
    private String skuId;
    private String offeringStatus;
    private String createdBy;
    private Integer offeringId;

     
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(String dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public List<AvailabilityDTO> getAvailabilityList() {
		return availabilityList;
	}
	public void setAvailabilityList(List<AvailabilityDTO> availabilityList) {
		this.availabilityList = availabilityList;
	}
	public String getProviderRevenue() {
		return providerRevenue;
	}
	public void setProviderRevenue(String providerRevenue) {
		this.providerRevenue = providerRevenue;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getOfferingStatus() {
		return offeringStatus;
	}
	public void setOfferingStatus(String offeringStatus) {
		this.offeringStatus = offeringStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getServiceRadius() {
		return serviceRadius;
	}
	public void setServiceRadius(String serviceRadius) {
		this.serviceRadius = serviceRadius;
	}
	public Integer getOfferingId() {
		return offeringId;
	}
	public void setOfferingId(Integer offeringId) {
		this.offeringId = offeringId;
	}
	
	
	
}
