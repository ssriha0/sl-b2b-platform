package com.servicelive.domain.so;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "buyer_sku_attributes")
public class BuyerOrderSkuAttribute {
    @EmbeddedId
    private BuyerOrderSkuAttributeId attributeId;

    public BuyerOrderSkuAttributeId getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(BuyerOrderSkuAttributeId attributeId) {
        this.attributeId = attributeId;
    }
}
