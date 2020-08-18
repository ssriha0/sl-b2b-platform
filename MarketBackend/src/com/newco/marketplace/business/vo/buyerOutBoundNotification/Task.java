package com.newco.marketplace.business.vo.buyerOutBoundNotification;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Task implements Serializable {
	/** generated serialVersionUID */
	private static final long serialVersionUID = -5157588994707371956L;

	private long taskId;
	private Long serviceOrderId;
	private String title;
	private String comments;
	private Boolean defaultTask;
	private String externalSku;
	private String specialtyCode;
	private Double amount;
	private String category;
	private String subCategory;
	private String serviceType;
	private Integer sequenceNumber;
	
	public Task() {}
	
	public Task(long taskId, Long serviceOrderId, String title,
			String comments, Boolean defaultTask, String externalSku,
			String specialtyCode, Double amount, String category,
			String subCategory, String serviceType, Integer sequenceNumber) {
		super();
		this.taskId = taskId;
		this.serviceOrderId = serviceOrderId;
		this.title = title;
		this.comments = comments;
		this.defaultTask = defaultTask;
		this.externalSku = externalSku;
		this.specialtyCode = specialtyCode;
		this.amount = amount;
		this.category = category;
		this.subCategory = subCategory;
		this.serviceType = serviceType;
		this.sequenceNumber = sequenceNumber;
	}
	public Double getAmount() {
		return amount;
	}
	public String getCategory() {
		return category;
	}
	public String getComments() {
		return comments;
	}
	public Boolean getDefaultTask() {
		return defaultTask;
	}
	public String getExternalSku() {
		return externalSku;
	}
	public Long getServiceOrderId() {
		return serviceOrderId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public String getSpecialtyCode() {
		return specialtyCode;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public long getTaskId() {
		return taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setDefaultTask(Boolean defaultTask) {
		this.defaultTask = defaultTask;
	}
	public void setExternalSku(String externalSku) {
		this.externalSku = externalSku;
	}
	public void setServiceOrderId(Long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
}
