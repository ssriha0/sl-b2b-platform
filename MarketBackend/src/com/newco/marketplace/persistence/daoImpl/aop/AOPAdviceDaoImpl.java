/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.aop;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.aop.AOPAdviceVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.aop.IAOPAdviceDao;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author schavda
 *
 */
public class AOPAdviceDaoImpl  extends ABaseImplDao implements IAOPAdviceDao {
	
	private static final Logger logger = Logger.getLogger(AOPAdviceDaoImpl.class.getName());
	
	@SuppressWarnings("unchecked")
	public List<AOPAdviceVO> getAOPAdvices (String actionName) throws DataServiceException{
		List<AOPAdviceVO> alAOPAdviceVOs = new ArrayList<AOPAdviceVO>();
        try {
        	alAOPAdviceVOs = queryForList("aopAdvices.query", actionName);
        } 
        catch (Exception ex) {
        	String message = "Could not query advices to be invoked";
        	logger.fatal(message, ex);
            throw new DataServiceException(message, ex);
        }
        
        return alAOPAdviceVOs;
	}

	public AOPAdviceVO getCachingEvent (Integer cachingEventId) throws DataServiceException{
		AOPAdviceVO aopAdviceVO = new AOPAdviceVO();
        try {
        	aopAdviceVO = (AOPAdviceVO)queryForObject("aopAdviceCache.query", cachingEventId);
        } 
        catch (Exception ex) {
        	String message = "Could not query caching advice";
        	logger.fatal(message, ex);
            throw new DataServiceException(message, ex);
        }
		return aopAdviceVO;
	}
}
