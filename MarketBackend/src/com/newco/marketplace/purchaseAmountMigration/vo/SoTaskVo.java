package com.newco.marketplace.purchaseAmountMigration.vo;

import java.math.BigDecimal;

public class SoTaskVo {
	private String soTaskId;
	private String sku;
	private BigDecimal purchaseAmount;
	
	public String getSoTaskId() {
		return soTaskId;
	}
	public void setSoTaskId(String soTaskId) {
		this.soTaskId = soTaskId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

}
