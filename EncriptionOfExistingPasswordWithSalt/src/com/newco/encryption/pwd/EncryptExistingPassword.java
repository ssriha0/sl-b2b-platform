package com.newco.encryption.pwd;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.CryptoUtil.CannotPerformOperationException;



public class EncryptExistingPassword {
	public static final String PROPERTY_FILE_WITH_PATH = "dbConnection.properties";

	public static String generateStrongPassword(String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException, CannotPerformOperationException {

		return CryptoUtil.generateStrongPasswordHash(password.toCharArray());
	}

	public static void main(String args[]) throws SQLException,
			NoSuchAlgorithmException, InvalidKeySpecException,
			ClassNotFoundException, CannotPerformOperationException {
		PreparedStatement preparedStatement = null;
		String dbUrl = null;
		String dbUserName = null;
		String dbPassword = null;
		int updated = 1;
		try {
			Class.forName(SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH,"datasource.driver"));
		// qa1 = 151.149.2.253:6446
		
			dbUrl = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH,"datasource.url");
			dbUserName = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH,"datasource.username");
			dbPassword = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH,"datasource.password");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Connection con = DriverManager.getConnection(dbUrl, dbUserName,
				dbPassword);
		con.setAutoCommit(false);
		Statement stmt = con.createStatement();
		try {
			
		ResultSet rs = stmt.executeQuery("SELECT DISTINCT password FROM user_profile;");
			
			while (rs.next()) {
				String password = rs.getString("password");
				if (null != password && (!password.trim().equals("")) && password.length()>15) {
					System.out.println("password from db is:" + password);
					String hashPwd = generateStrongPassword(password);
					System.out.println("hased format pwd is:" + hashPwd);
					String queryData = "UPDATE user_profile SET password = ? WHERE password = ? ";
					preparedStatement = con.prepareStatement(queryData);
					preparedStatement.setString(1, hashPwd);
					preparedStatement.setString(2, password);
				    preparedStatement.executeUpdate();
					System.out.println("Updated count: " + (updated++));
				}
			}
			con.commit();
			System.out.println("------COMPLETED-------");
		} catch (SQLException e) {

			System.out.println(e.getMessage());
			con.rollback();

		} finally {

			if (stmt != null) {
				stmt.close();
			}

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}



}
