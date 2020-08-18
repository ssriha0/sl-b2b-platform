package com.newco.marketplace.persistence.daoImpl.hi;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ledger.ReceivedAmountVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.hi.IAPILogginDao;
import com.newco.marketplace.vo.hi.provider.APILoggingVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class APILoggingDaoImpl extends ABaseImplDao implements IAPILogginDao {
	private static final Logger LOGGER = Logger.getLogger(APILoggingDaoImpl.class);
	
	public Integer logAPIHistoryRequest(APILoggingVO loggingVO)throws DataServiceException {
		Integer loggingId =null;
		try{
			loggingId = (Integer) insert("logApiHistoryRequest.insert", loggingVO);
		}catch (Exception e) {
		  LOGGER.error("Exception in logging API request for HI firm");
		  throw new DataServiceException(e.getMessage());
		}
		if(null!= loggingId){
			return loggingId;
		}
		return loggingId;
	}
	public void updateAPIHistory(APILoggingVO loggingVO)throws DataServiceException {
		try{
			update("logApiHistoryResponse.update", loggingVO);
		}catch (Exception e) {
		  LOGGER.error("Exception in logging API response for HI firm");
		  throw new DataServiceException(e.getMessage());
		}
	}
	
	public String apiLoggingSwitch() throws DataServiceException{
		return (String)this.queryForObject("apiLoggingSwitch.query",null);

	}

}
