package com.servicelive.service.databackup;

import java.sql.SQLException;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.db.SLTokenizationDAO;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys, Jun, 2014 BackUp Data from so_additional_payment to
 *         so_additional_payment_tmp
 * 
 */
public class SOAdditionalPaymentDataBackup implements Runnable {

	private Connection connectionSchemaSupplierProd;

	public SOAdditionalPaymentDataBackup(Connection connectionSchemaSupplierProd) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
	}

	public void run() {
		// Call DB procedure
		SLTokenizationDAO dao = new SLTokenizationDAO();
		try {
			Log.writeLog(Level.INFO,
					"Inside SOAdditionalPaymentDataBackup thread...");
			dao.backUpSOAdditionalPayment(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,
					"Exiting SOAdditionalPaymentDataBackup thread...");
		} catch (SQLException e) {
			Log.writeLog(Level.SEVERE,
					"Exiting SOAdditionalPaymentDataBackup thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

}
