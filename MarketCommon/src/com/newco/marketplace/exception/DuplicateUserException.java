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
public class DuplicateUserException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @param msg
     */
    public DuplicateUserException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param ex
     */
    public DuplicateUserException(String msg, Throwable ex) {
        super(msg, ex);
    }

}//end class