package com.servicelive.domain.so;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "buyer_retail_price")
public class BuyerOrderRetailPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="retail_price_id")
    private Long retailPriceId;

    @Column(name="buyer_id")
    private Long buyerId;

    @Column(name="store_no")
    private String storeNumberString;

    @Column(name="sku")
    private String sku;

    @Column(name="retail_price")
    private BigDecimal retailPrice;

    public Long getRetailPriceId() {
        return retailPriceId;
    }

    public void setRetailPriceId(Long retailPriceId) {
        this.retailPriceId = retailPriceId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getStoreNumberString() {
        return storeNumberString;
    }

    public void setStoreNumberString(String storeNumberString) {
        this.storeNumberString = storeNumberString;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }
}
