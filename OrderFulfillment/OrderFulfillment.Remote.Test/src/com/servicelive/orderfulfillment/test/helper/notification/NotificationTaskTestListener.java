package com.servicelive.orderfulfillment.test.helper.notification;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;

import javax.ejb.EJBException;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageEOFException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Vector;

public class NotificationTaskTestListener implements MessageListener {
    public void onMessage(Message message) {
        ServiceOrderNotificationTask svcOrderNotifyTask = extractNotificationTask(message);
        NotificationTaskInMemoryStorage.saveNotificationTask(svcOrderNotifyTask);
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
