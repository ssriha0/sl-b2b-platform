package com.newco.marketplace.dto.vo.serviceOfferings;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ManageServiceOfferingsDTO implements Serializable
{
	private static final long serialVersionUID = -1527770441184997995L;
	
    private String service;
    private String sku;
    private String status;
    private String price;
    private String serviceType;
    private String skuId;
    
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
    
    
	
	
	
}
