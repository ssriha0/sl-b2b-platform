package com.newco.marketplace.persistence.iDao.feemanager;


import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface FeeDao {
	public Integer getAccessFee(MarketPlaceTransactionVO marketVO)throws DataServiceException;

}
