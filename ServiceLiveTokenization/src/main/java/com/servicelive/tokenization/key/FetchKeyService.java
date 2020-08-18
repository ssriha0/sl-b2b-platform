/**
 * 
 */
package com.servicelive.tokenization.key;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.constants.DBConstants;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class FetchKeyService {

	public Map<String, String> getKeys(Connection connectionSchemaSlk)
			throws Exception {
		Map<String, String> keysMap = new HashMap<String, String>();

		PreparedStatement statement = null;
		try {
			// fetch the keys from database
			Log.writeLog(Level.INFO, "Fetch keys");
			statement = connectionSchemaSlk
					.prepareStatement(DBConstants.SELECT_CC_ENCRYPTION_KEYS);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				keysMap.put(rs.getString(1), rs.getString(2));
			}

			return keysMap;

		} catch (Exception e) {
			Log.writeLog(Level.SEVERE, "Exception in FetchKeyService:" + e);
			throw new Exception("Exception in FetchKeyService:" + e);
		}
	}
}
