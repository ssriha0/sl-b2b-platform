package com.newco.marketplace.api.beans.search.skillTree;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing task information.
 * @author Infosys
 *
 */
@XStreamAlias("task")
public class Task {

	
	@XStreamAlias("taskName")
	private String taskName;
	
	@XStreamAlias("serviceType")
	private String serviceType;
	
	@XStreamAlias("taskComment")
	private String taskComment;
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

}
