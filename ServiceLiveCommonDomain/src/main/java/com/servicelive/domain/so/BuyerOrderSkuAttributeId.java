package com.servicelive.domain.so;

import com.servicelive.domain.so.type.BuyerSkuAttributeType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BuyerOrderSkuAttributeId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="sku_id")
    private Long skuId;

    @Column(name="attribute_type", nullable=false)
    private BuyerSkuAttributeType skuAttributeType;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public BuyerSkuAttributeType getSkuAttributeType() {
        return skuAttributeType;
    }

    public void setSkuAttributeType(BuyerSkuAttributeType skuAttributeType) {
        this.skuAttributeType = skuAttributeType;
    }


    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object aThat){
        if ( this == aThat ) return true;
        if ( !(aThat instanceof BuyerOrderSkuAttributeId) ) return false;

        BuyerOrderSkuAttributeId o = (BuyerOrderSkuAttributeId)aThat;
        return EqualsBuilder.reflectionEquals(this, o);
    }
}
