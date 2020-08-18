/**
 * File Name: TestData.java
 **/
package com.newco.marketplace.persistence.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * 		File Name: TestData.java ,  
 * 		Created By: Munish Joshi  ,
 * 		Date: Jun 25, 2009    
 **/
public class TestCache {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		QueryCacheImpl queryCacheImpl = QueryCacheManager.getCache();
		List params = new ArrayList();
		params.add("param1");
		params.add("param2");
		params.add(2);
		QueryKey queryKey = new QueryKey("Key1", params);
		Object result = queryCacheImpl.get(queryKey);

		if (result != null) {
			System.out.println("Got from HashMap");
			queryCacheImpl.put(queryKey, result);
		} else {
			System.out.println("Got from the database");
			queryCacheImpl.put(queryKey, "Got from the cache");
		}
		
		List params2 = new ArrayList();
		params2.add("params2");
		QueryKey queryKey2 = new QueryKey("Key1", params2);
		Object result2 = queryCacheImpl.get(queryKey2);
		
		if (result2 != null) {
			System.out.println("Got from HashMap");
			queryCacheImpl.put(queryKey2, result2);
		} else {
			System.out.println("Got from the database");
			queryCacheImpl.put(queryKey2, "Got from the cache");
		}
		
		List params3 = new ArrayList();
		params3.add("param1");
		params3.add("param2");
		params3.add(2);
		QueryKey queryKey3 = new QueryKey("Key1", params3);
		Object result3 = queryCacheImpl.get(queryKey3);
		
		if (result3 != null) {
			System.out.println("Got from HashMap");
			queryCacheImpl.put(queryKey3, result3);
		} else {
			System.out.println("Got from the database");
			queryCacheImpl.put(queryKey3, "Got from the cache");
		}
		
	}

}
