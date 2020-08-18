package com.servicelive.wallet.batch.ach;

import com.servicelive.common.exception.SLBusinessServiceException;

// TODO: Auto-generated Javadoc
/**
 * NachaValidationException is thrown by the NachaProcessor when it does the
 * validation for size and type.
 * 
 * @author Siva
 */

public class NachaValidationException extends SLBusinessServiceException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new nacha validation exception.
	 * 
	 * @param message 
	 */
	public NachaValidationException(String message) {

		super(message);
	}

	/**
	 * Instantiates a new nacha validation exception.
	 * 
	 * @param message 
	 * @param cause 
	 */
	public NachaValidationException(String message, Exception cause) {

		super(message, cause);
	}
}
