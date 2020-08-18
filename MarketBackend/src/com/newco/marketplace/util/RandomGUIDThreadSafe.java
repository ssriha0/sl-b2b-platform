package com.newco.marketplace.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.utils.RandomGUID;

public class RandomGUIDThreadSafe extends RandomGUID {
	private static final Logger logger = Logger.getLogger(RandomGUIDThreadSafe.class.getName());
	private static RandomGUIDThreadSafe instance;
	
	private HashMap<Long, Long> cache;
	
	private RandomGUIDThreadSafe() {
		super();
		cache = new HashMap<Long, Long>();
	}
	
	public static final RandomGUIDThreadSafe getInstance() {
		if (instance == null) {
			synchronized (RandomGUIDThreadSafe.class) {
				if (instance == null) 
					instance =  new RandomGUIDThreadSafe();
			}
		}		
		return instance;		
	}
	
	@Override
	public synchronized Long generateGUID() throws Exception {
		Long rnumber = super.generateGUID();
		for (int i = 0 ; i < 5; i++) {
			if (cache.get(rnumber) == null) {
				putInCache(rnumber);
				return rnumber;
			} else {
				logger.info("Found duplicate number, generating new.");
				rnumber = super.generateGUID();	
			}
		}		
		return rnumber;		
	}
	
	private void putInCache(Long number) {
		if (cache.size() > 500)
			cache = new HashMap<Long, Long>();
		cache.put(number, number);
	}
}
