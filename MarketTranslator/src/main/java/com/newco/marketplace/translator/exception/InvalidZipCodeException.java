package com.newco.marketplace.translator.exception;

public class InvalidZipCodeException 
	extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3202566486212196409L;

	/**
     * 
     */
    public InvalidZipCodeException() {
    	// intentionally blank
    }

    /**
     * @param message
     */
    public InvalidZipCodeException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public InvalidZipCodeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public InvalidZipCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
