package com.servicelive.wallet.batch.ach.returnfile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.FileUtil;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.lookup.vo.ProviderLookupVO;
import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;
import com.servicelive.wallet.batch.ach.AchFileReader;
import com.servicelive.wallet.batch.ach.BaseAchProcessor;
import com.servicelive.wallet.batch.ach.dao.INachaDao;
import com.servicelive.wallet.batch.ach.vo.AchResponseReasonVO;
import com.servicelive.wallet.batch.ach.vo.AddendaRecordVO;
import com.servicelive.wallet.batch.ach.vo.BatchRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryDetailRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryRecordVO;
import com.servicelive.wallet.batch.ach.vo.FieldDetailVO;
import com.servicelive.wallet.batch.ach.vo.NachaFileVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.IWalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

/**
 * The Class ReturnsFileProcessor.
 */
public class ReturnsFileProcessor extends BaseAchProcessor {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ReturnsFileProcessor.class.getName());

	/** The Constant MAIL_BODY. */
	private static final String MAIL_BODY = "Process " + "Name: ACH Returns File Processor\n" + "Date : " + getCurrentTimestamp() + "\n" + "Exception Details: ";

	/** The ach file reader. */
	private AchFileReader achFileReader;

	/** The nacha dao. */
	private INachaDao nachaDao;

	/** The wallet client bo. */
	private IWalletBO walletBO;
	private IWalletRequestBuilder walletBuilder = new WalletRequestBuilder();
	
	private ILookupBO lookup;


	/**
	 * Construct ach process queue obj.
	 * 
	 * @param entryDetailRecordVO 
	 * @param addendaRecordVO 
	 * 
	 * @return the ach process queue entry vo
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private AchProcessQueueEntryVO constructAchProcessQueueObj(EntryDetailRecordVO entryDetailRecordVO, AddendaRecordVO addendaRecordVO) throws SLBusinessServiceException {

		HashMap<String, FieldDetailVO> addendaMap = addendaRecordVO.getHash(addendaRecordVO.getFieldDetailVO());
		HashMap<String, FieldDetailVO> entryDetailMap = entryDetailRecordVO.getHash(entryDetailRecordVO.getFieldDetailVO());
		HashMap<String, AchResponseReasonVO> rejectReasonHash = null;
		AchProcessQueueEntryVO achProcessQueueEntryVO = new AchProcessQueueEntryVO();

		FieldDetailVO fieldDetailVO_RRC = (FieldDetailVO) addendaMap.get("RETURN_REASON_CODE");
		// HARD CODED FOR NOW
		try {
			rejectReasonHash = nachaDao.getAllReasons(CommonConstants.FILE_TYPE_RETURN);
			String returnReason = fieldDetailVO_RRC.getFieldValue();

			// rejectReasonHash.
			achProcessQueueEntryVO.setRejectReasonId(((AchResponseReasonVO) rejectReasonHash.get(returnReason)).getReasonId());

			FieldDetailVO fieldDetailVO_IIN = (FieldDetailVO) entryDetailMap.get("INDIVIDUAL_ID");
			String ledgerId = fieldDetailVO_IIN.getFieldValue().trim();
			FieldDetailVO fieldDetailVO_RAN = (FieldDetailVO) entryDetailMap.get("AMOUNT");
			String amount = fieldDetailVO_RAN.getFieldValue().trim();
			double dbAmount = Double.valueOf( Long.valueOf(amount) / 100 );
			achProcessQueueEntryVO.setLedgerEntryId( Long.valueOf(ledgerId) );
			achProcessQueueEntryVO.setTransactionAmount(dbAmount);
		} catch (DataServiceException dse) {
			throw new SLBusinessServiceException(dse.getMessage(),dse);
		}
		return achProcessQueueEntryVO;

	}

	/**
	 * Gets the all return records.
	 * 
	 * @param nachaFileVO 
	 * 
	 * @return the all return records
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private ArrayList<AchProcessQueueEntryVO> getAllReturnRecords(NachaFileVO nachaFileVO) throws SLBusinessServiceException {

		ArrayList<BatchRecordVO> batchRecords = (ArrayList<BatchRecordVO>) nachaFileVO.getBatchRecords();
		ArrayList<AchProcessQueueEntryVO> achProcessQueueList = new ArrayList<AchProcessQueueEntryVO>();
		for (int i = 0; i < batchRecords.size(); i++) {
			BatchRecordVO batchRecordVO = (BatchRecordVO) batchRecords.get(i);
			ArrayList<EntryRecordVO> entryRecords = batchRecordVO.getEntryRecords();
			for (int j = 0; j < entryRecords.size(); j++) {
				EntryRecordVO entryRecordVO = (EntryRecordVO) entryRecords.get(j);
				AddendaRecordVO addendaRecordVO = entryRecordVO.getAddendaRecord();
				EntryDetailRecordVO entryDetailRecordVO = entryRecordVO.getEntryDetailRecord();
				achProcessQueueList.add(constructAchProcessQueueObj(entryDetailRecordVO, addendaRecordVO));

			}
		}
		return achProcessQueueList;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {

		ArrayList<NachaFileVO> nachaFileList = null;

		AchResponseReasonVO reasonCodesVO = null;

		/*
		 * Here we read the return file and get data in return file in template
		 * object we get from ach_template table, if there is a reversal
		 * indicator of 1 for reject code we reverse provider(not for buyer)
		 * deposit by getting data from file and updating ach process queue and
		 * ledger_transaction_tabe and send them a email for reject, else don't
		 * do anything but send a warning email for whatever wrong with account
		 */
		try {
			String[] files = FileUtil.getDirectoryFiles(getFileDirectory(CommonConstants.RETURNS_FILE_DIRECTORY));
			if (files != null && files.length > 0) {
				for (int h = 0; h < files.length; h++) {
					String fileName = files[h];
					String fileNameWithDir = getFileDirectory(CommonConstants.RETURNS_FILE_DIRECTORY) + "/" + fileName;
					String archiveFileNameWithDir = getFileDirectory(CommonConstants.RETURNS_FILE_ARCHIVE_DIRECTORY) + fileName;

					
					
					/* 1. Pick up and read the file */
					nachaFileList = achFileReader.objectizeFileContents(fileNameWithDir);
					
					
					
					if (nachaFileList != null && !nachaFileList.isEmpty()) {
						/* 2. Update Database values */
						for (int i = 0; i < nachaFileList.size(); i++) {
							NachaFileVO nachaFileVO = (NachaFileVO) nachaFileList.get(i);
							logger.info("nachaFileVO.getBatchRecords().size()"+nachaFileVO.getBatchRecords().size());
							ArrayList<AchProcessQueueEntryVO> achProcessQueueList = getAllReturnRecords(nachaFileVO);

							for (int j = 0; j < achProcessQueueList.size(); j++) {
								// get data in ach_process_queue table for a
								// particular ledger_entry_id from file return
								// file
								AchProcessQueueEntryVO achRecord = nachaDao.getAchProcessDataFromLedgerId(achProcessQueueList.get(j).getLedgerEntryId());
								
								if (achRecord != null) {

									achRecord.setRejectReasonId(achProcessQueueList.get(j).getRejectReasonId());// reject id from return file
									achRecord.setModifiedDate(getCurrentTimestamp());

									// get all reject codes from our database
									reasonCodesVO = nachaDao.getReasonCodeDetails(achProcessQueueList.get(j).getRejectReasonId());
									
                                   	Long transactionTypeId=nachaDao.getAchtransactionTypeId(achRecord.getLedgerEntryId());
									if (reasonCodesVO != null && reasonCodesVO.getReversalInd() != null && reasonCodesVO.getReversalInd().intValue() == 1)// only reverse if there is a reversal indicator
									{
										// rejectExists = true, so we will
										// reverse buyer or provider's money
										achRecord.setProcessStatusId(CommonConstants.ACH_RETURNED);
										achRecord.setReconciledIndicator(CommonConstants.ACH_RETURNED);
										// for the reversal for vendor withdrawals
										if (achRecord.getLedgerTransactionId() != null && achRecord.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER) {

											
											ProviderLookupVO provider = lookup.lookupProvider(achRecord.getEntityId());
											WalletVO request = walletBuilder.withdrawProviderFundsReversal(
													"SYSTEM", nachaDao.accountIdFromLedgerId(achRecord.getLedgerEntryId()),
													achRecord.getEntityId(), provider.getProviderState(), provider.getProviderV1AccountNumber(),
													achRecord.getTransactionAmount());

											walletBO.withdrawProviderFundsReversal(request);
											
											// Update to database for ledger balance happens here
											// only ledger tables are changed here nothing else with ach_process_queue or log tables
											// is modified here that is done next in returnFileDBCalls() method
											logger.info("Sending failure email to provider " + achRecord.getEntityId());
											if(transactionTypeId.longValue() == CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS){
												alertBO.sendEscheatmentACHFailuretoProdSupp(String.valueOf(achRecord.getLedgerTransactionId()),achRecord.getTransactionAmount(),
														reasonCodesVO.getReasonDesc(),CommonConstants.EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE);
											}else{
											alertBO.sendACHFailureAlertForProvider(achRecord.getEntityId(), String.valueOf(achRecord.getLedgerTransactionId()), achRecord
												.getTransactionAmount(), reasonCodesVO.getReasonDesc(), CommonConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE);
											}

										} else if (achRecord.getLedgerTransactionId() != null && achRecord.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION ) {

											
											Long entityId=nachaDao.entityIdFromLedgerId(achRecord.getLedgerEntryId());
											Long entityTypeId=nachaDao.entityTypeIdFromLedgerId(achRecord.getLedgerEntryId());
											
											if(entityTypeId.longValue()==CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER)
											{
												logger.info("before provider look up");
											ProviderLookupVO provider = lookup.lookupProvider(entityId);
											WalletVO request = walletBuilder.withdrawProviderFundsReversal(
													"SYSTEM", nachaDao.accountIdFromLedgerId(achRecord.getLedgerEntryId()),
													entityId, provider.getProviderState(), provider.getProviderV1AccountNumber(),
													achRecord.getTransactionAmount());

											walletBO.withdrawProviderFundsReversal(request);
											
											// Update to database for ledger balance happens here
											// only ledger tables are changed here nothing else with ach_process_queue or log tables
											// is modified here that is done next in returnFileDBCalls() method
											logger.info("Sending failure email to provider " + achRecord.getEntityId());
											if(transactionTypeId.longValue() == CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS){
												alertBO.sendEscheatmentACHFailuretoProdSupp(String.valueOf(achRecord.getLedgerTransactionId()),achRecord.getTransactionAmount(),
														reasonCodesVO.getReasonDesc(),CommonConstants.EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE);
											}else{
											alertBO.sendACHFailureAlertForProvider(achRecord.getEntityId(), String.valueOf(achRecord.getLedgerTransactionId()), achRecord
												.getTransactionAmount(), reasonCodesVO.getReasonDesc(), CommonConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE);
											}
											}
											else
											{
												
												BuyerLookupVO buyerVO = lookup.lookupBuyer(entityId);
												AdminLookupVO adminVO = lookup.lookupAdmin();
												
												WalletVO request = walletBuilder.adminCreditToBuyer("SYSTEM",
														entityId, buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV1AccountNumber(),
														"Buyer ach reversal",achRecord.getTransactionAmount());

												walletBO.withdrawBuyerdebitReversal(request);
												
												// Update to database for ledger balance happens here
												// only ledger tables are changed here nothing else with ach_process_queue or log tables
												// is modified here that is done next in returnFileDBCalls() method
												logger.info("Sending failure email to provider " + achRecord.getEntityId());
												if(transactionTypeId.longValue() == CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS){
													alertBO.sendEscheatmentACHFailuretoProdSupp(String.valueOf(achRecord.getLedgerTransactionId()),achRecord.getTransactionAmount(),
															reasonCodesVO.getReasonDesc(),CommonConstants.EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE);
												} else {
												alertBO.sendACHFailureAlertForProvider(achRecord.getEntityId(), String.valueOf(achRecord.getLedgerTransactionId()), achRecord
													.getTransactionAmount(), reasonCodesVO.getReasonDesc(), CommonConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE);
												}
												
											}

										}
										else if (achRecord.getLedgerTransactionId() != null && achRecord.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_BUYER) {
											// We don't do a reversal for buyer deposit because if there is a
											// error in buyer we never actually got the money from buyer,so we don't have to return it
											
											long ledgerbusnessTransID = nachaDao.busTransIdFromLedgerId(achRecord.getLedgerEntryId());
											//Adding a check to identify the Escheatment 
											//SL-21501 or buyer withdraw reversal
											if(CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEAT_SLB_FROM_BUYER == ledgerbusnessTransID 
													|| CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER == ledgerbusnessTransID )
											{	
												BuyerLookupVO buyerVO = lookup.lookupBuyer(achRecord.getEntityId());
												AdminLookupVO adminVO = lookup.lookupAdmin();
												
												WalletVO request = walletBuilder.adminCreditToBuyer("SYSTEM",
														achRecord.getEntityId(), buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV1AccountNumber(),
														"Buyer ach reversal",achRecord.getTransactionAmount());

												walletBO.withdrawBuyerdebitReversal(request);
											}
											
											logger.info("Sending failure email to buyer " + achRecord.getEntityId());
											if(transactionTypeId.longValue() == CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS){
												alertBO.sendEscheatmentACHFailuretoProdSupp(String.valueOf(achRecord.getLedgerTransactionId()),achRecord.getTransactionAmount(),
														reasonCodesVO.getReasonDesc(),CommonConstants.EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE);
											} else{
											emailTemplateBO.sendACHFailureEmailToBuyer(achRecord.getEntityId(), String.valueOf(achRecord.getLedgerTransactionId()), achRecord
												.getTransactionAmount(), reasonCodesVO.getReasonDesc(), CommonConstants.EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_FAILURE);
										}
										}
										

									} else // if no reversal indicator we only send a warning email to change
									// the account information and lock the account
									{
										
		 								
										if (achRecord.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER) {
											logger.info("Sending warning email to provider " + achRecord.getEntityId());
											alertBO.sendACHFailureAlertForProvider(achRecord.getEntityId(), String.valueOf(achRecord.getLedgerTransactionId()), achRecord
												.getTransactionAmount(), reasonCodesVO.getReasonDesc(), CommonConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_WARNING);
										} else if (achRecord.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_BUYER) {
											logger.info("Sending warning email to buyer " + achRecord.getEntityId());
											emailTemplateBO.sendACHFailureEmailToBuyer(achRecord.getEntityId(), String.valueOf(achRecord.getLedgerTransactionId()), achRecord
												.getTransactionAmount(), reasonCodesVO.getReasonDesc(), CommonConstants.EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_WARNING);
										}

									}

									// lock the account and update the ach_process_queue in this call specifying
									// rejection occurred for that ledger_entry
									returnFileDBCalls(achRecord);
								}
							}

						}

					}
					// move the file to archive directory
					FileUtil.moveFile(new File(fileNameWithDir), new File(archiveFileNameWithDir));
				}
			}

		} catch (Exception e) {
			logger.error("processReturnsFile()-->EXCEPTION-->", e);
			emailTemplateBO.sendFailureEmail(MAIL_BODY + StackTraceHelper.getStackTrace(e));
			throw new SLBusinessServiceException(e);
		}
	}

	/**
	 * Return file db calls.
	 * 
	 * @param achRecord 
	 */
	private void returnFileDBCalls(AchProcessQueueEntryVO achRecord) {

		try {
			// Updating ach_process_queue table table
			// We don't update ach_process_log and history table as in
			// origination because these tables have entries based on a process
			// for file level like when nacha and origination process ran for
			// whole file,
			// and return entries are for individual entries in nacha file not
			// the whole file
			nachaDao.achProcessQueueReturnUpdate(achRecord);

			// lock the account
			long accountId = nachaDao.accountIdFromLedgerId(achRecord.getLedgerEntryId());
			deactivateAccount(accountId);

		} catch (Exception e) {
			logger.error("returnFileDBCalls()-->EXCEPTION-->", e);
		}
	}

	/**
	 * Sets the ach file reader.
	 * 
	 * @param achFileReader the new ach file reader
	 */
	public void setAchFileReader(AchFileReader achFileReader) {

		this.achFileReader = achFileReader;
	}

	/**
	 * Sets the nacha dao.
	 * 
	 * @param nachaDao the new nacha dao
	 */
	public void setNachaDao(INachaDao nachaDao) {

		this.nachaDao = nachaDao;
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
}
