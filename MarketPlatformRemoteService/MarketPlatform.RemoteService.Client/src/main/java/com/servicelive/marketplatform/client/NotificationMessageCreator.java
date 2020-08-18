package com.servicelive.marketplatform.client;


import org.apache.log4j.Logger;
import org.springframework.jms.core.MessageCreator;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;


    /**
     * Singleton class to create Message Creator object in the Notification process flow
     *
     */
    public class NotificationMessageCreator{
   
        private static Logger logger = Logger.getLogger(NotificationMessageCreator.class);

    
        private static MessageCreator messageCreator;
        private static Serializable serializableObj;
        private static String payLoad;
        private static ObjectMessage objectMessage;
        
        /**
         * 
         */
        private NotificationMessageCreator(){
        
        }
        
        /**
         * Method used to create Message Creator object during the Notification flow.
         */
        public static synchronized MessageCreator getMessageCreator(final Serializable obj, final String payloadName){
        	
        	logger.info("Inside the getMessage Creator method during Notification or adding the service order document flow. Message Creator Object:"+messageCreator);
        	logger.info("Serializable Object:"+obj+" Pay load name:"+payloadName);
        	
        	serializableObj = obj;
        	payLoad = payloadName;
        	
        	try{
				if(messageCreator == null){
					messageCreator =  new MessageCreator() {
				        public Message createMessage(Session session) throws JMSException {
				        	objectMessage = session.createObjectMessage();
				            objectMessage.setStringProperty("Payload", payLoad);
				            objectMessage.setObject(serializableObj);
				            return objectMessage;
				        }
				    };
				   
				}else{
				    objectMessage.setStringProperty("Payload", payLoad);
				    objectMessage.setObject(serializableObj);
				}
        	}catch(JMSException jmsException){
			    logger.error("Exception while creating message creator as part of Notification/ServiceOrderBuyerDocument process flow...",jmsException);
        	}
			return messageCreator;
        }
    }
