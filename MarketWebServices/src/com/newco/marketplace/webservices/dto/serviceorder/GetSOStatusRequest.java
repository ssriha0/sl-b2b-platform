package com.newco.marketplace.webservices.dto.serviceorder;


public class GetSOStatusRequest extends ABaseWebserviceRequest {
	
	private static final long serialVersionUID = -2464125030975434362L;
	
	private String uniqueCustomReferenceValue;

	public String getUniqueCustomReferenceValue() {
		return uniqueCustomReferenceValue;
	}

	public void setUniqueCustomReferenceValue(String uniqueCustomReferenceValue) {
		this.uniqueCustomReferenceValue = uniqueCustomReferenceValue;
	}

	
}
