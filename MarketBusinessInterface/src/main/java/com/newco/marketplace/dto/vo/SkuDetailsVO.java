package com.newco.marketplace.dto.vo;


public class SkuDetailsVO {
	
	private String skuCategory;
	private String mainServiceCategory;
	private String category;
	private String subCategory;
	private String skill;
	private String mainServiceCategoryId;
	private String categoryId;
	private String subCategoryId;
    private String sku;
    private Integer skuId;
    private String description;
    private String template;
    private String orderType;
    private double retailPrice;
    private double margin;
    private double maximumPrice;
    private double billingMargin;
    private double billingPrice;
    private String taskName;
	private Integer nodeId;
	private String taskComments;
	private Integer skillType;
	
    private SkuTaskVO taskVO;

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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public double getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public double getBillingMargin() {
		return billingMargin;
	}

	public void setBillingMargin(double billingMargin) {
		this.billingMargin = billingMargin;
	}

	public double getBillingPrice() {
		return billingPrice;
	}

	public void setBillingPrice(double billingPrice) {
		this.billingPrice = billingPrice;
	}

	public SkuTaskVO getTaskVO() {
		return taskVO;
	}

	public void setTaskVO(SkuTaskVO taskVO) {
		this.taskVO = taskVO;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getSkuCategory() {
		return skuCategory;
	}

	public void setSkuCategory(String skuCategory) {
		this.skuCategory = skuCategory;
	}

	public String getMainServiceCategory() {
		return mainServiceCategory;
	}

	public void setMainServiceCategory(String mainServiceCategory) {
		this.mainServiceCategory = mainServiceCategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getMainServiceCategoryId() {
		return mainServiceCategoryId;
	}

	public void setMainServiceCategoryId(String mainServiceCategoryId) {
		this.mainServiceCategoryId = mainServiceCategoryId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public String getTaskComments() {
		return taskComments;
	}

	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}

	public Integer getSkillType() {
		return skillType;
	}

	public void setSkillType(Integer skillType) {
		this.skillType = skillType;
	}

}
