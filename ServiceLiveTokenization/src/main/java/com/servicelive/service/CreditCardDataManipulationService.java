package com.servicelive.service;

import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.service.dataTokenization.CreditCardDataTokenization;
import com.servicelive.service.dataTokenization.CreditCardDataTokenizationThreaded;
import com.servicelive.tokenization.constants.DBConstants;
import com.servicelive.tokenization.db.SLTokenizationDAO;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class CreditCardDataManipulationService {

	public void manipulateData(Connection connectionSchemaSupplierProd,Connection connectionSchemaSlk) throws Exception {
		SLTokenizationDAO dao = new SLTokenizationDAO();
		try {
            // get the tokenize url
			String tokenizeUrl=dao.getTokenizerUrl(connectionSchemaSupplierProd); 
			Log.writeLog(Level.INFO,tokenizeUrl);
			// get the tokenize header
			String tokenizeHeader=dao.getTokenizerHeader(connectionSchemaSupplierProd); 
			Log.writeLog(Level.INFO,tokenizeHeader); 
			//get the encryption key to decrypt the credit card numbers
			String encryptionKey=dao.getEncryptionKey(connectionSchemaSlk); 
			//String encryptionKey="ZabgbL7yCrkwnDnOhFD4oJ4s3XrUKEOoC2P0NXDNTRY="; 
			Log.writeLog(Level.INFO,encryptionKey); 
			//get the total no of cc in credit_card_tokenization table
			long countOfCc = dao.getTotalUniqueCcNoCount(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,"Total unique count "+countOfCc); 
			String slStoreNo = dao.getSlStoreNo(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,"Sl Store No "+slStoreNo); 
			float threadCount = Float.valueOf(DBConstants.QUERY_THREAD_COUNT);
			long chunkSize  = (long) Math.ceil(countOfCc/threadCount);
			int threadVariable = new Float(threadCount).intValue();
			// This for loop will create threads based on the variable threadCount
			long tokenizationstartId = Long.valueOf(DBConstants.T1_TOKENIZATION_ID);
			long tokenizationEndId = dao.getlastTokenizationId(connectionSchemaSupplierProd);
			Thread threadArray[]= new Thread[threadVariable];
			for(int i= 0 ;i <= threadVariable-1 ;i++){
				long startId = 0L;
				long endId = 0L;
				long startEndId = 0L;
				if(i==0){
					startId = tokenizationstartId;
					endId = dao.getThreadTokenizationId(connectionSchemaSupplierProd,startId,chunkSize);
				}
				startEndId = dao.getThreadTokenizationId(connectionSchemaSupplierProd,startId,chunkSize);
				
				if(i!= 0 || i!= threadVariable-1){
					endId =  startEndId;
				}
				if(i==threadVariable-1){
					startId = startEndId;
					endId =tokenizationEndId;
				}
				threadArray[i] = new Thread(new CreditCardDataTokenizationThreaded(connectionSchemaSupplierProd,tokenizeUrl,tokenizeHeader,encryptionKey,false,slStoreNo,startId,endId));
				startId = startEndId;
			}
            for(int i=0;i <= threadVariable-1;i++){
            	threadArray[i].start();
			}
			for(int i=0;i <= threadVariable-1;i++){
				while (threadArray[i].isAlive()) {
					threadArray[i].join();
				}
			}
			Log.writeLog(Level.INFO,"Data tokenization completed successfully..."); 
			return;
		} catch (InterruptedException intEx) {
			Log.writeLog(Level.SEVERE,"InterruptedException in DataManipulationService!!! "	+ intEx);
			throw new Exception("InterruptedException in DataManipulationService!!! "+ intEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,"Exception in DataManipulationService!!! " + e);
			throw new Exception("Exception in DataManipulationService!!! " + e);
		}

	}
	
	
	
	public void manipulateAdditionalData(
			Connection connectionSchemaSupplierProd,
			Connection connectionSchemaSlk) throws Exception {

		try {
          
			SLTokenizationDAO dao = new SLTokenizationDAO();
			String tokenizeUrl=dao.getTokenizerUrl(connectionSchemaSupplierProd); 
			Log.writeLog(Level.INFO,tokenizeUrl);
			String tokenizeHeader=dao.getTokenizerHeader(connectionSchemaSupplierProd); 
			Log.writeLog(Level.INFO,tokenizeHeader); 
			String encryptionKey=dao.getEncryptionKey(connectionSchemaSlk); 
			Log.writeLog(Level.INFO,encryptionKey); 
			String slStoreNo = dao.getSlStoreNo(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,"Sl Store No "+slStoreNo); 
			Thread t1 = new Thread(new CreditCardDataTokenization(connectionSchemaSupplierProd,tokenizeUrl,tokenizeHeader,encryptionKey,slStoreNo,true));
			    t1.start();
			while (t1.isAlive()) {
				t1.join();
			}		
			Log.writeLog(Level.INFO,
					"Data tokenization completed successfully..."); 
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
