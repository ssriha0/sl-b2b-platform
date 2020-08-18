package com.newco.marketplace.exception.gis;

@SuppressWarnings("serial")
public class GISException extends Exception {
	
	public GISException(String msg) {
		super(msg);
	}

	public GISException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
