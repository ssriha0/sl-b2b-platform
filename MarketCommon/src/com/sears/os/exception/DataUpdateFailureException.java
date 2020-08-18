package com.sears.os.exception;

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
public class DataUpdateFailureException extends DataAccessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataUpdateFailureException(String msg) {
		super(msg);
	}

	public DataUpdateFailureException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
