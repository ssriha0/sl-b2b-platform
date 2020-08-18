package com.newco.marketplace.interfaces;

public interface EventCallbackConstants {

	public static final String SORESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soResponse";

	public static final String SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";

	public static final String PATH_PARAM_EVENT_ERROR = "Path Param Event is missing";
	public static final String PATH_PARAM_EVENT_ERROR_CD = "0000";

	public static final String PATH_PARAM_REQUEST_ERROR = "Event Request is missing";
	public static final String PATH_PARAM_REQUEST_ERROR_CD = "0001";

	public static final String PARAM_EVENT_NUMERIC_ERROR = "Path Param Event should not be numeric or alphanumeric";
	public static final String PARAM_EVENT_NUMERIC_ERROR_CD = "0002";

	public static final String SUCCESS_CODE = "200";
	public static final String ATTEMPT_RESP_SUCCESS_CODE = "00";
	public static final String SUCCESS_MESSAGE = "Success";
	public static final Integer BAD_REQUEST_CODE = 400;

	public final static String CLOSE_ORDER_EVENT = "CLOSE_ORDER";
	public final static String INHOME_BUYER_ID = "3000";
	public final static String RESCHEDULE_ORDER_EVENT = "accept-reschedule";

	public static final String INVALID_PARAM_EVENT = "Path Param Event is invalid.";
	public static final String PARAM_EVENT_INVALID_ERROR = "0003";

	public static final String REQ_BODY_MAPPING_ERROR = "Request Mapping failed.";
	public static final String RESPONSE_AUTHENTICATION_ERROR_CODE = "401";
	public static final String RESPONSE_AUTHENTICATION_ERROR = "Authentication Error. Failure to get Auth Token from TechHub.";
	public static final String CONNECTION_ISSUE_MESG = "System connection issue";
	public static final String CONNECTION_ISSUE_CODE = "503";

	public static final String TH_API_HEADER_USER_ID_VALUE = "Buyer3000";
	public static final String TH_API_HEADER_CLIENT_ID_VALUE = "SVLV";
	
	public static final int TECH_COMMENT_REAL_LIMIT = 120;
	public static final int TECH_COMMENT_CONTENT_LIMIT = 117;
	public static final int BRAND_NAME_LIMIT = 12;
	public static final int MODEL_NUMBER_LIMIT = 24;
	public static final int SERIAL_NUMBER_LIMIT = 20;	
	
}
