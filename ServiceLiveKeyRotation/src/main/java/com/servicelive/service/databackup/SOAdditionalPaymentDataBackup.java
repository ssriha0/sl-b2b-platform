package com.servicelive.service.databackup;

import java.sql.SQLException;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.db.SLKeyRotationDAO;
import com.servicelive.keyrotation.log.Log;

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
		SLKeyRotationDAO dao = new SLKeyRotationDAO();
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
