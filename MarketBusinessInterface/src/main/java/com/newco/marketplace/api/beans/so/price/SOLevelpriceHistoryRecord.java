package com.newco.marketplace.api.beans.so.price;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("orderPriceHistoryRecord")
public class SOLevelpriceHistoryRecord {
	
	@XStreamAlias("laborPriceChange")
	private BigDecimal laborPriceChange;
	
	@XStreamAlias("partPriceChange")
	private BigDecimal partPriceChange;
	
	@XStreamAlias("permitPriceChange")
	private BigDecimal permitPriceChange;
	
	@XStreamAlias("addonPriceChange")
	private BigDecimal addonPriceChange;
	
	@XStreamAlias("invoicePartPriceChange")
	private BigDecimal invoicePartPriceChange;
	
	@XStreamAlias("totalPrice")
	private BigDecimal totalPrice;
	
	@XStreamAlias("action")
	private String action;
	
	@XStreamAlias("reasonCode")
	private String reasonCode;
	
	@XStreamAlias("changedDate")
	private String changedDate;
	
	@XStreamAlias("changedByUserName")
	private String changedByUserName;
	
	@XStreamAlias("changedByUserId")
	private String changedByUserId;
	

	public BigDecimal getLaborPriceChange() {
		return laborPriceChange;
	}

	public void setLaborPriceChange(BigDecimal laborPriceChange) {
		this.laborPriceChange = laborPriceChange;
	}

	public BigDecimal getPartPriceChange() {
		return partPriceChange;
	}

	public void setPartPriceChange(BigDecimal partPriceChange) {
		this.partPriceChange = partPriceChange;
	}

	public BigDecimal getPermitPriceChange() {
		return permitPriceChange;
	}

	public void setPermitPriceChange(BigDecimal permitPriceChange) {
		this.permitPriceChange = permitPriceChange;
	}

	public BigDecimal getAddonPriceChange() {
		return addonPriceChange;
	}

	public void setAddonPriceChange(BigDecimal addonPriceChange) {
		this.addonPriceChange = addonPriceChange;
	}

	public BigDecimal getInvoicePartPriceChange() {
		return invoicePartPriceChange;
	}

	public void setInvoicePartPriceChange(BigDecimal invoicePartPriceChange) {
		this.invoicePartPriceChange = invoicePartPriceChange;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(String changedDate) {
		this.changedDate = changedDate;
	}

	public String getChangedByUserName() {
		return changedByUserName;
	}

	public void setChangedByUserName(String changedByUserName) {
		this.changedByUserName = changedByUserName;
	}

	public String getChangedByUserId() {
		return changedByUserId;
	}

	public void setChangedByUserId(String changedByUserId) {
		this.changedByUserId = changedByUserId;
	}
}
