package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LuMarket entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "lu_market",  uniqueConstraints = {})
public class LuMarket extends AbstractLuMarket implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public LuMarket() {
		// intentionally blank
	}

	/** minimal constructor */
	public LuMarket(Integer marketId, String marketName) {
		super(marketId, marketName);
	}

	/** full constructor */
	public LuMarket(Integer marketId, String marketName, Date createdDate,
			Date modifiedDate, String modifiedBy,
			Set<BuyerMarketAdjustment> buyerMarketAdjustments) {
		super(marketId, marketName, createdDate, modifiedDate, modifiedBy,
				buyerMarketAdjustments);
	}

}
