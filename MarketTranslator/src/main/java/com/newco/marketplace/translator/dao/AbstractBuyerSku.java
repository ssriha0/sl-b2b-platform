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
 * AbstractBuyerSku entity provides the base persistence definition of the
 * BuyerSku entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerSku implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7653260675871431873L;
	private Integer skuId;
	private BuyerSkuCategory buyerSkuCategory;
	private BuyerSoTemplate buyerSoTemplate;
	private BuyerMT buyer;
	private String sku;
	private String priceType;
	private Double retailPrice;
	private Double bidPrice;
	private Double billingPrice;
	private Double bidMargin;
	private Double billingMargin;
	private String bidPriceSchema;
	private String billingPriceSchema;
	private String skuDescription;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	private String orderitemType;
	private Boolean manageScopeInd;
	private Set<BuyerSkuTask> buyerSkuTasks = new HashSet<BuyerSkuTask>(0);
	private Set<BuyerSkuAttributes> buyerSkuAttributeses = new HashSet<BuyerSkuAttributes>(
			0);

	// Constructors

	/** default constructor */
	public AbstractBuyerSku() {
	}

	/** minimal constructor */
	public AbstractBuyerSku(BuyerMT buyer, String sku, String priceType,
			Double retailPrice, Double bidPrice, Double billingPrice,
			Double bidMargin, Double billingMargin, Timestamp createdDate,
			String orderitemType) {
		this.buyer = buyer;
		this.sku = sku;
		this.priceType = priceType;
		this.retailPrice = retailPrice;
		this.bidPrice = bidPrice;
		this.billingPrice = billingPrice;
		this.bidMargin = bidMargin;
		this.billingMargin = billingMargin;
		this.createdDate = createdDate;
		this.orderitemType = orderitemType;
	}

	/** full constructor */
	public AbstractBuyerSku(BuyerSkuCategory buyerSkuCategory,
			BuyerSoTemplate buyerSoTemplate, BuyerMT buyer, String sku,
			String priceType, Double retailPrice, Double bidPrice,
			Double billingPrice, Double bidMargin, Double billingMargin,
			String bidPriceSchema, String billingPriceSchema,
			String skuDescription, Timestamp createdDate, String modifiedBy,
			Timestamp modifiedDate, String orderitemType,
			Set<BuyerSkuTask> buyerSkuTasks,
			Set<BuyerSkuAttributes> buyerSkuAttributeses,
			Boolean manageScopeInd) {
		this.buyerSkuCategory = buyerSkuCategory;
		this.buyerSoTemplate = buyerSoTemplate;
		this.buyer = buyer;
		this.sku = sku;
		this.priceType = priceType;
		this.retailPrice = retailPrice;
		this.bidPrice = bidPrice;
		this.billingPrice = billingPrice;
		this.bidMargin = bidMargin;
		this.billingMargin = billingMargin;
		this.bidPriceSchema = bidPriceSchema;
		this.billingPriceSchema = billingPriceSchema;
		this.skuDescription = skuDescription;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.orderitemType = orderitemType;
		this.buyerSkuTasks = buyerSkuTasks;
		this.buyerSkuAttributeses = buyerSkuAttributeses;
		this.manageScopeInd = manageScopeInd;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sku_id", unique = true, nullable = false)
	public Integer getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sku_category")
	public BuyerSkuCategory getBuyerSkuCategory() {
		return this.buyerSkuCategory;
	}

	public void setBuyerSkuCategory(BuyerSkuCategory buyerSkuCategory) {
		this.buyerSkuCategory = buyerSkuCategory;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "template_id")
	public BuyerSoTemplate getBuyerSoTemplate() {
		return this.buyerSoTemplate;
	}

	public void setBuyerSoTemplate(BuyerSoTemplate buyerSoTemplate) {
		this.buyerSoTemplate = buyerSoTemplate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buyer_id", nullable = false)
	public BuyerMT getBuyer() {
		return this.buyer;
	}

	public void setBuyer(BuyerMT buyer) {
		this.buyer = buyer;
	}

	@Column(name = "sku", nullable = false, length = 18)
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Column(name = "price_type", nullable = false, length = 8)
	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@Column(name = "retail_price", nullable = false)
	public Double getRetailPrice() {
		return this.retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	@Column(name = "bid_price", nullable = false)
	public Double getBidPrice() {
		return this.bidPrice;
	}

	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}

	@Column(name = "billing_price", nullable = false)
	public Double getBillingPrice() {
		return this.billingPrice;
	}

	public void setBillingPrice(Double billingPrice) {
		this.billingPrice = billingPrice;
	}

	@Column(name = "bid_margin", nullable = false, scale = 4)
	public Double getBidMargin() {
		return this.bidMargin;
	}

	public void setBidMargin(Double bidMargin) {
		this.bidMargin = bidMargin;
	}

	@Column(name = "billing_margin", nullable = false, scale = 4)
	public Double getBillingMargin() {
		return this.billingMargin;
	}

	public void setBillingMargin(Double billingMargin) {
		this.billingMargin = billingMargin;
	}

	@Column(name = "bid_price_schema", length = 50)
	public String getBidPriceSchema() {
		return this.bidPriceSchema;
	}

	public void setBidPriceSchema(String bidPriceSchema) {
		this.bidPriceSchema = bidPriceSchema;
	}

	@Column(name = "billing_price_schema", length = 50)
	public String getBillingPriceSchema() {
		return this.billingPriceSchema;
	}

	public void setBillingPriceSchema(String billingPriceSchema) {
		this.billingPriceSchema = billingPriceSchema;
	}

	@Column(name = "sku_description")
	public String getSkuDescription() {
		return this.skuDescription;
	}

	public void setSkuDescription(String skuDescription) {
		this.skuDescription = skuDescription;
	}

	@Column(name = "created_date", nullable = false, length = 19)
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "modified_by", length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "modified_date", length = 19)
	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "orderitem_type", nullable = false, length = 6)
	public String getOrderitemType() {
		return this.orderitemType;
	}

	public void setOrderitemType(String orderitemType) {
		this.orderitemType = orderitemType;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "buyerSku")
	public Set<BuyerSkuTask> getBuyerSkuTasks() {
		return this.buyerSkuTasks;
	}

	public void setBuyerSkuTasks(Set<BuyerSkuTask> buyerSkuTasks) {
		this.buyerSkuTasks = buyerSkuTasks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "buyerSku")
	public Set<BuyerSkuAttributes> getBuyerSkuAttributeses() {
		return this.buyerSkuAttributeses;
	}

	public void setBuyerSkuAttributeses(
			Set<BuyerSkuAttributes> buyerSkuAttributeses) {
		this.buyerSkuAttributeses = buyerSkuAttributeses;
	}
	
	@Column(name = "manage_scope_ind", nullable = false)
	public Boolean getManageScopeInd() {
		return manageScopeInd;
	}

	public void setManageScopeInd(Boolean manageScopeInd) {
		this.manageScopeInd = manageScopeInd;
	}

}