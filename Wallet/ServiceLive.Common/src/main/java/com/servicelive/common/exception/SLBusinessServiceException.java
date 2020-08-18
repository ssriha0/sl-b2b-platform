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
 * $Id: BusinessServiceException.java,v 1.4 2008/04/26 00:51:55 glacy Exp $
 */

package com.servicelive.common.exception;

import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * BusinessServiceExcepiton is thrown by the business service. This means all
 * the business bean Objects should throw only the BusinessServiceException. Any
 * Exceptions that are from the component at lower level should be wrapped by
 * the this exception, so a meaningful error message can be returned.
 * 
 * @author Anil S. Karkera
 */
public class SLBusinessServiceException extends com.newco.marketplace.exception.BusinessServiceException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private List<String> errors = new ArrayList<String>();

	/**
	 * Creates new <code>BusinessServiceException</code> without detail
	 * message.
	 * 
	 * @param message Error message that identifies the application problem.
	 */
	public SLBusinessServiceException(String message) {

		super(message);
	}

	/**
	 * Creates new <code>BusinessServiceException</code> without detail
	 * message.
	 * 
	 * @param message Error message that identifies the application problem.
	 * @param cause The actual exception that was caught.
	 */
	public SLBusinessServiceException(String message, Exception cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new business service exception.
	 * 
	 * @param t 
	 */
	public SLBusinessServiceException(Throwable t) {

		super(t.getMessage());
	}
	
	public SLBusinessServiceException(List<String> errors) {
		super(errors.get(0));
		this.errors.addAll(errors);
	}
	
	public List<String> getErrors() {
		return this.errors;
	}
}