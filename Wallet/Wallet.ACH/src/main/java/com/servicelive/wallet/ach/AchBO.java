package com.servicelive.wallet.ach;

import java.util.Date;

import com.servicelive.wallet.ach.vo.AutoFundingVO;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.ach.dao.IAchBatchRequestDao;
import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;
import com.servicelive.wallet.serviceinterface.vo.AchVO;

// TODO: Auto-generated Javadoc
/**
 * Class AchBO.
 */
public class AchBO implements IAchBO {

	/** logger. */
	private static final Logger logger = Logger.getLogger(AchBO.class.getName());

	/** achBatchRequestDao. */
	private IAchBatchRequestDao achBatchRequestDao;

	/**
	 * createAchEntry.
	 * 
	 * @param accountId 
	 * @param entryTypeId 
	 * @param entityTypeId 
	 * @param entityId 
	 * @param amount 
	 * @param fundingTypeId 
	 * @param transactionTypeId 
	 * @param achBatchAssocId 
	 * @param ledgerEntryId 
	 * 
	 * @return AchProcessQueueEntryVO
	 */
	private AchProcessQueueEntryVO createAchEntry(long accountId, int entryTypeId, long entityTypeId, long entityId, double amount, long fundingTypeId, long transactionTypeId,
		int achBatchAssocId, long ledgerEntryId) {

		AchProcessQueueEntryVO queueEntry = new AchProcessQueueEntryVO();
		queueEntry.setFundingTypeId(fundingTypeId);
		queueEntry.setAccountId(accountId);
		queueEntry.setEntityId(entityId);
		queueEntry.setEntityTypeId(entityTypeId);
		queueEntry.setProcessStatusId(CommonConstants.ACH_UNPROCESSED);
		queueEntry.setTransactionAmount(amount);
		queueEntry.setTransactionEntryTypeId(entryTypeId);
		queueEntry.setTransactionTypeId(transactionTypeId);
		queueEntry.setAchBatchAssocId(achBatchAssocId);
		queueEntry.setReconciledIndicator(0);//set it to be not reconciled
		queueEntry.setLedgerEntryId(ledgerEntryId);
		return queueEntry;
	}

	/**
	 * createACHEntry.
	 * 
	 * @param queueEntry 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void createACHEntry(AchProcessQueueEntryVO queueEntry) throws SLBusinessServiceException {

		try {
			// For non funded buyers we do not have to create ach process queue entries - JIRA SL-6172
			//If we have a db lock, we get ledger entry id as 0, SL-9634
			if ((queueEntry.getFundingTypeId() != CommonConstants.FUNDING_TYPE_NON_FUNDED
				|| queueEntry.getEntityTypeId() != CommonConstants.LEDGER_ENTITY_TYPE_BUYER) && queueEntry.getLedgerEntryId() != 0  &&
				queueEntry.getTransactionAmount() > 0.0 ) {
				
				Long achProcessId = achBatchRequestDao.createAchQueueEntry(queueEntry);
				queueEntry.setAchProcessId(achProcessId);
				// update the ach_transcode_acc_id in ach_process_queue
				achBatchRequestDao.updateAchQueueEntry(queueEntry);
				/**This code is written to update the ach_process_queue id in 
				account_hs_auth_resp for authorization of buyer deposit by cc */
				if(queueEntry.isHsLiveFlag() && null != queueEntry.getHsAuthRespId() && null!= achProcessId){
				  achBatchRequestDao.updateHsAuthResponse(achProcessId,queueEntry.getHsAuthRespId());
				}
			}

		} catch (Exception e) {
			logger.error("fundACHAccountAction-->Exception-->", e);
			throw new SLBusinessServiceException("fundACHAccountAction-->EXCEPTION-->", e);
		}

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#createConsolidatedEntryForAutoAch(long, double, int, long, long, long)
	 */
	public void createConsolidatedEntryForAutoAch(long ledgerEntryId, double transAmount, int achBatchAssocId, long accountId, long entityId, long entityTypeId, Date runDate)
		throws SLBusinessServiceException {

		try{
			logger.info("RUN DATE -- Start creation"+runDate.toString());
			AchProcessQueueEntryVO consolidatedEntry = new AchProcessQueueEntryVO();
			consolidatedEntry.setCreatedDate(runDate);
			consolidatedEntry.setTransactionTypeId(CommonConstants.AUTO_ACH_CONSOLIDATED_ENTRY);
			consolidatedEntry.setAccountId(accountId);
			consolidatedEntry.setEntityId(entityId);
			consolidatedEntry.setEntityTypeId(entityTypeId);
			consolidatedEntry.setAchBatchAssocId(achBatchAssocId);
			consolidatedEntry.setLedgerEntryId(ledgerEntryId);
			consolidatedEntry.setProcessStatusId(CommonConstants.ACH_UNPROCESSED);
			consolidatedEntry.setTransactionAmount(transAmount);
			consolidatedEntry.setReconciledIndicator(0);//set it to be not reconciled
			logger.info("RUN DATE -- After insert"+runDate.toString());			
			Long achProcessId = achBatchRequestDao.createAchQueueEntry(consolidatedEntry);
			consolidatedEntry.setAchProcessId(achProcessId);
			logger.info("RUN DATE -- Before update"+runDate.toString());
			logger.info("achProcessId -- Before update"+achProcessId);
			consolidatedEntry.setCreatedDate(runDate);
			achBatchRequestDao.updateAchCreatedDate(consolidatedEntry);
			logger.info("RUN DATE -- After update"+runDate.toString());
		}catch(Exception e){
			logger.error("createConsolidatedEntryForAutoAch", e);
			throw new SLBusinessServiceException("createConsolidatedEntryForAutoAch", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#createConsolidatedEntryForCC(long, double, int, int)
	 */
	public void createConsolidatedEntryForCC(long ledgerEntryId, double transAmount, int transactionTypeId, int achBatchAssocId, Date runDate) throws SLBusinessServiceException {

		try{
			AchProcessQueueEntryVO consolidatedEntry = new AchProcessQueueEntryVO();
			consolidatedEntry.setCreatedDate(runDate);
			consolidatedEntry.setTransactionTypeId(transactionTypeId);
			consolidatedEntry.setAccountId(CommonConstants.JPM_ACCOUNT_ID);
			consolidatedEntry.setEntityId(CommonConstants.JPM_ENTITY_ID);
			consolidatedEntry.setEntityTypeId(CommonConstants.SERVICELIVE_ENTITY_TYPE_ID);
			consolidatedEntry.setAchBatchAssocId(achBatchAssocId);
			consolidatedEntry.setLedgerEntryId(ledgerEntryId);
			consolidatedEntry.setProcessStatusId(CommonConstants.ACH_UNPROCESSED);
			consolidatedEntry.setTransactionAmount(transAmount);
			consolidatedEntry.setReconciledIndicator(0);//set it to be not reconciled
			Long achProcessId = achBatchRequestDao.createAchQueueEntry(consolidatedEntry);
			consolidatedEntry.setAchProcessId(achProcessId);
			consolidatedEntry.setCreatedDate(runDate);
			achBatchRequestDao.updateAchCreatedDate(consolidatedEntry);
		}catch(Exception e){
			logger.error("createConsolidatedEntryForAutoAch", e);
			throw new SLBusinessServiceException("createConsolidatedEntryForAutoAch", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#depositBuyerFundsWithCash(com.servicelive.wallet.ach.vo.AchVO)
	 */
	public void depositBuyerFundsWithCash(AchVO request) throws SLBusinessServiceException {

		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_DEBIT, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, request.getBuyerId(), request.getAmount(), request
				.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_DEPOSIT_CASH, 1, request.getLedgerEntryId());

		createACHEntry(queueEntry);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#depositBuyerFundsWithCreditCard(com.servicelive.wallet.ach.vo.AchVO)
	 */
	public void depositBuyerFundsWithCreditCard(AchVO request) throws SLBusinessServiceException {

		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_DEBIT, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, request.getBuyerId(), request.getAmount(), request
				.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_CREDIT_DEPOSIT, CommonConstants.CREDIT_CARD_AUTH_TRANS_CODE_ID, request.getLedgerEntryId());
		
		//updating ach_process_queue id in account_hs_auth_resp
		if( request.isHsWebLive() && null != request.getHsAuthRespId()){
			queueEntry.setHsAuthRespId(request.getHsAuthRespId());
			queueEntry.setHsLiveFlag(request.isHsWebLive());
		}
		createACHEntry(queueEntry);	
		
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#depositBuyerFundsWithInstantACH(com.servicelive.wallet.ach.vo.AchVO)
	 */
	public void depositBuyerFundsWithInstantACH(AchVO request) throws SLBusinessServiceException {
		if (request.getAccountId() == null){
            request.setAccountId(getAccountIdForAutoFundedBuyer(request.getBuyerId()));
        }
        AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_DEBIT, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, request.getBuyerId(), request.getAmount(), request
				.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_INSTANT_ACH_DEPOSIT, 1, request.getLedgerEntryId());

		createACHEntry(queueEntry);
	}

    private Long getAccountIdForAutoFundedBuyer(Long buyerId) throws SLBusinessServiceException {
        logger.info("Getting the account id for the auto funded buyer " + buyerId);        
        try {
            AutoFundingVO fundingVO = this.achBatchRequestDao.getAutoFundingVO(buyerId, CommonConstants.LEDGER_ENTITY_TYPE_BUYER);
            if (fundingVO == null) {
                throw new SLBusinessServiceException("Auto funding service record not found for buyer " + buyerId);
            }
            else {
                logger.info("Got the account id for the buyer " + fundingVO.getAccountId());
                return fundingVO.getAccountId();
            }
        } catch (com.servicelive.common.exception.DataServiceException e) {
            throw new SLBusinessServiceException("Exception while reading auto_funding_service table", e);
        }
    }
	/**
	 * setAchBatchRequestDao.
	 * 
	 * @param achBatchRequestDao 
	 * 
	 * @return void
	 */
	public void setAchBatchRequestDao(IAchBatchRequestDao achBatchRequestDao) {

		this.achBatchRequestDao = achBatchRequestDao;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#withdrawBuyerFundsWithInstantACH(com.servicelive.wallet.ach.vo.AchVO)
	 */
	public void withdrawBuyerFundsWithInstantACH(AchVO request) throws SLBusinessServiceException {
        if (request.getAccountId() == null){
            request.setAccountId(getAccountIdForAutoFundedBuyer(request.getBuyerId()));
        }
		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_CREDIT, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, request.getBuyerId(), request.getAmount(), request
				.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_INSTANT_ACH_REFUND,1, request.getLedgerEntryId());

		createACHEntry(queueEntry);

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#withdrawProviderFunds(com.servicelive.wallet.ach.vo.AchVO)
	 */
	public void withdrawProviderFunds(AchVO request) throws SLBusinessServiceException {
		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_CREDIT, CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER, request.getProviderId(), request.getAmount(),
				request.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH, 1, request.getLedgerEntryId());

		createACHEntry(queueEntry);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#withdrawProviderFunds(com.servicelive.wallet.ach.vo.AchVO)
	 */
	public void withdrawBuyerFundsCreditCard(AchVO request) throws SLBusinessServiceException {
		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_CREDIT, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, request.getBuyerId(), request.getAmount(),
				request.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_CREDIT_WITHDRAWAL, 1, request.getLedgerEntryId());
		//updating ach_process_queue id in account_hs_auth_resp
		if( request.isHsWebLive() && null != request.getHsAuthRespId()){
			queueEntry.setHsAuthRespId(request.getHsAuthRespId());
			queueEntry.setHsLiveFlag(request.isHsWebLive());
		}
		createACHEntry(queueEntry);
	}

	public void withdrawBuyerFunds(AchVO request) throws SLBusinessServiceException {
		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_CREDIT, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, request.getBuyerId(), request.getAmount(),
				request.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH, 1, request.getLedgerEntryId());

		createACHEntry(queueEntry);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#depositOperationFunds(com.servicelive.wallet.ach.vo.AchVO)
	 */
	public void depositOperationFunds(AchVO request) throws SLBusinessServiceException {
		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_DEBIT, CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION, CommonConstants.ENTITY_ID_SERVICELIVE_OPERATION, request.getAmount(),
				request.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_DEPOSIT_CASH, 1, request.getLedgerEntryId());

		createACHEntry(queueEntry);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.IAchBO#withdrawOperationFunds(com.servicelive.wallet.ach.vo.AchVO)
	 */
	public void withdrawOperationFunds(AchVO request) throws SLBusinessServiceException {
		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_CREDIT, CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION, CommonConstants.ENTITY_ID_SERVICELIVE_OPERATION, request.getAmount(),
				request.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH, 1, request.getLedgerEntryId());

		createACHEntry(queueEntry);
	}

    public boolean isEntityAutoFunded(Long entityId, Integer entityTypeId) throws SLBusinessServiceException {
        try{
            AutoFundingVO autoFundingVO = achBatchRequestDao.getAutoFundingVO(entityId, entityTypeId);
            if (autoFundingVO != null){
                return true;
            }else{
                return false;
            }
        }catch (com.servicelive.common.exception.DataServiceException dse) {
            throw new SLBusinessServiceException("isAutoFunded", dse);
        }
    }
    
	public void escheatBuyerFunds(AchVO request) throws SLBusinessServiceException {
		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_CREDIT, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, request.getBuyerId(), request.getAmount(),
				request.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH, 1, request.getLedgerEntryId());

		createACHEntry(queueEntry);
}

	public void escheatProviderFunds(AchVO request) throws SLBusinessServiceException {
		AchProcessQueueEntryVO queueEntry =
			createAchEntry(request.getAccountId(), CommonConstants.ENTRY_TYPE_CREDIT, CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER, request.getProviderId(), request.getAmount(),
				request.getFundingTypeId(), CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH, 1, request.getLedgerEntryId());

		createACHEntry(queueEntry);
	}
}
