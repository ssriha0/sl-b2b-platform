package com.newco.marketplace.api.utils.mappers.wallet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.beans.wallet.WalletBalanceResponse;

public class WalletBalanceMapper {
	private Logger logger = Logger.getLogger(WalletHistoryMapper.class);

	/**
	 * This method is for mapping financeManager Object to WalletBalanceResponse Object.
	 * 
	 * @param pendingBalance double, projectBalance double, availableBalance double
	 * @return WalletBalanceResponse
	 */
	public WalletBalanceResponse mapWalletBallanceResponse(double pendingBalance,double projectBalance,double availableBalance) {
		if(logger.isInfoEnabled()) logger.info("Inside WalletBalanceMapper.mapWalletBallanceResponse()");
		
		Results results = Results.getSuccess();		
		WalletBalanceResponse walletBalanceResponse = new WalletBalanceResponse(results,pendingBalance,projectBalance,availableBalance);
		return walletBalanceResponse;
	}
}
