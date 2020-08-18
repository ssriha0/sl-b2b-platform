/**
 * 
 */
package com.newco.marketplace.admintools.ach;

import java.util.Date;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.ledger.IGLProcessor;

/**
 * @author schavda
 *
 */
public class GenerateGLFile {
	private static final Logger logger = Logger.getLogger(GenerateGLFile.class.getName());
	/**
	 * @param args
	 */
	public static void writeGLFeed(Date date1, Date date2) throws Exception{
		boolean success = false;
		try{
			IGLProcessor glProcessor = (IGLProcessor)MPSpringLoaderPlugIn.getCtx().getBean("glProcessor");
			
			success = glProcessor.writeGLFeed(date1,date2);
        	System.out.println("GenerateGLFile-->SUCCESS = "+ success);
		}
		catch(Exception e){
			System.out.println("GenerateGLFile-->EXCEPTION = "+ success);
			logger.error(e.getMessage(), e);
			throw e;
		}

	}
}
