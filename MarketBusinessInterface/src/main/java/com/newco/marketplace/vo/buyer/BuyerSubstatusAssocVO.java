package com.newco.marketplace.vo.buyer;

import com.sears.os.vo.SerializableBaseVO;

public class BuyerSubstatusAssocVO extends SerializableBaseVO{
	
	private Integer buyerSubStatusAssocId;
	
	private Integer buyerId;
	
	private Integer wfStateId;
	
	private Integer soSubstatusId;
	
	private String buyerStatus;
	
	private String firstCompletion;
	
	private String comments;

	public Integer getBuyerSubStatusAssocId() {
		return buyerSubStatusAssocId;
	}

	public void setBuyerSubStatusAssocId(Integer buyerSubStatusAssocId) {
		this.buyerSubStatusAssocId = buyerSubStatusAssocId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getWfStateId() {
		return wfStateId;
	}

	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}

	public Integer getSoSubstatusId() {
		return soSubstatusId;
	}

	public void setSoSubstatusId(Integer soSubstatusId) {
		this.soSubstatusId = soSubstatusId;
	}

	public String getBuyerStatus() {
		return buyerStatus;
	}

	public void setBuyerStatus(String buyerStatus) {
		this.buyerStatus = buyerStatus;
	}

	public String getFirstCompletion() {
		return firstCompletion;
	}

	public void setFirstCompletion(String firstCompletion) {
		this.firstCompletion = firstCompletion;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	
	

}
