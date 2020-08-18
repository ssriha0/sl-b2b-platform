package com.servicelive.domain.sku.maintenance;

import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.servicelive.domain.buyer.Buyer;

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
	private static final long serialVersionUID = 8753150552299208362L;
	private Integer categoryId;
	private Buyer buyer;
	private String categoryName;
	private String categoryDescr;
	private Set<BuyerSKUEntity> buyerSkus = new HashSet<BuyerSKUEntity>(0);
	private Set<SkuCategoryHistoy> skuCategoryHistory = new HashSet<SkuCategoryHistoy>(0);

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;
	// Constructors

	/** default constructor */
	public AbstractBuyerSkuCategory() {
	}

	/** minimal constructor */
	public AbstractBuyerSkuCategory(Buyer buyer, String categoryName,
			String categoryDescr, Timestamp createdDate, Timestamp modifiedDate) {
		this.buyer = buyer;
		this.categoryName = categoryName;
		this.categoryDescr = categoryDescr;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	/** full constructor */
	public AbstractBuyerSkuCategory(Buyer buyer, String categoryName,
			String categoryDescr, Timestamp createdDate,
			Timestamp modifiedDate, Set<BuyerSKUEntity> buyerSkus, Integer categoryId, Set<SkuCategoryHistoy> skuCategoryHistory) {
		this.buyer = buyer;
		this.categoryName = categoryName;
		this.categoryDescr = categoryDescr;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.buyerSkus = buyerSkus;
		this.categoryId = categoryId;
		this.skuCategoryHistory = skuCategoryHistory;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", nullable = false)
	public Buyer getBuyer() {
		return this.buyer;
	}

	public void setBuyer(Buyer buyer) {
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
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	@Column(name = "modified_date", nullable = false, length = 19)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "buyerSkuCategory")
	public Set<BuyerSKUEntity> getBuyerSkus() {
		return this.buyerSkus;
	}

	public void setBuyerSkus(Set<BuyerSKUEntity> buyerSkus) {
		this.buyerSkus = buyerSkus;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "buyerSkuCategory")
	public Set<SkuCategoryHistoy> getSkuCategoryHistory() {
		return skuCategoryHistory;
	}

	public void setSkuCategoryHistory(Set<SkuCategoryHistoy> skuCategoryHistory) {
		this.skuCategoryHistory = skuCategoryHistory;
	}

}