package com.servicelive.esb.constant;

public class NPSConstants {
	
	// Default values for Call Close XML
	public final static String TECH_ID = "SL00002";
	public final static String CALL_CODE = "50";
	public final static String STATUS_CODE = "CO";
	public final static String ORDER_TYPE = "INS";
	public final static String FINAL_MERCHANDISE_STATUS = "Installed";
	
	public final static String PAYMENT_CASH_CHEQUE_TYPE = "CA";
	public final static String PAYMENT_CASH_CHEQUE_TYPE_IND = "GC";
	public final static String PAYMENT_CARD_TYPE = "CC";
	public final static String ENCRYPTION_KEY = "enKey";
	
	public final static String CREDIT_CARD_ENCRYPTION_KEY = "P3Q6GUCryVD51h5JOo0WMQ==";
	
	// Constants for formatting call close xml file name, xml header etc
	public static final String FILE_NAME_PREFIX = "CLSINSSO";
	public static final String DOT = ".";
    public static final String FILE_NAME_TIMESTAMP_FORMAT = "yyyyMMdd.HHmmssss";
    public static final String CALL_CLOSE_FILE_PATH = "NPS_CALL_CLOSE_FILE_PATH";
    public static final String NEW_CALL_CLOSE_FILE_PATH = "NPS_NEW_CALL_CLOSE_FILE_PATH";
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n";
    public static final String START_TAG = "<CallCloseInstallationServiceOrders>\r\n";
    public static final String END_TAG = "</CallCloseInstallationServiceOrders>\r\n";
    public static final String CR_NL = "\r\n";
    
    // ESB Session keys for storing NPS values in ESB session
	public static final String CLOSED_ORDERS_MSG_KEY = "npsClosedOrdersMsgKey";
	public static final String CALL_CLOSE_FILE_NAME_MSG_KEY = "npsCallCloseFileNameMsgKey";
	public static final String NPS_PROCESS_ID_MSG_KEY = "npsProcessIdMsgKey";
	
	public static final String RETRY_HOURS_KEY = "RETRY_HOURS";
}
