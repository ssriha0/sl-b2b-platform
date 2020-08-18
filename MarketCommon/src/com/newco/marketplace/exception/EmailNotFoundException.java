/*
** DuplicateUserException.java  1.0     2007/06/01
*/
package com.newco.marketplace.exception;

/**
 * Exception for Duplicate User
 * 
 * @version
 * @author blars04
 *
 */
public class EmailNotFoundException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @param msg
     */
    public EmailNotFoundException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param ex
     */
    public EmailNotFoundException(String msg, Throwable ex) {
        super(msg, ex);
    }

}//end class