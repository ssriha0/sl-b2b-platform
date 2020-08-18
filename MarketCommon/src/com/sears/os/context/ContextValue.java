package com.sears.os.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextValue {
	
	private  static ApplicationContext applicationContext =  null;
    private static String contextFile = "";	
	
	protected ContextValue() {}	
	
	public static ApplicationContext getApplicationContext(){
		if (applicationContext == null)
			applicationContext = new ClassPathXmlApplicationContext(contextFile);
		return applicationContext;	
	}

	/**
	 * Sets the contextFile.
	 * @param contextFile The contextFile to set
	 */
	public static void setContextFile(String contextFile) {
		ContextValue.contextFile = contextFile;
	}
}
