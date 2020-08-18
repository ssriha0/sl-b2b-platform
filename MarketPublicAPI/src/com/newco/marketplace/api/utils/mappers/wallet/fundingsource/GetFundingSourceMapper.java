package com.newco.marketplace.api.utils.mappers.wallet.fundingsource;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.fundingsources.GetFundingSourceResponse;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;

/**
 * @author ndixit
 * Maps the response data with the bean.
 * TODO : To be removed, this is not used anymore.
 */
public class GetFundingSourceMapper {
	private Logger logger = Logger.getLogger(GetFundingSourceMapper.class);
	
	public GetFundingSourceResponse mapGetFundingSourceRequest(
			List<Account> accountList, CreditCardVO creditCardVO) {
		
		logger.info("Entering mapGetFundingSourceRequest method");
		GetFundingSourceResponse getFundingSourceResponse = new GetFundingSourceResponse();
		
		return getFundingSourceResponse;
	}


}
