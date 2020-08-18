package com.servicelive.wallet.batch.gl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.common.util.FileUtil;
import com.servicelive.wallet.batch.gl.vo.GLFeedVO;

// TODO: Auto-generated Javadoc
/**
 * The Class GLWriter.
 */
public class GLWriter implements IGLWriter {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(GLWriter.class.getName());

	/** The application properties. */
	private IApplicationProperties applicationProperties;

	/**
	 * Gets the application properties.
	 * 
	 * @return the application properties
	 */
	public IApplicationProperties getApplicationProperties() {

		return applicationProperties;
	}

	/**
	 * Sets the application properties.
	 * 
	 * @param applicationProperties the new application properties
	 */
	public void setApplicationProperties(IApplicationProperties applicationProperties) {

		this.applicationProperties = applicationProperties;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.GLWriter#writeGLFeed()
	 */
	public String writeGLFeed(ArrayList<GLFeedVO> glFeedItems, String fileName) throws Exception {

		String fileDir = applicationProperties.getPropertyValue("gl_feed_dir");
		logger.info("fileName should be: " + fileName);
		try {

			logger.info("Creating directory if one does not already exist. Dir will be: " + fileDir);
			boolean success = new File(fileDir).mkdir();
			if (success) {
				logger.info("Directory was created successfully");
			} else {
				logger.info("Directory already exists");
			}

			StringBuffer sb = new StringBuffer();

			Iterator<GLFeedVO> i = glFeedItems.iterator();

			while (i.hasNext()) {
				sb.append(i.next().toString());
				sb.append("\n");
			}
			File outputFile = new File(fileDir + fileName);
			FileUtil.writeStringToFile(outputFile, sb.toString());

			logger.debug("GL Feed file closed. Location of file is: " + fileName);
		} catch (IOException ioe) {
			logger.error("writeGLFeed IO Exception. Directory incorrect or could not write file", ioe);
			throw new Exception(ioe);
		}
		return fileName;
	}

}
