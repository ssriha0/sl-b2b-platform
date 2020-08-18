package com.servicelive.wallet.valuelink.sharp.iso;

import com.servicelive.common.exception.SLBusinessServiceException;

// TODO: Auto-generated Javadoc
/**
 * Class StringParseException.
 */

public class StringParseException extends SLBusinessServiceException {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * StringParseException.
	 * 
	 * @param message 
	 */
	public StringParseException(String message) {

		super(message);
	}

	/**
	 * StringParseException.
	 * 
	 * @param message 
	 * @param cause 
	 */
	public StringParseException(String message, Exception cause) {

		super(message, cause);
	}
}
