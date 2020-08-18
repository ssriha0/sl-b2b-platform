package com.newco.marketplace.interfaces;

/**
 * @author @paugus2
 * */

 public interface BuyerConstants {
	 
	public static final Integer LOCATION_TYPE_BUSINESS = 1;
	public static final Integer LOCATION_TYPE_MAILING = 2;
	public static final Integer LOCATION_TYPE_HOME = 3;
	public static final Integer LOCATION_TYPE_WORK = 4;
	public static final Integer LOCATION_TYPE_BILLING = 5;
	public static final Integer LOCATION_TYPE_OTHER = 6;
	public static final String LOCATION_NAME_HOME = "Home";
	public static final Integer DEFAULT_LOCATION_YES = 1;
	public static final Integer TERM_COND_ID = 2;
	public static final Integer TERM_COND_IND = 1;
	
	public static final String SIMPLE_BUYER_POSTING_FEE = "simple_buyer_posting_fee";
	public static final String SIMPLE_BUYER_CANCELLATION_FEE = "simple_buyer_cancellation_fee";
	public static final String PRO_BUYER_POSTING_FEE = "pro_buyer_posting_fee";
	public static final String PRO_BUYER_CANCELLATION_FEE = "pro_buyer_cancellation_fee";
	
	public static final Integer OMS_BUYER_ID = 1000;
	public static final int HSR_BUYER_ID = 3000;
	public static final int RELAY_BUYER_ID = 3333;
	public static final int TECH_TALK_BUYER_ID = 7777;
	public static final int FACILITIES_BUYER_ID = 500244;
	public static final int ASSURANT_BUYER_ID = 1085;
	
	public static final String ACTIVE_SO_CANCELLATION_REFUND = "Void SO";
	public static final String CANCELLATION_WITHIN_24_HOURS = "Cancel SO Within 24 Hrs";
	public static final String CANCELLATION_OUTSIDE_24_HOURS = "Cancel SO Outside 24 Hrs";
	public static final int Void_SO_Bus_Trans_Id = 115;
	public static final String HSR_BUYER="3000";
	
	public static final double ZERO_CANCELLATION_REFUND = 0.00;
	public static final int Cancel_SO_With_Penalty_Bus_Trans_Id = 110;
	public static final int Cancel_SO_Without_Penalty_Bus_Trans_Id = 111;
}
