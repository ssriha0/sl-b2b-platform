package com.servicelive.common;

import java.io.PrintStream;
import java.io.PrintWriter;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author dgold1
 * 
 * *
 * Revision History
 * ------------------------------------------------------------------------------
 * dgold1 Aug 9, 2006 - Initial release
 */

public class BaseException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Root cause of this nested exception. */
	private Throwable cause;

	/**
	 * Instantiates a new base exception.
	 * 
	 * @param msg 
	 */
	public BaseException(String msg) {

		super(msg);
	}

	/**
	 * Instantiates a new base exception.
	 * 
	 * @param msg 
	 * @param ex 
	 */
	public BaseException(String msg, Throwable ex) {

		super(msg);
		this.cause = ex;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getCause()
	 */
	public Throwable getCause() {

		return (this.cause == this ? null : this.cause);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {

		if (getCause() == null) {
			return super.getMessage();
		} else {
			return super.getMessage() + "; nested exception is " + getCause().getClass().getName() + ": " + getCause().getMessage();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	public void printStackTrace(PrintStream ps) {

		if (getCause() == null) {
			super.printStackTrace(ps);
		} else {
			ps.println(this);
			getCause().printStackTrace(ps);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	public void printStackTrace(PrintWriter pw) {

		if (getCause() == null) {
			super.printStackTrace(pw);
		} else {
			pw.println(this);
			getCause().printStackTrace(pw);
		}
	}

}
