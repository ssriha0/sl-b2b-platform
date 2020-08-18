package com.newco.marketplace.webservices.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractShcOrderSku entity provides the base persistence definition of the
 * ShcOrderSku entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractShcOrderSku implements java.io.Serializable {

	// Fields

	private Integer shcOrderSkuId;
	private ShcOrder shcOrder;
	private String sku;
	private Short addOnInd;
	private Double initialBidPrice;
	private Double priceRatio;
	private Double finalPrice;
	private Double retailPrice;
	private Double sellingPrice;
	private Double serviceFee;
	private String chargeCode;
	private String coverage;
	private String type;
	private Short permitSkuInd;
	private String modifiedBy;
	private Integer qty;
	private String description;
	private String status;

	// Constructors

	/** default constructor */
	public AbstractShcOrderSku() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractShcOrderSku(Integer shcOrderSkuId, String sku) {
		this.shcOrderSkuId = shcOrderSkuId;
		this.sku = sku;
	}

	/** full constructor */
	public AbstractShcOrderSku(Integer shcOrderSkuId, ShcOrder shcOrder,
			String sku, Short addOnInd, Double initialBidPrice, Double priceRatio,
			Double finalPrice, Double retailPrice, Double sellingPrice, Double serviceFee,
			String chargeCode, String coverage, String type,
			Short permitSkuInd, Date createdDate, Date modifiedDate,
			String modifiedBy, Integer qty, String description, String status) {
		this.shcOrderSkuId = shcOrderSkuId;
		this.shcOrder = shcOrder;
		this.sku = sku;
		this.addOnInd = addOnInd;
		this.initialBidPrice = initialBidPrice;
		this.priceRatio = priceRatio;
		this.finalPrice = finalPrice;
		this.retailPrice = retailPrice;
		this.sellingPrice = sellingPrice;
		this.serviceFee = serviceFee;
		this.chargeCode = chargeCode;
		this.coverage = coverage;
		this.type = type;
		this.permitSkuInd = permitSkuInd;
		this.modifiedBy = modifiedBy;
		this.qty = qty;
		this.description = description;
		this.status = status;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "shc_order_sku_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getShcOrderSkuId() {
		return this.shcOrderSkuId;
	}

	public void setShcOrderSkuId(Integer shcOrderSkuId) {
		this.shcOrderSkuId = shcOrderSkuId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "shc_order_id", unique = false, nullable = true, insertable = true, updatable = true)
	public ShcOrder getShcOrder() {
		return this.shcOrder;
	}

	public void setShcOrder(ShcOrder shcOrder) {
		this.shcOrder = shcOrder;
	}

	@Column(name = "sku", unique = false, nullable = false, insertable = true, updatable = true)
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	/**
	 * @return the addOnInd
	 */
	@Column(name = "add_on_ind", unique = false, nullable = false, insertable = true, updatable = true)
	public Short getAddOnInd() {
		return addOnInd;
	}

	/**
	 * @param addOnInd the addOnInd to set
	 */
	public void setAddOnInd(Short addOnInd) {
		this.addOnInd = addOnInd;
	}


	@Column(name = "initial_bid_price", unique = false, nullable = true, insertable = true, updatable = true, precision = 9)
	public Double getInitialBidPrice() {
		return this.initialBidPrice;
	}

	public void setInitialBidPrice(Double initialBidPrice) {
		this.initialBidPrice = initialBidPrice;
	}

	@Column(name = "price_ratio", unique = false, nullable = true, insertable = true, updatable = true, precision = 9)
	public Double getPriceRatio() {
		return this.priceRatio;
	}

	public void setPriceRatio(Double priceRatio) {
		this.priceRatio = priceRatio;
	}

	@Column(name = "final_price", unique = false, nullable = true, insertable = true, updatable = true, precision = 9)
	public Double getFinalPrice() {
		return this.finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	@Column(name = "retail_price", unique = false, nullable = true, insertable = true, updatable = true, precision = 9)
	public Double getRetailPrice() {
		return this.retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	@Column(name = "selling_price", unique = false, nullable = true, insertable = true, updatable = true, precision = 9)
	public Double getSellingPrice() {
		return this.sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	
	/**
	 * @return the serviceFee
	 */
	@Column(name = "service_fee", unique = false, nullable = true, insertable = true, updatable = true, precision = 9)
	public Double getServiceFee() {
		return serviceFee;
	}

	/**
	 * @param serviceFee the serviceFee to set
	 */
	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	@Column(name = "charge_code", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getChargeCode() {
		return this.chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	@Column(name = "coverage", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getCoverage() {
		return this.coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "permit_sku_ind", unique = false, nullable = true, insertable = true, updatable = true)
	public Short getPermitSkuInd() {
		return this.permitSkuInd;
	}

	public void setPermitSkuInd(Short permitSkuInd) {
		this.permitSkuInd = permitSkuInd;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name = "qty", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "description", unique = false, insertable = true, updatable = true)
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}