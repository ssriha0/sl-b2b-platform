package com.newco.marketplace.exception;

import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * StringParseException is thrown while parsing String when it does the
 * validation for size and type 
 * @author Siva
 */

public class StringParseException extends BusinessServiceException{
    private static final long serialVersionUID = 1L;

	public StringParseException(String message){
		super(message);
	}
	
    public StringParseException(String message, Exception cause) {

        super(message, cause);
    }
}
