package compare;

import java.security.spec.KeySpec;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.constants.TokenizationConstants;
import com.servicelive.tokenization.db.DBConnection;
import com.servicelive.tokenization.key.FetchKeyService;
import com.servicelive.tokenization.log.Log;
import com.servicelive.tokenization.util.CryptographyUtil;
import com.servicelive.tokenization.util.PropertyUtil;

public class Compare {

	private Connection connectionSchemaSupplierProd = null;
	private Connection connectionSchemaAccountsProd = null;
	private Connection connectionSchemaSlk = null;
	private Connection connectionSchemaIntegration = null;
	private String oldKey;
	private String newKey;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Compare dataCompare = new Compare();
		try {
			// load the properties from the properties files
			final PropertyUtil properties = new PropertyUtil();
			Log.writeLog(Level.INFO, "Loading Property values...");
			properties.loadProperties();
			Log.writeLog(Level.INFO, "Property values loaded successfully...");
			// initialize connection object

			DBConnection dbConnection = new DBConnection();
			Log.writeLog(Level.INFO, "Initialize DB Connections...");
			dataCompare.connectionSchemaSupplierProd = dbConnection
					.getConnectionForSupplierProd();
			dataCompare.connectionSchemaAccountsProd = dbConnection
					.getConnectionForAccountsProd();
			dataCompare.connectionSchemaSlk = dbConnection
					.getConnectionForSlk();
			dataCompare.connectionSchemaIntegration = dbConnection
					.getConnectionForIntegration();

			// set auto commit false for all the connections
			dataCompare.connectionSchemaSupplierProd.setAutoCommit(false);
			dataCompare.connectionSchemaAccountsProd.setAutoCommit(false);
			dataCompare.connectionSchemaSlk.setAutoCommit(false);
			dataCompare.connectionSchemaIntegration.setAutoCommit(false);

			Log.writeLog(Level.INFO,
					"DB Connections Initialized successfully...");

			// The map contains two values:
			// 1. newKey
			// 2. oldKey

			FetchKeyService fetchKeyService = new FetchKeyService();
			Map<String, String> keysMap = fetchKeyService
					.getKeys(dataCompare.connectionSchemaSlk);

			Log.writeLog(Level.INFO, "" + keysMap.size());

			dataCompare.newKey = keysMap.get(TokenizationConstants.CCENKEY);
			dataCompare.oldKey = keysMap.get(TokenizationConstants.CCENKEY_TEMP);

			String tableIndex = null;
			try {
				tableIndex = args[0];
			} catch (Exception e) {
				tableIndex = "1";
			}

			// decrypt, log and compare data
			dataCompare.decryptAndLogData(tableIndex);

			// After all successful operation, commit all db transactions
			Log.writeLog(Level.INFO, "DB Operations committed successfully...");
		} catch (Exception exception) {
			Log.writeLog(Level.SEVERE,
					"Exception message: " + exception.getMessage());
			Log.writeLog(Level.SEVERE,
					"Severe Exception, program will terminate!!! " + exception);

			exception.printStackTrace();
		} finally {
			// close all the connections
			try {
				if (null != dataCompare.connectionSchemaSupplierProd) {
					dataCompare.connectionSchemaSupplierProd.close();
				}
				if (null != dataCompare.connectionSchemaAccountsProd) {
					dataCompare.connectionSchemaAccountsProd.close();
				}
				if (null != dataCompare.connectionSchemaSlk) {
					dataCompare.connectionSchemaSlk.close();
				}
				if (null != dataCompare.connectionSchemaIntegration) {
					dataCompare.connectionSchemaIntegration.close();
				}
			} catch (SQLException e) {
				Log.writeLog(Level.SEVERE,
						"Error occured while connection close....");
				e.printStackTrace();
			}
			Log.writeLog(Level.INFO, " SLKeyRotation batch ran successfully...");

		}
	}

	public void decryptAndLogData(String tableIndex) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;

		SecretKey secret = null;
		if (TokenizationConstants.BIT_128
				.equals(TokenizationConstants.CHANGE_TO_BIT)) {
			SecretKeyFactory factory = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(newKey.toCharArray(),
					newKey.getBytes(), 65536, 128);
			byte[] raw = factory.generateSecret(spec).getEncoded();

			secret = new SecretKeySpec(raw, "AES");
		} else if (TokenizationConstants.BIT_64
				.equals(TokenizationConstants.CHANGE_TO_BIT)) {
			byte[] raw = new BASE64Decoder().decodeBuffer(newKey);

			secret = new SecretKeySpec(raw, "AES");
		}

		SecretKey oldSecret = null;
		if (TokenizationConstants.BIT_128
				.equals(TokenizationConstants.CHANGE_FROM_BIT)) {
			SecretKeyFactory factory = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(oldKey.toCharArray(),
					oldKey.getBytes(), 65536, 128);
			byte[] raw = factory.generateSecret(spec).getEncoded();

			oldSecret = new SecretKeySpec(raw, "AES");
		} else if (TokenizationConstants.BIT_64
				.equals(TokenizationConstants.CHANGE_FROM_BIT)) {
			byte[] raw = new BASE64Decoder().decodeBuffer(oldKey);

			oldSecret = new SecretKeySpec(raw, "AES");
		}

		int offset = 0;
		int limit = 1000;
		int resultListSize = 0;
		ResultSet resultSet = null;
		// String selectQuery = null;

		if ("1".equalsIgnoreCase(tableIndex)) {
			connection = connectionSchemaIntegration;
			statement = connection
					.prepareStatement("SELECT org.omsBuyerNotificationId, org.paymentAccountNumber AS 'originalValue', pci.paymentAccountNumber AS 'pciValue' FROM servicelive_integration.oms_buyer_notifications org LEFT JOIN servicelive_integration.oms_buyer_notifications_pci pci ON (org.omsBuyerNotificationId = pci.omsBuyerNotificationId) WHERE org.paymentAccountNumber IS NOT NULL LIMIT ? OFFSET ?");
		} else if ("2".equalsIgnoreCase(tableIndex)) {
			connection = connectionSchemaAccountsProd;
			statement = connection
					.prepareStatement("SELECT org.account_id, org.account_no AS 'originalValue', pci.account_no AS 'pciValue' FROM accounts_prod.account_hdr org LEFT JOIN accounts_prod.account_hdr_pci pci ON (org.account_id = pci.account_id) WHERE org.account_no IS NOT NULL LIMIT ? OFFSET ?");
		} else if ("3".equalsIgnoreCase(tableIndex)) {
			connection = connectionSchemaSupplierProd;
			statement = connection
					.prepareStatement("SELECT org.shc_upsell_payment_id, org.payment_acc_no AS 'originalValue', pci.payment_acc_no AS 'pciValue' FROM supplier_prod.shc_upsell_payment org LEFT JOIN supplier_prod.shc_upsell_payment_pci pci ON (org.shc_upsell_payment_id = pci.shc_upsell_payment_id) WHERE org.payment_acc_no IS NOT NULL LIMIT ? OFFSET ?");
		} else if ("4".equalsIgnoreCase(tableIndex)) {
			connection = connectionSchemaSupplierProd;
			statement = connection
					.prepareStatement("SELECT org.so_id, org.cc_no AS 'originalValue', pci.cc_no AS 'pciValue' FROM supplier_prod.so_additional_payment_bkp org LEFT JOIN supplier_prod.so_additional_payment_bkp_pci pci ON (org.so_id = pci.so_id) WHERE org.cc_no IS NOT NULL LIMIT ? OFFSET ?");
		} else if ("5".equalsIgnoreCase(tableIndex)) {
			connection = connectionSchemaSupplierProd;
			statement = connection
					.prepareStatement("SELECT org.so_id, org.cc_no AS 'originalValue', pci.cc_no AS 'pciValue' FROM supplier_prod.so_additional_payment org LEFT JOIN supplier_prod.so_additional_payment_pci pci ON (org.so_id = pci.so_id) WHERE org.cc_no IS NOT NULL LIMIT ? OFFSET ?");
		}

		// loop and get chunk data
		do {
			statement.setInt(1, limit);
			statement.setInt(2, offset);

			resultSet = statement.executeQuery();

			// for each
			resultListSize = (new Compare()).decryptAndLog(resultSet, secret,
					oldSecret);
			Log.writeLog(Level.INFO, "TableDataEncryption : Fetch size : "
					+ resultListSize);

			offset = offset + limit;
		} while (resultListSize == limit);
		Log.writeLog(Level.INFO, "Exiting tableDataEncryption thread...");
	}

	private int decryptAndLog(ResultSet resultSet, SecretKey secret,
			SecretKey oldSecret) throws Exception {
		String originalString = null;
		String originalStringPCI = null;

		CryptographyUtil crypto = new CryptographyUtil();
		int rows = 0;

		while (resultSet.next()) {
			// 1. decrypt using new key
			if (null != resultSet.getString(2)) {
				try {
					originalString = crypto.decryptKey(resultSet.getString(2),
							secret);
					originalStringPCI = null;

					if (null != resultSet.getString(3)) {
						originalStringPCI = crypto.decryptKey(
								resultSet.getString(3), oldSecret);
					}

					if (null == originalStringPCI) {
						Log.writeLog(Level.INFO, resultSet.getString(1)
								+ " -- " + originalString + " -- "
								+ " -- NEW RECORD");
					} else {
						if (originalString.equals(originalStringPCI)) {
							/*Log.writeLog(Level.INFO, resultSet.getString(1)
									+ " -- " + originalString + " -- "
									+ originalStringPCI + " -- TRUE");*/
						} else {
							Log.writeLog(Level.INFO, resultSet.getString(1)
									+ " -- " + originalString + " -- "
									+ originalStringPCI + " -- FALSE");
						}
					}
				} catch (Exception e) {
					Log.writeLog(Level.SEVERE,
							e + " : " + resultSet.getString(1));
				}
			}
			rows = rows + 1;
		}
		return rows;
	}

	public Connection getConnectionSchemaSupplierProd() {
		return connectionSchemaSupplierProd;
	}

	public void setConnectionSchemaSupplierProd(
			Connection connectionSchemaSupplierProd) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
	}

	public Connection getConnectionSchemaAccountsProd() {
		return connectionSchemaAccountsProd;
	}

	public void setConnectionSchemaAccountsProd(
			Connection connectionSchemaAccountsProd) {
		this.connectionSchemaAccountsProd = connectionSchemaAccountsProd;
	}

	public Connection getConnectionSchemaSlk() {
		return connectionSchemaSlk;
	}

	public void setConnectionSchemaSlk(Connection connectionSchemaSlk) {
		this.connectionSchemaSlk = connectionSchemaSlk;
	}

	public Connection getConnectionSchemaIntegration() {
		return connectionSchemaIntegration;
	}

	public void setConnectionSchemaIntegration(
			Connection connectionSchemaIntegration) {
		this.connectionSchemaIntegration = connectionSchemaIntegration;
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