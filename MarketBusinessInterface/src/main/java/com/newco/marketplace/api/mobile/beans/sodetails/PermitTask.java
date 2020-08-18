package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing PermitTasks information.
 * @author Infosys
 *
 */

@XStreamAlias("permitTask")
@XmlAccessorType(XmlAccessType.FIELD)
public class PermitTask {
	
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
	
	@XStreamAlias("permitType")
	private String permitType;
	
	@XStreamAlias("custPrePaidAmount")
	private Double custPrePaidAmount;
	
	@XStreamAlias("finalPermitPriceByProvider")
	private Double finalPermitPriceByProvider;
	
	
	
	@XStreamOmitField
	private String soId;
	
	@XStreamOmitField
	private Double price = 0.0;
	
	@XStreamOmitField
	private Integer permitTypeId;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
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
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public Double getFinalPermitPriceByProvider() {
		return finalPermitPriceByProvider;
	}

	public void setFinalPermitPriceByProvider(Double finalPermitPriceByProvider) {
		this.finalPermitPriceByProvider = finalPermitPriceByProvider;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPermitType() {
		return permitType;
	}

	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}

	public Integer getPermitTypeId() {
		return permitTypeId;
	}

	public void setPermitTypeId(Integer permitTypeId) {
		this.permitTypeId = permitTypeId;
	}
	
}
