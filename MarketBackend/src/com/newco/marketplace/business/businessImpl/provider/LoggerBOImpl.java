/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */
package com.newco.marketplace.business.businessImpl.provider;

import com.newco.marketplace.business.iBusiness.provider.ILoggerBO;
import com.newco.marketplace.persistence.iDao.provider.ILoggerDao;
import com.newco.marketplace.vo.security.LoggingVO;

public class LoggerBOImpl implements ILoggerBO {

	private ILoggerDao loggerDaoImpl;
	private int logId;

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.ILoggerBO#logRequest(java.lang.String, java.lang.String, int)
	 */
	public int logRequest(String inputXML, String clientIp, int apiId) {
		LoggingVO loggingVO = new LoggingVO();
		loggingVO.setAppId(apiId);
		loggingVO.setClientIp(clientIp);
		loggingVO.setRequestXml(inputXML);
		try {
			loggingVO = loggerDaoImpl.logRequest(loggingVO);
			logId = loggingVO.getLogId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loggingVO.getLogId();
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.ILoggerBO#logResponse(java.lang.String, int)
	 */
	public void logResponse(String logResponse, int loggId) {
		LoggingVO loggingVO = new LoggingVO();
		loggingVO.setResponseXml(logResponse);
		loggingVO.setLogId(logId);

		try {
			loggingVO = loggerDaoImpl.logResponse(loggingVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void setLoggerDaoImpl(ILoggerDao loggerDaoImpl) {
		this.loggerDaoImpl = loggerDaoImpl;
	}

}
