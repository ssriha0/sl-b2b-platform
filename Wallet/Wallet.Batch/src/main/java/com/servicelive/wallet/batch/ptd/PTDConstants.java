package com.servicelive.wallet.batch.ptd;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDConstants.
 */
public class PTDConstants {

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
}
