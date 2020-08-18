package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BuyerLocationsId entity provides the base persistence definition of the
 * ationsId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class BuyerLocationsId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer buyerId;
	private Integer locnId;

	// Constructors

	/** default constructor */
	public BuyerLocationsId() {
		// intentionally blank
	}

	/** full constructor */
	public BuyerLocationsId(Integer buyerId, Integer locnId) {
		this.buyerId = buyerId;
		this.locnId = locnId;
	}

	// Property accessors

	@Column(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getBuyerId() {
		return this.buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	@Column(name = "locn_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getLocnId() {
		return this.locnId;
	}

	public void setLocnId(Integer locnId) {
		this.locnId = locnId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BuyerLocationsId))
			return false;
		BuyerLocationsId castOther = (BuyerLocationsId) other;

		return ((this.getBuyerId() == castOther.getBuyerId()) || (this
				.getBuyerId() != null
				&& castOther.getBuyerId() != null && this.getBuyerId().equals(
				castOther.getBuyerId())))
				&& ((this.getLocnId() == castOther.getLocnId()) || (this
						.getLocnId() != null
						&& castOther.getLocnId() != null && this.getLocnId()
						.equals(castOther.getLocnId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getBuyerId() == null ? 0 : this.getBuyerId().hashCode());
		result = 37 * result
				+ (getLocnId() == null ? 0 : this.getLocnId().hashCode());
		return result;
	}

}