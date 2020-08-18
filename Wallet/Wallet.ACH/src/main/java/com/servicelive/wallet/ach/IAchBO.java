package com.servicelive.wallet.ach;

import java.util.Date;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.vo.AchVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IAchBO.
 */
public interface IAchBO {

	/**
	 * createConsolidatedEntryForAutoAch.
	 * 
	 * @param ledgerEntryId 
	 * @param transAmount 
	 * @param achBatchAssocId 
	 * @param accountId 
	 * @param entityId 
	 * @param entityTypeId 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void createConsolidatedEntryForAutoAch(long ledgerEntryId, double transAmount, int achBatchAssocId, long accountId, long entityId, long entityTypeId, Date runDate)
		throws SLBusinessServiceException;

	/**
	 * createConsolidatedEntryForCC.
	 * 
	 * @param ledgerEntryId 
	 * @param transAmount 
	 * @param transactionTypeId 
	 * @param achBatchAssocId 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void createConsolidatedEntryForCC(long ledgerEntryId, double transAmount, int transactionTypeId, int achBatchAssocId, Date runDate) throws SLBusinessServiceException;

	/**
	 * depositBuyerFundsWithCash.
	 * 
	 * @param request 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void depositBuyerFundsWithCash(AchVO request) throws SLBusinessServiceException;

	/**
	 * depositBuyerFundsWithCreditCard.
	 * 
	 * @param request 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void depositBuyerFundsWithCreditCard(AchVO request) throws SLBusinessServiceException;

	/**
	 * depositBuyerFundsWithInstantACH.
	 * 
	 * @param request 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void depositBuyerFundsWithInstantACH(AchVO request) throws SLBusinessServiceException;

	/**
	 * withdrawBuyerFundsWithInstantACH.
	 * 
	 * @param request 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void withdrawBuyerFundsWithInstantACH(AchVO request) throws SLBusinessServiceException;

	/**
	 * withdrawProviderFunds.
	 * 
	 * @param request 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void withdrawProviderFunds(AchVO request) throws SLBusinessServiceException;

	/**
	 * withdrawBuyerFunds.
	 * 
	 * @param request 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void withdrawBuyerFunds(AchVO request) throws SLBusinessServiceException;

	/**
	 * @param request
	 * @throws SLBusinessServiceException
	 */
	public void withdrawBuyerFundsCreditCard(AchVO request) throws SLBusinessServiceException;
	
	/**
	 * depositOperationFunds.
	 * 
	 * @param ach 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void depositOperationFunds(AchVO ach)throws SLBusinessServiceException;

	/**
	 * withdrawOperationFunds.
	 * 
	 * @param ach 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void withdrawOperationFunds(AchVO ach)throws SLBusinessServiceException;

    /**
     * 
     * @param entityId
     * @param entityTypeId
     * @return
     * @throws SLBusinessServiceException
     */
    public boolean isEntityAutoFunded(Long entityId, Integer entityTypeId) throws SLBusinessServiceException;
    
    /**
     * 
     * @param ach
     * @return void
     * @throws SLBusinessServiceException
     */
	public void escheatProviderFunds(AchVO ach) throws SLBusinessServiceException;
	
	/**
     * 
     * @param ach
     * @return void
     * @throws SLBusinessServiceException
     */
	public void escheatBuyerFunds(AchVO ach) throws SLBusinessServiceException;
}
