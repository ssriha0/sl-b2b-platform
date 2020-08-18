package com.newco.marketplace.dto.vo.serviceorder;

import java.util.List;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * Object representing the UpSell SKU for a Service Order.
 * @author dpotlur
 *
 */
public class ServiceOrderAddonVO  extends CommonVO{

	private static final long serialVersionUID = -541014570414519372L;
	
	private String soId;
	private Integer addonId;
	private String sku;
	private String description;
	private String scopeOfWork;
	private double retailPrice;
	private double margin;
	private boolean miscInd;
	private int quantity;
	private double serviceFee;
	private String coverage;
	private boolean autoGenInd;
	private Integer addonPermitTypeId;
	private double taxPercentage;
	private Integer skuGroupType;
	

	//Reversed for Order Injection.
	private List<String>  skuList;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public boolean isMiscInd() {
		return miscInd;
	}

	public void setMiscInd(boolean miscInd) {
		this.miscInd = miscInd;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getScopeOfWork() {
		return scopeOfWork;
	}

	public void setScopeOfWork(String scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	
	public List<String> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<String> skuList) {
		this.skuList = skuList;
	}

	public boolean isAutoGenInd() {
		return autoGenInd;
	}

	public void setAutoGenInd(boolean autoGenInd) {
		this.autoGenInd = autoGenInd;
	}

	public Integer getAddonId() {
		return addonId;
	}

	public void setAddonId(Integer addonId) {
		this.addonId = addonId;
	}

	public Integer getAddonPermitTypeId() {
		return addonPermitTypeId;
	}

	public void setAddonPermitTypeId(Integer addonPermitTypeId) {
		this.addonPermitTypeId = addonPermitTypeId;
	}

	public double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public Integer getSkuGroupType() {
		return skuGroupType;
	}

	public void setSkuGroupType(Integer skuGroupType) {
		this.skuGroupType = skuGroupType;
	}

	
	
}
