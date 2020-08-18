package com.newco.marketplace.interfaces;

public interface RescheduleOrderConstants {
	public static final Integer RAND_VAL = 999999;
	public static final String CUST_REF_COVERAGE_LABOR = "CoverageTypeLabor";
	public static final String DEFAULT_START_TIME = "08:00";
	public static final String DEFAULT_END_TIME = "20:30";
	public static final String DEFAULT_TIME_DIFF = "0030";
	public static final String CUST_REF_FEEDBACK = "AddCustSvcsFdbk";
	public static final String IW = "IW";
	public static final String SP = "SP";
	public static final String CC = "CC";
	public static final String PT = "PT";
	public static final String PA = "PA";
	public static final String CALL_CODE ="16";
	public static final String REASON_CODE ="UR15";
	public static final String CUST_REF_ISP_ID = "ISP_ID";
	public static final Integer DIGIT = 7;
	public static final String RESCHEDULE_FLAG = "Y";
	public static final Integer ATTEMPT_COUNT = 1;
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String SO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String CUST_REF_BRAND = "Brand";
	public static final String CUST_REF_UNIT_NUMBER = "UnitNumber";
	public static final String CUST_REF_ORDER_NUMBER = "OrderNumber";
	public static final String CUST_REF_MODEL = "Model";
	public static final String CUST_REF_SERIAL_NUMBER = "SerialNumber";
	public static final String CHARGE_AMOUNT = "85.00";
	public static final String SEQUENCE = "1";
	public static final String RELATED_FLAG = "R";
	public static final String BLANK = "";
	public final static String TECHHUB_AUTH_TOKEN = "techhub.auth.token";

	public final static String TECHHUB_AUTH_URL = "techhub.auth.url";

	public final static String TECHHUB_RESCHEDULE_URL = "techhub.call-close.url";
	public static final String TH_REQUEST_HEADER_AUTH="Authorization";
	public static final String TH_AUTH_TOKEN_HEADER="Basic";
	public static final String SPACE_STRING=" ";
	public static final String TH_API_TOKEN_HEADER="Bearer";
	public static final String HYPHEN_STRING="-";
	public static final String SO_ID_PARAM = "soId";
	
	public static final String ATTEMPT_REQUEST_MAPPING_ERROR= "Issue while Order Attempt Request Mapping";
	public static final String ATTEMPT_REQUEST_MAPPING_ERROR_CD= "0005"; 
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String TIME_FORMAT = "HH:mm";
	
	public static final String BUYER_RESCHEDULE = "Buyer";
	public static final String PROVIDER_RESCHEDULE = "Provider";
	public static final String BUYER_RESCHEDULE_COMMENTS = "Buyer rescheduled the order";
	public static final String OTHER_RESCHEDULE_COMMENTS = "The order was rescheduled";
	public static final String TECH_COMMENTS = "See ServiceLive order comments.";
	
	public static final String Y = "Y";
	public static final String REMITTENCE_CODE = "0008735";
	public static final Integer REMITTENCE_SUB_ACCNT = 951;
	
}
