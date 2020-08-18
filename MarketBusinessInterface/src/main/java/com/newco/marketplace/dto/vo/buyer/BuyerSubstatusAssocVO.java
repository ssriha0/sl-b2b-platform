package com.newco.marketplace.dto.vo.buyer;

import com.newco.marketplace.webservices.base.CommonVO;

public class BuyerSubstatusAssocVO extends CommonVO {
	
	private Integer buyerSubStatusAssocId;
	private Integer substatusId;
	private Integer buyerId;
	private String buyerStatus;
	private Integer wfStateId;
	private String comments;
	private Integer updateCount;
	
	public Integer getBuyerSubStatusAssocId() {
		return buyerSubStatusAssocId;
	}
	public void setBuyerSubStatusAssocId(Integer buyerSubStatusAssocId) {
		this.buyerSubStatusAssocId = buyerSubStatusAssocId;
	}
	public Integer getSubstatusId() {
		return substatusId;
	}
	public void setSubstatusId(Integer substatusId) {
		this.substatusId = substatusId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerStatus() {
		return buyerStatus;
	}
	public void setBuyerStatus(String buyerStatus) {
		this.buyerStatus = buyerStatus;
	}
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
	}


}
