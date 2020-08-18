package com.servicelive.esb.actions;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.util.buyerFileUploadTool.IBuyerFileUploadToolParser;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.IntegrationName;

public class FileUploadParseInputAction extends AbstractIntegrationSpringAction {
	
	private static final Logger logger = Logger.getLogger(FileUploadParseInputAction.class);
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * 
	 * @param config
	 */
	public FileUploadParseInputAction(ConfigTree config) {
		super.configTree = config;
	}

	/**
	 * Default Constructor for JUnit test cases
	 */
	public FileUploadParseInputAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
	}
	
	public Message parseInput(Message message) throws Exception {
		
		Object payload = message.getBody().get(Body.DEFAULT_LOCATION);
		byte[] bytes = (byte[]) payload;
		
		Object fileFeedPropertyValue = message.getProperties().getProperty(
				MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
        String inputFilefeedName = String
				.valueOf(fileFeedPropertyValue == null ? ""
						: fileFeedPropertyValue);
		
		IBuyerFileUploadToolParser parser = (IBuyerFileUploadToolParser) getBeanFactory().getBean("BFUTParserImpl");
		List<Map<String, String>> orders = parser.parseBuyerXLSFile(bytes);
		
		int splitIndex = inputFilefeedName.indexOf("_");
		int endIndex = inputFilefeedName.indexOf(".", splitIndex);
		if (splitIndex < 0 || endIndex < 0) {
			throw new  Exception("File name, " + inputFilefeedName + " , could not be parsed to find the buyer resource Id.");
		}
		
		String fileUploadId = inputFilefeedName.substring(0, splitIndex);
		String buyerResourceIdString = inputFilefeedName.substring(splitIndex + 1, endIndex);
		Long buyerResourceId = Long.parseLong(buyerResourceIdString);

		@SuppressWarnings("unused")
		Batch batch = this.getIntegrationServiceCoordinator().createBatchForFileUpload(
				inputFilefeedName, orders, buyerResourceId, fileUploadId);
		
		return message;
	}

	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.FILE_UPLOAD.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.FILE_UPLOAD.name();
	}
}
