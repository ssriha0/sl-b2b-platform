package com.newco.marketplace.translator.dao;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * AbstractLuDocumentCategory entity provides the base persistence definition of
 * the LuDocumentCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractLuDocumentCategory implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4561517762956435946L;
	private Integer docCategoryId;
	private String type;
	private String descr;
	private Integer sortOrder;
	private Set<BuyerDocument> buyerDocuments = new HashSet<BuyerDocument>(0);

	// Constructors

	/** default constructor */
	public AbstractLuDocumentCategory() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractLuDocumentCategory(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	/** full constructor */
	public AbstractLuDocumentCategory(Integer docCategoryId, String type,
			String descr, Integer sortOrder, Set<BuyerDocument> buyerDocuments) {
		this.docCategoryId = docCategoryId;
		this.type = type;
		this.descr = descr;
		this.sortOrder = sortOrder;
		this.buyerDocuments = buyerDocuments;
	}

	// Property accessors
	@Id
	@Column(name = "doc_category_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getDocCategoryId() {
		return this.docCategoryId;
	}

	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "descr", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "sort_order", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "luDocumentCategory")
	public Set<BuyerDocument> getBuyerDocuments() {
		return this.buyerDocuments;
	}

	public void setBuyerDocuments(Set<BuyerDocument> buyerDocuments) {
		this.buyerDocuments = buyerDocuments;
	}

}