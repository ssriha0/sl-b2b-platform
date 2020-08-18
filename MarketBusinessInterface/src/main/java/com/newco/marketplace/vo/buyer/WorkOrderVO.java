package com.newco.marketplace.vo.buyer;

import java.util.List;

/**
 * This class is used as VO for work order SKU validation
 * and to fetch all the SKU details in Cancellation APT (v1.2)
 * */
public class WorkOrderVO {
	//List of work order SKUs
	private List<String> workOrderSKUs;
	//Buyer Id
	private Integer buyerId;
	
	private String skuName;
	
	public List<String> getWorkOrderSKUs() {
		return workOrderSKUs;
	}
	public void setWorkOrderSKUs(List<String> workOrderSKUs) {
		this.workOrderSKUs = workOrderSKUs;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	
	
}
