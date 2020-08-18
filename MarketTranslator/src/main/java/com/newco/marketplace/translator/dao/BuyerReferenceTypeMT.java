package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BuyerReferenceType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer_reference_type",  uniqueConstraints = {})
public class BuyerReferenceTypeMT extends AbstractBuyerReferenceType implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public BuyerReferenceTypeMT() {
		// intentionally blank
	}

	/** minimal constructor */
	public BuyerReferenceTypeMT(Integer buyerRefTypeId, BuyerMT buyer,
			String refType) {
		super(buyerRefTypeId, buyer, refType);
	}

	/** full constructor */
	public BuyerReferenceTypeMT(Integer buyerRefTypeId, BuyerMT buyer,
			String refType, String refDescr, Date createdDate,
			Date modifiedDate, String modifiedBy, Integer sortOrder,
			Byte activeInd) {
		super(buyerRefTypeId, buyer, refType, refDescr, createdDate,
				modifiedDate, modifiedBy, sortOrder, activeInd);
	}

}
