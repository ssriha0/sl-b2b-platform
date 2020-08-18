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
public class AccountHdrDataEncryption implements Runnable {

	private Connection connectionSchemaAccountsProd;
	private String oldKey;
	private String newKey;
	long offset = 0l;

	public AccountHdrDataEncryption(Map<String, String> keysMap,
			Connection connectionSchemaAccountsProd) {
		this.connectionSchemaAccountsProd = connectionSchemaAccountsProd;

		oldKey = keysMap.get(KeyRotationConstants.CCENKEY);
		newKey = keysMap.get(KeyRotationConstants.CCENKEY_TEMP);
	}

	public void run() {
		// Call DB procedure
		Log.writeLog(Level.INFO, "Inside AccountHdrDataEncryption thread...");
		PreparedStatement statement = null;
		try {
			//int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			offset = 0l;
			int limit = Integer.valueOf(DBConstants.QUERY_LIMIT);
			int resultListSize = 0;
			ResultSet resultSet = null;

			statement = connectionSchemaAccountsProd
					.prepareStatement(DBConstants.QUERY_CHUNK_ACCOUNT_HDR);

			CryptographyUtil cryptographyUtil = new CryptographyUtil();
			SecretKey secretNew = cryptographyUtil.getSecretForBits(newKey,
					KeyRotationConstants.CHANGE_TO_BIT);
			SecretKey secretOld = cryptographyUtil.getSecretForBits(oldKey,
					KeyRotationConstants.CHANGE_FROM_BIT);
			// loop and get chunk data
			do {
				statement.setLong(1, offset);
				statement.setInt(2, limit);

				resultSet = statement.executeQuery();

				// for each,
				resultListSize = encryptDecryptAccountData(resultSet, secretOld, secretNew);

				//offset = offset + limit;
				Log.writeLog(Level.INFO,
						"AccountHdrDataEncryption : Fetch size : "
								+ resultListSize +" : Offset : "
								+ offset);
				connectionSchemaAccountsProd.commit();
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,
					"Exiting AccountHdrDataEncryption thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exiting AccountHdrDataEncryption thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	private int encryptDecryptAccountData(ResultSet resultSet, SecretKey secretOld, SecretKey secretNew) throws Exception {
		String originalAccountString = null;
		String encryptedAccountData = null;
		PreparedStatement updateStatement = connectionSchemaAccountsProd
				.prepareStatement(DBConstants.UPDATE_NEW_DATA_ACCOUNT_HDR);

		CryptographyUtil crypto = new CryptographyUtil();
		int rows = 0;
		boolean isRowUpdated = false;

		while (resultSet.next()) {
			// 1. decrypt using old key and encrypt using new key
			if (null != resultSet.getString(2)) {
				try {
//					originalAccountString = crypto.decryptKey(
//							resultSet.getString(2), oldKey,
//							KeyRotationConstants.CHANGE_FROM_BIT);
					
					originalAccountString = crypto.decryptKey(
							resultSet.getString(2), secretOld);
					
//					encryptedAccountData = crypto.encryptKey(
//							originalAccountString, newKey,
//							KeyRotationConstants.CHANGE_TO_BIT);
					
					encryptedAccountData = crypto.encryptKey(
							originalAccountString, secretNew);

					// 2. update actual table
					updateStatement.setString(1, encryptedAccountData);
					updateStatement.setString(2, resultSet.getString(1));

					// Added for batch execution
					updateStatement.addBatch();
					isRowUpdated = true;
					// updateStatement.executeUpdate();
				} catch (Exception e) {
					Log.writeLog(Level.SEVERE,
							"Could not convert row in AccountHdrDataEncryption with account_id : "
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

}
