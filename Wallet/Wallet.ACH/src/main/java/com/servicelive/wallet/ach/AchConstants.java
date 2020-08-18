package com.servicelive.wallet.ach;

// TODO: Auto-generated Javadoc
/**
 * Class AchConstants.
 */
public class AchConstants {

	/** ACH_ACK_EMAIL_SUBJECT_FAILURE. */
	public static final String ACH_ACK_EMAIL_SUBJECT_FAILURE = "ACH Acknowledgement Failed for Batch ID ";

	/** ACH_ACK_EMAIL_SUBJECT_SUCCESS. */
	public static final String ACH_ACK_EMAIL_SUBJECT_SUCCESS = "ACH Acknowledgement Successful for Batch Id ";

	/** ACH_ACK_RECEIVED_REJECTED. */
	public static final Integer ACH_ACK_RECEIVED_REJECTED = 40;

	/** ACH_ACK_RECEIVED_SUCCESS. */
	public static final Integer ACH_ACK_RECEIVED_SUCCESS = 30;

	/** ACH_ACK_TEMPLATE_COMMENTS_FIELD. */
	public static final String ACH_ACK_TEMPLATE_COMMENTS_FIELD = "Comments";

	/** ACH_ACK_TEMPLATE_CREATED_DATE_FIELD. */
	public static final String ACH_ACK_TEMPLATE_CREATED_DATE_FIELD = "File_Create_Date";

	/** ACH_ACK_TEMPLATE_CREATED_TIME_FIELD. */
	public static final String ACH_ACK_TEMPLATE_CREATED_TIME_FIELD = "File_Create_Time";

	/** ACH_ADDENDA. */
	public final static String ACH_ADDENDA = "ADDENDA";

	/** ACH_ADDENDA_DETAIL_RECORD_PROCESS_ERROR. */
	public static final String ACH_ADDENDA_DETAIL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Addenda Detail Record. Error message is: ";

	/** ACH_ADMINTOOL_ERROR. */
	public static final String ACH_ADMINTOOL_ERROR = "An error occured while running the ACH Admin tool.";

	/** ACH_AMOUNT_MISMATCH_FAILURE. */
	public static final String ACH_AMOUNT_MISMATCH_FAILURE = "ACH Amount mismatch between ACH Process Queue and Ledger Transaction entries. Please update and retry. ";

	/** ACH_AMT_MISMATCH_APQ_VO_FAILURE. */
	public static final String ACH_AMT_MISMATCH_APQ_VO_FAILURE =
		"ACH Amount mismatch between ACH Process Queue and the values returned from the Process Value Object. Please update and retry. ";

	/** ACH_BATCH_CONTROL. */
	public final static String ACH_BATCH_CONTROL = "BATCH_CONTROL";

	/** ACH_BATCH_CONTROL_RECORD_PROCESS_ERROR. */
	public static final String ACH_BATCH_CONTROL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Batch Control Record. Error message is: ";

	/** ACH_BATCH_HEADER. */
	public final static String ACH_BATCH_HEADER = "BATCH_HEADER";

	/** ACH_BATCH_HEADER_RECORD_PROCESS_ERROR. */
	public static final String ACH_BATCH_HEADER_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Batch Header Record. Error message is: ";

	/** ACH_CREDIT_DEBIT_MISMATCH. */
	public static final String ACH_CREDIT_DEBIT_MISMATCH =
		"The total debit doesn't match with total credit " + "or the total calculated credit doesnt match with the pre-calculated credit. The transactions are rolled back.";

	/** ACH_ENTRY_DETAIL. */
	public final static String ACH_ENTRY_DETAIL = "ENTRY_DETAIL";

	/** ACH_ENTRY_DETAIL_RECORD_PROCESS_ERROR. */
	public static final String ACH_ENTRY_DETAIL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Entry Detail Record. Error message is: ";

	/** ACH_ENTRY_DETAIL_RECORDS_PROCESS_COMPLETED. */
	public static final String ACH_ENTRY_DETAIL_RECORDS_PROCESS_COMPLETED = "Ach Entry detail records got processed";

	/** ACH_ENTRY_DETAIL_RECORDS_PROCESSING. */
	public static final String ACH_ENTRY_DETAIL_RECORDS_PROCESSING = "Ach Entry detail records are currently under process";

	/** ACH_ERROR_DETAIL_CNT_OOB. */
	public static final String ACH_ERROR_DETAIL_CNT_OOB = "";

	/** ACH_ERROR_FILE_CNTL_MISSING. */
	public static final String ACH_ERROR_FILE_CNTL_MISSING = "FILE CNTL MISSING";

	/** ACH_ERROR_HDR_MISSING. */
	public static final String ACH_ERROR_HDR_MISSING = "FILE HDR MISSING ";

	/** ACH_FILE_CONTROL. */
	public final static String ACH_FILE_CONTROL = "FILE_CONTROL";

	/** ACH_FILE_CONTROL_RECORD_PROCESS_ERROR. */
	public static final String ACH_FILE_CONTROL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach File Control Record. Error message is: ";

	/** ACH_FILE_GENERATION_COMPLETED. */
	public static final Integer ACH_FILE_GENERATION_COMPLETED = 20;

	/*
	 * ACH_PROCESS_STATUS CODES
	 */
	/** ACH_FILE_GENERATION_NOT_STARTED. */
	public static final Integer ACH_FILE_GENERATION_NOT_STARTED = 0;

	/** ACH_FILE_GENERATION_STARTED. */
	public static final Integer ACH_FILE_GENERATION_STARTED = 10;

	/** ACH_FILE_HEADER. */
	public final static String ACH_FILE_HEADER = "FILE_HEADER";

	/** ACH_FILE_HEADER_RECORD_PROCESS_ERROR. */
	public static final String ACH_FILE_HEADER_RECORD_PROCESS_ERROR = "An error occurred while processing Ach File Header Record. Error message is: ";

	/** ACH_FILE_PROBLEM_FIXED. */
	public static final Integer ACH_FILE_PROBLEM_FIXED = 70;

	/** ACH_ORIGINATION_TOTAL_MISMATCH. */
	public static final String ACH_ORIGINATION_TOTAL_MISMATCH = "Origination and Nacha totals don't match. ";

	/** ACH_PREPROCESS_CHECK_ERROR. */
	public static final String ACH_PREPROCESS_CHECK_ERROR = "Previous process didn't complete successfully. Please check the database and fix the issue";

	/** ACH_PROCESS_BATCH_CHECK_ERROR. */
	public static final String ACH_PROCESS_BATCH_CHECK_ERROR = "Please check BatchId or its status and rerun.";

	/** ACH_PROCESS_COMPLETED_MSG. */
	public static final String ACH_PROCESS_COMPLETED_MSG = "Ach Process Completed";

	/** ACH_PROCESS_HISTORY_LOG_ERROR. */
	public static final String ACH_PROCESS_HISTORY_LOG_ERROR = "An error occurred while updating ach process history log information. Error message is: ";

	/** ACH_PROCESS_INITIATER. */
	public static final String ACH_PROCESS_INITIATER = "ACH ACK PARSER";

	/** ACH_PROCESS_LOG_ERROR. */
	public static final String ACH_PROCESS_LOG_ERROR = "An error occurred while updating ach process log information. Error message is: ";

	/** ACH_PROCESS_OWNER. */
	public static final String ACH_PROCESS_OWNER = "ACH PROCESS SCHEDULER";

	/** ACH_PROCESS_REJECTED_MSG. */
	public static final String ACH_PROCESS_REJECTED_MSG = "Ach Process Acknowledgement Rejected";

	/** ACH_PROCESS_RERUN_ERROR. */
	public static final String ACH_PROCESS_RERUN_ERROR = "An error occurred while re-running the ach transaction process queue information . Error message is: ";

	/** ACH_PROCESS_RETURN. */
	public static final String ACH_PROCESS_RETURN = "RETURN FILE PROCESSOR";

	/** ACH_PROCESS_STARTED_MSG. */
	public static final String ACH_PROCESS_STARTED_MSG = "Ach Process Started";

	/** ACH_PROCESS_STATUS_START. */
	public static final int ACH_PROCESS_STATUS_START = 1;

	/** ACH_PROCESS_TEMP_STATUS. */
	public static final Integer ACH_PROCESS_TEMP_STATUS = 999;

	/** ACH_QUEUE_TRANSACTION_RETRIEVAL_ERROR. */
	public static final String ACH_QUEUE_TRANSACTION_RETRIEVAL_ERROR = "An error occurred while retrieving queue transactions from database. Error message is: ";

	/** ACH_RECONCILED. */
	public final static int ACH_RECONCILED = 40;

	/** ACH_RECORD_COUNT_MISMATCH. */
	public static final String ACH_RECORD_COUNT_MISMATCH =
		"Record count is different in the VO to be written out and in the database. Please check and do the necessary updates before retrying. ";

	/** ACH_RECORD_SUCCESSFULLY_PROCESSED. */
	public static final Integer ACH_RECORD_SUCCESSFULLY_PROCESSED = 80;

	/** ACH_RECORD_TYPE_ADDENDA. */
	public static final String ACH_RECORD_TYPE_ADDENDA = "7";

	/** ACH_RECORD_TYPE_BATCH_CONTROL. */
	public static final String ACH_RECORD_TYPE_BATCH_CONTROL = "8";

	/** ACH_RECORD_TYPE_BATCH_HEADER. */
	public static final String ACH_RECORD_TYPE_BATCH_HEADER = "5";

	/* This constant should have 94 '9's. Don't delete any */
	/** ACH_RECORD_TYPE_BLOCK_CONTROL. */
	public static final String ACH_RECORD_TYPE_BLOCK_CONTROL = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";

	/** ACH_RECORD_TYPE_ENTRY_DETAIL. */
	public static final String ACH_RECORD_TYPE_ENTRY_DETAIL = "6";

	/** ACH_RECORD_TYPE_FILE_CONTROL. */
	public static final String ACH_RECORD_TYPE_FILE_CONTROL = "9";

	/** ACH_RECORD_TYPE_FILE_HEADER. */
	public static final String ACH_RECORD_TYPE_FILE_HEADER = "1";

	/** ACH_REJECTED. */
	public final static int ACH_REJECTED = 50;

	/** ACH_RESPONSE_CONFIRMATION. */
	public final static String ACH_RESPONSE_CONFIRMATION = "RESPONSE_CONFIRMATION";

	/** ACH_RESPONSE_ERROR. */
	public static final String ACH_RESPONSE_ERROR = "ERROR";

	/** ACH_RESPONSE_SUCCESS. */
	public static final String ACH_RESPONSE_SUCCESS = "success            ";

	/** ACH_RETURNED. */
	public final static int ACH_RETURNED = 60;

	/** ACH_SENT. */
	public final static int ACH_SENT = 20;

	/** ACH_SUCCESS. */
	public final static int ACH_SUCCESS = 30;

	/** ACH_TEMP_FILE_NAME. */
	public static final String ACH_TEMP_FILE_NAME = "temp";

	/** ACH_TEMPLATE_RETRIEVAL_ERROR. */
	public static final String ACH_TEMPLATE_RETRIEVAL_ERROR = "An error occurred while retrieving ach template record from database. Error message is: ";

	/** ACH_TRAN_TOTAL_MISMATCH. */
	public static final String ACH_TRAN_TOTAL_MISMATCH = "AchProcessQueue and TranFile totals don't match. ";

	/** ACH_TRANSACTION_BATCH_ID_RETRIEVAL_ERROR. */
	public static final String ACH_TRANSACTION_BATCH_ID_RETRIEVAL_ERROR = "An error occurred while retrieving transaction batch ids from database. Error message is: ";

	/** ACH_TRANSACTION_QUEUE_UDPATE_ERROR. */
	public static final String ACH_TRANSACTION_QUEUE_UDPATE_ERROR = "An error occurred while updating ach transaction process queue information . Error message is: ";

	/** ACH_UNBALANCED_FILE_PROCESS_COMPLETED_MSG. */
	public static final String ACH_UNBALANCED_FILE_PROCESS_COMPLETED_MSG = "Ach Unbalanced File Process Completed";

	/** ACH_UNBALANCED_FILE_PROCESS_REJECTED_MSG. */
	public static final String ACH_UNBALANCED_FILE_PROCESS_REJECTED_MSG = "Ach Unbalanced File Process Acknowledgement Rejected";

	/** ACH_UNBALANCED_FILE_PROCESS_STARTED_MSG. */
	public static final String ACH_UNBALANCED_FILE_PROCESS_STARTED_MSG = "Ach Unbalanced File Process Started";

	// ########################ACH RECONCILED STATUS########################
	/** ACH_UNPROCESSED. */
	public final static int ACH_UNPROCESSED = 10;

	/** AUTO_ACH_CONSOLIDATED_ENTRY. */
	public static final int AUTO_ACH_CONSOLIDATED_ENTRY = 2000;

	/** AUTO_ACH_DEPOSIT_TRANSACTION_TYPE_ID. */
	public static final int AUTO_ACH_DEPOSIT_TRANSACTION_TYPE_ID = 1900;

	/** AUTO_ACH_REFUND_TRANS_CODE_ID. */
	public static final int AUTO_ACH_REFUND_TRANS_CODE_ID = 5;

	/** AUTO_ACH_REFUND_TRANSACTION_TYPE_ID. */
	public static final int AUTO_ACH_REFUND_TRANSACTION_TYPE_ID = 1901;

	/** AUTO_ACH_TRANS_CODE_ID. */
	public static final int AUTO_ACH_TRANS_CODE_ID = 6;

	/** BALANCED_IND_FALSE. */
	public final static Integer BALANCED_IND_FALSE = 0;

	/** BALANCED_IND_TRUE. */
	public final static Integer BALANCED_IND_TRUE = 1;

	/** CONSOLIDATED_CREDIT_CARD_AUTH. */
	public static final int CONSOLIDATED_CREDIT_CARD_AUTH = 1500;

	/** CONSOLIDATED_CREDIT_CARD_REFUNDS. */
	public static final int CONSOLIDATED_CREDIT_CARD_REFUNDS = 1600;

	/** CREDIT_CARD_AUTH_TRANS_CODE_ID. */
	public static final int CREDIT_CARD_AUTH_TRANS_CODE_ID = 9;

	/** CREDIT_CARD_REFUND_TRANS_CODE_ID. */
	public static final int CREDIT_CARD_REFUND_TRANS_CODE_ID = 10;

	/** CREDIT_DEPOSIT_TRANSACTION_TYPE_ID. */
	public static final int CREDIT_DEPOSIT_TRANSACTION_TYPE_ID = 1200;

	/** CREDIT_REFUND_TRANSACTION_TYPE_ID. */
	public static final int CREDIT_REFUND_TRANSACTION_TYPE_ID = 1400;

	/** EMAIL_ACH_FAILURE_RETURN_CODE. */
	public static final int EMAIL_ACH_FAILURE_RETURN_CODE = 33862;

	/** EMAIL_PROFESSIONAL_FIRM_REGISTRATION. */
	public static final int EMAIL_PROFESSIONAL_FIRM_REGISTRATION = 33850;

	/** EMAIL_PROVIDER_REGISTRATION. */
	public static final int EMAIL_PROVIDER_REGISTRATION = 90037;

	/** EMAIL_SLADMIN_REGISTRATION. */
	public static final int EMAIL_SLADMIN_REGISTRATION = 33861;

	/** EMAIL_TEMPLATE_ACH_ACK. */
	public static final int EMAIL_TEMPLATE_ACH_ACK = 81;

	/** EMAIL_TEMPLATE_ACH_CLEARED_WITH_WARNING_CODE. */
	public static final int EMAIL_TEMPLATE_ACH_CLEARED_WITH_WARNING_CODE = 89;

	/** EMAIL_TEMPLATE_ACH_DEPOSIT_FUNDS. */
	public static final int EMAIL_TEMPLATE_ACH_DEPOSIT_FUNDS = 33847;

	/** EMAIL_TEMPLATE_ACH_PROVIDER_WITHDRAW_FUNDS. */
	public static final int EMAIL_TEMPLATE_ACH_PROVIDER_WITHDRAW_FUNDS = 33848;

	/** EMAIL_TEMPLATE_BUYER_CANCEL_PENALTY. */
	public static final int EMAIL_TEMPLATE_BUYER_CANCEL_PENALTY = 215;

	/** EMAIL_TEMPLATE_BUYER_CLOSE_SO. */
	public static final int EMAIL_TEMPLATE_BUYER_CLOSE_SO = 214;

	/** EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS. */
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS = 114;

	/** EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_CONFIRMATION. */
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_CONFIRMATION = 117;

	/** EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_FAILURE. */
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_FAILURE = 116;

	/** EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_WARNING. */
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_WARNING = 115;

	/** EMAIL_TEMPLATE_BUYER_POSTING_FEE. */
	public static final int EMAIL_TEMPLATE_BUYER_POSTING_FEE = 213;

	/** EMAIL_TEMPLATE_BUYER_PROVIDER_ISSUE_REFUNDS. */
	public static final int EMAIL_TEMPLATE_BUYER_PROVIDER_ISSUE_REFUNDS = 112;

	/** EMAIL_TEMPLATE_BUYER_SLBUCKS_CREDIT. */
	public static final int EMAIL_TEMPLATE_BUYER_SLBUCKS_CREDIT = 216;

	/** EMAIL_TEMPLATE_BUYER_SLBUCKS_DEBIT. */
	public static final int EMAIL_TEMPLATE_BUYER_SLBUCKS_DEBIT = 218;

	/** EMAIL_TEMPLATE_BUYER_WITHDRAW_FUNDS. */
	public static final int EMAIL_TEMPLATE_BUYER_WITHDRAW_FUNDS = 113;

	/** EMAIL_TEMPLATE_CC_BUYER_WITHDRAW_FUNDS. */
	public static final int EMAIL_TEMPLATE_CC_BUYER_WITHDRAW_FUNDS = 114;

	/** EMAIL_TEMPLATE_CC_DEPOSIT_FUNDS. */
	public static final int EMAIL_TEMPLATE_CC_DEPOSIT_FUNDS = 54544;

	/** EMAIL_TEMPLATE_GLFEED_FAILED. */
	public static final int EMAIL_TEMPLATE_GLFEED_FAILED = 91;

	/** EMAIL_TEMPLATE_ISSUE_REFUNDS. */
	public static final int EMAIL_TEMPLATE_ISSUE_REFUNDS = 55184;

	/** EMAIL_TEMPLATE_ORIGINATION. */
	public static final int EMAIL_TEMPLATE_ORIGINATION = 79;

	/** EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE. */
	public static final int EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE = 88;

	/** EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_SUCCESS. */
	public static final int EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_SUCCESS = 84;

	/** EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_WARNING. */
	public static final int EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_WARNING = 89;

	/** EMAIL_TEMPLATE_PROVIDER_CLOSE_SO. */
	public static final int EMAIL_TEMPLATE_PROVIDER_CLOSE_SO = 221;

	/** EMAIL_TEMPLATE_PROVIDER_SLBUCKS_CREDIT. */
	public static final int EMAIL_TEMPLATE_PROVIDER_SLBUCKS_CREDIT = 217;

	/** EMAIL_TEMPLATE_PROVIDER_SLBUCKS_DEBIT. */
	public static final int EMAIL_TEMPLATE_PROVIDER_SLBUCKS_DEBIT = 219;

	/** EMAIL_TEMPLATE_PROVIDER_SO_CANCELLED. */
	public static final int EMAIL_TEMPLATE_PROVIDER_SO_CANCELLED = 220;

	/** EMAIL_TEMPLATE_PROVIDER_WITHDRAW_FUNDS. */
	public static final int EMAIL_TEMPLATE_PROVIDER_WITHDRAW_FUNDS = 82;

	/*
	 * ACH File Types
	 */
	/** FILE_TYPE_ACH. */
	public static final int FILE_TYPE_ACH = 1;

	/** FILE_TYPE_ACKNOWLEDGEMENT. */
	public static final int FILE_TYPE_ACKNOWLEDGEMENT = 2;

	/** FILE_TYPE_ORIGINATION. */
	public static final int FILE_TYPE_ORIGINATION = 3;

	/** FILE_TYPE_RETURN. */
	public static final int FILE_TYPE_RETURN = 4;

	/** FIRSTDATA_ACCOUNT_ID. */
	public static final int FIRSTDATA_ACCOUNT_ID = 40;

	/** FIRSTDATA_ACH_BATCH_ID. */
	public static final int FIRSTDATA_ACH_BATCH_ID = 8;

	/** FIRSTDATA_BUSINESS_TRANS_ID. */
	public static final int FIRSTDATA_BUSINESS_TRANS_ID = 20;

	/** FIRSTDATA_ENTITY_ID. */
	public static final int FIRSTDATA_ENTITY_ID = 1;

	/** FIRSTDATA_ENTITY_TYPE_ID. */
	public static final int FIRSTDATA_ENTITY_TYPE_ID = 40;

	/** FIRSTDATA_FAILED_CONSOLIDATED_CREDIT_TRANS. */
	public static final int FIRSTDATA_FAILED_CONSOLIDATED_CREDIT_TRANS = 1100;

	/** FIRSTDATA_PROCESS_STATUS_ID. */
	public static final int FIRSTDATA_PROCESS_STATUS_ID = 10;

	/** FIRSTDATA_TRANSACTION_TYPE_ID. */
	public static final int FIRSTDATA_TRANSACTION_TYPE_ID = 1000;

	/** JPM_ACCOUNT_ID. */
	public static final int JPM_ACCOUNT_ID = 30;

	/** JPM_ENTITY_ID. */
	public static final int JPM_ENTITY_ID = 1;

	/** ORIGINATION_ADDENDA. */
	public final static String ORIGINATION_ADDENDA = "ORIGINATION_ADDENDA";

	/** ORIGINATION_BATCH_CONTROL. */
	public final static String ORIGINATION_BATCH_CONTROL = "ORIGINATION_BATCH_CONTROL";

	/** ORIGINATION_BATCH_HEADER. */
	public final static String ORIGINATION_BATCH_HEADER = "ORIGINATION_BATCH_HEADER";

	/** ORIGINATION_BLOCK_CONTROL. */
	public final static String ORIGINATION_BLOCK_CONTROL = "ORIGINATION_BLOCK_CONTROL";

	/** ORIGINATION_COMPLETION_MESSAGE. */
	public final static String ORIGINATION_COMPLETION_MESSAGE = "Origination Process Completed";

	/** ORIGINATION_ENTRY_DETAIL. */
	public final static String ORIGINATION_ENTRY_DETAIL = "ORIGINATION_ENTRY_DETAIL";

	/** ORIGINATION_FILE_CONTROL. */
	public final static String ORIGINATION_FILE_CONTROL = "ORIGINATION_FILE_CONTROL";

	/** ORIGINATION_FILE_HEADER. */
	public final static String ORIGINATION_FILE_HEADER = "ORIGINATION_FILE_HEADER";

	/** ORIGINATION_FILE_PROCESSED. */
	public static final Integer ORIGINATION_FILE_PROCESSED = 50;

	/** ORIGINATION_PROCESS_OWNER. */
	public static final String ORIGINATION_PROCESS_OWNER = "ORIGINATION PROCESS SCHEDULER";

	/** ORIGINATION_REJECT_PATTERN. */
	public static final String ORIGINATION_REJECT_PATTERN = "REJ0";

	/** RETURN_ADDENDA. */
	public final static String RETURN_ADDENDA = "RETURN_ADDENDA";

	/** RETURNS_ACCT_TRANS_CODE_ID. */
	public static final int RETURNS_ACCT_TRANS_CODE_ID = 6;

	/** RETURNS_FILE_PROCESSED. */
	public static final Integer RETURNS_FILE_PROCESSED = 60;

	/** SERVICELIVE_ENTITY_TYPE_ID. */
	public static final int SERVICELIVE_ENTITY_TYPE_ID = 30;

	/** TEMPLATE_BUYER_CANCELLATION_EMAIL. */
	public static final int TEMPLATE_BUYER_CANCELLATION_EMAIL = 54666;

	/** TEMPLATE_BUYER_CLOSE_EMAIL. */
	public static final int TEMPLATE_BUYER_CLOSE_EMAIL = 54484;

	/** TEMPLATE_BUYER_SLBUCKS_CREDIT. */
	public static final int TEMPLATE_BUYER_SLBUCKS_CREDIT = 54444;

	/** TEMPLATE_BUYER_SLBUCKS_DEBIT. */
	public static final int TEMPLATE_BUYER_SLBUCKS_DEBIT = 54464;

	/** TEMPLATE_BUYER_USER_REMOVED. */
	public static final int TEMPLATE_BUYER_USER_REMOVED = 33860;

	/** TEMPLATE_ID_BUYER_CLOSE_EMAIL. */
	public static final int TEMPLATE_ID_BUYER_CLOSE_EMAIL = 253;

	/** TEMPLATE_ID_PROVIDER_CLOSE_EMAIL. */
	public static final int TEMPLATE_ID_PROVIDER_CLOSE_EMAIL = 254;

	/** TEMPLATE_PROVIDER_REMOVE_USER. */
	public static final int TEMPLATE_PROVIDER_REMOVE_USER = 64686;

	/** UNBALANCED_TRANSACTION_TYPE_ID. */
	public static final int UNBALANCED_TRANSACTION_TYPE_ID = 1100;

}
