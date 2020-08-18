/**
 * 
 */
package com.servicelive.gl.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.servicelive.gl.constants.DBConstants;
import com.servicelive.gl.constants.FileConstants;

/**
 * @author sldev
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

		/*File queryFile =new File(FileConstants.SQL_FILE);
		File inputFile =new File(FileConstants.INPUT_FILE);

		File queryFile2 =new File(FileConstants.LOCAL_SQL_FILE);
		File inputFile2 =new File(FileConstants.LOCAL_INPUT_FILE);*/

		if (f.exists()) {

			loadAllProperties(f);

		} else if (f2.exists()) {
			
			loadAllProperties(f2);
			
		} else {

			System.out.println("File not found!");
			//Log.writeLog(Level.SEVERE, "File not found : sl1099properties.properties ");
			throw new FileNotFoundException("The config.properties  property file is missing");
		}

/*		if (queryFile.exists()) {
			loadAllQueryProperties(queryFile);
			
		}else if (queryFile2.exists()) {
			loadAllQueryProperties(queryFile2);
			
		} else {
			System.out.println("File not found!");
			//Log.writeLog(Level.SEVERE, "File not found : query.sql does not exit. ");
			throw new FileNotFoundException("The query.sql property file is missing");
		}
		
		if (inputFile.exists()) {
			loadAllBuyersProperties(inputFile);
			
		}else if(inputFile2.exists()) {
			loadAllBuyersProperties(inputFile2);
			
		} else {
			System.out.println("File not found!");
			//Log.writeLog(Level.SEVERE, "File not found : query.sql does not exit. ");
			throw new FileNotFoundException("The buyerInput.parameters property file is missing");
		}*/
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

		DBConstants.DB_URL = loadProperty("DB_URL", pro);
		DBConstants.DB_USER = loadProperty("DB_USER", pro);
		DBConstants.DB_PASSWORD = loadProperty("DB_PASSWORD", pro);

		DBConstants.QUERY_GL=loadProperty("QUERY_GL", pro);
		DBConstants.QUERY_FISCAL_WEEK=loadProperty("QUERY_FISCAL_WEEK", pro);	
		
		FileConstants.REPORT_DIR=loadProperty("REPORT_DIR", pro);
		
		DBConstants.START_DATE=loadProperty("START_DATE", pro);
		DBConstants.END_DATE=loadProperty("END_DATE", pro);
		
		DBConstants.BUYER_ID=loadProperty("BUYER_ID", pro);
	}
	
	
	/**
	 * @param f
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
/*	private void loadAllQueryProperties(File f) throws FileNotFoundException, IOException {
		Properties pro = new Properties();
		FileInputStream in = new FileInputStream(f);
		pro.load(in);

		//DBConstants.QUERY_CREDIT_DEBIT=loadProperty("QUERY_CREDIT_DEBIT", pro);
		//DBConstants.QUERY_SO_PAYMENT=loadProperty("QUERY_SO_PAYMENT", pro);
		//DBConstants.QUERY2 = loadProperty("QUERY2", pro);
		//DBConstants.QUERY3 = loadProperty("QUERY3", pro);
	
		//Log.writeLog(Level.INFO, pro.keySet().toString());
		//Log.writeLog(Level.INFO, pro.values().toString());
	}*/
	

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
				//Log.writeLog(Level.WARNING, " The Value for the propertyName  '"+propertyName+"' is missing in sl1099properties.properties");
			}
			
		} catch (Exception e) {
			/*Log.writeLog(Level.WARNING, " Following Property Name does not exist : " + propertyName + "  File:"
					+ FileConstants.PROPERTY_FILE_WITH_PATH);*/
			System.out.println(" Following Property Name does not exist : " + propertyName + "  File:"
					+ FileConstants.PROPERTY_FILE_WITH_PATH);
			e.printStackTrace();
		}
		return propertyValue==null?"":propertyValue.trim();
	}

}
