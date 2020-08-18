package com.newco.marketplace.dto.vo.leadsmanagement;


public class FirmServiceVO {
	private String rootCategory;
	private String project;
	private String firmId;
	private String serviceScope;
	private String rootNodeId;
	private String projectId;

	
	public String getServiceScope() {
		return serviceScope;
	}
	public void setServiceScope(String serviceScope) {
		this.serviceScope = serviceScope;
	}
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	
	public String getRootCategory() {
		return rootCategory;
	}
	public void setRootCategory(String rootCategory) {
		this.rootCategory = rootCategory;
	}
	public String getRootNodeId() {
		return rootNodeId;
	}
	public void setRootNodeId(String rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
}
