package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractBuyerDocument entity provides the base persistence definition of the
 * BuyerDocument entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerDocument implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7010609917289421949L;
	private BuyerDocumentId id;
	private BuyerMT buyer;
	private DocumentMT document;
	private LuDocumentCategory luDocumentCategory;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractBuyerDocument() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractBuyerDocument(BuyerDocumentId id, BuyerMT buyer,
			DocumentMT document, LuDocumentCategory luDocumentCategory) {
		this.id = id;
		this.buyer = buyer;
		this.document = document;
		this.luDocumentCategory = luDocumentCategory;
	}

	/** full constructor */
	public AbstractBuyerDocument(BuyerDocumentId id, BuyerMT buyer,
			DocumentMT document, LuDocumentCategory luDocumentCategory,
			Date createdDate, Date modifiedDate, String modifiedBy) {
		this.id = id;
		this.buyer = buyer;
		this.document = document;
		this.luDocumentCategory = luDocumentCategory;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "buyerId", column = @Column(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)),
			@AttributeOverride(name = "documentId", column = @Column(name = "document_id", unique = false, nullable = false, insertable = true, updatable = true)),
			@AttributeOverride(name = "docCategoryId", column = @Column(name = "doc_category_id", unique = false, nullable = false, insertable = true, updatable = true)) })
	public BuyerDocumentId getId() {
		return this.id;
	}

	public void setId(BuyerDocumentId id) {
		this.id = id;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", unique = false, nullable = false, insertable = false, updatable = false)
	public BuyerMT getBuyer() {
		return this.buyer;
	}

	public void setBuyer(BuyerMT buyer) {
		this.buyer = buyer;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "document_id", unique = false, nullable = false, insertable = false, updatable = false)
	public DocumentMT getDocument() {
		return this.document;
	}

	public void setDocument(DocumentMT document) {
		this.document = document;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "doc_category_id", unique = false, nullable = false, insertable = false, updatable = false)
	public LuDocumentCategory getLuDocumentCategory() {
		return this.luDocumentCategory;
	}

	public void setLuDocumentCategory(LuDocumentCategory luDocumentCategory) {
		this.luDocumentCategory = luDocumentCategory;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}