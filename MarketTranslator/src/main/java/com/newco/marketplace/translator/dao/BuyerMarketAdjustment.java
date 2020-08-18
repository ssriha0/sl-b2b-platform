package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BuyerMarketAdjustment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer_market_adjustment", uniqueConstraints = {})
public class BuyerMarketAdjustment extends AbstractBuyerMarketAdjustment
		implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public BuyerMarketAdjustment() {
		// intentionally blank
	}

	/** minimal constructor */
	public BuyerMarketAdjustment(Integer marketAdjustmentId, BuyerMT buyer,
			LuMarket luMarket, Double adjustment) {
		super(marketAdjustmentId, buyer, luMarket, adjustment);
	}

	/** full constructor */
	public BuyerMarketAdjustment(Integer marketAdjustmentId, BuyerMT buyer,
			LuMarket luMarket, Double adjustment, Date createdDate,
			Date modifiedDate, String modifiedBy) {
		super(marketAdjustmentId, buyer, luMarket, adjustment, createdDate,
				modifiedDate, modifiedBy);
	}

}
