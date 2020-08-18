package com.newco.marketplace.interfaces;

/**
 * @author schavda
 *
 */
public interface SurveyConstants {
	
	//SURVEY TYPES
	public final static String SURVEY_TYPE_BUYER = "SO_BUYER";
	public final static String SURVEY_TYPE_PROVIDER = "SO_PROVIDER";
	
	//SURVEY TYPES IDS
	public final static int SURVEY_TYPE_BUYER_ID = 2;
	public final static int SURVEY_TYPE_PROVIDER_ID = 1;
	
	
	//ENTITY TYPES
	public final static String ENTITY_BUYER = "BUYER";
	public final static String ENTITY_PROVIDER = "PROVIDER";
	public final static String ENTITY_MARKETPLACE = "MARKETPLACE";
	
	//ENTITY TYPES IDS
	public final static int ENTITY_BUYER_ID = 10;
	public final static int ENTITY_PROVIDER_ID = 20;
	public final static int ENTITY_MARKETPLACE_ID = 30;
	//SURVEY IDS
	public final static int SURVEY_BUYER_ID = 10;
	
	//SURVEY QUESTION IDS
	public final static int SURVEY_QUESTION_ID_ONE = 1;
	public final static int SURVEY_QUESTION_ID_TWO = 2;
	public final static int SURVEY_QUESTION_ID_THREE = 3;
	//SURVEY ANSWER IDS
	public final static int SURVEY_ANSWER_ID_ONE = 1;
	public final static int SURVEY_ANSWER_ID_TWO = 2;
	public final static int SURVEY_ANSWER_ID_FIVE = 5;
	public final static int SURVEY_ANSWER_ID_ZERO = 0;
	
	public final static int SURVEY_ANSWER_ID_TEN = 10;
	
	//WEST SURVEY 
	public final static String WEST_SURVEY_DIR_PATH = "WEST_SURVEY_DIR_PATH";
	public final static String WEST_SURVEY_ERROR_FILE_PATH = "WEST_SURVEY_ERROR_FILE_PATH";
	public final static String WEST_SURVEY_ARCHIVE_FILE_PATH = "WEST_SURVEY_ARCHIVE_FILE_PATH";
	public final static String WEST_SURVEY_DELIMITER = "|";
	public final static String ESCAPE_CHARACTER = "\\";
	public final static String BUYER_ID = "BUYER_ID";
	public final static int DATA_START_EXCELROW = 8;
	public final static int DATA_START_EXCELROW_BATCH = 1;
	//WEST SURVEY EMAIL SUBJECT AND BODY
	public final static String NEWLINE_CHAR = "\r\n";
	public final static String HYPHEN = "-";
	public final static String COMMA = ", ";
	public final static String WEST_MAIL_SUBJECT_ENV_DESCR = " environment: ";
	public final static String WEST_MAIL_BODY_EXCEPTION_STACKTRACE = "Exception StackTrace:";

	public final static String WEST_MAIL_SUBJECT_ENTIRE_FILE_FAILED = "West Survey File - Entire File Parsing Failed - "; 
	public final static String WEST_MAIL_BODY_ENTIRE_FILE_FAILED = "When attempting to parse West survey file, the entire file parsing failed. No provider ratings were updated.";
	
	public final static String WEST_MAIL_SUBJECT_FILE_RECORDS_FAILED = "West Survey File partial records parsing failed - " ;
	public final static String WEST_MAIL_BODY_FILE_RECORDS_FAILED = "When attempting to parse and upload West survey file, processing for following records failed. No provider ratings were updated for these records. Please fix the provider ratings for these records from application manually; or extract these error records into new spreadsheet; fix necessary data/formatting issue and re-ftp that file to retry processing of these records.";
	public final static String WEST_MAIL_BODY_RECORDS_FAILED_DESCR = "Records that failed: ";
	public final static String UNIT_NO = "Unit No: ";
	public final static String SERVICE_ORDER = "ServiceOrder: " ;
	public final static String WEST_MAIL_BODY_ERROR_DESCR = " and the error occured is : ";
	public final static String WEST_MAIL_BODY_FILES_FAILED_DESCR = "File that failed: ";
	public final static String WEST_MAIL_BODY_REFER_APP_LOGS = "File is going to be moved to errors folder. Please refer application logs for necessary error details. Fix the relevant data/excel formatting issue and re-ftp the file to input data folder to retry processing of this file.";

	public final static int HOMES_SERVICES_BUYER_ID=7000;
	public final static int HOME_SERVICE_ENTITY_TYPE=10;
	public final static int HOME_SERVICE_SURVEY_TYPE=1;
	
	public final static int INT_VALUE_ZERO=0;

	//R15_4 SL-20988
	public final static String FILE_EXTENSION_CSV = "csv";
	public final static String FILE_EXTENSION_TXT = "txt";
	public final static String DOT = ".";
	
	public final static String CSAT_MAIL_SUBJECT_FILE_RECORDS_FAILED = "CSAT Survey File records parsing failed - " ;
	public final static String CSAT_MAIL_BODY_FILE_RECORDS_FAILED = "When attempting to parse and upload Csat survey file, few records failed.";
	public final static String CSAT_MAIL_BODY_RECORDS_FAILED_DESCR = "Please find attached file for error details.";

	
	public final static String CSAT_NPS_EVENT_COMPLETED="COMPLETED";
	public final static String CSAT_NPS_EVENT_CLOSED="CLOSED";
	public final static String CSAT_NPS_OLDFLOW="OLD_FLOW";
	public final static String CSAT_NPS_NEWFLOW="NEW_FLOW";
	public final static String SURVEY_NOT_CONFIGURED="NOT_CONFIGURED";
	public final static String NO_TAB="NO_TAB";
	public final static String NOT_RATED="NOT_RATED";
	public final static String CSAT_NPS_NEWFLOW_APPKEY="NPS_CSAT_NEW_FLOW_IMPLEMENTATION";
	public final static String DATFORMAT="yyyy-MM-dd hh:mm:ss";
	
	public static final String SURVEY_BUYER_RATING = "SurveyBuyerRating";
	public static final String SURVEY_ENTITY_RATING = "SurveyEntityRating";
	public static final String DEFAULT = "default";
	public static final String SEPERATOR = "_";
	public static final String SURVEY_RATING_FILE_EXTENTION="survey_rating_file_extention";	
	public static final String SURVEY_RATING_DIRECTORY="SurveyRatingDirectory";
	public static final String SURVEY_RATING_ARCHIVE_DIRECTORY="SurveyRatingArchiveDirectory";
	public static final String SURVEY_RATING_DATA_SEPERATOR="survey_rating_file_data_seperator"; 
	 

	public static final String SURVEYTYPECSAT = "csat";
	public static final String SURVEYTYPENPS = "nps";
	public static final String SURVEYTYPECSAT_NPS = "csat_nps";
	public static final String BELOWSTR = "_belowAverage";
	public static final String ABOVESTR = "_aboveAverage";
	
	public static final String SOD_RSLT_BUYER = "surveyResults";
	public static final String SOD_RSLT_STATUS = "surveyStatus";
	
	public static final String CURRENTSCORE = "CURRENT";
	public static final String LIFETIMESCORE = "LIFETIME";
	public static final String CSATSCORE = "CSAT";
	public static final String NPSSCORE = "NPS";
	public static final Integer ENTITY_TYPE_ID_VENDOR=40;
	public static final Integer ENTITY_TYPE_ID_RESOURCE=20;
	

}
