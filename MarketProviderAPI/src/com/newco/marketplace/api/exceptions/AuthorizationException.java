package com.newco.marketplace.api.exceptions;

public class AuthorizationException  extends Exception {	
	public AuthorizationException() {
	    super();             // call superclass constructor	  
	  }
	
	  public AuthorizationException(String err) {
	    super(err);     // call super class constructor	    
	  }	  
}
