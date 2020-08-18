package com.newco.marketplace.iso.processors;

import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.iso.IMessageProducerHelper;
import com.newco.marketplace.iso.sharp.simulator.SharpSimulator;
import com.newco.spring.jms.ISpringJMSFacility;

public class MessageProducerHelper implements IMessageProducerHelper  {

	//static QueueConnectionFactory  reqqcf, resqcf;
	//static Queue reqDestination, resDestination, sharpSimulatorQueue, exceptionDestination;
	//private static final int SHARP_REQUEST_MODE = Integer.parseInt(PropertiesUtils.getPropertyValue("sharp_request_mode"));
    
    
    private static final int SHARP_REQUEST_MODE = 0;
	private static final Logger logger = Logger.getLogger(MessageProducerHelper.class.getName());
	private QueueConnection sharpQueueConnection;
	private static Queue sharpSimulatorQueue;
	private QueueSession sharpQueueSession;
	
	private ISpringJMSFacility jmsFacility;

	public void sendMessageToSharp(byte[] bytesMessage) throws Exception{
		if (SHARP_REQUEST_MODE==1) {
			sendMsgToSharpSimulator(bytesMessage);
		}
		else{
			sendMessageToSharpReal(bytesMessage);
		}
	}
			
	public void sendMessageToExceptionQueue(byte[] bytesMessage)throws JMSException, NamingException{
		logger.info("sendMessageToExceptionQueue()-->START");
		/*
		initExceptionQueue();
		QueueConnection requestConnection = reqqcf.createQueueConnection();
		QueueSession requestSession = requestConnection.createQueueSession(false,
				QueueSession.AUTO_ACKNOWLEDGE);
		QueueSender requestSender = requestSession.createSender(exceptionDestination);
		BytesMessage bm = requestSession.createBytesMessage();
		bm.writeBytes(message);
        requestSender.send (bm, DeliveryMode.NON_PERSISTENT, 4, 0);
        requestSender.close();
        requestConnection.close();
        logger.info ("\n========== Receive Message from responseDestination");		
		*/
		jmsFacility.sendExceptionMesage(bytesMessage);	
	}
	
	private void sendMessageToSharpReal(byte[] bytesMessage) throws JMSException, NamingException {
		logger.info("sendMessageToSharpReal()-->START");
		/*
		init();
		QueueConnection requestConnection = reqqcf.createQueueConnection();
		QueueSession requestSession = requestConnection.createQueueSession(false,
				QueueSession.AUTO_ACKNOWLEDGE);
		QueueSender requestSender = requestSession.createSender(reqDestination);
		BytesMessage bm = requestSession.createBytesMessage();
		bm.writeBytes(bytesMessage);
		bm.setJMSReplyTo(resDestination);

		//QueueConnection responseConnection = resqcf.createQueueConnection();
		//responseConnection.start();
        requestSender.send (bm, DeliveryMode.NON_PERSISTENT, 4, 0);
        requestSender.close();
        requestConnection.close();
        logger.info ("\n========== Receive Message from responseDestination");
        */
		jmsFacility.sendMesage(bytesMessage);
	}
	
    private void sendMsgToSharpSimulator(byte[] bytesMessage) throws JMSException, NamingException, Exception {
		SharpSimulator sharpSimulator = new SharpSimulator();
		byte responseBytes[]=sharpSimulator.getResponse(bytesMessage);
		System.out.println("Sharp Simlulator Response: "+new String(responseBytes));
		initSharpSimulator();
		QueueSender send = sharpQueueSession.createSender(sharpSimulatorQueue);
		BytesMessage bm = sharpQueueSession.createBytesMessage();
		bm.writeBytes(responseBytes);
		send.send(bm);
		send.close();

}
	private void initSharpSimulator() throws Exception{
        Properties props = new Properties();
        props.setProperty( "java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory" );
        props.setProperty( "java.naming.provider.url", "localhost:1099" );
        props.setProperty( "java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces" );
        Context iniCtx = new InitialContext( props );
        Object tmp = iniCtx.lookup("ConnectionFactory");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        sharpQueueConnection = qcf.createQueueConnection();
        sharpSimulatorQueue = (Queue) iniCtx.lookup("queue/SLResponseQueue");
        sharpQueueSession = sharpQueueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        sharpQueueConnection.start();
		
	}
	/*
	private static void init(){
		InitialContext iniCtx;
		try {
		Properties props = new Properties();
		props.setProperty( "java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory" );
        props.setProperty( "java.naming.provider.url", "localhost:1099" );
        props.setProperty( "java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces" );			
		iniCtx = new InitialContext(props);
		
		logger.info("just entered2");
		logger.info("looking up=" + iniCtx.lookup("MQQueueConnectionFactory"));
		reqqcf = (MQQueueConnectionFactory) iniCtx.lookup("MQQueueConnectionFactory");
		resqcf = (QueueConnectionFactory) iniCtx.lookup("MQQueueConnectionFactory");
		logger.info("just entered3");
		reqDestination = (Queue) iniCtx.lookup("wsmq/RequestQueue");	
		resDestination = (Queue) iniCtx.lookup("wsmq/ResponseQueue");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static void initExceptionQueue(){
		InitialContext iniCtx;
		try {
			Properties props = new Properties();
			props.setProperty( "java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory" );
	        props.setProperty( "java.naming.provider.url", "localhost:1099" );
	        props.setProperty( "java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces" );			
			iniCtx = new InitialContext(props);
			
			logger.info("just entered2");
			logger.info("looking up=" + iniCtx.lookup("ConnectionFactory"));
			reqqcf = (QueueConnectionFactory) iniCtx.lookup("ConnectionFactory");
			resqcf = (QueueConnectionFactory) iniCtx.lookup("ConnectionFactory");
			logger.info("just entered3");
			reqDestination = (Queue) iniCtx.lookup("queue/exceptionQueue");	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	*/
	
	public void sendMessageToQueue(byte[] bytesMessage,String queueName) throws Exception{
		if (SHARP_REQUEST_MODE==1) {
			sendMsgToSharpSimulator(bytesMessage);
		}
		else{
			sendMessageToSharpReal(bytesMessage);
		}
	}
	
	public ISpringJMSFacility getJmsFacility() {
		return jmsFacility;
	}

	public void setJmsFacility(ISpringJMSFacility jmsFacility) {
		this.jmsFacility = jmsFacility;
	}	
}
