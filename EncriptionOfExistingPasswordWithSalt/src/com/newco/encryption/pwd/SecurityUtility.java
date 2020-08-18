package com.newco.encryption.pwd;
import java.io.FileInputStream;
import java.util.Properties;

public class SecurityUtility {
	/**
	 * @param String
	 * @param String
	 * @return String
	 */
	public static String getProperty(String filePath,String key)throws Exception{
		String value="";
		Properties props = new Properties();
		props.load(new FileInputStream(filePath));
		value = props.getProperty(key);
		return value;
	}

}
