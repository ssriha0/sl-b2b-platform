package com.servicelive.wallet.batch.ofac;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.servicelive.common.util.FileUtil;
import com.servicelive.wallet.batch.ofac.vo.OFACProcessQueueVO;

// TODO: Auto-generated Javadoc
/**
 * The Class OFACWriter.
 */
public class OFACWriter {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(OFACWriter.class.getName());

	/**
	 * Write records to file.
	 * 
	 * @param ofacSummaryList 
	 * @param fileDir 
	 * @param fileName 
	 * 
	 * @return the string
	 * 
	 * @throws Exception 
	 */
	public String writeRecordsToFile(ArrayList<OFACProcessQueueVO> ofacSummaryList, String fileDir, String fileName) throws Exception {

		try {

			logger.info("Creating directory if one does not already exist. Dir will be: " + fileDir);
			boolean success = new File(fileDir).mkdir();
			if (success) {
				logger.info("Directory was created successfully");
			} else {
				logger.info("Directory already exists");
			}
			StringBuffer sb = new StringBuffer();

			Iterator<OFACProcessQueueVO> i = ofacSummaryList.iterator();
			sb.append("UserType");
			sb.append("|");
			sb.append("Entity ID");
			sb.append("|");
			sb.append("TaxPayerID");
			sb.append("|");
			sb.append("User Name");
			sb.append("|");
			sb.append("User ID");
			sb.append("|");
			sb.append("Business Name");
			sb.append("|");
			sb.append("Admin Contact No");
			sb.append("|");
			sb.append("Admin First Name");
			sb.append("|");
			sb.append("Admin Last Name");
			sb.append("|");
			sb.append("Email");
			sb.append("|");
			sb.append("Street 1");
			sb.append("|");
			sb.append("Street 2");
			sb.append("|");
			sb.append("City");
			sb.append("|");
			sb.append("State");
			sb.append("|");
			sb.append("Zip");
			sb.append("|");
			sb.append("Apt No");
			sb.append("|");
			sb.append("V1 Account");
			sb.append("|");
			sb.append("V2 Account");
			sb.append("|");
			sb.append("Created Date");
			sb.append("\n");

			while (i.hasNext()) {
				sb.append(i.next().toString());
				sb.append("\n");
			}
			File outputFile = new File(fileDir + fileName);
			FileUtil.writeStringToFile(outputFile, sb.toString());

		} catch (IOException ioe) {
			logger.error("OFACFileImpl IO Exception. Directory incorrect or could not write file", ioe);
			throw new Exception(ioe);
		}
		return fileName;
	}
}
