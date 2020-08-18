package com.newco.marketplace.webservices.dto.serviceorder;

import com.newco.marketplace.webservices.base.CommonVO;

public class CreateTaskRequest extends CommonVO { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 7771954315337419692L;
	private Integer skillNodeId; 
	private Integer subCategoryNodeId;
	private Integer serviceTypeId;	
 
	private String taskName;
	private String taskComments;
	private Integer serviceTypeTempleteId;
	private Integer partsSuppliedId;
	
	private String jobCode;
	private Double laborPrice;
	
	
	
	
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public Double getLaborPrice() {
		return laborPrice;
	}
	public void setLaborPrice(Double laborPrice) {
		this.laborPrice = laborPrice;
	}
	public Integer getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public Integer getSkillNodeId() {
		return skillNodeId;
	}
	public void setSkillNodeId(Integer skillNodeId) {
		this.skillNodeId = skillNodeId;
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
	public Integer getServiceTypeTempleteId() {
		return serviceTypeTempleteId;
	}
	public void setServiceTypeTempleteId(Integer serviceTypeTempleteId) {
		this.serviceTypeTempleteId = serviceTypeTempleteId;
	}
	public Integer getPartsSuppliedId() {
		return partsSuppliedId;
	}
	public void setPartsSuppliedId(Integer partsSuppliedId) {
		this.partsSuppliedId = partsSuppliedId;
	}
	public Integer getSubCategoryNodeId() {
		return subCategoryNodeId;
	}
	public void setSubCategoryNodeId(Integer subCategoryNodeId) {
		this.subCategoryNodeId = subCategoryNodeId;
	}
	
	
}
