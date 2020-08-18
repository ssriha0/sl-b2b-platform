package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Document entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "document",  uniqueConstraints = {})
public class DocumentMT extends AbstractDocument implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public DocumentMT() {
		// intentionally blank
	}

	/** minimal constructor */
	public DocumentMT(Integer documentId, Date createdDate, Date modifiedDate) {
		super(documentId, createdDate, modifiedDate);
	}

	/** full constructor */
	public DocumentMT(Integer documentId, Integer docCategoryId, String descr,
			String title, String fileName, String format, String source,
			String keywords, Date expireDate, Date purgeDate, String document,
			Date createdDate, Date modifiedDate, Integer vendorId,
			Integer roleId, Integer entityId, Byte deleteInd, String docUrl,
			String docPath, Integer docSize, Set<BuyerDocument> buyerDocuments) {
		super(documentId, docCategoryId, descr, title, fileName, format,
				source, keywords, expireDate, purgeDate, document, createdDate,
				modifiedDate, vendorId, roleId, entityId, deleteInd, docUrl,
				docPath, docSize, buyerDocuments);
	}

}
