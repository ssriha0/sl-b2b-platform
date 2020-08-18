package com.newco.marketplace.utils;

public class AchConstants {

	public static final String ACH_RECORD_TYPE_FILE_HEADER = "1";
	public static final String ACH_RECORD_TYPE_FILE_CONTROL = "9";
	public static final String ACH_RECORD_TYPE_BATCH_HEADER = "5";
	public static final String ACH_RECORD_TYPE_BATCH_CONTROL = "8";
	public static final String ACH_RECORD_TYPE_ENTRY_DETAIL = "6";
	public static final String ACH_RECORD_TYPE_ADDENDA = "7";

	/*This constant should have 94 '9's. Don't delete any  */
	public static final String ACH_RECORD_TYPE_BLOCK_CONTROL = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";


	public static final int JPM_ACCOUNT_ID = 30;
	public static final int JPM_ENTITY_ID = 1;
	public static final int SERVICELIVE_ENTITY_TYPE_ID = 30;

	public static final int FIRSTDATA_ACCOUNT_ID = 40;
	public static final int FIRSTDATA_ENTITY_ID = 1;
	public static final int FIRSTDATA_ENTITY_TYPE_ID = 40;
	public static final int FIRSTDATA_PROCESS_STATUS_ID = 10;
	public static final int FIRSTDATA_TRANSACTION_TYPE_ID = 1000;
	public static final int FIRSTDATA_BUSINESS_TRANS_ID = 20;
	public static final int FIRSTDATA_ACH_BATCH_ID = 8;
	public static final int FIRSTDATA_FAILED_CONSOLIDATED_CREDIT_TRANS = 1100; 
	public static final int UNBALANCED_TRANSACTION_TYPE_ID = 1100;
	public static final int CREDIT_DEPOSIT_TRANSACTION_TYPE_ID = 1200;
	public static final int CREDIT_REFUND_TRANSACTION_TYPE_ID = 1400;
	public static final int CONSOLIDATED_CREDIT_CARD_AUTH = 1500;
	public static final int CONSOLIDATED_CREDIT_CARD_REFUNDS = 1600;
	public static final int AUTO_ACH_DEPOSIT_TRANSACTION_TYPE_ID = 1900;
	public static final int AUTO_ACH_REFUND_TRANSACTION_TYPE_ID = 1901;
	public static final int AUTO_ACH_CONSOLIDATED_ENTRY = 2000;
	public final static String ACH_RESPONSE_CONFIRMATION ="RESPONSE_CONFIRMATION";
	public final static String ACH_FILE_CONTROL ="FILE_CONTROL";
	public final static String ACH_BATCH_HEADER ="BATCH_HEADER";
	public final static String ACH_BATCH_CONTROL ="BATCH_CONTROL";
	public final static String ACH_ADDENDA ="ADDENDA";
	public final static String ACH_ENTRY_DETAIL ="ENTRY_DETAIL";
	public final static String ACH_FILE_HEADER="FILE_HEADER";

	public final static String ORIGINATION_FILE_HEADER ="ORIGINATION_FILE_HEADER";
	public final static String ORIGINATION_FILE_CONTROL ="ORIGINATION_FILE_CONTROL";
	public final static String ORIGINATION_BATCH_HEADER ="ORIGINATION_BATCH_HEADER";
	public final static String ORIGINATION_BATCH_CONTROL ="ORIGINATION_BATCH_CONTROL";
	public final static String ORIGINATION_ENTRY_DETAIL ="ORIGINATION_ENTRY_DETAIL";
	public final static String ORIGINATION_ADDENDA ="ORIGINATION_ADDENDA";	
	public final static String ORIGINATION_BLOCK_CONTROL ="ORIGINATION_BLOCK_CONTROL";
	public final static String RETURN_ADDENDA ="RETURN_ADDENDA";


	public final static String ORIGINATION_COMPLETION_MESSAGE = "Origination Process Completed";
	public static final String ORIGINATION_REJECT_PATTERN = "REJ0";


	public static final int ACH_PROCESS_STATUS_START = 1;

	public static final String ACH_PROCESS_STARTED_MSG = "Ach Process Started";
	public static final String ACH_PROCESS_COMPLETED_MSG = "Ach Process Completed";
	public static final String ACH_PROCESS_REJECTED_MSG = "Ach Process Acknowledgement Rejected";

	public static final String ACH_UNBALANCED_FILE_PROCESS_STARTED_MSG = "Ach Unbalanced File Process Started";
	public static final String ACH_UNBALANCED_FILE_PROCESS_COMPLETED_MSG = "Ach Unbalanced File Process Completed";
	public static final String ACH_UNBALANCED_FILE_PROCESS_REJECTED_MSG = "Ach Unbalanced File Process Acknowledgement Rejected";


	public static final String ACH_PROCESS_INITIATER = "ACH ACK PARSER";
	public static final String ACH_PROCESS_RETURN =  "RETURN FILE PROCESSOR";
	public static final String ACH_PROCESS_OWNER = "ACH PROCESS SCHEDULER";
	public static final String ORIGINATION_PROCESS_OWNER = "ORIGINATION PROCESS SCHEDULER";

	public static final String ACH_TEMP_FILE_NAME = "temp";
	public static final String ACH_CREDIT_DEBIT_MISMATCH = "The total debit doesn't match with total credit " +
			"or the total calculated credit doesnt match with the pre-calculated credit. The transactions are rolled back.";
	public static final String ACH_ENTRY_DETAIL_RECORDS_PROCESSING = "Ach Entry detail records are currently under process";
	public static final String ACH_ENTRY_DETAIL_RECORDS_PROCESS_COMPLETED = "Ach Entry detail records got processed";

	public static final String ACH_BATCH_HEADER_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Batch Header Record. Error message is: ";
	public static final String ACH_BATCH_CONTROL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Batch Control Record. Error message is: ";
	public static final String ACH_FILE_HEADER_RECORD_PROCESS_ERROR = "An error occurred while processing Ach File Header Record. Error message is: ";
	public static final String ACH_FILE_CONTROL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach File Control Record. Error message is: ";
	public static final String ACH_ENTRY_DETAIL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Entry Detail Record. Error message is: ";
	public static final String ACH_ADDENDA_DETAIL_RECORD_PROCESS_ERROR = "An error occurred while processing Ach Addenda Detail Record. Error message is: ";
	public static final String ACH_TEMPLATE_RETRIEVAL_ERROR = "An error occurred while retrieving ach template record from database. Error message is: ";
	public static final String ACH_QUEUE_TRANSACTION_RETRIEVAL_ERROR = "An error occurred while retrieving queue transactions from database. Error message is: ";
	public static final String ACH_TRANSACTION_BATCH_ID_RETRIEVAL_ERROR = "An error occurred while retrieving transaction batch ids from database. Error message is: ";
	public static final String ACH_PROCESS_LOG_ERROR = "An error occurred while updating ach process log information. Error message is: ";
	public static final String ACH_ADMINTOOL_ERROR = "An error occured while running the ACH Admin tool.";
	public static final String ACH_PROCESS_HISTORY_LOG_ERROR = "An error occurred while updating ach process history log information. Error message is: ";
	public static final String ACH_TRANSACTION_QUEUE_UDPATE_ERROR = "An error occurred while updating ach transaction process queue information . Error message is: ";
	public static final String ACH_PROCESS_RERUN_ERROR = "An error occurred while re-running the ach transaction process queue information . Error message is: ";
	public static final String ACH_PREPROCESS_CHECK_ERROR = "Previous process didn't complete successfully. Please check the database and fix the issue";
	public static final String ACH_PROCESS_BATCH_CHECK_ERROR = "Please check BatchId or its status and rerun.";

	// ########################ACH RECONCILED STATUS########################
	public final static int ACH_UNPROCESSED = 10;
	public final static int ACH_SENT = 20;
	public final static int ACH_SUCCESS = 30;
	public final static int ACH_RECONCILED = 40;
	public final static int ACH_REJECTED = 50;
	public final static int ACH_RETURNED = 60;

	/**
	 * Balanced Indicator constants
	 */
	public final static Integer BALANCED_IND_TRUE = 1;
	public final static Integer BALANCED_IND_FALSE = 0;

	/*
	 * ACH_PROCESS_STATUS CODES
	 */
	public static final Integer ACH_FILE_GENERATION_NOT_STARTED = 0;
	public static final Integer ACH_FILE_GENERATION_STARTED = 10;
	public static final Integer ACH_FILE_GENERATION_COMPLETED = 20;
	public static final Integer ACH_ACK_RECEIVED_SUCCESS = 30;
	public static final Integer ACH_ACK_RECEIVED_REJECTED = 40;
	public static final Integer ORIGINATION_FILE_PROCESSED = 50;
	public static final Integer RETURNS_FILE_PROCESSED = 60;
	public static final Integer ACH_FILE_PROBLEM_FIXED = 70;
	public static final Integer ACH_RECORD_SUCCESSFULLY_PROCESSED = 80;
	public static final Integer ACH_PROCESS_TEMP_STATUS = 999;


	public static final String ACH_ERROR_HDR_MISSING = "FILE HDR MISSING ";
	public static final String ACH_ERROR_FILE_CNTL_MISSING ="FILE CNTL MISSING";
	public static final String ACH_ERROR_DETAIL_CNT_OOB="";
	public static final String ACH_RESPONSE_ERROR = "ERROR";
	public static final String ACH_RESPONSE_SUCCESS = "success            ";


	/*
	 * ACH File Types
	 */
	public static final int FILE_TYPE_ACH = 1;
	public static final int FILE_TYPE_ACKNOWLEDGEMENT = 2;
	public static final int FILE_TYPE_ORIGINATION = 3;
	public static final int FILE_TYPE_RETURN = 4;

	public static final int EMAIL_TEMPLATE_ORIGINATION = 79;	
	public static final int EMAIL_TEMPLATE_ACH_ACK = 81;
	public static final int EMAIL_TEMPLATE_PROVIDER_WITHDRAW_FUNDS = 82;
	public static final int EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_SUCCESS = 84;
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_CONFIRMATION = 117;
	public static final int EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_FAILURE = 88;
	public static final int EMAIL_TEMPLATE_PROVIDER_ACH_WITHDRAW_FUNDS_WARNING = 89;	
	public static final int EMAIL_TEMPLATE_ACH_CLEARED_WITH_WARNING_CODE = 89;	

	//SL-21117: Revenue Pull Code change starts
	public static final int EMAIL_TEMPLATE_REVENUE_PULL = 330;
	//Code change ends

	public static final int EMAIL_TEMPLATE_GLFEED_FAILED=91;
	public static final int EMAIL_TEMPLATE_BUYER_PROVIDER_ISSUE_REFUNDS = 112;
	public static final int EMAIL_TEMPLATE_BUYER_WITHDRAW_FUNDS = 113;
	public static final int EMAIL_TEMPLATE_CC_BUYER_WITHDRAW_FUNDS = 114;
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS = 114;
	public static final int EMAIL_TEMPLATE_ACH_DEPOSIT_FUNDS = 33847;
	public static final int EMAIL_TEMPLATE_ACH_PROVIDER_WITHDRAW_FUNDS = 33848;
	public static final int EMAIL_TEMPLATE_CC_DEPOSIT_FUNDS = 54544;
	public static final int EMAIL_TEMPLATE_ISSUE_REFUNDS = 55184;
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_WARNING = 115;
	public static final int EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS_FAILURE = 116;

	public static final int EMAIL_TEMPLATE_BUYER_POSTING_FEE = 213;	
	public static final int EMAIL_TEMPLATE_BUYER_CANCEL_PENALTY = 215;	
	public static final int EMAIL_TEMPLATE_BUYER_SLBUCKS_CREDIT = 216;	
	public static final int EMAIL_TEMPLATE_PROVIDER_SLBUCKS_CREDIT = 217;	
	public static final int EMAIL_TEMPLATE_BUYER_SLBUCKS_DEBIT = 218;	
	public static final int EMAIL_TEMPLATE_SLBUCKS_ESCHEATMENT = 292;
	public static final int EMAIL_TEMPLATE_PROVIDER_SLBUCKS_DEBIT = 219;	
	public static final int EMAIL_TEMPLATE_PROVIDER_SO_CANCELLED = 220;	
	public static final int EMAIL_TEMPLATE_BUYER_CLOSE_SO = 214;	
	public static final int EMAIL_TEMPLATE_PROVIDER_CLOSE_SO = 221;
	public static final int EMAIL_SLADMIN_REGISTRATION = 33861;
	public static final int EMAIL_PROFESSIONAL_FIRM_REGISTRATION = 33850;
	public static final int EMAIL_PROVIDER_REGISTRATION = 90037;
	public static final int TEMPLATE_PROVIDER_REMOVE_USER = 64686;

	public static final int RETURNS_ACCT_TRANS_CODE_ID = 6;
	public static final int AUTO_ACH_TRANS_CODE_ID = 6;
	public static final int AUTO_ACH_REFUND_TRANS_CODE_ID = 5;
	public static final int CREDIT_CARD_AUTH_TRANS_CODE_ID = 9;
	public static final int CREDIT_CARD_REFUND_TRANS_CODE_ID = 10;
	public static final int TEMPLATE_BUYER_SLBUCKS_CREDIT = 54444;
	public static final int TEMPLATE_BUYER_SLBUCKS_DEBIT = 54464;
	public static final int TEMPLATE_BUYER_CANCELLATION_EMAIL = 54666;	
	public static final int TEMPLATE_BUYER_CLOSE_EMAIL = 54484;
	public static final int TEMPLATE_ID_BUYER_CLOSE_EMAIL = 253;
	public static final int TEMPLATE_ID_PROVIDER_CLOSE_EMAIL = 254;
	public static final int TEMPLATE_ID_PROVIDER_USER_REMOVED_EMAIL = 328;

	public static final int TEMPLATE_BUYER_USER_REMOVED = 33860;
	public static final int EMAIL_ACH_FAILURE_RETURN_CODE = 33862;
	public static final int EMAIL_TEMPLATE_CONSUMER_CANT_ACCEPT_SO = 275;
	public static final int EMAIL_TEMPLATE_PROVIDER_CANT_ACCEPT_SO = 276;

	public static final String ACH_ACK_TEMPLATE_COMMENTS_FIELD = "Comments";
	public static final String ACH_ACK_TEMPLATE_CREATED_DATE_FIELD = "File_Create_Date";
	public static final String ACH_ACK_TEMPLATE_CREATED_TIME_FIELD = "File_Create_Time";


	public static final String ACH_ACK_EMAIL_SUBJECT_SUCCESS = "ACH Acknowledgement Successful for Batch Id ";
	public static final String ACH_ACK_EMAIL_SUBJECT_FAILURE = "ACH Acknowledgement Failed for Batch ID ";

	public static final String ACH_AMOUNT_MISMATCH_FAILURE = "ACH Amount mismatch between ACH Process Queue and Ledger Transaction entries. Please update and retry. ";
	public static final String ACH_AMT_MISMATCH_APQ_VO_FAILURE = "ACH Amount mismatch between ACH Process Queue and the values returned from the Process Value Object. Please update and retry. ";
	public static final String ACH_ORIGINATION_TOTAL_MISMATCH = "Origination and Nacha totals don't match. ";
	public static final String ACH_TRAN_TOTAL_MISMATCH = "AchProcessQueue and TranFile totals don't match. ";
	public static final String ACH_RECORD_COUNT_MISMATCH = "Record count is different in the VO to be written out and in the database. Please check and do the necessary updates before retrying. ";

	public static final String FLAG_FOR_CONSUMER_Y = "Y";
	public static final String FLAG_FOR_CONSUMER_N = "N";
}
