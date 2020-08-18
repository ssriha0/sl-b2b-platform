package com.newco.marketplace.exception;

import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * StringParseException is thrown while parsing String when it does the
 * validation for size and type 
 * @author Siva
 */

public class UnknownMessageTypeException extends BusinessServiceException{
    private static final long serialVersionUID = 1L;

	public UnknownMessageTypeException(String message){
		super(message);
	}
	
    public UnknownMessageTypeException(String message, Exception cause) {

        super(message, cause);
    }
}
