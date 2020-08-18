package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BuyerDocumentId entity provides the base persistence definition of the
 * umentId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class BuyerDocumentId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer buyerId;
	private Integer documentId;
	private Integer docCategoryId;

	// Constructors

	/** default constructor */
	public BuyerDocumentId() {
		// intentionally blank
	}

	/** full constructor */
	public BuyerDocumentId(Integer buyerId, Integer documentId,
			Integer docCategoryId) {
		this.buyerId = buyerId;
		this.documentId = documentId;
		this.docCategoryId = docCategoryId;
	}

	// Property accessors

	@Column(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getBuyerId() {
		return this.buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	@Column(name = "document_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	@Column(name = "doc_category_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getDocCategoryId() {
		return this.docCategoryId;
	}

	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BuyerDocumentId))
			return false;
		BuyerDocumentId castOther = (BuyerDocumentId) other;

		return ((this.getBuyerId() == castOther.getBuyerId()) || (this
				.getBuyerId() != null
				&& castOther.getBuyerId() != null && this.getBuyerId().equals(
				castOther.getBuyerId())))
				&& ((this.getDocumentId() == castOther.getDocumentId()) || (this
						.getDocumentId() != null
						&& castOther.getDocumentId() != null && this
						.getDocumentId().equals(castOther.getDocumentId())))
				&& ((this.getDocCategoryId() == castOther.getDocCategoryId()) || (this
						.getDocCategoryId() != null
						&& castOther.getDocCategoryId() != null && this
						.getDocCategoryId()
						.equals(castOther.getDocCategoryId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getBuyerId() == null ? 0 : this.getBuyerId().hashCode());
		result = 37
				* result
				+ (getDocumentId() == null ? 0 : this.getDocumentId()
						.hashCode());
		result = 37
				* result
				+ (getDocCategoryId() == null ? 0 : this.getDocCategoryId()
						.hashCode());
		return result;
	}

}