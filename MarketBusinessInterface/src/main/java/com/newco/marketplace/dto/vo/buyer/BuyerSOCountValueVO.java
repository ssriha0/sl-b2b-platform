package com.newco.marketplace.dto.vo.buyer;

import java.io.Serializable;

public class BuyerSOCountValueVO implements Serializable {

	private static final long serialVersionUID = 20100406L;

	private Integer buyerId;
	private Integer wfStateId; 
	private Integer soSubstatusId;
	private Integer soCount;
		
	/**
	 * 
	 * @return the total count of service orders
	 */
	public Integer getSoCount() {
		return soCount;
	}
	
	/**
	 * 
	 * @param count
	 */
	public void setSoCount(Integer count) {
		this.soCount = count;
	}
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
	 * @return the wf_state_id
	 */
	public Integer getWfStateId() {
		return wfStateId;
	}
	/**
	 * @param wf_state_id the wf_state_id to set
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
}
