/**
 * 
 */
package com.servicelive.service;

import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.service.databackup.AccountHdrDataBackup;
import com.servicelive.service.databackup.CreditCardDataBackup;
import com.servicelive.service.databackup.OmsBuyerNotificationDataBackup;
import com.servicelive.service.databackup.SHCUpsellPaymentDataBackup;
import com.servicelive.service.databackup.SOAdditionalPaymentBkpDataBackup;
import com.servicelive.service.databackup.SOAdditionalPaymentDataBackup;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys: Jun, 2014 The service class does the back up data in various
 *         temp tables. A separate thread is created for each temp table insert.
 *         The DB functionality for insertion is done from inside the run()
 *         method of each thread.
 * 
 */
public class CreditCardDataBackupService {

	public void backupData(Connection connectionSchemaSupplierProd) throws Exception {

		try {
			// create thread for each Runnable instance
			Thread t1 = new Thread(new CreditCardDataBackup(
					connectionSchemaSupplierProd)); 
			t1.start();			
			while (t1.isAlive()) {
				t1.join();
			}

			Log.writeLog(Level.INFO, "Data backup completed successfully...");
			return;
		} catch (InterruptedException intEx) {
			Log.writeLog(Level.SEVERE,
					"InterruptedException in DataBackupService!!! " + intEx);
			throw new Exception("InterruptedException in DataBackupService!!! "
					+ intEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE, "Exception in DataBackupService!!! " + e);
			throw new Exception("Exception in DataBackupService!!! " + e);
		}

	}
}
