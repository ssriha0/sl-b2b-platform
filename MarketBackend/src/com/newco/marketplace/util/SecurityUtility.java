package com.newco.marketplace.util;
import java.io.FileInputStream;

import org.owasp.esapi.reference.crypto.DefaultEncryptedProperties;


public class SecurityUtility {
	/**
	 * returns the value from encrypted property file for a particular key
	 * @param String
	 * @param String
	 * @return String
	 */
	public static String getProperty(String filePath,String key)throws Exception{
		String value="";
		DefaultEncryptedProperties props = new DefaultEncryptedProperties();
		props.load(new FileInputStream(filePath));
		value = props.getProperty(key);
		return value;
	}

}
