/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

public interface ILoggerBO {

	/**
	 * @param logRequest
	 * @param clientIp
	 * @param apiId
	 * @return
	 */
	public int logRequest(String logRequest, String clientIp, int apiId);

	/**
	 * @param logResponse
	 * @param logId
	 */
	public void logResponse(String logResponse, int logId);

}
