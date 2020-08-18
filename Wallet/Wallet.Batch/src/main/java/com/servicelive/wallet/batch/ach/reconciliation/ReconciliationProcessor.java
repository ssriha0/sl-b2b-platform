package com.servicelive.wallet.batch.ach.reconciliation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.log4j.Logger;


import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.DateUtils;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.wallet.batch.BaseProcessor;
import com.servicelive.wallet.batch.ach.dao.INachaDao;
import com.servicelive.wallet.batch.ach.vo.NachaProcessQueueVO;
import com.servicelive.wallet.ledger.dao.ITransactionDao;
import com.servicelive.wallet.ledger.vo.LedgerBalanceVO;
import com.servicelive.wallet.ledger.vo.TransactionEntryVO;
import com.servicelive.wallet.ledger.vo.TransactionVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.IWalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

// TODO: Auto-generated Javadoc
/**
 * The Class ReconciliationProcessor.
 */
public class ReconciliationProcessor extends BaseProcessor {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ReconciliationProcessor.class.getName());

	/** The nacha dao. */
	private INachaDao nachaDao;

	/** The transaction dao. */
	private ITransactionDao transactionDao;

	/** The wallet client bo. */
	private IWalletBO walletBO;
	private IWalletRequestBuilder walletBuilder = new WalletRequestBuilder();
	
	private ILookupBO lookup;

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {

		int processLogId = 0;
		Integer entityId = 0;
		Long ledgerId = null;
		Integer transactionTypeId = 0;
		String transactionId = null;
		double transAmount = 0.0;
		java.util.Date modifiedDate = null;
		java.util.Date now = new java.util.Date();
		TransactionVO transactionVO;
		boolean byPassReconciliationflag = false;
		try {
			int reconciledDays = Integer.parseInt(applicationProperties.getPropertyValue(CommonConstants.RECONCILATION_WORKING_DAYS));
			List<NachaProcessQueueVO> list = nachaDao.getReconciledData();
            //SL-18329 Buyers configured to bypass 5 day pending reconcilation flag for Deposits
			//Needs to make it Table driven for SL-18329
			HashMap<Integer, Integer> buyerReconcilationMap = null; 
			//buyerReconcilationMap.put("9",0);
			buyerReconcilationMap = nachaDao.getBuyerExceptionReconciliationMap();
			
			for (NachaProcessQueueVO queue : list) {
				transactionVO = new TransactionVO();
				processLogId = queue.getAchProcessId();
				modifiedDate = queue.getEntryDate();
				entityId = queue.getEntityId();
				transAmount = queue.getTransAmount();
				ledgerId = queue.getLedgerEntryId();
				transactionTypeId = queue.getTransactionTypeId();
				if(transactionTypeId.intValue() == new Long(CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS).intValue()){
					transactionTypeId = new Long(CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH).intValue();
				}
				int daysDiff = 0;
				if (modifiedDate != null) {
					daysDiff = DateUtils.workingDays(modifiedDate, now);
				}
				//Added a new condition to chek whether it is a SL-Admin Deposits or any specified buyer
				byPassReconciliationflag = false;
				if(null != buyerReconcilationMap && buyerReconcilationMap.size() > 0 ){
					byPassReconciliationflag = bypassBuyerDepositsReconcilationPeriod(entityId,transactionTypeId,buyerReconcilationMap,daysDiff);
					logger.info("byPassReconciliationflag::"+byPassReconciliationflag);
				}
				if (daysDiff >= reconciledDays || byPassReconciliationflag ) {
					logger.info("Inside date diff check and new check");
					//SL-20168 : Passing ledger_entry_id
					nachaDao.updateReconciledData(processLogId, queue.getLedgerEntryId());
					transactionDao.markLedgerEntryReconciled(queue.getLedgerEntryId());
					//We need to manually invoke the storedproc replacement as updating the modified date was intended to invoke the trigger that has now been replaced
					transactionVO = transactionDao.getLedgerEntryByEntryId(ledgerId,new Long(queue.getEntityTypeId()));
					transactionVO.setTransactions(transactionDao.getTransactionEntriesByLedgerEntry(queue.getLedgerEntryId()));
					if (transactionTypeId != CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH) {
						//transactionDao.updateLedgerTransEntryModifiedDate(new java.util.Date(), queue.getLedgerEntryId()); // update the modified_date
						invokeLedgerBalanceCreation(transactionVO);
					}
					if (transactionVO == null || transactionVO.getBusinessTransId() == null) {
						transactionId = "0";
					} else if (transactionVO.getBusinessTransId() == CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER) {
						BuyerLookupVO buyer = lookup.lookupBuyer(Long.valueOf(entityId));
						transactionId = transactionVO.getEmailTransactionId();
						WalletVO request = walletBuilder.depositBuyerFundsAtValueLink(
							"", entityId.longValue(), buyer.getBuyerState(), buyer.getBuyerV1AccountNumber(),
							buyer.getBuyerV2AccountNumber(), buyer.getBuyerFundingTypeId(), transAmount);
						request.setBusinessTransactionId(transactionVO.getBusinessTransId());
						walletBO.depositBuyerFundsAtValueLink(request);

					} else if (transactionVO.getBusinessTransId() == CommonConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS) {
						
						AdminLookupVO admin = lookup.lookupAdmin();
						transactionId = transactionVO.getEmailTransactionId();
						WalletVO request = walletBuilder.depositSLOperationFundsAtValueLink(admin.getSl3AccountNumber(), transAmount);
						request.setBusinessTransactionId(transactionVO.getBusinessTransId());
						walletBO.depositSLOperationFundsAtValueLink(request);
					} 
					//SL-11774, need to add condition for business_transaction_id = 20, i.e.provider withdraw funds, 
					//otherwise, the transactionId will always be null for this type of business transaction.
					else if (transactionVO.getBusinessTransId() == CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER) {
						transactionId = transactionVO.getEmailTransactionId();
					}
	
					if (transactionTypeId != null
						&& (transactionTypeId == CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH || transactionTypeId == CommonConstants.TRANSACTION_TYPE_ID_DEPOSIT_CASH)) {
						// Get both buyer or vendor administrator and account contact person
						if (queue.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER) {
							alertBO.sendSettlementConfirmationToProvider(queue.getEntityId(), transactionId, transAmount,
								CommonConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_SUCCESS);
						} else if (queue.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_BUYER && transactionVO != null
							&& transactionVO.getBusinessTransId() == CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER) {
							alertBO.sendSettlementConfirmationToBuyer(queue.getEntityId(), transactionId, transAmount,
								CommonConstants.EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_CONFIRMATION, "SimpleBuyer");
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("Error occured while updating the Ach_process_queue status by scheduler", e);
			throw new SLBusinessServiceException("Error occured while updating the Ach_process_queue status by scheduler",e);
		}
	}
	//
	private boolean bypassBuyerDepositsReconcilationPeriod(Integer entityId,Integer transactionTypeId,HashMap<Integer, Integer> buyerReconcilationMap,int daysDiff)
	{
	    //Check the table for specific buyers deposits	
		//entityId is the buyer
		int expReconciledDays = 0;

		if(buyerReconcilationMap.containsKey(new Long(entityId.intValue())) && transactionTypeId.equals(new Integer(100))){
			//logger.info("Class ::"+buyerReconcilationMap.get(new Long(entityId.intValue())).getClass());
			try{
				logger.info("Getting value from map ::"+buyerReconcilationMap.get(new Long(entityId)));
			}catch(Exception e){
				logger.error(e);
			}
			//Did not have any other option but to write this code to make things work
			int pos = Arrays.asList(buyerReconcilationMap.keySet().toArray()).indexOf(new Long(entityId));
			expReconciledDays =  ((Long)Arrays.asList(buyerReconcilationMap.values().toArray()).get(pos)).intValue();
			//expReconciledDays = 0;//buyerReconcilationMap.get(new Long(entityId.intValue())).intValue();
			logger.info("expReconciledDays::"+expReconciledDays);
			if(daysDiff >= expReconciledDays){
				return true;
			}
		}
		return false;
		
		
	}
	private  void invokeLedgerBalanceCreation(TransactionVO tranVO)
	{
		try
		{
			ArrayList<TransactionEntryVO> tranEntryVOList = tranVO.getTransactions();
			if (tranEntryVOList != null && !tranEntryVOList.isEmpty()) {
				for (int j = 0; j < tranEntryVOList.size(); j++) {
					TransactionEntryVO transactionEntryVO = tranEntryVOList.get(j);
					transactionEntryVO.setLedgerEntryId(tranVO.getLedgerEntryId());
					
					LedgerBalanceVO existingLedgerBalanceVO = new LedgerBalanceVO();
					existingLedgerBalanceVO.setLedgerEntityId(transactionEntryVO.getLedgerEntityId());
					existingLedgerBalanceVO.setLedgerEntityTypeId(new Long(transactionEntryVO.getLedgerEntityTypeId()).intValue());
					
					if(transactionEntryVO.getLedgerEntityTypeId()==30)
					{
						existingLedgerBalanceVO.setLedgerEntityId(transactionEntryVO.getOriginatingBuyerId());
						existingLedgerBalanceVO.setLedgerEntityTypeId(10);
					}
					existingLedgerBalanceVO = transactionDao.lockEntityBalance(existingLedgerBalanceVO);
					if(existingLedgerBalanceVO == null)
					{
						existingLedgerBalanceVO = new LedgerBalanceVO();
						existingLedgerBalanceVO.setLedgerEntityId(transactionEntryVO.getLedgerEntityId());
						existingLedgerBalanceVO.setLedgerEntityTypeId(new Long(transactionEntryVO.getLedgerEntityTypeId()).intValue());
					}
					transactionDao.createLedgerBalanceEntry(transactionEntryVO,existingLedgerBalanceVO);
				}
			}
		}catch(Exception e)
		{
			logger.error("Error occured in method invokeLedgerBalanceCreation", e);
		}
	}
	/**
	 * Sets the wallet client bo.
	 * 
	 * @param walletClientBO the new wallet client bo
	 */
	public void setWalletBO(IWalletBO walletBO) {

		this.walletBO = walletBO;
	}
	public void setWalletRequestBuilderBO(IWalletRequestBuilder walletBuilder) {

		this.walletBuilder = walletBuilder;
	}
	
	public void setLookup(ILookupBO lookup) {
	
		this.lookup = lookup;
	}
	public void setNachaDao(INachaDao nachaDao) {
		this.nachaDao = nachaDao;
	}
	public void setTransactionDao(ITransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}
}
