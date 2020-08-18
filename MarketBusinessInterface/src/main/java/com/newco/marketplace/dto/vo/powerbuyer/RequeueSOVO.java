/**
 * 
 */
package com.newco.marketplace.dto.vo.powerbuyer;

import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * 
 * @author mjoshi1
 *
 */

public class RequeueSOVO extends CommonVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1244048870195481279L;
	/**
	 * 
	 */
	public RequeueSOVO() {
	}
	
	private Date requeueDate;
	private String requeueTime;
	private String soId;
	private String groupSOId;
	private Integer buyerId;
	private Integer queueId;
	private Integer completedInd;
	private Date modifiedDate;
	private Date createdDate;
	private Integer groupedInd;
	private Integer queueSeq;
	private Long noteId;
	private Integer claimedInd; 
	private Date claimedDate;
	private Integer claimedById;
	private Integer  claimedFromQueueId;
	
	
	
	
	
	
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
	 * @return the groupedInd
	 */
	public Integer getGroupedInd() {
		return groupedInd;
	}
	/**
	 * @param groupedInd the groupedInd to set
	 */
	public void setGroupedInd(Integer groupedInd) {
		this.groupedInd = groupedInd;
	}
	public Integer getCompletedInd() {
		return completedInd;
	}
	public void setCompletedInd(Integer completedInd) {
		this.completedInd = completedInd;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getRequeueDate() {
		return requeueDate;
	}
	public void setRequeueDate(Date requeueDate) {
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
	public String getGroupSOId() {
		return groupSOId;
	}
	public void setGroupSOId(String groupSOId) {
		this.groupSOId = groupSOId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getClaimedInd() {
		return claimedInd;
	}
	public void setClaimedInd(Integer claimedInd) {
		this.claimedInd = claimedInd;
	}
	public Date getClaimedDate() {
		return claimedDate;
	}
	public void setClaimedDate(Date claimedDate) {
		this.claimedDate = claimedDate;
	}
	public Integer getClaimedById() {
		return claimedById;
	}
	public void setClaimedById(Integer claimedById) {
		this.claimedById = claimedById;
	}
	public Integer getClaimedFromQueueId() {
		return claimedFromQueueId;
	}
	public void setClaimedFromQueueId(Integer claimedFromQueueId) {
		this.claimedFromQueueId = claimedFromQueueId;
	}

	
	
}
