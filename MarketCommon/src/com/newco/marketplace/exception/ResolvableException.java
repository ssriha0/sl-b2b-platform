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
 * $Id: ResolvableException.java,v 1.4 2008/04/26 00:51:55 glacy Exp $
 */

package com.newco.marketplace.exception;

/**
 * ResolvableException provides the base for all checked exceptions in
 * MarketPlace.
 */
public class ResolvableException extends BaseException {
    
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