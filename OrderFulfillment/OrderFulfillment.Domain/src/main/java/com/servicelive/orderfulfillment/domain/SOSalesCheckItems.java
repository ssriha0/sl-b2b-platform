package com.servicelive.orderfulfillment.domain;

public class SOSalesCheckItems {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3313785717727460939L;
	

	private String itemId;		
	private String serviceOrderId;
	private String lineNumber;
	private String division;	
	private String itemNumber;	
	private String sku;	
	private String purchaseAmt;	
	private String description;	
	private String quantity ;
	private String giftFlag;	
	private String giftDate;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getServiceOrderId() {
		return serviceOrderId;
	}
	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
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
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getPurchaseAmt() {
		return purchaseAmt;
	}
	public void setPurchaseAmt(String purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getGiftFlag() {
		return giftFlag;
	}
	public void setGiftFlag(String giftFlag) {
		this.giftFlag = giftFlag;
	}
	public String getGiftDate() {
		return giftDate;
	}
	public void setGiftDate(String giftDate) {
		this.giftDate = giftDate;
	}
	


}
