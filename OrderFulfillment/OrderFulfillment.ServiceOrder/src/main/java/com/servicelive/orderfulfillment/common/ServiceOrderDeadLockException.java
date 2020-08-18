package com.servicelive.orderfulfillment.common;

/**
 * @author Mustafa Motiwala
 * @since Feb 22, 2010 11:34:43 AM
 */
public class ServiceOrderDeadLockException extends ServiceOrderException{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7351517210860819046L;

	public ServiceOrderDeadLockException(String message) {
        super(message);
    }
}
