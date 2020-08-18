package com.servicelive.tokenization.util;

import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;

import com.servicelive.tokenization.constants.TokenizationConstants;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class ValidationUtil {
	// validate user inputs
	// userName not blank
	// password not blank
	// passPhrase not blank
	public static void validateUserInput(String[] args) throws Exception {

		try {
			if ((args.length == Integer
					.valueOf(TokenizationConstants.USER_INPUT_VALUE_COUNT))
					|| (args.length == Integer
							.valueOf(TokenizationConstants.USER_INPUT_VALUE_COUNT_BATCH))) {
				Log.writeLog(Level.INFO,
						"All inputs received from user. args.length = "
								+ args.length);

				String userName = args[0];
				String password = args[1];
				String passPhrase = args[2];
				String batchIdentifier = null;
				if (args.length == Integer
						.valueOf(TokenizationConstants.USER_INPUT_VALUE_COUNT_BATCH)) {
					batchIdentifier = args[3];
				}

				if (StringUtils.isEmpty(userName)) {
					Log.writeLog(Level.SEVERE, "Enterprise UserId is required!");
					throw new Exception("Enterprise UserId is required!");
				}
				if (StringUtils.isEmpty(password)) {
					Log.writeLog(Level.SEVERE,
							"Enterprise Password is required!");
					throw new Exception("Enterprise Password is required!");
				}
				if (StringUtils.isEmpty(passPhrase)) {
					Log.writeLog(Level.SEVERE, "PassPhrase is required!");
					throw new Exception("PassPhrase is required!");
				}
				if (null != batchIdentifier
						&& !StringUtils.isEmpty(batchIdentifier)) {
					if (batchIdentifier.length() > 1
							|| !("01234567").contains(batchIdentifier)) {
						Log.writeLog(Level.SEVERE, "Invalid Batch Identifier!");
						throw new Exception(
								"Invalid Batch Identifier, Batch identifier should be any of 0,1,2,3,4,5,6,7");
					}
				}

			} else {
				// All inputs not received from user
				Log.writeLog(Level.SEVERE, "All Inputs not received from user!");
				throw new Exception("All Inputs not received from user!");
			}

		} catch (Exception e) {
			Log.writeLog(Level.SEVERE, "Invalid Input!!!" + e);
			throw new Exception(e.getMessage());
		}

	}

}
