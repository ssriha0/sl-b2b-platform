package com.servicelive.orderfulfillment.orderprep.buyersku;

import com.servicelive.domain.so.type.BidPriceSchema;

import java.math.BigDecimal;

public class BuyerSkuPricingItem {
    String specialtyCode;
    String sku;
    String storeNo;
    String serviceLocationZip;
    BigDecimal margin;
    BidPriceSchema bidPriceSchema;
    BigDecimal retailPrice;

    public BuyerSkuPricingItem(String sku, BidPriceSchema bidPriceSchema) {
        this.sku = sku;
        this.bidPriceSchema = bidPriceSchema;
    }

    public BuyerSkuPricingItem(String specialtyCode, String sku, String storeNo, String serviceLocationZip, Double margin) {
        this.specialtyCode = specialtyCode;
        this.sku = sku;
        this.storeNo = storeNo;
        this.serviceLocationZip = serviceLocationZip;
        this.margin = BigDecimal.valueOf(margin).setScale(4);
    }

    public BuyerSkuPricingItem(String externalSku) {
		this.sku = externalSku;
	}

	public String getSpecialtyCode() {
        return specialtyCode;
    }

    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getServiceLocationZip() {
        return serviceLocationZip;
    }

    public void setServiceLocationZip(String serviceLocationZip) {
        this.serviceLocationZip = serviceLocationZip;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BidPriceSchema getBidPriceSchema() {
        return bidPriceSchema;
    }

    public void setBidPriceSchema(BidPriceSchema bidPriceSchema) {
        this.bidPriceSchema = bidPriceSchema;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }
}
