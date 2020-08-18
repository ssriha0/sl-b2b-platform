package com.newco.marketplace.util;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.nacha.NachaDao;

/**
 * $Revision: 1.12 $ $Author: akashya $ $Date: 2008/05/30 19:20:51 $
 */
public class AchFDUtil {
	
	private static final Logger logger = Logger.getLogger(AchFDUtil.class.getName());
	private ILedgerFacilityBO accountingTransactionManagementImpl;
	public double calculateFirstDataCredit (NachaDao nachaDao) throws BusinessServiceException{
		Double totalCreditAmount = null;
		try {
			totalCreditAmount = nachaDao.getTotalCredit().getTransAmount();
		}catch(DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
		double totalCredit=0.00;
		if (totalCreditAmount!=null) {
			totalCredit= totalCreditAmount.doubleValue();
		}
		return totalCredit;
	}
	
/*	public long addFirstDataCreditToProcessQueue(NachaDao nachaDao, double totalCredit) throws BusinessServiceException {
		AchProcessQueueEntryVO queueEntry = new AchProcessQueueEntryVO();

	try{
		RandomGUID randomIdGenerator = new RandomGUID();
		queueEntry.setAccountId(AchConstants.JPM_ACCOUNT_ID);
		queueEntry.setAchProcessId(randomIdGenerator.generateGUID().longValue());
		queueEntry.setEntityId(AchConstants.JPM_ENTITY_ID);
		queueEntry.setEntityTypeId(AchConstants.SERVICELIVE_ENTITY_TYPE_ID);
		queueEntry.setProcessStatusId(AchConstants.FIRSTDATA_PROCESS_STATUS_ID);
		queueEntry.setTransactionAmount(totalCredit);
		queueEntry.setTransactionTypeId(AchConstants.FIRSTDATA_TRANSACTION_TYPE_ID);
		queueEntry.setTransactionEntryTypeId(LedgerConstants.ENTRY_TYPE_DEBIT);
		queueEntry.setAchBatchAssocId(AchConstants.FIRSTDATA_ACH_BATCH_ID);
		//queueEntry.setLedgerEntryId(NachaUtil.getConsolidatedLedgerId());
		queueEntry.setLedgerEntryId(new TransactionGUID().getTransactionId());
		System.out.println("Ledger Id "+queueEntry.getLedgerEntryId());
		MarketPlaceTransactionVO service =  new MarketPlaceTransactionVO();
		service.setBusinessTransId(AchConstants.FIRSTDATA_BUSINESS_TRANS_ID);
		service.setBuyerID(AchConstants.FIRSTDATA_ENTITY_ID);
		service.setCCInd(false);
		
		accountingTransactionManagementImpl.fundACHAccountAction(queueEntry,service);
		Long a = queueEntry.getAchProcessId();
		return a;
	} catch (NumberFormatException e1) {
		logger.info("Caught Exception",e1);
		throw new BusinessServiceException(e1);
	} catch (Exception e2) {
		logger.info("Caught Exception",e2);
		throw new BusinessServiceException(e2);

	}
}
*/


	public ILedgerFacilityBO getAccountingTransactionManagementImpl() {
		return accountingTransactionManagementImpl;
	}

	public void setAccountingTransactionManagementImpl(
			ILedgerFacilityBO accountingTransactionManagementImpl) {
		this.accountingTransactionManagementImpl = accountingTransactionManagementImpl;
	}
	
}
