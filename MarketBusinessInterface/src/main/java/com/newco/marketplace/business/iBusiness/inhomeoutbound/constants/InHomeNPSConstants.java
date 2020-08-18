package com.newco.marketplace.business.iBusiness.inhomeoutbound.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class acts as a constant file
 * 
 * @author Infosys
 * @version 1.0
 */
public class InHomeNPSConstants {
	
	public final static String INHOME_NPS_MESSAGE_FLAG = "inhome_outbound_flag";
	public final static String INHOME_NPS_MESSAGE_FLAG_ON = "ON";
	public static final String INHOME_MESSAGE_SERVICE_NAME = "HSSOSendMessage";
	public static final String INHOME_MESSAGE_SERVICE_VERSION = "1.0";
	public static final String INHOME_UNIT_NUMBER = "UnitNumber";
	public static final String INHOME_ORDER_NUMBER = "OrderNumber";
	public static final String INHOME_EMP_ID = "ISP_ID";
	public static final String INHOME_FROM_FUNCTION = "O";
	public static final String INHOME_TO_FUNCTION = "";
	public static final String INHOME_ORDER_TYPE = "IH";
	public static final String INHOME_TRANSIT_VALUE = "00:30";
	public static final Integer HSRBUYER = 3000;
	public static final String VALIDATION_MESSAGE = "Missing Information :";
	public static final String INVALID_DATA = "Invalid Data :";
    public static final String NPS_NOTIFICATION_INHOME_NO_OF_RETRIES = "NPS_no_of_retries_inHome";
    public static final String NPS_NOTIFICATION_INHOME_RECORDS_PROCESSING_LIMIT = "NPS_no_of_processing_limit_inHome";
    public static final String NPS_NOTIFICATION_INHOME_RECORDS_EMAIL_PROCESSING_LIMIT = "NPS_no_of_email_processing_limit_inHome";
    public static final String CLOSE_SERVICE_ID = "2";
    public static final String SUBSTATUS_SERVICE_ID = "3";
    public static final String SEQ_HYP = " - ";
    public static final String SEQ_COL = " : ";
    public static final String SEQ_COL1 = ":";
    public static final String SEQ_COM = ", ";
    public static final String SEQ_COM1 = ",";
    public static final String URL = "url";
    public static final String SPACE = " ";
    public static final String HEADER = "header";
    public static final String NPS_EXCEPTION = "NPS Webservice returned an empty response";
    public static final String SUCCESS_CODE = "00";
    public static final String VALIDATION_ERROR_CODE = "04";
    public static final String NPS_ERROR_CODE = "08";
    public static final String NPJ_ERROR_CODE = "10";
    public static final String SYSTEM_DOWN_CODE = "12";
    public static final String VALIDATION_ERROR = "Web Service Validation error";
    public static final String NPS_ERROR = "NPS Error";
    public static final String NPJ_ERROR = "NPJ Error";
    public static final String SYSTEM_DOWN = "System Down";
	public static final Integer PROVIDER = 1;
	public static final Integer BUYER = 3;
	public static final Integer PRIVATE_NOTE = 1;
	public static final Integer PUBLIC_NOTE = 0;
	public static final String BUYER_NOTE ="SL Buyer Note : ";
	public static final String PROVIDER_NOTE ="SL Provider Note : ";
	public static final String ADMIN_NOTE ="SL Agent Note : ";
	public static final String SEPERATOR=" | ";
	public static final String CLIENT_ID="serviceLive";
	public static final String USERID="slive";
	public static final String CURRENT_DATE_TIME = "currentDateTime";
	public static final String CORRELATION_ID = "correlationId";
	public static final String VALIDATION_SUCCESS = "success";
	public static final String ROUTED_DATE="20140204";
	public static final String OUTBOUND_FLAG_ON="ON";
	public static final Integer NPS_INACTIVE_IND = 0;
	public static final String  DEFAULT_SEQ_NO = "000-0000-000";
	public static final Integer NO_OF_RETRIES = -1;
	public static final Integer CALL_CLOSE_SERVICE_ID_INT = 2;
	public static final Integer SUBSTATUS_SERVICE_ID_INT = 3;
	public static final Integer DEFAULT_ACTIVE_IND = 0;
	public static final String SERVICE_ORDER_NOTFOUND_CODE = "203";
	public static final String SERVICE_ORDER_NOTFOUND = "Service Order doesnot exist";
	public static final Integer EMAILIND = 1;
	public static final String SERVICE_ORDER_CANCELEDORCLOSED_CODE = "261";
	public static final String SERVICE_ORDER_CANCELEDORCLOSED = "Service Order canceled or closed";
	public static final String INACTIVE = "INACTIVE";
	//Missing info details for sendMessage Webservice validation
	public static final String CORRELATION_ID_ERROR ="correlationId , ";
	public static final String ORDER_TYPE_ERROR ="orderType , ";
	public static final String UNIT_NUMBER_ERROR ="unitNum , ";
	public static final String ORDER_NUMBER_ERROR ="orderNum , ";
	public static final String FROM_FUNCTION_ERROR ="fromFunction , ";
	public static final String TO_FUNCTION_ERROR ="toFunction must be blank , ";
	public static final String MESSAGE_ERROR ="Message , ";
	public static final String EMP_ID_ERROR ="empID , ";
	public static final String UNIT_NUMBER_INVALID_LENGTH ="unitNum : Only 7 digit is allowed for the field, ";
	public static final String ORDER_NUMBER_INVALID_LENGTH="OrderNum: Only 8 digit is allowed for the field,";
	public static final String UNIT_NUMBER_INVALID="unitNum :UnitNumber must be Numeric ,";
	public static final String ORDER_NUMBER_INVALID ="orderNum:OrderNum must be Numeric, ";
    public static final String EMP_ID_INVALID ="empID , ";
    public static final int UNIT_NUMBER_LENGTH = 7;
    public static final int ORDER_NUMBER_LENGTH = 8;
    public static final int MESSAGE_LENGTH = 2000;
    public static final int EMP_ID_LENGTH = 25;
    public static final String INVALID_MESSAGE_LENGTH ="Message: Message length cannot exceed 2000 characters, ";
    public static final String INVALID_EMP_ID_LENGTH ="EmpId :  Only 8 digit is allowed for the field, ";
    public static final String RESCHEDULE_MESSAGE ="Service order has been rescheduled from ";
    public static final String APPOINMENT_DATES = " Appointment Date(s): ";
    public static final String APPOINMENT_DATE = " Appointment Date : ";
    public static final String APPOINMENT_TIME_WINDOW=" Service Window ";
    public static final String RESCHEDULE_DATES = " Reschedule Date(s): ";
    public static final String RESCHEDULE_DATE = " Reschedule Date : ";
    public static final String RESCHEDULE__TIME_WINDOW = " Reschedule Window : ";
    public static final String RESCHEDULE_MESSAGE_TO ="  to ";
    public static final String RESCHEDULE_MESSAGE_FROM ="  from ";
    public static final String PROBLEM_MESSAGE = "SL Problem - The order is in a problem state in ServiceLive.";
    public static final String EVENT_PROBLEM ="PROBLEM";
    //for email
    public static final String INHOME_EMAIL_FLAG = "inhome_outbound_email_flag";
    public static final String EMAIL_CONTENT = "ServiceLive is unable to update NPS for the following SO Ids(Notification Ids):Exception.";
    public static final String MAIL_HOST="smtp.sears.com";
    public static final String NPS_NOTIFICATION_SUBJECT_INHOME1 = "SL-NPS Integration ";
    public static final String NPS_NOTIFICATION_SUBJECT_INHOME2 = " Notification for InHome";
    public static final String NPS_EMAIL_FROM="servicelive@contact.servicelive.com";
	//for waiting email
    public static final String EMAIL_CONTENT_WAITING = "The following SO Ids(Notification Ids) are yet to be updated in NPS.";
    public static final String EMAIL_SUBJECT_WAITING = "SL-NPS Integration Waiting Notification for InHome";
    
    //for system down email
    public static final String EMAIL_SYSTEM_DOWN_CONSTANT = "1";
    public static final String EMAIL_SUBJECT_SYSTEM_DOWN = "SL-NPS Integration System Down Notification for InHome";
    public static final String EMAIL_CONTENT_SYSTEM_DOWN = "The following SO Id(Notification Id) got a System Down Response in NPS.";

    
	public static List<String> fromAndToFunction(){
		List<String> fromToFunctionList=new ArrayList<String>();
		fromToFunctionList.add("C");/*CUSTOMER CARE NETWORK*/
		fromToFunctionList.add("F");/*FLD OPERATION RES TEAM*/
		fromToFunctionList.add("I");/*INSTALLATION*/
		fromToFunctionList.add("N");/*NSC*/
		fromToFunctionList.add("P");/*PARTS*/
		fromToFunctionList.add("R");/*REGION ROUTING*/
		fromToFunctionList.add("S");/*SERV OPERATION RES TEAM*/
		fromToFunctionList.add("U");/*CUSTOMER RELATIONS*/
		fromToFunctionList.add("O");/*We will use this for Send Message API*/
		return fromToFunctionList;
	}
	public static List<String> orderTypeList(){
		List<String> orderTypeList = new ArrayList<String>();
		orderTypeList.add("IH");
		orderTypeList.add("IN");
		orderTypeList.add("CI");
		return orderTypeList;
	}
	public static List<String> callCodeList(){
		List<String> callCodeList = new ArrayList<String>();
		callCodeList.add("10");
		callCodeList.add("20");
		callCodeList.add("50"); // CHnaginig as part of SL-NPS call close code issue when COverage type is PT
		return callCodeList;
	}
	
	public static List<String> partLocationList() {
		List<String> partLocationList = new ArrayList<String>();
		partLocationList.add("U");
		partLocationList.add("I");
		partLocationList.add("L");
		partLocationList.add("E");
		partLocationList.add("X");
		partLocationList.add("H");
		partLocationList.add("A");
		partLocationList.add("K");
		partLocationList.add("T");
		partLocationList.add("D");
		partLocationList.add("B");
		partLocationList.add("S");
		return partLocationList;
	}
	
	public static List<String> validateSet() {
		List<String> validateSet = new ArrayList<String>();
		validateSet.add("Y");
		validateSet.add("y");
		validateSet.add("N");
		validateSet.add("n");
		return validateSet;
	}
	
	public static List<String> coverageCodeList(){
		List<String> chargeCodeList = new ArrayList<String>();
		chargeCodeList.add("PA");
		chargeCodeList.add("IW");
		chargeCodeList.add("SP");
		chargeCodeList.add("PT"); // CHnaginig as part of SL-NPS call close code issue when COverage type is PT
		return chargeCodeList;
	}
	
	public static final String INVALID_MESSAGE = "Invalid Information :";
	public static final String NO_DATA_MESSAGE = "No Service Order details available.";
	public static final int IN_HOME_SERVICE_ID = 2;
	public static final int IN_HOME_RETRY_COUNT = -1;
	
	public static final String REQUEST_VERSION = "1.0";
	public static final String INHOME_REQUEST_SCHEMALOCATION = "http://www.servicelive.com/namespaces/inHomeOutBoundRequest inHomeOutBoundRequest.xsd";
	public static final String INHOME_REQUEST_NAMESPACE = "http://www.servicelive.com/namespaces/inHomeOutBoundRequest";
	public static final String SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";
	
	public static final String ON = "ON";
	public static final String Y = "Y";
	public static final String N = "N";
	public static final String UNIT_NUMBER = "UnitNumber";
	public static final String ORDER_NUMBER = "OrderNumber";
	public static final String COVERAGE_TYPE_LABOR = "CoverageTypeLabor";
	public static final String PAYMENT_METHOD = "PaymentMethod";
	public static final String NO_COMMENTS = "Comments Not Entered";	
	public static final String IN_HOME_ORDER_TYPE = "IH";
	public static final String BRAND = "Brand";
	public static final String MODEL = "Model";
	public static final String SERIAL_NUMBER = "SerialNumber";
	public static final String TECH_ID = "ISP_ID";
	public static final String DEFAULT_JOB_CODE = "44000";
	public static final String DEFAULT_START_TIME = "0800AM";
	public static final String DEFAULT_END_TIME = "0830AM";
	public static final String PROTECTION_AGREEMENT = "Protection Agreement";
	public static final String IN_WARRANTY = "In-Warranty";
	public static final String TIME_FORMAT = "yyyy-MM-dd, h:mm:ss a";
	public static final String NO_DATA = "";
	public static final String X = "X";
	
	public static final String IW = "IW";
	public static final String SP = "SP";
	public static final String CC = "CC";
	public static final String PT = "PT";
	public static final String PA = "PA";
	
	public static final String IW_CODE = "10";
	public static final String SP_CODE = "20";
	public static final String CC_CODE = "30";
	public static final String PT_CODE = "50";
	
	public static HashMap<String, String> coverageCodes(){
		HashMap<String, String> coverageCodes = new HashMap<String, String>();
		coverageCodes.put(PROTECTION_AGREEMENT, SP);
		coverageCodes.put(IN_WARRANTY, IW);
		return coverageCodes;
	}
	
	public static HashMap<String, String> callCodes(){
		HashMap<String, String> callCodes = new HashMap<String, String>();
		callCodes.put(IW, IW_CODE);
		callCodes.put(SP, SP_CODE);
		callCodes.put(PA, SP_CODE);
		callCodes.put(PT, PT_CODE); // CHnaginig as part of SL-NPS call close code issue when COverage type is PT
		return callCodes;
	}

	//validation for closure notification
	public static final String CORRELATION_ERROR = "CorrelationId, ";
	public static final String CORRELATION_INVALID = "CorrelationId : Should be 8 chars, ";
	public static final String ORDERTYPE_ERROR = "orderType, ";
	public static final String ORDERTYPE_INVALID = "orderType : Not in valid set, ";
	public static final String UNITNUM_ERROR = "unitNum, ";
	public static final String UNITNUM_INVALID = "unitNum : Should be 7 digits, ";
	public static final String UNITNUM_NUMERIC = "unitNum : Should be numeric, ";
	public static final String ORDERNUM_ERROR = "orderNum, ";
	public static final String ORDERNUM_INVALID = "orderNum : Should be 8 digits, ";
	public static final String ORDERNUM_NUMERIC = "orderNum : Should be numeric, ";
	public static final String ROUTEDATE_ERROR = "routeDate, ";
	public static final String ROUTEDATE_INVALID = "routeDate : Should be in MMddyyyy format, ";	
	public static final String TECHID_ERROR = "techId, ";
	public static final String TECHID_INVALID = "techId : Should not exceed 20 chars";
	public static final String CALLCODE_ERROR = "callCd, ";
	public static final String CALLCODE_INVALID = "callCd : Should be 10(IW) or 20(SP), ";
	public static final String FROM_TIME_ERROR = "serviceFromTime, ";
	public static final String FROM_TIME_INVALID = "serviceFromTime : Should be in HHMMSS format, ";
	public static final String TO_TIME_ERROR = "serviceToTime, ";
	public static final String TO_TIME_INVALID = "serviceToTime : Should be in HHMMSS format, ";
	public static final String JOB_DATA_ERROR = "jobCodeData, ";
	public static final String JOB_PRICE_ERROR = "jobCalcPrice, ";
	public static final String JOB_PRICE_INVALID = "jobCalcPrice : Should be in XX.XX format, ";
	public static final String JOB_CHARGECD_ERROR = "jobChargeCd, ";
	public static final String JOB_CHARGECD_INVALID = "jobChargeCd : Should be 1 char, ";
	public static final String JOB_CODE_ERROR = "jobCode, ";
	public static final String JOB_CODE_INVALID = "jobCode : Should be 5 chars, ";
	public static final String JOB_COVERAGECD_ERROR = "jobCoverageCd, ";
	public static final String JOB_COVERAGECD_INVALID = "jobCoverageCd : Not in valid set, ";
	public static final String JOB_PRIMFL_ERROR = "jobCodePrimaryFl, ";
	public static final String JOB_RELFL_ERROR = "jobRelatedFl, ";
	public static final String JOB_PRIMFL_INVALID = "jobCodePrimaryFl : Should be Y/N, ";
	public static final String JOB_RELFL_INVALID = "jobRelatedFl : Should be Y/N, ";	
	public static final String PART_DIV_NO = "partDivNo for Part ";
	public static final String PART_PLS_NO = "partPlsNo for Part ";
	public static final String PART_NO = "partPartNo for Part ";
	public static final String PART_ORDER_QTY = "partOrderQty for Part ";
	public static final String PART_INSTALL_QTY = "partInstallQty for Part ";
	public static final String PART_LOCATION = "partLocation for Part ";
	public static final String PART_LOCATION_INVALID = " : Not in valid set, ";
	public static final String PART_COVERAGE_CODE = "partCoverageCode for Part ";
	public static final String PART_PRICE = "partPrice for Part ";
	public static final String PART_DIV_INVALID = " : Should be 3 chars, ";
	public static final String PART_SOURCE_INVALID = " : Should be 3 chars, ";
	public static final String PART_NO_INVALID = " : Should not exceed 24 chars, ";
	public static final String PART_QTY_INVALID = " : Should not exceed 3 digits, ";
	public static final String PART_QTY_NUMERIC = " : Should be numeric, ";
	public static final String PART_COVERAGE_INVALID = " : Should be 2 chars, ";
	public static final String PART_PRICE_INVALID = " : Should be in XX.XX format, ";
	public static final String BRAND_ERROR = "applBrand, ";
	public static final String BRAND_INVALID = "applBrand : Should not exceed 12 chars, ";
	public static final String BRAND_NUMERIC = "applBrand : Should be alphanumeric, ";
	public static final String MODEL_ERROR = "modelNum, ";
	public static final String MODEL_INVALID = "modelNum : Should not exceed 24 chars, ";
	public static final String MODEL_NUMERIC = "modelNum : Should be numeric, ";
	
	//SL-21009
	public static final String SERIAL_ERROR = "serialNum, ";
	public static final String SERIAL_INVALID = "serialNum : Should not exceed 20 chars, ";
	
	public static final String TECHCOMMENTS_ERROR = "techComments, ";
	public static final String PRICE_REGEX = "\\d{1,5}\\.\\d{2}";
	
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int TWELVE = 12;
	
	public static final int TWENTY = 20;
	public static final int FIFTY = 50;
	public static final int TWENTY_FOUR = 24;
	public static final int RELEASE_INDICATOR_TRUE= 1;
	public static final int RELEASE_INDICATOR_FALSE = 0;
	public static final String SUB_STATUS_MESSAGE = "<subStatus>";
	public static final String JOB_CALC_PRICE = "85.00";
	public static final Integer ON_VALUE = 1;
	public static final Integer OFF_VALUE = 0;
	public static final String STATUS_WAITING = "Waiting";
	
	//R12_0
	public static final String RESCHDATE_ERROR = "reschdDate, ";
	public static final String RESCHDATE_INVALID = "reschdDate : Should be in MMddyyyy format, ";	
	public static List<String> callCodeRevisitList(){
		List<String> callCodeList = new ArrayList<String>();
		callCodeList.add("13");
		callCodeList.add("15");
		callCodeList.add("16");
		return callCodeList;
	}
	
	public static final String CUSTOMER_NOT_HOME_CALLCODE = "13";
	public static final String RESCHD_PARTS_CALLCODE = "15";
	public static final String RESCHD_CALLCODE = "16";
	
	public static final String CUSTOMER_NOT_HOME_REASON = "Customer Not Home";
	public static final String PARTS_NEEDED_REASON = "Parts Needed";
	public static final String CUSTOMER_NOT_HOME_NPS_MESSAGE ="Provider on-site. Customer Not Home";
	
	public static final String FROM_RESCHD_TIME_ERROR = "reschdFromTime, ";
	public static final String TO_RESCHD_TIME_ERROR = "reschdToTime, ";
	public static final String CALLCODE_RESCHD_INVALID = "callCd : Should be 13 or 16, ";
	public static final String NOT_APPLICABLE="N/A";
	
	//Priority 5B changes
	public static final int MODEL_REF_ID = 652;
	public static final int SERIAL_REF_ID = 653;
	
}