/**
 * 
 */
package com.newco.marketplace.exception;

/**
 * @author blars04
 *
 */
public class EmailSenderException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    public EmailSenderException() {
    }

    /**
     * @param message
     */
    public EmailSenderException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public EmailSenderException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public EmailSenderException(String message, Throwable cause) {
        super(message, cause);
    }

}
