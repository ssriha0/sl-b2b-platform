package com.newco.marketplace.webservices.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.newco.marketplace.webservices.dao.SpecialtyAddOn;

/**
 * ShcOrderAddOn entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shc_order_add_on", uniqueConstraints = {})
public class ShcOrderAddOn extends AbstractShcOrderAddOn implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public ShcOrderAddOn() {
		// intentionally blank
	}

	/** minimal constructor */
	public ShcOrderAddOn(Integer shcOrderAddOnId) {
		super(shcOrderAddOnId);
	}

	/** full constructor */
	public ShcOrderAddOn(Integer shcOrderAddOnId,
			SpecialtyAddOn specialtyAddOn, ShcOrder shcOrder,
			Double retailPrice, Double margin, Integer qty, Integer miscInd,
			Date createdDate, Date modifiedDate, String modifiedBy) {
		super(shcOrderAddOnId, specialtyAddOn, shcOrder, retailPrice, margin,
				qty, miscInd, createdDate, modifiedDate, modifiedBy);
	}

}
