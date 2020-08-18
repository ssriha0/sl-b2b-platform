package com.servicelive.orderfulfillment.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the In Home Out
 * bound Notification Service
 * 
 * @author Infosys
 */
@XStreamAlias("merchandiseData")
public class MerchandiseData {

	@XStreamAlias("delReceiveDate")
	private String delReceiveDate;
	
	@XStreamAlias("itemSuffix")
	private String itemSuffix;

	@XStreamAlias("purchaseDate")
	private String purchaseDate;
	
	@XStreamAlias("storeNumber")
	private String storeNumber;
	
	@XStreamAlias("usageType")
	private String usageType;

	@XStreamAlias("whereBought")
	private String whereBought;

	@XStreamAlias("serialNum")
	private String serialNum;
	
	@XStreamAlias("modelNum")
	private String modelNum;
	
	@XStreamAlias("applBrand")
	private String applBrand;

	public String getDelReceiveDate() {
		return delReceiveDate;
	}

	public void setDelReceiveDate(String delReceiveDate) {
		this.delReceiveDate = delReceiveDate;
	}

	public String getItemSuffix() {
		return itemSuffix;
	}

	public void setItemSuffix(String itemSuffix) {
		this.itemSuffix = itemSuffix;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getUsageType() {
		return usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

	public String getWhereBought() {
		return whereBought;
	}

	public void setWhereBought(String whereBought) {
		this.whereBought = whereBought;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getModelNum() {
		return modelNum;
	}

	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}

	public String getApplBrand() {
		return applBrand;
	}

	public void setApplBrand(String applBrand) {
		this.applBrand = applBrand;
	}



}
