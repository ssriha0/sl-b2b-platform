package com.servicelive.domain.so;

import com.servicelive.domain.so.type.BidPriceSchema;
import com.servicelive.domain.so.type.PriceType;
import com.servicelive.domain.so.type.SkuOrderItemType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buyer_sku")
public class BuyerOrderSku implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sku_id")
    private Long skuId;

    @Column(name="sku", nullable=false)
    private String sku;

    @Column(name="buyer_id", nullable=false)
    private Long buyerId;

    @Column(name = "price_type", nullable=false)
    private PriceType priceType;

    @Column(name = "retail_price", nullable=false)
    private BigDecimal retailPrice;

    @Column(name = "bid_price", nullable=false)
    private BigDecimal bidPrice;

    @Column(name = "billing_price", nullable=false)
    private BigDecimal billingPrice;

    @Column(name = "bid_margin", nullable=false)
    private BigDecimal bidMargin;

    @Column(name = "billing_margin", nullable=false)
    private BigDecimal billingMargin;

    @Column(name = "bid_price_schema", nullable=true)
    private BidPriceSchema bidPriceSchema;

    @Column(name = "sku_description", nullable=false)
    private String skuDescription;

    @Column(name = "template_id", nullable=false)
    private Long templateId;

    @Column(name = "orderitem_type", nullable=false)
    private SkuOrderItemType orderItemType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="sku_id")
    private List<BuyerOrderSkuTask> buyerSkuTaskList = new ArrayList<BuyerOrderSkuTask>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="sku_id")
    private List<BuyerOrderSkuAttribute> buyerSkuAttributeList = new ArrayList<BuyerOrderSkuAttribute>();

    @ManyToOne
    @JoinColumn(name="sku_category", referencedColumnName="category_id" , nullable=false)
    private BuyerOrderSkuCategory buyerSkuCategory;
    
    @Column(name = "manage_scope_ind")
    private Boolean manageScopeInd;

    public Boolean getManageScopeInd() {
		return manageScopeInd;
	}

	public void setManageScopeInd(Boolean manageScopeInd) {
		this.manageScopeInd = manageScopeInd;
	}

	public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public BigDecimal getBillingPrice() {
        return billingPrice;
    }

    public void setBillingPrice(BigDecimal billingPrice) {
        this.billingPrice = billingPrice;
    }

    public BigDecimal getBidMargin() {
        return bidMargin;
    }

    public void setBidMargin(BigDecimal bidMargin) {
        this.bidMargin = bidMargin;
    }

    public BigDecimal getBillingMargin() {
        return billingMargin;
    }

    public void setBillingMargin(BigDecimal billingMargin) {
        this.billingMargin = billingMargin;
    }

    public BidPriceSchema getBidPriceSchema() {
        return bidPriceSchema;
    }

    public void setBidPriceSchema(BidPriceSchema bidPriceSchema) {
        this.bidPriceSchema = bidPriceSchema;
    }

    public String getSkuDescription() {
        return skuDescription;
    }

    public void setSkuDescription(String skuDescription) {
        this.skuDescription = skuDescription;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public SkuOrderItemType getOrderItemType() {
        return orderItemType;
    }

    public void setOrderItemType(SkuOrderItemType orderItemType) {
        this.orderItemType = orderItemType;
    }

    public List<BuyerOrderSkuTask> getBuyerSkuTaskList() {
        return buyerSkuTaskList;
    }

    public void setBuyerSkuTaskList(List<BuyerOrderSkuTask> buyerSkuTaskList) {
        this.buyerSkuTaskList = buyerSkuTaskList;
    }

    public List<BuyerOrderSkuAttribute> getBuyerSkuAttributeList() {
        return buyerSkuAttributeList;
    }

    public void setBuyerSkuAttributeList(List<BuyerOrderSkuAttribute> buyerSkuAttributeList) {
        this.buyerSkuAttributeList = buyerSkuAttributeList;
    }

    public BuyerOrderSkuCategory getBuyerSkuCategory() {
        return buyerSkuCategory;
    }

    public void setBuyerSkuCategory(BuyerOrderSkuCategory buyerSkuCategory) {
        this.buyerSkuCategory = buyerSkuCategory;
    }
}
