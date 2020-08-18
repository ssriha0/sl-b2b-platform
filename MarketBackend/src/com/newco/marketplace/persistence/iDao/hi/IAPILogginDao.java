package com.newco.marketplace.persistence.iDao.hi;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.hi.provider.APILoggingVO;

public interface IAPILogginDao {

	/**
	 * @param loggingVO
	 * @return
	 * @throws DataServiceException
	 */
	public Integer logAPIHistoryRequest(APILoggingVO loggingVO) throws DataServiceException;

	/**
	 * @param loggingVO
	 * @throws DataServiceException
	 */
	public void updateAPIHistory(APILoggingVO loggingVO)throws DataServiceException;
	
	public String apiLoggingSwitch() throws DataServiceException; 

  
}
