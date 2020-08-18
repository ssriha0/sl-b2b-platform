package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Merchandise")
public class Merchandise implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 8265011543072690409L;

	@XStreamAlias("Brand")
	private String brand;
	
	@XStreamAlias("Code")
	private String code;
	
	@XStreamAlias("Description")
	private String description;
	
	@XStreamAlias("Division")
	private String division;
	
	@XStreamAlias("ItemNumber")
	private String itemNumber;
	
	@XStreamAlias("Model")
	private String model;
	
	@XStreamAlias("PurchaseDate")
	private String purchaseDate;
	
	@XStreamAlias("ResidentialOrCommercialUsage")
	private String residentialOrCommercialUsage;
	
	@XStreamAlias("SerialNumber")
	private String serialNumber;
	
	@XStreamAlias("SearsSoldCode")
	private String searsSoldCode;
	
	@XStreamAlias("SearsStoreNumber")
	private String searsStoreNumber;
	
	@XStreamAlias("Specialty")
	private String specialty;
	
	@XStreamAlias("SystemItemSuffix")
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

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
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

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getSystemItemSuffix() {
		return systemItemSuffix;
	}

	public void setSystemItemSuffix(String systemItemSuffix) {
		this.systemItemSuffix = systemItemSuffix;
	}
}
