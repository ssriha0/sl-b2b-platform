package com.newco.marketplace.dto.vo;

import java.sql.Timestamp;
import java.util.List;

import com.servicelive.domain.sku.maintenance.BuyerSkuCategory;
import com.servicelive.domain.sku.maintenance.BuyerSkuTask;



public class BuyerSkuVO {
	private Integer skuId;
	private Integer buyerId;
	private String sku;
	private Integer primaryIndustryId;
	private String primaryIndustryDesc;
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
	private Integer templateId;
	private String modifiedBy;
	private Timestamp modifiedDate;
	private String orderitemType;
	private Boolean manageScopeInd;
	private Integer skuCategory;
	private List<VendorServiceOfferingVO> vendorServiceOfferVo;
	private BuyerSkuCategory buyerSkuCategory;
	private List<BuyerSkuTask> buyerSkuTasks ;
	
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public Double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	public Double getBillingPrice() {
		return billingPrice;
	}
	public void setBillingPrice(Double billingPrice) {
		this.billingPrice = billingPrice;
	}
	public Double getBidMargin() {
		return bidMargin;
	}
	public void setBidMargin(Double bidMargin) {
		this.bidMargin = bidMargin;
	}
	public Double getBillingMargin() {
		return billingMargin;
	}
	public void setBillingMargin(Double billingMargin) {
		this.billingMargin = billingMargin;
	}
	public String getBidPriceSchema() {
		return bidPriceSchema;
	}
	public void setBidPriceSchema(String bidPriceSchema) {
		this.bidPriceSchema = bidPriceSchema;
	}
	public String getBillingPriceSchema() {
		return billingPriceSchema;
	}
	public void setBillingPriceSchema(String billingPriceSchema) {
		this.billingPriceSchema = billingPriceSchema;
	}
	public String getSkuDescription() {
		return skuDescription;
	}
	public void setSkuDescription(String skuDescription) {
		this.skuDescription = skuDescription;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getOrderitemType() {
		return orderitemType;
	}
	public void setOrderitemType(String orderitemType) {
		this.orderitemType = orderitemType;
	}
	public Boolean getManageScopeInd() {
		return manageScopeInd;
	}
	public void setManageScopeInd(Boolean manageScopeInd) {
		this.manageScopeInd = manageScopeInd;
	}
	public Integer getSkuCategory() {
		return skuCategory;
	}
	public void setSkuCategory(Integer skuCategory) {
		this.skuCategory = skuCategory;
	}
	public List<VendorServiceOfferingVO> getVendorServiceOfferVo() {
		return vendorServiceOfferVo;
	}
	public void setVendorServiceOfferVo(List<VendorServiceOfferingVO> vendorServiceOfferVo) {
		this.vendorServiceOfferVo = vendorServiceOfferVo;
	}
	public BuyerSkuCategory getBuyerSkuCategory() {
		return buyerSkuCategory;
	}
	public void setBuyerSkuCategory(BuyerSkuCategory buyerSkuCategory) {
		this.buyerSkuCategory = buyerSkuCategory;
	}
	public List<BuyerSkuTask> getBuyerSkuTasks() {
		return buyerSkuTasks;
	}
	public void setBuyerSkuTasks(List<BuyerSkuTask> buyerSkuTasks) {
		this.buyerSkuTasks = buyerSkuTasks;
	}
	public Integer getPrimaryIndustryId() {
		return primaryIndustryId;
	}
	public void setPrimaryIndustryId(Integer primaryIndustryId) {
		this.primaryIndustryId = primaryIndustryId;
	}
	public String getPrimaryIndustryDesc() {
		return primaryIndustryDesc;
	}
	public void setPrimaryIndustryDesc(String primaryIndustryDesc) {
		this.primaryIndustryDesc = primaryIndustryDesc;
	}
}
