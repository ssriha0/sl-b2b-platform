package com.newco.marketplace.dto.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class SkuMaintenanceVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String skuCategoryName;
	private String skuCategoryDesc;
	private String sku;
	private String skuNameDesc;
	private String oldMainCategory;
	private String selectedtemplateId;
	private String orderItemType;
	private String priceType;
	private Double retailPrice;
	private Double bidMargin;
	private Double bidPrice;
	private Double billingMargin;
	private Double billingPrice;
	private String taskName;
	private String taskComments;
	private List<LookupVO> categoryList;
	private String oldCategory;
	private List<LookupVO> subCategoryList;
	private String oldSubCategory;
	private List<LookupVO> skillList;
	private String selectedSkill;
	private String nodeId;
	private Boolean manageScopeInd;
	private List<Map<Integer, String>> templateNames;
	
	public String getSelectedtemplateId() {
		return selectedtemplateId;
	}
	public void setSelectedtemplateId(String selectedtemplateId) {
		this.selectedtemplateId = selectedtemplateId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public List<LookupVO> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<LookupVO> categoryList) {
		this.categoryList = categoryList;
	}
	
	public String getSkuCategoryName() {
		return skuCategoryName;
	}
	public void setSkuCategoryName(String skuCategoryName) {
		this.skuCategoryName = skuCategoryName;
	}
	public String getSkuCategoryDesc() {
		return skuCategoryDesc;
	}
	public void setSkuCategoryDesc(String skuCategoryDesc) {
		this.skuCategoryDesc = skuCategoryDesc;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String skuName) {
		this.sku = skuName;
	}
	public String getSkuNameDesc() {
		return skuNameDesc;
	}
	public void setSkuNameDesc(String skuNameDesc) {
		this.skuNameDesc = skuNameDesc;
	}

	public String getselectedtemplateId() {
		return selectedtemplateId;
	}
	public void setselectedtemplateId(String selectedtemplateId) {
		this.selectedtemplateId = selectedtemplateId;
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
	public Double getBidMargin() {
		return bidMargin;
	}
	public void setBidMargin(Double bidMargin) {
		this.bidMargin = bidMargin;
	}
	public Double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	public Double getBillingMargin() {
		return billingMargin;
	}
	public void setBillingMargin(Double billingMargin) {
		this.billingMargin = billingMargin;
	}
	public Double getBillingPrice() {
		return billingPrice;
	}
	public void setBillingPrice(Double billingPrice) {
		this.billingPrice = billingPrice;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskComments() {
		return taskComments;
	}
	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}

	public String getSelectedSkill() {
		return selectedSkill;
	}
	public void setSelectedSkill(String selectedSkill) {
		this.selectedSkill = selectedSkill;
	}

	public String getOldMainCategory() {
		return oldMainCategory;
	}
	public void setOldMainCategory(String oldMainCategory) {
		this.oldMainCategory = oldMainCategory;
	}
	public String getOldCategory() {
		return oldCategory;
	}
	public void setOldCategory(String oldCategory) {
		this.oldCategory = oldCategory;
	}
	public String getOldSubCategory() {
		return oldSubCategory;
	}
	public void setOldSubCategory(String oldSubCategory) {
		this.oldSubCategory = oldSubCategory;
	}
	public List<LookupVO> getSubCategoryList() {
		return subCategoryList;
	}
	public void setSubCategoryList(List<LookupVO> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	public List<LookupVO> getSkillList() {
		return skillList;
	}
	public void setSkillList(List<LookupVO> skillList) {
		this.skillList = skillList;
	}
	public String getOrderItemType() {
		return orderItemType;
	}
	public void setOrderItemType(String orderItemType) {
		this.orderItemType = orderItemType;
	}
	public Boolean getManageScopeInd() {
		return manageScopeInd;
	}
	public void setManageScopeInd(Boolean manageScopeInd) {
		this.manageScopeInd = manageScopeInd;
	}
	public List<Map<Integer, String>> getTemplateNames() {
		return templateNames;
	}
	public void setTemplateNames(List<Map<Integer, String>> templateNames) {
		this.templateNames = templateNames;
	}

	
}
