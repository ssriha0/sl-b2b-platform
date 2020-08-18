package com.newco.marketplace.translator.dto;

public class Task extends BaseDTO {

	private static final long serialVersionUID = -2822787343504190712L;
	private String sku;
	private Integer taskNodeID;
	private String mainServiceCatName;
	private String taskCatName;
	private Integer taskSubCatNodeID;
	private String taskSubCatName;
	private String title;
	private String comments;
	private boolean defaultTask;
	private Integer leadTime;
	private Integer skillID;
	private String specialtyCode;
	private Integer serviceTypeId;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Integer getTaskNodeID() {
		return taskNodeID;
	}
	public void setTaskNodeID(Integer taskNodeID) {
		this.taskNodeID = taskNodeID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public boolean isDefaultTask() {
		return defaultTask;
	}
	public void setDefaultTask(boolean defaultTask) {
		this.defaultTask = defaultTask;
	}
	public Integer getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(Integer leadTime) {
		this.leadTime = leadTime;
	}
	public Integer getSkillID() {
		return skillID;
	}
	public void setSkillID(Integer skillID) {
		this.skillID = skillID;
	}
	public Integer getTaskSubCatNodeID() {
		return taskSubCatNodeID;
	}
	public void setTaskSubCatNodeID(Integer taskSubCatNodeID) {
		this.taskSubCatNodeID = taskSubCatNodeID;
	}
	/**
	 * @return the specialtyCode
	 */
	public String getSpecialtyCode() {
		return specialtyCode;
	}
	/**
	 * @param specialtyCode the specialtyCode to set
	 */
	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}
	public Integer getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public String getTaskCatName() {
		return taskCatName;
	}
	public void setTaskCatName(String taskCatName) {
		this.taskCatName = taskCatName;
	}
	public String getTaskSubCatName() {
		return taskSubCatName;
	}
	public void setTaskSubCatName(String taskSubCatName) {
		this.taskSubCatName = taskSubCatName;
	}
	public String getMainServiceCatName() {
		return mainServiceCatName;
	}
	public void setMainServiceCatName(String mainServiceCatName) {
		this.mainServiceCatName = mainServiceCatName;
	}
	
}
