package com.servicelive.domain.lookup;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LuZipMarket entity.
 * 
 */
@Entity
@Table(name = "lu_zip_market",  uniqueConstraints = {})
public class LookupZipMarket implements Serializable {
	
	private static final long serialVersionUID = 20090828L;

	@EmbeddedId
	private LookupZipMarketPk lookupZipMarketPk;

	/**
	 * @return the lookupZipMarketPk
	 */
	public LookupZipMarketPk getLookupZipMarketPk() {
		return lookupZipMarketPk;
	}

	/**
	 * @param lookupZipMarketPk the lookupZipMarketPk to set
	 */
	public void setLookupZipMarketPk(LookupZipMarketPk lookupZipMarketPk) {
		this.lookupZipMarketPk = lookupZipMarketPk;
	}
	
}
