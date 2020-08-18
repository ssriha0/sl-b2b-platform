package com.newco.marketplace.webservices.util;
/**
 *  Constants for Web Service Operations
 *
 *@author     Siva
 *@created    January 4, 2008
 */
public final class WSConstants {
	public final static String FAILED_SERVICE_ORDER_NO = "000-0000000000-00";
	
	public final static String DASH_EMPTY_STREET_ADDRESS = "-";
	
	public final static String CUSTOM_REF_TEMPLATE_NAME = "TEMPLATE NAME";
	
	public final static String CUSTOM_REF_SPECIALTY = "SPECIALTY CODE";
	
	public final static String BUYER_ROLE_ID = "3";
	
	public final static String NOTE_TYPE_ID_GENERAL = "2";
	
	/*
	 * All process operation codes go here. The convention used is
	 * <SL><ProcessOperation><Number>
	 * SL stands for ServiceLive
	 * ProcessOperation holds a representative two letter code
	 * Number is a running number.   
	 */
	public final class WSProcessCodes{
		public static final String CREATE_DRAFT 	= "SLCD01";
		public static final String UPDATE_PARTS 	= "SLUP01";
		public static final String RESCHEDULE_SO    = "SLUR02";
		public static final String CANCEL_SO   		= "SLUC03";
		public static final String ROUTE_SO   		= "SLRS01";
		public static final String CLOSE_SO			= "SLCL01";
		public static final String INSERT_EVENT_SO	= "SLIE01";
		public static final String VALIDATE_SO		= "SLVS01";
		public static final String ADD_DOCUMENT_SO	= "SLAD01";
		public static final String ADD_CLIENT_SO_NOTE = "SLACN01";
		public static final String UPDATE_SO_INCIDENT_TRACKING = "SLUSIT01";
	}
	
	/*
	 * All process statuses go here 
	 */
	public final class WSProcessStatus{
		public static final String SUCCESS 	= "Success";
		public static final String FAILURE 	= "Failed";
		public static final String WARNING  = "Warning";
		public static final String VALID 	= "Valid";
		public static final String INVALID	= "Invalid";

	}
	
	/*
	 * All error codes and messages go here. The Class 'Codes' 
	 * should be used to add the error codes and the class 'Messages'
	 * should be used to store the messages. Also, please use the 
	 * same field name for both Codes and Messages for a Code/Message Combination. 
	 * As an example, 'CR_DR_ZIP_REQD' is used as the field name in both of the 
	 * classes - Codes and Messages 
	 * 
	 * The convention for the code used is <ProcessOperation><Error><Number>
	 * ProcessOperation is represented by two letters. 
	 * Error is represented by the letter E
	 * Number is a running number 
	 */
	
	public final class WSErrors {
		public final class Codes {
			public static final String WS_AUTH_UID_PWD = "AUE01";
			public static final String CR_DR_BUSINESS_OBJ_ERROR = "CR_DR_01";
			public static final String CR_DR_ZIP_REQD = "CDE01";
			public static final String CR_DR_TITLE_REQD = "CDE02";
			public static final String CA_SO_BL_ERR = "CAE01";
			public static final String CA_SO_USR_ERR = "CAU01";
			public static final String CR_DR_Business_Name_Validation ="CDE03";
			public static final String CR_DR_First_Name_Length_Validation ="CDE04";
			public static final String CR_DR_Last_Name_Lenght_Validation ="CDE05";
			public static final String CR_DR_Street_Length_Validation_Msg ="CDE06";
			public static final String CR_DR_City_Length_Validation ="CDE07";
			public static final String CR_DR_Zip_Validation ="CDE08";
			public static final String CR_DR_Email_Validation_Msg ="CDE9";
			public static final String CR_DR_Email_Pattern_Validation_Msg ="CDE10";
			public static final String CR_DR_Phone_Missing_Number_Validation_Msg ="CDE11";
			public static final String CR_DR_Phone_Type_Validation ="CDE12";
			public static final String CR_DR_Alternate_Phone_Missing_Number_Validation_Msg ="CDE13";
			public static final String CR_DR_Alternate_Phone_Type_Validation ="CDE14";
			public static final String CR_DR_Fax_Missing_Number_Validation_Msg ="CDE15";
			public static final String CR_DR_Please_enter_a_contact_phone_no ="CDE16";
			public static final String CR_DR_Task_Name_Validation_Msg ="CDE17";
			public static final String CR_DR_Buyer_Terms_Condition_validation_Msg ="CDE18";
			public static final String CR_DR_Overview_Validation_Msg ="CDE19";
			public static final String CR_DR_Title_Validation ="CDE20";
			public static final String CR_DR_Special_Instruction_Validation_Msg ="CDE21";
			public static final String CR_DR_CurrentDate_Date1_Time_Validation ="CDE22";
			public static final String CR_DR_CurrentDate_Date1_Validation ="CDE23";
			public static final String CR_DR_Service_Date2 ="CDE24";
			public static final String CR_DR_EndTime_Validation ="CDE25";
			public static final String CR_DR_State_Validation_Msg = "CDE27";
		    public static final String CR_DR_Zip_Validation_Missing="CDE28";
		    public static final String CR_DR_Zip4_Validation="40";
		    public static final String CR_DR_Zip_Validation_Missing_Length="41";
			public static final String CR_DR_MainService_Category_Validation = "CDE29";
			public static final String CR_DR_Service_Contact="CDE30";
			public static final String CR_DR_Task_Not_Provided="CDE31";
			public static final String CR_DR_Comment_Validation="CDE32";
			public static final String CR_DR_Task_Name_Validation_Length_Msg="CDE33";
			public static final String CR_DR_Sub_Category_Validation_Msg = "CDE34";
			public static final String CR_DR_Title_Length_Validation="CDE35";
			public static final String CR_DR_StartTime_Validation="CDE36";
			public static final String CR_DR_StartEndTime_Validation="CDE37";
			public static final String CR_DR_InvalidDate_Validation="CDE38";
			public static final String CR_DR_InvalidFormatDate_Validation="CDE39";
			public static final String CR_DR_RETAIL_PRICE_MISSING="CDE40";
			public static final String CR_DR_BUYERID_MISSING="CDE41";
			public static final String CR_DR_INVALID_CUSTOMREF ="CDCR42";
			public static final String CR_DR_INVALID_SECURITY_CONTEXT ="CDSC43";
			
			//validate service order
			public static final String VSO_MISSING_RESOURCE_OR_SO = "VSOE01";
			public static final String VSO_INVALID_RESOURCE_OR_SO = "VSOE02";
			
			//General DB Exception
			public static final String DB_EX_GENERAL = "DBE01";
			
			//General Exception
			public static final String EX_GENERAL = "EXE01";

			public static final String INVALID_SERVICEORDERID = "SOE01";
			public static final String INVALID_MAINSERVICECATEGORYID = "MSCE01";
	        public static final String PART_REFERENCE_ID="PTE01";
			public static final String CR_DR_MISSING_TEMPLATE = "CDT01";
			public static final String ACN_SERVICEORDER_NOTFOUND = "ACNSL01";
			
			// PARTS
			public static final String PART_INFO_MISSING_MANUFACTURER = "PRT01";
			public static final String PART_INFO_MISSING_MODELNUMBER = "PRT02";
		}
		
		public final class Messages {

			public final static String WS_AUTH_UID_PWD = "Username and password error";
			public static final String CR_DR_TITLE_REQD = "Title mandatory";
			public static final String CR_DR_MainService_Category_Validation = "Main service category required(**Required**)";
			public static final String CR_DR_Comment_Validation =  "Task comment should not exceed 5000 characters";
			public static final String CR_DR_Task_Name_Validation_Msg = "Task name is empty";
			public static final String CR_DR_Task_Name_Validation_Length_Msg = "Task name cannot exceed 35 characters.";
			public static final String CR_DR_Task_Not_Provided = "At least one task is required.";
			public static final String CR_DR_Category_Validation_Msg = "Category should be selected.";
			public static final String CR_DR_Sub_Category_Validation_Msg = "A sub category must be selected.";
			public static final String CR_DR_Skill_Validation_Msg = "Selection of skill is required.";
			public static final String CR_DR_Business_Name_Validation = "Business Name should not exceed 100 characters.";
			public static final String CR_DR_First_Name_Validation = "First name is required.";
			public static final String CR_DR_First_Name_Length_Validation = "First name should not exceed 50 characters.";
			public static final String CR_DR_Last_Name_Validation = "Last name is required.";
			public static final String CR_DR_Last_Name_Lenght_Validation = "Last name should exceed 50 Characters.";
			public static final String CR_DR_Street_Name_Validation =  "Street Name is required.";
			public static final String CR_DR_Street_Length_Validation_Msg = "Street1 should not exceed 100 characters.";
			public static final String CR_DR_Street2_Length_Validation_Msg = "Street2 should not exceed 100 characters.";
			public static final String CR_DR_Apt_Validation_Msg = "Apt# should exceed 10 characters.";
			public static final String CR_DR_Zip_Validation = "Zip code validation failed, a valid zipcode is required";
			public static final String CR_DR_Zip_Validation_Missing = "Zip code is required.";
			public static final String CR_DR_BUYERID_MISSING= "Buyer id required";
			public static final String CR_DR_Zip_Validation_Missing_Length="first part of the zip should be 5 integers";
			public static final String CR_DR_Zip4_Validation = "Zip should be number in following format xxxxx-xxxx";
			public static final String CR_DR_City_Validation = "City is required.";
			public static final String CR_DR_City_Length_Validation = "City name cannot exceed 30 characters.";
			public static final String CR_DR_State_Validation_Msg = "Please select a state."; 
			public static final String CR_DR_Email_Validation_Msg = "Email should not exceed 255 characters.";
			public static final String CR_DR_Email_Pattern_Validation_Msg = "Email address is not valid.";
			public static final String CR_DR_Alternate_phone = "Alternate phone.";
			public static final String CR_DR_Phone_Validation_Msg = "Phone should be numeric.";
			public static final String CR_DR_Phone_Missing_Number_Validation_Msg = "Please enter a valid 10 digit Phone number [3 digits - 3 digits - 4 digits].";
			public static final String CR_DR_Alternate_Phone_Missing_Number_Validation_Msg = "Please enter a valid 10 digit  alternate Phone number [3 digits - 3 digits - 4 digits].";
			public static final String CR_DR_Fax_Missing_Number_Validation_Msg = "Please enter a valid 10 digit  Fax number [3 digits - 3 digits - 4 digits].";
			public static final String CR_DR_Phone_Validation_Msg_Req = "Atleast One phone required for posting.";
			public static final String CR_DR_Phone_Type_Validation = "Select a Valid Phone Type";
			public static final String CR_DR_Alternate_Phone_Type = "Alternate Phone Type";
			public static final String CR_DR_Alternate_Phone_Type_Validation = "Select a Valid Alternate Phone Type";
			public static final String CR_DR_Title_Validation ="Please enter a Title for this service order (**Required**).";
			public static final String CR_DR_Title_Length_Validation = "Title cannot exceed 255 character";
			public static final String CR_DR_Special_Instructions_validation_Msg = "Special instruction should not be empty.";
			public static final String CR_DR_Buyer_Terms_Condition_validation_Msg = "Buyer terms and condition should not exceed 5000 characters.";
			public static final String CR_DR_Overview_Validation_Msg = "Overview should not exceed 2500 characters.";
			public static final String CR_DR_Special_Instruction_Validation_Msg = "Special instruction should not exceed 5000 characters.";
			public static final String CR_DR_Date1_validation = "Please check  start service date(MM/DD/YY).";
			public static final String CR_DR_Date_Format_Validation = "Service date should be in MM/DD/YY format.";
			public static final String CR_DR_Date2_validation = "Please check  end service date(MM/DD/YY).";
			public static final String CR_DR_Date1_validation_Fixed_Msg= "Please check service date(MM/DD/YY).";
			public static final String CR_DR_Date1_Date2_Validation = "To Date should be greater or equal to Date1.";
			public static final String CR_DR_CurrentDate_Date1_Validation = "Start date and time should be greater or equal to Current date and time.";
			public static final String CR_DR_CurrentDate_Date1_Time_Validation = "Start Time Should be greater than Current Time.";
			public static final String CR_DR_InvalidDate_Validation = "End date should be grater than or equal to start date";
			public static final String CR_DR_InvalidFormatDate_Validation = "This in not a Valid Date Format.";
			public static final String CR_DR_StartTime_Validation = "Select a Valid Start Time.";
			public static final String CR_DR_EndTime_Validation = "Select a Valid End Time.";
			public static final String CR_DR_StartEndTime_Validation = "End Time Should be greater than Start Time.";
            public static final String CR_DR_Please_enter_a_contact_phone_no="please enter a contact phone no";
            public static final String CR_DR_StartTime_Validation_Fixed = "Select a valid Time";
            public static final String CR_DR_Service_Contact="Service contact cannot be empty,atleast zip required";
            public static final String CR_DR_RETAIL_PRICE_MISSING="Retail Price should not be null";
            public static final String CR_DR_INVALID_CUSTOMREF ="Invalid Custom Reference Key";
            public static final String CR_DR_INVALID_SECURITY_CONTEXT = "Error getting values from security context";
            
            public static final String VSO_MISSING_RESOURCE_OR_SO = "Missing resource ID or service order number";
            public static final String VSO_INVALID_RESOURCE_OR_SO = "Invalid resource ID - it must be a number";
            
             //General DB Exception
			public static final String DB_EX_GENERAL = "General database error";
			
			//General Exception
			public static final String EX_GENERAL = "General exception error";
			public static final String INVALID_SERVICEORDERID = "Service Order ID is not valid";
			public static final String INVALID_MAINSERVICECATEGORYID = "Main Service Catergory ID is not valid";
	        public static final String PART_REFERENCE_ID="Part Reference ID is required";
			public static final String CR_DR_MISSING_TEMPLATE = "Missing Template";
			public static final String ACN_SERVICEORDER_NOTFOUND = "Service Order not found from client order id string";
			
			// PARTS
			public static final String PART_INFO_MISSING_MANUFACTURER = "manufacturer not set in part";
			public static final String PART_INFO_MISSING_MODELNUMBER = "model not set in part";			
		}
		public final class FieldLength
		{
			public static final int STREET1 = 100;
			public static final int STREET2 = 100;
			public static final int CITY = 30;
		}
	}
	
	/*
	 * All Warning codes and messages go here. The Class 'Codes' 
	 * should be used to add the warning codes and the class 'Messages'
	 * should be used to store the messages. Also, please use the 
	 * same field name for both Codes and Messages for a Code/Message Combination. 
	 * As an example, 'CR_DR_ZIP_WARN_1' is used as the field name in both of the 
	 * classes - Codes and Messages 
	 * 
	 * The convention for the code used is <ProcessOperation><Warning><Number>
	 * ProcessOperation is represented by two letters. 
	 * Warning is represented by the letter W
	 * Number is a running number 
	 */
	public final class WSWarnings {
		public final class Codes {
			public static final String CR_DR_ZIP_WARN_1 = "CDW01";
			public static final String CR_DR_ZIP_WARN_2 = "CDW02";
			public static final String CR_DR_Street_Name_Validation="CDW04";
			public static final String CR_DR_First_Name_Validation="CDW05";
			public static final String CR_DR_Last_Name_Validation="CDW06";
			public static final String CR_DR_Street2_Length_Validation_Msg ="CDW07";
			public static final String CR_DR_City_Validation ="CDW08";
			public static final String CR_DR_Task_Name_Validation_Msg ="CDW09";
			public static final String CR_DR_Phone_Validation_Msg_Req ="CDW11";
			public static final String CR_DR_Date1_validation="CDW12";
			public static final String CR_DR_Date2_validation="CDW13";
			public static final String CR_DR_Date1_validation_Fixed_Msg = "CDW14";
			public static final String CR_DR_StartTime_Validation_Fixed="CDW15";
			public static final String CR_DR_Skill_Validation_Msg="CDW16";
			public static final String CR_DR_Category_Validation_Msg = "CDW17";
			public static final String CR_DR_SERVICE_DATE_VALIDATION="CDW18";
			public static final String CR_DR_Task_Name_Validation_Length_Msg="CDW19";
			
		}
		
		public final class Messages {
			public static final String CR_DR_First_Name_Validation = "First name is required.";
			public static final String CR_DR_Last_Name_Validation = "Last name is required.";
			public static final String CR_DR_Street2_Length_Validation_Msg = "Street2 should not exceed 30 characters.";
			public static final String CR_DR_City_Validation = "City is required.";
			public static final String CR_DR_Task_Name_Validation_Msg = "Task name is empty";
			public static final String CR_DR_Task_Name_Validation_Length_Msg = "Task name cannot exceed 35 characters.";
			public static final String CR_DR_Category_Validation_Msg = "Category should be selected.";
			public static final String CR_DR_Phone_Validation_Msg_Req = "Atleast One phone required for posting.";
			public static final String CR_DR_Date1_validation = "Please check  start service date(MM/DD/YY).";
			public static final String CR_DR_Date2_validation = "Please check  end service date(MM/DD/YY).";
			public static final String CR_DR_Date1_validation_Fixed_Msg= "Please check service date(MM/DD/YY).";
	        public static final String CR_DR_StartTime_Validation_Fixed = "Select a valid Time";
	    	public static final String CR_DR_Street_Name_Validation =  "Street Name is required.";
	    	public static final String CR_DR_Skill_Validation_Msg = "Selection of skill is required.";
	    	public static final String CR_DR_SERVICE_DATE_VALIDATION = "Please check the service date";
		}		
		
	}
	
	
}
