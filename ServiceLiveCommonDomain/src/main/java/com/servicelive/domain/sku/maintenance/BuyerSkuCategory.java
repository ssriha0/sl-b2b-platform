package com.servicelive.domain.sku.maintenance;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.servicelive.domain.buyer.Buyer;

/**
 * BuyerSkuCategory entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "BuyerSkuCategoryEntity")
@Table(name = "buyer_sku_category", uniqueConstraints = {})
public class BuyerSkuCategory extends AbstractBuyerSkuCategory implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 350059591013636470L;

	/** default constructor */
	public BuyerSkuCategory() {
	}

	/** minimal constructor */
	public BuyerSkuCategory(Buyer buyer, String categoryName,
			String categoryDescr, Timestamp createdDate, Timestamp modifiedDate) {
		super(buyer, categoryName, categoryDescr, createdDate, modifiedDate);
	}

	/** full constructor */
	public BuyerSkuCategory(Buyer buyer, String categoryName,
			String categoryDescr, Timestamp createdDate,
			Timestamp modifiedDate, Set<BuyerSKUEntity> buyerSkus, Integer categoryId, Set<SkuCategoryHistoy> skuCategoryHistory) {
		super(buyer, categoryName, categoryDescr, createdDate, modifiedDate,
				buyerSkus, categoryId, skuCategoryHistory);
	}

}
