package com.servicelive.keyrotation.util;

import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.constants.DBConstants;
import com.servicelive.keyrotation.db.DBConnection;
import com.servicelive.keyrotation.log.Log;

public class TestDbConnection {
	public static void main(String[] args) {
		try {
			DBConstants.DB_URL = "jdbc:mysql://151.149.118.31:3307/";
			DBConstants.SCHEMA_SUPPLIER_PROD = "supplier_prod";
			DBConstants.SCHEMA_ACCOUNTS_PROD = "accounts_prod";
			DBConstants.SCHEMA_INTEGRATION = "servicelive_integration";
			DBConstants.SCHEMA_SLK = "slk";
			DBConstants.DB_USER = "supply_usr";
			DBConstants.DB_PASSWORD = "******";
			DBConstants.SLK_DB_USER = "slk_user";
			DBConstants.SLK_DB_PASSWORD = "*********";

			DBConnection dbConnection = new DBConnection();
			Log.writeLog(Level.INFO, "Initialize DB Connections...");
			Connection connectionSchemaSupplierProd = dbConnection
					.getConnectionForSupplierProd();
			System.out.println("supplier prod connection success : "
					+ connectionSchemaSupplierProd);

			Connection connectionSchemaAccountsProd = dbConnection
					.getConnectionForAccountsProd();
			System.out.println("accounts prod connection success : "
					+ connectionSchemaAccountsProd);

			Connection connectionSchemaSlk = dbConnection.getConnectionForSlk();
			System.out.println("slk connection success : "
					+ connectionSchemaSlk);

			Connection connectionSchemaIntegration = dbConnection
					.getConnectionForIntegration();
			System.out.println("servicelive integration connection success : "
					+ connectionSchemaIntegration);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
