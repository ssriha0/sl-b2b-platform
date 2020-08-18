/**
 * 
 */
package com.newco.marketplace.web.dto.ajax;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

/**
 * @author MJOSHI1
 *
 */
public class SOQueueNoteDTO extends SerializedBaseDTO{
	
	private String noteText;
	private String pri;
	private String pub;
	private String queueAction;	
	private String queueID;
	private String requeueDate;
	private String requeueTime;
	private String soTaskId;
	private String taskState;
	private String taskCode;
	private String buyerId;
	private String soId;
	private String groupId;
	private Integer soQueueId;
	private Integer uniqueNumber;
	boolean newCallBackQueue;
	private Integer queueSeq = 0;
	private Long noteId;
	private String tabToShow;
	private String randomNumUnclaim;
	
	
	
	boolean primaryQueue = false; 
	
	public boolean isPrimaryQueue() {
		return primaryQueue;
	}
	public void setPrimaryQueue(boolean primaryQueue) {
		this.primaryQueue = primaryQueue;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getSoTaskId() {
		return soTaskId;
	}
	public void setSoTaskId(String soTaskId) {
		this.soTaskId = soTaskId;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getNoteText() {
		return noteText;
	}
	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}
	public String getPri() {
		return pri;
	}
	public void setPri(String pri) {
		this.pri = pri;
	}
	public String getPub() {
		return pub;
	}
	public void setPub(String pub) {
		this.pub = pub;
	}
	public String getQueueAction() {
		return queueAction;
	}
	public void setQueueAction(String queueAction) {
		this.queueAction = queueAction;
	}
	public String getQueueID() {
		return queueID;
	}
	public void setQueueID(String queueID) {
		this.queueID = queueID;
	}
	public String getRequeueDate() {
		return requeueDate;
	}
	public void setRequeueDate(String requeueDate) {
		this.requeueDate = requeueDate;
	}
	public String getRequeueTime() {
		return requeueTime;
	}
	public void setRequeueTime(String requeueTime) {
		this.requeueTime = requeueTime;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the uniqueNumber
	 */
	public Integer getUniqueNumber() {
		return uniqueNumber;
	}
	/**
	 * @param uniqueNumber the uniqueNumber to set
	 */
	public void setUniqueNumber(Integer uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
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
	/**
	 * @return the newCallBackQueue
	 */
	public boolean isNewCallBackQueue() {
		return newCallBackQueue;
	}
	/**
	 * @param newCallBackQueue the newCallBackQueue to set
	 */
	public void setNewCallBackQueue(boolean newCallBackQueue) {
		this.newCallBackQueue = newCallBackQueue;
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
	 * @return the noteId
	 */
	public Long getNoteId() {
		return noteId;
	}
	/**
	 * @param noteId the noteId to set
	 */
	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("buyerId", buyerId);
		builder.append("groupId", groupId);
		builder.append("newCallBackQueue", newCallBackQueue);
		builder.append("noteId", noteId);
		builder.append("noteText", noteText);
		builder.append("pri", pri);
		builder.append("primaryQueue", primaryQueue);
		builder.append("pub", pub);
		builder.append("queueAction", queueAction);
		builder.append("queueID", queueID);
		builder.append("queueSeq", queueSeq);
		builder.append("requeueDate", requeueDate);
		builder.append("requeueTime", requeueTime);
		builder.append("soId", soId);
		builder.append("soQueueId", soQueueId);
		builder.append("soTaskId", soTaskId);
		builder.append("taskCode", taskCode);
		builder.append("taskState", taskState);
		builder.append("uniqueNumber", uniqueNumber);
		builder.append("tabToShow", tabToShow);
		builder.append("randomNumUnclaim", randomNumUnclaim);
		return builder.toString();
	}
	/**
	 * @return the tabToShow
	 */
	public String getTabToShow() {
		return tabToShow;
	}
	/**
	 * @param tabToShow the tabToShow to set
	 */
	public void setTabToShow(String tabToShow) {
		this.tabToShow = tabToShow;
	}
	public String getRandomNumUnclaim() {
		return randomNumUnclaim;
	}
	public void setRandomNumUnclaim(String randomNumUnclaim) {
		this.randomNumUnclaim = randomNumUnclaim;
	}
	
	
	
	

}
