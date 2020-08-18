package com.newco.spring.jms;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate102;
import org.springframework.jms.core.MessageCreator;

import com.newco.marketplace.util.constants.SpringJMSConstants;

public class SpringJMSFacility implements ISpringJMSFacility, SpringJMSConstants{
	
	private JmsTemplate102 jmsTemplateSender;
	private JmsTemplate102 jmsTemplateNMMSender;
	private JmsTemplate102 jmsTemplateBalanceInquirySender;
	private JmsTemplate102 jmsTemplateReceiver;
	private JmsTemplate102 jmsTemplateLocalSender;
	private JmsTemplate102 jmsTemplateExceptionSender;
	private JmsTemplate102 jmsTemplateWorkerSender1;
	private JmsTemplate102 jmsTemplateWorkerSender2;
	private JmsTemplate102 jmsTemplateWorkerSender3;
	private Queue resDestination;
	private Queue resDestinationNMM;
	private Queue resDestinationBalance;
	private JmsTemplate102 jmsTemplateReceiverNMM;
	private JmsTemplate102 jmsTemplateReceiverBalance;

	
	public Queue getResDestinationBalance() {
		return resDestinationBalance;
	}

	public void setResDestinationBalance(Queue resDestinationBalance) {
		this.resDestinationBalance = resDestinationBalance;
	}

	
	public JmsTemplate102 getJmsTemplateReceiverBalance() {
		return jmsTemplateReceiverBalance;
	}

	public void setJmsTemplateReceiverBalance(
			JmsTemplate102 jmsTemplateReceiverBalance) {
		this.jmsTemplateReceiverBalance = jmsTemplateReceiverBalance;
	}

	private static final Logger logger = Logger.getLogger(SpringJMSFacility.class.getName());
	
	
	public void sendMesage(final String txtMessage){
		jmsTemplateSender.send(new MessageCreator() {
	      public Message createMessage(Session session) throws JMSException {
		        return session.createTextMessage(txtMessage);
		      }
		    });
	}
	
	public void sendMesage(final byte[] bytes){
		jmsTemplateSender.send(new MessageCreator() {
		 public Message createMessage(Session session) throws JMSException {
				
			 BytesMessage tm = session.createBytesMessage();
			 	tm.setJMSCorrelationID(JMS_CORRELATION_ID);
				tm.writeBytes(bytes);
				tm.setJMSReplyTo(resDestination);
				return (Message)tm;
		      }
		    });
	}
	
	public void sendNMMMesage(final byte[] bytes){
		jmsTemplateNMMSender.send(new MessageCreator() {
		 public Message createMessage(Session session) throws JMSException {
				
			 BytesMessage tm = session.createBytesMessage();
			 	tm.setJMSCorrelationID(JMS_CORRELATION_ID);
				tm.writeBytes(bytes);
				tm.setJMSReplyTo(resDestinationNMM);
				return (Message)tm;
		      }
		    });
	}	

	public void sendBalanceMesage(final byte[] bytes){
		jmsTemplateBalanceInquirySender.send(new MessageCreator() {
		 public Message createMessage(Session session) throws JMSException {
				
			 BytesMessage tm = session.createBytesMessage();
			 	tm.setJMSCorrelationID(JMS_CORRELATION_ID);
				tm.writeBytes(bytes);
				tm.setJMSReplyTo(resDestinationBalance);
				return (Message)tm;
		      }
		    });
	}
	
	public void sendBalanceMesageSync(final byte[] bytes,final Long pan){
		jmsTemplateBalanceInquirySender.send(new MessageCreator() {
		 public Message createMessage(Session session) throws JMSException {
				
			 BytesMessage tm = session.createBytesMessage();
			 	tm.setJMSCorrelationID(pan.toString());
				tm.writeBytes(bytes);
				tm.setJMSReplyTo(resDestinationBalance);
				tm.setStringProperty("service", pan.toString());
				return (Message)tm;
		      }
		    });
	}	
	
	public void sendLocalMesage(final byte[] bytes,final Long pan){
		jmsTemplateLocalSender.send(new MessageCreator() {
			 public Message createMessage(Session session) throws JMSException {
				 BytesMessage tm = session.createBytesMessage();
					if(pan!=null)
				 	{
				 		logger.info("In local message with pan");
				 		byte[] bytesInd = new String("ACCTNO").getBytes();
					 	byte[] bytesPan = pan.toString().getBytes();
					 	byte[] bytesMessage = new byte[bytes.length + bytesInd.length + bytesPan.length];
					 	//System.arraycopy(src, srcPos, dest, destPos, length);
					 	System.arraycopy(bytes, 0, bytesMessage, 0, bytes.length);
					 	System.arraycopy(bytesInd, 0, bytesMessage, bytes.length, bytesInd.length);
					 	System.arraycopy(bytesPan, 0, bytesMessage, bytes.length + bytesInd.length, bytesPan.length);
					 	if (logger.isDebugEnabled()) {
					 		logger.debug("bytes" + new String(bytes));
					 		logger.debug("bytesPan" + new String(bytesPan));
					 		logger.debug("bytesMessage" + new String(bytesMessage));
					 	}
						tm.writeBytes(bytesMessage);
				 	}
				 	else
				 	{
				 		logger.info("In local message with NO pan");
				 		tm.writeBytes(bytes);
				 	}
					return (Message)tm;
			      }
			    });
	}
	
	public void sendWorkerMesage(final byte[] bytesInput,final String queueName){
		byte[] bytesQueueName = new String(queueName).getBytes();
	 	byte[] bytesMessage = new byte[bytesInput.length + bytesQueueName.length];
	 	System.arraycopy(bytesInput, 0, bytesMessage, 0, bytesInput.length);
	 	System.arraycopy(bytesQueueName, 0, bytesMessage, bytesInput.length, bytesQueueName.length);
		final byte[] bytesOutput = bytesMessage;
		if(queueName.equals(QUEUE_1))
		{
			jmsTemplateWorkerSender1.send(new MessageCreator() {
				 public Message createMessage(Session session) throws JMSException {
					 	
					 BytesMessage tm = session.createBytesMessage();
					 	tm.setJMSCorrelationID(JMS_CORRELATION_ID);
						tm.writeBytes(bytesOutput);
						return (Message)tm;
				      }
				    });
		}
		else if (queueName.equals(QUEUE_2))
		{
			jmsTemplateWorkerSender2.send(new MessageCreator() {
				 public Message createMessage(Session session) throws JMSException {
					 	
					 BytesMessage tm = session.createBytesMessage();
					 	tm.setJMSCorrelationID(JMS_CORRELATION_ID);
						tm.writeBytes(bytesOutput);
						return (Message)tm;
				      }
				    });
		}
		else if (queueName.equals(QUEUE_3))
		{

			jmsTemplateWorkerSender3.send(new MessageCreator() {
				 public Message createMessage(Session session) throws JMSException {
					 	
					 BytesMessage tm = session.createBytesMessage();
					 	tm.setJMSCorrelationID(JMS_CORRELATION_ID);
						tm.writeBytes(bytesOutput);
						
						return (Message)tm;
				      }
				    });
		}
		else
		{
			System.out.println("Invalid queue name. Please recheck the configuration and db entries");
		}
		
		
	}
	
	public void sendExceptionMesage(final byte[] bytes){
		jmsTemplateExceptionSender.send(new MessageCreator() {
		 public Message createMessage(Session session) throws JMSException {
			 	
			 BytesMessage tm = session.createBytesMessage();
			 	tm.setJMSCorrelationID(JMS_CORRELATION_ID);
				tm.writeBytes(bytes);
				return (Message)tm;
		      }
		    });
	}
	
	public Message Receive(){
		Message msg = null;
		try{
			msg = jmsTemplateReceiver.receive();
			
		}catch (JmsException e) {
			
		}
		return msg;

	}
	
	public Message ReceiveSync(final String pan){
		Message msg = null;
		try{
			System.out.println("IN RECIEVE SYNC METHOD");
			jmsTemplateReceiverBalance.setReceiveTimeout(10000);
			msg = jmsTemplateReceiverBalance.receiveSelected(pan);
			System.out.println("msg" + msg);
		}catch (JmsException e) {
			
		}
		return msg;

	}
	
	public Message ReceiveNMM(){
		Message msg = null;
		try{
			msg = jmsTemplateReceiverNMM.receive();
			
		}catch (JmsException e) {
			
		}
		return msg;

	}	

	public Message ReceiveBalance(){
		Message msg = null;
		try{
			msg = jmsTemplateReceiverBalance.receive();
			
		}catch (JmsException e) {
			
		}
		return msg;

	}	
	

	public JmsTemplate102 getJmsTemplateSender() {
		return jmsTemplateSender;
	}

	public void setJmsTemplateSender(JmsTemplate102 jmsTemplateSender) {
		this.jmsTemplateSender = jmsTemplateSender;
	}

	public JmsTemplate102 getJmsTemplateReceiver() {
		return jmsTemplateReceiver;
	}

	public void setJmsTemplateReceiver(JmsTemplate102 jmsTemplateReceiver) {
		this.jmsTemplateReceiver = jmsTemplateReceiver;
	}

	public JmsTemplate102 getJmsTemplateLocalSender() {
		return jmsTemplateLocalSender;
	}

	public void setJmsTemplateLocalSender(JmsTemplate102 jmsTemplateLocalSender) {
		this.jmsTemplateLocalSender = jmsTemplateLocalSender;
	}

	public JmsTemplate102 getJmsTemplateExceptionSender() {
		return jmsTemplateExceptionSender;
	}

	public void setJmsTemplateExceptionSender(
			JmsTemplate102 jmsTemplateExceptionSender) {
		this.jmsTemplateExceptionSender = jmsTemplateExceptionSender;
	}

	public Queue getResDestinationNMM() {
		return resDestinationNMM;
	}

	public void setResDestinationNMM(Queue resDestinationNMM) {
		this.resDestinationNMM = resDestinationNMM;
	}

	public JmsTemplate102 getJmsTemplateReceiverNMM() {
		return jmsTemplateReceiverNMM;
	}

	public void setJmsTemplateReceiverNMM(JmsTemplate102 jmsTemplateReceiverNMM) {
		this.jmsTemplateReceiverNMM = jmsTemplateReceiverNMM;
	}

	public Queue getResDestination() {
		return resDestination;
	}

	public void setResDestination(Queue resDestination) {
		this.resDestination = resDestination;
	}

	public JmsTemplate102 getJmsTemplateWorkerSender1() {
		return jmsTemplateWorkerSender1;
	}

	public void setJmsTemplateWorkerSender1(JmsTemplate102 jmsTemplateWorkerSender1) {
		this.jmsTemplateWorkerSender1 = jmsTemplateWorkerSender1;
	}

	public JmsTemplate102 getJmsTemplateWorkerSender2() {
		return jmsTemplateWorkerSender2;
	}

	public void setJmsTemplateWorkerSender2(JmsTemplate102 jmsTemplateWorkerSender2) {
		this.jmsTemplateWorkerSender2 = jmsTemplateWorkerSender2;
	}

	public JmsTemplate102 getJmsTemplateWorkerSender3() {
		return jmsTemplateWorkerSender3;
	}

	public void setJmsTemplateWorkerSender3(JmsTemplate102 jmsTemplateWorkerSender3) {
		this.jmsTemplateWorkerSender3 = jmsTemplateWorkerSender3;
	}

	public JmsTemplate102 getJmsTemplateNMMSender() {
		return jmsTemplateNMMSender;
	}

	public void setJmsTemplateNMMSender(JmsTemplate102 jmsTemplateNMMSender) {
		this.jmsTemplateNMMSender = jmsTemplateNMMSender;
	}

	public JmsTemplate102 getJmsTemplateBalanceInquirySender() {
		return jmsTemplateBalanceInquirySender;
	}

	public void setJmsTemplateBalanceInquirySender(
			JmsTemplate102 jmsTemplateBalanceInquirySender) {
		this.jmsTemplateBalanceInquirySender = jmsTemplateBalanceInquirySender;
	}
	
	
}

