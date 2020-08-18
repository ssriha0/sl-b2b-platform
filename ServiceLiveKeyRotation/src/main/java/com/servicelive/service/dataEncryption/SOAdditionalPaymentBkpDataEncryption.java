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
public class SOAdditionalPaymentBkpDataEncryption implements Runnable{

	private Connection connectionSchemaSupplierProd;
	private String oldKey;
	private String newKey;

	public SOAdditionalPaymentBkpDataEncryption(Map<String, String> keysMap,
			Connection connectionSchemaSupplierProd) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;

		oldKey = keysMap.get(KeyRotationConstants.CCENKEY);
		newKey = keysMap.get(KeyRotationConstants.CCENKEY_TEMP);
	}

	public void run() {
		// Call DB procedure
		Log.writeLog(Level.INFO, "Inside SOAdditionalBkpDataEncryption thread...");
		PreparedStatement statement = null;
		try {
			int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			int limit = Integer.valueOf(DBConstants.QUERY_LIMIT);
			int resultListSize = 0;
			ResultSet resultSet = null;

			statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_BKP);

			CryptographyUtil cryptographyUtil = new CryptographyUtil();
			SecretKey secretNew = cryptographyUtil.getSecretForBits(newKey,
					KeyRotationConstants.CHANGE_TO_BIT);
			SecretKey secretOld = cryptographyUtil.getSecretForBits(oldKey,
					KeyRotationConstants.CHANGE_FROM_BIT);
			// loop and get chunk data
			do {
				statement.setInt(1, limit);
				statement.setInt(2, offset);

				resultSet = statement.executeQuery();

				// for each,
				resultListSize = encryptDecryptAdditionalData(resultSet, secretOld, secretNew);

				offset = offset + limit;
				Log.writeLog(Level.INFO,
						"SOAdditionalBkpDataEncryption : Fetch size : "
								+ resultListSize +" : Offset : "
								+ offset);
				connectionSchemaSupplierProd.commit();
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,
					"Exiting SOAdditionalBkpDataEncryption thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exiting SOAdditionalBkpDataEncryption thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	private int encryptDecryptAdditionalData(ResultSet resultSet, SecretKey secretOld, SecretKey secretNew)
			throws Exception {
		String originalAdditionalString = null;
		String encryptedAdditionalData = null;
		PreparedStatement updateStatement = connectionSchemaSupplierProd
				.prepareStatement(DBConstants.UPDATE_NEW_DATA_SO_ADDITIONAL_PAYMENT_BKP);

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
							"Could not convert row in SOAdditionalBkpDataEncryption with so_id : "
									+ resultSet.getString(1) + " -****** ERROR *****-");
					//throw e;
				}
			}
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
