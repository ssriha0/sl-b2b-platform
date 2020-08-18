package com.newco.marketplace.webservices.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ShcSkuAccountAssoc entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shc_sku_account_assoc", uniqueConstraints = {})
public class ShcSkuAccountAssoc extends AbstractShcSkuAccountAssoc implements
		java.io.Serializable {
	private static final long serialVersionUID = 8042748210758862437L;

	// Constructors
	
	/** default constructor */
	public ShcSkuAccountAssoc() {
		// intentionally blank
	}

	/** minimal constructor */
	public ShcSkuAccountAssoc(String sku) {
		super(sku);
	}

	/** full constructor */
	public ShcSkuAccountAssoc(String sku, Integer glAccount, String glDivision,
			String glCategory, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		super(sku, glAccount, glDivision, glCategory, createdDate,
				modifiedDate, modifiedBy);
	}

}
