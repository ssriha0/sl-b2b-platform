package com.newco.marketplace.webservices.dao;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ShcOrderTransaction entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shc_order_transaction", uniqueConstraints = {})
public class ShcOrderTransaction extends AbstractShcOrderTransaction implements
		java.io.Serializable {
	
	private static final long serialVersionUID = -5027969125567534746L;
	
	// Constructors

	/** default constructor */
	public ShcOrderTransaction() {
		// intentionally blank
	}

	/** minimal constructor */
	public ShcOrderTransaction(Integer shcOrderTransactionId) {
		super(shcOrderTransactionId);
	}

	/** full constructor */
	public ShcOrderTransaction(Integer shcOrderTransactionId,
			ShcOrder shcOrder, String transactionType, String xmlFragment,
			String inputFileName, Date createdDate, Date modifiedDate,
			String modifiedBy, Set<ShcErrorLogging> shcErrorLoggings) {
		super(shcOrderTransactionId, shcOrder, transactionType, xmlFragment,
				inputFileName, createdDate, modifiedDate, modifiedBy,
				shcErrorLoggings);
	}

}
