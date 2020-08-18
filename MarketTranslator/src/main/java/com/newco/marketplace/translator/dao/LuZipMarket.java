package com.newco.marketplace.translator.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LuZipMarket entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "lu_zip_market",  uniqueConstraints = {})
public class LuZipMarket extends AbstractLuZipMarket implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public LuZipMarket() {
		// intentionally blank
	}

	/** full constructor */
	public LuZipMarket(String zip, Integer marketId) {
		super(zip, marketId);
	}

}
