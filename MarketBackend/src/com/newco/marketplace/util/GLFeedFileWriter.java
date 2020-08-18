package com.newco.marketplace.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import org.apache.log4j.Logger;
import com.newco.marketplace.dto.vo.ledger.GLFeedVO;

public class GLFeedFileWriter {
	
	private static final Logger logger = Logger.getLogger(GLFeedFileWriter.class.getName());
	
	public static String writeGLFeed(ArrayList<GLFeedVO> glFeedItems) throws Exception{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_HHmmss");
		String dateStr = sdf.format(cal.getTime());
		String fileDir = PropertiesUtils.getPropertyValue("gl_feed_dir");
		String fileName =  dateStr + "_gl.dat";
		logger.info("fileName should be: " + fileName);
		try{
			
			logger.info("Creating directory if one does not already exist. Dir will be: " + fileDir);
			boolean success = new File(fileDir).mkdir();
			if(success){
				logger.info("Directory was created successfully");
			} else{
				logger.info("Directory already exists");
			}
			BufferedWriter out = new BufferedWriter(
									new FileWriter (fileDir + fileName));
			
			Iterator<GLFeedVO> i = glFeedItems.iterator();
			
			while(i.hasNext()){
				
				out.append(i.next().toString());
				out.append("\n");
				out.flush();
			}
			logger.debug("Done writing GL Feed. will close file");
			out.close();
			logger.debug("GL Feed file closed. Location of file is: " + fileName);
		} catch(IOException ioe){
			logger.error("writeGLFeed IO Exception. Directory incorrect or could not write file", ioe);
			throw new Exception(ioe);
		}
		return fileName;
	}
}
