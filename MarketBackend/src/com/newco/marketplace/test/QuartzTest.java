package com.newco.marketplace.test;

import org.quartz.Scheduler;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;


public class QuartzTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Scheduler scheduler = (Scheduler)MPSpringLoaderPlugIn.getCtx().getBean("scheduler");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
