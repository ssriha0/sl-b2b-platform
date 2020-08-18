package com.newco.marketplace.dto.vo.buyer;

import java.io.Serializable;

public class BuyerTabSummaryVO implements Serializable {
	private static final long serialVersionUID = 20100406L;
	private Integer buyerId;
	private Integer wfStateId;
	private Integer soCount;
	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	/**
	 * @return the wfStateId
	 */
	public Integer getWfStateId() {
		return wfStateId;
	}
	/**
	 * @param wfStateId the wfStateId to set
	 */
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	/**
	 * @return the soCount
	 */
	public Integer getSoCount() {
		return soCount;
	}
	/**
	 * @param soCount the soCount to set
	 */
	public void setSoCount(Integer soCount) {
		this.soCount = soCount;
	}
}
