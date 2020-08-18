package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractBuyerLocations entity provides the base persistence definition of the
 * BuyerLocations entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerLocations implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 799139696397327958L;
	private BuyerLocationsId id;
	private BuyerMT buyer;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractBuyerLocations() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractBuyerLocations(BuyerLocationsId id, BuyerMT buyer) {
		this.id = id;
		this.buyer = buyer;
	}

	/** full constructor */
	public AbstractBuyerLocations(BuyerLocationsId id, BuyerMT buyer,
			Date createdDate, Date modifiedDate, String modifiedBy) {
		this.id = id;
		this.buyer = buyer;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "buyerId", column = @Column(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)),
			@AttributeOverride(name = "locnId", column = @Column(name = "locn_id", unique = false, nullable = false, insertable = true, updatable = true)) })
	public BuyerLocationsId getId() {
		return this.id;
	}

	public void setId(BuyerLocationsId id) {
		this.id = id;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", unique = false, nullable = false, insertable = false, updatable = false)
	public BuyerMT getBuyer() {
		return this.buyer;
	}

	public void setBuyer(BuyerMT buyer) {
		this.buyer = buyer;
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