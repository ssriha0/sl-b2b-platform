package com.newco.marketplace.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.newco.marketplace.dto.vo.CryptographyVO;

public class MigrateVendorEINto128bit {

	public MigrateVendorEINto128bit() {
	}

	public static void main(String args[]) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(
					"MigrateVendorEINto128bit.properties"));
			db_host = properties.getProperty("db_host");
			db_port = properties.getProperty("db_port");
			db_name = properties.getProperty("db_name");
			db_user = properties.getProperty("db_user");
			db_pass = properties.getProperty("db_pass");
			enKey = properties.getProperty("enKey");
			enKey128 = properties.getProperty("enKey128");
			output_sql = properties.getProperty("output_sql");
		} catch (IOException ioexception) {
			System.out
					.println("Issues with properties file MigrateVendorEINto128bit.properties");
			System.exit(1);
		}
		CryptographyVO cryptographyvo = new CryptographyVO();
		cryptographyvo.setKAlias(enKey);
		ArrayList arraylist = new ArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		String s = "";
		String s3 = "";
		String s4 = "";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String s6 = "jdbc:mysql://";
			s6 = (new StringBuilder()).append(s6).append(db_host).toString();
			s6 = (new StringBuilder()).append(s6).append(":").toString();
			s6 = (new StringBuilder()).append(s6).append(db_port).toString();
			s6 = (new StringBuilder()).append(s6).append("/").toString();
			s6 = (new StringBuilder()).append(s6).append(db_name).toString();
			connection = DriverManager.getConnection(s6, db_user, db_pass);
			String s1 = "select * from vendor_w9";
			connection.setAutoCommit(true);
			statement = connection.createStatement();
			resultset = statement.executeQuery(s1);
			System.out
					.println("Building SQL file to run against vendor_w9_128_bit_encrypt table...");
			String s5;
			for (; resultset.next(); arraylist.add(s5)) {
				ResultSetMetaData resultsetmetadata = resultset.getMetaData();
				int i = resultsetmetadata.getColumnCount();
				if (s3.equals("")) {
					s3 = "insert into `vendor_w9_128_bit_encrypt`(";
					for (int j = 1; j <= i; j++) {
						if (resultsetmetadata.getColumnName(j).equals(
								"ein_no_128_bit_encrypt"))
							continue;
						if (resultsetmetadata.getColumnName(j).equals(
								"taxpayer_id_number_type")) {
							s3 = (new StringBuilder()).append(s3).append("`")
									.toString();
							s3 = (new StringBuilder()).append(s3).append(
									"archive_date").toString();
							s3 = (new StringBuilder()).append(s3).append("`")
									.toString();
						} else {
							s3 = (new StringBuilder()).append(s3).append("`")
									.toString();
							s3 = (new StringBuilder()).append(s3).append(
									resultsetmetadata.getColumnName(j))
									.toString();
							s3 = (new StringBuilder()).append(s3).append("`")
									.toString();
						}
						if (j != i)
							s3 = (new StringBuilder()).append(s3).append(",")
									.toString();
						else
							s3 = (new StringBuilder()).append(s3).append(
									") VALUES (").toString();
					}

				}
				s5 = s3;
				for (int k = 1; k <= i; k++) {
					if (resultsetmetadata.getColumnName(k).equals(
							"ein_no_128_bit_encrypt"))
						continue;
					if (resultsetmetadata.getColumnName(k).equals(
							"taxpayer_id_number_type"))
						s5 = (new StringBuilder()).append(s5).append("NULL")
								.toString();
					else if (resultsetmetadata.getColumnType(k) == 12) {
						if (resultset.getString(k) == null
								|| resultset.getString(k).equals("null"))
							s5 = (new StringBuilder()).append(s5)
									.append("NULL").toString();
						else if (resultsetmetadata.getColumnName(k).equals(
								"ein_no")) {
							s5 = (new StringBuilder()).append(s5).append(
									"AES_ENCRYPT(").toString();
							s5 = (new StringBuilder()).append(s5).append("'")
									.toString();
							cryptographyvo.setInput(resultset.getString(k));
							s5 = (new StringBuilder()).append(s5).append(
									decryptKey(cryptographyvo).getResponse())
									.toString();
							s5 = (new StringBuilder()).append(s5).append("','")
									.toString();
							s5 = (new StringBuilder()).append(s5).append(
									enKey128).toString();
							s5 = (new StringBuilder()).append(s5).append("')")
									.toString();
						} else {
							s5 = (new StringBuilder()).append(s5).append("'")
									.toString();
							s5 = (new StringBuilder()).append(s5).append(
									escapeSQL(resultset.getString(k)))
									.toString();
							s5 = (new StringBuilder()).append(s5).append("'")
									.toString();
						}
					} else if (resultsetmetadata.getColumnTypeName(k).equals(
							"DATETIME")) {
						s5 = (new StringBuilder()).append(s5).append("'")
								.toString();
						s5 = (new StringBuilder()).append(s5).append(
								resultset.getDate(k)).toString();
						s5 = (new StringBuilder()).append(s5).append(" ")
								.toString();
						s5 = (new StringBuilder()).append(s5).append(
								resultset.getTime(k)).toString();
						s5 = (new StringBuilder()).append(s5).append("'")
								.toString();
					} else if (resultsetmetadata.getColumnTypeName(k).equals(
							"DATE")) {
						if (resultset.getDate(k) == null) {
							s5 = (new StringBuilder()).append(s5)
									.append("NULL").toString();
						} else {
							s5 = (new StringBuilder()).append(s5).append("'")
									.toString();
							s5 = (new StringBuilder()).append(s5).append(
									resultset.getDate(k)).toString();
							s5 = (new StringBuilder()).append(s5).append("'")
									.toString();
						}
					} else {
						s5 = (new StringBuilder()).append(s5).append(
								resultset.getInt(k)).toString();
					}
					if (k != i)
						s5 = (new StringBuilder()).append(s5).append(",")
								.toString();
					else
						s5 = (new StringBuilder()).append(s5).append(");")
								.toString();
				}

			}

			try {
				if (resultset != null)
					resultset.close();
			} catch (Exception exception) {
			}

			for (ListIterator listiterator = arraylist.listIterator(); listiterator
					.hasNext(); statement.executeUpdate((String) listiterator
					.next()))
				;
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
			String s2 = "";
		}
		System.out
				.println("The SQL file containing the inserts for the 128-bit table is here:");
		System.out.println(output_sql);
	}

	public static CryptographyVO encryptKey(CryptographyVO cryptographyvo) {
		try {
			byte abyte0[] = (new BASE64Decoder())
					.decodeBuffer("P3Q6GUCryVD51h5JOo0WMQ==");
			SecretKeySpec secretkeyspec = new SecretKeySpec(abyte0, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(1, secretkeyspec);
			byte abyte1[] = cipher.doFinal(cryptographyvo.getInput().getBytes(
					"8859_1"));
			cryptographyvo.setResponse((new BASE64Encoder()).encode(abyte1));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return cryptographyvo;
	}

	public static CryptographyVO decryptKey(CryptographyVO cryptographyvo) {
		Object obj = null;
		try {
			byte abyte0[] = (new BASE64Decoder())
					.decodeBuffer("P3Q6GUCryVD51h5JOo0WMQ==");
			SecretKeySpec secretkeyspec = new SecretKeySpec(abyte0, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte abyte1[] = (new BASE64Decoder()).decodeBuffer(cryptographyvo
					.getInput());
			cipher.init(2, secretkeyspec);
			byte abyte2[] = cipher.doFinal(abyte1);
			String s = new String(abyte2, "8859_1");
			cryptographyvo.setResponse(s);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return cryptographyvo;
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
	static String enKey;
	static String enKey128;
	static String output_sql;
}
