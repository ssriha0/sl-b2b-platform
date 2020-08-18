/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.persistence.iDao.provider.ILoggerDao;
import com.newco.marketplace.vo.security.LoggingVO;

public class LoggerDaoImpl extends SqlMapClientDaoSupport implements ILoggerDao {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILoggerDao#logRequest(com.newco.marketplace.vo.security.LoggingVO)
	 * it insert the request xml into the db
	 */
	public LoggingVO logRequest(LoggingVO loggingVO) throws Exception {
		int result = 0;
		try {
			result = (Integer) getSqlMapClient().insert(
					"query.insertapilogging", loggingVO);
		} catch (Exception ex) {
			logger.info("[LoginDaoImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
			throw ex;
		}

		return loggingVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILoggerDao#logResponse(com.newco.marketplace.vo.security.LoggingVO)
	 * it insert the response xml into the db
	 */
	public LoggingVO logResponse(LoggingVO loggingVO) throws Exception {
		int result = 0;
		try {

			result = (Integer) getSqlMapClient().update(
					"query.updateapilogging", loggingVO);
		} catch (Exception ex) {
			logger.info("[LoginDaoImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
			throw ex;
		}

		return loggingVO;
	}

}
