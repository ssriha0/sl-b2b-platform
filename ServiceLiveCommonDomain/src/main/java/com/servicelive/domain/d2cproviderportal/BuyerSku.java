package com.servicelive.domain.d2cproviderportal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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

import com.servicelive.domain.sku.maintenance.BuyerSkuCategory;
import com.servicelive.domain.sku.maintenance.BuyerSkuTask;
import com.servicelive.domain.so.type.BidPriceSchema;
import com.servicelive.domain.so.type.PriceType;
import com.servicelive.domain.so.type.SkuOrderItemType;

@Entity(name="BuyerSkuD2C")
@Table(name = "buyer_sku")
public class BuyerSku  implements Serializable{

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

	    @Column(name = "orderitem_type", nullable=false)
	    private SkuOrderItemType orderItemType;

	    // @OneToMany(mappedBy="buyerSku",targetEntity=VendorServiceOffering.class, fetch=FetchType.LAZY)
	    // private List<VendorServiceOffering> vendorServiceOfferings ;
		
	    @OneToMany(mappedBy="buyerSku",targetEntity=BuyerSkuTask.class, fetch=FetchType.LAZY)
	     private List<BuyerSkuTask> buyerSkuTasks ;
	    
		@ManyToOne
		@JoinColumn (name = "template_id")
		private BuyerSoTemplateD2C buyerSoTemplate;
		
		@ManyToOne
		@JoinColumn (name = "sku_category")
		private BuyerSkuCategory buyerSkuCategory;
		
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

	    public SkuOrderItemType getOrderItemType() {
	        return orderItemType;
	    }

	    public void setOrderItemType(SkuOrderItemType orderItemType) {
	        this.orderItemType = orderItemType;
	    }
   
	    //public List<VendorServiceOffering> getVendorServiceOfferings() {
		//	return vendorServiceOfferings;
		//}

		//public void setVendorServiceOfferings(List<VendorServiceOffering> vendorServiceOfferings) {
		//	this.vendorServiceOfferings = vendorServiceOfferings;
		//}

		public BuyerSoTemplateD2C getBuyerSoTemplate() {
			return buyerSoTemplate;
		}

		public void setBuyerSoTemplate(BuyerSoTemplateD2C buyerSoTemplate) {
			this.buyerSoTemplate = buyerSoTemplate;
		}

		public List<BuyerSkuTask> getBuyerSkuTasks() {
			return buyerSkuTasks;
		}

		public void setBuyerSkuTasks(List<BuyerSkuTask> buyerSkuTasks) {
			this.buyerSkuTasks = buyerSkuTasks;
		}

		public BuyerSkuCategory getBuyerSkuCategory() {
			return buyerSkuCategory;
		}

		public void setBuyerSkuCategory(BuyerSkuCategory buyerSkuCategory) {
			this.buyerSkuCategory = buyerSkuCategory;
		}
}
