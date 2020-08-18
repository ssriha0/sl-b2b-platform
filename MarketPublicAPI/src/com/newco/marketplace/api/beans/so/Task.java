package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing task information.
 * @author Infosys
 *
 */
@XStreamAlias("task")
public class Task {
	
	@XStreamAlias("taskId")
	private int taskId;
	
	@XStreamAlias("taskName")
	private String taskName;
	
	@XStreamAlias("categoryID")
	private String categoryID;
	
	@XStreamAlias("serviceType")
	private String serviceType;
	
	@XStreamAlias("taskComment")
	private String taskComment;

	@XStreamAlias("categoryName")
	private String categoryName;
	
	@XStreamAlias("subCategoryName")
	private String subCategoryName;
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getTaskComment() {
		return taskComment;
	}

	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

}
