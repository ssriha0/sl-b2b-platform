package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
  * @author Munish Joshi
 * Feb 12, 2009
 * WFMBuyerQueueDTO.java
 *
 */
public class WFMBuyerQueueDTO extends SerializedBaseDTO{
	
	private static final long serialVersionUID = -8921680114723701648L;
	
	private Integer queueId;
	private String buyerId;
	private String activeInd;
	private String visibleInd;
	private String queueName;
	private Date queuedDate;
	private Integer uniqueNumber;
	private Integer soQueueId;
	private String note;
	private Integer queueSeq;
	private Integer claimedFromQueueId;
	private Integer completedInd;
	private Integer actionTaken;
	
	
	
	
	private List<WFMSOTasksDTO> wfmSOTasks = new ArrayList<WFMSOTasksDTO>();

	public Date getQueuedDate() {
		return queuedDate;
	}
	public void setQueuedDate(Date queuedDate) {
		this.queuedDate = queuedDate;
	}
	
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}
	public String getVisibleInd() {
		return visibleInd;
	}
	public void setVisibleInd(String visibleInd) {
		this.visibleInd = visibleInd;
	}
	public List<WFMSOTasksDTO> getWfmSOTasks() {
		return wfmSOTasks;
	}
	public void setWfmSOTasks(List<WFMSOTasksDTO> wfmSOTasks) {
		this.wfmSOTasks = wfmSOTasks;
	}
	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
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
	
}
