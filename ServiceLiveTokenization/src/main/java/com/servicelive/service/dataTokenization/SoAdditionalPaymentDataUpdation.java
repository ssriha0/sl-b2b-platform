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
public class SoAdditionalPaymentDataUpdation implements Runnable {

	private Connection connectionSchemaSupplierProd;

	long offset = 0l;
	private long splitId;
	private boolean isGreater;
	

	public SoAdditionalPaymentDataUpdation(Connection connectionSchemaSupplierProd,long splitId, boolean isGreater) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
		this.splitId = splitId;
		this.isGreater = isGreater;
	}

	public void run() {
		Log.writeLog(Level.INFO, "Inside SoAdditionalPaymentDataUpdation thread..." + Thread.currentThread().getName());
		PreparedStatement statement = null;
		try {
			//int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			if(isGreater){
				offset = splitId; 
			} else {
				offset = 0l;
			}
			int limit = Integer.valueOf(DBConstants.QUERY_LIMIT);
			int resultListSize = 0;
			ResultSet resultSet = null;

			if(isGreater){
				statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_DATA_UPDATE_GT);
			} else {
				statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_DATA_UPDATE_LT);
			}
           //loop and get chunk data
			do {
				if(isGreater){
					statement.setLong(1, offset);
					statement.setInt(2, limit);
				} else {
					statement.setLong(1, offset);
					statement.setLong(2, splitId);
					statement.setInt(3, limit);
				}

				Log.writeLog(Level.INFO, "SoAdditionalPaymentDataUpdation : Before Fetch : ");
				resultSet = statement.executeQuery();
				Log.writeLog(Level.INFO, "SoAdditionalPaymentDataUpdation : After Fetch : ");
                // for each,
				resultListSize = updateCreditCardData(resultSet,limit);
                //offset = offset + limit;
				Log.writeLog(Level.INFO,"SoAdditionalPaymentDataUpdation : Fetch size : "+ resultListSize +" : Offset : "+ offset);
				
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,"Exiting SoAdditionalPaymentDataUpdation thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,"Exiting SoAdditionalPaymentDataUpdation thread with exception..."+ e);
			e.printStackTrace();
		}
	}
	// update so_additional_payment table with token and masked account number
	private int updateCreditCardData(ResultSet resultSet, int limit ) throws Exception {
		PreparedStatement updateStatement = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_NEW_SO_ADDITIONAL_PAYMENT_DATA);
		PreparedStatement updateStatementDBUpdateStatusSuccess;
		PreparedStatement updateStatementDBUpdateStatusError;
		if(isGreater){
			//update db_update_status flag as UPDATED
			 updateStatementDBUpdateStatusSuccess = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_GT);
			//update db_update_status flag as ERROR
			 updateStatementDBUpdateStatusError = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_ERROR_GT);
		}else{
			//update db_update_status flag as UPDATED
			 updateStatementDBUpdateStatusSuccess = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_LT);
			//update db_update_status flag as ERROR
			 updateStatementDBUpdateStatusError = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_DB_UPDATE_STATUS_SO_ADDITIONAL_PAYMENT_ERROR_LT);
		}
		long dbUpdateOffset = offset;
		int rows = 0;
		boolean isRowUpdated = false;
		while (resultSet.next()) {
			if (null != resultSet.getString(1)) {
				try {
                       updateStatement.setString(1, resultSet.getString(2));
    				   updateStatement.setString(2, resultSet.getString(3));
    				   updateStatement.setString(3, resultSet.getString(1));
                       //Added for batch execution
    				   updateStatement.addBatch();
    				   isRowUpdated = true;  
					   //updateStatement.executeUpdate();
				} catch (Exception e) {
					Log.writeLog(Level.SEVERE,"Could not convert row in updateCreditCardData with tokenization_id : "+ resultSet.getString(4) + " -****** ERROR *****-");
					
				}
			}
			offset = resultSet.getLong(4);
			rows = rows + 1;
		}
		if (isRowUpdated) {
			try{
			   //Added for batch execution
				Log.writeLog(Level.INFO,"Updating so_additional_payment successfully from row : "+dbUpdateOffset);
				int[] updateCount = updateStatement.executeBatch();
				Log.writeLog(Level.INFO,"Updated so_additional_payment successfully for the records"+ updateCount.length);
				if(isGreater){
					updateStatementDBUpdateStatusSuccess.setLong(1, dbUpdateOffset);
 				    updateStatementDBUpdateStatusSuccess.setInt(2, limit);
				}else{
					updateStatementDBUpdateStatusSuccess.setLong(1, dbUpdateOffset);
 				    updateStatementDBUpdateStatusSuccess.setLong(2, splitId);
 				    updateStatementDBUpdateStatusSuccess.setInt(3, limit);
				}
				Log.writeLog(Level.INFO,"Updating credit_card_tokenization successfully from row : "+dbUpdateOffset);
				int updateCredit =updateStatementDBUpdateStatusSuccess.executeUpdate();
				Log.writeLog(Level.INFO,"Updated credit_card_tokenization successfully for records : "+updateCredit);
			}catch (Exception e) {
				//updating db_status_update as error in case of exception 
				if(isGreater){
					updateStatementDBUpdateStatusError.setLong(1, dbUpdateOffset);
 				    updateStatementDBUpdateStatusError.setInt(2, limit);
				}else{
					updateStatementDBUpdateStatusError.setLong(1, dbUpdateOffset);
 				    updateStatementDBUpdateStatusError.setLong(2, splitId);
 				    updateStatementDBUpdateStatusError.setInt(3, limit);
				}
				Log.writeLog(Level.INFO,"Updating credit_card_tokenization for errors from row : "+dbUpdateOffset);
				int updateCredit=updateStatementDBUpdateStatusError.executeUpdate();
				Log.writeLog(Level.INFO,"Updated credit_card_tokenization with  error for records : "+updateCredit);
				Log.writeLog(Level.SEVERE,"Exception in updateCreditCardData while updating so_additional_payment  from row : "+offset + " -****** ERROR *****-");
				//throw e;
			}
		}
		return rows;
	}

	
	public long getSplitId() {
		return splitId;
	}

	public void setSplitId(long splitId) {
		this.splitId = splitId;
	}

	public boolean isGreater() {
		return isGreater;
	}

	public void setGreater(boolean isGreater) {
		this.isGreater = isGreater;
	}

	
	
	

}
