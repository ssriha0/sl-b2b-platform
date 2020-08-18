package com.newco.marketplace.web.dto;

import java.util.Date;

/**
 * 
 * WFMSOTasksDTO.java
 * @author Munish Joshi
 * Jan 28, 2009
 */

public class WFMSOTasksDTO {
	
	private static final long serialVersionUID = -8921680114723701648L;
	private Integer soTaskId;
	private Integer queueId;
	private String soId;
	private Date claimDate;
	private Integer claimedById;
	private Integer companyId;
	private Date unclaimedDate;
	private String completeInd;
	private String expiredInd;
	private String taskCode;
	private String taskDesc;
	private String taskState;
	private Integer requeueHours;
	private Integer requeueMins;
	
	/**
	 * @return the requeueHours
	 */
	public Integer getRequeueHours() {
		return requeueHours;
	}
	/**
	 * @param requeueHours the requeueHours to set
	 */
	public void setRequeueHours(Integer requeueHours) {
		this.requeueHours = requeueHours;
	}
	/**
	 * @return the requeueMins
	 */
	public Integer getRequeueMins() {
		return requeueMins;
	}
	/**
	 * @param requeueMins the requeueMins to set
	 */
	public void setRequeueMins(Integer requeueMins) {
		this.requeueMins = requeueMins;
	}
	public Integer getSoTaskId() {
		return soTaskId;
	}
	public void setSoTaskId(Integer soTaskId) {
		this.soTaskId = soTaskId;
	}

	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}
	public Integer getClaimedById() {
		return claimedById;
	}
	public void setClaimedById(Integer claimedById) {
		this.claimedById = claimedById;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Date getUnclaimedDate() {
		return unclaimedDate;
	}
	public void setUnclaimedDate(Date unclaimedDate) {
		this.unclaimedDate = unclaimedDate;
	}
	public String getCompleteInd() {
		return completeInd;
	}
	public void setCompleteInd(String completeInd) {
		this.completeInd = completeInd;
	}
	public String getExpiredInd() {
		return expiredInd;
	}
	public void setExpiredInd(String expiredInd) {
		this.expiredInd = expiredInd;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}

}
