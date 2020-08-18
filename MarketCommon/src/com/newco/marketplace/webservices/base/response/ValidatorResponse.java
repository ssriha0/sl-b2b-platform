package com.newco.marketplace.webservices.base.response;


public class ValidatorResponse extends ABaseResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3521046199949090348L;

	public ValidatorResponse() {
		super();
	}
	
	public ValidatorResponse(String code, String subCode) {
		super(code, subCode);
	}
}
