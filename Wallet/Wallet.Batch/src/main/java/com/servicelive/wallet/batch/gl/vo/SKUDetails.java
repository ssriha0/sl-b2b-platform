package com.servicelive.wallet.batch.gl.vo;


import java.io.Serializable;
import java.math.BigDecimal;


// TODO: Auto-generated Javadoc
/**
 * The Class SKUDetails.
 */
public class SKUDetails implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8554798475137419492L;

	private String skuId;
	private BigDecimal skuPrice;
	private BigDecimal taxPercentage;
	private String taxType;
	
	//Constructor
	public SKUDetails(String skuId, BigDecimal skuPrice, 
			BigDecimal taxPercentage, String taxType){
		this.skuId = skuId;
		this.skuPrice = skuPrice;
		this.taxPercentage = taxPercentage;
		this.taxType = taxType;
	}
	
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public BigDecimal getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}
	public BigDecimal getTaxPercentage() {
		return taxPercentage;
	}
	public void setTaxPercentage(BigDecimal taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}


}
