package com.servicelive.orderfulfillment.signal;

public interface ProcessSignalStep {

	public final static String PSS_GETPROCESSRECORD = "SO record retreival from the process table";
	public final static String PSS_GETWFID = "WF process id retreival";
	public final static String PSS_GETSIGNAL = "signal object lookup";
	public final static String PSS_GETSO = "SO retrieval from the database";
	public final static String PSS_AUTHORIZE = "signal authorization";
	public final static String PSS_TRANSITIONCHECK = "transition validation";
	public final static String PSS_SIGNALPROCESS = "signal processing";
	public final static String PSS_WFTRANSITION = "WF transition";
	public final static String PSS_SOLOG = "SO logging";
	public final static String PSS_SAVESO = "SO saving in the DB";
	
}
