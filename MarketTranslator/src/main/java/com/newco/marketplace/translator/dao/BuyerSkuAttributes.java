package com.newco.marketplace.translator.dao;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BuyerSkuAttributes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer_sku_attributes", uniqueConstraints = {})
public class BuyerSkuAttributes extends AbstractBuyerSkuAttributes implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = -9128459542583796573L;

	/** default constructor */
	public BuyerSkuAttributes() {
	}

	/** full constructor */
	public BuyerSkuAttributes(BuyerSkuAttributesId id, BuyerSku buyerSku,
			Timestamp createdDate) {
		super(id, buyerSku, createdDate);
	}

}
