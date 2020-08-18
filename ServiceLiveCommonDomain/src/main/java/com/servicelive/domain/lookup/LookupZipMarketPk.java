package com.servicelive.domain.lookup;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * LuZipMarket entity.
 * 
 */
public class LookupZipMarketPk implements Serializable {
	
	private static final long serialVersionUID = 20090828L;

	@ManyToOne
	@JoinColumn(name="zip")
	private LookupZipGeocode zipGeocode;

	@ManyToOne
	@JoinColumn(name="market_id", unique=true, nullable=false)
	private LookupMarket market;

	/**
	 * @return the zipGeocode
	 */
	public LookupZipGeocode getZipGeocode() {
		return zipGeocode;
	}

	/**
	 * @param zipGeocode the zipGeocode to set
	 */
	public void setZipGeocode(LookupZipGeocode zipGeocode) {
		this.zipGeocode = zipGeocode;
	}

	/**
	 * @return the market
	 */
	public LookupMarket getMarket() {
		return market;
	}

	/**
	 * @param market the market to set
	 */
	public void setMarket(LookupMarket market) {
		this.market = market;
	}
	
	
}
