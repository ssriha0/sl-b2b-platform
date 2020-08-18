package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing task information.
 * @author Infosys
 *
 */

@XStreamAlias("task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {
	
	@OptionalParam
	@XStreamAlias("taskId")
	private Integer taskId;
	
	@XStreamAlias("taskName")
	private String taskName;
	
	@XStreamAlias("taskStatus")
	private String taskStatus;
	
	@XStreamAlias("taskCategory")
	private String taskCategory;
	
	@XStreamAlias("tasksubCategory")
	private String tasksubCategory;
	
	@XStreamAlias("taskSkill")
	private String taskSkill;
	
	@XStreamAlias("taskComments")
	private String taskComments;
	
	@XStreamAlias("taskType")
	private String taskType;
	
	@OptionalParam
	@XStreamAlias("custPrePaidAmount")
	private Double custPrePaidAmount;
	
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the taskId
	 */
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the taskStatus
	 */
	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * @return the taskCategory
	 */
	public String getTaskCategory() {
		return taskCategory;
	}

	/**
	 * @param taskCategory the taskCategory to set
	 */
	public void setTaskCategory(String taskCategory) {
		this.taskCategory = taskCategory;
	}

	/**
	 * @return the tasksubCategory
	 */
	public String getTasksubCategory() {
		return tasksubCategory;
	}

	/**
	 * @param tasksubCategory the tasksubCategory to set
	 */
	public void setTasksubCategory(String tasksubCategory) {
		this.tasksubCategory = tasksubCategory;
	}

	/**
	 * @return the taskSkill
	 */
	public String getTaskSkill() {
		return taskSkill;
	}

	/**
	 * @param taskSkill the taskSkill to set
	 */
	public void setTaskSkill(String taskSkill) {
		this.taskSkill = taskSkill;
	}

	/**
	 * @return the taskComments
	 */
	public String getTaskComments() {
		return taskComments;
	}

	/**
	 * @param taskComments the taskComments to set
	 */
	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}

	/**
	 * @return the taskType
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the custPrePaidAmount
	 */
	public Double getCustPrePaidAmount() {
		return custPrePaidAmount;
	}

	/**
	 * @param custPrePaidAmount the custPrePaidAmount to set
	 */
	public void setCustPrePaidAmount(Double custPrePaidAmount) {
		this.custPrePaidAmount = custPrePaidAmount;
	}



}
