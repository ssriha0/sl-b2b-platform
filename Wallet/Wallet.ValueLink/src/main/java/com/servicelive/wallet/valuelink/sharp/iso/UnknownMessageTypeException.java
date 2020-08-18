package com.servicelive.wallet.valuelink.sharp.iso;

import com.servicelive.common.exception.SLBusinessServiceException;

// TODO: Auto-generated Javadoc
/**
 * Class UnknownMessageTypeException.
 */

public class UnknownMessageTypeException extends SLBusinessServiceException {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * UnknownMessageTypeException.
	 * 
	 * @param message 
	 */
	public UnknownMessageTypeException(String message) {

		super(message);
	}

	/**
	 * UnknownMessageTypeException.
	 * 
	 * @param message 
	 * @param cause 
	 */
	public UnknownMessageTypeException(String message, Exception cause) {

		super(message, cause);
	}
}
