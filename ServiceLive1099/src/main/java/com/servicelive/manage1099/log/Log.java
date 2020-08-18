package com.servicelive.manage1099.log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.servicelive.manage1099.constants.FileConstants;

public class Log {

	public static Logger logger;
	private static FileHandler fh;

	/**
	 * 
	 *  TODO :  Have a unified log level, so that the log level can be maintained at the application level.
	 * Logs the string to a log file specified in FileConstants.
	 * 
	 * @param logString
	 */
	public static void writeLog(final Level level, final String logString) {
		try {
			if (FileConstants.LOGGING.equalsIgnoreCase("ON")) {
				logger = getLoggerInstance();

				logger.log(level, logString);

				System.out.println();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static FileHandler getInstanceOfFileHandler() throws SecurityException, IOException {
		if (fh == null) {
			fh = new FileHandler(FileConstants.logFile, 0, 1, true);
			
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		}
		return fh;
	}
	
	private static Logger getLoggerInstance()
			throws SecurityException, IOException {
		if (logger == null) {
			logger = Logger.getLogger("LogToFile");
			
			// This block configure the logger with handler and formatter
			fh = getInstanceOfFileHandler();
			logger.addHandler(fh);

			// the following statement is used to log any messages
			logger.removeHandler(new ConsoleHandler());
		}
		return logger;
	}
}