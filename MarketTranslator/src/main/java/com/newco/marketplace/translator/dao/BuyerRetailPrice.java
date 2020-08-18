package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BuyerRetailPrice entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer_retail_price",  uniqueConstraints = {})
public class BuyerRetailPrice extends AbstractBuyerRetailPrice implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public BuyerRetailPrice() {
		// intentionally blank
	}

	/** full constructor */
	public BuyerRetailPrice(Integer retailPriceId, BuyerMT buyer, String storeNo,
			String sku, Double retailPrice, Date createdDate,
			Date modifiedDate, String modifiedBy) {
		super(retailPriceId, buyer, storeNo, sku, retailPrice, createdDate,
				modifiedDate, modifiedBy);
	}

}
