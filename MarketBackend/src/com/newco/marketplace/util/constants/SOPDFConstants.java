package com.newco.marketplace.util.constants;

import java.awt.Color;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.ListItem;


public class SOPDFConstants {
	
	private static final Logger logger = Logger.getLogger(SOPDFConstants.class);
	
    public static final Font FONT_PAGE_HEADER = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    public static final Font FONT_HEADER = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
    public static final Font FONT_LABEL_SMALL = FontFactory.getFont(FontFactory.HELVETICA, 5);
    public static final Font FONT_LABEL_BIG = FontFactory.getFont(FontFactory.HELVETICA, 10);
    public static final Font FONT_LABEL_BIG_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
    public static final Font FONT_LABEL = FontFactory.getFont(FontFactory.HELVETICA, 8);
     // SL-21886
    public static final Font FONT_LABEL_BIG_BOLD_UNDERLINE = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.UNDERLINE);
    
    //Font added for left section of RI pdf under SL-18203
    public static final Font FONT_HEADER_MEDIUM = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7);
    public static final Font FONT_LABEL_MEDIUM = FontFactory.getFont(FontFactory.HELVETICA, 7);
    public static final Font FONT_ITALIC_MEDIUM = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.ITALIC);
   
    public static final Font FONT_ITALIC = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC);
    public static final Font FONT_RED = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.ITALIC, new Color(255, 0, 0));
    public static final ListItem NEW_LINE = new ListItem("\n", FONT_LABEL);
   
    //Constants for provider instructions PDF
    public static final String SERVICE_ORDER="Service Order";
    public static final String SPACE =" ";
    public static final String OPEN_SQUARE_BRACKET="[";
    public static final String CLOSE_SQUARE_BRACKET="]";
    public static final String HASH_STR="#";
    public static final String BLANK_SPACE ="";
    public static final String PROVIDER_INSTR="Provider Instructions";
    public static final String PROVIDER="Provider";
    public static final String COLON=":";
    public static final String COMMA=",";
    public static final String SERVICE_EVENT_DATE="SERVICE EVENT DATE";
    public static final String SERVICE_LOCATION_CONTACT_MSG="\n* Please confirm the service time with the service location contact.";
    public static final String SERVICE_LOCATION="SERVICE LOCATION";
    public static final String PICKUP_LOCATION="PICK UP/MERCHANDISE LOCATION";
    public static final String NOT_AVAILABLE = "Product At Job Site";
    public static final String CONTACT_INFORMATION="CONTACT INFORMATION";
    public static final String BUYER="Buyer: ";
    public static final String ATTACHMENTS="ATTACHMENTS:";
    public static final String CUSTOM_REFERENCE="CUSTOM REFERENCE:";
    public static final String IMPORTANT_ARRIVAL_DEPARTURE_MSG="IMPORTANT--UPON ARRIVAL AND DEPARTURE:";
    public static final String UPON_ARRIVAL = "Arrival Instructions:";
    public static final String UPON_DEPARTURE = "Departure Instructions:";
    public static final String UPON_ARRIVAL_STEP_1 = "Call 1.800.376.6960";
    public static final String UPON_ARRIVAL_STEP_2 = "When prompted, enter your individual 5 digit SERVICEPRO ID (aka \"Technician's ID\")";
    public static final String UPON_ARRIVAL_STEP_3 = "Next, enter the last 6 digits of Service Order #";
    public static final String UPON_DEPARTURE_STEP_1 = "Repeat Arrival Instructions.";
    public static final String UPON_DEPARTURE_STEP_2 = "Select either a) \"Departure - no issues\" or b) \"Departure with issues\"";
    public static final String UPON_DEPARTURE_STEP_3 = "If you chose b), select the appropriate reason for the issue";
    public static final String CALL_IVR_MSG="When arriving and departing from the service location, call our IVR system to indicate you are at the service location.";
    public static final String CALL_IVR_NO="1-800-376-6960";
    public static final String ARRIVAL_INDICATOR_MSG="Have your Service Provider Number and the last 6 digits of the Service Order Number ready. Follow the voice prompts to indicate arrival";
    public static final String DEPARTURE_INDICATOR_MSGSTR="and departure or departure with issues.";
    public static final String SCOPE_OF_WORK_MSG="SERVICE ORDER SCOPE OF WORK:";
    public static final String SERVICE_CATEGORY="Service Category: ";
    public static final String TITLE="Title: ";
    public static final String OVERVIEW="Overview: ";
    public static final String INST_FOR_PROVIDER="Special Instructions for the Provider:";
    public static final String SERVICE_LOCATION_NOTES="Service Location Notes:";
    public static final String TASK="Task ";
    public static final String TASK_CATEGORY="Task Category: ";
    public static final String SUB_CATEGORY="Sub Category: ";
    public static final String SKILL="Skill: ";
    public static final String SERVICE_ORDER_COMMENTS="Service Order Comments: ";
    public static final String PARTS_AND_MATERIALS="PARTS AND MATERIALS:";
   
    public static final String INVOICE_PARTS="PARTS REQUIRED";

    public static final String PART="Part ";
    public static final String MANUFACTURER="Manufacturer: ";
    public static final String MODEL="Model: ";
    public static final String DESCRIPTION="Description: ";
    public static final String QUANTITY="Quantity: ";
    public static final String ADDITIONAL_PARTS_INST="Additional Parts Instructions: ";
    public static final String SHIPPING_CARRIER="Shipping Carrier: ";
    public static final String SHIPPING_TRACKING_NO="Shipping Tracking Number: ";
    public static final String RETURN_CARRIER="Return Carrier: ";
    public static final String RETURN_TRACKING_NO="Return Tracking Number: ";
    public static final String GENERAL_SCOPE_COMMENTS="GENERAL SCOPE OF WORK COMMENTS: ";
   

    public final static String BLANK= "";
    public final static String ID_NUM = "ID# ";   
    public final static String SERVICE_ORDER_NUMBER= "Service Order #";
    public final static String CUSTOMER_COPY ="Customer Copy";
    public final static String PROVIDER_COPY ="Provider Copy";
    public final static String COMMA_SPACE =", ";
    public final static String COLON_SPACE=": ";
    public final static String NAME= "Name";
    public final static String PHONE= "Phone";
    public final static String ALTERNATE_PHONE="Alternate Phone";
    public final static String ADDRESS="Address";
    public final static String OPENING_BRACE = "(";
    public final static String CLOSING_BRACE = ")";
    public final static String SERVICE_EVENT="SERVICE EVENT DATE";
    public final static String APPOINTMENT_DATES="Appointment Date(s): ";
    public final static String SERVICE_PRO="SERVICE PROVIDER";
    public final static String PROVIDER_NAME="Provider Name: ";
    public final static String USER_ID="ID# ";
    public final static String COMPANY_ID = "Company ID# ";
    public final static String PROVIDER_FIRM_NUM="Provider Firm #: ";
    public final static String ARRIVAL_TIME="Arrival Time: _______AM/PM";
    public final static String DEPARTURE_TIME="Departure Time: ______ AM/PM";
    public final static String CALL_IVR_MSG_ON_CONTRACT="Call IVR upon arrival and at departure.\n\n1-800-376-6960";
    public final static String UNASSIGNED = "Unassigned";
       
    public final static String BUYER_CUSTOM_REF="BUYER CUSTOM REFERENCE";
    public final static String SERVICE_WINDOW="Service Window:\n";
    public final static String PROVIDER_CUSTOM_REF="Provider Custom Reference";
    public final static String SO_DESC="SERVICE ORDER DESCRIPTION";
    public final static String NEW_LINE_FEED="\n";
    public final static String SO_COMMENTS= "Service Order Comments: ";
    public final static String WEIGHT= "Weight";
    public static final String QUANTITY_STR="Quantity";
    public final static String SCOPE_OF_WORK_COMMENTS="GENERAL SCOPE OF WORK COMMENTS:";
    public final static String ADD_WORK_PERFORMED="ADDITIONAL WORK PERFORMED / SERVICE PROVIDER NOTES:";
   
    public final static String WAIVER_HEADER="WAIVER OF LIEN (TO BE SIGNED BY THE SERVICE PROVIDER)";
    public final static String WAIVER_HEADER_SIGNED="WAIVER OF LIEN (SIGNED BY THE SERVICE PROVIDER)";
    public final static String WAIVER_TEXT="I, the undersigned Service Provider, having performed the above referenced work, do hereby certify that the work referred to has been completed to the customer's satisfaction. I hereby certify that I have not filed any liens on the premises and I hereby waive and relinquish all liens and all rights and claims of liens which I, the undersigned, now have or may hereafter have for labor or material furnished. I, the undersigned, certify that all the work performed and materials furnished, if any, by any other party or parties upon the order ofthe undersigned, has been fully paid for. Further, I the undersigned, agree to cause the prompt release of any mechanic's lien which may be filed against the premises by anysubcontractor, laborer, mechanic, or material supplier claiming the right to file such a lien through work related to above referenced work. I further agree to hold harmless and indemnify the above referenced customer or the buying organization, from and against all costs and expenses arising from or by reason of such lien or the release or discharge of such liens.";
    public final static String WAIVER_SIGNATURE="          _____________________________           _____________________________             _____________________________\n                 Service Pro Name (Print)                              Service Pro Signature                                                        Date  ";
   
    public final static String SIGNATURE_TEXT_ONE="I have reviewed the work performed and I acknowledge the satisfactory completion of all above referenced work.";
    public final static String RI_SIGNATURE_TEXT_ONE="SATISFACTORY COMPLETION*** I have reviewed the work performed and I acknowledge the satisfactory completion of all above referenced work.";
   
    //SL-20716: Changing the verbiage
    public final static String RI_SIGNATURE_TEXT_TWO="DAMAGE POLICY NOTIFICATION*** I have the right to refuse damaged items and must report any visible damage within 72 hours of my home delivery/installation to be eligible for a refund or exchange.\n \n";

    public final static String SIGNATURE_TEXT_TWO="          _____________________________           _____________________________             _____________________________\n                  Customer  Name (Print)                               Customer Signature                                                           Date  ";
   
    public final static String WORK_ACK_STR="I have reviewed the work performed and I acknowledge the satisfactory completion of all above referenced work.";
    public final static String THANK_YOU_STRING="\nTHANK YOU FOR ALLOWING US TO SERVE YOU. WE TRULY APPRECIATE YOUR BUSINESS!";
   
    public final static String ATTACHMENTS_SMALL="Attachments";
    public final static String BUYER_STR= "Buyer" ;
    public final static String SUPPORT_CONTACT="Support Contact";
    public final static String ONSITE_VISITS="On-Site Visits";
   
    public final static String NEWLINE_STR="  ____________________________   \n";
   
    public static final String BUYER_TERMS_COND = "Buyer's Terms & Conditions:";
    public static final String SUPPORT_CONTACT_LABEL = "SERVICE ORDER SUPPORT CONTACT: ";
   
    public static final String SERVICE_ORDER_STRING = "Service Order ";
    public static final String PROVIDER_STRING = "Provider: ";
    public static final String PROVIDER_INSTRUCTIONS_STRING = "Provider Instructions";
    public static final String SO_SCOPE_OF_WORK_CONTD_STRING = "SERVICE ORDER SCOPE OF WORK(CONTINUED)";
    public static final String SPL_INSTRUCTIONS_FOR_PROVIDER_STRING = "Special Instructions for the Provider:";
    public static final String SERVICE_LOCATION_NOTES_STRING = "Service Location Notes:";
    public static final String SQUARE_BRACKET_OPEN = "[";
    public static final String SQUARE_BRACKET_CLOSE = "]";
    public static final String HYPHEN = "-";
   
    public static final String ZERO_PHONE = "0000000000";
    public static final String ONE = "1";

    // Addon Table Constants
    public static final String ADDON_TABLE_HEADING = "Additional Charges for Parts, Materials & Services:";
    public static final String ADDON_TABLE_COL_HEADING_QTY = "Qty";
    public static final String COVERAGE_CC = "CC";
    public static final String ADDON_TABLE_COL_HEADING_SKU = "SKU";
    public static final String ADDON_TABLE_COL_HEADING_DESC = "Description";
    public static final String ADDON_TABLE_COL_HEADING_SOW = "Scope of Work";
    public static final String ADDON_TABLE_COL_HEADING_PRICE = "Price";
    public static final String TOTAL_ADDONS_PRICE = "Total: $ ____________\n\nAll prices include tax.\n\n";
    public static final String ADDON_CREDIT_AUTH_HEADING = "Credit Authorization:";
    public static final String ADDON_CREDIT_AUTH_FORM_SEARS = "I agree that the credit purchase is subject to my Agreement with the credit card issuer identified hereon.\n\nCredit Card\nType __________________________ Last 4-Digits Only  ____________ Expiration _________________\n\n\nPre-Authorization ____________________ Date: ________________\n\n\nCustomer Signature _______________________________________\n\n\n";
    public static final String ADDON_CREDIT_AUTH_FORM = "I agree that the credit purchase is subject to my Agreement with the credit card issuer identified hereon.\n\nCredit Card\nType __________________________ Number ___________________________________________ Exp _________________\n\n Pre-Authorization ___________________________ Date: _____________________\n\nCustomer Signature __________________________________________\n\n\n";
    public static final String ADDON_CREDIT_AUTH_AMT_DETAILS = "\nCredit Card Amount $ _________________ Check Amount $ _________________ (#__________________________) Make checks payable to Sears\n\n\n";
    public static final String ADDON_CHG_SPECS_HEADING = "Change of Specification Affirmation";
    public static final String ADDON_CHG_SPECS_HEADING_EXTRA_TEXT = " (To be signed by customer if applicable)";
    public static final String ADDON_CHG_SPECS_FORM = "This is to confirm that the provider assigned to my job has notified me of additional work that is required to complete the service order. I agree to the changes in the amount specified for \"Additional Charges for Parts/Material & Services\" above. I acknowledge receipt of merchandise and/or services in the total amount shown hereon.\n\nCustomer Signature ________________________________________________ Date _____________________";
    public static final String MOBILE_ADDON_CHG_SPECS_FORM_1 = "This is to confirm that the provider assigned to my job has notified me of additional work that is required to complete the service order. I agree to the changes in the amount specified for \"Additional Charges for Parts/Material & Services\" above. I acknowledge receipt of merchandise and/or services in the total amount shown hereon.";
    public static final String MOBILE_ADDON_CHG_SPECS_FORM_2 = "Customer Signature ________________________________________________ Date _____________________";
    public static final String MOBILE_CHECK_TYPE = "CA";
    public static final String MOBILE_ADDON_CREDIT_AUTH_FORM = "I agree that the credit purchase is subject to my Agreement with the credit card issuer identified hereon.\n";
    public static final String MOBILE_ADDON_NEWLINE = "\n";
    public static final String MOBILE_ADDON_PLACEHOLDERLINE = "__________________________";
    public static final String MOBILE_ADDON_PLACEHOLDERLINE_SHORT = "_______________";
    public static final String MOBILE_ADDON_PLACEHOLDERLINE_SHORT1 = "____________";  
    public static final String MOBILE_ADDON_PLACEHOLDERLINE_MEDIUM1 = "________________";
    public static final String MOBILE_ADDON_PLACEHOLDERLINE_MEDIUM = "____________________";
    public static final String MOBILE_ADDON_PLACEHOLDERLINE_LONG = "__________________________________________";
    public static final String MOBILE_ADDON_TEXT_1 = "Credit Card Type";
    public static final String MOBILE_ADDON_TEXT_2_SEARS = "Last 4-Digits Only";
    public static final String MOBILE_ADDON_TEXT_3_SEARS = "Expiration";
    public static final String MOBILE_ADDON_TEXT_2 = "Number";
    public static final String MOBILE_ADDON_TEXT_3 = "Exp";
    public static final String MOBILE_ADDON_TEXT_4 = "Pre-Authorization";
    public static final String MOBILE_ADDON_TEXT_5 = "Date:";
    public static final String MOBILE_ADDON_TEXT_6 = "Customer Signature";
    public static final String MOBILE_ADDON_TEXT_7 = "Credit Card Amount $";
    public static final String MOBILE_ADDON_TEXT_8 = "Check Amount $";
    public static final String MOBILE_ADDON_TEXT_9 = "(#";
    public static final String MOBILE_ADDON_TEXT_10 = ") Make checks payable to Sears";
    //PDF changes for Innovel starts SL-21288
    public static final String ADDON_INNOVEL_SOLUTIONS = "Innovel Solutions.";
    public static final String ADDON_CREDIT_CARD_TEXT_INNOVEL = " Your Credit Card charges will be posted on your Credit Card statement as Innovel Solutions.\n\n";
   //PDF changes for Innovel end SL-21288
    //PDF provider picture
    public final static int ACTIVE_STATUS = 155;
    public static String DEFAULT_PROV_PICTURE_FILEPATH = "resources/images/no_photo_available_for_pdf.gif";
    public static int DEFAULT_PROV_PICTURE_WIDTH = 100;
    public static int DEFAULT_PROV_PICTURE_HEIGHT = 100;
   
    //added for SL-10233
    public final static String PRIMARY_PHONE = "PRIMARY:";
    public final static String ALTERNATE_PHONENUMBER = "ALTERNATE:";
    public static final String UPON_ARRIVAL_STEP_1_FOR_RI = "1.Call 1-800-376-6960";
    public static final String UPON_ARRIVAL_STEP_2_FOR_RI = "2.Enter Tech ID# ";
    public static final String UPON_ARRIVAL_STEP_3_FOR_RI = "3.Enter SO ID# ";
    public static final String UPON_ARRIVAL_STEP_4_FOR_RI = "4.Select \"Arrival\"";
    public static final String UPON_DEPARTURE_STEP_1_FOR_RI = "1.Call 1-800-376-6960";
    public static final String UPON_DEPARTURE_STEP_2_FOR_RI = "2.Enter Tech ID# ";
    public static final String UPON_DEPARTURE_STEP_3_FOR_RI = "3.Enter SO ID# ";
    public static final String UPON_DEPARTURE_STEP_4_FOR_RI = "4.Select \"Departure - no issues\" or \"Departure with issues\"";
    public static final String UPON_DEPARTURE_STEP_5_FOR_RI = "If you chose departure with issues, select the appropriate issue option";
    public final static String USER_ID_FOR_RI="TECH ID# ";
    public final static String COMPANY_ID_FOR_RI = "FIRM ID# ";
    public final static String CALL_IVR_MSG_ON_CONTRACT_HDR="Call IVR upon arrival and at departure.";
    public final static String CALL_IVR_MSG_ON_CONTRACT_STEP1="1.Call 1-800-376-6960";   
    public final static String CALL_IVR_MSG_ON_CONTRACT_STEP2="2.Enter Tech ID# ";
    public final static String CALL_IVR_MSG_ON_CONTRACT_STEP3="3.Enter SO ID# ";
    public final static String CALL_IVR_MSG_ON_CONTRACT_STEP4="4.Select \"Arrival\" or \"Departure\"";
    public static final String SERVICE_LOCATION_CONTACT_MSG_FOR_RI="* Please confirm the service time with the service location contact.";
    public static final String PRODUCT_INFORMATION="PRODUCT INFORMATION";
    public static final String DIVISION="DIVISION:";
    public static final String ITEM_NUMBER="ITEM NUMBER:";
    public static final String PRODUCT_DESCRIPTION="DESCRIPTION:";
    public static final String QUESTIONS_AND_CONCERNS="\nQuestions or concerns about your installation? Call 1-800-326-8738  |  Hours (CST): M-F 6:30a-10p, Sa 6:30a-9p, Su 9a-8p";                                                                         
    public static final String QUESTIONS_AND_CONCERNS_CONTD="An installation representative is available to resolve any questions or concerns you have regarding installation or damage to your product.";
    public static final String SALES_CHECK_NUM="SALES CHECK NUM 1";
    public static final String SALES_CHECK_DATE ="SALES CHECK DATE 1";   
    public static final String SALES_CHECK_TIME= "SALES CHECK TIME 1";
    public static final String SELLING_ASSOC= "SELLING ASSOC";
    public static final String ORDER_NUMBER= "ORDER NUMBER";
    public static final String SERVICE_REQUESTED= "SERVICE REQUESTED";
    public static final String PROCESS_ID= "ProcessID";
    public static final String[] CUSTOM_REF_LIST={"SALES CHECK NUM 1","SALES CHECK DATE 1","SALES CHECK TIME 1","SELLING ASSOC","ORDER NUMBER","SERVICE REQUESTED"};
    public static final String[] CUSTOM_REF_LIST_FOR_PROV = {"PICKUP LOCATION CODE","Merchandise Availability Date","ProcessID"};
    public static String CHECKBOX_PICTURE_FILEPATH = "resources/images/checkboxEnabled.gif";
    public static final String NOTICE_TO_CUSTOMER = "NOTICE TO CUSTOMER: A city permit may be required for the installation of a water heater or other installed appliance within your city limits. If a permit is required, you will be billed separately for the cost of the permit. The Sears Authorized contractor who performs the installation will requestthe permit from your city. Please contact your municipality following installation for inspection requirements.";
    public static final int POSTED_STATUS = 110;
    public static final  int TASK_COUNT_ONE = 1;
    public static final String NOTICE_TO_CUSTOMER_TITLE = "NOTICE TO CUSTOMER";
    public static final String PERIOD = "\\.";
    public static final String ZERO = "0";
    public static final String BLANK_LINE = "_____________";
    public static final String BLANK_LINE_FOR_PERMIT_PRICE ="$________";
    public static final String FINAL_PERMIT_PRICE = "FINAL PERMIT PRICE";
    public static final String END_OF_LINE = ".";
    public static final String DOLLAR = "$";
   
    //added for sl-15642
    public static final String ASSIGNMENT_TYPE_FIRM = "FIRM";
    public static final String ASSIGNMENT_TYPE_PROVIDER ="PROVIDER";
    public static final String PROVIDER_UNASSIGNED ="Unassigned";
    public static final String PROVIDER_UNASSIGNED_WARNING_1 = "** IMPORTANT:You MUST have the Service Provider assigned to complete the IVR process. **";
   
   
   
    //added for SL-8937
    public static final String PERMIT_PRICE_TEXT = "PERMIT PRICE PRE-PAID BY CUSTOMER";
    public static final String PERMIT_SKU = "99888";
    public static final String BLANK_SPACE_LONG="                                                  ";
    public static final String CHANGE_OF_SPECS="CHANGE OF SPECS";
    public static final String PERMIT="PERMIT";
   
    //added for SL-17429
        public static final String BUYER_CONT = "BUYER CONTACT";
        // Start--Made changes to solve JIRA SL-18192
        public static final String APPOINTMENT_DATE = "APPOINTMENT DATE";
        // End--Made changes to solve JIRA SL-18192
        public static final String SERVICE_DATE = "SERVICE DATE";
        public static final String SERVICE_TIME = "SERVICE TIME";
        public final static String ADDITIONAL_INFO="ADDITIONAL INFORMATION";
        public final static String SO_OVERVIEW="SERVICE ORDER OVERVIEW";
        public static final String SERVICE_ORDER_DETAILS="SERVICE ORDER DETAILS";
        public static final String ARROW=">";
        public final static String DEPARTURE="Departure: ____________  ";
        public final static String TIME_ON_SITE="TIME ON SITE";
        public final static String ARRIVAL="Arrival: ____________  ";
        public static final String AM = "AM";
        public static final String PM="PM";
        public static final String PICK_UP_LOCATION="PICK UP LOCATION";
        public final static String WAIVER_SIGNATURE_CUSTOMER="                           _____________________________           _____________________________             _____________________________\n                              Service Provider Name (print)                     Service Provider Signature                                            Date  ";
        public final static String SIGNATURE_TEXT           ="                           _____________________________           _____________________________             _____________________________\n                                  Customer  Name (print)                                 Customer Signature                                                  Date  ";

        public final static String THANK_YOU_STRING_LOWERCASE="\nThank you for allowing us to serve you. We truly appreciate your business!";                                                
        public static final String BIG_SPACE =   "       ";
        public static final String MED_SPACE =   "      ";
        public static final String LARGE_SPACE = "        ";
        public static final String CENTER_SPACE ="      ";
        public static final String ALLIGN_SPACE ="     ";
        public static final String SMALL_SPACE = "   ";
        public static final String MEDIUM_SPACE = "   ";
       
        public final static String WAIVER_HEAD="WAIVER OF LIEN ";
        public final static String WAIVER_CUST="(to be signed by the Service Provider and Customer)";
        public final static String WAIVER_CUST_SIGNED= "(Signed by the Service Provider and Customer)";
        public final static String WAIVER_CUST_RECEIPT="(Signed by the Service Provider and Customer)";
        public static String DEFAULT_DATE_ICON_FILEPATH = "resources/pdficons/icon-calendar.png";
        public static String DEFAULT_HOME_ICON_FILEPATH = "resources/pdficons/icon-home.png";
        public static String DEFAULT_COMMENT_ICON_FILEPATH = "resources/pdficons/icon-comment.png";
        public static String DEFAULT_OVERVIEW_ICON_FILEPATH = "resources/pdficons/icon-file.png";
        public static String DEFAULT_INFO_ICON_FILEPATH = "resources/pdficons/icon-info-sign.png";
        public static String DEFAULT_DETAILS_ICON_FILEPATH = "resources/pdficons/icon-list-ul.png";
        public static String DEFAULT_WAIVER_ICON_FILEPATH = "resources/pdficons/icon-pencil.png";
        public static String DEFAULT_USER_ICON_FILEPATH = "resources/pdficons/icon-user.png";
        public static String DEFAULT_PICKUP_ICON_FILEPATH ="resources/pdficons/icon-truck.png";
        public static String DEFAULT_PARTS_ICON_FILEPATH ="resources/pdficons/icon-cogs.png";
        public static String DEFAULT_BUYER_TC_ICON_FILEPATH ="resources/pdficons/icon-warning-sign.png";
        public static String DEFAULT_TIME_ICON_FILEPATH="resources/pdficons/icon-time.png";
        public static String DEFAULT_PHONE_ICON_FILEPATH="resources/pdficons/icon-phone-sign.png";
        public static String DEFAULT_EMAIL_ICON_FILEPATH="resources/pdficons/icon-envelope-sign.png";
        public static String DEFAULT_ARRIVAL_DEPARTURE_ICON_FILEPATH="resources/pdficons/icon-retweet.png";
       
        public static final Font FONT_LABEL_S = FontFactory.getFont(FontFactory.HELVETICA,7);
        public final static String FOOTER_PAGE = "Page";
        public final static String FOOTER_SO = "Service Order ";
        public final static String FOOTER_HASH = "#";
        public static final String BUYER_T_C="BUYER TERMS & CONDITIONS";
        public static final Font FONT_LABEL_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
        public static final String PART_PC=" Part ";
        public static final String MANUFACTURER_PC=" Manufacturer ";
        public static final String MODEL_PC=" Model ";
        public static final String DESCRIPTION_PC=" Description ";
        public static final String QUANTITY_PC=" Quantity ";
        public static final String PROVIDED_BY_PC=" Provided By ";
        public static final String ARRIVAL_DEPARTURE="ARRIVAL & DEPARTURE INSTRUCTIONS";
        public static final String WHEN_DEPARTING = "When departing:";
        public static final String UP_ON_ARRIVAL = "Upon Arrival:";
        public static final String ARRIVAL_STEP_1_1 = "1. Call the ServiceLive IVR ";
        public static final String ARRIVAL_STEP_1_2 ="800-376-6960";
        public static final String ARRIVAL_STEP_3 = "3. Enter SO ID# ";
        public static final String ARRIVAL_STEP_2 = "2. Enter Tech ID# ";
        public static final String ARRIVAL_STEP_4 = "4. Select \"Arrival\"";
        public static final String DEPARTURE_STEP_1_1 = "4. Select either";
        public static final String DEPARTURE_STEP_1_2 = " \"Departure, no issues\" ";
        public static final String OR ="or";
        public static final String DEPARTURE_STEP_1_3 = "\"Departure with issues\"";
        public static final String DEPARTURE_STEP_2 = "If departing with issues, select the appropriate reason for the issue";
        public static final String WORK = " (w)";
        public static final String MOBILE = " (m)";
        public static final String HOME = " (h)";
        public static final String EMAIL = " (e)";
        public static final String FAX = " (f)";
        public static final String PAGER = " (pg)";
        public static final String OTHER = " (o)";
        public static final String BUYER_CONTACT="Buyer Contact:";
        public static final String SL_IVR="ServiceLive IVR:";
        public static final String SL_IVR_NO="800-376-6960";
        public static final String SL_SUPPORT="ServiceLive Support:";
        public static final String SL_SUPPORT_NO="888-549-0640";
        public static final String SO_SUPPORT_NO="SERVICE ORDER SUPPORT NUMBERS";
        public final static String ADDN_WORK_PERFORMED="ADDITIONAL WORK PERFORMED / SERVICE PROVIDER NOTES";
        public static final String BUYER_PART="Buyer";
        //logo Image
        public static int DEFAULT_LOGO_PICTURE_WIDTH = 200;
        public static int DEFAULT_LOGO_PICTURE_HEIGHT = 55;
       
        public static int DEFAULT_SIGNATURE_WIDTH = 120;
        public static int DEFAULT_SIGNATURE_HEIGHT = 80;
       
        /* SL-18506 Permission to Sell Content for Inhome*/
        public final static String LEARN_ABT_SEARS_PRODUCTS_AND_SERVICES_HEAD="LEARN ABOUT SEARS PRODUCTS & SERVICES";
        public final static String GET_INITIAL = "Please initial 1, 2, ";
        public final static String GET_INITIAL_OR = "or";
        public final static String GET_INITIAL_PART    = " 3 below:";
        public final static String SEARS_PRODUCTS_AND_SERVICES_1     = "     1. I would like to learn more about other Sears products and services,and I agree to let the technician connect me with a Sears associate. I understand I am \n";
        public final static String SEARS_PRODUCTS_AND_SERVICES_1_SUB = "         under no obligation to purchase any additional products or services.  Customer initial: ____________\n";
        public final static String SEARS_PRODUCTS_AND_SERVICES_2 = "     2. I would prefer to call Sears on my own.  Customer initial: ____________\n";
        public final static String SEARS_PRODUCTS_AND_SERVICES_3 = "     3. I am not interested in any additional products and services from Sears at this time.  Customer initial: ____________\n";
        public final static int BUYER_INHOME_ID = 3000;
        public final static String THANK_YOU_STRING_CENTER_LOWERCASE ="\n                                      Thank you for allowing us to serve you. We truly appreciate your business!";
        /*RI Changes*/
        public final static String THANK_YOU_STRING_CENTER="                       THANK YOU FOR ALLOWING US TO SERVE YOU. WE TRULY APPRECIATE YOUR BUSINESS!";
        public final static String QUESTIONS_AND_CONCERNS_CENTER="                      Questions or concerns about your installation? Call 1-800-326-8738  |  Hours (CST): M-F 6:30a-10p, Sa 6:30a-9p, Su 9a-8p";
        //public final static String QUESTIONS_AND_CONCERNS_CONTD_CENTER="               An installation representative is available to resolve any questions or concerns you have regarding installation or damage to your product.";
        public final static String QUESTIONS_AND_CONCERNS_CONTD_CENTER="               Questions or concerns? Please refer to your receipt for the contact phone number to call about any concerns you may have regarding \n";
        public final static String QUESTIONS_AND_CONCERNS_CONTD_CENTER_NEXT="               your delivery/installation or damage to your product.";
        public final static String PART_NUMBER = "Part Number";
        public final static String PART_NAME = "Part Name";
        public final static String PART_STATUS = "Part Status";
        public final static String PART_INVOICE_NUMBER = "Invoice Number";
        public final static String PART_QTY = "Qty";
        public final static String PART_UNIT_COST= "Unit Cost($)";
        public final static String PART_EST_PAYMENT= "Est Net Provider Payment($)";
        public final static String PART_RETAIL_PRICE= "Retail Price($)";
        public final static String PART_INSTALLED= "Installed";
        public static final String SEARS_HOME_SERVICES = "Sears Home Services Repair Visit Summary";
        public static final String SEARS_REPAIR_VISIT_SUMMARY = "Sears Repair Visit Summary";

        public static final String APPOINTMENT = "APPOINTMENT";

        public static final String CONTACT_US = "CONTACT US";
        public static final String MYHOME_CONTACT_US = "1-800-4MY-HOME";
        public static final String SERVICE_PROVIDER_NOTES = "SERVICE PROVIDER NOTES";

        public static final int SEARS_BUYER = 1000;
        public static final String SEARS_CONTACT_US = "1-800-326-8738";
       
        public static final String REF_MODEL = "Model";
        public static final String REF_MODEL_DISPLAY = "Model Number";
        public static final String REF_SERIAL= "SerialNumber";
        public static final String REF_SERIAL_DISPLAY= "Serial Number";
        public static final String REF_BRAND= "Brand";
        public static final String REF_CUSTOMER_NUMBER= "CustomerNumber";
        public static final String REF_CUSTOMER_NUMBER_DISPLAY= "Customer Number";
        public static final String REF_COVERAGE_PARTS= "CoverageTypeParts";
        public static final String REF_COVERAGE_PARTS_DISPLAY= "Coverage Type Parts";
        public static final String REF_COVERAGE_LABOR= "CoverageTypeLabor";
        public static final String REF_COVERAGE_LABOR_DISPLAY= "Coverage Type Labor";
        
        //SL-20807
        public static final String SURVEY_MESSAGE1 = "\n\nDID OUR SERVICE EARN A \"9\" TODAY?\n";
        public static final String SURVEY_MESSAGE2 = "\nTHANK YOU for choosing Sears Home Services for today's repair. Because you are a valued Sears Member, you will soon receive a telephone survey about your overall experience with this repair. We would be grateful if you could please take the time to complete the survey and rate this service experience a \"9\", meaning that you are very satisfied overall and would be likely to recommend our service to family and friends.\n";
        public static final String SURVEY_MESSAGE3 = "\nIf there is anything else that we must do to earn a \"9\" on this repair experience, please let your service technician know during today's visit.";
        //SL-21886 
        public static final String SURVEY_MESSAGE4 = "\nYour feedback is very important!  Please check your Inbox, Spam and Junk folders for a Customer Satisfaction survey email from us.";
        
        //SL-20760
        public static final String NEW_CUSTOMER_PHONE_NO="8663914468";
        public static final int CONSTELLATION_HOME_BUYER_ID=513539;
        
        public static final int STRING_LEN=18;
        public static final int STRING_LEN_ZERO=0;
        public static final int STRING_LEN_SEVENTEEN=17;
        public static final String CONSTELLATION_BUYER_PHONE_NO="constellation_buyer_phone_number";
        public static final String CONSTELLATION_BUYER_EMAIL_ADDRESS="constellation_buyer_email_address";
        
        //SL-20856
        public final static String CONSTELLATION_BUYER_SAVINGS="Your Constellation Home Service Policy just saved you $______________!"; 
        
        //SL-20728
        public static final String HTML_CHARS_PROPERTY_FILE = "resources/properties/html_to_xml_characters.properties";
        public static final HashMap<String,String> htmlToXmlCharMap = new HashMap<String,String>();
        static{
        	Properties properties = new Properties();
    		try{
    			InputStream is = SOPDFConstants.class
    			.getClassLoader()
    			.getResourceAsStream(HTML_CHARS_PROPERTY_FILE);
    			properties.load(is);
    			
    			for (final Entry<Object, Object> entry : properties.entrySet()) {
    				htmlToXmlCharMap.put((String) entry.getKey(), (String) entry.getValue());
    			}
    		}catch(Exception e){
    			logger.error("Exception while trying to read values from property file: " + e);
    		}
    	}
        public static final String HTML_TAG = "<html>";
        public static final String HTML_END_TAG = "</html>";
        public static final String STRONG_TAG = "strong";
        public static final String BOLD_TAG = "b";
    	public static final String ITALICS_TAG = "em";
    	public static final String I_TAG = "i";
    	public static final String STYLE = "style";
    	public static final String UNDERLINE_ATT = "underline";
    	public static final String SPAN_TAG = "span";
    	public static final String LIST_TAG = "li";
    	public static final String ORDERED_LIST_TAG = "ol";
    	public static final String UNORDERED_LIST_TAG = "ul";
    	public static final String PARA_TAG = "p";
    	public static final String BREAK_TAG = "br";
    	public static final String BULLET = new String(new char[] { (char) 0xb7 });
    	public static final String FONT_SIZE = "font-size: ";
    	public static final String TEXT_TITLE = "title";
    	public static final String HREF = "href";
    	public static final String HEADING_1 = "h1";
    	public static final String HEADING_2 = "h2";
    	public static final String ALLIGN = "text-align: ";
    	public static final String ANCHOR_TAG = "a";
    	public static final String ALLIGN_LEFT = "left";
    	public static final String ALLIGN_RIGHT = "right";
    	public static final String ALLIGN_MEDIUM = "center";
    	public static final String INDENT = "padding-left: ";
    	public static final String ALLIGN_JUSTIFY = "justify";
    	public static final String HEADING_3 = "h3";
    	public static final String HEADING_4 = "h4";
    	public static final String HEADING_5 = "h5";
    	public static final String HEADING_6 = "h6";
    	public static final String PERIODS = ".";
    	public static final String UL_TAG = "<ul>";
    	public static final String UL_END_TAG = "</ul>";
    	public static final String LI_TAG = "<li>";
    	public static final String LI_END_TAG = "</li>";
    	public static final String START_LT = "<";
    	public static final String START_GT = ">";
    	public static final String END_LT = "</";
    	public static final String FONT_PT = "pt;";
    	public static final String FONT_PX = "px;";
    	public static final String EMPTY = "";
    	public static final String UNDERLINE_TAG = "u";
    	public static final String NO_ATTRIBUTE = "NO_ATTRIBUTE";
    	
    	public static final Map<String, Character> SPECIAL_ENTITIES;
        
        static {
            SPECIAL_ENTITIES = new HashMap<String, Character>();            
            SPECIAL_ENTITIES.put("quot", '"');
            SPECIAL_ENTITIES.put("gt", '>');
            SPECIAL_ENTITIES.put("lt", '<');
            SPECIAL_ENTITIES.put("amp", '&');
            
        }
        
}