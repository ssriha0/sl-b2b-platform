package com.servicelive.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * $Author: Saeid Tehrani $ $Date: 2009/10/05
 */

public abstract class CommonConstants {

	/** The Constant BUSINESS_OWNER_PHONE_NUMBER. */
	public static final String BUSINESS_OWNER_PHONE_NUMBER = "business_owner_phone_number";	
	/** The Constant RECONCILATION_WORKING_DAYS. */
	public static final String RECONCILATION_WORKING_DAYS = "reconcilation_working_days";
	/** ACK_PAGER_ALERT. */
	public static final String ACK_PAGER_ALERT = "ack_failure_notification_pager";
	/** The Constant ACKNOWLEDGEMENT_FILE_ARCHIVE_DIRECTORY. */
	public static final String ACKNOWLEDGEMENT_FILE_ARCHIVE_DIRECTORY = "acknowledgement_file_archive_directory";
	/** The Constant ACKNOWLEDGEMENT_FILE_DIRECTORY. */
	public static final String ACKNOWLEDGEMENT_FILE_DIRECTORY = "acknowledgement_file_directory";
	/** The Constant DAILY_RECONCILIATION_FILE_DIRECTORY. */
	public static final String DAILY_RECONCILIATION_FILE_DIRECTORY = "daily_reconciliation_file_directory";
	/** The Constant NACHA_FILE_ARCHIVE_DIRECTORY. */
	public static final String NACHA_FILE_ARCHIVE_DIRECTORY = "nacha_file_archive_directory";
	/** The Constant NACHA_FILE_DIRECTORY. */
	public static final String NACHA_FILE_DIRECTORY = "nacha_file_directory";
	/** The Constant ORG_PAGER_ALERT. */
	/** The Constant ORIGINATION_FILE_ARCHIVE_DIRECTORY. */
	public static final String ORIGINATION_FILE_ARCHIVE_DIRECTORY = "origination_file_archive_directory";
	/** The Constant ORIGINATION_FILE_DIRECTORY. */
	public static final String ORIGINATION_FILE_DIRECTORY = "origination_file_directory";
	public static final String PROVIDER_MAX_WITHDRAWAL = "provider_max_withdrawal";
	public static final String PROVIDER_MAX_WITHDRAWAL_NO = "provider_max_withdrawal_no";
	public static final String PROVIDER_WITHDRAWAL_AMT_TIME_INTERVAL = "provider_withdrawal_amt_time_interval";
	/** The Constant PTD_ALERT_TO_ADDRESS. */
	/** The Constant RETURNS_FILE_ARCHIVE_DIRECTORY. */
	public static final String RETURNS_FILE_ARCHIVE_DIRECTORY = "returns_file_archive_directory";
	/** The Constant RETURNS_FILE_DIRECTORY. */
	public static final String RETURNS_FILE_DIRECTORY = "returns_file_directory";
	public static final String PTD_FILE_DIRECTORY = "ptd_file_directory";
	public static final String PTD_ARCHIVE_FILE_DIRECTORY = "ptd_file_archive_directory";
	/** The Constant SERVICE_URL. */
	public final static String SERVICE_URL = "servicelive_url";
	/** The Constant SERVICE_URL based on role. */
	public final static String SL_BUCKS_SIMPLE = "sl_bucks_simple";
	public final static String SL_BUCKS_PROFESSIONAL = "sl_bucks_professional";
	/** BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_AMEX. */
	public final static long BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_AMEX = 270;
	/** BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_SEARS. */
	public final static long BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_SEARS = 300;
	// ########################Entity Type Id########################
	
	/** BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_V_MX. */
	public final static long BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_V_MX = 260;
	/** BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_DEPOSIT. */
	public final static long BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_DEPOSIT = 310;
	/** BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_REFUND. */
	public final static long BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_REFUND = 320;
	/** BUSINESS_TRANSACTION_BUYER_REFUND. */
	public final static long BUSINESS_TRANSACTION_BUYER_REFUND = 250;
	/** BUSINESS_TRANSACTION_CANCEL_SO. */
	public final static long BUSINESS_TRANSACTION_CANCEL_SO = 110;
	/** BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY. */
	public final static long BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY = 111;
	/** BUSINESS_TRANSACTION_CLOSE_SO. */
	public final static long BUSINESS_TRANSACTION_CLOSE_SO = 130;
	/** BUSINESS_TRANSACTION_DECREASE_SO_ESCROW. */
	public final static long BUSINESS_TRANSACTION_DECREASE_SO_ESCROW = 160;
	/** BUSINESS_TRANSACTION_DEPOSIT_FUNDS_BUYER_REVERSAL. */
	public final static long BUSINESS_TRANSACTION_DEPOSIT_FUNDS_BUYER_REVERSAL = 50;
	// ######################## TRANSACTION_ID ########################
	/** BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER. */
	public final static long BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER = 10;
	/** BUSINESS_TRANSACTION_INCREASE_SO_ESCROW. */
	public final static long BUSINESS_TRANSACTION_INCREASE_SO_ESCROW = 150;
	/** BUSINESS_TRANSACTION_COMPLETION. */
	public final static long BUSINESS_TRANSACTION_COMPLETION = 155;
	// ########################Transaction Type Id########################
	
	public final static long BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_AMEX = 290;

	public final static long BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_V_MC = 280;
	
	/** BUSINESS_TRANSACTION_MARKETPLACE_WITHDRAW_FUNDS. */
	public final static long BUSINESS_TRANSACTION_MARKETPLACE_WITHDRAW_FUNDS = 140;
	/** BUSINESS_TRANSACTION_NEW_BUYER. */
	public final static long BUSINESS_TRANSACTION_NEW_BUYER = 180;
	/** BUSINESS_TRANSACTION_NEW_PROVIDER. */
	public final static long BUSINESS_TRANSACTION_NEW_PROVIDER = 170;
	/** BUSINESS_TRANSACTION_POST_SO. */
	public final static long BUSINESS_TRANSACTION_POST_SO = 100;
	/** BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT. */
	public final static long BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT = 120;
	/** BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_BUYER. */
	public final static long BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_BUYER = 210;
	/** BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_PROVIDER. */
	public final static long BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_PROVIDER = 220;
	/** BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_BUYER. */
	public final static long BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_BUYER = 230;
	/** BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_PROVIDER. */
	public final static long BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_PROVIDER = 240;
	/** BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_BUYER. */
    public final static long BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_BUYER = 320;  
    /** BUSINESS_TRANSACTION_ESCHEATMENT_SLB_FROM_PROVIDER. */
	public final static long BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_PROVIDER = 330; 
	/**BUSINESS_TRANSACTION_ESCHEATMENT_BUYER_DEBIT_REVERSAL. */
	public final static long BUSINESS_TRANSACTION_ESCHEATMENT_BUYER_DEBIT_REVERSAL = 340; 
	/** BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS. */
	public final static long BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS = 190;
	/** BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS. */
	public final static long BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS = 200;
	/** BUSINESS_TRANSACTION_VOID_SO. */
	public final static long BUSINESS_TRANSACTION_VOID_SO = 115;
	/** BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER. */
	public final static long BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER = 30;
	/** BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER. */
	public final static long BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER = 20;
	/** BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL. */
	public final static long BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL = 40;
	/** BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_AMEX. */
	public final static long BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_AMEX = 290;
	/** BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_V_MC. */
	public final static long BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_V_MC = 280;
	  /** BUSINESS_TRANSACTION_ESCHEAT_SLB_FROM_PROVIDER. */
	public final static int BUSINESS_TRANSACTION_SLA_ESCHEAT_SLB_FROM_BUYER = 320;  
	    /** BUSINESS_TRANSACTION_ESCHEAT_SLB_FROM_PROVIDER. */
	public final static int BUSINESS_TRANSACTION_SLA_ESCHEAT_SLB_FROM_PROVIDER = 330;   
	/** BUYER_ROLE. */
	public static final String BUYER_ROLE = "BUYER";
	/** ENTITY_ID_DEPOSIT_WITHDRAWL. */
	public final static long ENTITY_ID_DEPOSIT_WITHDRAWL = 7;
	/** ENTITY_ID_ESCROW. */
	public final static long ENTITY_ID_ESCROW = 2;
	/** ENTITY_ID_MANAGED_SERVICES. */
	public final static long ENTITY_ID_MANAGED_SERVICES = 3;
	// ########################Financial Type Id########################
	/** ENTITY_ID_SERVICELIVE. */
	public final static long ENTITY_ID_SERVICELIVE = 1;
	/** ENTITY_ID_SERVICELIVE_OPERATION. */
	public final static long ENTITY_ID_SERVICELIVE_OPERATION = 9;
	/** ENTITY_ID_VIRTUAL_CASH. */
	public final static long ENTITY_ID_VIRTUAL_CASH = 8;
	/** ENTRY_TYPE_CREDIT. */
	public final static int ENTRY_TYPE_CREDIT = 2;
	
	//SL-21117: Revenue Pull Code change starts
	
	public final static long REVENUE_PULL_USER_IND = 1;
		
	public final static long REVENUE_PULL_STATUS_IND = 1;
		
	public final static String REVENUE_PULL_INITIAL_STATUS = "PENDING";
		
	//Code change ends
	
	
	// ########################NEW ENTRY TYPE ID ########################
	/** ENTRY_TYPE_DEBIT. */
	public final static int ENTRY_TYPE_DEBIT = 1;
	/** FUNDING_TYPE_DIRECT_FUNDED. */
	public final static long FUNDING_TYPE_DIRECT_FUNDED = 20;
	// ########################Funding Type complete########################
	/**NO_FUNDING_TYPE_ACTION */
	public final static long NO_FUNDING_TYPE_ACTION = 0;
	/** FUNDING_TYPE_NON_FUNDED. */
	public final static long FUNDING_TYPE_NON_FUNDED = 10;
	/** FUNDING_TYPE_PRE_FUNDED. */
	public final static long FUNDING_TYPE_PRE_FUNDED = 30;
	/** LEDGER_ENTITY_TYPE_BUYER. */
	public final static int LEDGER_ENTITY_TYPE_BUYER = 10;
	/** LEDGER_ENTITY_TYPE_DEPOSITS_WITHDRAWLS. */
	public final static int LEDGER_ENTITY_TYPE_DEPOSITS_WITHDRAWLS = 70;
	/** LEDGER_ENTITY_TYPE_FIRSTDATA. */
	public final static int LEDGER_ENTITY_TYPE_FIRSTDATA = 60;
	/** LEDGER_ENTITY_TYPE_MANAGE_SERVICES. */
	public final static int LEDGER_ENTITY_TYPE_MANAGE_SERVICES = 50;
	/** LEDGER_ENTITY_TYPE_PROVIDER. */
	public final static int LEDGER_ENTITY_TYPE_PROVIDER = 20;
	/** LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW. */
	public final static int LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW = 30;
	/** LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN. */
	public final static int LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN = 40;
	/** LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION. */
	public final static int LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION = 90;
	/** LEDGER_ENTITY_TYPE_VIRTUAL_CASH. */
	public final static int LEDGER_ENTITY_TYPE_VIRTUAL_CASH = 80;
	/** PROVIDER_ROLE. */
	public static final String PROVIDER_ROLE = "PROVIDER";
	/** SERVICELIVE_ROLE. */
	public static final String SERVICELIVE_ROLE = "SERVICELIVE";
	/** SHC_FUNDING_TYPE. */
	public final static long SHC_FUNDING_TYPE = 40;
	/** SHC_FUNDING_TYPE. */
	public final static int PRE_FUNDING_TYPE = 30;
	/** SHC_FUNDING_TYPE. */
	public final static int CONSUMER_FUNDING_TYPE = 70;
	// For funding type 90
	public final static long ACH_FUNDING_TYPE_EXTERNAL_BUYER = 90; // @bkumar2 As per SLT 1277 double dipping defect
		
	/** SL_STORE_NO. */
	public static final String SL_STORE_NO = "sl_store_no";
	/** SL_STORE_NO_WO_ZERO. */
	public static final String SL_STORE_NO_WO_ZERO = "sl_store_no_wo_zero";
	/** SL_STORE_NO_WO_ZERO. */
	public static final String MIN_AUTH_TRANS_AMT = "min_auth_trans_amt";
	/** TRANSACTION_TYPE_ID_ACTUAL_RETAIL. */
	public final static long TRANSACTION_TYPE_ID_ACTUAL_RETAIL = 900;
	/** TRANSACTION_TYPE_ID_CANCELLED_ORDER. */
	public final static long TRANSACTION_TYPE_ID_CANCELLED_ORDER = 600;
	/** TRANSACTION_TYPE_ID_CANCELLED_PENALTY. */
	public final static long TRANSACTION_TYPE_ID_CANCELLED_PENALTY = 700;
	/** TRANSACTION_TYPE_ID_CREDIT_DEPOSIT. */
	public final static long TRANSACTION_TYPE_ID_CREDIT_DEPOSIT = 1200;
	/** TRANSACTION_TYPE_ID_CREDIT_WITHDRAWAL. */
	public final static long TRANSACTION_TYPE_ID_CREDIT_WITHDRAWAL = 1400;
	/** TRANSACTION_TYPE_ID_DEPOSIT_CASH. */
	public final static long TRANSACTION_TYPE_ID_DEPOSIT_CASH = 100;
	/** TRANSACTION_TYPE_ID_INSTANT_ACH_DEPOSIT. */
	public final static long TRANSACTION_TYPE_ID_INSTANT_ACH_DEPOSIT = 1900;
	/** TRANSACTION_TYPE_ID_INSTANT_ACH_REFUND. */
	public final static long TRANSACTION_TYPE_ID_INSTANT_ACH_REFUND = 1901;
	/** TRANSACTION_TYPE_ID_ORDER_ESCROW. */
	public final static long TRANSACTION_TYPE_ID_ORDER_ESCROW = 400;
	/** TRANSACTION_TYPE_ID_ORDER_PAYMENT. */
	public final static long TRANSACTION_TYPE_ID_ORDER_PAYMENT = 500;
	/** TRANSACTION_TYPE_ID_POSTING_FEE. */
	public final static long TRANSACTION_TYPE_ID_POSTING_FEE = 200; // aka
	/** TRANSACTION_TYPE_ID_SERVICE_FEE. */
	public final static long TRANSACTION_TYPE_ID_SERVICE_FEE = 300;
	/** TRANSACTION_TYPE_ID_TRANSFER_FUNDS. */
	public final static long TRANSACTION_TYPE_ID_TRANSFER_FUNDS = 1300;
	/** TRANSACTION_TYPE_ID_VOIDED_ORDER. */
	public final static long TRANSACTION_TYPE_ID_VOIDED_ORDER = 750;
	/** TRANSACTION_TYPE_ID_WITHDRAW_CASH. */
	public final static long TRANSACTION_TYPE_ID_WITHDRAW_CASH = 800;
	/** TRANSACTION_TYPE_ID_ESCHEAT_FUNDS. */
	public final static long TRANSACTION_TYPE_ID_ESCHEAT_FUNDS = 2100;
	/** ESHEAT_LEDGER_RULE_BUYER. */
	public final static long ESCHEAT_LEDGER_RULE_BUYER = 5700;
	/** ESHEAT_LEDGER_RULE_BUYER_VIRTUAL. */
	public final static long ESCHEAT_LEDGER_RULE_BUYER_VIRTUAL = 5710;
	/** ESHEAT_LEDGER_RULE_PROVIDER. */
	public final static long ESCHEAT_LEDGER_RULE_PROVIDER = 5800;
	/** ESHEAT_LEDGER_RULE_PROVIDER_VIRTUAL. */
	public final static long ESCHEAT_LEDGER_RULE_PROVIDER_VIRTUAL = 5810;
	
	/** The Constant ACKNOWLEDGEMENT_PROCESS_FAILURE_BODY. */
	public static final String ACKNOWLEDGEMENT_PROCESS_FAILURE_BODY = "Acknowledgment file not found in source directory. ACHAcknowledgmentProcessor failed.";
	/** The Constant ACKNOWLEDGMENT_PROCESS_FAILURE_SUBJECT. */
	public static final String ACKNOWLEDGMENT_PROCESS_FAILURE_SUBJECT = "Acknowledgment file not found";
	/** The Constant BUSINESS_OWNER. */
	public static final String BUSINESS_OWNER = "business_owner";
	/** The Constant DAILY_RECONCILIATION_EMAIL_BODY. */
	public static final String DAILY_RECONCILIATION_EMAIL_BODY = "daily_reconciliation_email_body";
	/** The Constant DAILY_RECONCILIATION_EMAIL_TO. */
	public static final String DAILY_RECONCILIATION_EMAIL_TO = "daily_reconciliation_email_to";
	/** The Constant EMAIL_PROCESS_FAILURE_SUBJECT. */
	public static final String EMAIL_PROCESS_FAILURE_SUBJECT = "Batch Processing Failed and Threw a Exception";
	/** The Constant NO_REPLY. */
	public static final String NO_REPLY = "email_sl_no_reply";
	/** The Constant ORIGINATION_PROCESS_FAILURE_BODY. */
	public static final String ORIGINATION_PROCESS_FAILURE_BODY = "Origination file not found in source directory. ACHOriginationProcessor failed.";
	/** The Constant ORIGINATION_PROCESS_FAILURE_SUBJECT. */
	public static final String ORIGINATION_PROCESS_FAILURE_SUBJECT = "Origination file not found";
	/** The Constant SERVICELIVE_ADMIN. */
	public static final String SERVICELIVE_ADMIN = "email_admin_servicelive";
	/** SERVICE_LIVE_MAILID. */
	public static final String SERVICE_LIVE_MAILID = "noreply@servicelive.com";
	/** SERVICE_LIVE_MAILID_SO_SUPPORT. */
	public static final String SERVICE_LIVE_MAILID_SO_SUPPORT = "serviceordersupport@servicelive.com";
	/** SERVICE_LIVE_MAILID_SUPPORT. */
	public static final String SERVICE_LIVE_MAILID_SUPPORT = "support@servicelive.com";
	/** SERVICE_LIVE_PROD_SUPPORT_EMAIL_ID    */
	public static final String SERVICE_LIVE_MAILID_PROD_SUPPORT = "SLPRODSUPP@searshc.com";	
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
	/** The Constant ACH_CC_ROLLUP_COMPLETED. */
	public static final Integer ACH_CC_ROLLUP_COMPLETED = 25;
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
	/** The Constant EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE. */
	public static final int EMAIL_TEMPLATE_ESCHEATMENT_ACH_WITHDRAW_FUNDS_FAILURE = 293;
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
	/** ACTIVE_ACCOUNT_STATUS. */
	public static final int ACTIVE_ACCOUNT_STATUS = 1;
	/** BILLING_LOCATION_ID. */
	public static final int BILLING_LOCATION_ID = 5;
	/** BUSINESS_TRANSACTION_CC_AMEX. */
	public static final int BUSINESS_TRANSACTION_CC_AMEX = 270;
	/** BUSINESS_TRANSACTION_CC_SEARS_CARDS. */
	public static final int BUSINESS_TRANSACTION_CC_SEARS_CARDS = 300;
	/** BUSINESS_TRANSACTION_CC_VMC. */
	public static final int BUSINESS_TRANSACTION_CC_VMC = 260;
	/** CARD_ID_AMEX. */
	public static final int CARD_ID_AMEX = 8;
	/** CARD_ID_AMEX_STR. */
	public static final String CARD_ID_AMEX_STR = "AX";
	/** CARD_ID_COMMERCIAL_ONE_STR. */
	public static final String CARD_ID_COMMERCIAL_ONE_STR = "CM";
	/** CARD_ID_DISCOVER. */
	public static final int CARD_ID_DISCOVER = 5;
	/** CARD_ID_DISCOVER_STR. */
	public static final String CARD_ID_DISCOVER_STR = "DD";
	/** CARD_ID_GIFT_STR. */
	public static final String CARD_ID_GIFT_STR = "??"; // No definition given by business
	/** CARD_ID_MASTERCARD. */
	public static final int CARD_ID_MASTERCARD = 7;
	/** CARD_ID_MASTERCARD_STR. */
	public static final String CARD_ID_MASTERCARD_STR = "MC";
	/** CARD_ID_SEARS. */
	public static final int CARD_ID_SEARS = 0;
	/** CARD_ID_SEARS_CHARGE_PLUS_STR. */
	public static final String CARD_ID_SEARS_CHARGE_PLUS_STR = "SP";
	/** CARD_ID_SEARS_CHARGE_STR. */
	public static final String CARD_ID_SEARS_CHARGE_STR = "SS";
	/** CARD_ID_SEARS_COMMERCIAL. */
	public static final int CARD_ID_SEARS_COMMERCIAL = 3;
	/** CARD_ID_SEARS_MASTERCARD. */
	public static final int CARD_ID_SEARS_MASTERCARD = 4;
	/** CARD_ID_SEARS_PLUS. */
	public static final int CARD_ID_SEARS_PLUS = 2;
	/** CARD_ID_SEARS_PREMIER. */
	public static final int CARD_ID_SEARS_PREMIER = 1;
	/** CARD_ID_VISA. */
	public static final int CARD_ID_VISA = 6;
	/** CARD_ID_VISA_STR. */
	public static final String CARD_ID_VISA_STR = "VI";
	/** CC_ADDRESS_MAX_LENGTH. */
	public static final int CC_ADDRESS_MAX_LENGTH = 20;
	/** CREDIT_CARD_AUTH_URL. */
	public static final String CREDIT_CARD_AUTH_URL = "webservices.rtca.url";
	/** DIVISION. */
	public static final String DIVISION = "000";
	/** SEARS_CARD_16_BIN_RANGE. */
	public static final String[] SEARS_CARD_16_BIN_RANGE = { "504994", "380000", "381000", "382000", "383000" };
	/** SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS. */
	public static final String SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS = "540553";
	/** SEARS_MASTER_CARD_BIN_RANGE. */
	public static final String SEARS_MASTER_CARD_BIN_RANGE[] = { "512106", "512107", "518537", "512108", "520094", "520118", "520611", "520612", "521331", "549506" };
	/** SEARS_WHITE_CARD_BIN_RANGE. */
	public static final String[] SEARS_WHITE_CARD_BIN_RANGE =
		{ "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "11", "20", "21", "34", "36", "40", "44", "48", "50", "54", "57", "60", "64", "70", "75", "80", "81", "82",
			"95" };
	public static final String SYW_CARD_BIN = "512108";
	public static final String SYW_PAYMENT_TYPE_CODE = "J";
	/** ZIPCODE_MAX_LENGTH. */
	public static final int ZIPCODE_MAX_LENGTH = 9;
	/** The Constant ACTIVATION. */
	public static final int ACTIVATION = 1;
	/** The Constant ADMIN_TOOL_REDEEM. */
	public static final int ADMIN_TOOL_REDEEM = 800;
	/** The Constant ADMIN_TOOL_RELOAD. */
	public static final int ADMIN_TOOL_RELOAD = 460;
	/** The Constant BALANCE_INQUIRY. */
	public static final int BALANCE_INQUIRY = 400;
	/** The Constant EC_AMOUNT_MISMATCH. */
	public static final int EC_AMOUNT_MISMATCH = 2;
	/** The Constant EC_BAD_RESPONSE. */
	public static final int EC_BAD_RESPONSE = 4;
	/** The Constant EC_IGNORE. */
	public static final int EC_IGNORE = 0;
	/** The Constant EC_NO_ERROR. */
	public static final int EC_NO_ERROR = 1;
	/** The Constant EC_NO_RESPONSE. */
	public static final int EC_NO_RESPONSE = 5;
	/** The Constant EC_PTD_ENTRY_NOT_FOUND. */
	public static final int EC_PTD_ENTRY_NOT_FOUND = 6;
	/** The Constant EC_SIGN_MISMATCH. */
	public static final int EC_SIGN_MISMATCH = 3;
	/** The Constant EC_SLDB_ENTRY_NOT_FOUND. */
	public static final int EC_SLDB_ENTRY_NOT_FOUND = 8;
	/** The Constant EC_VLBC_ACTIVITY. */
	public static final int EC_VLBC_ACTIVITY = 7;
	/** The Constant PTD_ACTIVATION. */
	public static final int PTD_ACTIVATION = 2102;
	/** The Constant PTD_CLGC_BALANCE_INQUIRY. */
	public static final int PTD_CLGC_BALANCE_INQUIRY = 6405;
	/** The Constant PTD_CLGC_TRANS_HISTORY. */
	public static final int PTD_CLGC_TRANS_HISTORY = 6415;
	/** The Constant PTD_EMAIL_BAD_TRANSACTIONS. */
	public static final String PTD_EMAIL_BAD_TRANSACTIONS = "Bad Transactions that were PTD Reconciled";
	/** The Constant PTD_FILE_NOT_FOUND. */
	public static final String PTD_FILE_NOT_FOUND = "PTD File not found in the directory, ";
	/** The Constant PTD_HEADER. */
	public static final String PTD_HEADER = "PTD_HEADER";
	/** The Constant PTD_NON_RECONCILED_HEADER_MSG. */
	public static final String PTD_NON_RECONCILED_HEADER_MSG =
		"The following transactions weren't reconciled. The process couldn't find a matching transaction in SL Database.\n"
			+ " Following are the fullfillment entry ids as received from Value Link\n";
	/** The Constant PTD_NON_RECONCILED_TRANS_MSG. */
	public static final String PTD_NON_RECONCILED_TRANS_MSG = "Following are the fullfillment entry ids as received from Value Link\n";
	/** The Constant PTD_PROCESS_FAILURE_SUBJECT. */
	public static final String PTD_PROCESS_FAILURE_SUBJECT = "PTD File Process failed with Exception. ";
	/** The Constant PTD_PROCESS_NON_RECONCILED_TRANSACTIONS. */
	public static final String PTD_PROCESS_NON_RECONCILED_TRANSACTIONS = "PTD File process - A few non-reconciled transactions ";
	/** The Constant PTD_PROCESS_STATUS. */
	public static final String PTD_PROCESS_STATUS = "10";
	/** The Constant PTD_PROCESS_SUCCESS_SUBJECT. */
	public static final String PTD_PROCESS_SUCCESS_SUBJECT = "PTD File Processed successfully - no errors.";
	/** The Constant PTD_PROCESS_SUCCESS_UNREC_SUBJECT. */
	public static final String PTD_PROCESS_SUCCESS_UNREC_SUBJECT = "PTD File Processed successfully with Unreconciled records.";
	/** The Constant PTD_RECONCILATION_STATUS_MARKED. */
	public static final int PTD_RECONCILATION_STATUS_MARKED = 10;
	/** The Constant PTD_RECONCILATION_STATUS_SUCCESS. */
	public static final int PTD_RECONCILATION_STATUS_SUCCESS = 10;
	/** The Constant PTD_REDEMPTION. */
	public static final int PTD_REDEMPTION = 200;
	/** The Constant PTD_REDEMPTION_NO_NSF. */
	public static final int PTD_REDEMPTION_NO_NSF = 202;
	/** The Constant PTD_RELOAD. */
	public static final int PTD_RELOAD = 300;
	/** The Constant PTD_STORE_BALANCE_INQUIRY. */
	public static final int PTD_STORE_BALANCE_INQUIRY = 450;
	/** The Constant PTD_TRAILER. */
	public static final String PTD_TRAILER = "PTD_TRAILER";
	/** The Constant PTD_TRANSACTION. */
	public static final String PTD_TRANSACTION = "PTD_TRANSACTION";
	/** The Constant REDEMPTION. */
	public static final int REDEMPTION = 3;
	/** The Constant RELOAD. */
	public static final int RELOAD = 2;
	/** The Constant TIME_OUT_REVERSAL_IGNORE. */
	public static final int TIME_OUT_REVERSAL_IGNORE = 704;
	/** ACCOUNT_TYPE_BUSINESS_CHECKING. */
	public final static long ACCOUNT_TYPE_BUSINESS_CHECKING = 40;
	/** ACCOUNT_TYPE_BUSINESS_SAVINGS. */
	public final static long ACCOUNT_TYPE_BUSINESS_SAVINGS = 50;
	/** ACCOUNT_TYPE_CREDIT_CARD. */
	public final static long ACCOUNT_TYPE_CREDIT_CARD = 30;
	/** ACCOUNT_TYPE_PERSONAL_CHECKING. */
	public final static long ACCOUNT_TYPE_PERSONAL_CHECKING = 10;
	/** ACCOUNT_TYPE_PERSONAL_SAVINGS. */
	public final static long ACCOUNT_TYPE_PERSONAL_SAVINGS = 20;
	// ########################Rule Funding Type########################
	/** ACH_AMOUNT. */
	public final static String ACH_AMOUNT = "ACH_AMOUNT";
	/** CANCELLATION_FEE. */
	public final static String CANCELLATION_FEE = "CANCELLATION_FEE";
	// Consolidated Balance ledger_entry_id
	/** CONSOLIDATED_BALANCE_LEDGER_ID. */
	public final static int CONSOLIDATED_BALANCE_LEDGER_ID = 0;
	/** CREDIT_CARD_AMEX. */
	
	public final static long CREDIT_CARD_AMEX = 8;
	/** CREDIT_CARD_MC. */
	public final static long CREDIT_CARD_MC = 7;
	// ######################## Miscellaneous ########################
	
	/** CREDIT_CARD_SEARS. */
	public final static long CREDIT_CARD_SEARS = 4;
	/** CREDIT_CARD_VISA. */
	public final static long CREDIT_CARD_VISA = 6;
	/** DEFAULT_CATEGORY. */
	public final static String DEFAULT_CATEGORY = "0100";
	/** DEFAULT_DIVISION. */
	public final static String DEFAULT_DIVISION = "0400";
	/** DEPOSIT_AMOUNT. */
	public final static String DEPOSIT_AMOUNT = "DEPOSIT_AMOUNT";
	/** FINAL_LABOUR. */
	public final static String FINAL_LABOUR = "FINAL_LABOUR";
	/** FINAL_PARTS. */
	public final static String FINAL_PARTS = "FINAL_PARTS";
	/** FINAL_SERVICE_FEE. */
	public final static String FINAL_SERVICE_FEE = "FINAL_SERVICE_FEE";
	/** GL_ACCOUNTS_RETAIL_INSTALLATION_CASH_ACCOUNT. */
	public final static int GL_ACCOUNTS_RETAIL_INSTALLATION_CASH_ACCOUNT = 10302;
	/** GL_ACCOUNTS_RETAIL_INSTALLATION_LOCATION. */
	public final static String GL_ACCOUNTS_RETAIL_INSTALLATION_LOCATION = "54584";
	/** GL_ACCOUNTS_RETAIL_INSTALLATION_PREPAID_EXPENSES_ACCOUNT. */
	public final static String GL_ACCOUNTS_RETAIL_INSTALLATION_PREPAID_EXPENSES_ACCOUNT = "13165";
	/** GL_ACCOUNTS_RETAIL_INSTALLATION_SERVICE_COMMISSION_ACCOUNT. */
	public final static String GL_ACCOUNTS_RETAIL_INSTALLATION_SERVICE_COMMISSION_ACCOUNT = "50611";
	/** GL_ACCOUNTS_SHC_COMMISSION_ACCOUNT. */
	public final static String GL_ACCOUNTS_SHC_COMMISSION_ACCOUNT = "09930";
	// ########################NEW Rule Id########################
	
	/** GL_PROCESSED_FALSE. */
	public static final int GL_PROCESSED_FALSE = 0;
	/** GL_PROCESSED_TRUE. */
	public static final int GL_PROCESSED_TRUE = 1;
	/** HASHMAP_AMOUNT. */
	public final static String HASHMAP_AMOUNT = "amount";
	/** HASHMAP_COUNT. */
	public final static String HASHMAP_COUNT = "count";
	/** INITIAL_SPEND_LIMIT. */
	public final static String INITIAL_SPEND_LIMIT = "INITIAL_SPEND_LIMIT";
	/** LABOUR_SPEND_LIMIT. */
	public final static String LABOUR_SPEND_LIMIT = "LABOUR_SPEND_LIMIT";
	/** PARTS_SPEND_LIMIT. */
	public final static String PARTS_SPEND_LIMIT = "PARTS_SPEND_LIMIT";
	/** POSTING_FEE. */
	public final static String POSTING_FEE = "POSTING_FEE";
	/** RECONCILED_STATUS_RECONCILED. */
	public final static int RECONCILED_STATUS_RECONCILED = 40;
	/** RECONCILED_STATUS_REJECTED. */
	public final static int RECONCILED_STATUS_REJECTED = 50;
	/** RECONCILED_STATUS_RETURNED. */
	public final static int RECONCILED_STATUS_RETURNED = 60;
	/** RECONCILED_STATUS_SENT. */
	public final static int RECONCILED_STATUS_SENT = 20;
	/** RECONCILED_STATUS_SUCCESS. */
	public final static int RECONCILED_STATUS_SUCCESS = 30;
	/** RECONCILED_STATUS_UNPROCESSED. */
	public final static int RECONCILED_STATUS_UNPROCESSED = 10;
	/** RETAIL_CANCELLATION_FEE. */
	public final static String RETAIL_CANCELLATION_FEE = "RETAIL_CANCELLATION_FEE";
	/** RETAIL_SO_PRICE. */
	public final static String RETAIL_SO_PRICE = "RETAIL_SO_PRICE";
	/** RI_COMMISSION_DESC. */
	public final static String RI_COMMISSION_DESC = "Commission";
	/** RI_CONTRACTOR_EXPENSE_DESC. */
	public final static String RI_CONTRACTOR_EXPENSE_DESC = "Contractor Expense";
	/** RI_PREPAID_EXPENSE_DESC. */
	public final static String RI_PREPAID_EXPENSE_DESC = "Prepaid Expense";
	/** RULE_ID_ACTUAL_PENALITY. */
	public final static long RULE_ID_ACTUAL_PENALITY = 1340;
	/** RULE_ID_APPLY_PENALTY_TO_CONTRACTOR. */
	public final static long RULE_ID_APPLY_PENALTY_TO_CONTRACTOR = 1330;
	/** RULE_ID_APPLY_POSTING_FEE. */
	public final static long RULE_ID_APPLY_POSTING_FEE = 1100;
	/** RULE_ID_APPLY_POSTING_FEE_VIRTUAL. */
	public final static long RULE_ID_APPLY_POSTING_FEE_VIRTUAL = 1110;
	/** RULE_ID_APPLY_SERVICE_FEE. */
	public final static long RULE_ID_APPLY_SERVICE_FEE = 1410;
	/** RULE_ID_APPLY_SERVICE_FEE_VIRTUAL. */
	public final static long RULE_ID_APPLY_SERVICE_FEE_VIRTUAL = 1430;
	/** RULE_ID_CREDIT_RETAIL_COST_OF_SO. */
	public final static long RULE_ID_CREDIT_RETAIL_COST_OF_SO = 1450;
	/** RULE_ID_DECLARE_FINAL_PRICE. */
	public final static long RULE_ID_DECLARE_FINAL_PRICE = 1405;
	/** RULE_ID_DEPOSIT_CASH. */
	public final static long RULE_ID_DEPOSIT_CASH = 1000;
	/** RULE_ID_DEPOSIT_CASH_CC_SEARS. */
	public final static long RULE_ID_DEPOSIT_CASH_CC_SEARS = 1003;
	/** RULE_ID_DEPOSIT_CASH_CC_V_AMEX. */
	public final static long RULE_ID_DEPOSIT_CASH_CC_V_AMEX = 1002;
	/** RULE_ID_DEPOSIT_CASH_CC_V_MC. */
	public final static long RULE_ID_DEPOSIT_CASH_CC_V_MC = 1001;
	/** RULE_ID_DEPOSIT_CASH_SLA_OPERATIONS. */
	public final static long RULE_ID_DEPOSIT_CASH_SLA_OPERATIONS = 5100;
	/** RULE_ID_GL_DEBIT_PMT_FROM_CONTRACTOR_PMT. */
	public final static long RULE_ID_GL_DEBIT_PMT_FROM_CONTRACTOR_PMT = 1440;
	/** RULE_ID_INCREASE_SPEND_LIMIT. */
	public final static long RULE_ID_INCREASE_SPEND_LIMIT = 1200;
	/** RULE_ID_INSTANT_ACH_DEPOSIT. */
	public final static long RULE_ID_INSTANT_ACH_DEPOSIT = 1004;
	/** RULE_ID_POSTING_FEE_RECEIVABLE. */
	public final static long RULE_ID_POSTING_FEE_RECEIVABLE = 1101;
	/** RULE_ID_PROVIDER_PAYMENT_PAYABLE. */
	public final static long RULE_ID_PROVIDER_PAYMENT_PAYABLE = 1401;
	/** RULE_ID_REFUND_ESCROW. */
	public final static long RULE_ID_REFUND_ESCROW = 1420;
	/** RULE_ID_REFUND_ESCROW_CANCEL_SO. */
	public final static long RULE_ID_REFUND_ESCROW_CANCEL_SO = 1350;
	/** RULE_ID_REFUND_ESCROW_CANCEL_SO_WO_PENALTY. */
	public final static long RULE_ID_REFUND_ESCROW_CANCEL_SO_WO_PENALTY = 1390;
	/** RULE_ID_REFUND_ESCROW_VOID_SO. */
	public final static long RULE_ID_REFUND_ESCROW_VOID_SO = 1370;
	/** RULE_ID_RELEASE_PENALITY_PAYMENT. */
	public final static long RULE_ID_RELEASE_PENALITY_PAYMENT = 1300;
	/** RULE_ID_RELEASE_SO_PAYMENT. */
	public final static long RULE_ID_RELEASE_SO_PAYMENT = 1400;
	/** RULE_ID_RESERVE_SO_FUNDING. */
	public final static long RULE_ID_RESERVE_SO_FUNDING = 1120;
	/** RULE_ID_RETRUN_SO_FUNDING. */
	public final static long RULE_ID_RETRUN_SO_FUNDING = 1310;
	/** RULE_ID_SERVICE_FEE_RECEIVABLE. */
	public final static long RULE_ID_SERVICE_FEE_RECEIVABLE = 1411;
	/** RULE_ID_SHC_PREPAID_INSTANT_ACH. */
	public final static long RULE_ID_SHC_PREPAID_INSTANT_ACH = 10001;
	/** RULE_ID_SHC_RELEASE_PENALITY_PAYMENT. */
	public final static long RULE_ID_SHC_RELEASE_PENALITY_PAYMENT = 1301;
	/** RULE_ID_SHC_RELEASE_SO_PAYMENT. */
	public final static long RULE_ID_SHC_RELEASE_SO_PAYMENT = 10005;
	/** RULE_ID_SL_PROVIDER_PAYMENT_PAYABLE. */
	public final static long RULE_ID_SL_PROVIDER_PAYMENT_PAYABLE = 10006;
	/** RULE_ID_SL_RECEIVABLE_FOR_COMMISSION. */
	public final static long RULE_ID_SL_RECEIVABLE_FOR_COMMISSION = 10003;
	/** RULE_ID_TRANSFER_COMMISSION. */
	public final static long RULE_ID_TRANSFER_COMMISSION = 10002;
	/** RULE_ID_TRANSFER_FROM_BUYER_TO_SLA_OPERATIONS. */
	public final static long RULE_ID_TRANSFER_FROM_BUYER_TO_SLA_OPERATIONS = 5500;
	/** RULE_ID_TRANSFER_FROM_BUYER_TO_SLA_OPERATIONS_GL. */
	public final static long RULE_ID_TRANSFER_FROM_BUYER_TO_SLA_OPERATIONS_GL = 5510;
	/** RULE_ID_TRANSFER_FROM_PROVIDER_TO_SLA_OPERATIONS. */
	public final static long RULE_ID_TRANSFER_FROM_PROVIDER_TO_SLA_OPERATIONS = 5600;
	/** RULE_ID_TRANSFER_FROM_PROVIDER_TO_SLA_OPERATIONS_GL. */
	public final static long RULE_ID_TRANSFER_FROM_PROVIDER_TO_SLA_OPERATIONS_GL = 5610;
	/** RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_BUYER. */
	public final static long RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_BUYER = 5300;
	/** RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_BUYER_GL. */
	public final static long RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_BUYER_GL = 5310;
	/** RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_PROVIDER. */
	public final static long RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_PROVIDER = 5400;
	/** RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_PROVIDER_GL. */
	public final static long RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_PROVIDER_GL = 5410;
	/** RULE_ID_VIRTUAL_CASH_CANCEL_SO_WO_PENALTY. */
	public final static long RULE_ID_VIRTUAL_CASH_CANCEL_SO_WO_PENALTY = 1395;
	/** RULE_ID_VIRTUAL_CASH_DEPOSIT. */
	public final static long RULE_ID_VIRTUAL_CASH_DEPOSIT = 1010; // For Post SO
	/** RULE_ID_VIRTUAL_CASH_DEPOSIT_INCREASE_SPEND_LIMIT. */
	public final static long RULE_ID_VIRTUAL_CASH_DEPOSIT_INCREASE_SPEND_LIMIT = 1210;
	/** RULE_ID_VIRTUAL_CASH_RETURN. */
	public final static long RULE_ID_VIRTUAL_CASH_RETURN = 1460;
	/** RULE_ID_VIRTUAL_CASH_RETURN_CANCEL_SO. */
	public final static long RULE_ID_VIRTUAL_CASH_RETURN_CANCEL_SO = 1360;
	/** RULE_ID_VIRTUAL_CASH_RETURN_VOID_SO. */
	public final static long RULE_ID_VIRTUAL_CASH_RETURN_VOID_SO = 1380;
	/** RULE_ID_VIRTUAL_REFUND_ESCROW_DECREASE_SPEND_LIMIT. */
	public final static long RULE_ID_VIRTUAL_REFUND_ESCROW_DECREASE_SPEND_LIMIT = 1250;
	/** RULE_ID_WITHDRAWAL_CASH_BUYER. */
	public final static long RULE_ID_WITHDRAWAL_CASH_BUYER = 2000;
	/** RULE_ID_WITHDRAWAL_CASH_CC_AMEX. */
	public final static long RULE_ID_WITHDRAWAL_CASH_CC_AMEX = 2020;
	/** RULE_ID_WITHDRAWAL_CASH_CC_V_MC. */
	public final static long RULE_ID_WITHDRAWAL_CASH_CC_V_MC = 2010;
	/** RULE_ID_WITHDRAWAL_CASH_PROVIDER. */
	public final static long RULE_ID_WITHDRAWAL_CASH_PROVIDER = 5000;
	/** RULE_ID_WITHDRAWAL_CASH_PROVIDER_REVERSAL. */
	public final static long RULE_ID_WITHDRAWAL_CASH_PROVIDER_REVERSAL = 5010;
	/** RULE_ID_WITHDRAWAL_CASH_SLA_OPERATIONS. */
	public final static long RULE_ID_WITHDRAWAL_CASH_SLA_OPERATIONS = 5110;
	/** SERVICE_FEE_TOTAL. */
	public final static String SERVICE_FEE_TOTAL = "ServiceFeeTotal";
	/** STATE_IL. */
	public final static String STATE_IL = "IL";
	/** T_ACCOUNT_NO_ACCESS_FEE. */
	public final static long T_ACCOUNT_NO_ACCESS_FEE = 40500;
	/** T_ACCOUNT_NO_ACCOUNTS_RECEIVABLE. */
	public final static long T_ACCOUNT_NO_ACCOUNTS_RECEIVABLE = 11300;
	/** T_ACCOUNT_NO_AMEX_RECEIVABLE. */
	public final static long T_ACCOUNT_NO_AMEX_RECEIVABLE = 11525;
	// ################## NEW T_ACCOUNT_NO complete ###################
	/** T_ACCOUNT_NO_CASH. */
	public final static long T_ACCOUNT_NO_CASH = 10258;
	/** T_ACCOUNT_NO_CUSTOMER_DEPOSITS. */
	public final static long T_ACCOUNT_NO_CUSTOMER_DEPOSITS = 21150;
	/** T_ACCOUNT_NO_INSTALLATION_SALES. */
	public final static long T_ACCOUNT_NO_INSTALLATION_SALES = 40650;
	/** T_ACCOUNT_NO_PAYMENT_TO_CONTRACTORS. */
	public final static long T_ACCOUNT_NO_PAYMENT_TO_CONTRACTORS = 50650;
	/** T_ACCOUNT_NO_SERVICE_FEE. */
	public final static long T_ACCOUNT_NO_SERVICE_FEE = 40501;
	/** T_ACCOUNT_NO_V_MC_RECEIVABLE. */
	public final static long T_ACCOUNT_NO_V_MC_RECEIVABLE = 11531;
	/** TRANSFER_AMOUNT. */
	public final static String TRANSFER_AMOUNT = "TRANSFER_AMOUNT";
	/** TRANSFER_REASONCODE_ADJUSTMENT_CREDIT. */
	public final static int TRANSFER_REASONCODE_ADJUSTMENT_CREDIT = 30;
	// ###################### GL FEED ACCOUNT CONSTANTS ########################
	
	/** TRANSFER_REASONCODE_ADJUSTMENT_DEBIT. */
	public final static int TRANSFER_REASONCODE_ADJUSTMENT_DEBIT = 40;
	// ############# TRANSFER SLBUCKS REASON CODES ###########################
	/** TRANSFER_REASONCODE_GOODWILL_CREDIT. */
	public final static int TRANSFER_REASONCODE_GOODWILL_CREDIT = 10;
	/** TRANSFER_REASONCODE_OTHER_CREDIT. */
	public final static int TRANSFER_REASONCODE_OTHER_CREDIT = 50;
	/** TRANSFER_REASONCODE_OTHER_DEBIT. */
	public final static int TRANSFER_REASONCODE_OTHER_DEBIT = 60;
	/** TRANSFER_REASONCODE_REFUND_CREDIT. */
	public final static int TRANSFER_REASONCODE_REFUND_CREDIT = 20;
	/** UPSELL_PROVIDER_TOTAL. */
	public final static String UPSELL_PROVIDER_TOTAL = "UPSELL_PROVIDER_TOTAL";
	public final static String UPSELL_PARTS_TOTAL = "UPSELL_PARTS_TOTAL";

	
	/** UPSELL_SERVICE_FEE. */
	public final static String UPSELL_SERVICE_FEE = "UPSELL_SERVICE_FEE";
	/** USER_ENTERED. */
	public final static String USER_ENTERED = "USER_ENTERED";
	/** USER_TYPE_BUYER. */
	public final static String USER_TYPE_BUYER = "Buyer";
	/** USER_TYPE_PROVIDER. */
	public final static String USER_TYPE_PROVIDER = "Provider";
	/** ZERO. */
	public final static String ZERO = "ZERO";
	/** The Constant CUSTOMER_STATUS_ID_CODE. */
	public final static String CUSTOMER_STATUS_ID_CODE = " ";
	/** The Constant DIVISION_NUMBER. */
	public final static String DIVISION_NUMBER = "198";
	/** The Constant HEADER_SEGMENT_LENGTH. */
	public final static String HEADER_SEGMENT_LENGTH = "100";
	/** The Constant ITEM_FLAGS. */
	public final static String ITEM_FLAGS = "000000";
	/** The Constant ITEM_NUMBER. */
	public final static String ITEM_NUMBER = "98889";
	/** The Constant LINEITEM_SEGMENT_LENGTH. */
	public final static String LINEITEM_SEGMENT_LENGTH = "84";
	/** The Constant LTRAN_SEGMENT_DELIMIITER. */
	public final static Integer LTRAN_SEGMENT_DELIMIITER = 0xFF;
	/** The Constant MISC_ACCOUNT_NUMBER. */
	public final static String MISC_ACCOUNT_NUMBER = "189598";
	/** The Constant PAYMENT_METHOD_DATE_CODE. */
	public final static String PAYMENT_METHOD_DATE_CODE = " ";
	/** The Constant PAYMENT_STATUS_CODE. */
	public final static String PAYMENT_STATUS_CODE = "2";
	/** The Constant PAYMENTTYPE_SEGMENT_LENGTH. */
	public final static String PAYMENTTYPE_SEGMENT_LENGTH = "50";
	/** The Constant PLU_AMOUNT_TYPE_CODE. */
	public final static String PLU_AMOUNT_TYPE_CODE = "1";
	/** The Constant PURCHASING_ADDRESS_ID. */
	public final static String PURCHASING_ADDRESS_ID = "00000000000";
	/** The Constant PURCHASING_CUSTOMER_ID. */
	public final static String PURCHASING_CUSTOMER_ID = "00000000000";
	/** The Constant QUANTITY. */
	public final static String QUANTITY = "0001";
	/** The Constant REASON_CODE. */
	public final static String REASON_CODE = "00";
	/** The Constant RINGING_ASSOCIATE_ID. */
	public final static String RINGING_ASSOCIATE_ID = "000018";
	/** The Constant SELLING_ASSOCIATE. */
	public final static String SELLING_ASSOCIATE = "000018";
	/** The Constant SKU_NUMBER. */
	public final static String SKU_NUMBER = "000";
	/** The Constant STATUS_CODE. */
	public final static String STATUS_CODE = "1";
	/** The Constant STATUS_REASON_CODE. */
	public final static String STATUS_REASON_CODE = " ";
	/** The Constant TAX_AMOUNT. */
	public final static String TAX_AMOUNT = "0000000 ";
	/** The Constant TAX_CODE. */
	public final static String TAX_CODE = "1";
	/** The Constant TRANS_FILE_ARCHIVE_DIRECTORY. */
	public final static String TRANS_FILE_ARCHIVE_DIRECTORY = "trans_file_archive_directory";
	/** The Constant TRANS_FILE_DIRECTORY. */
	public final static String TRANS_FILE_DIRECTORY = "trans_file_directory";
	/** The Constant TRANS_FILE_ERROR_DIRECTORY. */
	public final static String TRANS_FILE_ERROR_DIRECTORY = "trans_file_error_directory";
	/** The Constant TRANSACTION_ERROR_CODE. */
	public final static String TRANSACTION_ERROR_CODE = "00";
	/** The Constant TRANSACTION_FLAGS. */
	public final static String TRANSACTION_FLAGS = "00";
	/** The Constant TRANSACTION_REASON_CODE. */
	public final static String TRANSACTION_REASON_CODE = " ";
	/** The Constant TRANSACTION_SOURCE_CODE. */
	public final static String TRANSACTION_SOURCE_CODE = "5";
	/** The Constant TRANSACTION_STATUS_CODE. */
	public final static String TRANSACTION_STATUS_CODE = "1";
	/** The Constant TRANSACTION_TAX_CODE. */
	public final static String TRANSACTION_TAX_CODE = "6";
	/** The Constant TRANSACTION_TOTAL_DISCOUNT. */
	public final static String TRANSACTION_TOTAL_DISCOUNT = "0000000 ";
	/** The Constant TRANSACTION_TOTAL_TAX. */
	public final static String TRANSACTION_TOTAL_TAX = "0000000 ";
	/** The Constant TRANSACTION_TYPE_CODE. */
	public final static String TRANSACTION_TYPE_CODE = "1";
	/** The Constant TYPE_CODE_1. */
	public final static String TYPE_CODE_1 = "3";
	/** The Constant TYPE_CODE_2_CANCELLATION. */
	public final static String TYPE_CODE_2_CANCELLATION = "3"; // currently not in use...
	/** The Constant TYPE_CODE_2_RETURN. */
	public final static String TYPE_CODE_2_RETURN = "2";
	/** The Constant TYPE_CODE_2_SALE. */
	public final static String TYPE_CODE_2_SALE = "1";
	/* These values are the same as found in lu_iso_sl_message_template table */
	/** ACTIVATION_RELOAD_REQUEST. */
	public static final String ACTIVATION_RELOAD_REQUEST = "ACTIVATION_RELOAD_REQUEST";
	/** ACTIVATION_RELOAD_RESPONSE. */
	public static final String ACTIVATION_RELOAD_RESPONSE = "ACTIVATION_RELOAD_RESPONSE";
	/** BALANCE_ADJUSTMENT_REDEEM_REQUEST. */
	public static final String BALANCE_ADJUSTMENT_REDEEM_REQUEST = "BALANCE_ADJUSTMENT_REDEEM_REQUEST";
	/** BALANCE_ADJUSTMENT_REDEEM_RESPONSE. */
	public static final String BALANCE_ADJUSTMENT_REDEEM_RESPONSE = "BALANCE_ADJUSTMENT_REDEEM_RESPONSE";
	/** BALANCE_ADJUSTMENT_RELOAD_REQUEST. */
	public static final String BALANCE_ADJUSTMENT_RELOAD_REQUEST = "BALANCE_ADJUSTMENT_RELOAD_REQUEST";
	/** BALANCE_ADJUSTMENT_RELOAD_RESPONSE. */
	public static final String BALANCE_ADJUSTMENT_RELOAD_RESPONSE = "BALANCE_ADJUSTMENT_RELOAD_RESPONSE";
	/** BALANCE_ENQUIRY_REQUEST. */
	public static final String BALANCE_ENQUIRY_REQUEST = "BALANCE_ENQUIRY_REQUEST";
	/** BALANCE_ENQUIRY_RESPONSE. */
	public static final String BALANCE_ENQUIRY_RESPONSE = "BALANCE_ENQUIRY_RESPONSE";
	/** BALANCE_ENQUIRY_STAN. */
	public static final String BALANCE_ENQUIRY_STAN = "BAL_ENQ";
	/** DATA_TYPE_ALPHA. */
	public static final String DATA_TYPE_ALPHA = "A";
	/** DATA_TYPE_ALPHANUMERIC. */
	public static final String DATA_TYPE_ALPHANUMERIC = "AN";
	/** DATA_TYPE_ALPHANUMERIC_SPACEPAD. */
	public static final String DATA_TYPE_ALPHANUMERIC_SPACEPAD = "ANP";
	/** DATA_TYPE_ALPHANUMERIC_SPECIALCHARS. */
	public static final String DATA_TYPE_ALPHANUMERIC_SPECIALCHARS = "ANS";
	/** DATA_TYPE_NUMERIC. */
	public static final String DATA_TYPE_NUMERIC = "N";
	/** DATA_TYPE_NUMERIC_SPECIALCHARS. */
	public static final String DATA_TYPE_NUMERIC_SPECIALCHARS = "NS";
	/** EMAIL_TEMPLATE_VL_RESPONSE_FAILURE. */
	public static final int EMAIL_TEMPLATE_VL_RESPONSE_FAILURE = 99;
	/** FAILURE. */
	public static final int FAILURE = 0;
	/** FORMAT_TYPE_FIXED. */
	public static final String FORMAT_TYPE_FIXED = "FIXED";
	/** FORMAT_TYPE_LLLVAR. */
	public static final String FORMAT_TYPE_LLLVAR = "LLLVAR";
	/** FORMAT_TYPE_LLVAR. */
	public static final String FORMAT_TYPE_LLVAR = "LLVAR";
	/** IDENTIFIER_FULLFILLMENT_STAN_MAX. */
	public static final long IDENTIFIER_FULLFILLMENT_STAN_MAX = 999999;
	/** LEDGER_ENTRY_ID. */
	public static final String LEDGER_ENTRY_ID = "LEDGER_ENTRY_ID";
	/** LEDGER_TRANS_ID. */
	public static final String LEDGER_TRANS_ID = "LEDGER_TRANS_ID";
	/** LEDGER_TRANS_ID_ADD_VALUE. */
	public static final long LEDGER_TRANS_ID_ADD_VALUE = 100000000;
	/** MESSAGE_DESC_ID_ACTIVATION_RELOAD. */
	public static final long MESSAGE_DESC_ID_ACTIVATION_RELOAD = 1;
	/** MESSAGE_DESC_ID_BALANCE_CREDIT. */
	public static final long MESSAGE_DESC_ID_BALANCE_CREDIT = 10;
	/** MESSAGE_DESC_ID_BALANCE_DEBIT. */
	public static final long MESSAGE_DESC_ID_BALANCE_DEBIT = 12;
	/** MESSAGE_DESC_ID_BALANCE_ENQ. */
	public static final long MESSAGE_DESC_ID_BALANCE_ENQ = 5;
	/** MESSAGE_DESC_ID_HEARTBEAT. */
	public static final long MESSAGE_DESC_ID_HEARTBEAT = 7;
	/** MESSAGE_DESC_ID_REDEMPTION. */
	public static final long MESSAGE_DESC_ID_REDEMPTION = 3;
	/** MESSAGE_DESC_ID_VOID. */
	public static final long MESSAGE_DESC_ID_VOID = 14;
	/** MESSAGE_TYPE_ACTIVATION. */
	public static final int MESSAGE_TYPE_ACTIVATION = 1;
	/** MESSAGE_TYPE_BALANCE_ENQ. */
	public static final int MESSAGE_TYPE_BALANCE_ENQ = 4;
	/** MESSAGE_TYPE_HEARTBEAT. */
	public static final int MESSAGE_TYPE_HEARTBEAT = 5;
	/** MESSAGE_TYPE_REDEMPTION. */
	public static final int MESSAGE_TYPE_REDEMPTION = 3;
	/** MESSAGE_TYPE_RELOAD. */
	public static final int MESSAGE_TYPE_RELOAD = 2;
	/** MESSAGE_TYPE_VOID. */
	public static final int MESSAGE_TYPE_VOID = 6;
	// ######################## VL PROMO CODES ########################
	/** NEW_BUYER_ACTIVATE_V1_PROMO_CODE_ID. */
	public final static int NEW_BUYER_ACTIVATE_V1_PROMO_CODE_ID = 1;
	/** NEW_BUYER_ACTIVATE_V2_PROMO_CODE_ID. */
	public final static int NEW_BUYER_ACTIVATE_V2_PROMO_CODE_ID = 2;
	/** NEW_PROVIDER_ACTIVATE_V1_PROMO_CODE_ID. */
	public final static int NEW_PROVIDER_ACTIVATE_V1_PROMO_CODE_ID = 3;
	/** REDEMPTION_REQUEST. */
	public static final String REDEMPTION_REQUEST = "REDEMPTION_REQUEST";
	/** REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID. */
	public static final String REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID = "REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID";
	/** REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID_RESPONSE. */
	public static final String REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID_RESPONSE = "REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID_RESPONSE";
	/** REDEMPTION_RESPONSE. */
	public static final String REDEMPTION_RESPONSE = "REDEMPTION_RESPONSE";
	/** REDEMPTION_RESPONSE_PARTIAL. */
	public static final String REDEMPTION_RESPONSE_PARTIAL = "REDEMPTION_RESPONSE_PARTIAL";
	public static final String REJECTION_RESPONSE = "REJECTION_RESPONSE";
	/** RESPONSE_ACTION_CODE_AUTHORIZER_UNAVAILABLE. */
	public static final String RESPONSE_ACTION_CODE_AUTHORIZER_UNAVAILABLE = "912";
	/** RESPONSE_ACTION_CODE_CANNOT_PARSE_MESSAGE. */
	public static final String RESPONSE_ACTION_CODE_CANNOT_PARSE_MESSAGE = "680";
	/** RESPONSE_ACTION_CODE_CARD_NOT_EFFECTIVE. */
	public static final String RESPONSE_ACTION_CODE_CARD_NOT_EFFECTIVE = "125";
	/** RESPONSE_ACTION_CODE_DECLINE. */
	public static final String RESPONSE_ACTION_CODE_DECLINE = "100";
	/** RESPONSE_ACTION_CODE_EXTERNAL_FORMAT_ERROR. */
	public static final String RESPONSE_ACTION_CODE_EXTERNAL_FORMAT_ERROR = "904";
	public static final String RESPONSE_ACTION_CODE_FULL_APPROVAL = "000";
	/** RESPONSE_ACTION_CODE_INTERNAL_FORMAT_ERROR. */
	public static final String RESPONSE_ACTION_CODE_INTERNAL_FORMAT_ERROR = "999";
	/** RESPONSE_ACTION_CODE_PARTIAL_APPROVAL. */
	public static final String RESPONSE_ACTION_CODE_PARTIAL_APPROVAL = "002";
	/** RESPONSE_ACTION_CODE_SUSPECT. */
	public static final String RESPONSE_ACTION_CODE_SUSPECT = "210";
	/** RESPONSE_ACTION_CODE_UNMAPPED. */
	public static final long RESPONSE_ACTION_CODE_UNMAPPED = 22;
	/** RESPONSE_ACTION_CODE_VERIFYCARD. */
	public static final String RESPONSE_ACTION_CODE_VERIFYCARD = "102";
	/** RULE_ID_ACH_ACTIVATE_RELOAD_BUYER_V1. */
	public final static int RULE_ID_ACH_ACTIVATE_RELOAD_BUYER_V1 = 4900;
	/** RULE_ID_ADMIN_AMEX_REFUND_REDEEM_BUYER_V1. */
	public final static int RULE_ID_ADMIN_AMEX_REFUND_REDEEM_BUYER_V1 = 4800;
	/** RULE_ID_ADMIN_VMC_REFUND_REDEEM_BUYER_V1. */
	public final static int RULE_ID_ADMIN_VMC_REFUND_REDEEM_BUYER_V1 = 4700;
	/** RULE_ID_BUYER_DEPOSIT_RELOAD_V1. */
	public final static int RULE_ID_BUYER_DEPOSIT_RELOAD_V1 = 3000;
	/** RULE_ID_BUYER_WITHDRAW_FUNDS_REDEEM_BUYER_V1. */
	public final static int RULE_ID_BUYER_WITHDRAW_FUNDS_REDEEM_BUYER_V1 = 3900;
	/** RULE_ID_CANCEL_ACH_SO_WO_PENALTY_RELOAD_BUYER_V1. */
	public final static int RULE_ID_CANCEL_ACH_SO_WO_PENALTY_RELOAD_BUYER_V1 = 3520;
	/** RULE_ID_CANCEL_SO_PENALTY_ACH_REDEEM_BUYER_V1. */
	public final static int RULE_ID_CANCEL_SO_PENALTY_ACH_REDEEM_BUYER_V1 = 3630;
	/** RULE_ID_CANCEL_SO_PENALTY_REDEEM_BUYER_V2. */
	public final static int RULE_ID_CANCEL_SO_PENALTY_REDEEM_BUYER_V2 = 3600;
	/** RULE_ID_CANCEL_SO_PENALTY_RELOAD_BUYER_V1. */
	public final static int RULE_ID_CANCEL_SO_PENALTY_RELOAD_BUYER_V1 = 3620;
	/** RULE_ID_CANCEL_SO_PENALTY_RELOAD_PROVIDER_V1. */
	public final static int RULE_ID_CANCEL_SO_PENALTY_RELOAD_PROVIDER_V1 = 3610;
	/** RULE_ID_CANCEL_SO_WO_PENALTY_REDEEM_BUYER_V2. */
	public final static int RULE_ID_CANCEL_SO_WO_PENALTY_REDEEM_BUYER_V2 = 3500;
	/** RULE_ID_CANCEL_SO_WO_PENALTY_RELOAD_BUYER_V1. */
	public final static int RULE_ID_CANCEL_SO_WO_PENALTY_RELOAD_BUYER_V1 = 3510;
	/** RULE_ID_DECREASE_ESCROW_ACH_REDEEM_BUYER_V1. */
	public final static int RULE_ID_DECREASE_ESCROW_ACH_REDEEM_BUYER_V1 = 3320;
	/** RULE_ID_DECREASE_ESCROW_REDEEM_BUYER_V2. */
	public final static int RULE_ID_DECREASE_ESCROW_REDEEM_BUYER_V2 = 3300;
	/** RULE_ID_DECREASE_ESCROW_RELOAD_BUYER_V1. */
	public final static int RULE_ID_DECREASE_ESCROW_RELOAD_BUYER_V1 = 3310;
	/** RULE_ID_INCREASE_ESCROW_ACH_RELOAD_BUYER_V1. */
	public final static int RULE_ID_INCREASE_ESCROW_ACH_RELOAD_BUYER_V1 = 3220;
	/** RULE_ID_INCREASE_ESCROW_REDEEM_BUYER_V1. */
	public final static int RULE_ID_INCREASE_ESCROW_REDEEM_BUYER_V1 = 3200;
	/** RULE_ID_INCREASE_ESCROW_RELOAD_BUYER_V2. */
	public final static int RULE_ID_INCREASE_ESCROW_RELOAD_BUYER_V2 = 3210;
	/** RULE_ID_NEW_BUYER_ACTIVATE_V1. */
	public final static int RULE_ID_NEW_BUYER_ACTIVATE_V1 = 2200;
	/** RULE_ID_NEW_BUYER_ACTIVATE_V2. */
	public final static int RULE_ID_NEW_BUYER_ACTIVATE_V2 = 2210;
	// ######################## FULLFILLMENT RULE IDs ########################
	/** RULE_ID_NEW_PROVIDER_ACTIVATE_V1. */
	public final static int RULE_ID_NEW_PROVIDER_ACTIVATE_V1 = 2100;
	/** RULE_ID_POST_SO_ACH_RELOAD_BUYER_V1. */
	public final static int RULE_ID_POST_SO_ACH_RELOAD_BUYER_V1 = 3130;
	/** RULE_ID_POST_SO_REDEEM_BUYER_V1. */
	public final static int RULE_ID_POST_SO_REDEEM_BUYER_V1 = 3100;
	/** RULE_ID_POST_SO_RELOAD_BUYER_V2. */
	public final static int RULE_ID_POST_SO_RELOAD_BUYER_V2 = 3120;
	/** RULE_ID_POST_SO_RELOAD_SL1. */
	public final static int RULE_ID_POST_SO_RELOAD_SL1 = 3110;
	/** RULE_ID_PROVIDER_WITHDRAW_FUNDS_REDEEM_PROVIDER_V1. */
	public final static int RULE_ID_PROVIDER_WITHDRAW_FUNDS_REDEEM_PROVIDER_V1 = 3800;
	/** RULE_ID_PROVIDER_WITHDRAW_REVERSAL_RELOAD_PROVIDER_V1. */
	public final static int RULE_ID_PROVIDER_WITHDRAW_REVERSAL_RELOAD_PROVIDER_V1 = 4000;
	/** RULE_ID_RELEASE_SO_PAYMENT_ACH_REDEEM_BUYER_V1. */
	public final static int RULE_ID_RELEASE_SO_PAYMENT_ACH_REDEEM_BUYER_V1 = 3740;
	/** RULE_ID_RELEASE_SO_PAYMENT_REDEEM_BUYER_V2. */
	public final static int RULE_ID_RELEASE_SO_PAYMENT_REDEEM_BUYER_V2 = 3700;
	/** RULE_ID_RELEASE_SO_PAYMENT_RELOAD_BUYER_V1. */
	public final static int RULE_ID_RELEASE_SO_PAYMENT_RELOAD_BUYER_V1 = 3730;
	/** RULE_ID_RELEASE_SO_PAYMENT_RELOAD_PROVIDER_V1. */
	public final static int RULE_ID_RELEASE_SO_PAYMENT_RELOAD_PROVIDER_V1 = 3720;
	/** RULE_ID_RELEASE_SO_PAYMENT_RELOAD_SL1. */
	public final static int RULE_ID_RELEASE_SO_PAYMENT_RELOAD_SL1 = 3710;
	/** RULE_ID_SLA_DEPOSIT_RELOAD_SL3. */
	public final static int RULE_ID_SLA_DEPOSIT_RELOAD_SL3 = 4200;
	/** RULE_ID_SLA_WITHDRAW_REDEEM_SL3. */
	public final static int RULE_ID_SLA_WITHDRAW_REDEEM_SL3 = 4210;
	/** RULE_ID_TRANSFER_SLB_FROM_BUYER_REDEEM_BUYER_V1. */
	public final static int RULE_ID_TRANSFER_SLB_FROM_BUYER_REDEEM_BUYER_V1 = 4500;
	/** RULE_ID_TRANSFER_SLB_FROM_BUYER_RELOAD_SL3. */
	public final static int RULE_ID_TRANSFER_SLB_FROM_BUYER_RELOAD_SL3 = 4510;
	/** RULE_ID_TRANSFER_SLB_FROM_PROVIDER_REDEEM_PROVIDER_V1. */
	public final static int RULE_ID_TRANSFER_SLB_FROM_PROVIDER_REDEEM_PROVIDER_V1 = 4600;
	/** RULE_ID_TRANSFER_SLB_FROM_PROVIDER_RELOAD_SL3. */
	public final static int RULE_ID_TRANSFER_SLB_FROM_PROVIDER_RELOAD_SL3 = 4610;
	/** RULE_ID_TRANSFER_SLB_TO_BUYER_REDEEM_SL3. */
	public final static int RULE_ID_TRANSFER_SLB_TO_BUYER_REDEEM_SL3 = 4300;
	/** RULE_ID_TRANSFER_SLB_TO_BUYER_RELOAD_BUYER_V1. */
	public final static int RULE_ID_TRANSFER_SLB_TO_BUYER_RELOAD_BUYER_V1 = 4310;
	/** RULE_ID_TRANSFER_SLB_TO_PROVIDER_REDEEM_SL3. */
	public final static int RULE_ID_TRANSFER_SLB_TO_PROVIDER_REDEEM_SL3 = 4400;
	/** RULE_ID_TRANSFER_SLB_TO_PROVIDER_RELOAD_PROVIDER_V1. */
	public final static int RULE_ID_TRANSFER_SLB_TO_PROVIDER_RELOAD_PROVIDER_V1 = 4410;
	/** RULE_ID_VOID_SO_ACH_REDEEM_BUYER_V1. */
	public final static int RULE_ID_VOID_SO_ACH_REDEEM_BUYER_V1 = 3420;
	/** RULE_ID_VOID_SO_REDEEM_BUYER_V2. */
	public final static int RULE_ID_VOID_SO_REDEEM_BUYER_V2 = 3400;
	/** RULE_ID_VOID_SO_RELOAD_BUYER_V1. */
	public final static int RULE_ID_VOID_SO_RELOAD_BUYER_V1 = 3410;
	/** RULE_ID_WITHDRAW_SL_REVENUE_REDEEM_SL1. */
	public final static int RULE_ID_WITHDRAW_SL_REVENUE_REDEEM_SL1 = 4100;
	/** RULE_ID_WITHDRAW_SL_REVENUE_REDEEM_SL2. */
	public final static int RULE_ID_WITHDRAW_SL_REVENUE_REDEEM_SL2 = 4110;
	/** SHARP_DUMMY_PAN_NUMBER. */
	public static final String SHARP_DUMMY_PAN_NUMBER = "9797979999999991";
	/** SHARP_HEARTBEAT_REQUEST. */
	public static final String SHARP_HEARTBEAT_REQUEST = "SHARP_HEARTBEAT_REQUEST";
	/** SHARP_HEARTBEAT_RESPONSE. */
	public static final String SHARP_HEARTBEAT_RESPONSE = "SHARP_HEARTBEAT_RESPONSE";
	/** SHARP_P63_ACCOUNT_NUMBER. */
	public static final String SHARP_P63_ACCOUNT_NUMBER = "AN";
	/** SHARP_P63_FULFILLMENT_GROUP. */
	public static final String SHARP_P63_FULFILLMENT_GROUP = "FG";
	/** SHARP_P63_LEDGER_ACCOUNT. */
	public static final String SHARP_P63_LEDGER_ACCOUNT = "LA";
	/** SHARP_P63_PROMO_CODE. */
	public static final String SHARP_P63_PROMO_CODE = "PC";
	/** SHARP_QUEUE. */
	public static final String SHARP_QUEUE = "sharpQueue";
	/** SHARP_REQUEST_MTI. */
	public static final String SHARP_REQUEST_MTI = "1200";
	/** SHARP_RESPONSE_MTI. */
	public static final String SHARP_RESPONSE_MTI = "1210";
	/** SHARP_SYSTEM. */
	public static final String SHARP_SYSTEM = "sharp";
	/** STAN_ID. */
	public static final String STAN_ID = "STAN_ID";
	/** STR_ADMIN. */
	public static final String STR_ADMIN = "admin";
	/** SUCCESS. */
	public static final int SUCCESS = 1;
	/** VALUELINK_QUEUE. */
	public static final String VALUELINK_QUEUE = "valueLinkQueue";
	/** VL_ACCOUNT_BUYER_V1. */
	public static final String VL_ACCOUNT_BUYER_V1 = "V1";
	/** VL_ACCOUNT_BUYER_V2. */
	public static final String VL_ACCOUNT_BUYER_V2 = "V2";
	/** VL_ACCOUNT_CC_FEE. */
	public final static String VL_ACCOUNT_CC_FEE = "SL5";
	// ######################## SL - VL Accounts ########################
	/** VL_ACCOUNT_POSTING_FEE. */
	public final static String VL_ACCOUNT_POSTING_FEE = "SL1";
	/** VL_ACCOUNT_PREFUNDING_ACCOUNT. */
	public final static String VL_ACCOUNT_PREFUNDING_ACCOUNT = "SL3";
	/** VL_ACCOUNT_PROVIDER_V1. */
	public static final String VL_ACCOUNT_PROVIDER_V1 = "V1";
	/** VL_ACCOUNT_SERVICE_FEE. */
	public final static String VL_ACCOUNT_SERVICE_FEE = "SL2";
	/** VL_ACCOUNT_SL_EXPENSES. */
	public final static String VL_ACCOUNT_SL_EXPENSES = "SL4";
	/** VL_SYSTEM. */
   
	public static final String VL_SYSTEM = "valuelink";
	
    public static final String NO_FULFILLMENT_ENTRY_FOUND = "There are no fulfillment entries that match your request(s).";
    public static final String DATABASE_ERROR_OCCURED="A database error has occurred in processing your request.";
    public static final String GENERIC_ERROR_OCCURED="An Exception has occurred in processing you adjustment";
    public static final String NUMERIC_ENTRIES_ALLOWED="Only numeric id(s) can be searched for.";
    public static final String ENTRY_TYPE_MISSING="The selected fulfillment record has no entry_type_id";
    public static final String RESENT_SUCCESS_MESSAGE = "The following Fulfillment Group(s) have been resent";
    public static final String NONRECONCILED_ENTRIES_ERROR = "There are no fulfillment entries that are non reconciled for the Fulfillment Group(s) ";

    public static final String AOP_USER_EMAIL = "USER_EMAIL";
    public static final String AOP_TEMPLATE_ID = "TEMPLATE_ID";

	public static final String INCOMPLETE_INDICATOR = "1";
	public static final String FM_RELEASE_DATE = "finance_mgr_release_date";
	
	// ######################### PROVIDER WITHDRAWAL ERROR LOGGING #####################
	
	public static final Integer WITHDRAWAL_LIMIT_ERROR_MESSAGE = 1;
	public static final Integer WITHDRAWAL_COUNT_ERROR_MESSAGE = 2;
	public static final Integer WITHDRAWAL_VL_BALANCE_ERROR_MESSAGE = 3;
	
	/** TIMER_BEAN_SLEEP_INTERVAL. */
	public static final String TIMER_BEAN_SLEEP_INTERVAL = "timer_bean_sleep_interval";
	
	/** SMS SUBSCRIPTION. */
	public static final String SUB_REQUESTED = "We have received your request to add your mobile phone number to the TESTWEB";
	public static final String SUB_ADDED = "You are already signed up to the TESTWEB mobile subscription list";
	public static final String SUB_ERROR = "error";
	
	public static final String SMSALERT_PROVIDER_METHOD_ONE = "/api/subscription_campaigns/";
	public static final String SMSALERT_PROVIDER_METHOD_TWO = "/send_message_to_subscribers.xml";
	public static final String SMSALERT_PROVIDER_HEADER_NAME = "api_token";
	public static final String SMSALERT_PROVIDER_METHOD_SUBSCRIBE = "/subscriptions.xml";
	//for HSR
	public final static String RETAIL_PRICE = "RETAIL_PRICE";
	public final static String REIMBURSEMENT_RETAIL_PRICE = "REIMBURSEMENT_RETAIL_PRICE";
	public final static String PARTS_SLGROSSUP = "PARTS_SLGROSSUP";
	public final static String RETAIL_PRICE_SLGROSSUP = "RETAIL_PRICE_SLGROSSUP";
	
	//ledger rules for invoice parts for HSR
	public final static int RETAIL_PRICE_RULE_ID = 2137;
	public final static int REIMBURSEMENT_RETAIL_PRICE_RULE_ID = 2138;
	public final static int RETAIL_PRICE_SLGROSSUP_RULE_ID = 2140;
	public final static int RULE_REFUND_ESCROW = 1420;
	public final static int AUTO_ACH_REFUND = 1470;
	public final static int AUTO_ACH_GL_REFUND = 1480;
	public final static long PARTS_RETAIL_PRICE_RULE_ID = 1137;
	public final static String PROD_DATE = "prod_date_new_parts_rules_hsr";
	
	//SL-18789
	public static final String CC_ENCRYPTION_KEY = "CCENKEY";
    public static final String SECRET_KEY = "AES";
    public static final String SECRET_KEY_BYTES = "8859_1";
    public static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
    //SLT-2154
    public static final String SECRET_KEY_ALGORITHM_DUPLICATE ="secret_key_algorithm";
    
    //SL-20730 : Constants for Shopify GL
    public static final String SHOPIFY_BUYER = "9000_";
    public static final String SHOPIFY_BUYER_ID = "9000";
    public static final String TOKEN = ",";
    public static final String SEPERATOR = "_";
    public static final String TAX_REAL = "Real";
    public static final String TAX_PERSONAL = "Personal";
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String REAL_TAX = "realTax";
    public static final String PERSONAL_TAX = "personalTax";
    public static final String CUSTOMER_DEPOSIT = "customerDeposit";
    public static final String RULE_DEF_REV = "DefRev";
    public static final String RULE_CUS_DEP = "CusDep";
    public static final String RULE_TAX_REAL = "TaxReal";
    public static final String RULE_TAX_PERS = "TaxPers";
    public static final String RULE_RTAX_REAL = "RTaxReal";
    public static final String RULE_RTAX_PERS = "RTaxPers";
    public static final String RULE_MISC_REV = "MiscRev";
    public static final String RULE_INST_REV = "InstRev";
    public static final String FUNDING_TYPE = "40";
    public static final String CREATED = "Created";
    public static final String DELETED = "Deleted";
    public static final String VOIDED = "Voided";
    public static final String INCREASE = "Increase";
    public static final String DECREASE = "Decrease";    
    
    public static final String CANCELLED = "Cancelled";
    public static final String CLOSED = "Closed";
    public static final int PROCESS_SUCCESSFUL = 1;
    public static final int INITIATED_MANUALLY = 0;
    public static final String[] STATUS_LIST={CREATED,INCREASE,DECREASE,CANCELLED,CLOSED};
    public static final String[] CANCELLED_STATUS_LIST={DELETED,VOIDED};
    public static final String ERROR = "E";
    public static final String RULE_CRED_FEE= "CredFee";  // Added for SLT-4200 to include new rule for Credit card

	public static List<String> CREATED_LIST(){
		List<String> CREATED_LIST = new ArrayList<String>();
		CREATED_LIST.add("1121");
		return CREATED_LIST;
	}
	
	public static List<String> INCREASE_LIST(){
		List<String> INCREASE_LIST = new ArrayList<String>();
		INCREASE_LIST.add("1201");
		return INCREASE_LIST;
	}
	
	public static List<String> DECREASE_LIST(){
		List<String> DECREASE_LIST = new ArrayList<String>();
		DECREASE_LIST.add("1270");
		DECREASE_LIST.add("1470");
		return DECREASE_LIST;
	}	
	
	public static List<String> CANCELLED_LIST(){
		List<String> CANCELLED_LIST = new ArrayList<String>();
		CANCELLED_LIST.add("1305");
		return CANCELLED_LIST;
	}
	
	public static List<String> CLOSED_LIST(){
		List<String> CLOSED_LIST = new ArrayList<String>();
		CLOSED_LIST.add("1405");
		return CLOSED_LIST;
	}
    
	public static HashMap<String, List<String>> ledgerRuleMap(){
		HashMap<String, List<String>> ledgerRuleMap = new HashMap<String, List<String>>();
		ledgerRuleMap.put(CREATED, CREATED_LIST());
		ledgerRuleMap.put(INCREASE, INCREASE_LIST());
		ledgerRuleMap.put(DECREASE, DECREASE_LIST());
		ledgerRuleMap.put(CANCELLED, CANCELLED_LIST());
		ledgerRuleMap.put(CLOSED, CLOSED_LIST());
		return ledgerRuleMap;
	}    
    
    //PCI Vault Changes--START
    public static final String INETBASED = "I";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HSS_APPROVED ="APPROVED";
    public static final String HSS_RESPONSE_CODE ="00";
    public static final String HSS_RESPONSE_MESSAGE ="SUCCESS";
    public static final String HSS_AUTH_CD ="000";
    public static final String HSS_STORE_NUM ="000";
    public static final String NAMESPACE = "http://service.credit.som.hs.searshc.com/Request/CreditAuth/";
    public static final String RESPONSE_NAMESPACE = "http://base.hs.searshc.com/Response/";
    public static final String RESPONSE_NAMESPACE_END = "http://service.credit.som.hs.searshc.com/Response/CreditAuth/";
    public static final String CREDIT_CARD_HS_AUTH_URL = "webservices.hs.url";
    public static final String CREDIT_CARD_HS_AUTH_HEADER = "webservices.hs.header";
    public static final String CREDIT_CARD_HDR_END = ",userid:";
    public static final String  SUCCESS_CODE = "00";
    public static final String  ERROR_MESSAGE = "This transaction was not approved; please verify the card information you provided and try again or contact your credit card company";
    public static final String AUTHORIZATION = "Full Authorization";
    public static final String VALIDATION = "Validation";
    public static final Double TRANS_AMOUNT= 0.0;
    public static final String TRANS_AMOUNT_STRING= "0.0";
    public static final String TRANS_AMOUNT_CARD_ADD= "00";
    public static final char DECIMAL_POINT= '.';
    public static final String DECIMAL_POINTS= ".";
    public static final int API_TIME_OUT = 15000;
    
    public static final String HS_REFUND_NAMESPACE = "http://service.credit.som.hs.searshc.com/Request/CreditRefund/";
    public static final String CREDIT_CARD_HS_REFUND_URL = "webservices.hs.refund.url";
    public static final String CREDIT_CARD_HS_REFUND_HEADER = "webservices.hs.refund.header";
    
    public static final String HS_REFUND_RESPONSE_NAMESPACE_END = "http://service.credit.som.hs.searshc.com/Response/CreditResponse/";
    //SL-20853 Trans file changes
    public static final String TRANS_FILE_CHANGES="Trans File Changes";
    public static final String TRANS_FILE_EXISTS="true";
    //public static final String VERSION_NUMBER="06 00";
    public static final String CC_ENCRYPTION_ON="ON";
    public static final String CC_ENCRYPTION_FLAG="cc_encryption";
    public static final String ADDITIONAL_PAYMENT_ENCRYPTION_FLAG="additional_payment_encryption";
    public static final String SPECIAL_CHARACTER="*";

    public static final String HS_WEBSERVICE_FLAG_ON = "ON";
    public static final String HS_WEBSERVICE_APP_KEY = "HS_Tokenization_Flag";
    
    //AuditorWorkFlowChanges start action
    public static final String AUDITOR_WORK_FLOW_START_ACTION="AUDIT_CREDENTIAL";
    public static final String AUDIT_NEXT_CREDENTIAL_START_ACTION="AUDIT_NEXT_CREDENTIAL";
	public static final String LICENCE_CERTIFICATE_START_ACTION="EDIT_LICENCE_CERTIFICATION_FIRM";
	public static final String PROCESS_INSURANCE_START_ACTION="EDIT_INSURANCE";
	public static final String PROCESS_INSURANCE_ADDITIONAL_START_ACTION="EDIT_ADDITIONAL_INSURANCE";
	public static final String TEAM_CREDENTIAL_START_ACTION="EDIT_LICENCE_CERTIFICATION_PROVIDER";
	public static final String TEAM_MEMBER_ADD_CREDENTIAL="ADD_NEW_TEAM_CREDENTIAL";
	public static final String COMPANY_ADD_CREDENTIAL="ADD_NEW_COMPANY_CREDENTIAL";
	public static final String ADDITIONAL_INSURANCE_ADD_ACTION="ADD_ADDITIONAL_INSURANCE";
	public static final String COMPANY_NEW_INSURANCE_ADD_ACTION="ADD_NEW_INSURANCE";
	
	//end action
	//public static final String AUDITOR_WORK_FLOW_END_ACTION="AUDIT_NEXT_CREDENTIAL";
	//public static final String LICENCE_CERTIFICATE_END_ACTION="UPDATE_CREDENTIAL";
	//public static final String PROCESS_INSURANCE_END_ACTION="SAVE_INSURANCE_POLICY";
	//public static final String PROCESS_INSURANCE_ADDITIONAL_END_ACTION="UPDATE_ADDITIONAL_INSURANCE_POLICY";
	public static final String TEAM_CREDENTIAL_END_ACTION="UPDATE_CREDENTIAL_PROVIDER";
	public static final String AUDITOR_WORK_FLOW_NEXT_CREDENTIAL_BUTTON_END_ACTION="AUDIT_NEXT_CREDENTIAL";
	public static final String AUDITOR_WORK_FLOW_CLOSE_AUDIT_BUTTON_END_ACTION="CLOSE_AUDIT_WINDOW";
	public static final String COMPANY_CREDENTIAL_END_ACTION="UPDATE_CREDENTIAL_FIRM";
	public static final String COMPANY_INSURANCE_END_ACTION="UPDATE_INSURANCE_POLICY_FROM_AUDITOR";
	public static final String UPDATE_CREDENTIAL_STATUS_END_ACTION="UPDATE_CREDENTIAL_STATUS";
	public static final String COMPANY_INSURANCE_SAVE_END_ACTION="SAVE_INSURANCE_POLICY";
	public static final String COMPANY_UPDATE_ADDITIONAL_INSURANCE_END_ACTION="SAVE_ADDITIONAL_INSURANCE_POLICY";
	public static final String INSURANCE_CLOSE_END_ACTION="CLOSE_INSURANCE_EDIT";
	public static final String TEAM_MEMBER_SAVE_CREDENTIAL="SAVE_NEW_TEAM_CREDENTIAL";
	public static final String COMPANY_SAVE_CREDENTIAL="SAVE_NEW_COMPANY_CREDENTIAL";
	public static final String ADDITIONAL_INSURANCE_SAVE_ACTION="SAVE_NEW_ADDITIONAL_INSURANCE";
	public static final String COMPANY_INSURANCE_SAVE_ACTION="SAVE_NEW_COMPANY_INSURANCE";
	
	//SL-20987 -Wallet Dirty Read
	public static final String WALLET_WITHDRAW_SWITCH="walletWithdrawSwitch";
	public static final String WALLET_SWITCH_ON="ON";
	public static final String INTERNAL_SERVER_ERROR="Internal Server Error";
	
	//SL-21458 - Change Webhook event data
	public static final String PROVIDER_REASSIGN_API_EVENT= "order_reassigned_to_new_provider";
	
	//SL-21470 - 
	public static final String[] CUSTOM_GL_BUYERS={"9000","3333","7777"};
	public static final String RELAY_BUYER_ID = "3333";
	public static final String TECH_TALK_BUYER_ID = "7777";
    
	
}
