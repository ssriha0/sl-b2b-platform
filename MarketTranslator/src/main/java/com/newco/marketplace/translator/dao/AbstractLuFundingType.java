package com.newco.marketplace.translator.dao;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * AbstractLuFundingType entity provides the base persistence definition of the
 * LuFundingType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractLuFundingType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8179756425362349970L;
	private Integer fundingTypeId;
	private String type;
	private String descr;
	private Integer sortOrder;
	private Set<BuyerMT> buyers = new HashSet<BuyerMT>(0);

	// Constructors

	/** default constructor */
	public AbstractLuFundingType() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractLuFundingType(Integer fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

	/** full constructor */
	public AbstractLuFundingType(Integer fundingTypeId, String type,
			String descr, Integer sortOrder, Set<BuyerMT> buyers) {
		this.fundingTypeId = fundingTypeId;
		this.type = type;
		this.descr = descr;
		this.sortOrder = sortOrder;
		this.buyers = buyers;
	}

	// Property accessors
	@Id
	@Column(name = "funding_type_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getFundingTypeId() {
		return this.fundingTypeId;
	}

	public void setFundingTypeId(Integer fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "descr", unique = false, nullable = true, insertable = true, updatable = true)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "sort_order", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "luFundingType")
	public Set<BuyerMT> getBuyers() {
		return this.buyers;
	}

	public void setBuyers(Set<BuyerMT> buyers) {
		this.buyers = buyers;
	}

}