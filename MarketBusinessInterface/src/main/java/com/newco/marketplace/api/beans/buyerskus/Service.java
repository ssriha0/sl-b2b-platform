package com.newco.marketplace.api.beans.buyerskus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("service")
@XmlAccessorType(XmlAccessType.FIELD)
public class Service {
	
	@XStreamAlias("mainServiceCategory")
	private String mainServiceCategory;
	
	@XStreamAlias("mainServiceCategoryId")
	private String mainServiceCategoryId;
	
	@XStreamAlias("sku")
	private String sku;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("template")
	private String template;
	
	@XStreamAlias("orderType")
	private String orderType;
	
	@XStreamAlias("retailPrice")
	private String retailPrice;
	
	@XStreamAlias("retailMargin")
	private String retailMargin;
	
	@XStreamAlias("maximumPrice")
	private String maximumPrice;
	
	@XStreamAlias("billingMargin")
	private String billingMargin;
	
	@XStreamAlias("billingPrice")
	private String billingPrice;

	@XStreamAlias("tasks")
	private Tasks tasks;

	public String getMainServiceCategory() {
		return mainServiceCategory;
	}

	public void setMainServiceCategory(String mainServiceCategory) {
		this.mainServiceCategory = mainServiceCategory;
	}

	public String getMainServiceCategoryId() {
		return mainServiceCategoryId;
	}

	public void setMainServiceCategoryId(String mainServiceCategoryId) {
		this.mainServiceCategoryId = mainServiceCategoryId;
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

	
	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getRetailMargin() {
		return retailMargin;
	}

	public void setRetailMargin(String retailMargin) {
		this.retailMargin = retailMargin;
	}

	public String getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(String maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public String getBillingMargin() {
		return billingMargin;
	}

	public void setBillingMargin(String billingMargin) {
		this.billingMargin = billingMargin;
	}

	public String getBillingPrice() {
		return billingPrice;
	}

	public void setBillingPrice(String billingPrice) {
		this.billingPrice = billingPrice;
	}

	

	
	
	
	
}
