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
 * $Id: DelegateException.java,v 1.4 2008/04/26 00:51:55 glacy Exp $
 */

package com.newco.marketplace.exception;

/**
 * BusinessServiceExcepiton is thrown by the business service. This means all
 * the business bean Objects should throw only the BusinessServiceException. Any
 * Exceptions that are from the component at lower level should be wrapped by
 * the this exception, so a meaningful error message can be returned.
 * 
 * @author Anil S. Karkera
 */
public class DelegateException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new <code>BusinessServiceException</code> without detail
     * message.
     * 
     * @param message
     *            Error message that identifies the application problem.
     * @param cause
     *            The actual exception that was caught.
     */
    public DelegateException(String message, Exception cause) {
        super(message, cause);
    }

    /**
     * Creates new <code>BusinessServiceException</code> without detail
     * message.
     * 
     * @param message
     *            Error message that identifies the application problem.
     */
    public DelegateException(String message) {

        super(message);
    }

    public DelegateException (Throwable t) {
        super(t.getMessage());
    }
}