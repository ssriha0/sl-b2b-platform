package com.servicelive.marketplatform.notification.mdb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenContext;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageEOFException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;

import org.apache.log4j.Logger;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.support.AbstractMessageDrivenBean;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;

/**
 * Consumes notification task messages to be saved to the alert_task table.
 */
public class NotificationTaskConsumerBean extends AbstractMessageDrivenBean implements MessageListener {

    private static final long serialVersionUID = 1;
    private static final String BEAN_FACTORY_NAME = "MarketPlatform.NotificationMDB";

    private static final Logger logger = Logger.getLogger(NotificationTaskConsumerBean.class);

    IMarketPlatformNotificationBO marketPlatformNotificationBO;

    @Override
    protected void onEjbCreate() {
    	logger.info("on EJB create");
        marketPlatformNotificationBO = (IMarketPlatformNotificationBO)this.getBeanFactory().getBean("notificationService");
    }

    @Override
    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) {
        super.setMessageDrivenContext(messageDrivenContext);
        this.setBeanFactoryLocator(ContextSingletonBeanFactoryLocator.getInstance("notifBeanContext.xml"));
        this.setBeanFactoryLocatorKey(BEAN_FACTORY_NAME);
    }

    public void onMessage(Message message) {
		logger.info("Notification Service MDB received a message");
        ServiceOrderNotificationTask svcOrderNotifyTask = extractNotificationTask(message);
        logger.info("extracted the message now saving it " + svcOrderNotifyTask.toString());
        marketPlatformNotificationBO.queueNotificationTask(svcOrderNotifyTask);
        logger.info("Done saving the message");
    }

    private ServiceOrderNotificationTask extractNotificationTask(Message message) throws EJBException {
    	try {
	    	Object obj = null;
	    	if (message instanceof ObjectMessage){
					obj = ((ObjectMessage)message).getObject();
	    	} else if (message instanceof BytesMessage){
	    		byte [] bytes = readBytesFromMessage((BytesMessage)message);
	    		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
	    		obj = ois.readObject();
	    	} else {
	            String errMsg = "NotificationTaskConsumerBean expects ObjectMessage or BytesMessage instance but got - " + message.getClass().getName();
	            logger.error(errMsg);
	            throw new EJBException(errMsg);
	    	}
	    	assertPayloadIsServiceOrderNotificationTask(obj);
	    	
            return (ServiceOrderNotificationTask) obj;
            
        } catch (JMSException e) {
            throw new EJBException(e);
        } catch (IOException e) {
        	throw new EJBException(e);
		} catch (ClassNotFoundException e) {
        	throw new EJBException(e);
		}
    }

    private void assertPayloadIsServiceOrderNotificationTask(Object obj) {
        String errMsg = "Unable to extract notification task from ObjectMessage.";
        if ( ! (obj instanceof ServiceOrderNotificationTask)) {
            logger.error(errMsg);
            throw new EJBException(errMsg);
        }
     }
	
	private byte [] readBytesFromMessage( BytesMessage msg ) throws JMSException {

		Vector<Byte> bytesVector = new Vector<Byte>();
		byte[] bytes;

		int i = 0;
		while(true){
			try{
				bytesVector.add(Byte.valueOf(msg.readByte()));
				i=i+1;
			}catch(MessageEOFException mee){
				break;
			}
		}
		
		bytes = new byte[i];
		int j = 0;
		Iterator<Byte> iter = bytesVector.iterator();
		while(iter.hasNext()){
			bytes[j] = iter.next().byteValue();
			j=j+1;
		}
		
		return bytes;
	}
}
