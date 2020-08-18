/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */
package com.newco.marketplace.api.security;

import com.newco.marketplace.business.iBusiness.provider.ILoggerBO;

public class LoggingProcess {
	private ILoggerBO loggerBoImpl;

	/**
	 * it invokes the request logging
	 * 
	 * @param logRequest	:	input xml
	 * @param clientIp		:	client ip
	 * @param appId			:	application id
	 */
	public int logRequest(String logRequest, String clientIp, int appId) {
		return loggerBoImpl.logRequest(logRequest, clientIp, appId);
	}

	/**
	 * it invokes the response logging
	 * 
	 * @param logResponse	:	response xml
	 */
	public void logResponse(String logResponse,int logId) {
		loggerBoImpl.logResponse(logResponse, logId);
	}

	public void setLoggerBoImpl(ILoggerBO loggerBoImpl) {
		this.loggerBoImpl = loggerBoImpl;
	}

}
