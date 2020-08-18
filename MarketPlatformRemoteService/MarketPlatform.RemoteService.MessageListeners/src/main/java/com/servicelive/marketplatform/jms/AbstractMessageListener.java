package com.servicelive.marketplatform.jms;

import org.apache.log4j.Logger;
import javax.ejb.EJBException;
import javax.jms.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: yburhani
 * Date: Jul 20, 2010
 * Time: 4:41:09 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMessageListener implements MessageListener {

    private Logger logger = Logger.getLogger(getClass());

    protected Object extractObjectFromMessage(Message message) {
        Object obj = null;
        try {
            if (message instanceof ObjectMessage){
                obj = ((ObjectMessage)message).getObject();
            } else if (message instanceof BytesMessage){
                byte [] bytes = readBytesFromMessage((BytesMessage)message);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                obj = ois.readObject();
            } else {
                String errMsg = "Expected ObjectMessage instance but got - " + message.getClass().getName();
                logger.error(errMsg);
                throw new EJBException(errMsg);
            }
        } catch (JMSException e) {
            throw new EJBException(e);
        } catch (IOException e) {
        	throw new EJBException(e);
		} catch (ClassNotFoundException e) {
        	throw new EJBException(e);
		}
        return obj;
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
