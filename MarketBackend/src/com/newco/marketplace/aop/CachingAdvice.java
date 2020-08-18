/**
 * 
 */
package com.newco.marketplace.aop;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.cache.ICacheManagerBO;
import com.newco.marketplace.dto.vo.cache.event.CacheEvent;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.AOPConstants;

/**
 * @author schavda
 *
 */
public class CachingAdvice {
	private static final Logger logger = Logger.getLogger(CachingAdvice.class.getName());
	private ICacheManagerBO cacheManagerBO;
		

	public void callCacheEvent(Map<String, Object> hmArguments) throws BusinessServiceException{
		try{
			Class<?> cls = Class.forName((String)hmArguments.get(AOPConstants.AOP_CACHING_EVENT_CLASS));
			//Class classType[] = new Class[1];
			Constructor<?> constructor = cls.getConstructor(HashMap.class);
			Object eventObject = constructor.newInstance(hmArguments);
			cacheManagerBO.handleEvent((CacheEvent)eventObject);
			
		}
		catch(Exception e){
			logger.warn("CachingAdvice-->callCacheEvent()-->EXCEPTION-->"+e.getMessage()); // Don't log stack trace unnecessarily
			throw new BusinessServiceException(e);
		}
	}
	
	/**
	 * @return the cacheManagerBO
	 */
	public ICacheManagerBO getCacheManagerBO() {
		return cacheManagerBO;
	}

	/**
	 * @param cacheManagerBO the cacheManagerBO to set
	 */
	public void setCacheManagerBO(ICacheManagerBO cacheManagerBO) {
		this.cacheManagerBO = cacheManagerBO;
	}
}
