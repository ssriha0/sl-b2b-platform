package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing task information.
 * @author Infosys
 *
 */
@XStreamAlias("task")
public class Task {
	

	
	@XStreamAlias("taskId")
	private Integer taskId;
	
	
	@XStreamAlias("taskStatus")
	private String taskStatus;


	public Integer getTaskId() {
		return taskId;
	}


	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}


	public String getTaskStatus() {
		return taskStatus;
	}


	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	

}
