package com.servicelive.marketplatform.common.exception;

import java.util.ArrayList;
import java.util.List;

public class MarketPlatformException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Throwable> errors = new ArrayList<Throwable>();

    public MarketPlatformException() {
    }

    public MarketPlatformException(String message) {
        super(message);
    }

    public MarketPlatformException(String message, Throwable cause) {
        super(message, cause);
        errors.add(cause);
    }

    public MarketPlatformException(Throwable cause) {
        super(cause);
        errors.add(cause);
    }

    public List<Throwable> getErrors() {
        return errors;
    }
}
