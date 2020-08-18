package com.servicelive.orderfulfillment.integration;

/**
 * User: Mustafa Motiwala
 * Date: Apr 28, 2010
 * Time: 4:27:07 PM
 */
public class AncestorNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2425895101180794104L;

	public AncestorNotFoundException(String message) {
        super(message);
    }
}
