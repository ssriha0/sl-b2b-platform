/**
 * 
 */
package com.servicelive.keyrotation.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.constants.DBConstants;
import com.servicelive.keyrotation.constants.KeyRotationConstants;
import com.servicelive.keyrotation.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class SLKeyRotationDAO {

	public void persistKey(String keyToInsert, Connection connectionSchemaSlk,
			String loggedUser) throws SQLException {
		PreparedStatement statement = null;
		try {
			Log.writeLog(Level.INFO, "Persisting key");
			statement = connectionSchemaSlk
					.prepareStatement(DBConstants.SELECT_CCENKEY_TEMP);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				// update
				Log.writeLog(Level.INFO, "Key updated by " + loggedUser);
				statement = connectionSchemaSlk
						.prepareStatement(DBConstants.UPDATE_CCENKEY_TEMP);
				//statement.setString(1, keyToInsert);
				
				StringBuilder newKey = new StringBuilder(KeyRotationConstants.CCENKEY_TEMP);
				SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyhhmmss");
				newKey.append("_").append(sdf.format(new Date()));
				
				statement.setString(1, newKey.toString());
				statement.setString(2, loggedUser);

				statement.executeUpdate();
			}
			// insert
			Log.writeLog(Level.INFO, "Key inserted by " + loggedUser);
			statement = connectionSchemaSlk
					.prepareStatement(DBConstants.INSERT_CCENKEY_TEMP);
			statement.setString(1, keyToInsert);
			statement.setString(2, loggedUser);

			statement.executeUpdate();

		} catch (SQLException sqEx) {
			Log.writeLog(Level.SEVERE,
					"SQL Exception occured while persisting key..." + sqEx);
			throw new SQLException(
					"SQL Exception occured while persisting key..." + sqEx);
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exception occured while persisting key..." + e);
			throw new SQLException("Exception occured while persisting key..."
					+ e);
		}
	}

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
	
	public long getOmsBuyerNotificationSplitRowId(Connection connectionSchemaIntegration) throws Exception{
		PreparedStatement statement = null;
		try {
			StringBuilder queryString = new StringBuilder(DBConstants.COUNT_OMS_BUYER_NOTIFICATIONS);
			if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
					!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
				queryString.append(" ").append(DBConstants.QUERY_CHUNK_OMS_BUYER_WHERE);
			}
			statement = connectionSchemaIntegration.prepareStatement(queryString.toString());
			
			if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
					!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
				statement.setString(1, DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD);
				statement.setString(2, DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD);
			}
			
			ResultSet rs = statement.executeQuery();
			rs.next();
			long totalRow = rs.getLong(1);
			long splitRow = totalRow / 2;
			
			Log.writeLog(Level.INFO,
					"Split Row Count..." + splitRow + " .... Total Row ... "+totalRow);
			
			queryString = new StringBuilder(DBConstants.FETCH_OMS_BUYER_NOTIFICATION_SPLITID);
			if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
					!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
				queryString.append(" ").append(DBConstants.QUERY_CHUNK_OMS_BUYER_WHERE);
			}
			queryString.append(" ").append(DBConstants.FETCH_OMS_SPLITID_LIMIT);
			
			statement = connectionSchemaIntegration.prepareStatement(queryString.toString());
			
			if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
					!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
				statement.setString(1, DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD);
				statement.setString(2, DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD);
				statement.setLong(3, splitRow);
			} else {
				statement.setLong(1, splitRow);
			}
			
			ResultSet rs1 = statement.executeQuery();
			rs1.next();
			Log.writeLog(Level.INFO,
					"Split Row Id.." + rs1.getLong(1));
			return rs1.getLong(1);
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getOmsBuyerNotificationSplitRowId...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getOmsBuyerNotificationSplitRowId..." + e);
		}
	}
	
	public String getSoAdditionalPaymentSplitRowId(Connection connectionSchemaSupplierProd) throws Exception{
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
			return rs1.getString(1);
		} catch (Exception e){
			Log.writeLog(Level.SEVERE,
					"Exception occured while getSoAdditionalPaymentSplitRowId...");
			e.printStackTrace();
			throw new Exception(
					"Exception occured while getSoAdditionalPaymentSplitRowId..." + e);
		}
	}
}