/**
 * Copyright
 * (c) 2007 Sears Holding, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Sears Holding, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sears Holding, Inc.
 *
 * Copyright (c) 2007 Sears Holding, Inc.
 *
 * All rights reserved.
 * See the LICENSE.txt file for the terms of use and distribution.
 *
 * $Id: UnresolvableException.java,v 1.3 2008/04/26 00:51:54 glacy Exp $
 */

package com.newco.marketplace.exception.core;

/**
 * UnresolvableException provides the base for all unchecked exceptions in
 * MarketPlace.
 * 
 * @author Anil S. Karkera
 */

public class UnresolvableException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a new ResolvableExceptin with an error message.
     * 
     * @param message
     *            Error message that identifies the application problem.
     */
    public UnresolvableException(final String message) {

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
    public UnresolvableException(final String message, final Exception cause) {
        super(message, cause);
    }

    /**
     * Get a string representation of the exception. This contains the unique
     * error ID as well as the exception class name and the default message for
     * this exception
     * 
     * @return String a string representation of the exception
     */
    public String toString() {

        return getClass() + ": " + getMessage();
    }
}