package com.newco.marketplace.dto.vo.buyer;

import java.io.Serializable;

public class BuyerPaginationSummaryVO implements Serializable {
	private static final long serialVersionUID = 20100406L;

	private Integer buyerId;
	private Integer wfStateId;
	private Integer soSubstatusId;
	private Integer count;
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
	 * @return the soSubstatusId
	 */
	public Integer getSoSubstatusId() {
		return soSubstatusId;
	}
	/**
	 * @param soSubstatusId the soSubstatusId to set
	 */
	public void setSoSubstatusId(Integer soSubstatusId) {
		this.soSubstatusId = soSubstatusId;
	}
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
}
