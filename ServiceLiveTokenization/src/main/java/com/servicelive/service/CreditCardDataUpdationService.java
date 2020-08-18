package com.servicelive.service;

import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.service.dataTokenization.AccountHdrDataUpdation;
import com.servicelive.service.dataTokenization.OMSBuyerNotificationDataUpdation;
import com.servicelive.service.dataTokenization.SHCUpsellPaymentDataUpdation;
import com.servicelive.service.dataTokenization.SoAdditionalPaymentDataUpdation;
import com.servicelive.tokenization.db.SLTokenizationDAO;
import com.servicelive.tokenization.log.Log;
 
/**
 * @author Infosys : Jun, 2014
 */
public class CreditCardDataUpdationService {

	public void manipulateData(
			Connection connectionSchemaSupplierProd,
			Connection connectionSchemaAccountsProd,
			Connection connectionSchemaIntegration) throws Exception {

			int soAdditionalPaymentCount = 0;
			int soAccountHdrCount = 0;
			int omsBuyerNotificationCount = 0;
			int shcUpsellPaymentCount = 0;
		try {
			Thread t1 = null;
			Thread t2 = null;
			Thread t31 = null;
			Thread t32 = null;
			Thread t41 = null;
			Thread t42 = null;
			SLTokenizationDAO SLdao = new SLTokenizationDAO();
			soAdditionalPaymentCount = SLdao.getSOAdditionalPaymentTokenCount(connectionSchemaSupplierProd);
			soAccountHdrCount = SLdao.getAccountHdrTokenCount(connectionSchemaSupplierProd);
			omsBuyerNotificationCount = SLdao.getOmsBuyerNotificationTokenCount(connectionSchemaSupplierProd);
			shcUpsellPaymentCount = SLdao.getShcUpsellPaymentTokenCount(connectionSchemaSupplierProd);
			
			if(soAccountHdrCount>0){
                // update account_hdr table
				t1 = new Thread(new AccountHdrDataUpdation(connectionSchemaSupplierProd,connectionSchemaAccountsProd));
			}
			if(shcUpsellPaymentCount>0){
				//update shc_upsell_payment table
				 t2 = new Thread(new SHCUpsellPaymentDataUpdation(connectionSchemaSupplierProd));
			}
			
			SLTokenizationDAO dao = new SLTokenizationDAO();
			if(soAdditionalPaymentCount>0){
				long splitId=dao.getSoAdditionalPaymentSplitRowId(connectionSchemaSupplierProd); 
				//update so_additional_payment table
				t31 = new Thread(new SoAdditionalPaymentDataUpdation( connectionSchemaSupplierProd, splitId, true));
				t32 = new Thread(new SoAdditionalPaymentDataUpdation(connectionSchemaSupplierProd, splitId, false));   
			}
			if(omsBuyerNotificationCount>0){
				 long omsSplitId=dao.getOMSBuyerNotificationSplitRowId(connectionSchemaSupplierProd);  
				 // update oms_buyer_notifications
				 t41 = new Thread(new OMSBuyerNotificationDataUpdation(connectionSchemaSupplierProd,connectionSchemaIntegration,omsSplitId, true)); 
				 t42 = new Thread(new OMSBuyerNotificationDataUpdation( connectionSchemaSupplierProd,connectionSchemaIntegration,omsSplitId, false));
			}
			if(soAccountHdrCount>0){
				t1.start();
			}
			if(shcUpsellPaymentCount>0){
				t2.start();
			}
			if(soAdditionalPaymentCount>0){
				t31.start();
				t32.start();
			}
			if(omsBuyerNotificationCount>0){
				t41.start();
				t42.start();
			}
			
			if(soAccountHdrCount>0){
				while (t1.isAlive()) {
					t1.join();
				}
			}
			if(shcUpsellPaymentCount>0){
				while (t2.isAlive()) {
					t2.join();
				}
			}
			if(soAdditionalPaymentCount>0){
					while (t31.isAlive()) {
						t31.join();
					}
				
				while (t32.isAlive()) {
					t32.join();
				}
			}
			if(omsBuyerNotificationCount>0){
				while (t41.isAlive()) {
					t41.join();
				}
				while (t42.isAlive()) {
					t42.join();
				}
			}
			Log.writeLog(Level.INFO,"Data Updation completed successfully..."); 
			return;
		} catch (InterruptedException intEx) {
			Log.writeLog(Level.SEVERE,"InterruptedException in Data Updation Service!!! "+ intEx);
			throw new Exception("InterruptedException in Data Updation Service!!! "+ intEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,"Exception in DataManipulationService!!! " + e);
			throw new Exception("Exception in DataManipulationService!!! " + e);
		} 

	}
}
