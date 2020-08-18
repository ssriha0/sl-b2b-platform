package com.newco.marketplace.webservices.dto.serviceorder;

import com.newco.marketplace.webservices.response.WSProcessResponse;

public class GetSOStatusResponse extends WSProcessResponse {
	
    private String SLServiceOrderStatus;

	public String getSLServiceOrderStatus() {
		return SLServiceOrderStatus;
	}

	public void setSLServiceOrderStatus(String serviceOrderStatus) {
		SLServiceOrderStatus = serviceOrderStatus;
	}

}
