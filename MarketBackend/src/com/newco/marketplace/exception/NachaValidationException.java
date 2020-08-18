package com.newco.marketplace.exception;

import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * NachaValidationException is thrown by the NachaProcessor when it does the
 * validation for size and type 
 * @author Siva
 */

public class NachaValidationException extends BusinessServiceException{
    private static final long serialVersionUID = 1L;

	public NachaValidationException(String message){
		super(message);
	}
	
    public NachaValidationException(String message, Exception cause) {

        super(message, cause);
    }
}
