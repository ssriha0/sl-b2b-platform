package com.servicelive.wallet.ledger;

// TODO: Auto-generated Javadoc
/**
 * Interface LedgerConstants.
 */
public interface LedgerConstants {

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

	// ######################## Miscellaneous ########################

	/** CREDIT_CARD_SEARS. */
	public final static long CREDIT_CARD_SEARS = 4;
	
	/** CREDIT_CARD_AMEX. */

	public final static long CREDIT_CARD_AMEX = 8;

	/** CREDIT_CARD_MC. */
	public final static long CREDIT_CARD_MC = 7;

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

	/** RULE_ID_DEPOSIT_CASH_CC_V_AMEX. */
	public final static long RULE_ID_DEPOSIT_CASH_CC_V_AMEX = 1002;

	/** RULE_ID_DEPOSIT_CASH_CC_V_MC. */
	public final static long RULE_ID_DEPOSIT_CASH_CC_V_MC = 1001;
	
	/** RULE_ID_DEPOSIT_CASH_CC_SEARS. */
	public final static long RULE_ID_DEPOSIT_CASH_CC_SEARS = 1003;

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

}
