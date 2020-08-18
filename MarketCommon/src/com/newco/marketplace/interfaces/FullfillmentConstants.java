/**
 * 
 */
package com.newco.marketplace.interfaces;

/**
 * @author schavda
 *
 */
public class FullfillmentConstants {
	
//	 ######################## FULLFILLMENT RULE IDs ########################
	public final static int RULE_ID_NEW_PROVIDER_ACTIVATE_V1 = 2100;
	
	public final static int RULE_ID_NEW_BUYER_ACTIVATE_V1 = 2200;
	
	public final static int RULE_ID_NEW_BUYER_ACTIVATE_V2 = 2210;
	
	public final static int RULE_ID_BUYER_DEPOSIT_RELOAD_V1 = 3000;
	
	public final static int RULE_ID_POST_SO_REDEEM_BUYER_V1 = 3100;
	
	public final static int RULE_ID_POST_SO_RELOAD_SL1 = 3110;
	
	public final static int RULE_ID_POST_SO_RELOAD_BUYER_V2 = 3120;
	
	public final static int RULE_ID_POST_SO_ACH_RELOAD_BUYER_V1 = 3130;
	
	public final static int RULE_ID_INCREASE_ESCROW_REDEEM_BUYER_V1 = 3200;
	
	public final static int RULE_ID_INCREASE_ESCROW_RELOAD_BUYER_V2 = 3210;
	
	public final static int RULE_ID_INCREASE_ESCROW_ACH_RELOAD_BUYER_V1 = 3220;
	
	public final static int RULE_ID_DECREASE_ESCROW_REDEEM_BUYER_V2 = 3300;
	
	public final static int RULE_ID_DECREASE_ESCROW_RELOAD_BUYER_V1 = 3310;
	
	public final static int RULE_ID_DECREASE_ESCROW_ACH_REDEEM_BUYER_V1 = 3320;
	
	public final static int RULE_ID_VOID_SO_REDEEM_BUYER_V2 = 3400;
	
	public final static int RULE_ID_VOID_SO_RELOAD_BUYER_V1 = 3410;
	
	public final static int RULE_ID_VOID_SO_ACH_REDEEM_BUYER_V1 = 3420;
	
	public final static int RULE_ID_CANCEL_SO_WO_PENALTY_REDEEM_BUYER_V2 = 3500;
	
	public final static int RULE_ID_CANCEL_SO_WO_PENALTY_RELOAD_BUYER_V1 = 3510;
	
	public final static int RULE_ID_CANCEL_ACH_SO_WO_PENALTY_RELOAD_BUYER_V1 = 3520;
	
	public final static int RULE_ID_CANCEL_SO_PENALTY_REDEEM_BUYER_V2 = 3600;
	
	public final static int RULE_ID_CANCEL_SO_PENALTY_RELOAD_PROVIDER_V1 = 3610;
	
	public final static int RULE_ID_CANCEL_SO_PENALTY_RELOAD_BUYER_V1 = 3620;
	
	public final static int RULE_ID_CANCEL_SO_PENALTY_ACH_REDEEM_BUYER_V1 = 3630;
	
	public final static int RULE_ID_RELEASE_SO_PAYMENT_REDEEM_BUYER_V2 = 3700;
	
	public final static int RULE_ID_RELEASE_SO_PAYMENT_RELOAD_SL1 = 3710;
	
	public final static int RULE_ID_RELEASE_SO_PAYMENT_RELOAD_PROVIDER_V1 = 3720;
	
	public final static int RULE_ID_RELEASE_SO_PAYMENT_RELOAD_BUYER_V1 = 3730;
	
	public final static int RULE_ID_RELEASE_SO_PAYMENT_ACH_REDEEM_BUYER_V1 = 3740;
	
	public final static int RULE_ID_PROVIDER_WITHDRAW_FUNDS_REDEEM_PROVIDER_V1 = 3800;
	
	public final static int RULE_ID_BUYER_WITHDRAW_FUNDS_REDEEM_BUYER_V1 = 3900;
	
	public final static int RULE_ID_PROVIDER_WITHDRAW_REVERSAL_RELOAD_PROVIDER_V1 = 4000;
	
	public final static int RULE_ID_WITHDRAW_SL_REVENUE_REDEEM_SL1 = 4100;
	
	public final static int RULE_ID_WITHDRAW_SL_REVENUE_REDEEM_SL2 = 4110;
	
	public final static int RULE_ID_SLA_DEPOSIT_RELOAD_SL3 = 4200;
	
	public final static int RULE_ID_SLA_WITHDRAW_REDEEM_SL3 = 4210;
	
	public final static int RULE_ID_TRANSFER_SLB_TO_BUYER_REDEEM_SL3 = 4300;
	
	public final static int RULE_ID_TRANSFER_SLB_TO_BUYER_RELOAD_BUYER_V1 = 4310;
	
	public final static int RULE_ID_TRANSFER_SLB_TO_PROVIDER_REDEEM_SL3 = 4400;
	
	public final static int RULE_ID_TRANSFER_SLB_TO_PROVIDER_RELOAD_PROVIDER_V1 = 4410;
	
	public final static int RULE_ID_TRANSFER_SLB_FROM_BUYER_REDEEM_BUYER_V1 = 4500;
	
	public final static int RULE_ID_TRANSFER_SLB_FROM_BUYER_RELOAD_SL3 = 4510;
	
	public final static int RULE_ID_TRANSFER_SLB_FROM_PROVIDER_REDEEM_PROVIDER_V1 = 4600;
	
	public final static int RULE_ID_TRANSFER_SLB_FROM_PROVIDER_RELOAD_SL3 = 4610;
	
	public final static int RULE_ID_ADMIN_VMC_REFUND_REDEEM_BUYER_V1 = 4700;
	
	public final static int RULE_ID_ADMIN_AMEX_REFUND_REDEEM_BUYER_V1 = 4800;
	
	public final static int RULE_ID_ACH_ACTIVATE_RELOAD_BUYER_V1 = 4900;		
	
	//######################## SL - VL Accounts ########################
	public final static String VL_ACCOUNT_POSTING_FEE = "SL1";
	public final static String VL_ACCOUNT_SERVICE_FEE = "SL2";
	public final static String VL_ACCOUNT_PREFUNDING_ACCOUNT = "SL3";
	public final static String VL_ACCOUNT_SL_EXPENSES = "SL4";
	public final static String VL_ACCOUNT_CC_FEE = "SL5";
	
	public static final String VL_ACCOUNT_BUYER_V1 = "V1";
	public static final String VL_ACCOUNT_BUYER_V2 = "V2";
	public static final String VL_ACCOUNT_PROVIDER_V1 = "V1";
	
	
	/*These values are the same as found in lu_iso_sl_message_template table*/
	public static final String ACTIVATION_RELOAD_REQUEST="ACTIVATION_RELOAD_REQUEST";
	public static final String ACTIVATION_RELOAD_RESPONSE="ACTIVATION_RELOAD_RESPONSE";
	public static final String REDEMPTION_REQUEST="REDEMPTION_REQUEST";
	public static final String REDEMPTION_RESPONSE="REDEMPTION_RESPONSE";
	public static final String BALANCE_ENQUIRY_REQUEST="BALANCE_ENQUIRY_REQUEST";
	public static final String BALANCE_ENQUIRY_RESPONSE="BALANCE_ENQUIRY_RESPONSE";
	public static final String SHARP_HEARTBEAT_REQUEST="SHARP_HEARTBEAT_REQUEST";
	public static final String SHARP_HEARTBEAT_RESPONSE="SHARP_HEARTBEAT_RESPONSE";
	public static final String REDEMPTION_RESPONSE_PARTIAL="REDEMPTION_RESPONSE_PARTIAL";
	public static final String REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID="REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID";
	public static final String REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID_RESPONSE="REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID_RESPONSE";
	public static final String BALANCE_ADJUSTMENT_RELOAD_REQUEST="BALANCE_ADJUSTMENT_RELOAD_REQUEST";
	public static final String BALANCE_ADJUSTMENT_RELOAD_RESPONSE="BALANCE_ADJUSTMENT_RELOAD_RESPONSE";
	public static final String BALANCE_ADJUSTMENT_REDEEM_REQUEST="BALANCE_ADJUSTMENT_REDEEM_REQUEST";
	public static final String BALANCE_ADJUSTMENT_REDEEM_RESPONSE="BALANCE_ADJUSTMENT_REDEEM_RESPONSE";
	
	public static final String FORMAT_TYPE_LLVAR="LLVAR";
	public static final String FORMAT_TYPE_LLLVAR="LLLVAR";
	public static final String FORMAT_TYPE_FIXED="FIXED";

	public static final String DATA_TYPE_NUMERIC="N";
	public static final String DATA_TYPE_ALPHA="A";
	public static final String DATA_TYPE_ALPHANUMERIC="AN";
	public static final String DATA_TYPE_ALPHANUMERIC_SPACEPAD="ANP";
	public static final String DATA_TYPE_ALPHANUMERIC_SPECIALCHARS="ANS";
	public static final String DATA_TYPE_NUMERIC_SPECIALCHARS="NS";
	
	public static final String SHARP_REQUEST_MTI = "1200";
	public static final String SHARP_RESPONSE_MTI = "1210";
	
	public static final String SHARP_P63_PROMO_CODE = "PC";
	public static final String SHARP_P63_ACCOUNT_NUMBER = "AN";
	public static final String SHARP_P63_LEDGER_ACCOUNT = "LA";
	public static final String SHARP_P63_FULFILLMENT_GROUP = "FG";

	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	
	
	public static final int MESSAGE_TYPE_ACTIVATION = 1;
	public static final int MESSAGE_TYPE_RELOAD = 2;
	public static final int MESSAGE_TYPE_REDEMPTION = 3;
	public static final int MESSAGE_TYPE_BALANCE_ENQ = 4;
	public static final int MESSAGE_TYPE_HEARTBEAT = 5;
	public static final int MESSAGE_TYPE_VOID = 6;
	
	public static final int MESSAGE_DESC_ID_ACTIVATION_RELOAD = 1;
	public static final int MESSAGE_DESC_ID_REDEMPTION = 3;
	public static final int MESSAGE_DESC_ID_BALANCE_ENQ = 5;
	public static final int MESSAGE_DESC_ID_HEARTBEAT = 7;
	public static final int MESSAGE_DESC_ID_BALANCE_CREDIT = 10;
	public static final int MESSAGE_DESC_ID_BALANCE_DEBIT = 12;
	public static final int MESSAGE_DESC_ID_VOID = 14;
	
	
    public static final String STAN_ID = "STAN_ID";
    public static final String BALANCE_ENQUIRY_STAN = "BAL_ENQ";
    public static final long IDENTIFIER_FULLFILLMENT_STAN_MAX = 999999;
    public static final String LEDGER_TRANS_ID = "LEDGER_TRANS_ID";
    public static final String LEDGER_ENTRY_ID = "LEDGER_ENTRY_ID";
    public static final long LEDGER_TRANS_ID_ADD_VALUE = 100000000;
    
    public static final String SHARP_DUMMY_PAN_NUMBER = "9797979999999991";
    
    public static final String SHARP_QUEUE = "sharpQueue";
    public static final String VALUELINK_QUEUE = "valueLinkQueue";

    
    public static final String RESPONSE_ACTION_CODE_VERIFYCARD = "102";
    public static final String RESPONSE_ACTION_CODE_AUTHORIZER_UNAVAILABLE = "912";
    public static final String RESPONSE_ACTION_CODE_DECLINE = "100";
    public static final String RESPONSE_ACTION_CODE_CANNOT_PARSE_MESSAGE = "680";
    public static final String RESPONSE_ACTION_CODE_EXTERNAL_FORMAT_ERROR = "904";
    public static final String RESPONSE_ACTION_CODE_PARTIAL_APPROVAL = "002";
    public static final String RESPONSE_ACTION_CODE_CARD_NOT_EFFECTIVE = "125";
    public static final String RESPONSE_ACTION_CODE_SUSPECT = "210";    
    public static final String RESPONSE_ACTION_CODE_INTERNAL_FORMAT_ERROR = "999";
    public static final long RESPONSE_ACTION_CODE_UNMAPPED = 22;
    
    
    
    public static final String SHARP_SYSTEM = "sharp";
    public static final String VL_SYSTEM = "valuelink";
    public static final String STR_ADMIN="admin";
    
    public static final int EMAIL_TEMPLATE_VL_RESPONSE_FAILURE = 99;
    
    //Fullfillment messages for admin tool
    public static final String NO_FULFILLMENT_ENTRY_FOUND = "There are no fulfillment entries that match your request(s).";
    public static final String DATABASE_ERROR_OCCURED="A database error has occurred in processing your request.";
    public static final String GENERIC_ERROR_OCCURED="An Exception has occurred in processing you adjustment";
    public static final String NUMERIC_ENTRIES_ALLOWED="Only numeric id(s) can be searched for.";
    public static final String ENTRY_TYPE_MISSING="The selected fulfillment record has no entry_type_id";
    public static final String RESENT_SUCCESS_MESSAGE = "The following Fulfillment Group(s) have been resent";
    public static final String NONRECONCILED_ENTRIES_ERROR = "There are no fulfillment entries that are non reconciled for the Fulfillment Group(s) ";
}
