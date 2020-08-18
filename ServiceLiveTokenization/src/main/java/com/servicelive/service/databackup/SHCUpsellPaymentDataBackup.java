package com.servicelive.service.databackup;

import java.sql.SQLException;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.db.SLTokenizationDAO;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys, Jun, 2014 BackUp Data from shc_upsell_payment to
 *         shc_upsell_payment_tmp
 * 
 */
public class SHCUpsellPaymentDataBackup implements Runnable {

	private Connection connectionSchemaSupplierProd;

	public SHCUpsellPaymentDataBackup(Connection connectionSchemaSupplierProd) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
	}

	public void run() {
		// Call DB procedure
		SLTokenizationDAO dao = new SLTokenizationDAO();
		try {
			Log.writeLog(Level.INFO,
					"Inside SHCUpsellPaymentDataBackup thread...");
			dao.backUpSHCUpsellPayment(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,
					"Exiting SHCUpsellPaymentDataBackup thread...");
		} catch (SQLException e) {
			Log.writeLog(Level.SEVERE,
					"Exiting SHCUpsellPaymentDataBackup thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

}
