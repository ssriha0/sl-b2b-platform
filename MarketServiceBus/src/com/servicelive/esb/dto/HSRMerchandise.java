package com.servicelive.esb.dto;

import java.io.Serializable;

public class HSRMerchandise implements Serializable {

	
	/** generated serialVersionUID */
	private static final long serialVersionUID = -2446380137466278760L;

	private String code;
	
	private String description;
	
	private String brand;
	
	private String division;
	
	private String itemNumber;
	
	private String model;
	
	private String residentialOrCommercialUsage;
	
	private String serialNumber;
	
	private String searsSoldCode;
	
	private String searsStoreNumber;
	
	private String systemItemSuffix;
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getResidentialOrCommercialUsage() {
		return residentialOrCommercialUsage;
	}

	public void setResidentialOrCommercialUsage(
			String residentialOrCommercialUsage) {
		this.residentialOrCommercialUsage = residentialOrCommercialUsage;
	}

	public String getSearsSoldCode() {
		return searsSoldCode;
	}

	public void setSearsSoldCode(String searsSoldCode) {
		this.searsSoldCode = searsSoldCode;
	}

	public String getSearsStoreNumber() {
		return searsStoreNumber;
	}

	public void setSearsStoreNumber(String searsStoreNumber) {
		this.searsStoreNumber = searsStoreNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSystemItemSuffix() {
		return systemItemSuffix;
	}

	public void setSystemItemSuffix(String systemItemSuffix) {
		this.systemItemSuffix = systemItemSuffix;
	}
}
