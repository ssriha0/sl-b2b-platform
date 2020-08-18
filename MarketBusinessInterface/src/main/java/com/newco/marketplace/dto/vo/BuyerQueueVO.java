package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class BuyerQueueVO extends SerializableBaseVO{

	private Integer queueId;
	private Integer buyerId;
	private Integer sortOrder;
	private Integer activeInd;
	private Integer visibilityInd;
	
	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(Integer activeInd) {
		this.activeInd = activeInd;
	}
	public Integer getVisibilityInd() {
		return visibilityInd;
	}
	public void setVisibilityInd(Integer visibilityInd) {
		this.visibilityInd = visibilityInd;
	}
	
}
