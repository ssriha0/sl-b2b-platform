/**
 * 
 */
package com.servicelive.tokenization.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.servicelive.service.dataTokenization.CommonConstants;
import com.servicelive.tokenization.constants.DBConstants;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class SLTokenizationDAO {

	

	public void backUpAccountHdr(Connection connectionSchemaAccountsProd)
			throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaAccountsProd
					.prepareStatement(DBConstants.QUERY_BACKUP_ACCOUNT_HDR_TMP);
			statement.executeQuery();
			Log.writeLog(Level.INFO, "Data backup account_hdr_tmp success...");
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while backUpAccountHdr...");
			sqEx.printStackTrace();
			connectionSchemaAccountsProd.rollback();
			throw new SQLException(
					"SQL Exception occured while backUpAccountHdr..." + sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while backUpAccountHdr...");
			e.printStackTrace();
			connectionSchemaAccountsProd.rollback();
			throw new SQLException(
					"Exception occured while backUpAccountHdr..." + e);
		}
	}

	public void backUpOmsBuyerNotification(
			Connection connectionSchemaIntegration) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaIntegration
					.prepareStatement(DBConstants.QUERY_BACKUP_OMS_BUYER_NOTIFICATION_TMP);
			statement.executeQuery();
			Log.writeLog(Level.INFO,
					"Data backup oms_buyer_notifications_tmp success...");
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while backUpOmsBuyerNotification...");
			sqEx.printStackTrace();
			connectionSchemaIntegration.rollback();
			throw new SQLException(
					"SQL Exception occured while backUpOmsBuyerNotification..."
							+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while backUpOmsBuyerNotification...");
			e.printStackTrace();
			connectionSchemaIntegration.rollback();
			throw new SQLException(
					"Exception occured while backUpOmsBuyerNotification..." + e);
		}
	}

	public void backUpSOAdditionalPayment(
			Connection connectionSchemaSupplierProd) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_BACKUP_SO_ADDITIONAL_PAYMENT_TMP);
			statement.executeQuery();
			Log.writeLog(Level.INFO,
					"Data backup so_additional_payment_tmp success...");
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while backUpSOAdditionalPayment...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"SQL Exception occured while backUpSOAdditionalPayment..."
							+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while backUpSOAdditionalPayment...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"Exception occured while backUpSOAdditionalPayment..." + e);
		}
	}
	
	public void backUpSOAdditionalPaymentBkp(
			Connection connectionSchemaSupplierProd) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_BACKUP_SO_ADDITIONAL_PAYMENT_BKP_TMP);
			statement.executeQuery();
			Log.writeLog(Level.INFO,
					"Data backup so_additional_payment_bkp success...");
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while backUpSOAdditionalPaymentBkp...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"SQL Exception occured while backUpSOAdditionalPaymentBkp..."
							+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while backUpSOAdditionalPaymentBkp...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"Exception occured while backUpSOAdditionalPaymentBkp..." + e);
		}
	}

	public void backUpSHCUpsellPayment(Connection connectionSchemaSupplierProd)
			throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_BACKUP_SHC_UPSELL_PAYMENT_TMP);
			statement.executeQuery();
			Log.writeLog(Level.INFO,
					"Data backup shc_upsell_payment_tmp success...");
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while backUpSHCUpsellPayment...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"SQL Exception occured while backUpSHCUpsellPayment..."
							+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while backUpSHCUpsellPayment...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"Exception occured while backUpSHCUpsellPayment..." + e);
		}
	}
	 
	// get the offset corresponding to oms_buyer_notification table
		public long getOMSBuyerNotificationSplitRowId(Connection connectionSchemaSupplierProd) throws Exception{
			PreparedStatement statement = null;
			try {
				statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.COUNT_OMS_BUYER_NOTIFICATIONS); 
				ResultSet rs = statement.executeQuery(); 
				rs.next();
				long totalRow = rs.getLong(1);
				long splitRow = totalRow / 2; 
				
				Log.writeLog(Level.INFO,
						"Split Row Count..." + splitRow + " .... Total Row ... "+totalRow);
				statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.FETCH_OMS_BUYER_NOTIFICATION_SPLITID);
				statement.setLong(1, splitRow);
				
				ResultSet rs1 = statement.executeQuery();
				rs1.next();
				Log.writeLog(Level.INFO,
						"Split Row Id.." + rs1.getString(1));
				return rs1.getLong(1);
			} catch (Exception e){
				Log.writeLog(Level.SEVERE,
						"Exception occured while getOMSBuyerNotificationSplitRowId...");
				e.printStackTrace();
				throw new Exception(
						"Exception occured while getOMSBuyerNotificationSplitRowId..." + e);
			}
		}
		
		// get the offset corresponding to so_additional_payment table
	public long getSoAdditionalPaymentSplitRowId(Connection connectionSchemaSupplierProd) throws Exception{
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.COUNT_SO_ADDITIONAL_PAYMENT); 
			ResultSet rs = statement.executeQuery(); 
			rs.next();
			long totalRow = rs.getLong(1);
			long splitRow = totalRow / 2; 
			
			Log.writeLog(Level.INFO,
					"Split Row Count..." + splitRow + " .... Total Row ... "+totalRow);
			
			statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.FETCH_SO_ADDITIONAL_PAYMENT_SPLITID);
			statement.setLong(1, splitRow);
			
			ResultSet rs1 = statement.executeQuery();
			rs1.next();
			Log.writeLog(Level.INFO,
					"Split Row Id.." + rs1.getString(1));
			return rs1.getLong(1);
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getSoAdditionalPaymentSplitRowId...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getSoAdditionalPaymentSplitRowId..." + e);
		}
	}
	
	public int getSOAdditionalPaymentTokenCount(Connection connectionSchemaSupplierProd) throws Exception{
		Statement statement = null;
		try {
			
			statement = (Statement) connectionSchemaSupplierProd.createStatement();
			ResultSet rs1 = statement.executeQuery(DBConstants.QUERY_TOKEN_COUNT_SO_ADDITIONAL_PAYMENT);
			while (rs1.next()) {
			Log.writeLog(Level.INFO, 
					"soAdditionalPaymentCount.." + rs1.getInt(1));
			return rs1.getInt(1);
			}
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getSOAdditionalPaymentTokenCount...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getSOAdditionalPaymentTokenCount..." + e);
		}
		return 0;
	}
	
	public int getAccountHdrTokenCount(Connection connectionSchemaSupplierProd) throws Exception{
		Statement statement = null;
		try {
			
			statement = (Statement) connectionSchemaSupplierProd.createStatement();
			ResultSet rs1 = statement.executeQuery(DBConstants.QUERY_TOKEN_COUNT_ACCOUNT_HDR);
			while (rs1.next()) {
			Log.writeLog(Level.INFO, 
					"Account hdr count.." + rs1.getInt(1));
			return rs1.getInt(1);
			}
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getAccountHdrTokenCount...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getAccountHdrTokenCount..." + e);
		}
		return 0;
	}
	
	public int getOmsBuyerNotificationTokenCount(Connection connectionSchemaSupplierProd) throws Exception{
		Statement statement = null;
		try {
			
			statement = (Statement) connectionSchemaSupplierProd.createStatement();
			ResultSet rs1 = statement.executeQuery(DBConstants.QUERY_TOKEN_COUNT_OMS_BUYER_NOTIFICATION);
			while (rs1.next()) {
			Log.writeLog(Level.INFO, 
					"omsBuyerNotificationCount.." + rs1.getInt(1));
			return rs1.getInt(1);
			}
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getOmsBuyerNotificationTokenCount...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getOmsBuyerNotificationTokenCount..." + e);
		}
		return 0;
	}
	
	public int getShcUpsellPaymentTokenCount(Connection connectionSchemaSupplierProd) throws Exception{
		Statement statement = null;
		try {
			
			statement = (Statement) connectionSchemaSupplierProd.createStatement();
			ResultSet rs1 = statement.executeQuery(DBConstants.QUERY_TOKEN_COUNT_SHC_UPSELL_PAYMENT);
			while (rs1.next()) {
			Log.writeLog(Level.INFO, 
					"shcupsellPaymentCount.." + rs1.getInt(1));
			return rs1.getInt(1);
			}
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getShcUpsellPaymentTokenCount...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getShcUpsellPaymentTokenCount..." + e);
		}
		return 0;
	}
	
	
	public String getTokenizerUrl(Connection connectionSchemaSupplierProd) throws Exception{
		Statement statement = null;
		try {
			
			statement = (Statement) connectionSchemaSupplierProd.createStatement();
			ResultSet rs1 = statement.executeQuery(DBConstants.FETCH_TOKENIZER_URL);
			while (rs1.next()) {
			Log.writeLog(Level.INFO, 
					"Split Row Id.." + rs1.getString(1));
			return rs1.getString(1);
			}
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getSoAdditionalPaymentSplitRowId...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getSoAdditionalPaymentSplitRowId..." + e);
		}
		return null;  
	}
	
	
	public String getTokenizerHeader(Connection connectionSchemaSupplierProd) throws Exception{
		Statement statement = null;
		try {
			
			statement = (Statement) connectionSchemaSupplierProd.createStatement(); 
			ResultSet rs1 = statement.executeQuery(DBConstants.FETCH_TOKENIZER_HEADER);
			while (rs1.next()) {
			Log.writeLog(Level.INFO, 
					"Split Row Id.." + rs1.getString(1));
			return rs1.getString(1);
			}
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getSoAdditionalPaymentSplitRowId...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getSoAdditionalPaymentSplitRowId..." + e);
		}
		return null;  
	}
	
	
	
	public String getEncryptionKey(Connection connectionSchemaSlk) throws Exception{
		Statement statement = null;
		try {
			
			statement = (Statement) connectionSchemaSlk.createStatement(); 
			ResultSet rs1 = statement.executeQuery(DBConstants.FETCH_ENCRYPTION_KEY); 
			while (rs1.next()) {
			Log.writeLog(Level.INFO, 
					"Split Row Id.." + rs1.getString(1));
			return rs1.getString(1);
			}
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getSoAdditionalPaymentSplitRowId...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getSoAdditionalPaymentSplitRowId..." + e);
		}
		return null;  
	}
	
	
	public void backUpAccountHdrCreditCardData(
			Connection connectionSchemaSupplierProd) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_BACKUP_ACCOUNT_HDR_TMP);
			statement.executeQuery();
			Log.writeLog(Level.INFO,
					"Data backup account_hdr success...");
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while backUpAccountHdrCreditCardData...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"SQL Exception occured while backUpAccountHdrCreditCardData..."
							+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while backUpAccountHdrCreditCardData...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"Exception occured while backUpAccountHdrCreditCardData..." + e);
		}
	}
	
	
	
	
	public void backUpSoAdditionalPaymentCreditCardData(
			Connection connectionSchemaSupplierProd) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_BACKUP_SO_ADDITIONAL_PAYMENT_TMP);
			statement.executeQuery();
			Log.writeLog(Level.INFO,
					"Data backup so_additional_payment success...");
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while backUpSoAdditionalPaymentData...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"SQL Exception occured while backUpSoAdditionalPaymentData..."
							+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while backUpSoAdditionalPaymentData...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"Exception occured while backUpSoAdditionalPaymentData..." + e);
		}
	}
	
	public void backUpOMSBuyerNotificationsCreditCardData(
			Connection connectionSchemaSupplierProd) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_BACKUP_OMS_BUYER_NOTIFICATION_TMP);
			statement.executeQuery();
			Log.writeLog(Level.INFO,
					"Data backup oms_buyer_notification success...");
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while backUpOMSBuyerNotificationsData...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"SQL Exception occured while backUpOMSBuyerNotificationsData..."
							+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while backUpOMSBuyerNotificationsData...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"Exception occured while backUpOMSBuyerNotificationsData..." + e);
		}
	}
	
	
	
	public void backUpSHCUpSellPaymentCreditCardData(
			Connection connectionSchemaSupplierProd) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_BACKUP_SHC_UPSELL_PAYMENT_TMP);
			statement.executeQuery();
			Log.writeLog(Level.INFO,
					"Data backup oms_buyer_notification success...");
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while backUpSHCPaymentSellData...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"SQL Exception occured while backUpSHCPaymentSellData..."
							+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while backUpSHCPaymentSellData...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException(
					"Exception occured while backUpSHCPaymentSellData..." + e);
		}
	}
	
	public int getTotalUniqueCcNoCount(Connection connectionSchemaSupplierProd) throws SQLException{
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.QUERY_TOTAL_COUNT_CREDIT_CARDS);
			ResultSet rs = statement.executeQuery();
			while (rs.next()){
				Log.writeLog(Level.INFO, "unique cc count.." + rs.getInt(1));
				return rs.getInt(1);
			}
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,"SQL Exception occured while Retrieving unique cc count for result null...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException("SQL Exception occured while Retrieving unique cc count for result null..."+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,"Exception occured while Retrieving unique cc count for result null...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException("Exception occured while  Retrieving unique cc count for result null..." + e);
		}
		return 0;
	}

	public long getThreadTokenizationId(Connection connectionSchemaSupplierProd,long t1TokenizationId,long chunkSize) throws SQLException {
		long tokenizationId = Long.valueOf(DBConstants.T1_TOKENIZATION_ID);
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.FETCH_CHUNK_TOKENIZED_PRIMARY_KEY);
			statement.setLong(1,t1TokenizationId);
			statement.setLong(2,new Long(String.valueOf(chunkSize)));
			statement.setLong(3,new Long(String.valueOf(DBConstants.QUERY_OFFSET)));
			ResultSet rs = statement.executeQuery();
			while (rs.next()){
				Log.writeLog(Level.INFO, "servicelive tokenization id for the thread" + rs.getInt(1));
				return rs.getLong(1);
			}
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,"SQL Exception occured while Retrieving getThreadTokenizationId ...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException("SQL Exception occured while Retrieving getThreadTokenizationId..."+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,"Exception occured while Retrieving getThreadTokenizationId...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException("Exception occured while  Retrieving getThreadTokenizationId..." + e);
		}
		return tokenizationId;
	}
 
	public long getlastTokenizationId(Connection connectionSchemaSupplierProd) throws SQLException {
		long tokenizationId = Long.valueOf(DBConstants.T1_TOKENIZATION_ID);
		PreparedStatement statement = null;
		try {
			statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.QUERY_FETCH_LAST_TOKENIZATION_ID);
			statement.setLong(1,new Long(String.valueOf(DBConstants.QUERY_OFFSET)));
			ResultSet rs = statement.executeQuery();
			while (rs.next()){
				Log.writeLog(Level.INFO, "servicelive tokenization id for the last thread" + rs.getLong(1));
				return rs.getLong(1);
			}
		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,"SQL Exception occured while Retrieving last tokenization id ...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException("SQL Exception occured while Retrieving last tokenization id..."+ sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,"Exception occured while Retrieving last tokenization id...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException("Exception occured while  Retrieving last tokenization id..." + e);
		}
		return tokenizationId;
	}
	public String getSlStoreNo(Connection connectionSchemaSupplierProd) throws SQLException{
		PreparedStatement statement = null;
		String SlStoreNo = CommonConstants.SL_STORE_NO;
		try{
		statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.QUERY_FETCH_SL_STORE_NO);
		statement.setString(1,SlStoreNo);
		ResultSet rs = statement.executeQuery();
		while (rs.next()){
			Log.writeLog(Level.INFO, "SL Store No from application properties" + rs.getString(1));
			return rs.getString(1);
		}
		
	   } catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,"SQL Exception occured while Retrieving last tokenization id ...");
			sqEx.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException("SQL Exception occured while Retrieving last tokenization id..."+ sqEx);
	    } catch (Exception e) {
			Log.writeLog(Level.SEVERE,"Exception occured while Retrieving last tokenization id...");
			e.printStackTrace();
			connectionSchemaSupplierProd.rollback();
			throw new SQLException("Exception occured while  Retrieving last tokenization id..." + e);
	  }
		return SlStoreNo;
		
	}
}