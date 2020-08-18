package com.servicelive.bus;

/**
 * User: Mustafa Motiwala Date: Mar 27, 2010 Time: 4:35:41 PM
 */
public class EventBusException extends RuntimeException {
	/** generated serialVersionUID */
	private static final long serialVersionUID = 8780524089630006169L;

	public EventBusException(String message) {
		super(message);
	}

	public EventBusException(String message, Throwable cause) {
		super(message, cause);
	}
}
