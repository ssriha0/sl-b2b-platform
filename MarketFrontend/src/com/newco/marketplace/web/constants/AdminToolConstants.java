package com.newco.marketplace.web.constants;

public class AdminToolConstants {

	public final static String ADJUSTMENT_COMMENTS_ERROR = "Please enter comments for this adjustment transaction.";
	public final static String ADJUSTMENT_IDS_ERROR = "Please enter ID(s) of record(s) to be adjusted.";
	public final static String COPY_COMMENTS_ERROR = "Please enter comments for this copy transaction";
	public final static String COPY_IDS_ERROR = "Please enter ID(s) of record to be copied.";
	public final static String AMOUNT_ERROR = "Please enter an amount greater than zero.";
	public final static String SEARCH_IDS_ERROR = "Please enter ID(s) to be searched for";
	public final static String RESENT_COMMENTS_ERROR="Please enter comments for the Fullfillment Group to be resent.";
	public final static String RESENT_IDS_ERROR="Please enter ID(s) of record(s) to be Resent.";
	public final static String GL_SUCCESS_MESSAGE="GL Process has been run successfully.";
	public final static String GL_FAILURE_MESSAGE="GL Process has failed.";
	public final static String NACHA_SUCCESS_MESSAGE="Nacha Process has been run successfully.";
	public final static String NACHA_FAILURE_MESSAGE="Nacha Process has failed. Please verify a file for the same date has not already been generated.";
	public final static String BALANCE_INQUIRY_SEND_FAILURE_MESSAGE="We were unable to send balance inquiries for this Vendor Id. Please verify that correct id has been entered.";
	public final static String BALANCE_INQUIRY_SUCCESS_MESSAGE="Balance Inquiry has been sent successfully.";
}
