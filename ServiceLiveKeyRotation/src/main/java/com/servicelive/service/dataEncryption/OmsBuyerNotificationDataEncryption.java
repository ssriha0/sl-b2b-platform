package com.servicelive.service.dataEncryption;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;

import javax.crypto.SecretKey;

import org.apache.commons.lang.StringUtils;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.constants.DBConstants;
import com.servicelive.keyrotation.constants.KeyRotationConstants;
import com.servicelive.keyrotation.log.Log;
import com.servicelive.keyrotation.util.CryptographyUtil;

/**
 * @author Infosys: Jun, 2014
 */
public class OmsBuyerNotificationDataEncryption implements Runnable{

	private Connection connectionSchemaIntegration;
	private String oldKey;
	private String newKey;
	private long splitId;
	private boolean isGreater;
	long offset = 0l;

	public OmsBuyerNotificationDataEncryption(Map<String, String> keysMap,
			Connection connectionSchemaIntegration, long splitId, boolean isGreater,
			String batchIdentifier) {
		this.connectionSchemaIntegration = connectionSchemaIntegration;

		if(batchIdentifier.equals("6")){
			newKey = keysMap.get(KeyRotationConstants.CCENKEY);
			oldKey = keysMap.get(KeyRotationConstants.CCENKEY_TEMP);
		} else {
			oldKey = keysMap.get(KeyRotationConstants.CCENKEY);
			newKey = keysMap.get(KeyRotationConstants.CCENKEY_TEMP);
		}
		
		this.splitId = splitId;
		this.isGreater = isGreater;
	}

	public void run() {
		// Call DB procedure
		Log.writeLog(Level.INFO,
				"Inside OmsBuyerNotificationDataEncryption thread..." + Thread.currentThread().getName());
		PreparedStatement statement = null;
		try {
			//int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			if(isGreater){
				offset = splitId;
			} else {
				offset = 0l;
			}
			int limit = Integer.valueOf(DBConstants.QUERY_LIMIT);
			int resultListSize = 0;
			ResultSet resultSet = null;
			
			StringBuilder queryString = new StringBuilder();
			if(isGreater){
				queryString.append(DBConstants.QUERY_CHUNK_OMS_BUYER_NOTIFICATION_GT);
			} else {
				queryString.append(DBConstants.QUERY_CHUNK_OMS_BUYER_NOTIFICATION_LT);
			}
			
			if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
					!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
				queryString.append(" ").append(DBConstants.QUERY_CHUNK_OMS_BUYER_WHERE);
			}
			
			queryString.append(" ").append(DBConstants.QUERY_CHUNK_OMS_BUYER_ORDER);
			
			statement = connectionSchemaIntegration
					.prepareStatement(queryString.toString());

			CryptographyUtil cryptographyUtil = new CryptographyUtil();
			SecretKey secretNew = cryptographyUtil.getSecretForBits(newKey,
					KeyRotationConstants.CHANGE_TO_BIT);
			SecretKey secretOld = cryptographyUtil.getSecretForBits(oldKey,
					KeyRotationConstants.CHANGE_FROM_BIT);
			// loop and get chunk data
			do {
				if(isGreater){
					statement.setLong(1, offset);
					if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
							!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
						statement.setString(2, DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD);
						statement.setString(3, DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD);
						statement.setInt(4, limit);
					} else {
						statement.setInt(2, limit);
					}
				} else {
					statement.setLong(1, offset);
					statement.setLong(2, splitId);
					if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
							!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
						statement.setString(3, DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD);
						statement.setString(4, DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD);
						statement.setInt(5, limit);
					} else {
						statement.setInt(3, limit);
					}
				}
				
				Log.writeLog(Level.INFO, "OmsBuyerNotificationDataEncryption : Before Fetch : ");
				resultSet = statement.executeQuery();
				Log.writeLog(Level.INFO, "OmsBuyerNotificationDataEncryption : After Fetch : ");

				// for each,
				resultListSize = encryptDecryptOmsData(resultSet, secretOld, secretNew);

				//offset = offset + limit;
				Log.writeLog(Level.INFO,
						"OmsBuyerNotificationDataEncryption : Fetch size : "
								+ resultListSize +" : Offset : "
								+ offset);
				connectionSchemaIntegration.commit();
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,
					"Exiting OmsBuyerNotificationDataEncryption thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exiting OmsBuyerNotificationDataEncryption thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	private int encryptDecryptOmsData(ResultSet resultSet, SecretKey secretOld, SecretKey secretNew) 
			throws Exception {
		String originalOmsString = null;
		String encryptedOmsData = null;
		PreparedStatement updateStatement = connectionSchemaIntegration
				.prepareStatement(DBConstants.UPDATE_NEW_DATA_OMS_BUYER_NOTIFICATION);

		CryptographyUtil crypto = new CryptographyUtil();
		int rows = 0;
		boolean isRowUpdated = false;

		while (resultSet.next()) {
			// 1. decrypt using old key and encrypt using new key
			if (null != resultSet.getString(2)) {
				try {
//					originalOmsString = crypto.decryptKey(
//							resultSet.getString(2), oldKey,
//							KeyRotationConstants.CHANGE_FROM_BIT);
					
					originalOmsString = crypto.decryptKey(
							resultSet.getString(2), secretOld);
					
//					encryptedOmsData = crypto.encryptKey(
//							originalOmsString, newKey,
//							KeyRotationConstants.CHANGE_TO_BIT);
					
					encryptedOmsData = crypto.encryptKey(
							originalOmsString, secretNew);
					
					// 2. update actual table
					updateStatement.setString(1, encryptedOmsData);
					updateStatement.setLong(2, resultSet.getLong(1));

					// Added for batch execution										
					updateStatement.addBatch();
					isRowUpdated = true;					
					// updateStatement.executeUpdate();
				} catch (Exception e) {
					Log.writeLog(Level.SEVERE,
							"Could not convert row in OmsBuyerNotificationDataEncryption with omsBuyerNotificationId : "
									+ resultSet.getString(1) + " -****** ERROR *****-");
					//throw e;
				}
			}
			offset = resultSet.getLong(1);
			rows = rows + 1;
		}
		if (isRowUpdated) {
			// Added for batch execution
			updateStatement.executeBatch();
		}
		return rows;
	}

	public String getOldKey() {
		return oldKey;
	}

	public void setOldKey(String oldKey) {
		this.oldKey = oldKey;
	}

	public String getNewKey() {
		return newKey;
	}

	public void setNewKey(String newKey) {
		this.newKey = newKey;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getSplitId() {
		return splitId;
	}

	public void setSplitId(long splitId) {
		this.splitId = splitId;
	}

	public boolean isGreater() {
		return isGreater;
	}

	public void setGreater(boolean isGreater) {
		this.isGreater = isGreater;
	}

}
