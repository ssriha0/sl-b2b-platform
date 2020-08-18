package com.newco.marketplace.dto.vo;



public class BuyerSkuTaskForSoVO {
	private Integer skuTaskId;
	private Integer skuId;
	private Integer categoryNodeId;
	private Integer serviceTypeTemplateId;
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
	public Integer getCategoryNodeId() {
		return categoryNodeId;
	}
	public void setCategoryNodeId(Integer categoryNodeId) {
		this.categoryNodeId = categoryNodeId;
	}
	public Integer getServiceTypeTemplateId() {
		return serviceTypeTemplateId;
	}
	public void setServiceTypeTemplateId(Integer serviceTypeTemplateId) {
		this.serviceTypeTemplateId = serviceTypeTemplateId;
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
	

}
