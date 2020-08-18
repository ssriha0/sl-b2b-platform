/**
 * 
 */
package com.servicelive.esb.actions;

import java.net.URI;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.Address;
import com.servicelive.esb.dto.JobCode;
import com.servicelive.esb.dto.JobCodes;
import com.servicelive.esb.dto.Logistics;
import com.servicelive.esb.dto.LogisticsMerchandise;
import com.servicelive.esb.dto.LogisticsOrder;
import com.servicelive.esb.dto.Merchandise;
import com.servicelive.esb.dto.Messages;
import com.servicelive.esb.dto.RepairLocation;
import com.servicelive.esb.dto.SalesCheck;
import com.servicelive.esb.dto.SalesCheckItem;
import com.servicelive.esb.dto.SalesCheckItems;
import com.servicelive.esb.dto.ServiceOrder;
import com.servicelive.esb.dto.ServiceOrderCustomer;
import com.servicelive.esb.dto.ServiceOrders;
import com.servicelive.esb.service.ExceptionHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;

/**
 * @author himanshu
 *
 */
public class ServiceOrderMessageUnMarshallerAction extends AbstractEsbSpringAction {
	private Logger logger = Logger.getLogger(ServiceOrderMessageUnMarshallerAction.class);
	public static final Class<?>[] classes = new Class[] { Address.class, JobCode.class, JobCodes.class, 
		Logistics.class, LogisticsOrder.class,
		LogisticsMerchandise.class,
		Merchandise.class, Messages.class,
		RepairLocation.class,
		ServiceOrders.class, ServiceOrder.class,
		ServiceOrderCustomer.class, SalesCheck.class,
		SalesCheckItem.class, SalesCheckItems.class };
	
	public Message unmarshalPayload(Message message) {
		logger.info("Inside ServiceOrderMessageUnMarshallerAction.unmarshalPayload() method>>>>>>>>>>>");
		Body body = message.getBody();
		Object xmlFeed = body.get();
		//TODO Add actual functionality here and add the Orderlist to MessageBUS
		logger.info("*********** Processing the file feed : \""+xmlFeed+"\"");
		
		XStream xstream = new XStream();
		
		xstream.processAnnotations(classes);
		
		logger.info("Before unmarshalling the xml payload...");
		ServiceOrders serviceOrders = null;
		
		URI inuptmessageid =  message.getHeader().getCall().getMessageID();
		if(inuptmessageid == null ) {
			//TODO  handle the Message ID not found scenario
			/*ExceptionHandler.handle((String) message.getBody().get(MarketESBConstant.CLIENT_KEY), 
					xmlFeed.toString(), 
					inputFilefeedName,
					errorMsg, 
					 xmlFeed,
					e);*/
			return null; // This will prevent message to propogate
		}
		String inputFilefeedName = getStrippedOffIDString(inuptmessageid.toString());
		logger.info("About to process Message " +inputFilefeedName );
		try {
			//Capture the input file feed name for future use (in event of an error)
			body.add(MarketESBConstant.FILE_FEED_NAME, inputFilefeedName);
			
			//Add the client key to the message to drive specific translation and mapping rules
			body.add(MarketESBConstant.CLIENT_KEY, MarketESBConstant.Client.OMS);
			//Parse XML into Object
			serviceOrders = (ServiceOrders) xstream.fromXML(xmlFeed.toString());
			logger.debug("Unmarshalled the bin payload into ServiceOrders obj="+serviceOrders);
			
			//Set the unmarshalled object onto message body with a key
			body.add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH, serviceOrders);
			logger.info("Unmarshalled the java pojo's from the xml payload and set into message body...");
		} catch(ConversionException e) {
			String errorMsg = "Exception caught UnMarshalling Message from the MQ with Message ID: " + inputFilefeedName;
			//Since we are already handling this information 
			logger.info( errorMsg );
			ExceptionHandler.handle((String) message.getBody().get(MarketESBConstant.CLIENT_KEY), 
					xmlFeed.toString(), 
					inputFilefeedName,
					errorMsg, 
					 xmlFeed,
					e,true);
		} 
		return message;
	}
	
	private String getStrippedOffIDString(String originalMessageID) {
		StringBuffer sb = new StringBuffer(originalMessageID);
		int startIndex = originalMessageID.indexOf("ID:");
		sb.replace(startIndex, startIndex+ "ID:".length(), "MQID_");
		return sb.toString();
	}
	/**    	 */
	public ServiceOrderMessageUnMarshallerAction() {
		super();
	}
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public ServiceOrderMessageUnMarshallerAction(ConfigTree config) { super.configTree = config; }
}
