package com.newco.marketplace.dto.vo.powerbuyer;

import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;


public class ClaimHistoryVO extends CommonVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 280449991720963082L;
	
	private int historyId;
	private String soId;
	private Integer claimByResourceId;	
	private Integer unClaimByResourceId;
	private Date claimDate;
	private Date unClaimDate;	
	private Integer buyerId;
	private int reasonCode;
	


	public int getHistoryId() {
		return historyId;
	}


	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}


	public String getSoId() {
		return soId;
	}


	public void setSoId(String soId) {
		this.soId = soId;
	}


	public Integer getClaimByResourceId() {
		return claimByResourceId;
	}


	public void setClaimByResourceId(Integer claimByResourceId) {
		this.claimByResourceId = claimByResourceId;
	}


	public Integer getUnClaimByResourceId() {
		return unClaimByResourceId;
	}


	public void setUnClaimByResourceId(Integer unClaimByResourceId) {
		this.unClaimByResourceId = unClaimByResourceId;
	}


	public Date getClaimDate() {
		return claimDate;
	}


	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}


	public Date getUnClaimDate() {
		return unClaimDate;
	}


	public void setUnClaimDate(Date unClaimDate) {
		this.unClaimDate = unClaimDate;
	}


	public Integer getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}


	public int getReasonCode() {
		return reasonCode;
	}


	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}


	public ClaimHistoryVO() {
	}


	
}
