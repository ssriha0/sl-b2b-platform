/**
 * 
 */
package com.servicelive.manage1099.properties;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

import com.mysql.jdbc.StringUtils;
import com.servicelive.manage1099.beans.BuyerInputBean;
import com.servicelive.manage1099.constants.DBConstants;
import com.servicelive.manage1099.constants.DatumConstants;
import com.servicelive.manage1099.constants.EmailConstants;
import com.servicelive.manage1099.constants.FTPConstants;
import com.servicelive.manage1099.constants.FileConstants;
import com.servicelive.manage1099.constants.ValidationConstants;
import com.servicelive.manage1099.log.Log;
import com.servicelive.manage1099.util.CommonUtil;

/**
 * @author mjoshi1
 * 
 */
public class ReadProperties {

	//String str, key;

	public ReadProperties(){
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void loadInputProperties() throws FileNotFoundException,
			IOException {
		File f = new File(FileConstants.PROPERTY_FILE_WITH_PATH);
		File f2 = new File(FileConstants.LOCAL_FILE_FOR_TESTING);

		File queryFile =new File(FileConstants.SQL_FILE);
		File inputFile =new File(FileConstants.INPUT_FILE);

		File queryFile2 =new File(FileConstants.LOCAL_SQL_FILE);
		File inputFile2 =new File(FileConstants.LOCAL_INPUT_FILE);

		
		
		
		if (f.exists()) {

			loadAllProperties(f);

		} else if (f2.exists()) {
			
			loadAllProperties(f2);
			
		} else {

			System.out.println("File not found!");
			Log.writeLog(Level.SEVERE, "File not found : sl1099properties.properties ");
			throw new FileNotFoundException("The sl1099properties.properties  property file is missing");
		}

		if (queryFile.exists()) {
			loadAllQueryProperties(queryFile);
			
		}else if (queryFile2.exists()) {
			loadAllQueryProperties(queryFile2);
			
		} else {
			System.out.println("File not found!");
			Log.writeLog(Level.SEVERE, "File not found : query.sql does not exit. ");
			throw new FileNotFoundException("The query.sql property file is missing");
		}
		
		if (inputFile.exists()) {
			loadAllBuyersProperties(inputFile);
			
		}else if(inputFile2.exists()) {
			loadAllBuyersProperties(inputFile2);
			
		} else {
			System.out.println("File not found!");
			Log.writeLog(Level.SEVERE, "File not found : query.sql does not exit. ");
			throw new FileNotFoundException("The buyerInput.parameters property file is missing");
		}

		
		
	}

	/**
	 * @param f
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void loadAllProperties(File f) throws FileNotFoundException, IOException {
		Properties pro = new Properties();
		FileInputStream in = new FileInputStream(f);
		pro.load(in);

		FileConstants.LOGGING = loadProperty("LOGGING", pro);
		DBConstants.DB_URL = loadProperty("DB_URL", pro);
		DBConstants.DB_USER = loadProperty("DB_USER", pro);
		DBConstants.DB_PASSWORD = loadProperty("DB_PASSWORD", pro);
		DBConstants.DB_SUPPLIER = loadProperty("DB_SUPPLIER", pro);
		DBConstants.DB_ACCOUNTS = loadProperty("DB_ACCOUNTS", pro);
		
		DBConstants.SHOW_QUERY = loadProperty("SHOW_QUERY", pro);
		
		FileConstants.FILE_NAME_WITH_PATH = loadProperty("FILE_NAME_WITH_PATH", pro);
		final String fileNameWithTimeStamp = CommonUtil.suffixTimeStamp(FileConstants.FILE_NAME_WITH_PATH + "_");
		final String fileNameWithExt = fileNameWithTimeStamp + FileConstants.TEXT_EXTENSION;
		FileConstants.FILE_NAME_WITH_PATH = fileNameWithExt;
		FileConstants.REMOTE_FILE_WITH_PATH = loadProperty("REMOTE_FILE_WITH_PATH", pro);
		
		FileConstants.PGP_ENCRYPTION_SCRIPT_FILE = loadProperty("PGP_ENCRYPTION_SCRIPT_FILE", pro);
		
		
		FileConstants.DELETE_FILE = loadProperty("DELETE_FILE", pro);
		FileConstants.W9_ENTRY_YEAR = loadProperty("W9_ENTRY_YEAR", pro);

	/*	FTPConstants.FTP_HOST = loadProperty("FTP_HOST", pro);
		FTPConstants.FTP_USER = loadProperty("FTP_USER", pro);
		FTPConstants.FTP_PASSWORD =loadProperty("FTP_PASSWORD", pro);
		*/
		
		EmailConstants.EMAIL_NOTIFICATION=loadProperty("EMAIL_NOTIFICATION", pro);
		EmailConstants.RECIPIENT_LIST_COMMA_SEPARATED = loadProperty("RECIPIENT_LIST_COMMA_SEPARATED", pro);
		EmailConstants.EMAIL_SMTP=loadProperty("EMAIL_SMTP", pro);
		EmailConstants.SENDER_EMAIL=loadProperty("SENDER_EMAIL", pro);	
		EmailConstants.EMAIL_SUBJECT=loadProperty("EMAIL_SUBJECT", pro);
		EmailConstants.EMAIL_SIGNATURE=loadProperty("EMAIL_SIGNATURE", pro);
		
		
		ValidationConstants.VALIDATE=loadProperty("VALIDATE", pro);
		ValidationConstants.VALIDATE_AGAINST_FILE=loadProperty("VALIDATE_AGAINST_FILE", pro);
		ValidationConstants.MISSING_VENDORS=loadProperty("MISSING_VENDORS", pro);
		
		generateMissingVendorsList(ValidationConstants.MISSING_VENDORS);
		
		Log.writeLog(Level.INFO, pro.keySet().toString());
		//Log.writeLog(Level.INFO, pro.values().toString());
	}
	
	
	/**
	 * @param f
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void loadAllQueryProperties(File f) throws FileNotFoundException, IOException {
		Properties pro = new Properties();
		FileInputStream in = new FileInputStream(f);
		pro.load(in);

		DBConstants.QUERY_CREDIT_DEBIT=loadProperty("QUERY_CREDIT_DEBIT", pro);
		DBConstants.QUERY_SO_PAYMENT=loadProperty("QUERY_SO_PAYMENT", pro);
		DBConstants.QUERY_SO_CANCELLATION_FIX=loadProperty("QUERY_SO_CANCELLATION_FIX", pro);
		//DBConstants.QUERY2 = loadProperty("QUERY2", pro);
		//DBConstants.QUERY3 = loadProperty("QUERY3", pro);
	
		Log.writeLog(Level.INFO, pro.keySet().toString());
		//Log.writeLog(Level.INFO, pro.values().toString());
	}
	
	

	/**
	 * @param f
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void loadAllBuyersProperties(File f) throws FileNotFoundException, IOException {
		Properties pro = new Properties();
		FileInputStream in = new FileInputStream(f);
		pro.load(in);

		Enumeration<Object> keys = pro.keys();
		
		try {
			while (keys.hasMoreElements()) {
				String buyerId = (String) keys.nextElement();
				
				// Make sure we place  CREDIT_DEBIT_RANGE on the last index
				// of the array. It is very important to have it on the last index
				// because credit debit query should only be executed once query
				// for all the buyers fetch amount for providers.
				if(!buyerId.trim().equalsIgnoreCase(DatumConstants.CREDIT_DEBIT_RANGE)){
					String dateRange = loadProperty(buyerId, pro);
					String startDate="";
					String endDate="";
					BuyerInputBean buyerBean = new BuyerInputBean();
					buyerBean.setBuyerid(buyerId);
					if(dateRange!=null && dateRange.length()>0){
						 startDate=dateRange.substring(0,dateRange.indexOf(",")).trim();
						 endDate=dateRange.substring(dateRange.indexOf(",")+1).trim();
					}
					buyerBean.setStartDate(startDate);
					buyerBean.setEndDate(endDate);
					FileConstants.BUYER_INPUT.add(buyerBean);
				
				}
				//System.out.println(buyerBean);
				
			}
			
			String dateRange = loadProperty(DatumConstants.CREDIT_DEBIT_RANGE, pro);
			String startDate="";
			String endDate="";
			BuyerInputBean buyerBean = new BuyerInputBean();
			buyerBean.setBuyerid(DatumConstants.CREDIT_DEBIT_RANGE);
			if(dateRange!=null && dateRange.length()>0){
				 startDate=dateRange.substring(0,dateRange.indexOf(",")).trim();
				 endDate=dateRange.substring(dateRange.indexOf(",")+1).trim();
			}
			buyerBean.setStartDate(startDate);
			buyerBean.setEndDate(endDate);
			FileConstants.BUYER_INPUT.add(buyerBean);
			
			FileConstants.START_DATE = startDate;
			FileConstants.END_DATE = endDate;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(" *****  INVALID DATE RANGE IN input.properties file !!!" );
			System.exit(0);
		}
		
				
		Log.writeLog(Level.INFO, pro.keySet().toString());
		//Log.writeLog(Level.INFO, pro.values().toString());
	}
	
	
	
	
	
	
	
	/**
	 * Loads a particular property from the property file.
	 * @param propertyName
	 * @param pro
	 */
	private String loadProperty(String propertyName, Properties pro) {

		String propertyValue="";
		try {
			propertyValue = pro.getProperty(propertyName);
			
			if(propertyValue==null || propertyValue.trim().length() <1){
				Log.writeLog(Level.WARNING, " The Value for the propertyName  '"+propertyName+"' is missing in sl1099properties.properties");
			}
			
		} catch (Exception e) {
			Log.writeLog(Level.WARNING, " Following Property Name does not exist : " + propertyName + "  File:"
					+ FileConstants.PROPERTY_FILE_WITH_PATH);
			System.out.println(" Following Property Name does not exist : " + propertyName + "  File:"
					+ FileConstants.PROPERTY_FILE_WITH_PATH);
			e.printStackTrace();
		}
		return propertyValue==null?"":propertyValue.trim();
	}
	
	
	
	
	// The MISSING_VENDORS in ValidationConstants.java property contains all vendors as comma separated.
	// MISSING_VENDORS_LIST breaks them down as a list.
	
	private void generateMissingVendorsList(String vendorsList) {

		if (vendorsList != null && vendorsList.length() > 0) {

			StringTokenizer vendorTokens = new StringTokenizer(vendorsList, ",");

			while (vendorTokens.hasMoreTokens()) {
				String vendor = vendorTokens.nextToken();
				if (vendor != null) {
					ValidationConstants.MISSING_VENDORS_LIST.add(vendor.trim());
				}

			}

		}

	}	
	
	

}
