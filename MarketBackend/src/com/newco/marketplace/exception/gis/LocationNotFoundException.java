package com.newco.marketplace.exception.gis;

@SuppressWarnings("serial")
public class LocationNotFoundException extends Exception {
	
	public LocationNotFoundException(String msg) {
		super(msg);
	}

	public LocationNotFoundException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
