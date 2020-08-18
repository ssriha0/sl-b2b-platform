package com.newco.marketplace.persistence.daoImpl.feemanager;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.feemanager.FeeDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class FeeDaoImpl extends ABaseImplDao implements FeeDao {
	private static final Logger logger = Logger.getLogger(FeeDaoImpl.class.getName());
	public Integer getAccessFee(MarketPlaceTransactionVO marketVO)throws DataServiceException
	{
		Integer accessFee;
		
		try {
		
			accessFee = (Integer)queryForObject("mvo.query", marketVO);
        }
        catch (Exception ex) {
        	logger.info("[FeeDaoImpl.query - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        
		return accessFee;
	}

}
