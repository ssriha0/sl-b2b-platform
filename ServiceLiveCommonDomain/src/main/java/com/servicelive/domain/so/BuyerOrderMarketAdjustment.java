package com.servicelive.domain.so;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "buyer_market_adjustment")
public class BuyerOrderMarketAdjustment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="market_adjustment_id")
    private Long marketAdjustmentId;

    @Column(name="buyer_id")
    private Long buyerId;

    @Column(name="market_id")
    private Integer marketId;

    @Column(name="adjustment")
    private BigDecimal adjustment;

    public Long getMarketAdjustmentId() {
        return marketAdjustmentId;
    }

    public void setMarketAdjustmentId(Long marketAdjustmentId) {
        this.marketAdjustmentId = marketAdjustmentId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public BigDecimal getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(BigDecimal adjustment) {
        this.adjustment = adjustment;
    }
}
