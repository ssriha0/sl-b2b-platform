package com.newco.marketplace.dto.vo;

//SLT-1613 VO to store WFM Queue details
public class WFMQueueVO {
	
	private Integer queueId;
	private String queueName;
	private String queueDesc;
	private String category;
	private String isStandard;
	private String isGeneric;
	private String visibilityInd;
	private String checkInd;
	
	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getQueueDesc() {
		return queueDesc;
	}
	public void setQueueDesc(String queueDesc) {
		this.queueDesc = queueDesc;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIsStandard() {
		return isStandard;
	}
	public void setIsStandard(String isStandard) {
		this.isStandard = isStandard;
	}
	public String getIsGeneric() {
		return isGeneric;
	}
	public void setIsGeneric(String isGeneric) {
		this.isGeneric = isGeneric;
	}
	public String getVisibilityInd() {
		return visibilityInd;
	}
	public void setVisibilityInd(String visibilityInd) {
		this.visibilityInd = visibilityInd;
	}
	public String getCheckInd() {
		return checkInd;
	}
	public void setCheckInd(String checkInd) {
		this.checkInd = checkInd;
	}
}
