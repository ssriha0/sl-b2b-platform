package com.newco.marketplace.dto.vo.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.response.ABaseServiceResponseVO;

public class DetailsResponse extends ABaseServiceResponseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2008589045752514086L;
	private ServiceOrderDetail details;
	private boolean hasError = false;
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		    .append("processId", getProcessId())
			.append("code", getCode())
			.append("subCode", getSubCode())
			.append("messages", getMessages())
			.toString();
	}
	
	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public ServiceOrderDetail getDetails() {
		return details;
	}

	public void setDetails(ServiceOrderDetail details) {
		this.details = details;
	}

}