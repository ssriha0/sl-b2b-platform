package com.servicelive.manage1099.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Properties;

import com.servicelive.manage1099.encode.Cryptography;

/**
 * This program is to update the EIN data to 128 bit MySQL AES encrypted form.
 * Please ensure the query to populate the delta data has been loaded in the Reporting DB
 * 
 * 	INSERT supplier_accounts_prod_bus.`vendor_w9_history_128bit` 
 *	SELECT * FROM supplier_prod.`vendor_w9_history` 
 *	WHERE archive_date > <Time stamp>;
 *
 *  Time stamp is the max archived date in the vendor_w9_history_128bit table in 
 *  reporting DB - supplier_accounts_prod_bus schema
 *
 */
public class MigrateVendorEINHistoryto128bit {

	public MigrateVendorEINHistoryto128bit() {
	}

	public static void main(String args[]) {
		Properties properties = new Properties();
		Cryptography cryptography = null;
		try {
			//Loading the properties from the properties file. Ensure correct DB is pointed to
			
			properties.load(new FileInputStream(
					"MigrateVendorEINHistoryto128bit.properties"));
			db_host = properties.getProperty("db_host");
			db_port = properties.getProperty("db_port");
			//Please ensure the right host is used normally it is supplier_accounts_prod_bus schema
			db_name = properties.getProperty("db_name");
			db_user = properties.getProperty("db_user");
			db_pass = properties.getProperty("db_pass");
			
			//enKey128 is the 128 bit encryption key used by the reporting team
			enKey128 = properties.getProperty("enKey128");
			
			cryptography = new Cryptography();
		} catch (IOException ioexception) {
			System.out
					.println("Issues with properties file MigrateVendorEINHistoryto128bit.properties");
			System.exit(1);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> arraylist = new ArrayList<String>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String s6 = "jdbc:mysql://";
			s6 = (new StringBuilder()).append(s6).append(db_host).toString();
			s6 = (new StringBuilder()).append(s6).append(":").toString();
			s6 = (new StringBuilder()).append(s6).append(db_port).toString();
			s6 = (new StringBuilder()).append(s6).append("/").toString();
			s6 = (new StringBuilder()).append(s6).append(db_name).toString();
			connection = DriverManager.getConnection(s6, db_user, db_pass);
			//String s1 = "SELECT ein_no FROM vendor_w9_history_128bit limit 1,1";
			
			//We are fetching the records which have no 128 bit encrypted data. This will be delta from last years run
			String s1 = "SELECT DISTINCT ein_no FROM vendor_w9_history_128bit WHERE ein_no_128bit IS NULL";
			connection.setAutoCommit(true);
			statement = connection.createStatement();
			resultset = statement.executeQuery(s1);
			System.out
					.println("Completed fetching the data to be updated from the vendor_w9_history_128bit");
			String s5="";
			String ein = "";
			String decryptedStr = "";
			
			//Building the update queries to be run for updating the 128 bit EIN data  
			while(resultset.next()){
				//s5="";
				ein = resultset.getString(1);
				decryptedStr = cryptography.decodeString(ein);
				s5 = "UPDATE vendor_w9_history_128bit SET ein_no_128bit = ";
				
				if(decryptedStr.equals("")){
					decryptedStr = ein;
					s5 = (new StringBuilder()).append(s5).append("'").append(decryptedStr).append("'").toString();
				}else{
					s5 = (new StringBuilder()).append(s5).append("AES_ENCRYPT('").append(decryptedStr).append("','").append(enKey128).append("') ").toString();
				}
				s5 = (new StringBuilder()).append(s5).append(" WHERE ein_no = '").append(ein).append("'").toString();
				
				//This array holds the list of update queries
				arraylist.add(s5);
			}
			
			try {
				if (resultset != null)
					resultset.close();
			} catch (Exception exception) {
			}
			
			for(String query:arraylist){
				System.out.println(query);
				//Executing the updates one by one
				statement.executeUpdate(query);
			}


		} catch (ClassNotFoundException classnotfoundexception) {
			System.err.println(classnotfoundexception.getMessage());
		} catch (IllegalAccessException illegalaccessexception) {
			System.err.println(illegalaccessexception.getMessage());
		} catch (InstantiationException instantiationexception) {
			System.err.println(instantiationexception.getMessage());
		} catch (SQLException sqlexception) {
			System.err.println(sqlexception.getMessage());
		} finally {
			try {
				if (resultset != null)
					resultset.close();
			} catch (Exception exception2) {
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception exception3) {
			}
			try {
				if (connection != null) {
					connection.commit();
					connection.close();
				}
			} catch (Exception exception4) {
			}
			connection = null;
			statement = null;
			resultset = null;
		}

	}

	public static String escapeSQL(String s) {
		StringBuffer stringbuffer = new StringBuffer();
		StringCharacterIterator stringcharacteriterator = new StringCharacterIterator(
				s);
		for (char c = stringcharacteriterator.current(); c != '\uFFFF'; c = stringcharacteriterator
				.next())
			if (c == '\'')
				stringbuffer.append("\\'");
			else
				stringbuffer.append(c);

		return stringbuffer.toString();
	}

	static String db_host;
	static String db_port;
	static String db_name;
	static String db_user;
	static String db_pass;
	static String enKey128;
}
