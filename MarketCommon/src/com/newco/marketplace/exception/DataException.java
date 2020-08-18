/*
 * DataException.java   1.0     2007/05/23
 */
package com.newco.marketplace.exception;

/**
 * @author blars04
 *
 */
public class DataException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    public DataException() {
    }

    /**
     * @param message
     */
    public DataException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DataException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

}
