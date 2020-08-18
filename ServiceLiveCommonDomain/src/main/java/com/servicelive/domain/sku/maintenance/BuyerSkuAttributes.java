package com.servicelive.domain.sku.maintenance;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BuyerSkuAttributes entity. @author MyEclipse Persistence Tools
 */
@Entity(name="BuyerSkuAttributesEntity")
@Table(name = "buyer_sku_attributes", uniqueConstraints = {})
public class BuyerSkuAttributes extends AbstractBuyerSkuAttributes implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = -9128459542283796573L;

	/** default constructor */
	public BuyerSkuAttributes() {
	}

	/** full constructor */
	public BuyerSkuAttributes(BuyerSkuAttributesId id, BuyerSKUEntity buyerSku,
			Timestamp createdDate) {
		super(id, buyerSku, createdDate);
	}

}
