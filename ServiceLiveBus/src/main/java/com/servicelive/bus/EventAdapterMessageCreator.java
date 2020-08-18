package com.servicelive.bus;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.MessageCreator;

import com.servicelive.bus.event.ServiceLiveEvent;

public class EventAdapterMessageCreator {
	
    	private static final Log log = LogFactory.getLog(EventAdapterMessageCreator.class);

        private static MessageCreator messageCreator;
        private static ServiceLiveEvent serviceLiveEvent;
        private static List<Class<?>> eventClasses = new ArrayList<Class<?>>();
        
        private EventAdapterMessageCreator(){
        
        }
        
        /**
         * Method used to create Message Creator object as singleton and invoke createMessage method as part of JMS process.
         * @param event
         * @param classes
         * @return
         */
        public static MessageCreator  getEventAdapterMessageCreator(final ServiceLiveEvent event,final List<Class<?>> classes){
        	
			serviceLiveEvent = event;
			eventClasses = classes;
			
			log.info("Inside the getEventAdapterMessageCreator method.Message Creator Object:"+messageCreator);
        		
				if(messageCreator == null){
					
					messageCreator =  new MessageCreator() {
						
					/**
					 * Method invoked by the Spring JMS Integration subsystem. This method
					 * serializes the event to an XML Message type and adds the Event Header as
					 * properties to the JMS message.
					 * 
					 * @param session
					 *            A JMS session (managed by spring).
					 * @return - A JMS Message (TextMessage) containing the serialized XML
					 *         message as text and event headers and JMS Message properties.
					 * @throws JMSException
					 *             - A JMS Exception is thrown if there is an error in creating
					 *             the Message or if something goes wrong during JAXB
					 *             Serialization.
					 */
					public Message createMessage(Session session) throws JMSException {
						
						log.info("Event : "+serviceLiveEvent+ " Event Classes: "+eventClasses);

					
						final String methodName = "raiseEvent";
						log.info(String.format("Entered %s", methodName));
						
						TextMessage message = session.createTextMessage();
						
						Map<String, String> headers = serviceLiveEvent.getEventHeader();
						for (Map.Entry<String, String> entry : headers.entrySet()) {
							message.setStringProperty(entry.getKey(), entry.getValue());
						}
					
						try {
						    JAXBContext jaxbContext = JAXBContext.newInstance(eventClasses.toArray(new Class[eventClasses.size()]));
						    Marshaller marshaller = jaxbContext.createMarshaller();
						    StringWriter xmlMessageBuffer = new StringWriter();
						    marshaller.marshal(event, xmlMessageBuffer);
						    message.setText(xmlMessageBuffer.toString());
							log.info(String.format("After JAXB marshal method... %s", methodName));
						
						} catch (JAXBException e) {
						    log.error("Exception attempting to raise event!",e);
						    throw new JMSException("Exception trying to marshal event using JAXB!");
						}
						
						log.info(String.format("Exiting %s", methodName));
						return message;
					}
			    };
			}
			return messageCreator;
        }
   }



