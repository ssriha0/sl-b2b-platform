package com.newco.marketplace.interfaces;

public interface ClientConstants {
	
	// Assurant related Constants
	public static final String ASSURANT_CLIENT_NAME = "Assurant";
	public static final String BUYER_STATUS = "buyer_status";
	public static final String COMMENTS= "comments";
	public static final String BUYER_SUBSTATUS_ASSOC_ID = "BUYER_SUBSTATUS_ASSOC_ID";
	public static final String DISPATCHED_RECEIVED_STATUS = "Dispatch Received";
	public static final String PARTS_SHIPPED_STATUS = "Parts Shipped";
	public static final String PARTS_SHIPPED_COMMENTS = "Parts Have Shipped";
	
	public static final String CLOSED_STATUS = "Closed";
	public static final String CLOSED_COMMENTS = "ServiceLive creating second service event";
	public static final String SP_REOPEN_STATUS = "SP Reopen";
	public static final String SP_REOPEN_COMMENTS = "ServiceLive has reopened the incident for continued service";
	public static final int PARTS_DESC_LENGTH = 35;


	//FOR OUTFILE
	public static final int ASSURANT_OUTFILE_DESC_MAX_LENGTH = 3000;

}
