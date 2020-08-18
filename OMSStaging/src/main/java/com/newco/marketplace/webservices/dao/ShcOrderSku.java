package com.newco.marketplace.webservices.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ShcOrderSku entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shc_order_sku", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"sku", "add_on_ind", "shc_order_id" }) })
public class ShcOrderSku extends AbstractShcOrderSku implements
		java.io.Serializable {
	
	private static final long serialVersionUID = 8750518769602420697L;
	// Constructors
	
	/** default constructor */
	public ShcOrderSku() {
		// intentionally blank
	}

	/** minimal constructor */
	public ShcOrderSku(Integer shcOrderSkuId, String sku) {
		super(shcOrderSkuId, sku);
	}

	/** full constructor */
	public ShcOrderSku(Integer shcOrderSkuId, ShcOrder shcOrder, String sku,
			Short addOnInd, Double initialBidPrice, Double priceRatio, Double finalPrice,
			Double retailPrice, Double sellingPrice, Double serviceFee, String chargeCode,
			String coverage, String type, Short permitSkuInd, Date createdDate,
			Date modifiedDate, String modifiedBy, Integer qty, String description, String status) {
		super(shcOrderSkuId, shcOrder, sku, addOnInd, initialBidPrice, priceRatio,
				finalPrice, retailPrice, sellingPrice, serviceFee, chargeCode, coverage,
				type, permitSkuInd, createdDate, modifiedDate, modifiedBy, qty, description, status);
	}

}
