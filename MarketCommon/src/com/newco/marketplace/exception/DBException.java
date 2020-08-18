package com.newco.marketplace.exception;

import org.springframework.dao.DataAccessException;

/**
 * @author dgold1
 *
 * * 
 * Revision History
 * ------------------------------------------------------------------------------
 * dgold1 Aug 24, 2006 - Initial release
 *
 *
 */
public class DBException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBException(String msg) {
		super(msg);
	}

	public DBException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
