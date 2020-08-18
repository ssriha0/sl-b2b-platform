/**
 * 
 */
package com.servicelive.service;

import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.service.databackup.AccountHdrDataBackup;
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
public class DataBackupService {

	public void backupData(Connection connectionSchemaSupplierProd,
			Connection connectionSchemaAccountsProd,
			Connection connectionSchemaIntegration) throws Exception {

		try {
			// create thread for each Runnable instance
			Thread t1 = new Thread(new AccountHdrDataBackup(
					connectionSchemaAccountsProd));
			//Thread t2 = new Thread(new OmsBuyerNotificationDataBackup(
			//		connectionSchemaIntegration));
			Thread t3 = new Thread(new SOAdditionalPaymentDataBackup(
					connectionSchemaSupplierProd));
			//Thread t4 = new Thread(new SHCUpsellPaymentDataBackup(
			//		connectionSchemaSupplierProd));
			Thread t5 = new Thread(new SOAdditionalPaymentBkpDataBackup(
					connectionSchemaSupplierProd));

			// start all the threads
			t1.start();
			//t2.start();
			t3.start();
			//t4.start();
			t5.start();

			// wait for all threads to finish, to proceed further
			while (t1.isAlive()) {
				t1.join();
			}

			/*while (t2.isAlive()) {
				t2.join();
			}*/

			while (t3.isAlive()) {
				t3.join();
			}

			/*while (t4.isAlive()) {
				t4.join();
			}*/
			
			while (t5.isAlive()) {
				t5.join();
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
