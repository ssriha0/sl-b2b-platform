package com.newco.marketplace.interfaces;

public interface LedgerConstants {

	// ########################Entity Type Id########################


	public final static int LEDGER_ENTITY_TYPE_BUYER = 10;

	public final static int LEDGER_ENTITY_TYPE_PROVIDER = 20;

	public final static int LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW = 30;

	public final static int LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN = 40;

	public final static int LEDGER_ENTITY_TYPE_MANAGE_SERVICES = 50;

	public final static int LEDGER_ENTITY_TYPE_FIRSTDATA = 60;
	
	public final static int LEDGER_ENTITY_TYPE_DEPOSITS_WITHDRAWLS = 70;
	
	public final static int LEDGER_ENTITY_TYPE_VIRTUAL_CASH = 80;
	
	public final static int LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION = 90;

	// ########################Financial Type Id########################
	public final static int ENTITY_ID_SERVICELIVE = 1;

	public final static int ENTITY_ID_ESCROW = 2;
	
	public final static int ENTITY_ID_MANAGED_SERVICES = 3;

	public final static int ENTITY_ID_DEPOSIT_WITHDRAWL = 7;
	
	public final static int ENTITY_ID_VIRTUAL_CASH = 8;
	
	public final static int ENTITY_ID_SERVICELIVE_OPERATION = 9;
	
	public final static int CREDIT_CARD_VISA = 6;
	
	public final static int CREDIT_CARD_MC = 7;
	
	public final static int CREDIT_CARD_AMEX = 8;

	// ########################NEW ENTRY TYPE ID ########################
	public final static int ENTRY_TYPE_DEBIT = 1;

	public final static int ENTRY_TYPE_CREDIT = 2;

	// ########################Transaction Type Id########################

	public final static int TRANSACTION_TYPE_ID_DEPOSIT_CASH = 100;

	public final static int TRANSACTION_TYPE_ID_POSTING_FEE = 200; // aka
	
	public final static int TRANSACTION_TYPE_ID_SERVICE_FEE = 300;

	public final static int TRANSACTION_TYPE_ID_ORDER_ESCROW = 400;

	public final static int TRANSACTION_TYPE_ID_ORDER_PAYMENT = 500;

	public final static int TRANSACTION_TYPE_ID_CANCELLED_ORDER = 600;

	public final static int TRANSACTION_TYPE_ID_CANCELLED_PENALTY = 700;
	
	public final static int TRANSACTION_TYPE_ID_VOIDED_ORDER = 750;

	public final static int TRANSACTION_TYPE_ID_WITHDRAW_CASH = 800;

	public final static int TRANSACTION_TYPE_ID_ACTUAL_RETAIL = 900;
	
	public final static int TRANSACTION_TYPE_ID_CREDIT_DEPOSIT = 1200;
	
	public final static int TRANSACTION_TYPE_ID_TRANSFER_FUNDS = 1300;
	
	public final static int TRANSACTION_TYPE_ID_CREDIT_WITHDRAWAL = 1400;
	
	public final static int TRANSACTION_TYPE_ID_INSTANT_ACH_DEPOSIT = 1900;
	
	public final static int TRANSACTION_TYPE_ID_INSTANT_ACH_REFUND = 1901;

	// ######################## Miscellaneous ########################

	public static final int GL_PROCESSED_TRUE = 1;

	public static final int GL_PROCESSED_FALSE = 0;

	public final static int CC_ACCOUNT_TYPE = 30;
	
	public final static String STATE_IL = "IL";
	
	public final static String USER_TYPE_BUYER = "Buyer";
	
	public final static String USER_TYPE_PROVIDER = "Provider";
	
	public final static String USER_TYPE_SIMPLE_BUYER = "SimpleBuyer";
	
	// ################## NEW T_ACCOUNT_NO complete ###################
	public final static int T_ACCOUNT_NO_CASH = 10258;

	public final static int T_ACCOUNT_NO_ACCOUNTS_RECEIVABLE = 11300;

	public final static int T_ACCOUNT_NO_CUSTOMER_DEPOSITS = 21150;

	public final static int T_ACCOUNT_NO_SERVICE_FEE = 40501;

	public final static int T_ACCOUNT_NO_ACCESS_FEE = 40500;

	public final static int T_ACCOUNT_NO_INSTALLATION_SALES = 40650;

	public final static int T_ACCOUNT_NO_PAYMENT_TO_CONTRACTORS = 50650;
	
	public final static int T_ACCOUNT_NO_AMEX_RECEIVABLE = 11525;
	
	public final static int T_ACCOUNT_NO_V_MC_RECEIVABLE = 11531;
	

	// ######################## TRANSACTION_ID ########################
	public final static int BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER = 10;
	
	public final static int BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER = 20;

	public final static int BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER = 30;
	
	public final static int BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL = 40;
	
	public final static int BUSINESS_TRANSACTION_DEPOSIT_FUNDS_BUYER_REVERSAL = 50;

	public final static int BUSINESS_TRANSACTION_POST_SO = 100;

	public final static int BUSINESS_TRANSACTION_CANCEL_SO = 110;
	
	public final static int BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY = 111;
	
	public final static int BUSINESS_TRANSACTION_VOID_SO = 115;

	public final static int BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT = 120;

	public final static int BUSINESS_TRANSACTION_CLOSE_SO = 130;

	public final static int BUSINESS_TRANSACTION_MARKETPLACE_WITHDRAW_FUNDS = 140;

	public final static int BUSINESS_TRANSACTION_INCREASE_SO_ESCROW = 150;
	
	public final static int BUSINESS_TRANSACTION_DECREASE_SO_ESCROW = 160;
	
	public final static int BUSINESS_TRANSACTION_NEW_PROVIDER = 170;
	
	public final static int BUSINESS_TRANSACTION_NEW_BUYER = 180;
	
	public final static int BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS = 190;
	
	public final static int BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS = 200;
	
	public final static int BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_BUYER = 210;
	
	public final static int BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_PROVIDER = 220;
	
	public final static int BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_BUYER = 230;
	
	public final static int BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_PROVIDER = 240;
	
	public final static int BUSINESS_TRANSACTION_BUYER_REFUND = 250;
	
	public final static int BUSINESS_TRANSACTION_BUYER_DEPOISTS_FROM_V_MX = 260;
	
	public final static int BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_AMEX = 270;
	
	public final static int BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_V_MC = 280;
	
	public final static int BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_AMEX = 290;
	
	public final static int BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_DEPOSIT = 310;
	
	public final static int BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_REFUND = 320;
	
	// Escheatment Business Transaction Ids.
    public final static int BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_BUYER = 320;
	
	public final static int BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_PROVIDER = 330;
	 

	// ########################NEW Rule Id########################

	public final static int RULE_ID_DEPOSIT_CASH = 1000;

	public final static int RULE_ID_INSTANT_ACH_DEPOSIT = 1004;
	
	public final static int RULE_ID_VIRTUAL_CASH_DEPOSIT = 1010; //For Post SO

	public final static int RULE_ID_APPLY_POSTING_FEE = 1100;
	
	public final static int RULE_ID_POSTING_FEE_RECEIVABLE = 1101;
	
	public final static int RULE_ID_APPLY_POSTING_FEE_VIRTUAL = 1110;

	public final static int RULE_ID_RESERVE_SO_FUNDING = 1120;

	public final static int RULE_ID_VIRTUAL_CASH_DEPOSIT_INCREASE_SPEND_LIMIT = 1210;
	
	public final static int RULE_ID_INCREASE_SPEND_LIMIT = 1200;
	
	public final static int RULE_ID_VIRTUAL_REFUND_ESCROW_DECREASE_SPEND_LIMIT = 1250;
	
	public final static int RULE_ID_RELEASE_PENALITY_PAYMENT = 1300;

	public final static int RULE_ID_SHC_RELEASE_PENALITY_PAYMENT = 1301;

	public final static int RULE_ID_RETRUN_SO_FUNDING = 1310;

	public final static int RULE_ID_APPLY_PENALTY_TO_CONTRACTOR = 1330;

	public final static int RULE_ID_ACTUAL_PENALITY = 1340;

	public final static int RULE_ID_REFUND_ESCROW_CANCEL_SO = 1350;
	
	public final static int RULE_ID_VIRTUAL_CASH_RETURN_CANCEL_SO = 1360;
	
	public final static int RULE_ID_REFUND_ESCROW_VOID_SO = 1370;
	
	public final static int RULE_ID_VIRTUAL_CASH_RETURN_VOID_SO = 1380;
	
	public final static int RULE_ID_REFUND_ESCROW_CANCEL_SO_WO_PENALTY = 1390;
	
	public final static int RULE_ID_VIRTUAL_CASH_CANCEL_SO_WO_PENALTY = 1395;
	
	public final static int RULE_ID_RELEASE_SO_PAYMENT = 1400;
	
	public final static int RULE_ID_PROVIDER_PAYMENT_PAYABLE = 1401;

	public final static int RULE_ID_SHC_RELEASE_SO_PAYMENT = 10005;

	public final static int RULE_ID_SL_PROVIDER_PAYMENT_PAYABLE = 10006;
	
	public final static int RULE_ID_DECLARE_FINAL_PRICE = 1405;

	public final static int RULE_ID_APPLY_SERVICE_FEE = 1410;
	
	public final static int RULE_ID_SERVICE_FEE_RECEIVABLE = 1411;
	
	public final static int RULE_ID_TRANSFER_COMMISSION = 10002;

	public final static int RULE_ID_SL_RECEIVABLE_FOR_COMMISSION = 10003;

	public final static int RULE_ID_REFUND_ESCROW = 1420;

	public final static int RULE_ID_APPLY_SERVICE_FEE_VIRTUAL = 1430;

	public final static int RULE_ID_GL_DEBIT_PMT_FROM_CONTRACTOR_PMT = 1440;

	public final static int RULE_ID_CREDIT_RETAIL_COST_OF_SO = 1450;

	public final static int RULE_ID_VIRTUAL_CASH_RETURN = 1460;
	
	public final static int RULE_ID_WITHDRAWAL_CASH_BUYER = 2000;

	public final static int RULE_ID_WITHDRAWAL_CASH_PROVIDER = 5000;
	
	public final static int RULE_ID_WITHDRAWAL_CASH_PROVIDER_REVERSAL = 5010;
	
	public final static int RULE_ID_WITHDRAWAL_CASH_CC_V_MC = 2010;
	
	public final static int RULE_ID_WITHDRAWAL_CASH_CC_AMEX = 2020;
	
	public final static int RULE_ID_DEPOSIT_CASH_CC_V_MC = 1001;
	
	public final static int RULE_ID_DEPOSIT_CASH_CC_V_AMEX = 1002;
	
	public final static int RULE_ID_DEPOSIT_CASH_SLA_OPERATIONS = 5100;
	
	public final static int RULE_ID_WITHDRAWAL_CASH_SLA_OPERATIONS = 5110;
	

	// ########################Funding Type complete########################
	public final static int FUNDING_TYPE_NON_FUNDED = 10;

	public final static int FUNDING_TYPE_DIRECT_FUNDED = 20;

	public final static int FUNDING_TYPE_PRE_FUNDED = 30;
	public final static int SHC_FUNDING_TYPE = 40;
	public final static int ACH_FUNDING_TYPE_EXTERNAL_BUYER = 90;
	
	//Consolidated Balance ledger_entry_id
	public final static int CONSOLIDATED_BALANCE_LEDGER_ID = 0;
	
	public final static int RECONCILED_STATUS_UNPROCESSED = 10;
	public final static int RECONCILED_STATUS_SENT = 20;
	public final static int RECONCILED_STATUS_SUCCESS = 30;
	public final static int RECONCILED_STATUS_RECONCILED = 40;
	public final static int RECONCILED_STATUS_REJECTED = 50; 
	public final static int RECONCILED_STATUS_RETURNED = 60;
	
	public final static String HASHMAP_AMOUNT = "amount";
	public final static String HASHMAP_COUNT = "count";
	
	// ############# TRANSFER SLBUCKS REASON CODES ########################### 
	public final static int TRANSFER_REASONCODE_GOODWILL_CREDIT = 10;
	public final static int TRANSFER_REASONCODE_REFUND_CREDIT = 20;
	public final static int TRANSFER_REASONCODE_ADJUSTMENT_CREDIT = 30;
	public final static int TRANSFER_REASONCODE_ADJUSTMENT_DEBIT = 40;
	public final static int TRANSFER_REASONCODE_OTHER_CREDIT = 50;
	public final static int TRANSFER_REASONCODE_OTHER_DEBIT = 60;
	public final static int TRANSFER_REASONCODE_ESCHEATMENT = 70;
	public static final int TRANSFER_REASONCODE_IRS_LEVY = 90;
	public static final int TRANSFER_REASONCODE_LEGAL_HOLD = 100;
	
	// ###################### GL FEED ACCOUNT CONSTANTS ########################
	
	public final static String GL_ACCOUNTS_RETAIL_INSTALLATION_PREPAID_EXPENSES_ACCOUNT = "13165";
	public final static int GL_ACCOUNTS_RETAIL_INSTALLATION_CASH_ACCOUNT = 10302;
	public final static String GL_ACCOUNTS_RETAIL_INSTALLATION_SERVICE_COMMISSION_ACCOUNT = "50611";
	public final static String GL_ACCOUNTS_RETAIL_INSTALLATION_LOCATION = "54584";
	public final static String GL_ACCOUNTS_SHC_COMMISSION_ACCOUNT = "09930";
	public final static String SERVICE_FEE_TOTAL = "ServiceFeeTotal";
	public final static String RI_CONTRACTOR_EXPENSE_DESC = "Contractor Expense";
	public final static String RI_PREPAID_EXPENSE_DESC = "Prepaid Expense";
	public final static String RI_COMMISSION_DESC = "Commission";
	public final static String DEFAULT_DIVISION = "0400";
	public final static String DEFAULT_CATEGORY = "0100";
}
