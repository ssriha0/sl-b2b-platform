package com.newco.marketplace.persistence.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * File Name: QueryCache.java , Created By: Munish Joshi , Date: Jun 24, 2009
 * 
 */
 
public class QueryCacheImpl implements ICache {

	final static Map<Object, Object> cache = new HashMap<Object, Object>();


	private static final Logger logger = Logger.getLogger(QueryCacheImpl.class
			.getName());

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.cache.Cache#get(java.lang.Object)
	 */
	public Object get(final Object queryKey) {
		
		
		Object result = cache.get(queryKey);
	
		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object key, Object value) {
			cache.put(key, value);
			return key;
	}
	
	
	

}
