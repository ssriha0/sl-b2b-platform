/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-Oct-2009	ProviderSearch  SHC				1.0
 */
package com.newco.marketplace.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.newco.marketplace.search.types.Bucket;

/**
 * This class acts as an utility class.
 * 
 * @author pgangra
 * @version 1.0
 */

public class CommonUtility {
	private static final String PROPERTY_FILE="properties/search.properties"; 
	private static Logger logger = Logger.getLogger(CommonUtility.class);

	private static final Properties properties;
	private static final HashMap<String, String> map = new HashMap<String, String>();

	// The below static block will load the property file only once
	static{
		properties = new Properties();
		try{
			InputStream is = CommonUtility.class
			.getClassLoader()
			.getResourceAsStream(PROPERTY_FILE);
			properties.load(is);
			loadProperties();
		}catch(IOException e){
			logger.error("IO Exception while trying to load property file: " + e);
		}catch(Exception e){
			logger.error("Exception while trying to load property file: " + e);
		}
	}
	
	/**
	 * Static method for getting the internationalized message value for a 
	 * particular property code.
	 * 
	 * @param errorCode
	 * @return String errorMessage 
	 */
	public static String getMessage(String code) {
		return properties.getProperty(code);
	}
	
	
	private static void loadProperties() {
		for(Object key:properties.keySet()) {
			map.put(key.toString(), getMessage(key.toString()));
		}
	}
		
	
	public static void main(String args[]) {
		CommonUtility util = new CommonUtility();
		util.loadFacets(map);
	}
	
	public static HashMap<String, String> getPropertiesAsMap() {
		return map;
	}
	
	private void loadFacets (HashMap<String, String> map2) {
		List<Bucket> facetList = new ArrayList<Bucket>();
		for (String key: map2.keySet()) {			
			Bucket bucket = null;
			
			//populate provider ratings bucket
			if (key.toString().startsWith("provider.ratings.bucket")) {				
				bucket = new Bucket(Bucket.RATING_TYPE);
			} else if(key.toString().startsWith("provider.distance.bucket")) { 		
				//populate provider distance bucket				
				bucket = new Bucket(Bucket.DISTANCE_TYPE);
			} else if(key.toString().startsWith("provider.language.bucket")) {
				bucket = new Bucket(Bucket.LANGUAGE_TYPE);
			} else {
				continue;
			}
			
			String keyArray[] = key.toString().split("-");
			String valueArray[] = CommonUtility.getMessage(key.toString()).split(":");
			
			if(keyArray != null && keyArray.length>1) {
				bucket.setSortOrder(new Integer(keyArray[1]));
			}
			
			if(valueArray!=null) {
				bucket.setStartRange(valueArray[0]);
				if(valueArray.length>1) {
					bucket.setEndRange(valueArray[1]);
				}
			}
			facetList.add(bucket);
			System.out.println(bucket);
		}
	}
	
}


