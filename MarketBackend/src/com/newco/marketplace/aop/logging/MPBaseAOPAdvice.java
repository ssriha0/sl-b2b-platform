/*
 * Created on Jun 27, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.newco.marketplace.aop.logging;

import org.aopalliance.intercept.MethodInterceptor;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.logging.ILoggingDao;


/**
 * @author RHarini
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class MPBaseAOPAdvice implements MethodInterceptor{

	private static final Logger logger = Logger.getLogger(MPBaseAOPAdvice.class.getName());
    private ILoggingDao loggingDao = null;
    public void insertChange(SoLoggingVo soLoggingVo){
    	try {
    		loggingDao.insertLog(soLoggingVo);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
    }
	public ILoggingDao getLoggingDao() {
		return loggingDao;
	}
	public void setLoggingDao(ILoggingDao loggingDao) {
		this.loggingDao = loggingDao;
	}
}
