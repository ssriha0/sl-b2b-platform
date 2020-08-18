package com.servicelive.tokenization.db;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.constants.DBConstants;

/**
 * @author Infosys : Jun, 2014
 */
public class DBConnection {

	private Connection connectionSchemaSupplierProd;
	private Connection connectionSchemaAccountsProd;
	private Connection connectionSchemaSlk;
	private Connection connectionSchemaIntegration;

	public Connection getConnectionForSupplierProd() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		if (null == connectionSchemaSupplierProd
				|| connectionSchemaSupplierProd.isClosed()) {
			connectionSchemaSupplierProd = (Connection) DriverManager
					.getConnection(DBConstants.DB_URL
							+ DBConstants.SCHEMA_SUPPLIER_PROD + "?",
							DBConstants.DB_USER, DBConstants.DB_PASSWORD);
		}
		return connectionSchemaSupplierProd;
	}

	public Connection getConnectionForAccountsProd() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		if (null == connectionSchemaAccountsProd) {
			connectionSchemaAccountsProd = (Connection) DriverManager
					.getConnection(DBConstants.DB_URL
							+ DBConstants.SCHEMA_ACCOUNTS_PROD + "?",
							DBConstants.DB_USER, DBConstants.DB_PASSWORD);
		}
		return connectionSchemaAccountsProd;
	}

	public Connection getConnectionForSlk() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		if (null == connectionSchemaSlk) {
			connectionSchemaSlk = (Connection) DriverManager.getConnection(
					DBConstants.DB_URL + DBConstants.SCHEMA_SLK + "?",
					DBConstants.SLK_DB_USER, DBConstants.SLK_DB_PASSWORD);
		}
		return connectionSchemaSlk;
	}

	public Connection getConnectionForIntegration() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		if (null == connectionSchemaIntegration) {
			connectionSchemaIntegration = (Connection) DriverManager
					.getConnection(DBConstants.DB_URL
							+ DBConstants.SCHEMA_INTEGRATION + "?",
							DBConstants.DB_USER, DBConstants.DB_PASSWORD);
		}
		return connectionSchemaIntegration;
	} 
}
