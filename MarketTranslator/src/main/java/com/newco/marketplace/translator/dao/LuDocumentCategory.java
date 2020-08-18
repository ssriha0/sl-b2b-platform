package com.newco.marketplace.translator.dao;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LuDocumentCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "lu_document_category",  uniqueConstraints = {})
public class LuDocumentCategory extends AbstractLuDocumentCategory implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public LuDocumentCategory() {
		// intentionally blank
	}

	/** minimal constructor */
	public LuDocumentCategory(Integer docCategoryId) {
		super(docCategoryId);
	}

	/** full constructor */
	public LuDocumentCategory(Integer docCategoryId, String type, String descr,
			Integer sortOrder, Set<BuyerDocument> buyerDocuments) {
		super(docCategoryId, type, descr, sortOrder, buyerDocuments);
	}

}
