/**
 * 
 */
package com.newco.marketplace.util;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.sears.os.context.ContextValue;
import com.sears.os.factory.BeanFactory;

/**
 * @author schavda
 *
 */
public class PropertiesUtils {
	
	private static final Logger logger = Logger.getLogger(PropertiesUtils.class);
	static{
		ContextValue.setContextFile("resources/spring/applicationPropertiesContext.xml");
	}
	private static IApplicationPropertiesDao applicationPropertiesDao = (IApplicationPropertiesDao)BeanFactory.getBean("applicationPropertiesDao");
	
	
	public static String getPropertyValue(String key){
		ApplicationPropertiesVO prop = new ApplicationPropertiesVO();
		try{
			prop = applicationPropertiesDao.query(key);
		}
		catch(Exception e){
			logger.error("Unable to getPropertyValue(" + key + ")", e);
		}
		
		return prop.getAppValue();
	}
	public static String getFMPropertyValue(String key){
		String keyValue = "";
		try{
			keyValue = applicationPropertiesDao.queryByKey(key);
		}
		catch(Exception e){
			logger.error("applicationPropertiesDao.queryByKey(" + key + ")", e);
		}
		return keyValue;
	}

}
