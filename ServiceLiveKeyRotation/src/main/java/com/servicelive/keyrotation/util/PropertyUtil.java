package com.servicelive.keyrotation.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import com.servicelive.keyrotation.constants.DBConstants;
import com.servicelive.keyrotation.constants.KeyRotationConstants;
import com.servicelive.keyrotation.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class PropertyUtil {
	private static final String SL_KEYROTATION_QUERY_NAME = "keyrotationqueries.properties";
	private static final String SL_KEYROTATION_CONSTANTS_NAME = "keyrotationconstants.properties";

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

			queries = new FileInputStream(SL_KEYROTATION_QUERY_NAME);
			props.load(queries);

			constants = new FileInputStream(SL_KEYROTATION_CONSTANTS_NAME);
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
		KeyRotationConstants.USER_INPUT_VALUE_COUNT = loadProperty("USER_INPUT_VALUE_COUNT");
		KeyRotationConstants.USER_INPUT_VALUE_COUNT_BATCH = loadProperty("USER_INPUT_VALUE_COUNT_BATCH");

		KeyRotationConstants.LDAP_DOMAIN = loadProperty("LDAP_DOMAIN");
		KeyRotationConstants.LDAP_URL = loadProperty("LDAP_URL");
		KeyRotationConstants.LDAP_AUTHENTICATION = loadProperty("LDAP_AUTHENTICATION");

		// set property list
		KeyRotationConstants.AUTHORIZED_USER_LIST
				.addAll(loadPropertyToList("AUTHORIZED_USER_LIST"));

		// set Email properties
		KeyRotationConstants.EMAIL_NOTIFICATION = loadProperty("EMAIL_NOTIFICATION");
		KeyRotationConstants.EMAIL_SMTP = loadProperty("EMAIL_SMTP");
		KeyRotationConstants.EMAIL_SENDER = loadProperty("EMAIL_SENDER");
		KeyRotationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED = loadProperty("EMAIL_RECIPIENT_LIST_COMMA_SEPARATED");
		KeyRotationConstants.EMAIL_SUBJECT = loadProperty("EMAIL_SUBJECT");
		KeyRotationConstants.EMAIL_CONTENT = loadProperty("EMAIL_CONTENT");
		KeyRotationConstants.EMAIL_SIGNATURE = loadProperty("EMAIL_SIGNATURE");
		KeyRotationConstants.EMAIL_DETAIL_1 = loadProperty("EMAIL_DETAIL_1");
		KeyRotationConstants.EMAIL_DETAIL_2 = loadProperty("EMAIL_DETAIL_2");

		KeyRotationConstants.CCENKEY = loadProperty("CCENKEY");
		KeyRotationConstants.CCENKEY_TEMP = loadProperty("CCENKEY_TEMP");

		KeyRotationConstants.CHANGE_FROM_BIT = loadProperty("CHANGE_FROM_BIT");
		KeyRotationConstants.CHANGE_TO_BIT = loadProperty("CHANGE_TO_BIT");

		KeyRotationConstants.DATE_FORMAT = loadProperty("DATE_FORMAT");
		KeyRotationConstants.BIT_64 = loadProperty("BIT_64");
		KeyRotationConstants.BIT_128 = loadProperty("BIT_128");
		KeyRotationConstants.LOGFILE = loadProperty("LOGFILE");
		KeyRotationConstants.LOGGING = loadProperty("LOGGING");

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

		DBConstants.SELECT_CCENKEY_TEMP = loadProperty("SELECT_CCENKEY_TEMP");
		DBConstants.UPDATE_CCENKEY_TEMP = loadProperty("UPDATE_CCENKEY_TEMP");
		DBConstants.INSERT_CCENKEY_TEMP = loadProperty("INSERT_CCENKEY_TEMP");
		DBConstants.SELECT_CC_ENCRYPTION_KEYS = loadProperty("SELECT_CC_ENCRYPTION_KEYS");
		DBConstants.SWAP_CC_ENCRYPTION_KEYS = loadProperty("SWAP_CC_ENCRYPTION_KEYS");
		DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD = loadProperty("PROCESS_OMS_START_DATE_YYYY_MM_DD");
		DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD = loadProperty("PROCESS_OMS_END_DATE_YYYY_MM_DD");

		DBConstants.QUERY_BACKUP_ACCOUNT_HDR_TMP = loadProperty("QUERY_BACKUP_ACCOUNT_HDR_TMP");
		DBConstants.QUERY_BACKUP_OMS_BUYER_NOTIFICATION_TMP = loadProperty("QUERY_BACKUP_OMS_BUYER_NOTIFICATION_TMP");
		DBConstants.QUERY_BACKUP_SO_ADDITIONAL_PAYMENT_TMP = loadProperty("QUERY_BACKUP_SO_ADDITIONAL_PAYMENT_TMP");
		DBConstants.QUERY_BACKUP_SO_ADDITIONAL_PAYMENT_BKP_TMP = loadProperty("QUERY_BACKUP_SO_ADDITIONAL_PAYMENT_BKP_TMP");
		DBConstants.QUERY_BACKUP_SHC_UPSELL_PAYMENT_TMP = loadProperty("QUERY_BACKUP_SHC_UPSELL_PAYMENT_TMP");

		DBConstants.QUERY_CHUNK_ACCOUNT_HDR = loadProperty("QUERY_CHUNK_ACCOUNT_HDR");
		DBConstants.QUERY_CHUNK_OMS_BUYER_NOTIFICATION_GT = loadProperty("QUERY_CHUNK_OMS_BUYER_NOTIFICATION_GT");
		DBConstants.QUERY_CHUNK_OMS_BUYER_NOTIFICATION_LT = loadProperty("QUERY_CHUNK_OMS_BUYER_NOTIFICATION_LT");
		DBConstants.QUERY_CHUNK_OMS_BUYER_WHERE = loadProperty("QUERY_CHUNK_OMS_BUYER_WHERE");
		DBConstants.QUERY_CHUNK_OMS_BUYER_ORDER = loadProperty("QUERY_CHUNK_OMS_BUYER_ORDER");
		DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_GT = loadProperty("QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_GT");
		DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_LT = loadProperty("QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_LT");
		DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_BKP = loadProperty("QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_BKP");
		DBConstants.QUERY_CHUNK_SHC_UPSELL_PAYMENT = loadProperty("QUERY_CHUNK_SHC_UPSELL_PAYMENT");

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
