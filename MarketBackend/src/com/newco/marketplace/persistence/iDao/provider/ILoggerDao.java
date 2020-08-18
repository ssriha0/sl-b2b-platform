/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.vo.security.LoggingVO;

public interface ILoggerDao {

	/**
	 * @param loggingVO
	 * @return
	 * @throws Exception
	 */
	public LoggingVO logRequest(LoggingVO loggingVO) throws Exception;

	/**
	 * @param loggingVO
	 * @return
	 * @throws Exception
	 */
	public LoggingVO logResponse(LoggingVO loggingVO) throws Exception;

}
