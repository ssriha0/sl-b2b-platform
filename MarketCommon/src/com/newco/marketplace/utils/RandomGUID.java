package com.newco.marketplace.utils;

import org.apache.log4j.Logger;

public class RandomGUID {
	public static final int LIMIT_INT = (int) Math.pow(2, 32) ; // MAX SIGNED 32 BIT NUMBER IS 2147483647 OR 2^31 - 1
	private static final Logger logger = Logger.getLogger(RandomGUID.class.getName());

	/**
	 * Returns 32-bit signed number that's guaranteed to be 10-digits
	 * MAX SIGNED 32 BIT NUMBER IS 2147483647 OR 2^31 - 1
	 * */
	public Long generateGUID()
		throws Exception
	{
		StringBuffer id = new StringBuffer();
		int timestamp = findBetweenMillionAndHundredMillion();
		id.append(String.valueOf(timestamp));
		id.insert(0, findIntBetweenHundredAndThousand());
//		logger.debug("generateGUID() "+id.toString());
		return Long.valueOf(id.toString());
	}
	private int findBetweenMillionAndHundredMillion()
	{
		int randomNo = (int)(Math.random()*10000000);
		if(randomNo < 1000000)
		{
			return findBetweenMillionAndHundredMillion();
		}
		if(randomNo == 10000000) 
		{
			return findBetweenMillionAndHundredMillion();
		}
//		logger.debug("findIntBetweenHundredAndThousand "+Integer.valueOf(randomNo).toString());
		return randomNo;
	}
	private int findIntBetweenHundredAndThousand()
	{
		int randomNo = (int)(Math.random()*1000);
		if(randomNo < 100)
		{
			return findIntBetweenHundredAndThousand();
		}
		if(randomNo >= 214)
		{
			return findIntBetweenHundredAndThousand();
		}
//		if(randomNo == 1000) 
//		{
//			return findIntBetweenHundredAndThousand();
//		}
//		logger.debug("findIntBetweenHundredAndThousand "+Integer.valueOf(randomNo).toString());
		return randomNo;
	}
	public Long _generateGUID() {
		
		// Generate 5-digit random # smaller than 21474
		int randomNo = 0;
		while(true) {
			randomNo = (int)(Math.random() * 21474);
			if(randomNo >= 10000) {
				break;
			}
		}
		
		// Append 5-digit random #
		StringBuilder sb = new StringBuilder();
		sb.append(randomNo);
		
		// Append 5-digit timestamp
		String timestamp = String.valueOf(System.currentTimeMillis());
		int length = timestamp.length();
		sb.append(timestamp.substring(length - 5, length));
		
		return Long.valueOf(sb.toString());
	}
	
}
