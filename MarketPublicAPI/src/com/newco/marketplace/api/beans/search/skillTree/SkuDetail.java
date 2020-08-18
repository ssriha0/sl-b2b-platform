package com.newco.marketplace.api.beans.search.skillTree;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("sku")
public class SkuDetail {
	
	@XStreamAlias("skuId")
	private Integer skuId;
	
	@XStreamAlias("skuName")
	private String sku;
	
	@XStreamAlias("skuDescription")
	private String description;
	
	@XStreamAlias("template")
	private String template;
	
	@XStreamAlias("orderType")
	private String orderType;
	
	@XStreamAlias("retailPrice")
    private double retailPrice;
	
	@XStreamAlias("margin")
    private double margin;
	
	@XStreamAlias("maximumPrice")
    private double maximumPrice;
	
	@XStreamAlias("billingMargin")
    private double billingMargin;
	
	@XStreamAlias("billingPrice")
    private double billingPrice;
	
	@XStreamAlias("tasks")
	private Tasks tasks;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public double getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public double getBillingMargin() {
		return billingMargin;
	}

	public void setBillingMargin(double billingMargin) {
		this.billingMargin = billingMargin;
	}

	public double getBillingPrice() {
		return billingPrice;
	}

	public void setBillingPrice(double billingPrice) {
		this.billingPrice = billingPrice;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

}
