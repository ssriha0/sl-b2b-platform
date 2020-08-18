/**
 * 
 */
package com.servicelive.manage1099.constants;


import java.util.ArrayList;

import com.servicelive.manage1099.beans.BuyerInputBean;

/**
 * @author mjoshi1
 *
 */
public class FileConstants {
	public static String FILE_NAME_WITH_PATH = "";
	public static String FILE_NAME_WITH_PATH_TEST = "testFile";
	public static String logFile = "slive1099Logs.log";
	public static int limit = 1000000; // 1 Mb
	public static final String PROPERTY_FILE_WITH_PATH = "sl1099properties.properties";
	public static String REMOTE_FILE_WITH_PATH="";
	public static String LOGGING = "ON";
	public static String PGP_ENCRIPTION_SCRIPT =""; 
	public static final String TEXT_EXTENSION = ".txt";
	public static final String PGP_EXTENSION = ".pgp";
	public static String DELETE_FILE= "true";
	public static String PGP_ENCRYPTION_SCRIPT_FILE ="";
	public static String SQL_FILE="query.sql";
	public static String INPUT_FILE="buyerInput.parameters";
	public static java.util.List<BuyerInputBean> BUYER_INPUT = new ArrayList<BuyerInputBean>();
	public static String VALIDATION_REPORT = "validationReport.txt";
	
	public static String START_DATE = "";
	public static String END_DATE = "";
	
	public static String W9_ENTRY_YEAR = "";
	
	/*
	 * These vars are only used for testing on local without creating any jar files.
	 *  
	 */
	public static String LOCAL_HOME="./";//"/appl/sl/sl1099app/";
	public static String LOCAL_FILE_FOR_TESTING = LOCAL_HOME+"sl1099properties.properties";
	public static String LOCAL_SQL_FILE=LOCAL_HOME+"query.sql";
	public static String LOCAL_INPUT_FILE=LOCAL_HOME+"buyerInput.parameters";
	
	
}
