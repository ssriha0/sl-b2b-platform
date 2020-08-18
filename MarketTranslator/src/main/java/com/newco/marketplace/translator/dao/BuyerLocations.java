package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BuyerLocations entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer_locations", uniqueConstraints = {})
public class BuyerLocations extends AbstractBuyerLocations implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public BuyerLocations() {
		// intentionally blank
	}

	/** minimal constructor */
	public BuyerLocations(BuyerLocationsId id, BuyerMT buyer) {
		super(id, buyer);
	}

	/** full constructor */
	public BuyerLocations(BuyerLocationsId id, BuyerMT buyer, Date createdDate,
			Date modifiedDate, String modifiedBy) {
		super(id, buyer, createdDate, modifiedDate, modifiedBy);
	}

}
