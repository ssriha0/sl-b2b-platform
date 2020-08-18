package com.servicelive.wallet.batch.ptd;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.servicelive.common.CommonConstants;
import com.servicelive.wallet.batch.ptd.vo.PTDFileVO;
import com.servicelive.wallet.batch.ptd.vo.PTDFullfillmentEntryVO;
import com.servicelive.wallet.batch.ptd.vo.PTDProcessLogVO;
import com.servicelive.wallet.batch.ptd.vo.PTDRecordTypeVO;
import com.servicelive.wallet.batch.ptd.vo.PTDRecordVO;
import com.servicelive.wallet.batch.ptd.vo.PtdEntryVO;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDTransformer.
 */
public class PTDTransformer {

	/**
	 * Description: This method will create the VO for the PTD entry that gets written to the ptd_file_tranactions table.
	 * We are going to log every PTD file entry in that table.
	 * 
	 * @param ptdTransactionRecordVO 
	 * @param ptdFullfillmentEntryVO 
	 * @param ptdLogId 
	 * 
	 * @return 
	 */
	public PtdEntryVO createPTDEntryVO(PTDRecordVO ptdTransactionRecordVO, PTDFullfillmentEntryVO ptdFullfillmentEntryVO, int ptdLogId) {

		HashMap<String, PTDRecordTypeVO> map = ptdTransactionRecordVO.getHash(ptdTransactionRecordVO.getPtdRecordTypeList());
		PtdEntryVO ptdEntryVO = new PtdEntryVO(ptdFullfillmentEntryVO);
		// All new fields for story
		ptdEntryVO.setPtdLogId(ptdLogId);
		PTDRecordTypeVO tempVO = map.get("RECORD_FORM_INDICATOR");
		ptdEntryVO.setRecordFormIndicator(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("RESERVED_1");
		ptdEntryVO.setReserved1(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("RESERVED_2");
		ptdEntryVO.setReserved2(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("MERCHANT_ID");
		ptdEntryVO.setMerchantId(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("RESERVED_3");
		ptdEntryVO.setReserved3(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("RESERVED_4");
		ptdEntryVO.setReserved4(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("ALTERNATE_MERCHANT_NUMBER");
		ptdEntryVO.setAlternateMerchantNumber(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("TERMINAL_ID");
		ptdEntryVO.setTerminalId(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("TERMINAL_TRANSACTION_NUMBER");
		ptdEntryVO.setTerminalTransactionNumber(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("BASE_AMOUNT");
		ptdEntryVO.setBaseAmount(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("REPORTING_AMOUNT");
		ptdEntryVO.setReportingAmount(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("BASE_CURRENCY_CODE");
		ptdEntryVO.setBaseCurrencyCode(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("LOCAL_CURRENCY_CODE");
		ptdEntryVO.setLocalCurrencyCode(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("REPORTING_CURRENCY_CODE");
		ptdEntryVO.setReportingCurrencyCode(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("EXCHANGE_CODE");
		ptdEntryVO.setExchangeCode(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("CLERK_ID");
		ptdEntryVO.setClerkId(tempVO.getFieldValue());
		tempVO = map.get("BALANCE_SIGN");
		ptdEntryVO.setBalanceSign(tempVO.getFieldValue());
		tempVO = map.get("BASE_BALANCE");
		ptdEntryVO.setBaseBalance(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("CONSORTIUM_CODE");
		ptdEntryVO.setConsortiumCode(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("PROMOTION_CODE");
		ptdEntryVO.setPromotionCode(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("FDMS_LOCAL_TIMESTAMP");
		ptdEntryVO.setFdmsLocalTimestamp(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("TERMINAL_LOCAL_TIMESTAMP");
		ptdEntryVO.setTerminalLocalTimestamp(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("REPLACED_BY_ACCOUNT_NUMBER");
		ptdEntryVO.setReplacedByAccountNumber(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("RESERVED_5");
		ptdEntryVO.setReserved5(tempVO.getFieldValue());
		tempVO = map.get("USER_ID");
		ptdEntryVO.setUserId(tempVO.getFieldValue());
		tempVO = map.get("CARD_CLASS");
		ptdEntryVO.setCardClass(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("EXPIRATION_DATE");
		ptdEntryVO.setExpirationDate(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("CARD_COST");
		ptdEntryVO.setCardCost(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("ESCHEATABLE_TRANSACTION");
		ptdEntryVO.setEscheaTableTransaction(tempVO.getFieldValue());
		tempVO = map.get("REFERENCE_NUMBER");
		ptdEntryVO.setReferenceNumber(tempVO.getFieldValue());
		tempVO = map.get("USER_2");
		ptdEntryVO.setUser2(tempVO.getFieldValue());
		tempVO = map.get("LOCAL_CASH_BACK");
		ptdEntryVO.setLocalCashback(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("BASE_CASH_BACK");
		ptdEntryVO.setBaseCashback(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("REPORTING_CASH_BACK");
		ptdEntryVO.setReportingCashback(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("LOCAL_LOCK_AMOUNT");
		ptdEntryVO.setLocalLockAmount(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("BASE_LOCK_AMOUNT");
		ptdEntryVO.setBaseLockAmount(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("REVERSED_TIMESTAMP");
		ptdEntryVO.setReversedTimestamp(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("PROCESSED_DATE");
		ptdEntryVO.setProcessedDate(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("ORIGINAL_REQUEST_CODE");
		ptdEntryVO.setOriginalRequestCode(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("INTERNAL_REQUEST_CODE");
		ptdEntryVO.setInternalRequestCode(Integer.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("CALL_TRACE_INFO");
		ptdEntryVO.setCallTraceInfo(tempVO.getFieldValue());
		tempVO = map.get("UPLIFT_AMOUNT");
		ptdEntryVO.setUpliftAmount(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("GMT_TIMESTAMP");
		ptdEntryVO.setGmtTimestamp(tempVO.getFieldValue());
		tempVO = map.get("ALTERNATE_ACCOUNT_NUMBER");
		ptdEntryVO.setAlternateAccountNumber(Long.valueOf(tempVO.getFieldValue()));
		tempVO = map.get("AUTH_CODE");
		ptdEntryVO.setPtdAuthCode(tempVO.getFieldValue());
		// All new fields for story

		return ptdEntryVO;
	}

	/**
	 * This method returns a PTDProcessLogVO object for the file that was
	 * processed.
	 * 
	 * @param ptdFileVO 
	 * 
	 * @return 
	 */
	public PTDProcessLogVO getProcessLogVO(PTDFileVO ptdFileVO) {

		PTDProcessLogVO ptdProcessLogVO = new PTDProcessLogVO();
		ptdProcessLogVO.setPtdTransactionsReceived(ptdFileVO.getPtdTransactionList().size());
		PTDRecordVO headerVO = ptdFileVO.getPtdFileHeaderRecordVO();
		HashMap<String, PTDRecordTypeVO> hmap = headerVO.getHash(headerVO.getPtdRecordTypeList());
		PTDRecordTypeVO seqVO = hmap.get("SEQUENCE_NUMBER");
		PTDRecordTypeVO prevSeqVO = hmap.get("PREV_SEQUENCE_NUMBER");
		ptdProcessLogVO.setPtdSequenceNumber(Integer.valueOf(seqVO.getFieldValue()));
		ptdProcessLogVO.setPrevSequenceNumber(Integer.valueOf(prevSeqVO.getFieldValue()));
		ptdProcessLogVO.setPtdProcessStatus(CommonConstants.PTD_PROCESS_STATUS);
		ptdProcessLogVO.setPtdReconsiledInd(CommonConstants.PTD_RECONCILATION_STATUS_SUCCESS);
		ptdProcessLogVO.setProcessRunDate(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
		return ptdProcessLogVO;

	}

}
