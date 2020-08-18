package com.servicelive.tokenization.constants;

/**
 * @author Infosys : Jun, 2014
 */
public class DBConstants {

	public static String SCHEMA_SUPPLIER_PROD = "";
	public static String SCHEMA_ACCOUNTS_PROD = "";
	public static String SCHEMA_SLK = "";
	public static String SCHEMA_INTEGRATION = "";

	public static String SHOW_QUERY = "FALSE";

	public static String DB_USER = "";
	public static String DB_PASSWORD = "";
	public static String DB_URL = "";

	public static String SLK_DB_USER = "";
	public static String SLK_DB_PASSWORD = "";
	
	public static String PROCESS_OMS_START_DATE_YYYY_MM_DD = "";
	public static String PROCESS_OMS_END_DATE_YYYY_MM_DD = "";

	public static String QUERY_LIMIT = "";
	public static String QUERY_INITIAL_OFFSET = "";
	public static String QUERY_THREAD_COUNT = "";
	public static String QUERY_THREAD_COUNT_INT = "";
	public static String T1_TOKENIZATION_ID = "";
    public static String QUERY_OFFSET = "";
    public static String QUERY_TOTAL_COUNT_CREDIT_CARDS = "";
    public static String QUERY_FETCH_LAST_TOKENIZATION_ID = "";
    public static String FETCH_CHUNK_TOKENIZED_PRIMARY_KEY="";
	public static String SELECT_CCENKEY_TEMP = "";
	public static String UPDATE_CCENKEY_TEMP = "";
	public static String INSERT_CCENKEY_TEMP = "";

	public static String SELECT_CC_ENCRYPTION_KEYS = "";
	public static String SWAP_CC_ENCRYPTION_KEYS = "";

	public static String QUERY_BACKUP_ACCOUNT_HDR_TMP = "";
	public static String QUERY_BACKUP_OMS_BUYER_NOTIFICATION_TMP = "";
	public static String QUERY_BACKUP_SO_ADDITIONAL_PAYMENT_TMP = "";
	public static String QUERY_BACKUP_SO_ADDITIONAL_PAYMENT_BKP_TMP = "";
	public static String QUERY_BACKUP_SHC_UPSELL_PAYMENT_TMP = "";
	public static String QUERY_CHUNK_ACCOUNT_HDR = "";
	public static String QUERY_CHUNK_OMS_BUYER_NOTIFICATION_GT = "";
	public static String QUERY_CHUNK_OMS_BUYER_NOTIFICATION_LT = "";
	public static String QUERY_CHUNK_OMS_BUYER_WHERE = "";
	public static String QUERY_CHUNK_OMS_BUYER_ORDER = "";
	public static String QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_GT = "";
	public static String QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_LT = "";
	public static String QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_BKP = "";
	public static String QUERY_CHUNK_SHC_UPSELL_PAYMENT = "";

	public static String UPDATE_NEW_DATA_ACCOUNT_HDR = "";
	public static String UPDATE_NEW_DATA_OMS_BUYER_NOTIFICATION = "";
	public static String UPDATE_NEW_DATA_SO_ADDITIONAL_PAYMENT = "";
	public static String UPDATE_NEW_DATA_SO_ADDITIONAL_PAYMENT_BKP = "";
	public static String UPDATE_NEW_DATA_SHC_UPSELL_PAYMENT = "";
	
	public static String COUNT_OMS_BUYER_NOTIFICATIONS;
	public static String COUNT_SO_ADDITIONAL_PAYMENT;
	
	public static String FETCH_OMS_BUYER_NOTIFICATION_SPLITID;
	public static String FETCH_SO_ADDITIONAL_PAYMENT_SPLITID;
	
	public static String FETCH_OMS_SPLITID_LIMIT;
	
	public static String FETCH_TOKENIZER_URL;
	public static String FETCH_TOKENIZER_HEADER;
	public static String FETCH_ENCRYPTION_KEY;
    
	
	public static String QUERY_CHUNK_CREDIT_CARD_DATA = "";
	public static String UPDATE_NEW_CREDIT_CARD_DATA = "";
	
	public static String QUERY_CHUNK_ACCOUNT_HDR_DATA_UPDATE = "";
	public static String UPDATE_NEW_ACCOUNT_HDR_DATA = "";  

	public static String QUERY_CHUNK_SHC_UPSELL_PAYMENT_DATA_UPDATE = "";
	public static String UPDATE_NEW_SHC_UPSELL_PAYMENT_DATA = "";

	public static String QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_DATA_UPDATE_GT = "";
	public static String QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_DATA_UPDATE_LT = "";

	public static String UPDATE_NEW_SO_ADDITIONAL_PAYMENT_DATA = ""; 

	
	public static String QUERY_CHUNK_OMS_BUYER_NOTIFICATION_DATA_UPDATE_GT = "";
	public static String QUERY_CHUNK_OMS_BUYER_NOTIFICATION_DATA_UPDATE_LT = "";

	public static String UPDATE_NEW_OMS_BUYER_NOTIFICATION_DATA = ""; 

	public static String QUERY_CHUNK_CREDIT_CARD_DATA_ADDITIONAL = "";
	
	public static String QUERY_FETCH_SL_STORE_NO = "";

	public static String USER_NAME = "migration";
	
	public static String USER_ID = ",userid:migration";
	//changes for thread creation flag
	public static String QUERY_TOKEN_COUNT_SO_ADDITIONAL_PAYMENT;
	public static String QUERY_TOKEN_COUNT_ACCOUNT_HDR;
	public static String QUERY_TOKEN_COUNT_OMS_BUYER_NOTIFICATION;
	public static String QUERY_TOKEN_COUNT_SHC_UPSELL_PAYMENT;
	
	public static String UPDATE_DB_UPDATE_STATUS_ACCT_HDR = "";  
	public static String UPDATE_DB_UPDATE_STATUS_ACCT_HDR_ERROR= ""; 
	
	public static String UPDATE_DB_UPDATE_STATUS_SHC_UPSELL_PAYMENT = "";  
	public static String UPDATE_DB_UPDATE_STATUS_SHC_UPSELL_PAYMENT_ERROR= "";
	
	public static String UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_GT = "";  
	public static String UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_ERROR_GT= "";
	public static String UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_LT = "";  
	public static String UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_ERROR_LT= "";
	
	public static String UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_GT = "";  
	public static String UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_ERROR_GT= "";
	public static String UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_LT = "";  
	public static String UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_ERROR_LT= "";
	



}
