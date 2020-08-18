package com.newco.marketplace.translator.dao;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LuFundingType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "lu_funding_type",  uniqueConstraints = {})
public class LuFundingType extends AbstractLuFundingType implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public LuFundingType() {
		// intentionally blank
	}

	/** minimal constructor */
	public LuFundingType(Integer fundingTypeId) {
		super(fundingTypeId);
	}

	/** full constructor */
	public LuFundingType(Integer fundingTypeId, String type, String descr,
			Integer sortOrder, Set<BuyerMT> buyers) {
		super(fundingTypeId, type, descr, sortOrder, buyers);
	}

}
