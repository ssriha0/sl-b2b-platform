package com.sears.os.service;


public interface ServiceConstants {
	
	//service messages
	public static final String METHOD_START_MESSAGE = "message.ABaseService.methodStart";
	public static final String METHOD_END_MESSAGE = "message.ABaseService.methodEnd";
			
	public static final String VALID_RC = "00";
	public static final String WARNING_RC = "04";
	public static final String USER_ERROR_RC = "08";
	public static final String SYSTEM_ERROR_RC = "12";
	public static final String TOKENIZATION_ERROR_RC="14";


	public static final String VALID_MSG = "SUCCESS";
	public static final String WARNIG_ERROR_MSG = "WARNING";
	public static final String USER_ERROR_MSG = "USER ERROR";
	public static final String SYSTEM_ERROR_MSG = "SYSTEM ERROR";

	//SLT-2227 Wallet control audit notes
		public static final String SUCCESS="success";
		public static final String ACCOUNT_UNDER_WALLET_CONTROL="Account is under";
		public static final String ACCOUNT_RELEASED__WALLET_CONTROL="Account is released from";
		public static final String WALLET_CONTROL_EMAIL_SENT="Email sent to provider";
		public static final String WALLET_CONTROL_EMAIL_NOT_SENT="Email not sent to provider";
		public static final String DOLLAR_SYMBOL="$";
		public static final String HYPHEN_SYMBOL="-";
}
