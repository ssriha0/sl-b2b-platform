package com.newco.marketplace.business.businessImpl.ledger;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.ledger.ILedgerTransactionBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.ledger.TransactionDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.IWalletBO;

public class LedgerTransactionBOImpl implements ILedgerTransactionBO {

	private TransactionDao transactionDao;
	private static final Logger logger = Logger.getLogger(LedgerTransactionBOImpl.class);

	private IWalletBO walletBO;
	
	public boolean isDepositOverLimit(int buyerId, double fundingAmount)  throws BusinessServiceException{
		boolean bOverLimit = false;
		try{
			bOverLimit = ((walletBO.getBuyerTotalDeposit((long)buyerId) + fundingAmount) >= getBuyerDepositLimit()) ;
		}
		catch (SLBusinessServiceException ex) {
			logger.error("isDepositOverLimit-->DataServiceException-->",ex);
			throw new BusinessServiceException("isDepositOverLimit-->EXCEPTION-->", ex);
		} catch (Exception e) {
			logger.error("isDepositOverLimit-->Exception-->", e);
			throw new BusinessServiceException("isDepositOverLimit-->EXCEPTION-->", e);
		}
		return bOverLimit;
	}

	private double getBuyerDepositLimit(){
		return Double.parseDouble(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SIMPLE_BUYER_DEPOSIT_LIMIT));
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	/**
	 * Sets the wallet client bo.
	 * 
	 * @param walletClientBO the new wallet client bo
	 */
	public void setWalletBO(IWalletBO walletBO) {

		this.walletBO = walletBO;
	}
}