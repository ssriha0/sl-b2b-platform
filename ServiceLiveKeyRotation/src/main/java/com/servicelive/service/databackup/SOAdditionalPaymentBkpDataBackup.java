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
public class SOAdditionalPaymentBkpDataBackup implements Runnable {

	private Connection connectionSchemaSupplierProd;

	public SOAdditionalPaymentBkpDataBackup(Connection connectionSchemaSupplierProd) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
	}

	public void run() {
		// Call DB procedure
		SLKeyRotationDAO dao = new SLKeyRotationDAO();
		try {
			Log.writeLog(Level.INFO,
					"Inside SOAdditionalPaymentBkpDataBackup thread...");
			dao.backUpSOAdditionalPaymentBkp(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,
					"Exiting SOAdditionalPaymentBkpDataBackup thread...");
		} catch (SQLException e) {
			Log.writeLog(Level.SEVERE,
					"Exiting SOAdditionalPaymentBkpDataBackup thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

}
