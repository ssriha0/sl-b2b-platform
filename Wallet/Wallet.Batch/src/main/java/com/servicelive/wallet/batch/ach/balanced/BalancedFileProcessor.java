package com.servicelive.wallet.batch.ach.balanced;

import java.io.File;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.FileUtil;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.wallet.ach.IAchBO;
import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;
import com.servicelive.wallet.batch.BaseFileProcessor;
import com.servicelive.wallet.batch.ach.NachaUtil;
import com.servicelive.wallet.batch.ach.NachaValidationException;
import com.servicelive.wallet.batch.ach.dao.INachaDao;
import com.servicelive.wallet.batch.ach.dao.INachaMetaDataDao;
import com.servicelive.wallet.batch.ach.vo.BatchControlRecordVO;
import com.servicelive.wallet.batch.ach.vo.BatchHeaderRecordVO;
import com.servicelive.wallet.batch.ach.vo.BatchRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryRecordVO;
import com.servicelive.wallet.batch.ach.vo.FieldDetailVO;
import com.servicelive.wallet.batch.ach.vo.FileControlRecordVO;
import com.servicelive.wallet.batch.ach.vo.FileHeaderRecordVO;
import com.servicelive.wallet.batch.ach.vo.NachaErrorVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessQueueVO;
import com.servicelive.wallet.batch.trans.vo.TransactionBatchVO;
import com.servicelive.wallet.common.IIdentifierDao;

/**
 * AchFileProcessor.java - This class generates the NACHA data with all information required for
 * a NACHA extract. It generates the following logical records:
 * File Record Header, Batch Record Header, Entry Detail,
 * Addenda detail, Batch Control and File Control Records.
 * 
 * @author Siva
 * @version 1.0
 */
public class BalancedFileProcessor extends BaseFileProcessor {

	/** The Constant fileNamePrefix. */
	private static final String fileNamePrefix = "ach_";

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(BalancedFileProcessor.class.getName());

	/** The Constant MAIL_BODY. */
	private static final String MAIL_BODY = "Process Name: ACH Balanced File Processor\n" + "Date : " + getCurrentTimestamp() + "\n" + "Exception Details: ";

	/** The ach bo. */
	protected IAchBO achBO;

	/** The identifier dao. */
	protected IIdentifierDao identifierDao;

	/** The nacha dao. */
	protected INachaDao nachaDao;

	/** The nacha meta data dao. */
	protected INachaMetaDataDao nachaMetaDataDao;

	/** The nacha transformer. */
	protected NachaTransformer nachaTransformer;

	/**
	 * This private method returns a list batchIds that the program needs to process.
	 * 
	 * @return List returns a list of transactionBatchVO
	 * 
	 * @throws SLBusinessServiceException 
	 */

	private ArrayList<TransactionBatchVO> getBatchesFromTransQueue(Date runDate) throws SLBusinessServiceException {

		ArrayList<TransactionBatchVO> transactionBatch = new ArrayList<TransactionBatchVO>();
		try {
			transactionBatch = nachaMetaDataDao.retrieveUniqueBatchIds(runDate);
		} catch (Exception e) {
			throw new SLBusinessServiceException(CommonConstants.ACH_TRANSACTION_BATCH_ID_RETRIEVAL_ERROR + e.getStackTrace(),e);
		}
		return transactionBatch;
	}

	/**
	 * This private method returns a list of user specific account detail for the passed batchId.
	 * The batchId is an id that is tagged to each of the transaction based on the CCD (Cash Concentration or Disbursement)
	 * or PPD (Prearranged Payment and Deposit Entry)
	 * 
	 * @param userTransMap 
	 * 
	 * @return List returns a list of NachaProcessQueueVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private List<NachaProcessQueueVO> getUserTransactionList(HashMap<String, String> userTransMap) throws SLBusinessServiceException {

		List<NachaProcessQueueVO> list = null;
		try {
			list = nachaDao.getNachaExtract(userTransMap);
		} catch (Exception e) {
			logger.info("[AchFileProcessor.getUserTransactionList - Exception] " + StackTraceHelper.getStackTrace(e));
			throw new SLBusinessServiceException(CommonConstants.ACH_QUEUE_TRANSACTION_RETRIEVAL_ERROR + e.getStackTrace(),e);
		}
		return list;

	}

	/**
	 * Insert nacha process history log.
	 * 
	 * @param achProcessLogId 
	 * @param achProcessStatusId 
	 * @param updatedTimestamp 
	 * @param comments 
	 * @param updatedBy 
	 * 
	 * @return the long
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private Long insertNachaProcessHistoryLog(long achProcessLogId, int achProcessStatusId, Timestamp updatedTimestamp, String comments, String updatedBy)
		throws SLBusinessServiceException {

		Long id = null;
		try {
			NachaProcessLogHistoryVO nachaProcessLogHistoryVO = new NachaProcessLogHistoryVO();

			nachaProcessLogHistoryVO.setAchProcessLogId(achProcessLogId);
			nachaProcessLogHistoryVO.setAchProcessStatusId(achProcessStatusId);
			nachaProcessLogHistoryVO.setComments(comments);
			nachaProcessLogHistoryVO.setUpdatedBy(updatedBy);
			nachaProcessLogHistoryVO.setUpdatedDate(updatedTimestamp);
			id = nachaDao.insertNachaProcessHistoryLog(nachaProcessLogHistoryVO);
		} catch (Exception e) {
			logger.info("[AchFileProcessor.addNachaProcessLog - Exception] " + StackTraceHelper.getStackTrace(e));
			throw new SLBusinessServiceException(CommonConstants.ACH_PROCESS_HISTORY_LOG_ERROR + e.getStackTrace(),e);
		}
		return id;

	}

	/**
	 * Insert nacha process log.
	 * 
	 * @param machineName 
	 * @param fileName 
	 * @param timestamp 
	 * 
	 * @return the long
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private Long insertNachaProcessLog(String machineName, String fileName, Timestamp timestamp) throws SLBusinessServiceException {

		Long id = null;
		try {
			NachaProcessLogVO nachaProcessLogVO = new NachaProcessLogVO();
			nachaProcessLogVO.setCreatedDate(timestamp);
			nachaProcessLogVO.setGeneratedMachineName(machineName);
			nachaProcessLogVO.setGeneratedFileName(fileName);
			nachaProcessLogVO.setInitiatedManually("N");
			nachaProcessLogVO.setProcessStatusId(1);
			id = nachaDao.insertNachaProcessLog(nachaProcessLogVO);

		} catch (Exception e) {
			logger.info("[AchFileProcessor.addNachaProcessLog - Exception] " + StackTraceHelper.getStackTrace(e));
			throw new SLBusinessServiceException(CommonConstants.ACH_PROCESS_LOG_ERROR + e.getStackTrace(),e);
		}
		return id;

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		java.sql.Timestamp startDate = new java.sql.Timestamp(cal.getTime().getTime());
		process(startDate);
	}
	
	public void process(Date startDate) throws SLBusinessServiceException {

		TransactionBatchVO transactionBatchVO = null;
		String machineName = "";
		try {
			machineName = InetAddress.getLocalHost().getHostName() + "( " + (InetAddress.getLocalHost().getHostAddress()) + " )";

			String encryptProp = this.applicationProperties.getPropertyFromDB("nacha_file_encrypt");
		
			boolean encrypt = false;
			if(encryptProp != null && encryptProp.equalsIgnoreCase("true"))
				encrypt = true;
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			
			java.sql.Timestamp sDate = new java.sql.Timestamp(cal.getTime().getTime());
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 250);//SL-20103
			java.sql.Timestamp endDate = new java.sql.Timestamp(cal.getTime().getTime());

			logger.info("START DATE "+startDate.toString());
			logger.info("END DATE "+startDate.toString());
			transactionBatchVO = processBatches(sDate, endDate);

			String fileName = NachaUtil.getFileName(fileNamePrefix);
			String fileNameWithDir = getFileDirectory(CommonConstants.NACHA_FILE_DIRECTORY) + fileName;
			String fileNameWithDirError = getFileDirectory(CommonConstants.NACHA_FILE_DIRECTORY) + "error/" + fileName;
			String archiveFileNameWithDir = getFileDirectory(CommonConstants.NACHA_FILE_ARCHIVE_DIRECTORY) + fileName;
			
			logger.info("***************CREDIT" + transactionBatchVO.getTotalCreditAmount());
			logger.info("***************DEBIT" + transactionBatchVO.getTotalDebitAmount());

			if (transactionBatchVO != null && !transactionBatchVO.isAmountMismatchFlag()) {
				NachaProcessLogVO nachaProcessLogVO = nachaTransformer.createNachaProcessLog(transactionBatchVO, sDate, machineName, fileNameWithDir);

				if (transactionBatchVO.getAchProcessIds() != null && transactionBatchVO.getAchProcessIds().size() > 0) {
					nachaDao.setStatusCompleteFlag(transactionBatchVO);
				}

				nachaDao.updatetNachaProcessLog(nachaProcessLogVO);
				HashMap<String, BigDecimal> processLogMap = nachaDao.getSumAchProcessByLogId(transactionBatchVO.getProcessLogId());
				BigDecimal sumTransAmount = (BigDecimal) processLogMap.get("sumTransAmount");
				BigDecimal totalCount = (BigDecimal) processLogMap.get("totalCount");
				Double sumVoTransAmt =  MoneyUtil.getRoundedMoney(transactionBatchVO.getTotalCreditAmount() + transactionBatchVO.getTotalDebitAmount());

				Timestamp timestamp = getCurrentTimestamp();
				logger.info("Nacha Records");
				logger.info(transactionBatchVO.getAchRecordsOutput().toString());
				
				if (sumTransAmount.doubleValue() != sumVoTransAmt) {
					logger.info("sumTransAmount.doubleValue() != sumVoTransAmt");
					File outputFile = new File(fileNameWithDirError);
					FileUtil.writeStringToFile(outputFile, transactionBatchVO.getAchRecordsOutput().toString());
					logger.error("[NACHAFileImpl.writeNachaRecordsToFile] AMOUNT MISMATCH:\nrecord amount =  " + sumTransAmount.doubleValue()  + "\nvo amount =  " + sumVoTransAmt);
					emailTemplateBO.sendFailureEmail("System : " + machineName + " \n" + MAIL_BODY + CommonConstants.ACH_AMT_MISMATCH_APQ_VO_FAILURE +
							"\nAMOUNT MISMATCH:\nrecord amount =  " + sumTransAmount.doubleValue()  + "\nvo amount =  " + sumVoTransAmt);
				} else if (totalCount.longValue() != transactionBatchVO.getRecordCounter()) {
					logger.info("totalCount.longValue() != transactionBatchVO.getRecordCounter()");
					File outputFile = new File(fileNameWithDirError);
					FileUtil.writeStringToFile(outputFile, transactionBatchVO.getAchRecordsOutput().toString());
					logger.error("[NACHAFileImpl.writeNachaRecordsToFile] COUNT MISMATCH: \nrecord count =  " + totalCount.longValue() + "\nvo count =  " + transactionBatchVO.getRecordCounter());
					emailTemplateBO.sendFailureEmail("System : " + machineName + " \n" + MAIL_BODY + CommonConstants.ACH_RECORD_COUNT_MISMATCH +
							"/nCOUNT MISMATCH: \nrecord count =  " + totalCount.longValue() + "\nvo count =  " + transactionBatchVO.getRecordCounter());
				} else {
					logger.info("else condition");
					File outputFile = new File(fileNameWithDir);
					logger.info(transactionBatchVO.getAchRecordsOutput().toString());
					logger.info("end of file records");
					FileUtil.writeStringToFile(outputFile, transactionBatchVO.getAchRecordsOutput().toString(),encrypt);
					insertNachaProcessHistoryLog(transactionBatchVO.getProcessLogId(), CommonConstants.ACH_FILE_GENERATION_COMPLETED, timestamp, CommonConstants.ACH_PROCESS_COMPLETED_MSG, CommonConstants.ACH_PROCESS_OWNER);
					if(encrypt)
					{
						fileNameWithDir = fileNameWithDir + ".pgp";
						archiveFileNameWithDir = archiveFileNameWithDir + ".pgp";
					}
					FileUtil.copyFile(new File(fileNameWithDir), new File(archiveFileNameWithDir));
				}

			} else if (transactionBatchVO != null && transactionBatchVO.isAmountMismatchFlag()) {
				logger.error("System : " + machineName + " \n" + MAIL_BODY + CommonConstants.ACH_AMOUNT_MISMATCH_FAILURE);
				emailTemplateBO.sendFailureEmail("System : " + machineName + " \n" + MAIL_BODY + CommonConstants.ACH_AMOUNT_MISMATCH_FAILURE);
			}

		} catch (Exception exception) {
			logger.error("[BalancedFileProcessor.process - Exception] " + StackTraceHelper.getStackTrace(exception));
			emailTemplateBO.sendFailureEmail("System : " + machineName + " \n" + MAIL_BODY + StackTraceHelper.getStackTrace(exception));
			throw new SLBusinessServiceException(exception);
		}

	}

	/**
	 * This method will reconcile all auto ach entries in ach_process_queue and associated entries in ledger_entry table
	 * creates a consolidated debit entry which will be processed by NACHA process.
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void processAutoAchEntries(Date runDate) throws SLBusinessServiceException {

		try {

			AchProcessQueueEntryVO achProcessQueueVO = new AchProcessQueueEntryVO();
			achProcessQueueVO.setCreatedDate(runDate);
			achProcessQueueVO.setTmpProcessStatusId(CommonConstants.ACH_PROCESS_TEMP_STATUS); //new
			achProcessQueueVO.setProcessStatusId(CommonConstants.ACH_FILE_GENERATION_STARTED);//old
			achProcessQueueVO.setReconciledIndicator(0);//reconciled

			// update all the auto ach entries to temp status
			nachaDao.updateAutoAchStatus(achProcessQueueVO);

			List<AchProcessQueueEntryVO> consolidatedEntryList = nachaDao.getAutoAchConsolidationAmount(CommonConstants.ACH_PROCESS_TEMP_STATUS);// For getting consolidated entry fro all auto ach deposits

			// update ledger entries for auto ach and mark them reconciled
			nachaDao.reconcileAutoAchLedgerEntries(CommonConstants.ACH_PROCESS_TEMP_STATUS);

			achProcessQueueVO.setTmpProcessStatusId(CommonConstants.ACH_RECORD_SUCCESSFULLY_PROCESSED); //new
			achProcessQueueVO.setProcessStatusId(CommonConstants.ACH_PROCESS_TEMP_STATUS); //old
			achProcessQueueVO.setReconciledIndicator(CommonConstants.ACH_RECONCILED); //reconciled
			
			// update all the auto ach entries from temp to reconciled state
			nachaDao.updateAutoAchStatus(achProcessQueueVO);

			for (AchProcessQueueEntryVO queueEntry : consolidatedEntryList) {
				double consolidatedAmount = MoneyUtil.getRoundedMoney(queueEntry.getTransactionAmount());

				Long consolidatedLedgerEntryId = null;
				if (consolidatedAmount > 0) {			
					consolidatedLedgerEntryId = nachaDao.insertLedgerEntryIdForConCreditCard();
					achBO.createConsolidatedEntryForAutoAch(consolidatedLedgerEntryId, consolidatedAmount,
						CommonConstants.AUTO_ACH_TRANS_CODE_ID, queueEntry.getAccountId(), queueEntry.getEntityId(), queueEntry.getEntityTypeId(), runDate);
					
				} else if (consolidatedAmount < 0) {
					consolidatedLedgerEntryId = nachaDao.insertLedgerEntryIdForConCreditCard();
					achBO.createConsolidatedEntryForAutoAch(consolidatedLedgerEntryId, Math.abs(consolidatedAmount),
							CommonConstants.AUTO_ACH_REFUND_TRANS_CODE_ID, queueEntry.getAccountId(), queueEntry.getEntityId(), queueEntry.getEntityTypeId(), runDate); 
				}

			}
		} catch (DataServiceException d) {
			throw new SLBusinessServiceException(d);
		}
	}

	/**
	 * This method processes multiple batches and adds File Header and File Control records in exactly the following sequence
	 * 1. Loads the File Record template from the database - Add it to the StringBuffer
	 * 2. Finds out the number of batches by querying the database (accounts_<env>_ch<version>.ach_process_queue)
	 * 3. For each batch, it does the following:
	 * 3.1 Get the list of user transaction list
	 * 3.2 Load the Entry Detail template from the database
	 * 3.3 Load user transaction specific data on the entry detail template object that we created - Add it to the StringBuffer
	 * 3.4 If addenda record is to be added, load the addenda template
	 * 3.4.1 Load the addenda specific data on the addenda template object - Add it to the StringBuffer
	 * 4. Load the Batch Control template from the database
	 * 6. Process/Calculate and load the specific information onto Batch Control template object - Add it to the StringBuffer
	 * 7. Load the File Control template from the database
	 * 8. Process/Calculate and load the specific information onto File Control template object - Add it to the StringBuffer
	 * 9. Return the StringBuffer
	 * 
	 * @param startDate 
	 * @param endDate 
	 * 
	 * @return StringBuffer returns StringBuffer
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public TransactionBatchVO processBatches(Date startDate, Date endDate) throws SLBusinessServiceException {

		long traceNumberSequence = 1;
		long batchNumberCounter = 1;
		long blockNumberCounter = 1; // assigned 1 to start with which stands for FileRecordHeader block
		long entryAddendaRecordCounter = 1;
		long hashEntrySum = 0;
		long totalCredit = 0;
		long totalDebit = 0;
		int transBatchListSize = 0;
		long recordCounter = 0;

		Timestamp timestamp = getCurrentTimestamp();
		TransactionBatchVO transactionBatchVO = new TransactionBatchVO();
		ArrayList<Long> achProcessIds = new ArrayList<Long>();
		ArrayList<NachaErrorVO> nachaErrorObjList = new ArrayList<NachaErrorVO>();
		StringBuffer sb = new StringBuffer();
		ArrayList<BatchRecordVO> batchList = new ArrayList<BatchRecordVO>();

		transactionBatchVO.setAchProcessIds(achProcessIds);
		Long processLogId = insertNachaProcessLog("", NachaUtil.getFileName(CommonConstants.ACH_TEMP_FILE_NAME), timestamp);
		Long achProcessLogHistoryId =
			insertNachaProcessHistoryLog(processLogId.longValue(), CommonConstants.ACH_PROCESS_STATUS_START, timestamp, CommonConstants.ACH_PROCESS_STARTED_MSG,
				CommonConstants.ACH_PROCESS_OWNER);
		transactionBatchVO.setProcessLogId(processLogId.longValue());
		transactionBatchVO.setProcessLogHistoryId(achProcessLogHistoryId);

		// 04-17-2008: Code commented out to avoid the consolidated data entry into ach_process_queue for total amount as per story# 27571
		/* Call processFirstDataCredit to make an entry((to FirstData)) in the ach_process_queue table for the total debit(from FirstData) amount we will send */
		// double totalCreditFirstData = processFirstDataCredit(transactionBatchVO);
		// Credit Card Authorizations updates and consolidation record entry will happen here.we add a consolidated entry to ach_process_queue table here for credit cards
		processCreditCardAuth(endDate);
		processCreditCardRefunds(endDate);

		 // processing auto Ach entries deposit and refund entries and consolidating them and add a consolidated entry to ach_process_queue table
		processAutoAchEntries(endDate);

		/* Process File Header record */
		FileHeaderRecordVO fileHeaderRecord = nachaTransformer.processFileHeaderRecord(nachaErrorObjList);

		// set File ID Modifier
		transactionBatchVO.setFileIdModifier(fileHeaderRecord.getFileIdModifier());

		// Ach Date Time
		transactionBatchVO.setAchDateTime(fileHeaderRecord.getAchDateTime());

		// Construct the File Record Header
		HashMap<String, FieldDetailVO> fileRecordHash = fileHeaderRecord.getHash(fileHeaderRecord.getFieldDetailVO());
		FieldDetailVO fieldDetail_ID = fileRecordHash.get("IMMEDIATE_DESTINATION");

		sb.append(fileHeaderRecord.getRecord());
		sb.append("\n");

		// 1. Get the distinct number of batches by querying the transaction queue table
		ArrayList<TransactionBatchVO> transBatchList = getBatchesFromTransQueue(endDate);

		transBatchListSize = transBatchList.size();
		boolean entryRecordsExist = false;
		Set<Long> detailSet = new HashSet<Long>();

		// 3. Process one batch at a time
		for (int i = 0; i < transBatchListSize; i++) {
			// if (i!=0) {sb.append("\n");}
			// 3.1 Get the batch Id from the TransactionBatch object
			TransactionBatchVO transBatch = (TransactionBatchVO) transBatchList.get(i);
			int transBatchId = transBatch.getBatchNumber();

			String discretionaryData = transBatch.getDiscretionaryData();// for merchant id

			int achTranscodeAccId = transBatch.getAchTranscodeAccId();
			HashMap<String, String> userTransMap = new HashMap<String, String>();
			userTransMap.put("batchId", String.valueOf(achTranscodeAccId));
			userTransMap.put("startDate", startDate.toString());
			userTransMap.put("endDate", endDate.toString());
			ArrayList<NachaProcessQueueVO> transactionList = (ArrayList<NachaProcessQueueVO>) getUserTransactionList(userTransMap);
			if (transactionList != null && !transactionList.isEmpty()) {

				entryRecordsExist = true;
				ArrayList<EntryRecordVO> entryRecordList = new ArrayList<EntryRecordVO>();
				// 3.3 Form a Batch Header Record
				BatchHeaderRecordVO batchHeaderRecord = new BatchHeaderRecordVO();
				// 3.2 Get all the User Transaction record objects by passing the batch Id. This will give a collection of
				// user transaction objects that belong to a SEC code.

				/* Process Batch Header record */
				nachaTransformer.processBatchHeaderRecord(batchHeaderRecord, nachaErrorObjList, sb, transBatchId, batchNumberCounter, discretionaryData);

				String immediateDestination = fieldDetail_ID.getFieldType();
				/* Process Entry Detail Records */
				HashMap<String, Long> myHash =
					nachaTransformer.processEntryDetailRecords(transactionList, entryRecordList, nachaErrorObjList, sb, achProcessIds, traceNumberSequence,
						entryAddendaRecordCounter, immediateDestination, transBatch);
				traceNumberSequence = ((Long) myHash.get("TRACE_SEQUENCE_COUNTER")).longValue();
				entryAddendaRecordCounter = ((Long) myHash.get("ENTRY_ADDENDA_COUNTER")).longValue();
				if (((Long) myHash.get("DO_NOT_GENERATE_FILE")).longValue() == 1) {
					transactionBatchVO.setAmountMismatchFlag(true);
					break;
				}
				BatchRecordVO batchRecord = new BatchRecordVO();
				batchRecord.setBatchHeaderRecord(batchHeaderRecord);
				batchRecord.setEntryRecords(entryRecordList);
				batchRecord.setBatchEntryTypeId(transBatch.getLedgerTransEntryTypeId());
				/* Process Batch Control Record */
				BatchControlRecordVO batchControlRecord = nachaTransformer.processBatchControlRecord(batchHeaderRecord, batchRecord, nachaErrorObjList);

				hashEntrySum = hashEntrySum + batchControlRecord.getEntryHash();
				totalCredit = totalCredit + batchControlRecord.getTotalCreditAmount();
				totalDebit = totalDebit + batchControlRecord.getTotalDebitAmount();

				// System.out.println("Batch Contrl: "+batchControlRecord.formatString());
				sb.append(batchControlRecord.getRecord());
				sb.append("\n");
				batchList.add(batchRecord);
				batchNumberCounter++;
			}
			
			//SL-8824 - Start: prevent double count of transactions
			for (NachaProcessQueueVO nachaVO : transactionList) {
				detailSet.add(nachaVO.getLedgerEntryId());
			}
		}
		recordCounter = detailSet.size();//SL-8824 - End.
		transactionBatchVO.setEntryRecordsExist(entryRecordsExist);
		FileControlRecordVO fileControlRecord = new FileControlRecordVO();
		fileControlRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ACH_FILE_CONTROL, 0));

		// subtract 1 from batchNumberCounter(to offset the final counter increment) and multiply by 2 (to comprehend batch control)
		// subtract 1 from entryAddendaRecordCounter to offset the final counter increment
		// add 1 to include the file control record
		blockNumberCounter = blockNumberCounter + ((batchNumberCounter - 1) * 2) + (entryAddendaRecordCounter - 1) + 1;

		// We subtract 1 from batchNumberCounter to offset the counter increment by 1 at the end of processing
		try {
			nachaTransformer.populateFileControlRecord(fileControlRecord, batchNumberCounter - 1, blockNumberCounter, entryAddendaRecordCounter - 1, hashEntrySum, totalCredit,
				totalDebit);

		} catch (NachaValidationException nachaException) {
			NachaErrorVO nachaErrorVO = new NachaErrorVO();
			nachaErrorVO.setFieldDetailVO(fileControlRecord.getFieldDetailVO());
			nachaErrorObjList.add(nachaErrorVO);
		}
		double dblCreditVal = (double) totalCredit / 100;
		double dblDebitVal = (double) totalDebit / 100;
		transactionBatchVO.setTotalCreditAmount(dblCreditVal);
		transactionBatchVO.setTotalDebitAmount(dblDebitVal);
		transactionBatchVO.setRecordCounter(recordCounter);

		sb.append(fileControlRecord.getRecord());


		// 04-17-2008: Code commented out to avoid the check for total amount as per story# 27571
		// 3. Assign batch number for each batch on the batch header
		// if (validateDebitCredit(totalCredit, totalDebit, totalCreditFirstData)==false) {
		// throw new BusinessServiceException(CommonConstants.ACH_CREDIT_DEBIT_MISMATCH);
		// }
		transactionBatchVO.setAchRecordsOutput(sb);
		return transactionBatchVO;

	}

	/**
	 * Process batches.
	 * 
	 * @param nachaProcessLogId 
	 * @param startDate 
	 * @param endDate 
	 * 
	 * @return the transaction batch vo
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public TransactionBatchVO processBatches(long nachaProcessLogId, Date startDate, Date endDate) throws SLBusinessServiceException {

		TransactionBatchVO transactionBatchVO = new TransactionBatchVO();
		transactionBatchVO.setProcessLogId(nachaProcessLogId);
		try {
			nachaDao.achProcessRerun(transactionBatchVO);
		} catch (DataServiceException dse) {
			throw new SLBusinessServiceException(CommonConstants.ACH_PROCESS_RERUN_ERROR + dse.getMessage(),dse);
		}
		return processBatches(startDate, endDate);
	}

	/**
	 * This method will reconcile all credit card Auth entries in ach_process_queue and associated entries in ledger_entry table
	 * creates a consolidated debit entry which will be processed by NACHA process.
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void processCreditCardAuth(Date runDate) throws SLBusinessServiceException {

		try {
			AchProcessQueueEntryVO achProcessQueueVO = new AchProcessQueueEntryVO();
			achProcessQueueVO.setCreatedDate(runDate);
			achProcessQueueVO.setTmpProcessStatusId(CommonConstants.ACH_PROCESS_TEMP_STATUS);
			achProcessQueueVO.setTransactionTypeId(CommonConstants.CREDIT_DEPOSIT_TRANSACTION_TYPE_ID);
			achProcessQueueVO.setProcessStatusId(CommonConstants.ACH_FILE_GENERATION_STARTED);
			achProcessQueueVO.setReconciledIndicator(0);
			// Here 9 refers to credit card auth
			achProcessQueueVO.setAchBatchAssocId(CommonConstants.CREDIT_CARD_AUTH_TRANS_CODE_ID);
			// update ACH_PROCESS_QUEUE table all auth records with status 10 to temp status.
			nachaDao.updateCreditCardAuthStatus(achProcessQueueVO);
			// 999 has no specific meaning. Here I am using this as temp status for just coding purpose.
			achProcessQueueVO.setProcessStatusId(CommonConstants.ACH_PROCESS_TEMP_STATUS);
			double consolidatedAmt = nachaDao.getCreditCardConsolidationAmount(achProcessQueueVO);
			List<Long> ledgerIds = nachaDao.getLedgerIdsForNachaProcess(achProcessQueueVO);
			// update ledger_entry table for credit card auth entries reconcilation.
			if (ledgerIds != null && !ledgerIds.isEmpty()) {
				nachaDao.reconcileCreditCardAuthLedgerEntry(ledgerIds);
			}
			achProcessQueueVO.setTmpProcessStatusId(CommonConstants.ACH_RECORD_SUCCESSFULLY_PROCESSED);
			achProcessQueueVO.setReconciledIndicator(CommonConstants.ACH_RECONCILED);
			// This update call will update all credit card auth records with
			// temp status to reconciled successfully.
			nachaDao.updateCreditCardAuthStatus(achProcessQueueVO);
			if (consolidatedAmt != 0) {
				Long consolidatedLedgerEntryId = null;
				consolidatedLedgerEntryId = nachaDao.insertLedgerEntryIdForConCreditCard();
				achBO.createConsolidatedEntryForCC(consolidatedLedgerEntryId, consolidatedAmt, CommonConstants.CONSOLIDATED_CREDIT_CARD_AUTH,
					CommonConstants.CREDIT_CARD_AUTH_TRANS_CODE_ID, runDate);
			}
		} catch (DataServiceException d) {
			throw new SLBusinessServiceException(d);
		}

	}

	/**
	 * This method will reconcile all credit card refund entries in ach_process_queue and associated entries in ledger_entry table
	 * creates a consolidated credit entry which will be processed by NACHA process.
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void processCreditCardRefunds(Date runDate) throws SLBusinessServiceException {

		try {
			AchProcessQueueEntryVO achProcessQueueVO = new AchProcessQueueEntryVO();
			achProcessQueueVO.setCreatedDate(runDate);
			achProcessQueueVO.setTmpProcessStatusId(CommonConstants.ACH_PROCESS_TEMP_STATUS);
			achProcessQueueVO.setTransactionTypeId(CommonConstants.CREDIT_REFUND_TRANSACTION_TYPE_ID);
			achProcessQueueVO.setProcessStatusId(CommonConstants.ACH_FILE_GENERATION_STARTED);
			achProcessQueueVO.setReconciledIndicator(0);
			// Trans code id 10 refers to credit card refunds
			achProcessQueueVO.setAchBatchAssocId(CommonConstants.CREDIT_CARD_REFUND_TRANS_CODE_ID);
			// update ACH_PROCESS_QUEUE table all auth records with status 10 to temp status.
			nachaDao.updateCreditCardAuthStatus(achProcessQueueVO);
			// 999 has no specific meaning. Here I am using this as temp status for just coding purpose.
			achProcessQueueVO.setProcessStatusId(CommonConstants.ACH_PROCESS_TEMP_STATUS);
			double consolidatedAmt = nachaDao.getCreditCardConsolidationAmount(achProcessQueueVO);
			List<Long> ledgerIds = nachaDao.getLedgerIdsForNachaProcess(achProcessQueueVO);
			// update ledger_entry table for credit card refund entries reconcilation.
			if (ledgerIds != null && !ledgerIds.isEmpty()) {
				nachaDao.reconcileCreditCardAuthLedgerEntry(ledgerIds);
			}
			achProcessQueueVO.setTmpProcessStatusId(CommonConstants.ACH_RECORD_SUCCESSFULLY_PROCESSED);
			achProcessQueueVO.setReconciledIndicator(CommonConstants.ACH_RECONCILED);
			// This update call will update all credit card refund records with
			// temp status to reconciled successfully.
			nachaDao.updateCreditCardAuthStatus(achProcessQueueVO);
			if (consolidatedAmt != 0) {
				Long consolidatedLedgerEntryId = null;
				consolidatedLedgerEntryId = nachaDao.insertLedgerEntryIdForConCreditCard();
				achBO.createConsolidatedEntryForCC(consolidatedLedgerEntryId, consolidatedAmt, CommonConstants.CONSOLIDATED_CREDIT_CARD_REFUNDS,
					CommonConstants.CREDIT_CARD_REFUND_TRANS_CODE_ID, runDate);
			}

		} catch (DataServiceException d) {
			throw new SLBusinessServiceException(d);
		}
	}

	/**
	 * Sets the ach bo.
	 * 
	 * @param achBO the new ach bo
	 */
	public void setAchBO(IAchBO achBO) {

		this.achBO = achBO;
	}

	/**
	 * Sets the identifier dao.
	 * 
	 * @param identifierDao the new identifier dao
	 */
	public void setIdentifierDao(IIdentifierDao identifierDao) {

		this.identifierDao = identifierDao;
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
	 * Sets the nacha meta data dao.
	 * 
	 * @param nachaMetaDataDao the new nacha meta data dao
	 */
	public void setNachaMetaDataDao(INachaMetaDataDao nachaMetaDataDao) {

		this.nachaMetaDataDao = nachaMetaDataDao;
	}

	/**
	 * Sets the nacha transformer.
	 * 
	 * @param nachaTransformer the new nacha transformer
	 */
	public void setNachaTransformer(NachaTransformer nachaTransformer) {

		this.nachaTransformer = nachaTransformer;
	}

}
