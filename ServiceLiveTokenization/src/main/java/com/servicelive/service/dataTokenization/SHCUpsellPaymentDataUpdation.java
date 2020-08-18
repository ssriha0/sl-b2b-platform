package com.servicelive.service.dataTokenization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.constants.DBConstants;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys: Jun, 2014
 */
public class SHCUpsellPaymentDataUpdation implements Runnable {

	private Connection connectionSchemaSupplierProd;
	long offset = 0l;
	

	public SHCUpsellPaymentDataUpdation(Connection connectionSchemaSupplierProd) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
        
	}

	public void run() { 
		// Call DB procedure
		Log.writeLog(Level.INFO, "Inside SHCUpsellPaymentDataUpdation thread...");
		PreparedStatement statement = null; 
		try {
			//int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			int limit =Integer.valueOf(DBConstants.QUERY_LIMIT);
			int resultListSize = 0;  
			ResultSet resultSet = null;
			statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.QUERY_CHUNK_SHC_UPSELL_PAYMENT_DATA_UPDATE);
            do {
				statement.setLong(1, offset);
				statement.setInt(2, limit);
                resultSet = statement.executeQuery();
				// for each,
				resultListSize = updateCreditCardData(resultSet,limit); 
                //offset = offset + limit;
				Log.writeLog(Level.INFO,"SHCUpsellPaymentDataDataEncryption : Fetch size : "+ resultListSize +" : Offset : "+ offset);
				
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,"Exiting SHCUpsellPaymentDataUpdation thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,"Exiting SHCUpsellPaymentDataUpdation thread with exception..."+ e);
			e.printStackTrace();
		}
	}
	// update shc_upsell_payment table with token and masked account number
	private int updateCreditCardData(ResultSet resultSet, int limit) throws Exception {
		PreparedStatement updateStatement = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_NEW_SHC_UPSELL_PAYMENT_DATA);
		//update db_update_status flag as UPDATED
		PreparedStatement updateStatementDBUpdateStatusSuccess = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_DB_UPDATE_STATUS_SHC_UPSELL_PAYMENT);
		//update db_update_status flag as ERROR
		PreparedStatement updateStatementDBUpdateStatusError = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_DB_UPDATE_STATUS_SHC_UPSELL_PAYMENT_ERROR);
		long dbUpdateOffset = offset;
		int rows = 0; 
		boolean isRowUpdated = false;
		while (resultSet.next()) {
			if (null != resultSet.getString(1)) {
				try {
                       updateStatement.setString(1, resultSet.getString(2));
    				   updateStatement.setString(2, resultSet.getString(3));
    				   updateStatement.setString(3, resultSet.getString(1));
    				   // Added for batch execution
    				   updateStatement.addBatch();
    				   isRowUpdated = true;  
                       // updateStatement.executeUpdate();
				} catch (Exception e) {
					Log.writeLog(Level.SEVERE,"Could not convert row in updateCreditCardData with tokenization id : "+ resultSet.getLong(4) + " -****** ERROR *****-");
					
				}
			}
			offset = resultSet.getLong(4);
			rows = rows + 1;
		}
		if (isRowUpdated) {
			try{
			   //Added for batch execution
				Log.writeLog(Level.INFO,"Updating shc_upsell_payment successfully from row : "+dbUpdateOffset);
				int[] updateCount =updateStatement.executeBatch();
				Log.writeLog(Level.INFO,"Updated shc_upsell_payment successfully for the records"+ updateCount.length);
				updateStatementDBUpdateStatusSuccess.setLong(1, dbUpdateOffset);
				updateStatementDBUpdateStatusSuccess.setInt(2, limit);
				Log.writeLog(Level.INFO,"Updating credit_card_tokenization successfully from row : "+dbUpdateOffset);
				int updateCredit =updateStatementDBUpdateStatusSuccess.executeUpdate();
				Log.writeLog(Level.INFO,"Updated credit_card_tokenization successfully for records : "+updateCredit);
			}catch (Exception e) {
				//updating db_status_update as error in case of exception 
				updateStatementDBUpdateStatusError.setLong(1, dbUpdateOffset);
				updateStatementDBUpdateStatusError.setInt(2, limit);
				Log.writeLog(Level.INFO,"Updating shc_upsell_payment for exception from row : "+dbUpdateOffset);
				int updateCredit= updateStatementDBUpdateStatusError.executeUpdate();
				Log.writeLog(Level.INFO,"Updated credit_card_tokenization for error for records : "+updateCredit);
				Log.writeLog(Level.SEVERE,"Exception in updateCreditCardData while updating shc_upsell_paymentfrom row : "+dbUpdateOffset + " -****** ERROR *****-");
				//throw e;
			}
		}
		return rows;
	}

	
	
	
	

}
