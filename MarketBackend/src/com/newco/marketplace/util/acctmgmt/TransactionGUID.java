package com.newco.marketplace.util.acctmgmt;

import java.util.Calendar;

public class TransactionGUID {
	
	
	public  Integer getTransactionId() throws Exception {

		StringBuffer id = new StringBuffer();
		String sourceId = "";
		String ts = "";
		String random = "";

		try {
			// current time stamp is the 2nd part of the service order
			// it is at least 13 characters as of today...
			ts = String.valueOf(Calendar.getInstance().getTimeInMillis());
			int ran = (int)(Math.random()*100000000);
		
			if(ran < 0)
				ran = (ran * (-1));
			
			// remove first 3 chars 
			//ts = ts.substring(3, 7);

			// a random number is the 3rd part of the key
			//random = String.valueOf(Calendar.getInstance().getTimeInMillis());
			//random = StringUtils.substring(random, 0, 4);

			// construct the service order
			id.append(ran);
			
		} catch (Exception e) {
			
		}
		return new Integer(id.toString());
	}

}
