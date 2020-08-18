package com.newco.marketplace.api.exceptions;

/**
 * This is an exception created as a master exception for all the exceptions thrown from Mobile API
 * @author Infosys
 *
 */
public class MobileAPIException extends Exception {
	public MobileAPIException() {
		super(); // call superclass constructor	  
	}

	public MobileAPIException(String err) {
		super(err); // call super class constructor	    
	}
}
