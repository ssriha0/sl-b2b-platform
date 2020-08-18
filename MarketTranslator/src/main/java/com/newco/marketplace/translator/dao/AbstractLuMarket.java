package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractLuMarket entity provides the base persistence definition of the
 * LuMarket entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractLuMarket implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6531956626769975354L;
	private Integer marketId;
	private String marketName;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private Set<BuyerMarketAdjustment> buyerMarketAdjustments = new HashSet<BuyerMarketAdjustment>(
			0);

	// Constructors

	/** default constructor */
	public AbstractLuMarket() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractLuMarket(Integer marketId, String marketName) {
		this.marketId = marketId;
		this.marketName = marketName;
	}

	/** full constructor */
	public AbstractLuMarket(Integer marketId, String marketName,
			Date createdDate, Date modifiedDate, String modifiedBy,
			Set<BuyerMarketAdjustment> buyerMarketAdjustments) {
		this.marketId = marketId;
		this.marketName = marketName;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.buyerMarketAdjustments = buyerMarketAdjustments;
	}

	// Property accessors
	@Id
	@Column(name = "market_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getMarketId() {
		return this.marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	@Column(name = "market_name", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public String getMarketName() {
		return this.marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
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

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "luMarket")
	public Set<BuyerMarketAdjustment> getBuyerMarketAdjustments() {
		return this.buyerMarketAdjustments;
	}

	public void setBuyerMarketAdjustments(
			Set<BuyerMarketAdjustment> buyerMarketAdjustments) {
		this.buyerMarketAdjustments = buyerMarketAdjustments;
	}

}