/**
 * 
 */
package com.newco.marketplace.persistence.cache;

/**
 * 
 * File Name: Cache.java , Created By: Munish Joshi , Date: Jun 24, 2009
 * 
 */
 
public interface ICache {
	Object get(final Object key);
	Object put(final Object key, final Object value);
} 

