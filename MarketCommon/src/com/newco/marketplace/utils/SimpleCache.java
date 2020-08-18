package com.newco.marketplace.utils;

/*
 * SimpleCache
 * Copyright (C) 2008 Christian Schenk
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.swing.internal.plaf.synth.resources.synth;


/**
 * This class provides a very simple implementation of an object cache.
 * 
 * @author Christian Schenk
 * 
 * Modified by Shekhar Nirkhe
 */
public class SimpleCache  {

	/** Objects are stored here */
	public final Map<String, Object> objects;
	/** Holds custom expiration dates */
	private final Map<String, Long> expire;
	/** The default expiration date */
	private final long defaultExpire;
	/** Is used to speed up some operations */
	//private final ExecutorService threads;
	private static SimpleCache cache;
	
	public static final long ONE_MINUTES = 60 * 1;
	public static final long TWO_MINUTES = ONE_MINUTES * 2;
	public static final long THREE_MINUTES = ONE_MINUTES * 3;
	public static final long FIVE_MINUTES = ONE_MINUTES * 5;
	public static final long TEN_MINUTES = ONE_MINUTES * 10;
	public static final long THIRTY_MINUTES = TEN_MINUTES * 3;
	public static final long ONE_HOUR = ONE_MINUTES * 60;
	
	private static boolean cacheEnabled = true;
	
	private static final Logger logger = Logger.getLogger(SimpleCache.class.getName());
	private static final int maxLimit = 30000;
	 
	/**
	 *  * Constructs the cache with a default expiration time for the objects of
	 * 100 seconds.
	 * 
	 * 
	 * @param defaultExpire
	 *            default expiration time in seconds
	 */
	
	private SimpleCache() {
		this.objects = Collections.synchronizedMap(new HashMap<String, Object>());
		this.expire = Collections.synchronizedMap(new HashMap<String, Long>());
		this.defaultExpire = THIRTY_MINUTES;		
	}

	public static final SimpleCache getInstance() {
		if (cache == null) {
			synchronized (SimpleCache.class) {
				if (cache == null) 
				  cache =  new SimpleCache();
			}
		}
		if (!cacheEnabled) {
			logger.info("SimpleCache is disabled");
		}
		return cache;		
	}
	
	 
	
	
	/**
	 * Returns a runnable that removes a specific object from the cache.
	 * 
	 * @param name
	 *            the name of the object
	 */
	public synchronized void remove(final String name)  {
		if (cacheEnabled) {
			objects.remove(name);
			expire.remove(name);
		}
	}

	/**
	 * Returns the default expiration time for the objects in the cache.
	 * 
	 * @return default expiration time in seconds
	 */
	public long getExpire() {
		return this.defaultExpire;
	}

	/**
	 * Put an object into the cache.
	 * 
	 * @param name
	 *            the object will be referenced with this name in the cache
	 * @param obj
	 *            the object
	 */
	public void put(final String name, final Object obj) {		
		this.put(name, obj, this.defaultExpire);
	}

	/**
	 * Put an object into the cache with a custom expiration date.
	 * 
	 * @param name
	 *            the object will be referenced with this name in the cache
	 * @param obj
	 *            the object
	 * @param expire
	 *            custom expiration time in seconds
	 */
	public void put(final String name, final Object obj, final long expireTime) {
		if (cacheEnabled) {
			if (this.objects.size() > maxLimit) {
				cleanup();
			}			
			if (this.objects.size() <= maxLimit) {
				this.objects.put(name, obj);
				this.expire.put(name, System.currentTimeMillis() + expireTime * 1000);
			} else {
				logger.debug("-- Cache is full --");
			}
		}
	}

	/**
	 * Returns an object from the cache.
	 * 
	 * @param name
	 *            the name of the object you'd like to get
	 * @param type
	 *            the type of the object you'd like to get
	 * @return the object for the given name and type
	 */
	public Object get(final String name) {
		if (!cacheEnabled) {
			return null;
		}
		
		if (isExpired(name))
		 remove(name);
		
		return this.objects.get(name);
	}

	
	private boolean isExpired(final String name) {
		final Long expireTime = this.expire.get(name);
		if (expireTime == null) return false;
		if (System.currentTimeMillis() > expireTime) {		
			if (logger.isDebugEnabled())
				  logger.debug("Object " + name + " has expired");			
			return true;
		}
		return false;
	}
	/**
	 * Convenience method.
	 */
	@SuppressWarnings("unchecked")
	public <R extends Object> R get(final String name, final Class<R> type) {
		if (cacheEnabled) 
		  return (R) this.get(name);
		else
			return null;
	}
	
	private synchronized void cleanup() {
		if (logger.isDebugEnabled())
		  logger.debug("Doing cache cleanup:" + expire.size());	
		
		ArrayList<String> list = new ArrayList<String>();
		for (String key:expire.keySet()) {
			if (isExpired(key))
			  list.add(key);
		}
		
		for (String key:list) {			
			remove(key);
		}	
	}
	
	public static void main(String arg[]) throws InterruptedException {
		for (int i = 0; i < 20000 ; i++) {
			String key = "KK" + i;
			Thread.sleep(1);
			SimpleCache.getInstance().put(key, i, 1);			
		}	
		
		System.out.println("Size:" + SimpleCache.getInstance().objects.size());		
	}
}
