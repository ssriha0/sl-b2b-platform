/**
 * 
 */
package com.newco.batch.shipregistration;

import java.util.Date;
   
public interface ShipImportErrorMessages {
	
	public final static String DEFAULT_CELL_VALUE = null;
	public final static String DOT = ".";
	public final static String SPACE = " ";
	public final static String DEFAULT_EXP_DATE = "01/01/2000";
	public final static Date DEFAULT_DATE = null;
	public final static String SHIP_INPUT_DIRECTORY = "ship_input_directory";
	public final static String SHIP_SUCCESS_DIRECTORY = "ship_success_directory";
	public final static String SHIP_FAILURE_DIRECTORY = "ship_failure_directory";
	public final static String SUCCESS_FILE = "_success.xlsx";
	public final static String FAILURE_FILE = "_failure.xlsx";
	public final static String FAILURE_SHEET = "failure";
	public final static String SHEET = "Sheet";
	public final static String ROW_NO = "Row No Excluding The Header";
	public final static String ERROR = "Error";
	public final static String FIRM_SUCCESS_SHEET = "FirmSuccessData";
	public final static String USER_NAME ="UserName";
	public final static String PASSWORD = "Password";
	public final static String EMAIL_ID = "Email Id";
	public final static String FIRST_NAME = "FirstName";
	public final static String LAST_NAME = "LastName";
	public final static String SUBCONTRACTOR_ID = "Firm Subcontractor Id";
	public final static String PROVIDER_SUCCESS_SHEET="ProviderSuccessData";
	public final static String ARCHIEVE_FOLDER = "archive";
	public final static String EXCEL_XLS = ".xls";
	public final static String EXCEL_XLSX = ".xlsx";
	public final static String FIRM_SHEET_NAME = "ship_firm_sheet_name";
	public final static String CREW_SHEET_NAME = "ship_crew_sheet_name";
	public final static String INVALID_SUBCONTRACTOR_ID = "Please provide a valid Subcontractor id. Only numeric value is accepted.";
	public final static String INVALID_FIRST_NAME = "Please provide the First Name.";
	public final static String INVALID_LAST_NAME = "Please provide the Last Name.";
	public final static String INVALID_EMAIL_ID = "Please provide a valid e-mail address.";
	public final static String INVALID_DOING_BUSINESS_AS = "Please provide a valid Doing Business As(DBA).";
	public final static String INVALID_STATE = "Please provide a valid state.";
	public final static String INVALID_FIRST_NAME_LENGTH = "First Name field length should not be greater than 50";
	public final static String INVALID_LAST_NAME_LENGTH = "Last Name field length should not be greater than 50";
	public final static String INVALID_MIDDLE_NAME_LENGTH = "Middle Name field length should not be greater than 50";
	public final static String INVALID_EMAIL_FIELD_LENGTH = "Email length field should not be greater than 50";
	public final static String INVALID_ADDRESS1_LENGTH="Address1 field length should not be greater than 40";
	public final static String INVALID_ADDRESS2_LENGTH="Address2 field length should not be greater than 40";
	public final static String INVALID_CITY_LENGTH="City field length should not be greater than 30";
	public final static String INVALID_DBA_LENGTH="Doing Business As field length should not be greater than 30";
	public final static String INVALID_CELL_NO = "Please provide a valid cell number. Only numeric value is accepted";
	public final static String INVALID_HOME_NO = "Please provide a valid home number. Only numeric value is accepted";
	public final static String INVALID_CELL_NO_LENGTH = "Length of the cell number must be 10.";
	public final static String INVALID_HOME_NO_LENGTH = "Length of the home number must be 10";
	public final static String INVALID_GL_INSURANCE_LENGTH = "General Liability Insurance Company length should not be greater than 100";
	public final static String INVALID_WC_INSURANCE_LENGTH = "Workmans Compensation  Insurance Company length should not be greater than 100";
	public final static String INVALID_AO_INSURANCE_LENGTH = "Auto Insurance Company length should not be greater than 100";
	public final static String INVALID_GL_POLICY_NO = "General Liability Insurance Policy number should be  AlphaNumeric";
	public final static String INVALID_WC_POLICY_NO = "Workmans Compensation Policy number should be AlphaNumeric";
	public final static String INVALID_AO_POLICY_NO = "Auto Insurance Policy number should be AlphaNumeric";
	public final static String INVALID_GL_EDATE_FORMATE = "Please enter General Liability Expiration Date in 'MM/dd/yyyy HH:mm' format";
	public final static String INVALID_WC_EDATE_FORMATE = "Please enter Workmans Compensation Expiration Date in 'MM/dd/yyyy HH:mm' format";
	public final static String INVALID_AO_EDATE_FORMATE = "Please enter Auto Insurance Expiration Date in 'MM/dd/yyyy HH:mm' format";
	public final static String INVALID_GL_EDATE = "Please provide a valid expiration date for General Liability Insurance.";
	public final static String INVALID_WC_EDATE = "Please provide a valid expiration date for Workmans Compensation Insurance.";
	public final static String INVALID_AO_EDATE = "Please provide a valid expiration date for Auto Insurance.";
	public final static String INVALID_BUSINESS_STATE = "Invalid Business State";
	public final static String INVALID_PRODUCT_DESCRIPTION = "Invalid Product Description";
	public final static String INVALID_ZIP_CODE = "Please provide a valid business zip code which matches the state you provided.";
	public final static String INVALID_EMAIL_FORMAT = "Please provide a valid e-mail address.";
	public final static String INVALID_SUBCONTRACTOR_ID1="Provider firm with subcontractor id";
	public final static String INVALID_SUBCONTRACTOR_ID2="is already exists in ServiceLive";
	public final static String FIRM_DATA="Firm Data";
	public final static String PROVIDER_DATA="Provider Data";
	public final static String INVALID_SUBCONTRACTOR_CREWID1="Provider Resource with crewId";
	public final static String INVALID_SUBCONTRACTOR_CREWID2="already exists in ServiceLive for Firm with subcontractor Id";
	public final static String INVALID_FIRST_NAME_RESOURCE = "Please provide the First Name for the provider resource.";
	public final static String INVALID_LAST_NAME_RESOURCE = "Please provide the Last Name for the provider resource.";
	public final static String INVALID_SUBCONTRACTOR_CREW_ID = "Please provide a valid Subcontractor crew id. Only numeric value is accepted.";
	public final static String INVALID_SUBCONTRACTOR_ID_FOR_RESOURCE="Subcontractor id provided does not exists in ServiceLive.";
	public final static String INVALID_FIRM_SHEET = "Please make sure that the sub contractor details are present in the sheet named ";
	public final static String INVALID_PROVIDER_SHEET = "Please make sure that the crew details are present in the sheet named ";
}
