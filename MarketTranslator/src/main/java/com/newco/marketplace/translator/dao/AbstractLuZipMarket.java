package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractLuZipMarket entity provides the base persistence definition of the
 * LuZipMarket entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractLuZipMarket implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3439219099336405323L;
	private String zip;
	private Integer marketId;

	// Constructors

	/** default constructor */
	public AbstractLuZipMarket() {
		// intentionally blank
	}

	/** full constructor */
	public AbstractLuZipMarket(String zip, Integer marketId) {
		this.zip = zip;
		this.marketId = marketId;
	}

	// Property accessors
	@Id
	@Column(name = "zip", unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "market_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getMarketId() {
		return this.marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

}