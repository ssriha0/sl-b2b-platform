package com.servicelive.spn.scheduler.quartz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author svanloon
 *
 */
public class QuartzLoaderMain {

	/**
	 * This method is here to start the quartz job in a separate VM.
	 * DO NOT REMOVE THIS CLASS.
	 * @param args
	 */
	public static void main(String...args) {
		new ClassPathXmlApplicationContext("/resources/spnBackEndAppContext.xml");
	}
}
