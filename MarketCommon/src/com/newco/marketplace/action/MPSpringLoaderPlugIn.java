package com.newco.marketplace.action;

import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>Title: MPSpringLoaderPlugIn.java</p>
 * <p>Description: Servelet that loads the spring.xml on the start up.</p>
 * @author pchandrika1
 * @date Created on Aug 16, 2006
 */
public class MPSpringLoaderPlugIn {
	private static final String[] locations = {
		"resources/spring/mainApplicationContext.xml",
		"spring/mainApplicationContext.xml"
	};
	private static ApplicationContext ctx;
	static {
		for (String loc : locations) {
			URL res = Thread.currentThread().getContextClassLoader().getResource(loc);
			if (res != null) {
				ctx = new ClassPathXmlApplicationContext(loc);
				break;
			}
		}
	}

	/**
	 * This should only be ever called in test cases.
	 * @param pCtx
	 */
	public static void setCtx(ApplicationContext pCtx) {
		ctx = pCtx;
	}

    public static ApplicationContext getCtx() {
    	if(ctx == null) {
    		ctx = new ClassPathXmlApplicationContext(locations);
    	}
		return ctx;
	}

}
