package com.newco.marketplace.persistence.iDao.feemanager;

import java.util.Map;

import com.newco.marketplace.dto.vo.fee.FeeInfoVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface FeeManagerDao {
	
	public FeeInfoVO getLedgerFee(MarketPlaceTransactionVO marketVO)throws DataServiceException;
	public FeeInfoVO getLedgerFeeAndAmount(MarketPlaceTransactionVO marketVO) throws DataServiceException;
	public Map<String, String> getLedgerRulePricingExpressionsMap()throws DataServiceException;

}
