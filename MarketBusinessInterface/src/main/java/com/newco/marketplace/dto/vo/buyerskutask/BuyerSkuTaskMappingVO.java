package com.newco.marketplace.dto.vo.buyerskutask;

public class BuyerSkuTaskMappingVO {
	
	private Integer skuTaskId;
	private Integer skuId;
	private Integer categoryId;
	private Integer skillId;
	private Double bidPrice;
	private String sku;
	private String taskName;
	private String taskComments;

	public Integer getSkuTaskId() {
		return skuTaskId;
	}
	public void setSkuTaskId(Integer skuTaskId) {
		this.skuTaskId = skuTaskId;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getSkillId() {
		return skillId;
	}
	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
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

	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	

}
