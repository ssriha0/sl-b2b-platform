package com.newco.marketplace.exception.core;

/**
 * BusinessServiceException is thrown by the Service. Any Exceptions that are
 * from the component at lower level should be wrapped by the this exception, so
 * a meaningful error message can be returned.
 * 
 * @author
 */
public class BusinessServiceException extends ResolvableException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new <code>BusinessServiceException</code> with a detail message.
     * 
     * @param message
     *            Error message that identifies the application problem.
     * @param cause
     *            The actual exception that was caught.
     */
    public BusinessServiceException(String message, Exception cause) {

        super(message, cause);
    }

    /**
     * Creates new <code>BusinessServiceException</code> with detail message.
     * 
     * @param message
     *            Error message that identifies the application problem.
     */
    public BusinessServiceException(String message) {
        super(message);
    }
   
    public BusinessServiceException (Throwable t) {
        super(t.getMessage());
    }

}
