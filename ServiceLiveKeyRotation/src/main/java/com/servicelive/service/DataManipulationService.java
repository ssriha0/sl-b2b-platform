package com.servicelive.service;

import java.util.Map;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.db.SLKeyRotationDAO;
import com.servicelive.keyrotation.log.Log;
import com.servicelive.service.dataEncryption.AccountHdrDataEncryption;
import com.servicelive.service.dataEncryption.OmsBuyerNotificationDataEncryption;
import com.servicelive.service.dataEncryption.SHCUpsellPaymentDataEncryption;
import com.servicelive.service.dataEncryption.SOAdditionalPaymentBkpDataEncryption;
import com.servicelive.service.dataEncryption.SOAdditionalPaymentDataEncryption;

/**
 * @author Infosys : Jun, 2014
 */
public class DataManipulationService {

	public void manipulateData(Map<String, String> keysMap,
			Connection connectionSchemaSupplierProd,
			Connection connectionSchemaAccountsProd,
			Connection connectionSchemaIntegration, String batchIdentifier) throws Exception {

		try {

			if (null == keysMap || keysMap.size() == 0) {
				throw new Exception("Encryption keys not available!");
			}

			// create thread for each Runnable instance
			Thread t0 = new Thread(new SOAdditionalPaymentBkpDataEncryption(
					keysMap, connectionSchemaSupplierProd));
			Thread t1 = new Thread(new AccountHdrDataEncryption(keysMap,
					connectionSchemaAccountsProd));
			Thread t4 = new Thread(new SHCUpsellPaymentDataEncryption(keysMap,
					connectionSchemaSupplierProd));
			
			// Need to split the below 2 threads
			SLKeyRotationDAO dao = new SLKeyRotationDAO();
			// select count and get id of the row to split in two 
			long splitId = dao.getOmsBuyerNotificationSplitRowId(connectionSchemaIntegration);
			
			Thread t21 = new Thread(new OmsBuyerNotificationDataEncryption(
					keysMap, connectionSchemaIntegration, splitId, true, batchIdentifier));
			Thread t22 = new Thread(new OmsBuyerNotificationDataEncryption(
					keysMap, connectionSchemaIntegration, splitId, false, batchIdentifier));
			
			// Need to split the below 2 threads
			// select count and get id of the row to split in two 
			String soId = dao.getSoAdditionalPaymentSplitRowId(connectionSchemaSupplierProd);
						
			// select count and get id of the row to split in two
			Thread t31 = new Thread(new SOAdditionalPaymentDataEncryption(
					keysMap, connectionSchemaSupplierProd, soId, true));	
			Thread t32 = new Thread(new SOAdditionalPaymentDataEncryption(
					keysMap, connectionSchemaSupplierProd, soId, false));

			// start all the threads
			if(batchIdentifier.equals("0") || batchIdentifier.equals("3")){
				t0.start();
				t1.start();
				t4.start();
				t31.start();
				t32.start();
			}
			if(batchIdentifier.equals("0") || batchIdentifier.equals("3") || batchIdentifier.equals("6")){
				t21.start();
				t22.start();
			}

			// wait for all threads to finish, to proceed further
			if(batchIdentifier.equals("0") || batchIdentifier.equals("3")){
				while (t0.isAlive()) {
					t0.join();
				}
				
				while (t1.isAlive()) {
					t1.join();
				}
	
				while (t4.isAlive()) {
					t4.join();
				}
				
				while (t31.isAlive()) {
					t31.join();
				}
				
				while (t32.isAlive()) {
					t32.join();
				}
			}
			
			if(batchIdentifier.equals("0") || batchIdentifier.equals("3") || batchIdentifier.equals("6")){	
				while (t21.isAlive()) {
					t21.join();
				}
				
				while (t22.isAlive()) {
					t22.join();
				}
			}
			
			Log.writeLog(Level.INFO,
					"Data encryption completed successfully...");
			return;
		} catch (InterruptedException intEx) {
			Log.writeLog(Level.SEVERE,
					"InterruptedException in DataManipulationService!!! "
							+ intEx);
			throw new Exception(
					"InterruptedException in DataManipulationService!!! "
							+ intEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception in DataManipulationService!!! " + e);
			throw new Exception("Exception in DataManipulationService!!! " + e);
		}

	}
}
