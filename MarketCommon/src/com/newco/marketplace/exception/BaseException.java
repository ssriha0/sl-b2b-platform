package com.newco.marketplace.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author dgold1
 *
 * * 
 * Revision History
 * ------------------------------------------------------------------------------
 * dgold1 Aug 9, 2006 - Initial release
 *
 *
 */

public class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Root cause of this nested exception */
	private Throwable cause;

	public BaseException(String msg) {
		super(msg);
	}

	public BaseException(String msg, Throwable ex) {
		super(msg);
		this.cause = ex;
	}

	public Throwable getCause() {
		return (this.cause == this ? null : this.cause);
	}

	public String getMessage() {
		if (getCause() == null) {
			return super.getMessage();
		} else {
			return super.getMessage()
				+ "; nested exception is "
				+ getCause().getClass().getName()
				+ ": "
				+ getCause().getMessage();
		}
	}

	public void printStackTrace(PrintStream ps) {
		if (getCause() == null) {
			super.printStackTrace(ps);
		} else {
			ps.println(this);
			getCause().printStackTrace(ps);
		}
	}

	public void printStackTrace(PrintWriter pw) {
		if (getCause() == null) {
			super.printStackTrace(pw);
		} else {
			pw.println(this);
			getCause().printStackTrace(pw);
		}
	}

}
