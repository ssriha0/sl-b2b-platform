package com.newco.marketplace.exception.core;

/**
 * ResolvableException provides the base for all checked exceptions in
 * MarketPlace.
 */
public class ResolvableException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Construct a new ResolvableExceptin with an error message.
     * 
     * @param message
     *            Error message that identifies the application problem.
     */
    public ResolvableException(final String message) {

        this(message, null);
    }

    /**
     * Construct a new ResolvableExceptin with an error message as well as the
     * root cause of this exception
     * 
     * @param message
     *            Error message that describes the application problem.
     * @param cause
     *            The actual exception that was caught.
     */
    public ResolvableException(final String message, final Exception cause) {

        super(message, cause);
    }
}
