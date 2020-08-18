package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractBuyerRetailPrice entity provides the base persistence definition of
 * the BuyerRetailPrice entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerRetailPrice implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5181823906131581268L;
	private Integer retailPriceId;
	private BuyerMT buyer;
	private String storeNo;
	private String sku;
	private Double retailPrice;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractBuyerRetailPrice() {
		// intentionally blank
	}

	/** full constructor */
	public AbstractBuyerRetailPrice(Integer retailPriceId, BuyerMT buyer,
			String storeNo, String sku, Double retailPrice, Date createdDate,
			Date modifiedDate, String modifiedBy) {
		this.retailPriceId = retailPriceId;
		this.buyer = buyer;
		this.storeNo = storeNo;
		this.sku = sku;
		this.retailPrice = retailPrice;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@Id
	@Column(name = "retail_price_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getRetailPriceId() {
		return this.retailPriceId;
	}

	public void setRetailPriceId(Integer retailPriceId) {
		this.retailPriceId = retailPriceId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	public BuyerMT getBuyer() {
		return this.buyer;
	}

	public void setBuyer(BuyerMT buyer) {
		this.buyer = buyer;
	}

	@Column(name = "store_no", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getStoreNo() {
		return this.storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	@Column(name = "sku", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Column(name = "retail_price", unique = false, nullable = false, insertable = true, updatable = true, precision = 10)
	public Double getRetailPrice() {
		return this.retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}