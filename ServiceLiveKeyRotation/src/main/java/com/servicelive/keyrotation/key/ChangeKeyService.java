/**
 * 
 */
package com.servicelive.keyrotation.key;

import java.sql.PreparedStatement;
import java.util.Map;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.constants.DBConstants;
import com.servicelive.keyrotation.constants.KeyRotationConstants;
import com.servicelive.keyrotation.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class ChangeKeyService {

	public void changeKeys(Map<String, String> keysMap,
			Connection connectionSchemaSlk) throws Exception {
		Log.writeLog(Level.INFO, "Swap the keys in database");

		PreparedStatement statement = null;
		try {
			// Swap the keys in database
			statement = connectionSchemaSlk
					.prepareStatement(DBConstants.SWAP_CC_ENCRYPTION_KEYS);

			statement.setString(1,
					keysMap.get(KeyRotationConstants.CCENKEY_TEMP));
			statement.setString(2, KeyRotationConstants.CCENKEY);

			statement.executeUpdate();

			statement.setString(1, keysMap.get(KeyRotationConstants.CCENKEY));
			statement.setString(2, KeyRotationConstants.CCENKEY_TEMP);

			statement.executeUpdate();

		} catch (Exception e) {
			Log.writeLog(Level.SEVERE, "Exception in changeKeyService:" + e);
			throw new Exception("Exception in changeKeyService:" + e);
		}
	}

}
