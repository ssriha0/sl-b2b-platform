package com.servicelive.serviceordercreation;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.servicelive.serviceordercreation.PropertyManagerUtil;

public class PropertyManagerUtilTest {
	private static Logger logger = Logger.getLogger(PropertyManagerUtilTest.class);
	private static String propertyFileName = "../serviceOrderCreation.properties";
	private static String key = "numberOfServiceOrdersToCreate";
	private static String originalValue = "20";
	private PropertyManagerUtil propertyManagerUtil;
	
	@Test
	public void getValueFromPropertyFile(){
		propertyManagerUtil = new PropertyManagerUtil(propertyFileName);
		String value = null;
		try{
			value = propertyManagerUtil.getProperty(key);
		}catch(Exception e){
			logger.error("Exception in getting value for key:"+ e.getMessage());
		}
		Assert.assertEquals("Value extraction failed",originalValue, value);
	}
  
}
