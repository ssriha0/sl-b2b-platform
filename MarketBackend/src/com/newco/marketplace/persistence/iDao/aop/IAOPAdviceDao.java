/**
 * 
 */
package com.newco.marketplace.persistence.iDao.aop;

import java.util.List;

import com.newco.marketplace.dto.vo.aop.AOPAdviceVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author schavda
 *
 */
public interface IAOPAdviceDao {

	public List<AOPAdviceVO> getAOPAdvices (String actionName) throws DataServiceException;
	
	public AOPAdviceVO getCachingEvent (Integer cachingEventId) throws DataServiceException;
	
}
