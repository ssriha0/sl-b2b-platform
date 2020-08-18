package com.servicelive.keyrotation.main;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang.StringUtils;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.constants.KeyRotationConstants;
import com.servicelive.keyrotation.db.DBConnection;
import com.servicelive.keyrotation.email.EmailUtil;
import com.servicelive.keyrotation.key.ChangeKeyService;
import com.servicelive.keyrotation.key.FetchKeyService;
import com.servicelive.keyrotation.key.KeyGeneratorService;
import com.servicelive.keyrotation.log.Log;
import com.servicelive.keyrotation.util.FileUtil;
import com.servicelive.keyrotation.util.LDAPUtil;
import com.servicelive.keyrotation.util.PropertyUtil;
import com.servicelive.keyrotation.util.ValidationUtil;
import com.servicelive.service.DataBackupService;
import com.servicelive.service.DataManipulationService;

/**
 * @author Infosys : Jun, 2014
 * 
 *         This is the main class for Key Rotation batch. Tasks to be performed by the batch are as follows 
 *         1. Validate Inputs 
 *         2. LDAP authentication 
 *         3. Validate against list of users 
 *         4. Key generation, generate the key based on pass phrase and random generated key,
 *         		persist the key to SLK schema, for CCENKEY_TEMP 
 *         5. Data backup, move all the data from main table to a corresponding PCI tables 
 *         6. Key fetch keys from SLK 
 *         7. Data manipulation, Get each row from PCI tables, decrypt the data using
 *         		old key, encrypt the data using new key and update the data in main table 
 *         8. Key change, Interchange the CCENKEY and CCENKEY_TEMP in SLK 
 *         9. Write the key to a txt file
 *         10. Send Email, send email to the specified list notifying the batch ran successfully
 * 
 *         Mandatory Inputs to the main method: 
 *         1. Enterprise UserId 
 *         2. Enterprise user Password 
 *         3. Pass phrase 
 *         4. Identifier for batch mode execution
 * 
 * 		   Sections Identifiers
 *         0: Run entire batch 
 *         1: Run only Key generation section 
 *         2: Run only Data backup section  
 *         3: Run only Data manipulation section
 *         6: Run only oms_buyer_notifications 2nd phase records 
 *         4: Run only Key change section 
 *         5: Run only Send Email section
 */
public class RunKeyRotation {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection connectionSchemaSupplierProd = null;
		Connection connectionSchemaAccountsProd = null;
		Connection connectionSchemaSlk = null;
		Connection connectionSchemaIntegration = null;

		try {
			// load the properties from the properties files
			final PropertyUtil properties = new PropertyUtil();
			Log.writeLog( Level.INFO, "Loading Property values...");
			properties.loadProperties();
			Log.writeLog( Level.INFO, "Property values loaded successfully...");
			// initialize connection objects
			
			DBConnection dbConnection = new DBConnection();
			Log.writeLog( Level.INFO, "Initialize DB Connections...");
			connectionSchemaSupplierProd = dbConnection
					.getConnectionForSupplierProd();
			connectionSchemaAccountsProd = dbConnection
					.getConnectionForAccountsProd();
			connectionSchemaSlk = dbConnection.getConnectionForSlk();
			connectionSchemaIntegration = dbConnection
					.getConnectionForIntegration();

			// set auto commit false for all the connections
			connectionSchemaSupplierProd.setAutoCommit(false);
			connectionSchemaAccountsProd.setAutoCommit(false);
			connectionSchemaSlk.setAutoCommit(false);
			connectionSchemaIntegration.setAutoCommit(false);

			Log.writeLog( Level.INFO, "DB Connections Initialized successfully...");

			// 1. Validate user inputs
			ValidationUtil.validateUserInput(args);

			String batchIdentifier = null;
			if (args.length == Integer
					.valueOf(KeyRotationConstants.USER_INPUT_VALUE_COUNT_BATCH)) {
				batchIdentifier = args[3];
			}
			if (null == batchIdentifier || StringUtils.isEmpty(batchIdentifier)) {
				batchIdentifier = "0";
			}

			// 2. LDAP authentication
			userAuthentication(args[0], args[1]);

			// 3. Validate against list of users
			userAuthorization(args[0]);

			String generatedKey = null;
			// 4. Key generation
			if (batchIdentifier.equals("0") || batchIdentifier.equals("1")) {
				KeyGeneratorService generator = new KeyGeneratorService();
				try {
					generatedKey = generator.generateAndPersistKey(args[2],
							connectionSchemaSlk, args[0]);
					connectionSchemaSlk.commit();
				} catch (Exception e) {
					// rollback the db operation
					connectionSchemaSlk.rollback();
					throw new Exception(e.getMessage());
				}
			}

			// 5. Data backup
			if (batchIdentifier.equals("0") || batchIdentifier.equals("2")) {
				DataBackupService backupService = new DataBackupService();
				backupService.backupData(connectionSchemaSupplierProd,
						connectionSchemaAccountsProd,
						connectionSchemaIntegration);
				connectionSchemaSupplierProd.commit();
				connectionSchemaAccountsProd.commit();
				connectionSchemaIntegration.commit();
			}

			// 6. Key fetch
			Map<String, String> keysMap = new HashMap<String, String>();
			if (batchIdentifier.equals("0") || batchIdentifier.equals("3") || batchIdentifier.equals("4") ||
					batchIdentifier.equals("6")) {
				// The map contains two values:
				// 1. mainKey- <mainKeyValue>
				// 2. tempKey- <tempKeyValue>

				FetchKeyService fetchKeyService = new FetchKeyService();
				keysMap = fetchKeyService.getKeys(connectionSchemaSlk);

				Log.writeLog( Level.INFO, ""+keysMap.size());

			}
			
			if(batchIdentifier.equals("0") || batchIdentifier.equals("3") || (batchIdentifier.equals("6"))){
				// 7. Data manipulation
				DataManipulationService dataService = new DataManipulationService();
				dataService.manipulateData(keysMap,
						connectionSchemaSupplierProd,
						connectionSchemaAccountsProd,
						connectionSchemaIntegration, batchIdentifier);
			}

			// 8. Key change
			if (batchIdentifier.equals("0") || batchIdentifier.equals("4")) {
				ChangeKeyService changeKeyService = new ChangeKeyService();
				try {
					changeKeyService.changeKeys(keysMap, connectionSchemaSlk);
					connectionSchemaSlk.commit();
				} catch (Exception e) {
					// rollback the db operation
					connectionSchemaSlk.rollback();
					throw new Exception(e.getMessage());
				}
			}
			
			// 9. Write the Key to a file
			if(null != generatedKey){
				FileUtil fileUtil = new FileUtil();
				String filePath = fileUtil.writeKeyFile(generatedKey);
				Log.writeLog( Level.INFO, "Key written to the file : "+filePath);
			}

			// 10. Send Email
			if (batchIdentifier.equals("0") || batchIdentifier.equals("5")) {
				EmailUtil.sendEmail(args[0]);
			}

			// After all successful operation, commit all db transactions
			Log.writeLog( Level.INFO, "DB Operations committed successfully...");

		} catch (Exception exception) {
			Log.writeLog( Level.SEVERE, "Exception message: " + exception.getMessage());
			Log.writeLog( Level.SEVERE, "Severe Exception, program will terminate!!! "
					+ exception);

			exception.printStackTrace();

			// rollback all the db operations
			try {
				connectionSchemaSupplierProd.rollback();
				connectionSchemaAccountsProd.rollback();
				connectionSchemaSlk.rollback();
				connectionSchemaIntegration.rollback();
			} catch (SQLException e) {
				Log.writeLog( Level.SEVERE, "Severe Error occured while connection rollback....");
				e.printStackTrace();
			}

			System.exit(0);
		} finally {
			// close all the connections
			try {
				if (null != connectionSchemaSupplierProd) {
					connectionSchemaSupplierProd.close();
				}
				if (null != connectionSchemaAccountsProd) {
					connectionSchemaAccountsProd.close();
				}
				if (null != connectionSchemaSlk) {
					connectionSchemaSlk.close();
				}
				if (null != connectionSchemaIntegration) {
					connectionSchemaIntegration.close();
				}
			} catch (SQLException e) {
				Log.writeLog( Level.SEVERE, "Error occured while connection close....");
				e.printStackTrace();
			}
			Log.writeLog( Level.INFO, " SLKeyRotation batch ran successfully...");

		}
	}

	private static boolean userAuthentication(String userName, String password)
			throws Exception {
		LdapContext ldapContext = null;
		try {
			// appending the user name with domain
			StringBuilder fullUserName = new StringBuilder(
					KeyRotationConstants.LDAP_DOMAIN).append("\\").append(
					userName);

			Log.writeLog( Level.INFO, "Logged in user : " + fullUserName.toString());
			ldapContext = LDAPUtil.getLdapContext(
					KeyRotationConstants.LDAP_AUTHENTICATION,
					fullUserName.toString(), password,
					KeyRotationConstants.LDAP_URL, true);
			Attributes attrs = LDAPUtil.getUserAttributesSubtreeScope(
					"sAMAccountName", String.valueOf(userName), ldapContext);
			if ((null != attrs) && (null != attrs.get("sAMAccountName"))) {
				Log.writeLog( Level.INFO, "Valid user : " + attrs.get("sAMAccountName"));
				return true;
			} else {
				Log.writeLog( Level.INFO, "Invalid User! " + userName);
				return false;
			}
		} catch (NamingException e) {
			Log.writeLog( Level.INFO, "Invalid User! " + userName);
			throw e;
		} catch (Exception e) {
			Log.writeLog( Level.SEVERE, "Exception in LDAP authentication : " + e);
			throw e;
		} finally {
			if (ldapContext != null) {
				try {
					ldapContext.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private static boolean userAuthorization(String userName) throws Exception {
		if (!KeyRotationConstants.AUTHORIZED_USER_LIST.contains(userName)) {
			Log.writeLog( Level.SEVERE, "User Not authorized to run Key Rotation!!!");
			throw new Exception("User Not authorized to run Key Rotation!!!");
		}
		return false;
	}
}
