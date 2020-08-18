package com.servicelive.service.dataEncryption;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;

import javax.crypto.SecretKey;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.constants.DBConstants;
import com.servicelive.keyrotation.constants.KeyRotationConstants;
import com.servicelive.keyrotation.log.Log;
import com.servicelive.keyrotation.util.CryptographyUtil;

/**
 * @author Infosys: Jun, 2014
 */
public class SOAdditionalPaymentDataEncryption implements Runnable{

	private Connection connectionSchemaSupplierProd;
	private String oldKey;
	private String newKey;
	private String splitId;
	private boolean isGreater;
	String offset = "";

	public SOAdditionalPaymentDataEncryption(Map<String, String> keysMap,
			Connection connectionSchemaSupplierProd, String splitId, boolean isGreater) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;

		oldKey = keysMap.get(KeyRotationConstants.CCENKEY);
		newKey = keysMap.get(KeyRotationConstants.CCENKEY_TEMP);
		
		this.splitId = splitId;
		this.isGreater = isGreater;
	}

	public void run() {
		// Call DB procedure
		Log.writeLog(Level.INFO, 
				"Inside SOAdditionalDataEncryption thread..." + Thread.currentThread().getName());
		PreparedStatement statement = null;
		try {
			//int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			if(isGreater){
				offset = splitId;
			} else {
				offset = "";
			}
			int limit = Integer.valueOf(DBConstants.QUERY_LIMIT);
			int resultListSize = 0;
			ResultSet resultSet = null;

			if(isGreater){
				statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_GT);
			} else {
				statement = connectionSchemaSupplierProd
						.prepareStatement(DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_LT);
			}

			CryptographyUtil cryptographyUtil = new CryptographyUtil();
			SecretKey secretNew = cryptographyUtil.getSecretForBits(newKey,
					KeyRotationConstants.CHANGE_TO_BIT);
			SecretKey secretOld = cryptographyUtil.getSecretForBits(oldKey,
					KeyRotationConstants.CHANGE_FROM_BIT);
			// loop and get chunk data
			do {
				if(isGreater){
					statement.setString(1, offset);
					statement.setInt(2, limit);
				} else {
					statement.setString(1, offset);
					statement.setString(2, splitId);
					statement.setInt(3, limit);
				}

				Log.writeLog(Level.INFO, "SOAdditionalDataEncryption : Before Fetch : ");
				resultSet = statement.executeQuery();
				Log.writeLog(Level.INFO, "SOAdditionalDataEncryption : After Fetch : ");

				// for each,
				resultListSize = encryptDecryptAdditionalData(resultSet, secretOld, secretNew);

				//offset = offset + limit;
				Log.writeLog(Level.INFO,
						"SOAdditionalDataEncryption : Fetch size : "
								+ resultListSize +" : Offset : "
								+ offset);
				connectionSchemaSupplierProd.commit();
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,
					"Exiting SOAdditionalDataEncryption thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exiting SOAdditionalDataEncryption thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	private int encryptDecryptAdditionalData(ResultSet resultSet, SecretKey secretOld, SecretKey secretNew)
			throws Exception {
		String originalAdditionalString = null;
		String encryptedAdditionalData = null;
		PreparedStatement updateStatement = connectionSchemaSupplierProd
				.prepareStatement(DBConstants.UPDATE_NEW_DATA_SO_ADDITIONAL_PAYMENT);

		CryptographyUtil crypto = new CryptographyUtil();
		int rows = 0;
		boolean isRowUpdated = false;

		while (resultSet.next()) {
			// 1. decrypt using old key and encrypt using new key
			if (null != resultSet.getString(2)) {
				try {
//					originalAdditionalString = crypto.decryptKey(
//							resultSet.getString(2), oldKey,
//							KeyRotationConstants.CHANGE_FROM_BIT);
					
					originalAdditionalString = crypto.decryptKey(
							resultSet.getString(2), secretOld);
					
//					encryptedAdditionalData = crypto.encryptKey(
//							originalAdditionalString, newKey,
//							KeyRotationConstants.CHANGE_TO_BIT);
					
					encryptedAdditionalData = crypto.encryptKey(
							originalAdditionalString, secretNew);

					// 2. update actual table
					updateStatement.setString(1, encryptedAdditionalData);
					updateStatement.setString(2, resultSet.getString(1));

					// Added for batch execution
					updateStatement.addBatch();
					isRowUpdated = true;
					// updateStatement.executeUpdate();
				} catch (Exception e) {
					Log.writeLog(Level.SEVERE,
							"Could not convert row in SOAdditionalDataEncryption with so_id : "
									+ resultSet.getString(1) + " -****** ERROR *****-");
					//throw e;
				}
			}
			offset = resultSet.getString(1);
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
	
	public boolean isGreater() {
		return isGreater;
	}

	public void setGreater(boolean isGreater) {
		this.isGreater = isGreater;
	}

	public String getSplitId() {
		return splitId;
	}

	public void setSplitId(String splitId) {
		this.splitId = splitId;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

}
