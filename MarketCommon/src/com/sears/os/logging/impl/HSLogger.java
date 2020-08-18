package com.sears.os.logging.impl;

import org.apache.log4j.Logger;
import org.apache.commons.logging.Log;

public final class HSLogger implements Log {
	private Logger logger = null;
	static HSLogger _hsLog = null;

	public HSLogger() {
	}

	/**
	 * Base constructor
	 */
	public HSLogger(String name) {
		this.logger = Logger.getLogger(name);
	}

	/** For use with a log4j factory
	 */
	public HSLogger(Logger logger) {
		this.logger = logger;
	}

	// ---------------------------------------------------------- Implmentation

	/**
	 * Log a message to the Log4j Logger with <code>TRACE</code> priority.
	 * Currently logs to <code>DEBUG</code> level in Log4J.
	 */
	public void trace(Object message) {
		trace(message, null);
	}

	/**
	 * Log an error to the Log4j Logger with <code>TRACE</code> priority.
	 * Currently logs to <code>DEBUG</code> level in Log4J.
	 */
	public void trace(Object message, Throwable t) {
		logger.debug(message, t);
	}

	/**
	 * Log a message to the Log4j Logger with <code>DEBUG</code> priority.
	 */
	public void debug(Object message) {
		debug(message, null);
	}

	/**
	 * Log an error to the Log4j Logger with <code>DEBUG</code> priority.
	 */
	public void debug(Object message, Throwable t) {
		logger.debug(message, t);
	}

	/**
	 * Log a message to the Log4j Logger with <code>INFO</code> priority.
	 */
	public void info(Object message) {
		info(message, null);
	}

	/**
	 * Log an error to the Log4j Logger with <code>INFO</code> priority.
	 */
	public void info(Object message, Throwable t) {
		logger.info(message, t);
	}

	/**
	 * Log a message to the Log4j Logger with <code>WARN</code> priority.
	 */
	public void warn(Object message) {
		warn(message, null);
	}

	/**
	 * Log an error to the Log4j Logger with <code>WARN</code> priority.
	 */
	public void warn(Object message, Throwable t) {
		logger.warn(message, t);
	}

	/**
	 * Log a message to the Log4j Logger with <code>ERROR</code> priority.
	 */
	public void error(Object message) {
		logger.error(message, null);
	}

	/**
	 * Log an error to the Log4j Logger with <code>ERROR</code> priority.
	 */
	public void error(Object message, Throwable t) {
		logger.error(message, t);
	}

	/**
	 * Log a message to the Log4j Logger with <code>FATAL</code> priority.
	 */
	public void fatal(Object message) {
		logger.fatal(message, null);
	}

	/**
	 * Log an error to the Log4j Logger with <code>FATAL</code> priority.
	 */
	public void fatal(Object message, Throwable t) {
		logger.fatal(message, t);
	}

	/**
	 * Return the native Logger instance we are using.
	 */
	public Logger getLogger() {
		return (this.logger);
	}

	/**
	 * Check whether the Log4j Logger used is enabled for <code>DEBUG</code> priority.
	 */
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/**
	* Check whether the Log4j Logger used is enabled for <code>ERROR</code> priority.
	*/
	public boolean isErrorEnabled() {
		return true;
		//return logger.isErrorEnabled();
	}

	/**
	 * Check whether the Log4j Logger used is enabled for <code>FATAL</code> priority.
	 */
	public boolean isFatalEnabled() {
		return true;
		//return logger.isFatalEnabled();
	}

	/**
	 * Check whether the Log4j Logger used is enabled for <code>INFO</code> priority.
	 */
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/**
	 * Check whether the Log4j Logger used is enabled for <code>TRACE</code> priority.
	 * For Log4J, this returns the value of <code>isDebugEnabled()</code>
	 */
	public boolean isTraceEnabled() {
		return logger.isDebugEnabled();
	}

	/**
	 * Check whether the Log4j Logger used is enabled for <code>WARN</code> priority.
	 */
	public boolean isWarnEnabled() {
		return true;
		//return logger.isWarnEnabled();
	}
}
