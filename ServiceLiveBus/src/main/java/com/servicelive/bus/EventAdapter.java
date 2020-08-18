package com.servicelive.bus;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.bus.event.EventHeader;
import com.servicelive.bus.event.ServiceLiveEvent;
import com.servicelive.bus.event.log.IServiceLiveBusEventLogBO;

/**
 * 
 * User: Mustafa Motiwala
 * Date: Mar 25, 2010
 * Time: 12:02:30 PM
 */
public class EventAdapter implements MessageListener {

    private static final Log log = LogFactory.getLog(EventAdapter.class);
    private String clientId;
    private JmsTemplate jmsTemplate;
    private List<ServiceLiveEventListener> eventListeners = new ArrayList<ServiceLiveEventListener>();
    private List<Class<?>> classes = new ArrayList<Class<?>>();
    private List<String> allEventHeaders = new ArrayList<String>();
    private IServiceLiveBusEventLogBO serviceLiveBusEventLogBO;

    protected EventAdapter() {
    	// default c'tor allowing sub classing by CGLIB 
    }
	public EventAdapter(String clientId, JmsTemplate jmsTemplate,
			List<ServiceLiveEventListener> eventListeners,
			List<Class<?>> classes, List<String> customEventHeaders) {

		this.clientId = clientId;
		this.jmsTemplate = jmsTemplate;
		if (eventListeners != null) this.eventListeners.addAll(eventListeners);
		if (classes != null) this.classes.addAll(classes);
		if (customEventHeaders != null) this.allEventHeaders.addAll(customEventHeaders);
		for (EventHeader eventHeader : EventHeader.values()) {
			this.allEventHeaders.add(eventHeader.name());
		}
		logPropertiesDetails();
	}
    
	@Transactional
    public void onMessage(Message message) {
		final String methodName = "onMessage";
		log.info(String.format("Entered %s", methodName));

        if (!(message instanceof TextMessage)) {
        	String errorMessage = String.format("Unable to handle JMS Message of type '%s'. I only know how to handle TextMessages.", message.getClass().getName());
        	log.error(errorMessage);
            throw new EventBusException(errorMessage);
        }

        TextMessage textMessage = (TextMessage)message;
    	String messageText;
    	try {
    		messageText = textMessage.getText();
        } catch(JMSException e){
            String errorMessage = "Exception trying to get message text from JMS:";
            log.error(errorMessage, e);
            throw new EventBusException(errorMessage, e);
        }

        if (log.isDebugEnabled()) log.debug(String.format("Received message: %s", messageText));
        ServiceLiveEvent serviceLiveEvent = unmarshalEventSource(messageText);
        extractAndSetEventHeaders(message, serviceLiveEvent);

        if (this.serviceLiveBusEventLogBO.isEventNewForClient(serviceLiveEvent, this.clientId)) {
        	log.info(String.format("Received previously unlogged event with eventId '%s' for clientId '%s'. This event will attempted to be logged.", serviceLiveEvent.getEventId(), this.clientId));
        	boolean eventLoggedSuccessfully =
        	this.serviceLiveBusEventLogBO.logEventForClient(serviceLiveEvent, this.clientId);
        	if (eventLoggedSuccessfully) {
            	log.info(String.format("Successfully logged event with eventId '%s' for clientId '%s'. This event will be forwarded to the registered event handlers.", serviceLiveEvent.getEventId(), this.clientId));
	        for(ServiceLiveEventListener listener : eventListeners){
	            listener.handleEvent(serviceLiveEvent);
	        }
        }
        else {
            	log.info(String.format("Unable to log event with eventId '%s' for clientId '%s' as it has already been logged. This event will not be forwarded to the event handlers.", serviceLiveEvent.getEventId(), this.clientId));
        	}
        }
        else {
        	log.info(String.format("Received duplicate event with eventId '%s' for clientId '%s'. This event will not be forwarded to the event handlers.", serviceLiveEvent.getEventId(), this.clientId));
        }
		log.info(String.format("Exiting %s", methodName));
    }

	@Transactional
	public void raiseEvent(final ServiceLiveEvent event){
		final String methodName = "raiseEvent";
		log.info(String.format("Entered %s", methodName));
 
        log.info(String.format("Raising event: %s", event));
        
        //Calling JMS Process - Message Creator object gets created only once.
        //jmsTemplate.send(EventAdapterMessageCreator.getEventAdapterMessageCreator(event,classes));
        
		jmsTemplate.send(new MessageCreator() {
			/**
			 * Method invoked by the Spring JMS Integration subsystem. This
			 * method serializes the event to an XML Message type and adds the
			 * Event Header as properties to the JMS message.
			 * 
			 * @param session
			 *            A JMS session (managed by spring).
			 * @return - A JMS Message (TextMessage) containing the serialized
			 *         XML message as text and event headers and JMS Message
			 *         properties.
			 * @throws JMSException
			 *             - A JMS Exception is thrown if there is an error in
			 *             creating the Message or if something goes wrong
			 *             during JAXB Serialization.
			 */
			public Message createMessage(Session session) throws JMSException {
				final String methodName = "raiseEvent";
				log.info(String.format("Entered %s", methodName));

				TextMessage message = session.createTextMessage();
				Map<String, String> headers = event.getEventHeader();
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					message.setStringProperty(entry.getKey(), entry.getValue());
				}
				try {
					JAXBContext jaxbContext = JAXBContext.newInstance(classes.toArray(new Class[classes.size()]));
					Marshaller marshaller = jaxbContext.createMarshaller();
					StringWriter xmlMessageBuffer = new StringWriter();
					marshaller.marshal(event, xmlMessageBuffer);
					message.setText(xmlMessageBuffer.toString());
				} catch (JAXBException e) {
					log.error("Exception attempting to raise event!", e);
					throw new JMSException("Exception trying to marshal event using JAXB!");
				}

				log.info(String.format("Exiting %s", methodName));
				return message;
			}
		});
        
        log.info(String.format("Exiting %s", methodName));
    }

    public void addEventListener(ServiceLiveEventListener listener){
        eventListeners.add(listener);
    }

    public void addAllListeners(List<ServiceLiveEventListener> listeners){
        eventListeners.addAll(listeners);
    }

	public void setServiceLiveBusEventLogBO(IServiceLiveBusEventLogBO serviceLiveBusEventLogBO) {
		this.serviceLiveBusEventLogBO = serviceLiveBusEventLogBO;
	}

    /**
     * @return - an immutable list of EventHandlers registered with the Adapter.
     */
    public List<ServiceLiveEventListener> getEventListeners() {
        return Collections.unmodifiableList(eventListeners);
    }

	private void logPropertiesDetails() {
    	if (!log.isDebugEnabled()) return;
    	
		if (this.eventListeners != null) {
			log.debug(String.format("found %d entries in eventListeners", this.eventListeners.size()));
			for (ServiceLiveEventListener listener : this.eventListeners) {
				log.debug(String.format("found eventListenerList with class name '%s' and hashCode %d.",
						listener.getClass().getName(), listener.hashCode()));
			}
		}
		else {
			log.debug("eventListenerList is null");
		}
		
		if (this.classes != null) {
			log.debug(String.format("found %d entries in classes", this.classes.size()));
			for (Class<?> clazz : this.classes) {
				log.debug(String.format("found classes entry '%s'.", clazz.getName()));
			}
		}
		else {
			log.debug("classes is null");
		}

		if (this.allEventHeaders != null) {
			log.debug(String.format("found %d entries in allEventHeaders", this.allEventHeaders.size()));
			for (String entry : this.allEventHeaders) {
				log.debug(String.format("found allEventHeaders entry '%s'.", entry));
			}
		}
		else {
			log.debug("allEventHeaders is null");
		}
	}

    private ServiceLiveEvent unmarshalEventSource(String message) {
       try {
            JAXBContext jaxbContext = JAXBContext.newInstance(
            		this.classes.toArray(new Class[this.classes.size()]));
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader xmlMessageBuffer = new StringReader(message);
            ServiceLiveEvent returnVal = (ServiceLiveEvent)unmarshaller.unmarshal(xmlMessageBuffer);
            return returnVal;
        } catch (JAXBException e) {
            String errorMessage = "Exception attempting to de-serialize event!";
            log.error(errorMessage,e);
            throw new EventBusException(errorMessage,e);
        }
    }

    private void extractAndSetEventHeaders(Message message, 
			ServiceLiveEvent serviceLiveEvent) {
    	
    	for (String headerName : this.allEventHeaders) {
    		try {
	    		String value = message.getStringProperty(headerName);
	    		if (null != value) {
	    			serviceLiveEvent.addHeader(headerName, value);
	    		}
    		}
    		catch (JMSException e) {
    			log.warn(String.format("Encountered error while trying to read header '%s' from message. The header is being ignored.", headerName), e);
    		}
    	}
	}
}
