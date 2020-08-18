package com.newco.marketplace.exception.core;

public class ExcelParserException extends ResolvableException {


    private static final long serialVersionUID = 1L;

    /**
     * Creates new <code>ExcelParserException</code> without detail
     * message.
     * 
     * @param message
     *            Error message that identifies the application problem.
     * @param cause
     *            The actual exception that was caught.
     */
    public ExcelParserException(String message, Exception cause) {
        super(message, cause);
    }

    /**
     * Creates new <code>ExcelParserException</code> without detail
     * message.
     * 
     * @param message
     *            Error message that identifies the application problem.
     */
    public ExcelParserException(String message) {

        super(message);
    }

    public ExcelParserException (Throwable t) {
        super(t.getMessage());
    }
}
