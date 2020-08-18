package com.newco.marketplace.util.constants;

public class SystemGeneratedEmailConstants {

	public static final String LOGO_URL = "buyer_email_logo_base_url";
	public static final String SURVEY_URL = "survey_link";
	public static final String SURVEY_URL_KEY = "?key=";
	public static final String SURVEY_URL_TYPE = "&surveyType=";
	public static final String SURVEY_URL_RATING = "&ratings=";
	
	// email Parameters
	public static final String SURVEY_LINK = "SURVEY_LINK";
	public static final String SGE = "SystemGeneratedEmail";
	public static final String MODIFIED_BY = "MODIFIED_BY";
	public static final String REMINDER_SERVICE = "ORDER_SERVICE_DUE_REMINDER";
	public static final String EMAIL = "EMAIL";
	public static final String SO_ID = "SO_ID";
	public static final String SO_TITLE = "SO_TITLE";
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String DATE_SERVICE = "DATE_SERVICE";
	public static final String TIME_SERVICE = "TIME_SERVICE";
	public static final String TIME_SLOT = "TIME_SLOT";
	public static final String TIME_SLOTS = "TIME_SLOTS";
	public static final String TIME_SLOTS0 = "TIME_SLOTS0";
	public static final String PROVIDER_FIRM_NAME = "PROVIDER_FIRM_NAME";
	public static final String PROVIDER_PHONENUMBER = "PROVIDER_PHONENUMBER";
	public static final String PROVIDER_RATING = "PROVIDER_RATING";
	public static final String SCHEDULE_TYPE_1 = "1";
	public static final String SCHEDULE_TYPE_2 = "2";
	public static final String SCHEDULE_TYPE_3 = "3";
	public static final String MAX_HEIGHT = "MAX_HEIGHT";
	public static final String MAX_WIDTH = "MAX_WIDTH";
	public static final String EMAIL_LOGO_MAX_HEIGHT = "email_logo_max_height";
	public static final String EMAIL_LOGO_MAX_WIDTH = "email_logo_max_width";
	//SLT-2232
	public static final String AMOUNT_DEBITED="AMOUNT_DEBITED";
	public static final String CREATED_DATE="CREATED_DATE";
	//SLT-2329 
	public static final String SGE_BATCH = "TRG_ALERT_SYSTEM_GENERATED_EMAIL";
	public static final String REMINDER_BATCH = "TRG_ALERT_REMINDER_EMAIL";
	public static final String RECORD_FAILED_EMAIL_SUBJECT="%s : alert for failed records";
	public static final String RECORD_FAILED_EMAIL_BODY = "<html><body><div>*This is a system generated message - Please do not reply to this email* <br/><br/>Hi Team, <br/><br/>During '%s' batch processing, data processing failed for records with below so_logging_id: <br/><br/> <ul> %s </ul>Please resolve the data issue based on details in server log and reprocess the records to trigger email.<br/><a href=\"https://wiki.intra.sears.com/confluence/display/SOUQ/Alert+for+Failed+SGE+emails\">Steps to Reprocess Failed Emails</a></div></body></html>";
	public static final String ALERT_RECIPIENT = "email_failure_notification_recipient";
	//SLT-3782
	public static final Integer RELAY_SERVICES_BUYER = 3333;
}
