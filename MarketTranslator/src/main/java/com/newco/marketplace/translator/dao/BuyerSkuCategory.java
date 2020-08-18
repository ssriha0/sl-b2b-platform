package com.newco.marketplace.translator.dao;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BuyerSkuCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer_sku_category", uniqueConstraints = {})
public class BuyerSkuCategory extends AbstractBuyerSkuCategory implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 3500595951013636470L;

	/** default constructor */
	public BuyerSkuCategory() {
	}

	/** minimal constructor */
	public BuyerSkuCategory(BuyerMT buyer, String categoryName,
			String categoryDescr, Timestamp createdDate, Timestamp modifiedDate) {
		super(buyer, categoryName, categoryDescr, createdDate, modifiedDate);
	}

	/** full constructor */
	public BuyerSkuCategory(BuyerMT buyer, String categoryName,
			String categoryDescr, Timestamp createdDate,
			Timestamp modifiedDate, Set<BuyerSku> buyerSkus, Integer categoryId) {
		super(buyer, categoryName, categoryDescr, createdDate, modifiedDate,
				buyerSkus, categoryId);
	}

}
