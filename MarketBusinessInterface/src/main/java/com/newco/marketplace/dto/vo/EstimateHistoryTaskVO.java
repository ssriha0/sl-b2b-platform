package com.newco.marketplace.dto.vo;

public class EstimateHistoryTaskVO {
    
	private Integer itemId;
	private Integer estimationId;
	private Integer taskSeqNumber;
	private String taskName;
	private Integer partSeqNumber;
	private String partNumber;
	private String partName;
	private String taskType;
	private String description;
	private Double unitPrice;
	private Integer quantity;
	private Double totalPrice;
	private String additionalDetails;
	private String status;
	private String action;
	//Adding Other Services as part of R16_2_1
	private Integer otherServiceSeqNumber;
	private String otherServiceType;
	private String otherServiceName;
	private Integer estimationHistoryId;
	
	
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getEstimationId() {
		return estimationId;
	}
	public void setEstimationId(Integer estimationId) {
		this.estimationId = estimationId;
	}
	public Integer getTaskSeqNumber() {
		return taskSeqNumber;
	}
	public void setTaskSeqNumber(Integer taskSeqNumber) {
		this.taskSeqNumber = taskSeqNumber;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getPartSeqNumber() {
		return partSeqNumber;
	}
	public void setPartSeqNumber(Integer partSeqNumber) {
		this.partSeqNumber = partSeqNumber;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getAdditionalDetails() {
		return additionalDetails;
	}
	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getOtherServiceSeqNumber() {
		return otherServiceSeqNumber;
	}
	public void setOtherServiceSeqNumber(Integer otherServiceSeqNumber) {
		this.otherServiceSeqNumber = otherServiceSeqNumber;
	}
	public String getOtherServiceType() {
		return otherServiceType;
	}
	public void setOtherServiceType(String otherServiceType) {
		this.otherServiceType = otherServiceType;
	}
	public String getOtherServiceName() {
		return otherServiceName;
	}
	public void setOtherServiceName(String otherServiceName) {
		this.otherServiceName = otherServiceName;
	}
	public Integer getEstimationHistoryId() {
		return estimationHistoryId;
	}
	public void setEstimationHistoryId(Integer estimationHistoryId) {
		this.estimationHistoryId = estimationHistoryId;
	}
    
	
	
}
