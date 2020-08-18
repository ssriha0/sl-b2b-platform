package com.newco.marketplace.webservices.base;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.invoker.BeanInvoker;

import com.sears.os.business.ABaseBO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 00:51:53 $
 *
 */
public abstract class AbstractServiceInvoker extends SerializableBaseVO{
	
	private static final Logger logger = Logger.getLogger(AbstractServiceInvoker.class.getName());
	private static final String SERVICE_NAME = "CommonMarketBusinessService";
	
	public Service getService(MessageContext message)
	{
		Service marketService = 
			message.getXFire().getServiceRegistry().getService(SERVICE_NAME);
		return marketService;
	}
	
	public Service setInvocationBusinessBean( Service service, ABaseBO businessBean )
	{
		service.setInvoker(new BeanInvoker(businessBean));
		return service;
	}
	
	public Method getBusinessBeanMethod( Class clazz, String methodName, Class[] parameters)
	{
		Method m = null;
		try {
			 m = clazz.getDeclaredMethod(methodName, parameters);
		} catch (SecurityException e1) {
			logger.info("Caught Exception and ignoring",e1);
		} catch (NoSuchMethodException e1) {
			logger.info("Caught Exception and ignoring",e1);
		}
		return m;
	}
}
