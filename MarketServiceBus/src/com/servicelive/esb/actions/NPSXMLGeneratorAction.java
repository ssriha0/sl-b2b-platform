package com.servicelive.esb.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.servicelive.esb.constant.NPSConstants;
import com.servicelive.esb.dto.NPSCallCloseServiceOrder;
import com.servicelive.esb.dto.NPSDiscounts;
import com.servicelive.esb.dto.NPSJobCode;
import com.servicelive.esb.dto.NPSJobCodes;
import com.servicelive.esb.dto.NPSMerchandise;
import com.servicelive.esb.dto.NPSMerchandiseDisposition;
import com.servicelive.esb.dto.NPSMonetary;
import com.servicelive.esb.dto.NPSPayment;
import com.servicelive.esb.dto.NPSSalesCheck;
import com.servicelive.esb.mapper.NPSCallCloseStagingMapper;
import com.servicelive.esb.service.ExceptionHandler;
import com.thoughtworks.xstream.XStream;

/**
 * ESB Action responsible for generating call close xml from the unprocessed closed orders available in ESB session
 */
public class NPSXMLGeneratorAction extends AbstractEsbSpringAction {

	private Logger logger = Logger.getLogger(NPSXMLGeneratorAction.class);
	private static ResourceBundle serviceLiveESBConfig = ResourceBundle.getBundle("servicelive_esb_"  + System.getProperty("sl_app_lifecycle"));

	/**
	 * This method retrieves the staging object from session;
	 * converts (i.e. maps) the staging ShcOrder object to call close object, 
	 * appends the closed order xmls to single call close output file.
	 *  
	 * @return Message
	 * @throws Exception
	 */
	public Message generateXML(Message message) throws IOException {

		// Retrieve closed orders from session
		Body body = message.getBody();
		StagingDetails stagingDetails = (StagingDetails) body.get(NPSConstants.CLOSED_ORDERS_MSG_KEY);
		if (null == stagingDetails || null == stagingDetails.getStageServiceOrder() || stagingDetails.getStageServiceOrder().size() == 0) {
			return message;
		}
		
		// Generate NPS Call Close XML
		Class<?>[] classes = new Class[] {NPSCallCloseServiceOrder.class, NPSSalesCheck.class, NPSMonetary.class,
											NPSDiscounts.class, NPSPayment.class, NPSMerchandise.class,
											NPSJobCodes.class, NPSJobCode.class, NPSMerchandiseDisposition.class};
		XStream xStream = new XStream();
		xStream.processAnnotations(classes);
		
		// Format filename
		String npsCallCloseXMLFilePath = serviceLiveESBConfig.getString(NPSConstants.CALL_CLOSE_FILE_PATH);
		String timestamp = new SimpleDateFormat(NPSConstants.FILE_NAME_TIMESTAMP_FORMAT).format(Calendar.getInstance().getTime());
		String npsFileName = new StringBuilder(npsCallCloseXMLFilePath)
								.append(NPSConstants.FILE_NAME_PREFIX)
								.append(NPSConstants.DOT)
								.append(timestamp)
								.append(NPSConstants.DOT)
								.append("xml").toString();
		
		// Write XML
		boolean success = false;
		File npsFile = new File(npsFileName);
		FileWriter npsFileWriter = null;
		try {
			npsFileWriter = new FileWriter(npsFile);
			npsFileWriter.write(NPSConstants.XML_HEADER);
			npsFileWriter.write(NPSConstants.START_TAG);
	
			// Map ShcOrder to NPSCallCloseServiceOrder and stream it out
			NPSCallCloseStagingMapper npsCallCloseStagingMapper = new NPSCallCloseStagingMapper();
			for (ShcOrder shcOrder : stagingDetails.getStageServiceOrder()) {
				NPSCallCloseServiceOrder npsCallCloseServiceOrder = npsCallCloseStagingMapper.convertShcOrderToNPSCallCloseServiceOrder(shcOrder);
				xStream.toXML(npsCallCloseServiceOrder, npsFileWriter);
				npsFileWriter.write(NPSConstants.CR_NL);
			}
			
			npsFileWriter.write(NPSConstants.END_TAG);
			logger.info("File {" + npsFileName + "} generated successfully.");
			success = true;
		} finally {
			if (npsFileWriter != null) {
				try {
					npsFileWriter.close();
				} catch (IOException ioEx) {
					logger.error("Unexpected exception while closing the Call Close File Stream for file: " + npsFileName, ioEx);
				}
			}
			
			if (success) {
				// Putting the file name to session for storing in NPS Process Log table
				body.add(NPSConstants.CALL_CLOSE_FILE_NAME_MSG_KEY, npsFileName);
			} else {
				if (npsFile.exists()) {
					npsFile.delete();
				}
			}
		}
		return message;
	}

	/* (non-Javadoc)
	 * @see org.jboss.soa.esb.actions.AbstractSpringAction#exceptionHandler(org.jboss.soa.esb.message.Message, java.lang.Throwable)
	 */
	@Override
	public void exceptionHandler(Message msg, Throwable th) {
		ExceptionHandler.handleNPSException(msg, th);
		super.exceptionHandler(msg, th);
	}

	/**
	 * Default Constructor for JUnit test cases
	 */
	public NPSXMLGeneratorAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
	}

	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public NPSXMLGeneratorAction(ConfigTree configTree) {
		super.configTree = configTree;
	}
}
