package com.newco.marketplace.exception.webservices;

import javax.xml.namespace.QName;

public class CommonServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommonServiceException(Throwable cause, String message) {
		super(message, cause);
	}

	public static QName getFaultName() {
		return new QName("http://market.service.net", "CommonServiceException");
	}
}
