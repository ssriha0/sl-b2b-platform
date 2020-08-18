/*
 * Created on Jun 27, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.newco.marketplace.util;

/**
 * @author RHarini
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.MPConstants;

public class LoggerUtils {

	/* Displays message for class with type
		* @param Class object
		* @param String type
		* @param String message
		*/
	  public static void log(Class object, String type, String message) {

		  try {
			  Logger logger = Logger.getLogger(object);
			  if (type.equals(MPConstants.LOGGER_DEBUG)) {
				  logger.debug(message);
			  }
			  if (type.equals(MPConstants.LOGGER_INFO)) {
				  logger.info(message);
			  }
			  if (type.equals(MPConstants.LOGGER_ERROR)) {
				  logger.error(message);
			  }
			  if (type.equals(MPConstants.LOGGER_FATAL)) {
				  logger.fatal(message);
			  }
		  } catch (Exception ex) {
			  ex.printStackTrace();
		  }
	  }


}
