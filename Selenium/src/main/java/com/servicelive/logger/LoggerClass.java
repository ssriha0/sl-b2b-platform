package com.servicelive.logger;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;


public class LoggerClass {
	private Logger logger;
	private SimpleLayout layout;
	private FileAppender appender;
	public LoggerClass(){
		logger=Logger.getLogger("log");
		layout=new SimpleLayout(); 
		try{
			appender=new FileAppender(layout, "logs/LogFile.log", true);
			logger.addAppender(appender);
		}
		catch(IOException exception){
			exception.printStackTrace();
		}
	}
	public void debugTracker(String message){
		logger.info(message);
	}
}
