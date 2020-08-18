package com.newco.marketplace.dto.vo.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.request.ABaseServiceRequestVO;

public class DetailsRequest extends ABaseServiceRequestVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2345427780530318163L;
	private Integer buyerId;
	private String soId;

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("clientId", getClientId())
			.append("userId", getUserId())
			.append("password", getPassword())
			.append("buyerId", getBuyerId())
			.append("soId", getSoId())
			.toString();
	}
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
}
