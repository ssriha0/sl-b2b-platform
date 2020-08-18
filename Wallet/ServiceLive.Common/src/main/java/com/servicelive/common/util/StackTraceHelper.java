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
 * $Id: StackTraceHelper.java,v 1.3 2008/04/26 00:51:53 glacy Exp $
 */

package com.servicelive.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class StackTraceHelper.
 */
public class StackTraceHelper {

	/**
	 * Gets the stack trace.
	 * 
	 * @param t 
	 * 
	 * @return the stack trace
	 */
	public static String getStackTrace(Throwable t) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}