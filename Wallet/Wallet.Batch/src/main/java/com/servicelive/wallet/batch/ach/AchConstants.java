package com.servicelive.wallet.batch.ach;

// TODO: Auto-generated Javadoc
/**
 * The Class AchConstants.
 */
public class AchConstants {

	/** The Constant ACH_ACK_EMAIL_SUBJECT_FAILURE. */
	public static final String ACH_ACK_EMAIL_SUBJECT_FAILURE = "ACH Acknowledgement Failed for Batch ID ";

	/** The Constant ACH_ACK_EMAIL_SUBJECT_SUCCESS. */
	public static final String ACH_ACK_EMAIL_SUBJECT_SUCCESS = "ACH Acknowledgement Successful for Batch Id ";

	/** The Constant ACH_ACK_RECEIVED_REJECTED. */
	public static final Integer ACH_ACK_RECEIVED_REJECTED = 40;

	/** The Constant ACH_ACK_RECEIVED_SUCCESS. */
	public static final Integer ACH_ACK_RECEIVED_SUCCESS = 30;

	/** The Constant ACH_ACK_TEMPLATE_COMMENTS_FIELD. */
	public static final String ACH_ACK_TEMPLATE_COMMENTS_FIELD = "Comments";

	/** The Constant ACH_ACK_TEMPLATE_CREATED_DATE_FIELD. */
	public static final String ACH_ACK_TEMPLATE_CREATED_DATE_FIELD = "File_Create_Date";

	/** The Constant ACH_ACK_TEMPLATE_CREATED_TIME_FIELD. */
	public static final String ACH_ACK_TEMPLATE_CREATED_TIME_FIELD = "File_Create_Time";

	/** The Constant ACH_ADDENDA. */
	public final static String ACH_ADDENDA = "ADDENDA";

	/** The Constant ACH_ADDENDA_DETAIL_RECORD_PROCESS_ERROR. */
	public static final String ACH_ADDENDA_DETAIL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Addenda Detail Record. Error message is: ";

	/** The Constant ACH_ADMINTOOL_ERROR. */
	public static final String ACH_ADMINTOOL_ERROR = "An error occured while running the ACH Admin tool.";

	/** The Constant ACH_AMOUNT_MISMATCH_FAILURE. */
	public static final String ACH_AMOUNT_MISMATCH_FAILURE = "ACH Amount mismatch between ACH Process Queue and Ledger Transaction entries. Please update and retry. ";

	/** The Constant ACH_AMT_MISMATCH_APQ_VO_FAILURE. */
	public static final String ACH_AMT_MISMATCH_APQ_VO_FAILURE =
		"ACH Amount mismatch between ACH Process Queue and the values returned from the Process Value Object. Please update and retry. ";

	/** The Constant ACH_BATCH_CONTROL. */
	public final static String ACH_BATCH_CONTROL = "BATCH_CONTROL";

	/** The Constant ACH_BATCH_CONTROL_RECORD_PROCESS_ERROR. */
	public static final String ACH_BATCH_CONTROL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Batch Control Record. Error message is: ";

	/** The Constant ACH_BATCH_HEADER. */
	public final static String ACH_BATCH_HEADER = "BATCH_HEADER";

	/** The Constant ACH_BATCH_HEADER_RECORD_PROCESS_ERROR. */
	public static final String ACH_BATCH_HEADER_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Batch Header Record. Error message is: ";

	/** The Constant ACH_CREDIT_DEBIT_MISMATCH. */
	public static final String ACH_CREDIT_DEBIT_MISMATCH =
		"The total debit doesn't match with total credit " + "or the total calculated credit doesnt match with the pre-calculated credit. The transactions are rolled back.";

	/** The Constant ACH_ENTRY_DETAIL. */
	public final static String ACH_ENTRY_DETAIL = "ENTRY_DETAIL";

	/** The Constant ACH_ENTRY_DETAIL_RECORD_PROCESS_ERROR. */
	public static final String ACH_ENTRY_DETAIL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Entry Detail Record. Error message is: ";

	/** The Constant ACH_ENTRY_DETAIL_RECORDS_PROCESS_COMPLETED. */
	public static final String ACH_ENTRY_DETAIL_RECORDS_PROCESS_COMPLETED = "Ach Entry detail records got processed";

	/** The Constant ACH_ENTRY_DETAIL_RECORDS_PROCESSING. */
	public static final String ACH_ENTRY_DETAIL_RECORDS_PROCESSING = "Ach Entry detail records are currently under process";

	/** The Constant ACH_ERROR_DETAIL_CNT_OOB. */
	public static final String ACH_ERROR_DETAIL_CNT_OOB = "";

	/** The Constant ACH_ERROR_FILE_CNTL_MISSING. */
	public static final String ACH_ERROR_FILE_CNTL_MISSING = "FILE CNTL MISSING";

	/** The Constant ACH_ERROR_HDR_MISSING. */
	public static final String ACH_ERROR_HDR_MISSING = "FILE HDR MISSING ";

	/** The Constant ACH_FILE_CONTROL. */
	public final static String ACH_FILE_CONTROL = "FILE_CONTROL";

	/** The Constant ACH_FILE_CONTROL_RECORD_PROCESS_ERROR. */
	public static final String ACH_FILE_CONTROL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach File Control Record. Error message is: ";

	/** The Constant ACH_FILE_GENERATION_COMPLETED. */
	public static final Integer ACH_FILE_GENERATION_COMPLETED = 20;

	/*
	 * ACH_PROCESS_STATUS CODES
	 */
	/** The Constant ACH_FILE_GENERATION_NOT_STARTED. */
	public static final Integer ACH_FILE_GENERATION_NOT_STARTED = 0;

	/** The Constant ACH_FILE_GENERATION_STARTED. */
	public static final Integer ACH_FILE_GENERATION_STARTED = 10;

	/** The Constant ACH_FILE_HEADER. */
	public final static String ACH_FILE_HEADER = "FILE_HEADER";

	/** The Constant ACH_FILE_HEADER_RECORD_PROCESS_ERROR. */
	public static final String ACH_FILE_HEADER_RECORD_PROCESS_ERROR = "An error occurred while processing Ach File Header Record. Error message is: ";

	/** The Constant ACH_FILE_PROBLEM_FIXED. */
	public static final Integer ACH_FILE_PROBLEM_FIXED = 70;

	/** The Constant ACH_ORIGINATION_TOTAL_MISMATCH. */
	public static final String ACH_ORIGINATION_TOTAL_MISMATCH = "Origination and Nacha totals don't match. ";

	/** The Constant ACH_PREPROCESS_CHECK_ERROR. */
	public static final String ACH_PREPROCESS_CHECK_ERROR = "Previous process didn't complete successfully. Please check the database and fix the issue";

	/** The Constant ACH_PROCESS_BATCH_CHECK_ERROR. */
	public static final String ACH_PROCESS_BATCH_CHECK_ERROR = "Please check BatchId or its status and rerun.";

	/** The Constant ACH_PROCESS_COMPLETED_MSG. */
	public static final String ACH_PROCESS_COMPLETED_MSG = "Ach Process Completed";

	/** The Constant ACH_PROCESS_HISTORY_LOG_ERROR. */
	public static final String ACH_PROCESS_HISTORY_LOG_ERROR = "An error occurred while updating ach process history log information. Error message is: ";

	/** The Constant ACH_PROCESS_INITIATER. */
	public static final String ACH_PROCESS_INITIATER = "ACH ACK PARSER";

	/** The Constant ACH_PROCESS_LOG_ERROR. */
	public static final String ACH_PROCESS_LOG_ERROR = "An error occurred while updating ach process log information. Error message is: ";

	/** The Constant ACH_PROCESS_OWNER. */
	public static final String ACH_PROCESS_OWNER = "ACH PROCESS SCHEDULER";

	/** The Constant ACH_PROCESS_REJECTED_MSG. */
	public static final String ACH_PROCESS_REJECTED_MSG = "Ach Process Acknowledgement Rejected";

	/** The Constant ACH_PROCESS_RERUN_ERROR. */
	public static final String ACH_PROCESS_RERUN_ERROR = "An error occurred while re-running the ach transaction process queue information . Error message is: ";

	/** The Constant ACH_PROCESS_RETURN. */
	public static final String ACH_PROCESS_RETURN = "RETURN FILE PROCESSOR";

	/** The Constant ACH_PROCESS_STARTED_MSG. */
	public static final String ACH_PROCESS_STARTED_MSG = "Ach Process Started";

	/** The Constant ACH_PROCESS_STATUS_START. */
	public static final int ACH_PROCESS_STATUS_START = 1;

	/** The Constant ACH_PROCESS_TEMP_STATUS. */
	public static final Integer ACH_PROCESS_TEMP_STATUS = 999;

	/** The Constant ACH_QUEUE_TRANSACTION_RETRIEVAL_ERROR. */
	public static final String ACH_QUEUE_TRANSACTION_RETRIEVAL_ERROR = "An error occurred while retrieving queue transactions from database. Error message is: ";

	/** The Constant ACH_RECONCILED. */
	public final static int ACH_RECONCILED = 40;

	/** The Constant ACH_RECORD_COUNT_MISMATCH. */
	public static final String ACH_RECORD_COUNT_MISMATCH =
		"Record count is different in the VO to be written out and in the database. Please check and do the necessary updates before retrying. ";

	/** The Constant ACH_RECORD_SUCCESSFULLY_PROCESSED. */
	public static final Integer ACH_RECORD_SUCCESSFULLY_PROCESSED = 80;

	/** The Constant ACH_RECORD_TYPE_ADDENDA. */
	public static final String ACH_RECORD_TYPE_ADDENDA = "7";

	/** The Constant ACH_RECORD_TYPE_BATCH_CONTROL. */
	public static final String ACH_RECORD_TYPE_BATCH_CONTROL = "8";

	/** The Constant ACH_RECORD_TYPE_BATCH_HEADER. */
	public static final String ACH_RECORD_TYPE_BATCH_HEADER = "5";

	/* This constant should have 94 '9's. Don't delete any */
	/** The Constant ACH_RECORD_TYPE_BLOCK_CONTROL. */
	public static final String ACH_RECORD_TYPE_BLOCK_CONTROL = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";

	/** The Constant ACH_RECORD_TYPE_ENTRY_DETAIL. */
	public static final String ACH_RECORD_TYPE_ENTRY_DETAIL = "6";

	/** The Constant ACH_RECORD_TYPE_FILE_CONTROL. */
	public static final String ACH_RECORD_TYPE_FILE_CONTROL = "9";

	/** The Constant ACH_RECORD_TYPE_FILE_HEADER. */
	public static final String ACH_RECORD_TYPE_FILE_HEADER = "1";

	/** The Constant ACH_REJECTED. */
	public final static int ACH_REJECTED = 50;

	/** The Constant ACH_RESPONSE_CONFIRMATION. */
	public final static String ACH_RESPONSE_CONFIRMATION = "RESPONSE_CONFIRMATION";

	/** The Constant ACH_RESPONSE_ERROR. */
	public static final String ACH_RESPONSE_ERROR = "ERROR";

	/** The Constant ACH_RESPONSE_SUCCESS. */
	public static final String ACH_RESPONSE_SUCCESS = "success            ";

	/** The Constant ACH_RETURNED. */
	public final static int ACH_RETURNED = 60;

	/** The Constant ACH_SENT. */
	public final static int ACH_SENT = 20;

	/** The Constant ACH_SUCCESS. */
	public final static int ACH_SUCCESS = 30;

	/** The Constant ACH_TEMP_FILE_NAME. */
	public static final String ACH_TEMP_FILE_NAME = "temp";

	/** The Constant ACH_TEMPLATE_RETRIEVAL_ERROR. */
	public static final String ACH_TEMPLATE_RETRIEVAL_ERROR = "An error occurred while retrieving ach template record from database. Error message is: ";

	/** The Constant ACH_TRAN_TOTAL_MISMATCH. */
	public static final String ACH_TRAN_TOTAL_MISMATCH = "AchProcessQueue and TranFile totals don't match. ";

	/** The Constant ACH_TRANSACTION_BATCH_ID_RETRIEVAL_ERROR. */
	public static final String ACH_TRANSACTION_BATCH_ID_RETRIEVAL_ERROR = "An error occurred while retrieving transaction batch ids from database. Error message is: ";

	/** The Constant ACH_TRANSACTION_QUEUE_UDPATE_ERROR. */
	public static final String ACH_TRANSACTION_QUEUE_UDPATE_ERROR = "An error occurred while updating ach transaction process queue information . Error message is: ";

	/** The Constant ACH_UNBALANCED_FILE_PROCESS_COMPLETED_MSG. */
	public static final String ACH_UNBALANCED_FILE_PROCESS_COMPLETED_MSG = "Ach Unbalanced File Process Completed";

	/** The Constant ACH_UNBALANCED_FILE_PROCESS_REJECTED_MSG. */
	public static final String ACH_UNBALANCED_FILE_PROCESS_REJECTED_MSG = "Ach Unbalanced File Process Acknowledgement Rejected";

	/** The Constant ACH_UNBALANCED_FILE_PROCESS_STARTED_MSG. */
	public static final String ACH_UNBALANCED_FILE_PROCESS_STARTED_MSG = "Ach Unbalanced File Process Started";

	// ########################ACH RECONCILED STATUS########################
	/** The Constant ACH_UNPROCESSED. */
	public final static int ACH_UNPROCESSED = 10;

	/** The Constant AUTO_ACH_CONSOLIDATED_ENTRY. */
	public static final int AUTO_ACH_CONSOLIDATED_ENTRY = 2000;

	/** The Constant AUTO_ACH_DEPOSIT_TRANSACTION_TYPE_ID. */
	public static final int AUTO_ACH_DEPOSIT_TRANSACTION_TYPE_ID = 1900;

	/** The Constant AUTO_ACH_REFUND_TRANS_CODE_ID. */
	public static final int AUTO_ACH_REFUND_TRANS_CODE_ID = 5;

	/** The Constant AUTO_ACH_REFUND_TRANSACTION_TYPE_ID. */
	public static final int AUTO_ACH_REFUND_TRANSACTION_TYPE_ID = 1901;

	/** The Constant AUTO_ACH_TRANS_CODE_ID. */
	public static final int AUTO_ACH_TRANS_CODE_ID = 6;

	/** The Constant BALANCED_IND_FALSE. */
	public final static Integer BALANCED_IND_FALSE = 0;

	/** Balanced Indicator constants. */
	public final static Integer BALANCED_IND_TRUE = 1;

	/** The Constant CONSOLIDATED_CREDIT_CARD_AUTH. */
	public static final int CONSOLIDATED_CREDIT_CARD_AUTH = 1500;

	/** The Constant CONSOLIDATED_CREDIT_CARD_REFUNDS. */
	public static final int CONSOLIDATED_CREDIT_CARD_REFUNDS = 1600;

	/** The Constant CREDIT_CARD_AUTH_TRANS_CODE_ID. */
	public static final int CREDIT_CARD_AUTH_TRANS_CODE_ID = 9;

	/** The Constant CREDIT_CARD_REFUND_TRANS_CODE_ID. */
	public static final int CREDIT_CARD_REFUND_TRANS_CODE_ID = 10;

	/** The Constant CREDIT_DEPOSIT_TRANSACTION_TYPE_ID. */
	public static final int CREDIT_DEPOSIT_TRANSACTION_TYPE_ID = 1200;

	/** The Constant CREDIT_REFUND_TRANSACTION_TYPE_ID. */
	public static final int CREDIT_REFUND_TRANSACTION_TYPE_ID = 1400;

	/** The Constant EMAIL_ACH_FAILURE_RETURN_CODE. */
	public static final int EMAIL_ACH_FAILURE_RETURN_CODE = 33862;

	/** The Constant EMAIL_PROFESSIONAL_FIRM_REGISTRATION. */
	public static final int EMAIL_PROFESSIONAL_FIRM_REGISTRATION = 33850;

	/** The Constant EMAIL_PROVIDER_REGISTRATION. */
	public static final int EMAIL_PROVIDER_REGISTRATION = 90037;

	/** The Constant EMAIL_SLADMIN_REGISTRATION. */
	public static final int EMAIL_SLADMIN_REGISTRATION = 33861;

	/** The Constant EMAIL_TEMPLATE_ACH_ACK. */
	public static final int EMAIL_TEMPLATE_ACH_ACK = 81;

	/** The Constant EMAIL_TEMPLATE_ACH_CLEARED_WITH_WARNING_CODE. */
	public static final int EMAIL_TEMPLATE_ACH_CLEARED_WITH_WARNING_CODE = 89;

	/** The Constant EMAIL_TEMPLATE_ACH_DEPOSIT_FUNDS. */
	public static final int EMAIL_TEMPLATE_ACH_DEPOSIT_FUNDS = 33847;

	/** The Constant EMAIL_TEMPLATE_ACH_PROVIDER_WITHDRAW_FUNDS. */
	public static final int EMAIL_TEMPLATE_ACH_PROVIDER_WITHDRAW_FUNDS = 33848;

	/** The Constant EMAIL_TEMPLATE_BUYER_CANCEL_PENALTY. */
	public static final int EMAIL_TEMPLATE_BUYER_CANCEL_PENALTY = 215;

	/** The Constant EMAIL_TEMPLATE_BUYER_CLOSE_SO. */
	public static final int EMAIL_TEMPLATE_BUYER_CLOSE_SO = 214;

	/** The Constant EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS. */
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS = 114;

	/** The Constant EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_CONFIRMATION. */
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_CONFIRMATION = 117;

	/** The Constant EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_FAILURE. */
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_FAILURE = 116;

	/** The Constant EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_WARNING. */
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_WARNING = 115;

	/** The Constant EMAIL_TEMPLATE_BUYER_POSTING_FEE. */
	public static final int EMAIL_TEMPLATE_BUYER_POSTING_FEE = 213;

	/** The Constant EMAIL_TEMPLATE_BUYER_PROVIDER_ISSUE_REFUNDS. */
	public static final int EMAIL_TEMPLATE_BUYER_PROVIDER_ISSUE_REFUNDS = 112;

	/** The Constant EMAIL_TEMPLATE_BUYER_SLBUCKS_CREDIT. */
	public static final int EMAIL_TEMPLATE_BUYER_SLBUCKS_CREDIT = 216;

	/** The Constant EMAIL_TEMPLATE_BUYER_SLBUCKS_DEBIT. */
	public static final int EMAIL_TEMPLATE_BUYER_SLBUCKS_DEBIT = 218;

	/** The Constant EMAIL_TEMPLATE_BUYER_WITHDRAW_FUNDS. */
	public static final int EMAIL_TEMPLATE_BUYER_WITHDRAW_FUNDS = 113;

	/** The Constant EMAIL_TEMPLATE_CC_BUYER_WITHDRAW_FUNDS. */
	public static final int EMAIL_TEMPLATE_CC_BUYER_WITHDRAW_FUNDS = 114;

	/** The Constant EMAIL_TEMPLATE_CC_DEPOSIT_FUNDS. */
	public static final int EMAIL_TEMPLATE_CC_DEPOSIT_FUNDS = 54544;

	/** The Constant EMAIL_TEMPLATE_GLFEED_FAILED. */
	public static final int EMAIL_TEMPLATE_GLFEED_FAILED = 91;

	/** The Constant EMAIL_TEMPLATE_ISSUE_REFUNDS. */
	public static final int EMAIL_TEMPLATE_ISSUE_REFUNDS = 55184;

	/** The Constant EMAIL_TEMPLATE_ORIGINATION. */
	public static final int EMAIL_TEMPLATE_ORIGINATION = 79;

	/** The Constant EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE. */
	public static final int EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE = 88;

	/** The Constant EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_SUCCESS. */
	public static final int EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_SUCCESS = 84;

	/** The Constant EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_WARNING. */
	public static final int EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_WARNING = 89;

	/** The Constant EMAIL_TEMPLATE_PROVIDER_CLOSE_SO. */
	public static final int EMAIL_TEMPLATE_PROVIDER_CLOSE_SO = 221;

	/** The Constant EMAIL_TEMPLATE_PROVIDER_SLBUCKS_CREDIT. */
	public static final int EMAIL_TEMPLATE_PROVIDER_SLBUCKS_CREDIT = 217;

	/** The Constant EMAIL_TEMPLATE_PROVIDER_SLBUCKS_DEBIT. */
	public static final int EMAIL_TEMPLATE_PROVIDER_SLBUCKS_DEBIT = 219;

	/** The Constant EMAIL_TEMPLATE_PROVIDER_SO_CANCELLED. */
	public static final int EMAIL_TEMPLATE_PROVIDER_SO_CANCELLED = 220;

	/** The Constant EMAIL_TEMPLATE_PROVIDER_WITHDRAW_FUNDS. */
	public static final int EMAIL_TEMPLATE_PROVIDER_WITHDRAW_FUNDS = 82;

	/*
	 * ACH File Types
	 */
	/** The Constant FILE_TYPE_ACH. */
	public static final int FILE_TYPE_ACH = 1;

	/** The Constant FILE_TYPE_ACKNOWLEDGEMENT. */
	public static final int FILE_TYPE_ACKNOWLEDGEMENT = 2;

	/** The Constant FILE_TYPE_ORIGINATION. */
	public static final int FILE_TYPE_ORIGINATION = 3;

	/** The Constant FILE_TYPE_RETURN. */
	public static final int FILE_TYPE_RETURN = 4;

	/** The Constant FIRSTDATA_ACCOUNT_ID. */
	public static final int FIRSTDATA_ACCOUNT_ID = 40;

	/** The Constant FIRSTDATA_ACH_BATCH_ID. */
	public static final int FIRSTDATA_ACH_BATCH_ID = 8;

	/** The Constant FIRSTDATA_BUSINESS_TRANS_ID. */
	public static final int FIRSTDATA_BUSINESS_TRANS_ID = 20;

	/** The Constant FIRSTDATA_ENTITY_ID. */
	public static final int FIRSTDATA_ENTITY_ID = 1;

	/** The Constant FIRSTDATA_ENTITY_TYPE_ID. */
	public static final int FIRSTDATA_ENTITY_TYPE_ID = 40;

	/** The Constant FIRSTDATA_FAILED_CONSOLIDATED_CREDIT_TRANS. */
	public static final int FIRSTDATA_FAILED_CONSOLIDATED_CREDIT_TRANS = 1100;

	/** The Constant FIRSTDATA_PROCESS_STATUS_ID. */
	public static final int FIRSTDATA_PROCESS_STATUS_ID = 10;

	/** The Constant FIRSTDATA_TRANSACTION_TYPE_ID. */
	public static final int FIRSTDATA_TRANSACTION_TYPE_ID = 1000;

	/** The Constant JPM_ACCOUNT_ID. */
	public static final int JPM_ACCOUNT_ID = 30;

	/** The Constant JPM_ENTITY_ID. */
	public static final int JPM_ENTITY_ID = 1;

	/** The Constant ORIGINATION_ADDENDA. */
	public final static String ORIGINATION_ADDENDA = "ORIGINATION_ADDENDA";

	/** The Constant ORIGINATION_BATCH_CONTROL. */
	public final static String ORIGINATION_BATCH_CONTROL = "ORIGINATION_BATCH_CONTROL";

	/** The Constant ORIGINATION_BATCH_HEADER. */
	public final static String ORIGINATION_BATCH_HEADER = "ORIGINATION_BATCH_HEADER";

	/** The Constant ORIGINATION_BLOCK_CONTROL. */
	public final static String ORIGINATION_BLOCK_CONTROL = "ORIGINATION_BLOCK_CONTROL";

	/** The Constant ORIGINATION_COMPLETION_MESSAGE. */
	public final static String ORIGINATION_COMPLETION_MESSAGE = "Origination Process Completed";

	/** The Constant ORIGINATION_ENTRY_DETAIL. */
	public final static String ORIGINATION_ENTRY_DETAIL = "ORIGINATION_ENTRY_DETAIL";

	/** The Constant ORIGINATION_FILE_CONTROL. */
	public final static String ORIGINATION_FILE_CONTROL = "ORIGINATION_FILE_CONTROL";

	/** The Constant ORIGINATION_FILE_HEADER. */
	public final static String ORIGINATION_FILE_HEADER = "ORIGINATION_FILE_HEADER";

	/** The Constant ORIGINATION_FILE_PROCESSED. */
	public static final Integer ORIGINATION_FILE_PROCESSED = 50;

	/** The Constant ORIGINATION_PROCESS_OWNER. */
	public static final String ORIGINATION_PROCESS_OWNER = "ORIGINATION PROCESS SCHEDULER";

	/** The Constant ORIGINATION_REJECT_PATTERN. */
	public static final String ORIGINATION_REJECT_PATTERN = "REJ0";

	/** The Constant RETURN_ADDENDA. */
	public final static String RETURN_ADDENDA = "RETURN_ADDENDA";

	/** The Constant RETURNS_ACCT_TRANS_CODE_ID. */
	public static final int RETURNS_ACCT_TRANS_CODE_ID = 6;

	/** The Constant RETURNS_FILE_PROCESSED. */
	public static final Integer RETURNS_FILE_PROCESSED = 60;

	/** The Constant SERVICELIVE_ENTITY_TYPE_ID. */
	public static final int SERVICELIVE_ENTITY_TYPE_ID = 30;

	/** The Constant TEMPLATE_BUYER_CANCELLATION_EMAIL. */
	public static final int TEMPLATE_BUYER_CANCELLATION_EMAIL = 54666;

	/** The Constant TEMPLATE_BUYER_CLOSE_EMAIL. */
	public static final int TEMPLATE_BUYER_CLOSE_EMAIL = 54484;

	/** The Constant TEMPLATE_BUYER_SLBUCKS_CREDIT. */
	public static final int TEMPLATE_BUYER_SLBUCKS_CREDIT = 54444;

	/** The Constant TEMPLATE_BUYER_SLBUCKS_DEBIT. */
	public static final int TEMPLATE_BUYER_SLBUCKS_DEBIT = 54464;

	/** The Constant TEMPLATE_BUYER_USER_REMOVED. */
	public static final int TEMPLATE_BUYER_USER_REMOVED = 33860;

	/** The Constant TEMPLATE_ID_BUYER_CLOSE_EMAIL. */
	public static final int TEMPLATE_ID_BUYER_CLOSE_EMAIL = 253;

	/** The Constant TEMPLATE_ID_PROVIDER_CLOSE_EMAIL. */
	public static final int TEMPLATE_ID_PROVIDER_CLOSE_EMAIL = 254;

	/** The Constant TEMPLATE_PROVIDER_REMOVE_USER. */
	public static final int TEMPLATE_PROVIDER_REMOVE_USER = 64686;

	/** The Constant UNBALANCED_TRANSACTION_TYPE_ID. */
	public static final int UNBALANCED_TRANSACTION_TYPE_ID = 1100;

}
