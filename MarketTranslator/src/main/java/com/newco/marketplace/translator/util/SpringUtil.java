package com.newco.marketplace.translator.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
	
	private static final String CONTEXT_URI = "classpath:spring/mainApplicationContext.xml";
	public static BeanFactory factory;
	
	static {
		factory = new  ClassPathXmlApplicationContext(CONTEXT_URI);
	}

}
