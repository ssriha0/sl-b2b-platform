package com.servicelive.ibatis.sqlchecker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * 
 * @author svanloo 
 *
 */
public class ConnectionUtil {
	private static final Logger logger = Logger.getLogger(ConnectionUtil.class);
	/**
	 * 
	 * @param url
	 * @param userName
	 * @param password
	 * @param jdbcDriver
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection(String url, String userName,	String password, String jdbcDriver) throws SQLException {
		Connection con=null;
		try {

			Class.forName(jdbcDriver);

			if (url == null) {
				throw new RuntimeException("missing system property: jdbc.url");
			}
			
			//con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:8080", "SA","");
			if (userName == null && password == null) {
				con = DriverManager.getConnection(url);
			} else {
				con = DriverManager.getConnection(url, userName, password);
			}

			con.setReadOnly(false);
			con.setAutoCommit(false);

			return con;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		finally{
			try{
			if(con!=null)
				con.close();			
			}
			catch(Exception e){
				logger.error("ConnectionUtil.java::getConnection::Error occured while closing connection", e);				
			}			
		}
	}
	
	/**
	 * 
	 * @param con
	 */
	public static void closeNoException(Connection con) {
		if(con == null) {
			return;
		}
		try {
			con.rollback();
		} catch (SQLException e) {
			// intentionally blank
		}
		try {
			con.close();
		} catch (SQLException e) {
			logger.warn(e);
		}
	}
	/**
	 * 
	 * @param rs
	 */
	public static void closeNoException(ResultSet rs) {
		if(rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (SQLException e) {
			logger.warn(e);
		}
	}

	/**
	 * 
	 * @param stmt
	 */
	public static void closeNoException(Statement stmt) {
		if(stmt == null) {
			return;
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			logger.warn(e);
		}
	}
}
