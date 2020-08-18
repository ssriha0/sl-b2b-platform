package com.servicelive.domain.sku.maintenance;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.servicelive.domain.buyer.Buyer;

/**
 * BuyerSku entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer_sku", uniqueConstraints = {})
public class BuyerSKUEntity extends AbstractBuyerSKUEntity implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 7713561600828181684L;

	/** default constructor */
	public BuyerSKUEntity() {
	}

	/** minimal constructor */
	public BuyerSKUEntity(Buyer buyer, String sku, String priceType,
			Double retailPrice, Double bidPrice, Double billingPrice,
			Double bidMargin, Double billingMargin, Timestamp createdDate,
			String orderitemType) {
		super(buyer, sku, priceType, retailPrice, bidPrice, billingPrice,
				bidMargin, billingMargin, createdDate, orderitemType);
	}

	/** full constructor */
	public BuyerSKUEntity(BuyerSkuCategory buyerSkuCategory,
			BuyerSoTemplate buyerSoTemplate, Buyer buyer, String sku,
			String priceType, Double retailPrice, Double bidPrice,
			Double billingPrice, Double bidMargin, Double billingMargin,
			String bidPriceSchema, String billingPriceSchema,
			String skuDescription, Timestamp createdDate, String modifiedBy,
			Timestamp modifiedDate, String orderitemType,
			Set<BuyerSkuTask> buyerSkuTasks,
			Set<BuyerSkuAttributes> buyerSkuAttributeses,
			Boolean manageScopeInd) {
		super(buyerSkuCategory, buyerSoTemplate, buyer, sku, priceType,
				retailPrice, bidPrice, billingPrice, bidMargin, billingMargin,
				bidPriceSchema, billingPriceSchema, skuDescription,
				createdDate, modifiedBy, modifiedDate, orderitemType,
				buyerSkuTasks, buyerSkuAttributeses,manageScopeInd);
	}

}
