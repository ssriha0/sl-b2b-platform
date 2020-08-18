package com.newco.marketplace.business.businessImpl.buyerOutBoundNotification.mdb;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

public class BuyerJMSInvoker {

	private String queue_name = "queue/buyerOutBoundNotification";
	private static final Logger logger = Logger.getLogger("BuyerJMSInvoker");
	QueueConnection connection;
	QueueSession session;
	Queue queue;
	QueueReceiver recv;
	private static Properties jmsProps;

	public Properties getJmsProps() {
		return jmsProps;
	}

	public void setJmsProps(Properties jmsProps) {
		this.jmsProps = jmsProps;
	}

	public void setup() throws JMSException, NamingException {
		Context iniCtx = getInitialContext();
		Object tmp = iniCtx.lookup("ConnectionFactory");
		QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
		connection = qcf.createQueueConnection();
		queue = (Queue) iniCtx.lookup(queue_name);
		session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		// Set the async listener
		recv = session.createReceiver(queue);
		recv.setMessageListener(BuyerOutBoundNotificationMDB.getInstance());
		connection.start();
	}

	public void sendRecvAsync(String text) throws JMSException, NamingException {
		logger.info("Begin sendRecvAsync");
		try{
			if(null == connection){
				logger.info("connection is null"); 
				setup();
			}else if(null == session){
				logger.info("session is null"); 
				session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
				recv = session.createReceiver(queue);
				recv.setMessageListener(BuyerOutBoundNotificationMDB.getInstance());
			}else if(null == recv){
				logger.info("Queue reciever is null"); 
				recv = session.createReceiver(queue);
				recv.setMessageListener(BuyerOutBoundNotificationMDB.getInstance());
			}
			// Send a text msg
			QueueSender send = session.createSender(queue);
			TextMessage tm = session.createTextMessage(text);
			send.send(tm);
			
			// Wait until the acknowledgment is received from the listener
			//Thread.sleep(1000);
			logger.info("sendRecvAsync, sent text=" + tm.getText());
			
			// close the sender
			send.close();
			logger.info("End sendRecvAsync");
			
			// TODO Queue Listener will not remove the message from the queue.
			// Once the message is read by the listener, it has to be removed.
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} /*catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			stop();
		}*/
	}

	public void stop() throws JMSException {
		if(null!=connection){
			connection.stop();
			connection.close();
		}
	}

	// TODO : Check whether this is required as this can be 
	// fetched from the properties files
	public static Context getInitialContext()
			throws javax.naming.NamingException {
		logger.info("Property:"+ jmsProps.getProperty("java.naming.provider.url"));
		return new javax.naming.InitialContext(jmsProps);
	}

}
