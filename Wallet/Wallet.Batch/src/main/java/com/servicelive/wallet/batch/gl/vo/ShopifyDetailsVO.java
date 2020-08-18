package com.servicelive.wallet.batch.gl.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


// TODO: Auto-generated Javadoc
/**
 * The Class ShopifyDetailsVO to hold the details of buyer 9000 order.
 */
public class ShopifyDetailsVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8554798475137419492L;

	private String category;
	private String soId;
	private String shopifyOrderNumber;
	private String price1;
	private String price2;
	private String price3;
	private String price4;
	private Date createdDate;
	private String status;
	
	//Fields added for Relay Buyer Custom GL
	private String relayOrderNumber;
	private String relayTotalPrice;
	private String relayTotalTax;
	private String spendLimitLabor;
	private String spendLimitParts;
	private String finalLaborPrice;
	private String finalPartsPrice;
	private String finalAddonPrice;
	
	private String shopifyTransId;
	private String ledgerTransId;
	
	private BigDecimal taxPercentParts;
	private BigDecimal taxPercentLabor;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getShopifyOrderNumber() {
		return shopifyOrderNumber;
	}
	public void setShopifyOrderNumber(String shopifyOrderNumber) {
		this.shopifyOrderNumber = shopifyOrderNumber;
	}
	public String getPrice1() {
		return price1;
	}
	public void setPrice1(String price1) {
		this.price1 = price1;
	}
	public String getPrice2() {
		return price2;
	}
	public void setPrice2(String price2) {
		this.price2 = price2;
	}
	public String getPrice3() {
		return price3;
	}
	public void setPrice3(String price3) {
		this.price3 = price3;
	}
	public String getPrice4() {
		return price4;
	}
	public void setPrice4(String price4) {
		this.price4 = price4;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRelayOrderNumber() {
		return relayOrderNumber;
	}

	public void setRelayOrderNumber(String relayOrderNumber) {
		this.relayOrderNumber = relayOrderNumber;
	}

	public String getRelayTotalPrice() {
		return relayTotalPrice;
	}

	public void setRelayTotalPrice(String relayTotalPrice) {
		this.relayTotalPrice = relayTotalPrice;
	}

	public String getRelayTotalTax() {
		return relayTotalTax;
	}

	public void setRelayTotalTax(String relayTotalTax) {
		this.relayTotalTax = relayTotalTax;
	}
	public String getSpendLimitLabor() {
		return spendLimitLabor;
	}

	public void setSpendLimitLabor(String spendLimitLabor) {
		this.spendLimitLabor = spendLimitLabor;
	}

	public String getSpendLimitParts() {
		return spendLimitParts;
	}

	public void setSpendLimitParts(String spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}

	public String getFinalLaborPrice() {
		return finalLaborPrice;
	}

	public void setFinalLaborPrice(String finalLaborPrice) {
		this.finalLaborPrice = finalLaborPrice;
	}

	public String getFinalPartsPrice() {
		return finalPartsPrice;	
	}
	
	public void setFinalPartsPrice(String finalPartsPrice) {
		this.finalPartsPrice = finalPartsPrice;
	}
	
	public String getFinalAddonPrice() {
		return finalAddonPrice;
	}
	
	public void setFinalAddonPrice(String finalAddonPrice) {
		this.finalAddonPrice = finalAddonPrice;
	}
	
	public String getShopifyTransId() {
		return shopifyTransId;
	}
	public void setShopifyTransId(String shopifyTransId) {
		this.shopifyTransId = shopifyTransId;
	}
	public String getLedgerTransId() {
		return ledgerTransId;
	}
	public void setLedgerTransId(String ledgerTransId) {
		this.ledgerTransId = ledgerTransId;
	}
	public BigDecimal getTaxPercentParts() {
		return taxPercentParts;
	}
	public void setTaxPercentParts(BigDecimal taxPercentParts) {
		this.taxPercentParts = taxPercentParts;
	}
	public BigDecimal getTaxPercentLabor() {
		return taxPercentLabor;
	}
	public void setTaxPercentLabor(BigDecimal taxPercentLabor) {
		this.taxPercentLabor = taxPercentLabor;
	}
	
	
}
