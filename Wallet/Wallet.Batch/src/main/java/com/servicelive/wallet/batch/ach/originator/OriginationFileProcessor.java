package com.servicelive.wallet.batch.ach.originator;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.DateUtils;
import com.servicelive.common.util.FileUtil;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.lookup.vo.ProviderLookupVO;
import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;
import com.servicelive.wallet.batch.IProcessor;
import com.servicelive.wallet.batch.ach.AchFileReader;
import com.servicelive.wallet.batch.ach.BaseAchProcessor;
import com.servicelive.wallet.batch.ach.dao.INachaDao;
import com.servicelive.wallet.batch.ach.vo.AchResponseReasonVO;
import com.servicelive.wallet.batch.ach.vo.BatchRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryDetailRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryRecordVO;
import com.servicelive.wallet.batch.ach.vo.FieldDetailVO;
import com.servicelive.wallet.batch.ach.vo.NachaFileVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.IWalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

/**
 * The Class OriginationFileProcessor.
 */
public class OriginationFileProcessor extends BaseAchProcessor implements IProcessor {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(OriginationFileProcessor.class.getName());

	/** The Constant MAIL_BODY. */
	private static final String MAIL_BODY = "Process Name: ACH Origination File Processor\n" +
											"Date : " + getCurrentTimestamp() + "\n"+
											"Exception Details: ";

	/** The Constant originationEmailSubject. */
	private static final String originationEmailSubject = "Origination Process ran successfully for Ach file batch Id, ";

	/** The ach file reader. */
	private AchFileReader achFileReader;

	/** The nacha dao. */
	private INachaDao nachaDao;

	/** The wallet client bo. */
	private IWalletBO walletBO;
	private IWalletRequestBuilder walletBuilder = new WalletRequestBuilder();
	
	private ILookupBO lookup;
	

	/**
	 * Gets the ach process queue list.
	 * 
	 * @param entryDetailRecordList 
	 * 
	 * @return the ach process queue list
	 * 
	 * @throws DataServiceException 
	 */
	private ArrayList<AchProcessQueueEntryVO> getAchProcessQueueList(ArrayList<EntryDetailRecordVO> entryDetailRecordList) throws DataServiceException {

		ArrayList<AchProcessQueueEntryVO> achProcessQueueList = new ArrayList<AchProcessQueueEntryVO>();
		if (entryDetailRecordList != null && !entryDetailRecordList.isEmpty()) {
			HashMap<String, AchResponseReasonVO> rejectReasonHash = nachaDao.getAllReasons(CommonConstants.FILE_TYPE_ORIGINATION);

			for (int i = 0; i < entryDetailRecordList.size(); i++) {
				EntryDetailRecordVO entryDetailRecordVO = (EntryDetailRecordVO) entryDetailRecordList.get(i);
				HashMap<String, FieldDetailVO> map = entryDetailRecordVO.getHash(entryDetailRecordVO.getFieldDetailVO());
				FieldDetailVO fieldDetail_ledgerID = (FieldDetailVO) map.get("INDIVIDUAL_ID");
				FieldDetailVO fieldDetail_traceNumber = (FieldDetailVO) map.get("TRACE_NUMBER");
				FieldDetailVO fieldDetail_amount = (FieldDetailVO) map.get("AMOUNT");
				AchProcessQueueEntryVO achProcessQueueEntryVO = new AchProcessQueueEntryVO();

				achProcessQueueEntryVO.setLedgerEntryId( Long.valueOf(fieldDetail_ledgerID.getFieldValue().trim()) );

				String strAmount = StringUtils.trim(fieldDetail_amount.getFieldValue());
				String integerPart = strAmount.substring(0, strAmount.length() - 2);
				integerPart = String.valueOf(Integer.parseInt(integerPart));
				String decimalPart = strAmount.substring(strAmount.length() - 2, strAmount.length());
				strAmount = integerPart + "." + decimalPart;
				Double amount = Double.valueOf(strAmount);

				achProcessQueueEntryVO.setTransactionAmount(amount);
				String traceNumber = fieldDetail_traceNumber.getFieldValue().trim();
				String rejectPattern = traceNumber.substring(0, 4);
				String reasonCode = traceNumber.substring(4, 8);
				if (rejectPattern.equals(CommonConstants.ORIGINATION_REJECT_PATTERN)) {
					AchResponseReasonVO rejectReasonVO = (AchResponseReasonVO) rejectReasonHash.get(reasonCode);
					String rejectReasonCode = rejectReasonVO.getReasonCode();

					// only reverse if there is a reversal indicator
					if (rejectReasonVO.getReversalInd() != null && rejectReasonVO.getReversalInd().intValue() == 1)
					{
						achProcessQueueEntryVO.setRejectIndicator(true);
						achProcessQueueEntryVO.setRejectCode(rejectReasonCode);
						achProcessQueueEntryVO.setRejectReasonId(rejectReasonVO.getReasonId());
						achProcessQueueEntryVO.setReconciledIndicator(CommonConstants.ACH_REJECTED);

					}
				} else {
					achProcessQueueEntryVO.setReconciledIndicator(CommonConstants.ACH_SUCCESS);

				}

				achProcessQueueList.add(achProcessQueueEntryVO);
			}
		}
		return achProcessQueueList;
	}

	/**
	 * Gets the all entry detail records.
	 * 
	 * @param nachaFileVO 
	 * 
	 * @return the all entry detail records
	 */
	private ArrayList<EntryDetailRecordVO> getAllEntryDetailRecords(NachaFileVO nachaFileVO) {

		ArrayList<EntryDetailRecordVO> entryDetailRecordList = null;
		if (nachaFileVO != null) {
			ArrayList<BatchRecordVO> batchList = (ArrayList<BatchRecordVO>) nachaFileVO.getBatchRecords();

			if (batchList != null && !batchList.isEmpty()) {
				entryDetailRecordList = new ArrayList<EntryDetailRecordVO>();
				for (BatchRecordVO batchRecordVO : batchList) {
					ArrayList<EntryRecordVO> entryRecords = batchRecordVO.getEntryRecords();
					if (entryRecords != null && !entryRecords.isEmpty()) {
						for (EntryRecordVO entryRecordVO : entryRecords) {
							EntryDetailRecordVO entryDetailRecordVO = entryRecordVO.getEntryDetailRecord();
							entryDetailRecordList.add(entryDetailRecordVO);
						}
					}
				}
			}
		}
		return entryDetailRecordList;
	}

	/**
	 * Gets the business owner email.
	 * 
	 * @return the business owner email
	 * 
	 * @throws DataServiceException 
	 */
	private String getBusinessOwnerEmail() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.BUSINESS_OWNER);
	}

	/**
	 * Gets the business owner phone number.
	 * 
	 * @return the business owner phone number
	 * 
	 * @throws DataServiceException 
	 */
	private String getBusinessOwnerPhoneNumber() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.BUSINESS_OWNER_PHONE_NUMBER);
	}

	/**
	 * Gets the email subject.
	 * 
	 * @param batchId 
	 * @param rejectIndicator 
	 * 
	 * @return the email subject
	 */
	private String getEmailSubject(long batchId, boolean rejectIndicator) {

		String emailSubject = "";
		if (rejectIndicator) {
			emailSubject = originationEmailSubject + batchId + " and has rejects";
		} else {
			emailSubject = originationEmailSubject + batchId + " and has no rejects";

		}
		return emailSubject;
	}

	/**
	 * Gets the nacha process log.
	 * 
	 * @param achProcessQueueEntryVO 
	 * 
	 * @return the nacha process log
	 * 
	 * @throws DataServiceException 
	 */
	private NachaProcessLogVO getNachaProcessLog(AchProcessQueueEntryVO achProcessQueueEntryVO) throws DataServiceException {

		NachaProcessLogVO nachaProcessLogVO = null;
		if (achProcessQueueEntryVO != null) {
			nachaProcessLogVO = new NachaProcessLogVO();
			nachaProcessLogVO.setModifiedDate(getCurrentTimestamp());
			long ledgerEntryId = achProcessQueueEntryVO.getLedgerEntryId();
			long nachaProcessLogId = nachaDao.getAchProcessId(ledgerEntryId);
			nachaProcessLogVO.setNachaProcessId(nachaProcessLogId);
		}
		return nachaProcessLogVO;
	}

	/**
	 * Gets the nacha process log history.
	 * 
	 * @param achProcessLogId 
	 * 
	 * @return the nacha process log history
	 * 
	 * @throws DataServiceException 
	 */
	private NachaProcessLogHistoryVO getNachaProcessLogHistory(long achProcessLogId) throws DataServiceException {

		NachaProcessLogHistoryVO nachaProcessLogHistoryVO = null;
		nachaProcessLogHistoryVO = new NachaProcessLogHistoryVO();
		nachaProcessLogHistoryVO.setAchProcessLogId(achProcessLogId);
		nachaProcessLogHistoryVO.setAchProcessStatusId(CommonConstants.ORIGINATION_FILE_PROCESSED);
		nachaProcessLogHistoryVO.setUpdatedBy(CommonConstants.ORIGINATION_PROCESS_OWNER);
		nachaProcessLogHistoryVO.setUpdatedDate(getCurrentTimestamp());
		nachaProcessLogHistoryVO.setComments(CommonConstants.ORIGINATION_COMPLETION_MESSAGE);
		return nachaProcessLogHistoryVO;
	}

	/**
	 * Gets the no reply email.
	 * 
	 * @return the no reply email
	 * 
	 * @throws DataServiceException 
	 */
	private String getNoReplyEmail() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.NO_REPLY);
	}

	/**
	 * Gets the pager alert email.
	 * 
	 * @return the pager alert email
	 * 
	 * @throws DataServiceException 
	 */
	private String getPagerAlertEmail() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.SERVICELIVE_ADMIN);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {

		try {
			String[] files = FileUtil.getDirectoryFiles(getFileDirectory(CommonConstants.ORIGINATION_FILE_DIRECTORY));
			if (files == null) {
				emailTemplateBO.sendNotificationEmail(getPagerAlertEmail(), CommonConstants.ORIGINATION_PROCESS_FAILURE_SUBJECT,
					CommonConstants.ORIGINATION_PROCESS_FAILURE_BODY);
			}
			if (files != null && files.length > 0) {
				for (String fileName : files) {
					String fileNameWithDir = getFileDirectory(CommonConstants.ORIGINATION_FILE_DIRECTORY) + fileName;
					String archiveFileWithDir = getArchiveFileDirectory(CommonConstants.ORIGINATION_FILE_ARCHIVE_DIRECTORY) + fileName;

					ArrayList<NachaFileVO> nachaFileList = achFileReader.objectizeFileContents(fileNameWithDir);

					// We may have concatenated origination files so we loop through nachaFileList which represent values from origination file , but usually nachaFileList will have size of 1
					if (nachaFileList != null && !nachaFileList.isEmpty()) {

						Double voAmt = new Double(0.0);
						BigDecimal sumTransAmount = new BigDecimal(0.0);

						for (int i = 0; i < nachaFileList.size(); i++) {
							NachaFileVO nachaFileVO = (NachaFileVO) nachaFileList.get(i);

							ArrayList<FieldDetailVO> controlRecords = (ArrayList<FieldDetailVO>) nachaFileVO.getFileControlRecordVO().getFieldDetailVO();
							if (controlRecords != null && !controlRecords.isEmpty()) {
								for (FieldDetailVO fieldDetailVO : controlRecords) {
									if (StringUtils.equalsIgnoreCase(fieldDetailVO.getFieldName(), "TOTAL_DEBIT_ENTRY")) {
										nachaFileVO.getFileControlRecordVO().setTotalDebitAmount(fieldDetailVO.getFieldValue());
									} else if (StringUtils.equalsIgnoreCase(fieldDetailVO.getFieldName(), "TOTAL_CREDIT_ENTRY")) {
										nachaFileVO.getFileControlRecordVO().setTotalCreditAmount(fieldDetailVO.getFieldValue());
									}
								}
							}

							ArrayList<EntryDetailRecordVO> allEntryDetailList = getAllEntryDetailRecords(nachaFileVO);

							if (allEntryDetailList != null && !allEntryDetailList.isEmpty()) {
		
								ArrayList<AchProcessQueueEntryVO> achProcessQueueEntryVOList = getAchProcessQueueList(allEntryDetailList);
		
								NachaProcessLogVO nachaProcessLogVO;
								NachaProcessLogHistoryVO nachaProcessLogHistoryVO;
		
								boolean doneOnce = false;
								long logIdHold = 0;
								Set nachaLogIdSet = new HashSet();
		
								if (achProcessQueueEntryVOList != null && achProcessQueueEntryVOList.size() > 0) {
									for (AchProcessQueueEntryVO achProcessQueueEntryVO : achProcessQueueEntryVOList) {
		
										// first time stuff
										nachaProcessLogVO = getNachaProcessLog(achProcessQueueEntryVO);
										Long logId = nachaProcessLogVO.getNachaProcessId();
										if (logId != null) {
											boolean added = nachaLogIdSet.add(logId.longValue());
											if (logIdHold != logId.longValue() && added) {
		
												if (StringUtils.isNumeric(nachaFileVO.getFileControlRecordVO().getTotalCreditAmount())) {
													nachaProcessLogVO.setTotalCreditAmount((Double.valueOf(nachaFileVO.getFileControlRecordVO().getTotalCreditAmount())) / 100);
												} else {
													nachaProcessLogVO.setTotalCreditAmount(0.0);
												}
		
		
												if (StringUtils.isNumeric(nachaFileVO.getFileControlRecordVO().getTotalDebitAmount())) {
													nachaProcessLogVO.setTotalDebitAmount((Double.valueOf(nachaFileVO.getFileControlRecordVO().getTotalDebitAmount())) / 100);
												} else {
													nachaProcessLogVO.setTotalDebitAmount(0.0);
												}
		
												nachaProcessLogVO.setOriginationRunDate(DateUtils.getCurrentTimeStamp());
												nachaProcessLogHistoryVO = getNachaProcessLogHistory(nachaProcessLogVO.getNachaProcessId());
		
		
												// Updates ach_process_log with
												// 50 and total debit and credit
												// amount
												this.nachaDao.orginationProcessUpdatesForAchProceesLog(nachaProcessLogVO, nachaProcessLogHistoryVO);
		
												// the HashMap will contain the
												// result of: SELECT
												// COALESCE(sum(trans_amount),0.0)
												// as
												// sumTransAmount,COALESCE(count(1),0.0)
												// AS totalCount from
												// ach_process_queue where
												// nacha_process_log_id=#processLogId#
												HashMap<String, BigDecimal> processLogMap = nachaDao.getSumAchProcessByLogId(nachaProcessLogVO.getNachaProcessId());
												if (!doneOnce){
													voAmt = MoneyUtil.getRoundedMoney(nachaProcessLogVO.getTotalCreditAmount() + nachaProcessLogVO.getTotalDebitAmount());
													doneOnce = true;
												}
												sumTransAmount = sumTransAmount.add((BigDecimal) processLogMap.get("sumTransAmount"));
												logIdHold = logId;
											}
										}
										// first time stuff end 
										AchProcessQueueEntryVO achProcessQueueEntryVOFromOrigFile = (AchProcessQueueEntryVO) achProcessQueueEntryVO;								
										
										AchProcessQueueEntryVO achRecordFromDb = nachaDao.getAchProcessDataFromLedgerId(achProcessQueueEntryVOFromOrigFile.getLedgerEntryId());
										achRecordFromDb.setReconciledIndicator(achProcessQueueEntryVOFromOrigFile.getReconciledIndicator());
										achRecordFromDb.setModifiedDate(getCurrentTimestamp());

										// only reverse if there is a reversal indicator
										if (achProcessQueueEntryVOFromOrigFile.isRejectIndicator()) {

											// rejectExists = true, so we will reverse buyer or provider's money
											achRecordFromDb.setRejectCode(achProcessQueueEntryVOFromOrigFile.getRejectCode());
											achRecordFromDb.setRejectReasonId(achProcessQueueEntryVOFromOrigFile.getRejectReasonId());
											achRecordFromDb.setProcessStatusId(CommonConstants.ACH_RETURNED);

											// lock the account on reject per SL-8437
											long accountId = nachaDao.accountIdFromLedgerId(achRecordFromDb.getLedgerEntryId());
											deactivateAccount(accountId);
											
											// how do we deal with emails to provider and buyer in batch???
											AchResponseReasonVO reasonCodesVO = null;

											// Used to retrieve Map containing rejection reason codes and reason description for withdrawal reversal during ACH reject
											reasonCodesVO = nachaDao.getReasonCodeDetails(achRecordFromDb.getRejectReasonId());
											Long transactionTypeId=nachaDao.getAchtransactionTypeId(achRecordFromDb.getLedgerEntryId());

											if (achRecordFromDb.getLedgerTransactionId() != null
												&& achRecordFromDb.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER) {
												
												ProviderLookupVO provider = lookup.lookupProvider(achRecordFromDb.getEntityId());
												
												WalletVO request = walletBuilder.withdrawProviderFundsReversal(
													"SYSTEM", nachaDao.accountIdFromLedgerId(achRecordFromDb.getLedgerEntryId()),
													achRecordFromDb.getEntityId(), provider.getProviderState(), 
													provider.getProviderV1AccountNumber(), 
													achRecordFromDb.getTransactionAmount());
												
													walletBO.withdrawProviderFundsReversal(request);
												
												// reverseTheProviderWithdrawl(achRecordFromDb);
												if(transactionTypeId.longValue() == CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS){
													alertBO.sendEscheatmentACHFailuretoProdSupp(String.valueOf(achRecordFromDb.getLedgerTransactionId()),achRecordFromDb.getTransactionAmount(),
															reasonCodesVO.getReasonDesc(),CommonConstants.EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE);
												}else{
												
												alertBO.sendACHFailureAlertForProvider(achRecordFromDb.getEntityId(), String.valueOf(achRecordFromDb.getLedgerTransactionId()),
													achRecordFromDb.getTransactionAmount(), reasonCodesVO.getReasonDesc(),
													CommonConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE);
												}

											} else if (achRecordFromDb.getLedgerTransactionId() != null && achRecordFromDb.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION ) {

												logger.info("inside provider type");
												
												Long entityId=nachaDao.entityIdFromLedgerId(achRecordFromDb.getLedgerEntryId());
												logger.info("entityId.longValue() "+entityId.longValue());
												Long entityTypeId=nachaDao.entityTypeIdFromLedgerId(achRecordFromDb.getLedgerEntryId());
												logger.info("entityTypeId.longValue()"+entityTypeId.longValue());
												logger.info("achRecord.getTransactionAmount()"+achRecordFromDb.getTransactionAmount());
												
												if(entityTypeId.longValue()==CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER)
												{
													logger.info("before provider look up");
												ProviderLookupVO provider = lookup.lookupProvider(entityId);
												WalletVO request = walletBuilder.withdrawProviderFundsReversal(
														"SYSTEM", nachaDao.accountIdFromLedgerId(achRecordFromDb.getLedgerEntryId()),
														entityId, provider.getProviderState(), provider.getProviderV1AccountNumber(),
														achRecordFromDb.getTransactionAmount());

												walletBO.withdrawProviderFundsReversal(request);
												
												// Update to database for ledger balance happens here
												// only ledger tables are changed here nothing else with ach_process_queue or log tables
												// is modified here that is done next in returnFileDBCalls() method
												logger.info("Sending failure email to provider " + achRecordFromDb.getEntityId());
												if(transactionTypeId.longValue() == CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS){
													alertBO.sendEscheatmentACHFailuretoProdSupp(String.valueOf(achRecordFromDb.getLedgerTransactionId()),achRecordFromDb.getTransactionAmount(),
															reasonCodesVO.getReasonDesc(),CommonConstants.EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE);
												}else{
												alertBO.sendACHFailureAlertForProvider(achRecordFromDb.getEntityId(), String.valueOf(achRecordFromDb.getLedgerTransactionId()), achRecordFromDb
													.getTransactionAmount(), reasonCodesVO.getReasonDesc(), CommonConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE);
												}
												}
												else
												{
													
													logger.info("inside buyer reversal");
													BuyerLookupVO buyerVO = lookup.lookupBuyer(entityId);
													
													WalletVO request = walletBuilder.adminCreditToBuyer("SYSTEM",
															entityId, buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV1AccountNumber(),
															"Buyer ach reversal",achRecordFromDb.getTransactionAmount());

													walletBO.withdrawBuyerdebitReversal(request);
													
													// Update to database for ledger balance happens here
													// only ledger tables are changed here nothing else with ach_process_queue or log tables
													// is modified here that is done next in returnFileDBCalls() method
													logger.info("Sending failure email to provider " + achRecordFromDb.getEntityId());
													if(transactionTypeId.longValue() == CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS){
														alertBO.sendEscheatmentACHFailuretoProdSupp(String.valueOf(achRecordFromDb.getLedgerTransactionId()),achRecordFromDb.getTransactionAmount(),
																reasonCodesVO.getReasonDesc(),CommonConstants.EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE);
													}else{
													alertBO.sendACHFailureAlertForProvider(achRecordFromDb.getEntityId(), String.valueOf(achRecordFromDb.getLedgerTransactionId()), achRecordFromDb
														.getTransactionAmount(), reasonCodesVO.getReasonDesc(), CommonConstants.EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE);
													}
													
												}

											}else if (achRecordFromDb.getLedgerTransactionId() != null
												&& achRecordFromDb.getEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_BUYER) {
												// reverseTheBuyerDeposit(achRecordFromDb);
												// We don't do a reversal for buyer deposit because if there is a error in buyer we never actually got the money from buyer,so we don't have to return it
												
												//Adding a check to identify the Escheatment
												if(CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEAT_SLB_FROM_BUYER == nachaDao.busTransIdFromLedgerId(achRecordFromDb.getLedgerEntryId())){
													logger.info("inside buyer reversal");
													BuyerLookupVO buyerVO = lookup.lookupBuyer(achRecordFromDb.getEntityId());
													AdminLookupVO adminVO = lookup.lookupAdmin();
													
													WalletVO request = walletBuilder.adminCreditToBuyer("SYSTEM",
															achRecordFromDb.getEntityId(), buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV1AccountNumber(),
															"Buyer ach reversal",achRecordFromDb.getTransactionAmount());

													walletBO.withdrawBuyerdebitReversal(request);
												} 
												
												if(transactionTypeId.longValue() == CommonConstants.TRANSACTION_TYPE_ID_ESCHEAT_FUNDS){
													alertBO.sendEscheatmentACHFailuretoProdSupp(String.valueOf(achRecordFromDb.getLedgerTransactionId()),achRecordFromDb.getTransactionAmount(),
															reasonCodesVO.getReasonDesc(),CommonConstants.EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE);
												}else{
												emailTemplateBO.sendACHFailureEmailToBuyer(achRecordFromDb.getEntityId(), String.valueOf(achRecordFromDb.getLedgerTransactionId()),
													achRecordFromDb.getTransactionAmount(), reasonCodesVO.getReasonDesc(),
													CommonConstants.EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_FAILURE);
											}
											}

											NachaProcessLogVO nachaProcessLogVOForReject = getNachaProcessLog(achRecordFromDb);
											sendEmail(getEmailSubject(nachaProcessLogVOForReject.getNachaProcessId(), achProcessQueueEntryVOFromOrigFile.isRejectIndicator()));

										} else {
											achRecordFromDb.setProcessStatusId(CommonConstants.ORIGINATION_FILE_PROCESSED);
										}

										// we update ach record in database here. Note we don't disable user accounts here in origination as we do in return process on reject or warning entry
										nachaDao.orginationProcessUpdatesForAchProceesQueue(achRecordFromDb);

										nachaDao.adjustConsolidatedRecordOnOrigination(achProcessQueueEntryVOFromOrigFile.getLedgerEntryId());

									}

								}

							}
							//Check totals and send the error email if they don not match.  But do not stop the show.
							if (sumTransAmount == null || (sumTransAmount.doubleValue() != (voAmt))) {
								String errorMsg = "\n" + CommonConstants.ACH_ORIGINATION_TOTAL_MISMATCH + "\nDB amount = " + sumTransAmount + "\nVO amount = " + voAmt;
								this.emailTemplateBO.sendFailureEmail(errorMsg);
								logger.error(errorMsg);
							}
							// write code for adding orig totals to nacha process log table
						}
					}
					// move the file to archive directory
					FileUtil.moveFile(new File(fileNameWithDir), new File(archiveFileWithDir));
				}
			}

		} catch (Exception e) {
			logger.error("error happened while processing origination file", e);
			emailTemplateBO.sendFailureEmail(MAIL_BODY + StackTraceHelper.getStackTrace(e));
		}
	}

	/**
	 * Send email.
	 * 
	 * @param subject 
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void sendEmail(String subject) throws SLBusinessServiceException {

		try {
			emailTemplateBO.sendTemplateEmail(getBusinessOwnerEmail(), getNoReplyEmail(), subject, "Origination Email", CommonConstants.EMAIL_TEMPLATE_ORIGINATION);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
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
