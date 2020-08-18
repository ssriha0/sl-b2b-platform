package com.servicelive.tokenization.main;

import java.sql.SQLException;
import java.util.logging.Level;
 
import com.mysql.jdbc.Connection;
import com.servicelive.service.CreditCardDataManipulationService;
import com.servicelive.service.CreditCardDataUpdationService;
import com.servicelive.tokenization.db.DBConnection;
import com.servicelive.tokenization.log.Log;
import com.servicelive.tokenization.util.PropertyUtil;


public class RunTokenization {
	
	/* step1: By executing DB scripts move the credit card data from following tables into table credit_card_tokenization
		a) account_hdr.
		b) so_additional_payment.
		c) oms_buyer_notification.
		d) shc_upsell_payment.*/

	/* step2: By executing the tokenization.jar get the token and masked account number for
	   the data in table credit_card_tokenization by invoking the webservice for tokenization.(This is applicable for the records having result is NULL or ERROR)
	 */
	
	/* step3: By executing DB scripts move the data which are not in table credit_card_tokenization but are present in following tables
	    into table credit_card_tokenization
	    a) account_hdr.
		b) so_additional_payment.
		c) oms_buyer_notification.
		d) shc_upsell_payment.
	 */
	 
	/* step4: By executing the tokenization.jar update the token and masked account number of the following tables from table credit_card_tokenization 
	     a) account_hdr.
	     b) so_additional_payment.
	     c) oms_buyer_notification.
	     d) shc_upsell_payment.
	 */
	// The following modes are available in the module
	// mode1: step2
	// mode2: step2 and step4 
	// mode3: step4
	 
	
	
	
	public static void main(String[] args) {
		Connection connectionSchemaSupplierProd = null;
		Connection connectionSchemaAccountsProd = null;
		Connection connectionSchemaSlk = null;
		Connection connectionSchemaIntegration = null;
		String batchIdentifier = null;

		try {
			// load the properties from the properties files
			//final PropertyUtilCustomized properties = new PropertyUtilCustomized();
			final PropertyUtil properties = new PropertyUtil();
			Log.writeLog( Level.INFO, "Loading Property values..."); 
			properties.loadProperties();
			Log.writeLog( Level.INFO, "Property values loaded successfully..."); 
			// initialize connection objects
			
			DBConnection dbConnection = new DBConnection();
			Log.writeLog( Level.INFO, "Initialize DB Connections...");
			connectionSchemaSupplierProd = dbConnection.getConnectionForSupplierProd(); 
			connectionSchemaSlk = dbConnection.getConnectionForSlk();
			if(null==args || args.length==0 ){
				batchIdentifier="1";	
			}else{
				batchIdentifier = args[0];

			}
			
			if (batchIdentifier.equals("2") || batchIdentifier.equals("3")){
                connectionSchemaAccountsProd = dbConnection.getConnectionForAccountsProd();
				connectionSchemaIntegration = dbConnection.getConnectionForIntegration();
				connectionSchemaIntegration.setAutoCommit(false);
			}
			 Log.writeLog( Level.INFO, "DB Connections Initialized successfully...");
			// Tokenizing the existing credit card in credit_card_tokenization table.
			CreditCardDataManipulationService creditcardDataService = new CreditCardDataManipulationService();
			if (batchIdentifier.equals("1") || batchIdentifier.equals("2")){
				long timeInitial=System.currentTimeMillis();	
				// invoke webservice for the tokenization data	(PRE RELEASE ACTIVITY)		
				creditcardDataService.manipulateData(connectionSchemaSupplierProd,connectionSchemaSlk);
				String time=String.valueOf(System.currentTimeMillis()-timeInitial); 
				Log.writeLog( Level.INFO,"time taken to tokenize whole data "+ time);
			 } 
		    if (batchIdentifier.equals("2") || batchIdentifier.equals("3")){
			long timeInitial=System.currentTimeMillis();	
				// Update the data to the original tables (RELEASE DAY ACTIVITY)	
				CreditCardDataUpdationService creditCardDataUpdationService = new CreditCardDataUpdationService();
				creditCardDataUpdationService.manipulateData(connectionSchemaSupplierProd,connectionSchemaAccountsProd,connectionSchemaIntegration);  
				String time=String.valueOf(System.currentTimeMillis()-timeInitial); 
				Log.writeLog( Level.INFO,"time taken to update the  whole data "+ time);
			 }
			 Log.writeLog( Level.INFO, "DB Operations committed successfully...");

		} catch (Exception exception) {
			Log.writeLog( Level.SEVERE, "Exception message: " + exception.getMessage());
			Log.writeLog( Level.SEVERE, "Severe Exception, program will terminate!!! "+ exception);
            exception.printStackTrace();
       } finally {
			// close all the connections
			try {
				if (null != connectionSchemaSupplierProd) {
					connectionSchemaSupplierProd.close();
				}
				if (null != connectionSchemaAccountsProd) {
					connectionSchemaAccountsProd.close();
				}
				if (null != connectionSchemaSlk) {
					connectionSchemaSlk.close();
				}
				if (null != connectionSchemaIntegration) {
					connectionSchemaIntegration.close();
				}
			} catch (SQLException e) {
				Log.writeLog( Level.SEVERE, "Error occured while connection close....");
				e.printStackTrace();
			}
			Log.writeLog( Level.INFO, " Tokenization batch ran successfully in  mode "+ batchIdentifier);

		} 
	}

	
}
