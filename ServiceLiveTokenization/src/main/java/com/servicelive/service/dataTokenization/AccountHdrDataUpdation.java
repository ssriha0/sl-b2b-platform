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
public class AccountHdrDataUpdation implements Runnable {

	private Connection connectionSchemaSupplierProd;
	private Connection connectionSchemaAccountsProd;
	long offset = 0l;
	

	public AccountHdrDataUpdation(Connection connectionSchemaSupplierProd,Connection connectionSchemaAccountsProd) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
		this.connectionSchemaAccountsProd = connectionSchemaAccountsProd;

	}

	public void run() { 
		
		Log.writeLog(Level.INFO, "Inside AccountHdrDataUpdation thread...");
		PreparedStatement statement = null;
		try {
			//int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			int limit =Integer.valueOf(DBConstants.QUERY_LIMIT);
			int resultListSize = 0;  
			ResultSet resultSet = null;
			statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.QUERY_CHUNK_ACCOUNT_HDR_DATA_UPDATE);
			do {
				statement.setLong(1, offset);
				statement.setInt(2, limit);
                resultSet = statement.executeQuery();
				// for each,
				resultListSize = updateCreditCardData(resultSet,limit); 
				Log.writeLog(Level.INFO,"AccountHdrDataEncryption : Fetch size : "+ resultListSize +" : Offset : "+ offset);
				
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,"Exiting AccountHdrDataEncryption thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,"Exiting AccountHdrDataEncryption thread with exception..."+ e);
			e.printStackTrace();
		}
	}
  // update account_hdr table with token and masked account number
	private int updateCreditCardData(ResultSet resultSet, int limit) throws Exception {

		PreparedStatement updateStatement = connectionSchemaAccountsProd.prepareStatement(DBConstants.UPDATE_NEW_ACCOUNT_HDR_DATA);
		//update db_update_status flag as UPDATED
		PreparedStatement updateStatementDBUpdateStatusSuccess = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_DB_UPDATE_STATUS_ACCT_HDR);
		//update db_update_status flag as ERROR
		PreparedStatement updateStatementDBUpdateStatusError = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_DB_UPDATE_STATUS_ACCT_HDR_ERROR);
		long dbUpdateOffset = offset;
		int rows = 0;
		boolean isRowUpdated = false;
		while (resultSet.next()) {
			if (null != resultSet.getString(1)) {
				try {
                       updateStatement.setString(1, resultSet.getString(2));
    				   updateStatement.setString(2, resultSet.getString(3));
    				   updateStatement.setString(3, resultSet.getString(1));
    				   updateStatement.addBatch();
    				   isRowUpdated = true; 
				} catch (Exception e) {
					Log.writeLog(Level.SEVERE,"Could not convert row in CreditCardDataEncryption with account_id : "+ resultSet.getString(1) + " -****** ERROR *****-");
					
				}
			}
			offset = resultSet.getLong(4);
			rows = rows + 1;
		}
		if (isRowUpdated) {
			try{
			    //Added for batch execution
				Log.writeLog(Level.INFO,"Updating account hdr successfully from row : "+dbUpdateOffset);
				int[] updateCount = updateStatement.executeBatch();
				Log.writeLog(Level.INFO,"Updated account hdr successfully for the records : "+ updateCount.length);
				updateStatementDBUpdateStatusSuccess.setLong(1, dbUpdateOffset);
				updateStatementDBUpdateStatusSuccess.setInt(2, limit);
		         // Added for batch execution
				Log.writeLog(Level.INFO,"Updating credit_card_tokenization successfully from row : "+dbUpdateOffset);
				int updateCountTokenization= updateStatementDBUpdateStatusSuccess.executeUpdate();
				Log.writeLog(Level.INFO,"Updated credit_card_tokenization successfully for records "+ updateCountTokenization);
                //offset = offset + limit;
			}catch (Exception e) {
				//updating db_status_update as error in case of exception 
				updateStatementDBUpdateStatusError.setLong(1, dbUpdateOffset);
				updateStatementDBUpdateStatusError.setInt(2, limit);
				Log.writeLog(Level.INFO,"Updating credit_card_tokenization for error  from row : "+dbUpdateOffset);
				int updateCountTokenization=updateStatementDBUpdateStatusError.executeUpdate();
				Log.writeLog(Level.INFO,"Updated credit_card_tokenization for error for records : "+updateCountTokenization);
				Log.writeLog(Level.SEVERE,"Exception in updateCreditCardData while updating account hdr from row : "+dbUpdateOffset+ " -****** ERROR *****-");
				e.printStackTrace();
			}
		}
		return rows;
	}

	
	

}
