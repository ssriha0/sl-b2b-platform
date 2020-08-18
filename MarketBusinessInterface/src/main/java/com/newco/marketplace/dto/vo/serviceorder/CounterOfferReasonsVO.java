package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;

/**
 *  POJO object for storing counter offer reasons 
 */
public class CounterOfferReasonsVO extends SerializableBaseVO{

	private static final long serialVersionUID = 1L;
	private Integer providerRespReasonId;
	private Integer counterOfferReasonId;
	private String counterOfferReasonDesc;
	
	public Integer getProviderRespReasonId() {
		return providerRespReasonId;
	}
	public void setProviderRespReasonId(Integer providerRespReasonId) {
		this.providerRespReasonId = providerRespReasonId;
	}
	public Integer getCounterOfferReasonId() {
		return counterOfferReasonId;
	}
	public void setCounterOfferReasonId(Integer counterOfferReasonId) {
		this.counterOfferReasonId = counterOfferReasonId;
	}
	public String getCounterOfferReasonDesc() {
		return counterOfferReasonDesc;
	}
	public void setCounterOfferReasonDesc(String counterOfferReasonDesc) {
		this.counterOfferReasonDesc = counterOfferReasonDesc;
	}
}
