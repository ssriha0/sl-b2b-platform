package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BuyerDocument entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "buyer_document", uniqueConstraints = {})
public class BuyerDocument extends AbstractBuyerDocument implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public BuyerDocument() {
		// intentionally blank
	}

	/** minimal constructor */
	public BuyerDocument(BuyerDocumentId id, BuyerMT buyer, DocumentMT document,
			LuDocumentCategory luDocumentCategory) {
		super(id, buyer, document, luDocumentCategory);
	}

	/** full constructor */
	public BuyerDocument(BuyerDocumentId id, BuyerMT buyer, DocumentMT document,
			LuDocumentCategory luDocumentCategory, Date createdDate,
			Date modifiedDate, String modifiedBy) {
		super(id, buyer, document, luDocumentCategory, createdDate,
				modifiedDate, modifiedBy);
	}

}
