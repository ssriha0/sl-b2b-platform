/*
 * Created on Mar 8, 2007
 *
 * Author: dgold1
 * 
 * Revisions:
 * 
 */
package com.sears.os.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sears.os.utils.ProcessUtils;

public abstract class ABaseEvent implements Event {

	protected final Log logger = LogFactory.getLog(getClass());

	protected void logEntry(String name, ABaseEvent event) {
		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("EVENT NAME [");
			sb.append(name);
			sb.append("] ID[");
			sb.append(ProcessUtils.getProcessId());
			sb.append("] has started...");
			logger.debug(sb.toString());
		}
	}

	protected void logExit(String name, ABaseEvent event) {
		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("EVENT NAME [");
			sb.append(name);
			sb.append("] ID[");
			sb.append(ProcessUtils.getProcessId());
			sb.append("] has ended...");
			logger.debug(sb.toString());
		}
	}
}
