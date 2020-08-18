/**
 * File Name: QueryCacheManager.java
 **/
package com.newco.marketplace.persistence.cache;

/**
 * 		File Name: QueryCacheManager.java ,  
 * 		Created By: Munish Joshi  ,
 * 		Date: Jun 25, 2009    
 **/
public class QueryCacheManager {
	
	private static QueryCacheImpl cache = null;

	public QueryCacheManager() {
		// Exists only to defeat instantiation.
	}

	public static QueryCacheImpl getCache() {
		if (cache == null) {
			cache = new QueryCacheImpl();
		}
		return cache;
	}

	
	

}
