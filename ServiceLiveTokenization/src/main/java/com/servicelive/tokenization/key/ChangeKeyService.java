/**
 * 
 */
package com.servicelive.tokenization.key;

import java.sql.PreparedStatement;
import java.util.Map;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.constants.DBConstants;
import com.servicelive.tokenization.constants.TokenizationConstants;
import com.servicelive.tokenization.log.Log;

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
					keysMap.get(TokenizationConstants.CCENKEY_TEMP));
			statement.setString(2, TokenizationConstants.CCENKEY);

			statement.executeUpdate();

			statement.setString(1, keysMap.get(TokenizationConstants.CCENKEY));
			statement.setString(2, TokenizationConstants.CCENKEY_TEMP);

			statement.executeUpdate();

		} catch (Exception e) {
			Log.writeLog(Level.SEVERE, "Exception in changeKeyService:" + e);
			throw new Exception("Exception in changeKeyService:" + e);
		}
	}

}
