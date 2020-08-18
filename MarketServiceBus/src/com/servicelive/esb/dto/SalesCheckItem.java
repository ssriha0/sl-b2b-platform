package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Item")
public class SalesCheckItem  implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 8653177417669832241L;

	@XStreamAlias("LineNumber")
	private String lineNumber;
	
	@XStreamAlias("Division")
	private String division;
	
	@XStreamAlias("ItemNumber")
	private String itemNumber;
	
	@XStreamAlias("SKU")
	private String sku;
	
	@XStreamAlias("PurchaseAmt")
	private String purchaseAmt;
	
	@XStreamAlias("Description")
	private String description;
	
	@XStreamAlias("Quantity")
	private String quantity;
	
	@XStreamAlias("GiftFlag")
	private String giftFlag;
	
	@XStreamAlias("GiftDate")
	private String giftDate;
	
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

	public String getGiftDate() {
		return giftDate;
	}

	public void setGiftDate(String giftDate) {
		this.giftDate = giftDate;
	}

	public String getGiftFlag() {
		return giftFlag;
	}

	public void setGiftFlag(String giftFlag) {
		this.giftFlag = giftFlag;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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
	
}
