package com.servicelive.orderfulfillment.integration.domain;

public class KeyValuePairs {
	
	private String category;
	
	private String subCategory;
	
	private String keyCode;
	
	private String keyName;
	
	private String keyValue;
	
	private Integer priorityIndicator;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public Integer getPriorityIndicator() {
		return priorityIndicator;
	}

	public void setPriorityIndicator(Integer priorityIndicator) {
		this.priorityIndicator = priorityIndicator;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	
}
