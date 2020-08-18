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
 * AbstractBuyerMarketAdjustment entity provides the base persistence definition
 * of the BuyerMarketAdjustment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerMarketAdjustment implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2454865498439634579L;
	private Integer marketAdjustmentId;
	private BuyerMT buyer;
	private LuMarket luMarket;
	private Double adjustment;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractBuyerMarketAdjustment() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractBuyerMarketAdjustment(Integer marketAdjustmentId,
			BuyerMT buyer, LuMarket luMarket, Double adjustment) {
		this.marketAdjustmentId = marketAdjustmentId;
		this.buyer = buyer;
		this.luMarket = luMarket;
		this.adjustment = adjustment;
	}

	/** full constructor */
	public AbstractBuyerMarketAdjustment(Integer marketAdjustmentId,
			BuyerMT buyer, LuMarket luMarket, Double adjustment,
			Date createdDate, Date modifiedDate, String modifiedBy) {
		this.marketAdjustmentId = marketAdjustmentId;
		this.buyer = buyer;
		this.luMarket = luMarket;
		this.adjustment = adjustment;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@Id
	@Column(name = "market_adjustment_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getMarketAdjustmentId() {
		return this.marketAdjustmentId;
	}

	public void setMarketAdjustmentId(Integer marketAdjustmentId) {
		this.marketAdjustmentId = marketAdjustmentId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	public BuyerMT getBuyer() {
		return this.buyer;
	}

	public void setBuyer(BuyerMT buyer) {
		this.buyer = buyer;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "market_id", unique = false, nullable = false, insertable = true, updatable = true)
	public LuMarket getLuMarket() {
		return this.luMarket;
	}

	public void setLuMarket(LuMarket luMarket) {
		this.luMarket = luMarket;
	}

	@Column(name = "adjustment", unique = false, nullable = false, insertable = true, updatable = true, precision = 5)
	public Double getAdjustment() {
		return this.adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}