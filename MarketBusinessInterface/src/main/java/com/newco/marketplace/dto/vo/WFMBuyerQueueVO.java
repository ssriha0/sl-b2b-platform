package com.newco.marketplace.dto.vo;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * WFMBuyerQueueVO.java
 * @author Munish Joshi
 * Jan 28, 2009
 */

public class WFMBuyerQueueVO extends SerializableBaseVO {

	public Integer qId;
	public String buyerId;
	public String qName;
	public String qDesc;
	public Integer taskId;
	public String taskCode;
	public String taskDesc;
	public String taskState;
	private Date queuedDate;
	private Integer soQueueId;
	private String note;
	private Integer queueSeq;
	private Integer claimedFromQueueId;
	private Integer completedInd;
	private Integer actionTaken;
	
	
		
	/**
	 * @return the actionTaken
	 */
	public Integer getActionTaken() {
		return actionTaken;
	}
	/**
	 * @param actionTaken the actionTaken to set
	 */
	public void setActionTaken(Integer actionTaken) {
		this.actionTaken = actionTaken;
	}
	/**
	 * @return the completedInd
	 */
	public Integer getCompletedInd() {
		return completedInd;
	}
	/**
	 * @param completedInd the completedInd to set
	 */
	public void setCompletedInd(Integer completedInd) {
		this.completedInd = completedInd;
	}
	/**
	 * @return the queueSeq
	 */
	public Integer getQueueSeq() {
		return queueSeq;
	}
	/**
	 * @param queueSeq the queueSeq to set
	 */
	public void setQueueSeq(Integer queueSeq) {
		this.queueSeq = queueSeq;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the soQueueId
	 */
	public Integer getSoQueueId() {
		return soQueueId;
	}
	/**
	 * @param soQueueId the soQueueId to set
	 */
	public void setSoQueueId(Integer soQueueId) {
		this.soQueueId = soQueueId;
	}
	public Date getQueuedDate() {
		return queuedDate;
	}
	public void setQueuedDate(Date queuedDate) {
		this.queuedDate = queuedDate;
	}
	public Integer getQId() {
		return qId;
	}
	public void setQId(Integer id) {
		qId = id;
	}
	public String getQName() {
		return qName;
	}
	public void setQName(String name) {
		qName = name;
	}
	public String getQDesc() {
		return qDesc;
	}
	public void setQDesc(String desc) {
		qDesc = desc;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
		
	
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	/**
	 * @return the claimedFromQueueId
	 */
	public Integer getClaimedFromQueueId() {
		return claimedFromQueueId;
	}
	/**
	 * @param claimedFromQueueId the claimedFromQueueId to set
	 */
	public void setClaimedFromQueueId(Integer claimedFromQueueId) {
		this.claimedFromQueueId = claimedFromQueueId;
	}

	
	
}
