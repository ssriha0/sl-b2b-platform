package com.newco.marketplace.translator.dao;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * AbstractBuyerSkuCategory entity provides the base persistence definition of
 * the BuyerSkuCategory entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerSkuCategory implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8753150552799208362L;
	private Integer categoryId;
	private BuyerMT buyer;
	private String categoryName;
	private String categoryDescr;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Set<BuyerSku> buyerSkus = new HashSet<BuyerSku>(0);

	// Constructors

	/** default constructor */
	public AbstractBuyerSkuCategory() {
	}

	/** minimal constructor */
	public AbstractBuyerSkuCategory(BuyerMT buyer, String categoryName,
			String categoryDescr, Timestamp createdDate, Timestamp modifiedDate) {
		this.buyer = buyer;
		this.categoryName = categoryName;
		this.categoryDescr = categoryDescr;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	/** full constructor */
	public AbstractBuyerSkuCategory(BuyerMT buyer, String categoryName,
			String categoryDescr, Timestamp createdDate,
			Timestamp modifiedDate, Set<BuyerSku> buyerSkus, Integer categoryId) {
		this.buyer = buyer;
		this.categoryName = categoryName;
		this.categoryDescr = categoryDescr;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.buyerSkus = buyerSkus;
		this.categoryId = categoryId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "category_id", unique = true, nullable = false)
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buyer_id", nullable = false)
	public BuyerMT getBuyer() {
		return this.buyer;
	}

	public void setBuyer(BuyerMT buyer) {
		this.buyer = buyer;
	}

	@Column(name = "category_name", nullable = false, length = 30)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "category_descr", nullable = false)
	public String getCategoryDescr() {
		return this.categoryDescr;
	}

	public void setCategoryDescr(String categoryDescr) {
		this.categoryDescr = categoryDescr;
	}

	@Column(name = "created_date", nullable = false, length = 19)
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "modified_date", nullable = false, length = 19)
	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "buyerSkuCategory")
	public Set<BuyerSku> getBuyerSkus() {
		return this.buyerSkus;
	}

	public void setBuyerSkus(Set<BuyerSku> buyerSkus) {
		this.buyerSkus = buyerSkus;
	}

}