package com.newco.marketplace.interfaces;

import java.util.Arrays;
import java.util.List;

public class CloseOrderConstants {
	
	public final static String TECHHUB_AUTH_TOKEN = "techhub.auth.token";

	public final static String TECHHUB_AUTH_URL = "techhub.auth.url";

	public final static String TECHHUB_CALL_CLOSE_URL = "techhub.call-close.url";
	public static final String TH_REQUEST_HEADER_AUTH="Authorization";
	public static final String TH_AUTH_TOKEN_HEADER="Basic";
	public static final String SPACE_STRING=" ";
	public static final String TH_API_TOKEN_HEADER="Bearer";
	public static final String HYPHEN_STRING="-";
	public static final String SO_ID_PARAM = "soId";
	
	public static final String ATTEMPT_REQUEST_MAPPING_ERROR= "Issue while Order Attempt Request Mapping";
	public static final String ATTEMPT_REQUEST_MAPPING_ERROR_CD= "0005";
	
	public static final List<String> errorCodes = Arrays.asList(ATTEMPT_REQUEST_MAPPING_ERROR_CD);
	
	public static final int IW_CODE = 10;
	public static final int SP_CODE = 20;
	public static final int CC_CODE = 30;
	public static final int PT_CODE = 50;

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String SO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static final Integer RAND_VAL = 999999;
	public static final Integer DIGIT = 7;
	public static final Integer ATTEMPT_COUNT = 1;
	
	public static final String REMITTENCE_CODE = "0008735";
	public static final Integer REMITTENCE_SUB_ACCNT = 951;
	public static final String DEFAULT_START_TIME = "08:00";
	public static final String DEFAULT_END_TIME = "08:30";
	public static final String DEFAULT_TIME_DIFF = "0030";
	public static final String PRIMARY_AMOUNT = "0.00";
	
	public static final String CUST_REF_COVERAGE_LABOR = "CoverageTypeLabor";
	public static final String CUST_REF_COVERAGE_PARTS = "CoverageTypeParts";
	public static final String CUST_REF_TEMPLATE_NAME = "TEMPLATE NAME";
	public static final String CUST_REF_PROCESS_ID = "ProcID";
	public static final String CUST_REF_ISP_ID = "ISP_ID";
	public static final String CUST_REF_BRAND = "Brand";
	public static final String CUST_REF_UNIT_NUMBER = "UnitNumber";
	public static final String CUST_REF_ORDER_NUMBER = "OrderNumber";
	public static final String CUST_REF_MODEL = "Model";
	public static final String CUST_REF_SERIAL_NUMBER = "SerialNumber";
	
	public static final String PMCHECK_TEMPLATE_NAME = "Preventative Maintenance Check";
	public static final String PMCHECK_JOBCODE = "95100";
	public static final String PMCHECK_CHARGE_AMT = "80.00";
	
	public static final String X = "X";
	public static final String Y = "Y";
	public static final String BLANK = "";
	
	public static final String IW = "IW";
	public static final String SP = "SP";
	public static final String CC = "CC";
	public static final String PT = "PT";
	public static final String PA = "PA";
	public static final String RELATED_FLAG="R";
	public static final String SEQUENCE="1";
	
	public static final String IW_PROC_ID = "SRW000";
	public static final String TECH_COMMENTS = "See ServiceLive order comments.";
}
