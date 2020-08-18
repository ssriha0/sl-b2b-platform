package com.newco.marketplace.webservices.dto.serviceorder;

import java.util.List;


public class CreatePartsRequest extends ABaseWebserviceRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 555778431685979753L;
	private List<CreatePartRequest> parts;

	public List<CreatePartRequest> getParts() {
		return parts;
	}

	public void setParts(List<CreatePartRequest> parts) {
		this.parts = parts;
	}
	
}
