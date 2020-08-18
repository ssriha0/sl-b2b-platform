/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.spring;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 *
 * @author hoza
 */
public class BeanPropertySetterBeanPostProcessor {
    //Log log = LogFactory.getLog(BeanPropertySetterBeanPostProcessor.class);
	private ConfigurableListableBeanFactory factory = null;
	private Map config = null;

	public void setConfig(Map config) {
		this.config = config;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		//log.debug("before: "+bean+", "+beanName);
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		//log.debug("after: "+bean+", "+beanName);
		// Now set the dependency
		if (config == null) return bean;
		Map beanConfig = (Map) config.get(beanName);
		if (beanConfig != null) {
			BeanInfo beanInfo = null;
			try {
				beanInfo = Introspector.getBeanInfo(bean.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				if (propertyDescriptors != null) {
					for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
						String propertyName = propertyDescriptor.getName();
						if (beanConfig.get(propertyName) != null) {
							String beanId = (String) beanConfig.get(propertyName);
//							if (log.isDebugEnabled()) {
//								log.debug("Found propertyDescriptor for " + beanName + ": " + propertyName);
//							}
							try {
								Object targetBean = factory.getBean(beanId);
								Method setter = propertyDescriptor.getWriteMethod();
								setter.invoke(bean, targetBean);
							} catch (NoSuchBeanDefinitionException nsbde) {
							//	log.error("Bean not found", nsbde);
							} catch (IllegalAccessException e) {
							//	log.error("IllegalAccessException", e);
							} catch (InvocationTargetException e) {
							//	log.error("InvocationTargetException", e);
							}
						}
					}
				}
			} catch (IntrospectionException ie) {
				//log.error("IntrospectionException", ie);
			}
		}
		return bean;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
		this.factory = factory;
	}


}
