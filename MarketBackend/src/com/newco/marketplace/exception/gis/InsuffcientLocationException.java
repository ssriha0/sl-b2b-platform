package com.newco.marketplace.exception.gis;

@SuppressWarnings("serial")
public class InsuffcientLocationException extends Exception {
	
	public InsuffcientLocationException(String msg) {
		super(msg);
	}

	public InsuffcientLocationException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
