package com.servicelive.service;

import java.util.Map;
import java.util.logging.Level;
import com.mysql.jdbc.Connection;
import com.servicelive.service.dataTokenization.AccountHdrDataTokenization;
import com.servicelive.service.dataTokenization.OmsBuyerNotificationTokenization;
import com.servicelive.service.dataTokenization.SHCUpsellPaymentDataEncryption;
import com.servicelive.service.dataTokenization.SOAdditionalPaymentBkpDataEncryption;

import com.servicelive.service.dataTokenization.SOAdditionalPaymentTokenization;
import com.servicelive.tokenization.db.SLTokenizationDAO;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class DataManipulationService {

	public void manipulateData(
			Connection connectionSchemaSupplierProd,
			Connection connectionSchemaAccountsProd,
			Connection connectionSchemaIntegration,Connection connectionSchemaSlk,String batchIdentifier) throws Exception {

		try {

          /*
			// create thread for each Runnable instance
			Thread t0 = new Thread(new SOAdditionalPaymentBkpDataEncryption(
					 connectionSchemaSupplierProd));*/
			SLTokenizationDAO dao = new SLTokenizationDAO();
			String tokenizeUrl=dao.getTokenizerUrl(connectionSchemaSupplierProd); 
			System.out.println(tokenizeUrl);
			String tokenizeHeader=dao.getTokenizerHeader(connectionSchemaSupplierProd); 
			System.out.println(tokenizeHeader); 
			String encryptionKey=dao.getEncryptionKey(connectionSchemaSlk); 
			System.out.println(encryptionKey); 
			Thread t1 = new Thread(new AccountHdrDataTokenization(
					connectionSchemaAccountsProd,tokenizeUrl,tokenizeHeader,encryptionKey));
		/*	Thread t4 = new Thread(new SHCUpsellPaymentDataEncryption(
					connectionSchemaSupplierProd)); 
			*/ 
			// Need to split the below 2 threads
			
			// select count and get id of the row to split in two 
			long splitId =0l;// dao.getOmsBuyerNotificationSplitRowId(connectionSchemaIntegration);
			   
		Thread t21 = new Thread(new OmsBuyerNotificationTokenization(
				 connectionSchemaIntegration, splitId, true,encryptionKey)); 
			/*Thread t22 = new Thread(new OmsBuyerNotificationDataEncryption(
					 connectionSchemaIntegration, splitId, false));
			*/
			//SLTokenizationDAO dao = new SLTokenizationDAO();

			// Need to split the below 2 threads
			// select count and get id of the row to split in two  
			String soId ="";// dao.getSoAdditionalPaymentSplitRowId(connectionSchemaSupplierProd); 
						
			// select count and get id of the row to split in two
			Thread t31 = new Thread(new SOAdditionalPaymentTokenization(
					 connectionSchemaSupplierProd, soId, true,tokenizeUrl,tokenizeHeader,encryptionKey));	
			//Thread t32 = new Thread(new SOAdditionalPaymentDataEncryption(
				//	 connectionSchemaSupplierProd, soId, false));

			// start all the threads
		//	if(batchIdentifier.equals("0") || batchIdentifier.equals("3")){
//				t0.start();
			    t1.start();
//				t4.start();
			//	t31.start();
			//	t32.start();
		//	}
		//	if(batchIdentifier.equals("0") || batchIdentifier.equals("3") || batchIdentifier.equals("6")){
			//	t21.start();
//				t22.start();
		//	}

			// wait for all threads to finish, to proceed further
		//	if(batchIdentifier.equals("0") || batchIdentifier.equals("3")){
//				while (t0.isAlive()) {
//					t0.join();
//				}
//				
			while (t1.isAlive()) {
				t1.join();
			}
//	
//				while (t4.isAlive()) {
//					t4.join();
//				}
				
				while (t31.isAlive()) {
					t31.join();
				}
				
				//while (t32.isAlive()) {
				//	t32.join();
				//}
		//	}
			
		//	if(batchIdentifier.equals("0") || batchIdentifier.equals("3") || batchIdentifier.equals("6")){	
			//	while (t21.isAlive()) {
				//	t21.join();
				//}
//				
//				while (t22.isAlive()) {
//					t22.join();
//				}
		//	}
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
