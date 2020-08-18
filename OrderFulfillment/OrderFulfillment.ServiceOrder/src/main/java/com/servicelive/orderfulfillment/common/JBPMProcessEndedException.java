package com.servicelive.orderfulfillment.common;

public class JBPMProcessEndedException extends ServiceOrderException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 553448241722968058L;

	public JBPMProcessEndedException(String processId) {
		super(processId);
	}

}
