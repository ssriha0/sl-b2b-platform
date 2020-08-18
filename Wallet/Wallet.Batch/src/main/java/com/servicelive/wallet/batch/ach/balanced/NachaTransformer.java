package com.servicelive.wallet.batch.ach.balanced;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.Cryptography;
import com.servicelive.common.util.Cryptography128;
import com.servicelive.wallet.batch.ach.NachaUtil;
import com.servicelive.wallet.batch.ach.NachaValidationException;
import com.servicelive.wallet.batch.ach.dao.INachaDao;
import com.servicelive.wallet.batch.ach.dao.INachaMetaDataDao;
import com.servicelive.wallet.batch.ach.vo.AddendaRecordVO;
import com.servicelive.wallet.batch.ach.vo.BatchControlRecordVO;
import com.servicelive.wallet.batch.ach.vo.BatchHeaderRecordVO;
import com.servicelive.wallet.batch.ach.vo.BatchRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryDetailRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryRecordVO;
import com.servicelive.wallet.batch.ach.vo.FieldDetailVO;
import com.servicelive.wallet.batch.ach.vo.FileControlRecordVO;
import com.servicelive.wallet.batch.ach.vo.FileHeaderRecordVO;
import com.servicelive.wallet.batch.ach.vo.NachaErrorVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessQueueVO;
import com.servicelive.wallet.batch.trans.vo.TransactionBatchVO;

// TODO: Auto-generated Javadoc
/**
 * The Class NachaTransformer.
 */
public class NachaTransformer {

	/** The cryptography. */
	private Cryptography cryptography;

	/** The nacha dao. */
	private INachaDao nachaDao;

	/** The nacha meta data dao. */
	private INachaMetaDataDao nachaMetaDataDao;
	
	private Cryptography128 cryptography128;
	
	private static final Logger logger = Logger.getLogger(NachaTransformer.class);

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}


	public void setNachaDao(INachaDao nachaDao) {
		this.nachaDao = nachaDao;
	}


	public void setNachaMetaDataDao(INachaMetaDataDao nachaMetaDataDao) {
		this.nachaMetaDataDao = nachaMetaDataDao;
	}


	/**
	 * This method checks to see if the all mandatory/required fields
	 * have non zero values
	 * 
	 * @param fieldDetail 
	 * 
	 * @return true, if successful
	 */
	public boolean checkCondition(FieldDetailVO fieldDetail) {

		// Check if all the mandatory fields have data.
		boolean flag = false;
		if (fieldDetail != null) {
			if (fieldDetail.getFieldType().equals("M")) {
				String str = fieldDetail.getFieldValue();
				if (str != null && str.length() > 0) {
					flag = true;
				}

			} else {
				flag = true;
			}
		}
		return flag;
	}


	/**
	 * This method checks the size of the value to see if the size
	 * matches to the specification
	 * 
	 * @param fieldDetail 
	 * 
	 * @return true, if successful
	 */
	public boolean checkSize(FieldDetailVO fieldDetail) {

		boolean flag = false;
		if (fieldDetail != null) {
			int size = fieldDetail.getEndPosition() - fieldDetail.getStartPosition() + 1;
			String value = fieldDetail.getFieldValue();
			if (value.length() == size) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Creates the nacha process log.
	 * 
	 * @param transactionBatchVO 
	 * @param startDate 
	 * @param machineName 
	 * @param fileNameWithDir 
	 * 
	 * @return the nacha process log vo
	 */
	public NachaProcessLogVO createNachaProcessLog(TransactionBatchVO transactionBatchVO, Timestamp startDate, String machineName, String fileNameWithDir) {

		NachaProcessLogVO nachaProcessLogVO = new NachaProcessLogVO();
		nachaProcessLogVO.setNachaProcessId(transactionBatchVO.getProcessLogId());
		nachaProcessLogVO.setGeneratedMachineName(machineName);
		nachaProcessLogVO.setGeneratedFileName(fileNameWithDir);
		nachaProcessLogVO.setProcessStatusId(CommonConstants.ACH_FILE_GENERATION_COMPLETED);
		nachaProcessLogVO.setFileIdModifier(transactionBatchVO.getFileIdModifier());
		nachaProcessLogVO.setAchDateTime(transactionBatchVO.getAchDateTime());
		nachaProcessLogVO.setTotalCreditAmount(transactionBatchVO.getTotalCreditAmount());
		nachaProcessLogVO.setTotalDebitAmount(transactionBatchVO.getTotalDebitAmount());
		nachaProcessLogVO.setNachaRunDate(startDate);
		nachaProcessLogVO.setRecordCount(transactionBatchVO.getRecordCounter());
		return nachaProcessLogVO;
	}

	/**
	 * Format dollar.
	 * 
	 * @param dollar 
	 * 
	 * @return the string
	 */
	private String formatDollar(double dollar) {
		BigDecimal bdDollar = new BigDecimal(dollar);
		BigDecimal bdDollarRight =  bdDollar.movePointRight(2);
		BigDecimal bdDollarRightScale = bdDollarRight.setScale(2, RoundingMode.HALF_UP);
		String strDollar = bdDollarRightScale.toString();
		int decimalIndex = strDollar.indexOf('.');
		if (decimalIndex>0) {
			strDollar = strDollar.substring(0, decimalIndex);
		}
		return strDollar;
	}

	/**
	 * Gets the block count.
	 * 
	 * @param totalRecords 
	 * 
	 * @return the block count
	 */
	private String getBlockCount(String totalRecords) {

		int totalRecordsInt = Integer.valueOf(totalRecords);

		double mod = totalRecordsInt % 10;
		double totalRecordsDouble = Math.ceil(totalRecordsInt / 10);
		int i = new Double(totalRecordsDouble).intValue();
		if (mod != 0) {
			i = i + 1;
		}
		return String.valueOf(i);
	}

	/**
	 * Populate addenda record.
	 * 
	 * @param addendaRecordVO 
	 * 
	 * @throws NachaValidationException 
	 * @throws SLBusinessServiceException 
	 */
	private void populateAddendaRecord(AddendaRecordVO addendaRecordVO) throws NachaValidationException, SLBusinessServiceException {

		// No implementation is provided as we are not handling addenda records

		String strRecord = addendaRecordVO.formatString();
		addendaRecordVO.setRecord(strRecord);
		try {
			validateRules(addendaRecordVO.getFieldDetailVO());
		} catch (NachaValidationException nve) {
			throw new NachaValidationException(nve.getMessage(), nve);
		} catch (Exception e) {
			throw new SLBusinessServiceException(e.getMessage(), e);

		}

	}

	/**
	 * This method sets the control information for each batch that is processed during the Nacha file
	 * generation. It sets the following:
	 * 1. Service Class Code
	 * 2. Entry Addenda Count
	 * 3. Total Credit amount in the batch
	 * 4. Total Debit amount in the batch
	 * 5. Entry Hash
	 * 6. Batch Number - A sequence number generated by the program. This should be the same as the number in batch header
	 * 
	 * @param batchHeaderRecord 
	 * @param batchControlRecordVO 
	 * @param batchRecord 
	 * 
	 * @throws NachaValidationException 
	 * @throws SLBusinessServiceException 
	 */
	private void populateBatchControlRecord(BatchHeaderRecordVO batchHeaderRecord, BatchControlRecordVO batchControlRecordVO, BatchRecordVO batchRecord)
		throws NachaValidationException, SLBusinessServiceException {

		ArrayList<FieldDetailVO> fieldDetails = batchControlRecordVO.getFieldDetailVO();
		HashMap<String, FieldDetailVO> hashMap = batchControlRecordVO.getHash(fieldDetails);

		// 1. Set the Service Class Code from the Batch Header
		FieldDetailVO fieldDetail_SCC = hashMap.get("SERVICE_CLASS_CODE");
		HashMap<String, FieldDetailVO> batchHeaderHash = batchHeaderRecord.getHash(batchHeaderRecord.getFieldDetailVO());
		FieldDetailVO fieldDetail_SCC_BH = (FieldDetailVO) batchHeaderHash.get("SERVICE_CLASS_CODE");
		fieldDetail_SCC.setFieldValue(fieldDetail_SCC_BH.getFieldValue());

		// 2. Calculate and set the entry/addenda count
		FieldDetailVO fieldDetail_EAC = hashMap.get("ENTRY_ADDENDA_COUNT");
		int no = batchRecord.getEntryRecords().size();
		fieldDetail_EAC.setFieldValue(String.valueOf(no));

		// 3. Calculate total credit & debit dollar amount and the entry Hash
		FieldDetailVO fieldDetail_TDDA = hashMap.get("TOTAL_DEBIT");
		FieldDetailVO fieldDetail_TCDA = hashMap.get("TOTAL_CREDIT");
		FieldDetailVO fieldDetail_EH = hashMap.get("ENTRY_HASH");
		ArrayList<EntryRecordVO> entryRecords = batchRecord.getEntryRecords();
		long totalDebitDollar = 0;
		long totalCreditDollar = 0;
		long entryHash = 0;
		for (int i = 0; i < entryRecords.size(); i++) {
			EntryRecordVO entryRecord = (EntryRecordVO) entryRecords.get(i);
			EntryDetailRecordVO entryDetailRecord = entryRecord.getEntryDetailRecord();
			ArrayList<FieldDetailVO> entryDetailRecordFieldDetailVOs = entryDetailRecord.getFieldDetailVO();
			HashMap<String, FieldDetailVO> entryDetailRecordHash = batchControlRecordVO.getHash(entryDetailRecordFieldDetailVOs);
			FieldDetailVO strDollarFieldDetailVO = entryDetailRecordHash.get("AMOUNT");
			FieldDetailVO FieldDetailVO_RTRAN = entryDetailRecordHash.get("RDFI_TRANSIT_ROUTING_ABA_NUMBER");
			String strRoutingTranNum = FieldDetailVO_RTRAN.getFieldValue();
			entryHash = entryHash + (long) Long.valueOf(strRoutingTranNum);
			String strDollar = strDollarFieldDetailVO.getFieldValue();
			if(batchRecord.getBatchEntryTypeId() == CommonConstants.ENTRY_TYPE_CREDIT)
			{
				totalCreditDollar = totalCreditDollar + Integer.valueOf(strDollar);
			}else
			{
				totalDebitDollar = totalDebitDollar + Integer.valueOf(strDollar);
			}
			
		}
		fieldDetail_TDDA.setFieldValue(String.valueOf(totalDebitDollar));
		fieldDetail_TCDA.setFieldValue(String.valueOf(totalCreditDollar));
		batchControlRecordVO.setTotalCreditAmount(totalCreditDollar);
		batchControlRecordVO.setTotalDebitAmount(totalDebitDollar);
		// entryHash holds the total of the first 8 digit routing number of the user
		batchControlRecordVO.setEntryHash(entryHash);
		String str = String.valueOf(entryHash);
		int length = str.length();
		int startIndex = 0;
		if (length > 10) {
			// Set the entry hash taking the last 10 digit of the total
			startIndex = length - 10;
			str = str.substring(startIndex);
		}
		fieldDetail_EH.setFieldValue(str);
		// 4. Set the Batch Number from the Batch Header
		FieldDetailVO fieldDetail_BN = hashMap.get("BATCH_NUMBER");
		ArrayList<FieldDetailVO> batchHeaderFieldDetailVOs = batchHeaderRecord.getFieldDetailVO();
		HashMap<String, FieldDetailVO> batchHeaderFieldHash = batchControlRecordVO.getHash(batchHeaderFieldDetailVOs);
		// Get the 'BATCH_NUMBER' Field Value of Batch Header Record
		FieldDetailVO batchHeaderBatchNumber = batchHeaderFieldHash.get("BATCH_NUMBER");
		fieldDetail_BN.setFieldValue(batchHeaderBatchNumber.getFieldValue());

		String strRecord = batchControlRecordVO.formatString();
		batchControlRecordVO.setRecord(strRecord);
		try {
			validateRules(batchControlRecordVO.getFieldDetailVO());

		} catch (NachaValidationException nve) {
			throw new NachaValidationException(nve.getMessage(), nve);
		} catch (Exception e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	/**
	 * This method sets the header record information for each batch that is processed during the Nacha file
	 * generation. It sets the following:
	 * 1. Service Class Code
	 * 2. Company Discretion data
	 * 3. Company Descriptive date
	 * 4. Effective Entry Date
	 * 
	 * @param batchHeaderRecord 
	 * @param discretionaryData 
	 * 
	 * @throws NachaValidationException 
	 * @throws SLBusinessServiceException 
	 */
	private void populateBatchHeaderRecord(BatchHeaderRecordVO batchHeaderRecord, String discretionaryData) throws NachaValidationException, SLBusinessServiceException {

		ArrayList<FieldDetailVO> fieldDetails = batchHeaderRecord.getFieldDetailVO();
		HashMap<String, FieldDetailVO> hashMap = batchHeaderRecord.getHash(fieldDetails);

		// 1. System sets Service Type Code
		FieldDetailVO fieldDetail_STC = hashMap.get("SERVICE_CLASS_CODE");
		fieldDetail_STC.setFieldValue("200");

		// 2. System sets Company Discretion Data
		FieldDetailVO fieldDetail_CDD = hashMap.get("MERCHANT_NUMBER");
		fieldDetail_CDD.setFieldValue(discretionaryData);

		// 3. System sets Company Descriptive Date
		FieldDetailVO fieldDetail_CDDT = hashMap.get("COMPANY_DESCRIPTIVE_DATE");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
		String myDate = simpleDateFormat.format(cal.getTime());
		fieldDetail_CDDT.setFieldValue(myDate);

		// 4. System sets Effective Entry Date
		FieldDetailVO fieldDetail_EED = hashMap.get("EFFECTIVE_ENTRY_DATE");
		// Setting the Effective date the same as descriptive date
		fieldDetail_EED.setFieldValue(myDate);

		// 5. Batch number of this instance will be a running number and will be set by the
		// BatchProcessor

		String strRecord = batchHeaderRecord.formatString();
		batchHeaderRecord.setRecord(strRecord);
		try {
			validateRules(batchHeaderRecord.getFieldDetailVO());
		} catch (NachaValidationException nve) {
			throw new NachaValidationException(nve.getMessage(), nve);
		} catch (Exception e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	/**
	 * This method sets the entry detail information for each user transaction that is processed during the Nacha file
	 * generation. It sets the following:
	 * 1. Routing Number of the user's bank
	 * 2. Routing Check digit in the routing number
	 * 3. Account number of the user
	 * 4. Amount
	 * 5. Individual Identification Number (ledger id)
	 * 6. Individual Name
	 * 7. Trace Number
	 * 
	 * @param entryDetailRecord 
	 * @param nachaProcessQueueVO 
	 * @param cryptography 
	 * @param traceNumberSequence 
	 * @param transactionBatchVO 
	 * 
	 * @return EntryDetailRecordVO
	 * 
	 * @throws Exception 
	 * @throws NachaValidationException 
	 */
	private void populateEntryDetailRecord(EntryDetailRecordVO entryDetailRecord, NachaProcessQueueVO nachaProcessQueueVO, Cryptography cryptography, long traceNumberSequence,
		TransactionBatchVO transactionBatchVO) throws NachaValidationException, Exception {

		logger.info("Inside populateEntryDetailRecord method in NachaTransformer");
		
		ArrayList<FieldDetailVO> fieldDetails = entryDetailRecord.getFieldDetailVO();
		HashMap<String, FieldDetailVO> hashMap = entryDetailRecord.getHash(fieldDetails);

		// 1. Set the RDFI Transit Routing/ABA Number
		FieldDetailVO fieldDetail_RTRAN = hashMap.get("RDFI_TRANSIT_ROUTING_ABA_NUMBER");
		String routingCheckDigitNumber = cryptography.decryptKey(nachaProcessQueueVO.getRoutingNo());
		String routingNumber = routingCheckDigitNumber.substring(0, 8);
		fieldDetail_RTRAN.setFieldValue(routingNumber);

		// 2. Set the Transit Routing Check Digit
		FieldDetailVO fieldDetail_TRCD = hashMap.get("TRANSIT_ROUTING_CHECK_DIGIT");

		fieldDetail_TRCD.setFieldValue(routingCheckDigitNumber.substring(8, 9));

		// 3. Set the Transit Routing Check Digit
		FieldDetailVO fieldDetail_DAN = hashMap.get("DFI_ACCOUNT_NUMBER");
		
		//Commenting code for SL-18789
		//fieldDetail_DAN.setFieldValue(cryptography.decryptKey(nachaProcessQueueVO.getAccountNo().toString()));
		fieldDetail_DAN.setFieldValue(cryptography128.decryptKey128Bit(nachaProcessQueueVO.getAccountNo().toString()));
		logger.info("Decrypted Account Number"+cryptography128.decryptKey128Bit(nachaProcessQueueVO.getAccountNo().toString()));
		// 4. Set the dollar Amount
		FieldDetailVO fieldDetail_A = hashMap.get("AMOUNT");
		// Format the Double
		fieldDetail_A.setFieldValue(formatDollar(nachaProcessQueueVO.getTransAmount()));

		// 5. Set the Individual Identification Number
		FieldDetailVO fieldDetail_IIN = hashMap.get("INDIVIDUAL_IDENTIFICATION_NUMBER");
		fieldDetail_IIN.setFieldValue(String.valueOf(nachaProcessQueueVO.getLedgerEntryId()));

		// 6. Set the Individual Name
		FieldDetailVO fieldDetail_IN = hashMap.get("INDIVIDUAL_COMPANY_NAME");
		int fieldLength = fieldDetail_IN.getEndPosition() - fieldDetail_IN.getStartPosition() + 1;
		String name = nachaProcessQueueVO.getUserName();
		if (name != null) {
			if (name.length() > fieldLength) {
				name = name.substring(0, fieldLength);
			}
		} else {
			name = "";
		}

		fieldDetail_IN.setFieldValue(name);
		if(transactionBatchVO.getLedgerTransEntryTypeId() == CommonConstants.ENTRY_TYPE_CREDIT)
		{
			nachaProcessQueueVO.setTransTypeDescr("CREDIT");
		}
		else
		{
			nachaProcessQueueVO.setTransTypeDescr("DEBIT");
		}
		entryDetailRecord.setTransactionType(nachaProcessQueueVO.getTransTypeDescr()); // This needs to be changed

		// 8. Set the transaction code
		FieldDetailVO fieldDetail_TN = hashMap.get("TRANSACTION_CODE");
		fieldDetail_TN.setFieldValue(String.valueOf(transactionBatchVO.getAchTransactionCode()));

		/*
		 * FieldDetailVO fieldDetail_TRDTL = (FieldDetailVO)hashMap.get("TRACE_NUMBER");
		 * fieldDetail_TRDTL.setFieldValue(routingNumber);
		 */

		FieldDetailVO fieldDetail_TRTRS = (FieldDetailVO) hashMap.get("TRACE_NUMBER_SEQUENCE");
		fieldDetail_TRTRS.setFieldValue(String.valueOf(traceNumberSequence));

		// 10. Set the Addenda Indicator
		FieldDetailVO fieldDetail_AI = hashMap.get("ADDENDA_RECORD_INDICATOR");
		if (fieldDetail_AI.getFieldValue().equals("0")) {
			entryDetailRecord.setAddendaAvailable(false);
		} else {
			entryDetailRecord.setAddendaAvailable(true);
		}

		String strRecord = entryDetailRecord.formatString();
		logger.info("Final record"+strRecord);
		entryDetailRecord.setRecord(strRecord);
		try {
			validateRules(entryDetailRecord.getFieldDetailVO());
		} catch (NachaValidationException nve) {
			throw new NachaValidationException(nve.getMessage(), nve);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);

		}

	}

	/**
	 * This method sets the file control information for the Nacha file. It sets the following:
	 * 1. Batch Count
	 * 2. Block Count
	 * 3. Entry/Addenda Count
	 * 4. Entry Hash
	 * 5. Total Credit dollars that the Nacha File claims to be processed
	 * 6. Total Debit dollars that the Nacha File claims to be processed
	 * 
	 * @param batchCount 
	 * @param blockCount 
	 * @param entryAddendaCount 
	 * @param hashEntrySum 
	 * @param totalCredit 
	 * @param totalDebit 
	 * @param fileControlRecord 
	 * 
	 * @return FileControlRecordVO
	 * 
	 * @throws NachaValidationException 
	 * @throws SLBusinessServiceException 
	 */

	public void populateFileControlRecord(FileControlRecordVO fileControlRecord, long batchCount, long blockCount, long entryAddendaCount, long hashEntrySum, long totalCredit,
		long totalDebit) throws NachaValidationException, SLBusinessServiceException {

		HashMap<String, FieldDetailVO> fileControlHash = fileControlRecord.getHash(fileControlRecord.getFieldDetailVO());

		// 1. Set the Batch count
		FieldDetailVO fieldDetail_BaC = fileControlHash.get("BATCH_COUNT");
		fieldDetail_BaC.setFieldValue(String.valueOf(batchCount));

		// 2. Set the Block Count
		FieldDetailVO fieldDetail_BlC = fileControlHash.get("BLOCK_COUNT");
		// fieldDetail_BlC.setFieldValue(getBlockCount(fieldDetail_BlC.getFieldValue()));
		fieldDetail_BlC.setFieldValue(getBlockCount(String.valueOf(blockCount)));
		// 3. Set the Entry/Addenda Count
		FieldDetailVO fieldDetail_EAC = fileControlHash.get("ENTRY_ADDENDA_COUNT");
		fieldDetail_EAC.setFieldValue(String.valueOf(entryAddendaCount));

		// 4. Set the Entry Hash aggregate
		FieldDetailVO fieldDetail_EH = fileControlHash.get("ENTRY_HASH");
		String strHashSum = String.valueOf(hashEntrySum);
		int hashLength = strHashSum.length();
		int startIndex = 0;
		if (hashLength > 10) {
			startIndex = hashLength - 10;
			strHashSum = strHashSum.substring(startIndex);

		}
		fieldDetail_EH.setFieldValue(strHashSum);

		// 5. Set the Total Credit Aggregate
		FieldDetailVO fieldDetail_TC = fileControlHash.get("TOTAL_CREDIT");
		fieldDetail_TC.setFieldValue(String.valueOf(totalCredit));

		// 6. Set the Total Debit Aggregate
		FieldDetailVO fieldDetail_TD = fileControlHash.get("TOTAL_DEBIT");
		fieldDetail_TD.setFieldValue(String.valueOf(totalDebit));

		String strRecord = fileControlRecord.formatString();
		fileControlRecord.setRecord(strRecord);
		try {
			validateRules(fileControlRecord.getFieldDetailVO());
		} catch (NachaValidationException nve) {
			throw new NachaValidationException(nve.getMessage(), nve);
		} catch (Exception e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	/**
	 * This method sets the file creation date and time on the fileRecordHeaderRecordVO object.
	 * 
	 * @param fileHeaderRecord 
	 * @param iPorcessLogCount 
	 * 
	 * @return FileHeaderRecordVO
	 * 
	 * @throws NachaValidationException 
	 * @throws SLBusinessServiceException 
	 */
	private void populateFileHeaderRecord(FileHeaderRecordVO fileHeaderRecord, Integer iPorcessLogCount) throws NachaValidationException, SLBusinessServiceException {

		ArrayList<FieldDetailVO> fieldDetails = fileHeaderRecord.getFieldDetailVO();
		HashMap<String, FieldDetailVO> fileHeaderHash = fileHeaderRecord.getHash(fieldDetails);

		// 1. System calculates the File Creation Date
		FieldDetailVO fieldDetail_FCD = fileHeaderHash.get("FILE_CREATION_DATE");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
		String myDate = simpleDateFormat.format(cal.getTime());
		fieldDetail_FCD.setFieldValue(myDate);

		// 2. System calculates the File Creation Time
		FieldDetailVO fieldDetail_FCT = fileHeaderHash.get("FILE_CREATION_TIME");
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HHmm");
		String myTime = simpleDateFormat1.format(cal.getTime());
		fieldDetail_FCT.setFieldValue(myTime);

		// set AchDateTime to capture ACH Date Time in database
		fileHeaderRecord.setAchDateTime(myDate + myTime);

		// 3. System sets the File ID Modifier
		FieldDetailVO fieldDetail_FIM = fileHeaderHash.get("FILE_ID_MODIFIER");
		fieldDetail_FIM.setFieldValue(NachaUtil.getNextFileIdModifier(iPorcessLogCount));
		fileHeaderRecord.setFileIdModifier(fieldDetail_FIM.getFieldValue());

		// This is set in the database
		String strRecord = fileHeaderRecord.formatString();
		fileHeaderRecord.setRecord(strRecord);
		try {
			validateRules(fileHeaderRecord.getFieldDetailVO());
		} catch (NachaValidationException nve) {
			throw new NachaValidationException(nve.getMessage(), nve);
		} catch (Exception e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	/**
	 * Process batch control record.
	 * 
	 * @param batchHeaderRecord 
	 * @param batchRecord 
	 * @param nachaErrorObjList 
	 * 
	 * @return the batch control record vo
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public BatchControlRecordVO processBatchControlRecord(BatchHeaderRecordVO batchHeaderRecord, BatchRecordVO batchRecord, ArrayList<NachaErrorVO> nachaErrorObjList)
		throws SLBusinessServiceException {

		BatchControlRecordVO batchControlRecord = new BatchControlRecordVO();// This should come from DAO
		batchControlRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ACH_BATCH_CONTROL, 0));

		try {
			populateBatchControlRecord(batchHeaderRecord, batchControlRecord, batchRecord);
		} catch (NachaValidationException nachaException) {
			NachaErrorVO nachaErrorVO = new NachaErrorVO();
			nachaErrorVO.setFieldDetailVO(batchControlRecord.getFieldDetailVO());
			nachaErrorObjList.add(nachaErrorVO);
		} catch (Exception e) {
			throw new SLBusinessServiceException(CommonConstants.ACH_BATCH_CONTROL_RECORD_PROCESS_ERROR + e.getStackTrace(),e);
		}

		return batchControlRecord;
	}

	/**
	 * Process batch header record.
	 * 
	 * @param batchHeaderRecord 
	 * @param nachaErrorObjList 
	 * @param sb 
	 * @param transBatchId 
	 * @param batchNumberCounter 
	 * @param discretionaryData 
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void processBatchHeaderRecord(BatchHeaderRecordVO batchHeaderRecord, ArrayList<NachaErrorVO> nachaErrorObjList, StringBuffer sb, int transBatchId,
		long batchNumberCounter, String discretionaryData) throws SLBusinessServiceException {

		// 3.3.1 Load the default values of Batch Header from the database
		batchHeaderRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ACH_BATCH_HEADER, transBatchId));
		// 3.3.2 Call getHash() to load the FieldDetailVOs in a HashMap
		HashMap<String, FieldDetailVO> batchHeaderHash = batchHeaderRecord.getHash(batchHeaderRecord.getFieldDetailVO());
		FieldDetailVO fieldDetail_BN = batchHeaderHash.get("BATCH_NUMBER");
		// 3.3.3 Set the Batch Number to the appropriate FieldDetailVO object on Batch Header
		fieldDetail_BN.setFieldValue(String.valueOf(batchNumberCounter));
		// 3.3.4 Call handleSpecificData to load specific values for the Batch Header
		try {
			populateBatchHeaderRecord(batchHeaderRecord, discretionaryData);
		} catch (NachaValidationException nachaException) {
			NachaErrorVO nachaErrorVO = new NachaErrorVO();
			nachaErrorVO.setFieldDetailVO(batchHeaderRecord.getFieldDetailVO());
			nachaErrorObjList.add(nachaErrorVO);

		} catch (Exception e) {
			throw new SLBusinessServiceException(CommonConstants.ACH_BATCH_HEADER_RECORD_PROCESS_ERROR + e.getStackTrace(),e);
		}
		// 3.3.5 Add the batch header to string buffer
		sb.append(batchHeaderRecord.getRecord());
		sb.append("\n");

	}

	/**
	 * Process entry detail records.
	 * 
	 * @param transactionList 
	 * @param entryRecordList 
	 * @param nachaErrorObjList 
	 * @param sb 
	 * @param achProcessIds 
	 * @param traceNumberSequence 
	 * @param entryAddendaRecordCounter 
	 * @param immediateDestination 
	 * @param transactionBatchVO 
	 * 
	 * @return the hash map< string, long>
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public HashMap<String, Long> processEntryDetailRecords(ArrayList<NachaProcessQueueVO> transactionList, ArrayList<EntryRecordVO> entryRecordList,
		ArrayList<NachaErrorVO> nachaErrorObjList, StringBuffer sb, ArrayList<Long> achProcessIds, long traceNumberSequence, long entryAddendaRecordCounter,
		String immediateDestination, TransactionBatchVO transactionBatchVO) throws SLBusinessServiceException {
		logger.info("inside processEntryDetailRecords");
		String batchRecordDiscretionaryData = transactionBatchVO.getDiscretionaryData();// for merchant id of batch record
		long doNotGenerateFile = 0;
		HashMap<String, Long> myHash = new HashMap<String, Long>();
		long traceNumberSequenceCounter = traceNumberSequence;
		// 3.4 Prepare an Entry Detail Record
		EntryDetailRecordVO entryDetailRecord = new EntryDetailRecordVO();// This should come from DAO
		// 3.4.1 Load the default values of Entry Detail Record from the database
		entryDetailRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ACH_ENTRY_DETAIL, 0));
		entryDetailRecord.setImmediateDestination(immediateDestination);
		// 3.4.2 Prepare an Addenda Detail Record
		AddendaRecordVO addendaRecord = new AddendaRecordVO();// This should come from DAO
		// 3.4.3 Load the default values of Addenda Detail Record from the database
		addendaRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ACH_ADDENDA, 0));
		// 3.4.5 Prepare Entry Detail and Addenda Record Processors
		int entryRecordCounter = 1;
		EntryDetailRecordVO edrCopy = null;

		for (int j = 0; j < transactionList.size(); j++) {
			edrCopy = NachaUtil.getEntryDetailCopy(entryDetailRecord);
			NachaProcessQueueVO nachaProcessQueueVO = transactionList.get(j);

			if (nachaProcessQueueVO != null && (StringUtils.equals(String.valueOf(nachaProcessQueueVO.getDiscretionaryData()), batchRecordDiscretionaryData))) {
				logger.info("inside if loop");
				if (nachaProcessQueueVO.getTransAmount() != nachaProcessQueueVO.getLedgerTransAmount()) {
					doNotGenerateFile = 1;
					break;
				}
				edrCopy.setEntryDetailNumber(String.valueOf(entryRecordCounter));
				try {
					populateEntryDetailRecord(edrCopy, nachaProcessQueueVO, cryptography, traceNumberSequenceCounter, transactionBatchVO);
					traceNumberSequenceCounter = traceNumberSequenceCounter + 1;
				} catch (NachaValidationException nachaException) {
					NachaErrorVO nachaErrorVO = new NachaErrorVO();
					nachaErrorVO.setFieldDetailVO(edrCopy.getFieldDetailVO());
					nachaErrorObjList.add(nachaErrorVO);

				} catch (Exception e) {
					throw new SLBusinessServiceException(CommonConstants.ACH_ENTRY_DETAIL_RECORD_PROCESS_ERROR + e.getStackTrace(),e);

				}
				sb.append(edrCopy.getRecord());
				sb.append("\n");
				achProcessIds.add(Long.valueOf(nachaProcessQueueVO.getAchProcessId()));
				if (edrCopy.isAddendaAvailable() == true) {
					AddendaRecordVO addendaCopy = (AddendaRecordVO) addendaRecord.clone();
					try {
						populateAddendaRecord(addendaCopy);
					} catch (NachaValidationException nachaException) {
						NachaErrorVO nachaErrorVO = new NachaErrorVO();
						nachaErrorVO.setFieldDetailVO(addendaRecord.getFieldDetailVO());
						nachaErrorObjList.add(nachaErrorVO);
					} catch (Exception e) {
						throw new SLBusinessServiceException(CommonConstants.ACH_ADDENDA_DETAIL_RECORD_PROCESS_ERROR + e.getStackTrace(),e);
					}

					sb.append(addendaCopy.getRecord());
					sb.append("\n");
					entryAddendaRecordCounter++;
				}
				EntryRecordVO entryRecord = new EntryRecordVO();
				entryRecord.setAddendaRecord(addendaRecord);
				entryRecord.setEntryDetailRecord(edrCopy);
				entryRecordList.add(entryRecord);
				entryRecordCounter++;
				entryAddendaRecordCounter++;
			}
		}
		myHash.put("TRACE_SEQUENCE_COUNTER", Long.valueOf(traceNumberSequenceCounter));
		myHash.put("ENTRY_ADDENDA_COUNTER", Long.valueOf(entryAddendaRecordCounter));
		myHash.put("DO_NOT_GENERATE_FILE", Long.valueOf(doNotGenerateFile));
		myHash.put("DETAIL_COUNT",  new Long (entryRecordCounter - 1));
		return myHash;
	}

	/**
	 * Process file header record.
	 * 
	 * @param nachaErrorObjList 
	 * 
	 * @return the file header record vo
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public FileHeaderRecordVO processFileHeaderRecord(ArrayList<NachaErrorVO> nachaErrorObjList) throws SLBusinessServiceException {

		FileHeaderRecordVO fileHeaderRecord = new FileHeaderRecordVO();
		fileHeaderRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ACH_FILE_HEADER, 0));
		try {
			// Get the Process Log error
			Integer iPorcessLogCount = nachaDao.getProcessLogCount(new java.sql.Date(System.currentTimeMillis()));

			populateFileHeaderRecord(fileHeaderRecord, iPorcessLogCount);
		} catch (NachaValidationException nachaException) {
			NachaErrorVO nachaErrorVO = new NachaErrorVO();
			nachaErrorVO.setFieldDetailVO(fileHeaderRecord.getFieldDetailVO());
			nachaErrorObjList.add(nachaErrorVO);
		} catch (Exception e) {
			throw new SLBusinessServiceException(CommonConstants.ACH_FILE_HEADER_RECORD_PROCESS_ERROR + e.getStackTrace(),e);
		}

		return fileHeaderRecord;
	}

	/**
	 * This method should be called at the last just before writing to the
	 * file to ensure we write good records to the NACHA file.
	 * All error records will be collected and logged somewhere ?
	 * 
	 * @param al 
	 * 
	 * @throws NachaValidationException 
	 */
	private void validateRules(ArrayList<FieldDetailVO> al) throws NachaValidationException {

		if (al != null && !al.isEmpty()) {
			for (int t = 0; t < al.size(); t++) {
				FieldDetailVO fieldDetail = al.get(t);
				boolean flagSize = checkSize(fieldDetail);
				boolean flagCondition = checkCondition(fieldDetail);
				if (flagSize == false || flagCondition == false) {
					throw new NachaValidationException("Nacha Specification Validation Failed for FieldDetail: " + fieldDetail.toString());
				}
			}
		}
	}


	public Cryptography128 getCryptography128() {
		return cryptography128;
	}


	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}

}
