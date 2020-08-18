package com.servicelive.marketplatform.common.exception;

public class MarketPlatformBusinessException extends MarketPlatformException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8141923202328868448L;

	public MarketPlatformBusinessException() {
    }

    public MarketPlatformBusinessException(String message) {
        super(message);
    }

    public MarketPlatformBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarketPlatformBusinessException(Throwable cause) {
        super(cause);
    }
}
