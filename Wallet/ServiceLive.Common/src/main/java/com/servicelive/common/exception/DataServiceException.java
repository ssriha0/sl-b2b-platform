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
 * $Id: DataServiceException.java,v 1.3 2008/04/26 00:51:53 glacy Exp $
 */

package com.servicelive.common.exception;

// TODO: Auto-generated Javadoc
/**
 * DataServiceException is thrown by the Data service. This means all the DAO
 * Objects should throw only the DataServiceException. Any Exceptions that are
 * from the component at lower level should be wrapped by the this exception, so
 * a meaningful error message can be returned.
 * 
 * @author Anil S. Karkera
 */
public class DataServiceException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new <code>DataServiceException</code> with detail message.
	 * 
	 * @param message Error message that identifies the application problem.
	 */
	public DataServiceException(String message) {

		super(message);
	}

	/**
	 * Creates new <code>DataServiceException</code> with a detail message.
	 * 
	 * @param message Error message that identifies the application problem.
	 * @param cause The actual exception that was caught.
	 */
	public DataServiceException(String message, Exception cause) {

		super(message, cause);
	}
}