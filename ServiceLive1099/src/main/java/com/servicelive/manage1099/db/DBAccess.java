/**
 * 
 */
package com.servicelive.manage1099.db;

/**
 * @author mjoshi1
 *
 */
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.servicelive.manage1099.constants.DBConstants;
import com.servicelive.manage1099.constants.EncriptionConstants;
import com.servicelive.manage1099.log.Log;
import com.servicelive.manage1099.util.CommonUtil;

public class DBAccess {
	public static final String PROPERTY_FILE_WITH_PATH = "ServiceLive1099Db.properties";
	
	private StringBuilder queryPart = null;
	private StringBuilder queryPartInner = null;
		

	/**
	 * 
	 * @param query
	 * @param buyers
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	
/*	public ResultSet read1099Data(List<String> buyers , String dBquery,  String startDate, String endDate) throws Exception {
		
		String query = replaceDatabaseNamesInQuery(dBquery); 
		
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {

			MysqlDataSource ds = new MysqlDataSource();
			ConnectionPoolMgr poolMgr = new ConnectionPoolMgr(ds, 5);
			connection = poolMgr.getConnection();
			PreparedStatement preparedStatement = createPreparedStatement(query, connection, buyers, startDate, endDate);
						
			ResultSet rs = preparedStatement.executeQuery();
						
			
			if (rs == null) {
				System.out.println("There was no data in database, for this query="+query);
				throw new Exception("There was no data in database, for this query="+query);
			}
			
			return rs;
		
		
		} catch(Exception e) {
			System.out.println("Error occured during database operation...");
			Log.writeLog( Level.SEVERE, "Error occured during database operation");
            throw new Exception("Error occured during database operation "+e);
        }
		finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();

		}
		
	}*/


	
	/**
	 * 
	 * @param query
	 * @param buyers
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	
	public ResultSet read1099Data(String buyer , String dBquery,  String startDate, String endDate) throws Exception {
		
		String query = replaceDatabaseNamesInQuery(dBquery); 
		
		//new change
		if(null == buyer){
			query = query.replaceAll("startDate", startDate);
			query = query.replaceAll("endDate", endDate);			
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {

			MysqlDataSource ds = new MysqlDataSource();
			ConnectionPoolMgr poolMgr = new ConnectionPoolMgr(ds, 5);
			connection = poolMgr.getConnection();
			PreparedStatement preparedStatement = createPreparedStatement(query, connection, buyer, startDate, endDate);
			
			Log.writeLog( Level.INFO, "Query: "+query);
			
			ResultSet rs = preparedStatement.executeQuery();
						
			
			if (rs == null) {
				System.out.println("There was no data in database, for this query="+query);
				throw new Exception("There was no data in database, for this query="+query);
			}
			
			return rs;
		
		
		} catch(Exception e) {
			System.out.println("Error occured during database operation...");
			Log.writeLog( Level.SEVERE, "Error occured during database operation");
            throw new Exception("Error occured during database operation "+e);
        }
		finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();

		}
		
	}

	
	
	
	/**
	 * This method reads the  key value is in column app_value in table application_properties, in supplier db.
	 * @return
	 * @throws Exception
	 */
	
	
	public static String readEncondingString() throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		String encodingString = "";
		String dbUrl = null;
		String dbUserName = null;
		String dbPassword = null;
		Properties properties = new Properties();
	
		String aes_decryption_key=null;
				
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			properties.load(new FileInputStream(PROPERTY_FILE_WITH_PATH));
			
			dbUrl = properties.getProperty("datasource.url");
			dbUserName = properties.getProperty("datasource.username");
			dbPassword = properties.getProperty("datasource.password");
			aes_decryption_key=properties.getProperty("AES_DECRYPTION_KEY");
			// MysqlDataSource ds = new MysqlDataSource();
			MysqlDataSource ds = new MysqlDataSource(dbUrl, dbUserName, dbPassword);
		
			ConnectionPoolMgr poolMgr = new ConnectionPoolMgr(ds, 5);
			connection = poolMgr.getConnection();
			DBConstants.SecureEncodingQUERY = DBConstants.SecureEncodingQUERY.replaceFirst("AES_DECRYPTION_KEY",aes_decryption_key);
			PreparedStatement preparedStatement  = connection.prepareStatement(DBConstants.SecureEncodingQUERY);
			preparedStatement.setString(1, EncriptionConstants.ENCRYPTION_KEY);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs == null) {
				System.out.println("There was no data in database, for this query="+DBConstants.SecureEncodingQUERY);
				Log.writeLog( Level.WARNING,"There was no data in database, for this query="+DBConstants.SecureEncodingQUERY);
			}
					
			while(rs.next()){
				
				encodingString = rs.getString(EncriptionConstants.ENCRYPTION_KEY_COLUMN);
				
			}
		
		
		} catch(Exception e) {
			Log.writeLog(Level.SEVERE, "Error occured during database operation "+e);
            throw new Exception("Error occured during database operation "+e);
        }
		finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();

		}
		return encodingString;
	}
	
	
	
	
	/**
	 * @param query
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement createPreparedStatement(String query, Connection connection ,String buyer ,  String startDate, String endDate) throws SQLException {
		
		//Log.writeLog(Level.INFO, query);
		if(buyer!=null){
			
			// Create the query string by replacing all the buyers in the where clause.
			createSubStringsForWhereClause(buyer);
		}
		
		final String finalQuery = replaceHoldersInQuery(query, startDate,endDate);
				
		LogablePreparedStatement logablePreparedStatement  = new LogablePreparedStatement(connection, finalQuery);
		
		if(DBConstants.SHOW_QUERY.equalsIgnoreCase("ON") || DBConstants.SHOW_QUERY.equalsIgnoreCase("TRUE") || DBConstants.SHOW_QUERY.equalsIgnoreCase("YES")){
			Log.writeLog(Level.INFO, logablePreparedStatement.toString());
		}
		
		return logablePreparedStatement;
	}


	
	

	/**
	 * @param query
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement createPreparedStatement(String query, Connection connection ,List<String> buyers ,  String startDate, String endDate) throws SQLException {
		
		
		if(buyers!=null && buyers.size()>0){
			
			// Create the query string by replacing all the buyers in the where clause.
			createSubStringsForWhereClause(buyers);
		}
		
		final String finalQuery = replaceHoldersInQuery(query, startDate,endDate);
				
		LogablePreparedStatement logablePreparedStatement  = new LogablePreparedStatement(connection, finalQuery);
		
		if(DBConstants.SHOW_QUERY.equalsIgnoreCase("ON") || DBConstants.SHOW_QUERY.equalsIgnoreCase("TRUE") || DBConstants.SHOW_QUERY.equalsIgnoreCase("YES")){
			//Log.writeLog(Level.INFO, logablePreparedStatement.toString());
		}
		
		return logablePreparedStatement;
	}

	
	
	
	
	/**
	 * Create the query string by replacing all the buyers in the where clause.
	 * @param buyers
	 */
	private void createSubStringsForWhereClause(List<String> buyers) {

		queryPart = new StringBuilder();
		queryPartInner = new StringBuilder();
		
		for(int i=0;i<buyers.size(); i++){
			queryPart.append(" te.originating_buyer_id = ");
			queryPart.append(buyers.get(i));
			
			
			queryPartInner.append(" te1.originating_buyer_id = ");
			queryPartInner.append(buyers.get(i));
			
			// append OR with every buyer condition in where clause
			if(i<buyers.size()-1){
				queryPart.append(" OR ");
				queryPartInner.append(" OR ");
			}
			
		}
	}

	
	/**
	 * Create the query string by replacing all the buyers in the where clause.
	 * @param buyers
	 */
	private void createSubStringsForWhereClause(String buyer) {

		queryPart = new StringBuilder();
		queryPartInner = new StringBuilder();
		
			queryPart.append(" te.originating_buyer_id = ");
			queryPart.append(buyer);
			
			queryPartInner.append(" te1.originating_buyer_id = ");
			queryPartInner.append(buyer);
			
	}

	

	/**
	 * @param query
	 * @param startDate
	 * @param endDate
	 * @param queryPart
	 * @param queryPartInner
	 * @return
	 */
	private String replaceHoldersInQuery(String query, String startDate,String endDate) {
		/*
		 * The setString functions didn't work very well in the query
		 * for PreparedStatemnts.
		 */
		String newQuery = query;
		if(null != queryPart && null != queryPartInner){
			query = query.replaceAll("#buyerListHolder", queryPart.toString());
			query = query.replaceAll("#innerBuyerListHolder", queryPartInner.toString());
			query = query.replaceAll("#startDateHolder", startDate.trim()+" 00:00:00");
			newQuery = query.replaceAll("#endDateHolder", endDate.trim()+" 23:59:59");			
		}
		
		return newQuery;
	}
	
	
	
	/**
	 * Replace teh supplier and accounts db name in query.
	 * @param query
	 * @return
	 */
	
	private static String replaceDatabaseNamesInQuery(String query) {
		
		String newQuery = new String(query);
		
		newQuery = CommonUtil.replaceAllOccurrences(query, DBConstants.DB_SUPPLIER_IN_QUERY, DBConstants.DB_SUPPLIER);
		newQuery = CommonUtil.replaceAllOccurrences(newQuery, DBConstants.DB_ACCOUNTS_IN_QUERY, DBConstants.DB_ACCOUNTS);
		
		
		return newQuery;

	}
	
	
	
	
	public static void main(String[] args) {
		try {
			//System.out.println(DBAccess.readEncondingString());
			System.out.println(replaceDatabaseNamesInQuery(DBConstants.QUERY_SO_PAYMENT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public ResultSet getW9History(String sql, String vendorIds) throws Exception {
		String query = sql.replace("?", vendorIds);
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {

			MysqlDataSource ds = new MysqlDataSource();
			ConnectionPoolMgr poolMgr = new ConnectionPoolMgr(ds, 5);
			connection = poolMgr.getConnection();
			PreparedStatement preparedStatement = new LogablePreparedStatement(connection, query);
						
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs == null) {
				System.out.println("There was no data in database, for this query="+query);
				throw new Exception("There was no data in database, for this query="+query);
			}
			
			return rs;
		
		} catch(Exception e) {
			System.out.println("Error occured during database operation...");
			Log.writeLog( Level.SEVERE, "Error occured during database operation");
            throw new Exception("Error occured during database operation "+e);
        }
		finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();

		}
	}

	
}
