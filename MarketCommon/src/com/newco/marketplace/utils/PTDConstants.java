package com.newco.marketplace.utils;

public class PTDConstants {
	public static final String PTD_HEADER = "PTD_HEADER";
	public static final String PTD_TRANSACTION = "PTD_TRANSACTION";
	public static final String PTD_TRAILER = "PTD_TRAILER";
	public static final String PTD_NON_RECONCILED_TRANS_MSG = "Following are the fullfillment entry ids as received from Value Link\n";
	public static final String PTD_PROCESS_FAILURE_SUBJECT =  "PTD File Process failed with Exception. ";
	public static final String PTD_PROCESS_SUCCESS_SUBJECT =  "PTD File Processed successfully - no errors.";
	public static final String PTD_PROCESS_SUCCESS_UNREC_SUBJECT =  "PTD File Processed successfully with Unreconciled records.";
	public static final String PTD_FILE_NOT_FOUND = "PTD File not found in the directory, ";
	public static final String PTD_PROCESS_NON_RECONCILED_TRANSACTIONS =  "PTD File process - A few non-reconciled transactions ";
	public static final String PTD_PROCESS_STATUS =  "10";
	public static final int PTD_RECONCILATION_STATUS_MARKED =  10;
	public static final int PTD_RECONCILATION_STATUS_SUCCESS =  10;
	public static final String PTD_EMAIL_BAD_TRANSACTIONS = "Bad Transactions that were PTD Reconciled";
	
	public static final String PTD_NON_RECONCILED_HEADER_MSG = "The following transactions weren't reconciled. The process couldn't find a matching transaction in SL Database.\n"+
	" Following are the fullfillment entry ids as received from Value Link\n";
	
	public static final int EC_IGNORE = 0;
	public static final int EC_NO_ERROR = 1;
	public static final int EC_AMOUNT_MISMATCH = 2;
	public static final int EC_SIGN_MISMATCH = 3;
	public static final int EC_BAD_RESPONSE = 4;
	public static final int EC_NO_RESPONSE = 5;
	public static final int EC_PTD_ENTRY_NOT_FOUND = 6;
	public static final int EC_VLBC_ACTIVITY = 7;
	public static final int EC_SLDB_ENTRY_NOT_FOUND = 8;
	public static final int ACTIVATION = 1;
	public static final int REDEMPTION = 3;
	public static final int RELOAD = 2;
	public static final int PTD_RELOAD = 300;
	public static final int PTD_ACTIVATION = 2102;
	public static final int PTD_REDEMPTION = 200;
	public static final int PTD_REDEMPTION_NO_NSF = 202;
	public static final int PTD_CLGC_BALANCE_INQUIRY = 6405;
	public static final int PTD_CLGC_TRANS_HISTORY = 6415;
	public static final int PTD_STORE_BALANCE_INQUIRY = 450;
	public static final int ADMIN_TOOL_RELOAD = 460;
	public static final int BALANCE_INQUIRY = 400;
	public static final int TIME_OUT_REVERSAL_IGNORE = 704;
	public static final int ADMIN_TOOL_REDEEM = 800;
}
