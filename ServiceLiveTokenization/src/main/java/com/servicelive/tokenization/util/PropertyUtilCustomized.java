package com.servicelive.tokenization.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import com.servicelive.tokenization.constants.DBConstants;
import com.servicelive.tokenization.constants.TokenizationConstants;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class PropertyUtilCustomized {
	private static final String SL_TOKENIZATION_QUERY_NAME = "D:/ServiceLive/TokenizationBatch/properties/tokenizationqueries.properties";
	private static final String SL_TOKENIZATION_CONSTANTS_NAME = "D:/ServiceLive/TokenizationBatch/properties/tokenizationconstants.properties";
	private Properties props = new Properties();
	/**
	 * Loads all the properties from an external property file
	 * 
	 * @throws IOException
	 */
	public void loadProperties() throws IOException {
		Properties temp = new Properties();
		FileInputStream constants = null;
		FileInputStream queries = null;
		try {
			Log.writeLog(Level.INFO, "Loading properties...");

			queries = new FileInputStream(SL_TOKENIZATION_QUERY_NAME);
			props.load(queries);

			constants = new FileInputStream(SL_TOKENIZATION_CONSTANTS_NAME);
			temp.load(constants);
			props.putAll(temp);

			Log.writeLog(Level.INFO, "Assigning properties to constants..."); 
			loadConstantValues();

			constants.close();
			queries.close();

			Log.writeLog(Level.INFO, "All properties loaded.");
		} catch (IOException e) {
			Log.writeLog(Level.SEVERE,
					"Problem occured while reading from properties file!!! "
							+ e);
			throw e;
		} finally {
			if (null != constants) {
				constants.close();
			}
			if (null != queries) {
				queries.close();
			}
		}
	}

	public void loadConstantValues() {
		// add new properties here once added in the property file
	
		// set Email properties
		TokenizationConstants.EMAIL_NOTIFICATION = loadProperty("EMAIL_NOTIFICATION");
		TokenizationConstants.EMAIL_SMTP = loadProperty("EMAIL_SMTP");
		TokenizationConstants.EMAIL_SENDER = loadProperty("EMAIL_SENDER");
		TokenizationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED = loadProperty("EMAIL_RECIPIENT_LIST_COMMA_SEPARATED");
		TokenizationConstants.EMAIL_SUBJECT = loadProperty("EMAIL_SUBJECT");
		TokenizationConstants.EMAIL_CONTENT = loadProperty("EMAIL_CONTENT");
		TokenizationConstants.EMAIL_SIGNATURE = loadProperty("EMAIL_SIGNATURE");
		TokenizationConstants.EMAIL_DETAIL_1 = loadProperty("EMAIL_DETAIL_1");
		TokenizationConstants.EMAIL_DETAIL_2 = loadProperty("EMAIL_DETAIL_2");


		TokenizationConstants.DATE_FORMAT = loadProperty("DATE_FORMAT");
		TokenizationConstants.LOGFILE = loadProperty("LOGFILE");
		TokenizationConstants.LOGGING = loadProperty("LOGGING");

		// set properties for DB Constants
		DBConstants.DB_URL = loadProperty("DB_URL");
		DBConstants.DB_USER = loadProperty("DB_USER");
		DBConstants.DB_PASSWORD = loadProperty("DB_PASSWORD");
		DBConstants.SCHEMA_SUPPLIER_PROD = loadProperty("SCHEMA_SUPPLIER_PROD");
		DBConstants.SCHEMA_ACCOUNTS_PROD = loadProperty("SCHEMA_ACCOUNTS_PROD");
		DBConstants.SCHEMA_SLK = loadProperty("SCHEMA_SLK");
		DBConstants.SCHEMA_INTEGRATION = loadProperty("SCHEMA_INTEGRATION");
		DBConstants.SLK_DB_USER = loadProperty("SLK_DB_USER");
		DBConstants.SLK_DB_PASSWORD = loadProperty("SLK_DB_PASSWORD");
		DBConstants.SHOW_QUERY = loadProperty("SHOW_QUERY");

		DBConstants.QUERY_LIMIT = loadProperty("QUERY_LIMIT");
		DBConstants.QUERY_INITIAL_OFFSET = loadProperty("QUERY_INITIAL_OFFSET");



		DBConstants.UPDATE_NEW_DATA_ACCOUNT_HDR = loadProperty("UPDATE_NEW_DATA_ACCOUNT_HDR");
		DBConstants.UPDATE_NEW_DATA_OMS_BUYER_NOTIFICATION = loadProperty("UPDATE_NEW_DATA_OMS_BUYER_NOTIFICATION");
		DBConstants.UPDATE_NEW_DATA_SO_ADDITIONAL_PAYMENT = loadProperty("UPDATE_NEW_DATA_SO_ADDITIONAL_PAYMENT");
		DBConstants.UPDATE_NEW_DATA_SO_ADDITIONAL_PAYMENT_BKP = loadProperty("UPDATE_NEW_DATA_SO_ADDITIONAL_PAYMENT_BKP");
		DBConstants.UPDATE_NEW_DATA_SHC_UPSELL_PAYMENT = loadProperty("UPDATE_NEW_DATA_SHC_UPSELL_PAYMENT");
		
		DBConstants.COUNT_OMS_BUYER_NOTIFICATIONS = loadProperty("COUNT_OMS_BUYER_NOTIFICATIONS");
		DBConstants.COUNT_SO_ADDITIONAL_PAYMENT = loadProperty("COUNT_SO_ADDITIONAL_PAYMENT");
		
		DBConstants.FETCH_OMS_BUYER_NOTIFICATION_SPLITID = loadProperty("FETCH_OMS_BUYER_NOTIFICATION_SPLITID");
		DBConstants.FETCH_SO_ADDITIONAL_PAYMENT_SPLITID = loadProperty("FETCH_SO_ADDITIONAL_PAYMENT_SPLITID");
		DBConstants.FETCH_OMS_SPLITID_LIMIT = loadProperty("FETCH_OMS_SPLITID_LIMIT");
		DBConstants.FETCH_TOKENIZER_URL =  loadProperty("FETCH_TOKENIZER_URL"); 
		DBConstants.FETCH_TOKENIZER_HEADER =  loadProperty("FETCH_TOKENIZER_HEADER");
		DBConstants.FETCH_ENCRYPTION_KEY =  loadProperty("FETCH_ENCRYPTION_KEY");	
		
		
		DBConstants.QUERY_CHUNK_CREDIT_CARD_DATA = loadProperty("QUERY_CHUNK_CREDIT_CARD_DATA");
		DBConstants.UPDATE_NEW_CREDIT_CARD_DATA = loadProperty("UPDATE_NEW_CREDIT_CARD_DATA");
		DBConstants.QUERY_CHUNK_ACCOUNT_HDR_DATA_UPDATE = loadProperty("QUERY_CHUNK_ACCOUNT_HDR_DATA_UPDATE");
		DBConstants.UPDATE_NEW_ACCOUNT_HDR_DATA = loadProperty("UPDATE_NEW_ACCOUNT_HDR_DATA");
		DBConstants.QUERY_CHUNK_SHC_UPSELL_PAYMENT_DATA_UPDATE = loadProperty("QUERY_CHUNK_SHC_UPSELL_PAYMENT_DATA_UPDATE");
		DBConstants.UPDATE_NEW_SHC_UPSELL_PAYMENT_DATA = loadProperty("UPDATE_NEW_SHC_UPSELL_PAYMENT_DATA");
		DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_DATA_UPDATE_GT = loadProperty("QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_DATA_UPDATE_GT");
		DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_DATA_UPDATE_LT = loadProperty("QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_DATA_UPDATE_LT");
		DBConstants.UPDATE_NEW_SO_ADDITIONAL_PAYMENT_DATA = loadProperty("UPDATE_NEW_SO_ADDITIONAL_PAYMENT_DATA");  

		DBConstants.QUERY_CHUNK_OMS_BUYER_NOTIFICATION_DATA_UPDATE_GT = loadProperty("QUERY_CHUNK_OMS_BUYER_NOTIFICATION_DATA_UPDATE_GT");  
		DBConstants.QUERY_CHUNK_OMS_BUYER_NOTIFICATION_DATA_UPDATE_LT = loadProperty("QUERY_CHUNK_OMS_BUYER_NOTIFICATION_DATA_UPDATE_LT");  
		DBConstants.UPDATE_NEW_OMS_BUYER_NOTIFICATION_DATA = loadProperty("UPDATE_NEW_OMS_BUYER_NOTIFICATION_DATA");  
		DBConstants.QUERY_CHUNK_CREDIT_CARD_DATA_ADDITIONAL = loadProperty("QUERY_CHUNK_CREDIT_CARD_DATA_ADDITIONAL");  
		DBConstants.QUERY_CHUNK_CREDIT_CARD_DATA_ADDITIONAL = loadProperty("QUERY_CHUNK_CREDIT_CARD_DATA_ADDITIONAL");   
		DBConstants.FETCH_CHUNK_TOKENIZED_PRIMARY_KEY = loadProperty("FETCH_CHUNK_TOKENIZED_PRIMARY_KEY");    
		
		DBConstants.QUERY_TOTAL_COUNT_CREDIT_CARDS = loadProperty("QUERY_TOTAL_COUNT_CREDIT_CARDS");  
		DBConstants.QUERY_THREAD_COUNT = loadProperty("QUERY_THREAD_COUNT");
		DBConstants.T1_TOKENIZATION_ID = loadProperty("T1_TOKENIZATION_ID");  
		DBConstants.QUERY_OFFSET = loadProperty("QUERY_OFFSET");  
		DBConstants.QUERY_FETCH_LAST_TOKENIZATION_ID = loadProperty("QUERY_FETCH_LAST_TOKENIZATION_ID");
		DBConstants.QUERY_FETCH_SL_STORE_NO = loadProperty("QUERY_FETCH_SL_STORE_NO");
		
		DBConstants.QUERY_TOKEN_COUNT_SO_ADDITIONAL_PAYMENT = loadProperty("QUERY_TOKEN_COUNT_SO_ADDITIONAL_PAYMENT");
		DBConstants.QUERY_TOKEN_COUNT_ACCOUNT_HDR = loadProperty("QUERY_TOKEN_COUNT_ACCOUNT_HDR");
		DBConstants.QUERY_TOKEN_COUNT_OMS_BUYER_NOTIFICATION = loadProperty("QUERY_TOKEN_COUNT_OMS_BUYER_NOTIFICATION");
		DBConstants.QUERY_TOKEN_COUNT_SHC_UPSELL_PAYMENT = loadProperty("QUERY_TOKEN_COUNT_SHC_UPSELL_PAYMENT");
		
		DBConstants.UPDATE_DB_UPDATE_STATUS_ACCT_HDR = loadProperty("UPDATE_DB_UPDATE_STATUS_ACCT_HDR");
		DBConstants.UPDATE_DB_UPDATE_STATUS_ACCT_HDR_ERROR = loadProperty("UPDATE_DB_UPDATE_STATUS_ACCT_HDR_ERROR");
		DBConstants.UPDATE_DB_UPDATE_STATUS_SHC_UPSELL_PAYMENT = loadProperty("UPDATE_DB_UPDATE_STATUS_SHC_UPSELL_PAYMENT");
		DBConstants.UPDATE_DB_UPDATE_STATUS_SHC_UPSELL_PAYMENT_ERROR = loadProperty("UPDATE_DB_UPDATE_STATUS_SHC_UPSELL_PAYMENT_ERROR");
		DBConstants.UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_GT = loadProperty("UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_GT");
		DBConstants.UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_ERROR_GT = loadProperty("UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_ERROR_GT");
		DBConstants.UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_LT = loadProperty("UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_LT");
		DBConstants.UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_ERROR_LT = loadProperty("UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_ERROR_LT");
		DBConstants.UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_GT = loadProperty("UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_GT");
		DBConstants.UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_ERROR_GT = loadProperty("UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_ERROR_GT");
		DBConstants.UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_LT = loadProperty("UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_LT");
		DBConstants.UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_ERROR_LT = loadProperty("UPDATE_DB_UPDATE_STATUS_OMS_BUYER_NOTIFICATION_ERROR_LT");
		
	}

	private String loadProperty(String propertyName) {
		String propertyValue = "";
		try {
			propertyValue = props.getProperty(propertyName);

			if (propertyValue == null || propertyValue.trim().length() < 1) {
				Log.writeLog(Level.WARNING, "The Value for the propertyName  '"
						+ propertyName + "' is missing in property file");
			}
		} catch (Exception e) {
			Log.writeLog(Level.WARNING, "Following Property does not exist : "
					+ propertyName);
			e.printStackTrace();
		}
		return propertyValue == null ? "" : propertyValue.trim();
	}

	private List<String> loadPropertyToList(String propertyName) {

		String propertyValue = "";
		List<String> propertyList = new ArrayList<String>();
		try {
			propertyValue = props.getProperty(propertyName);

			if (propertyValue == null || propertyValue.trim().length() < 1) {
				Log.writeLog(Level.WARNING, "The Value for the propertyName  '"
						+ propertyName + "' is missing in property file");
			} else {
				String[] propertyArray = propertyValue.split(",");

				for (String property : propertyArray) {
					propertyList.add(property);
				}

			}
		} catch (Exception e) {
			Log.writeLog(Level.WARNING, "Following Property does not exist : "
					+ propertyName);
			e.printStackTrace();
		}
		return propertyList;
	}
}
