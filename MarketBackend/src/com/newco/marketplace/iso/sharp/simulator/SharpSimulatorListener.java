package com.newco.marketplace.iso.sharp.simulator;

import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.newco.marketplace.util.FullfillmentHelper;

public class SharpSimulatorListener implements MessageListener{
    static Logger log;
    private static final String sharQueueJNDI = "queue/sharpQueue";
    QueueConnection conn;
    QueueSession session;
    Queue que;
	
		public void init(){
			
		    Properties props = new Properties();
		    props.setProperty( "java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory" );
		    props.setProperty( "java.naming.provider.url", "localhost:1099" );
		    props.setProperty( "java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces" );
		    try {
			    Context iniCtx = new InitialContext( props );
			    Object tmp = iniCtx.lookup("ConnectionFactory");
			    QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
			    conn = qcf.createQueueConnection();
			    que = (Queue) iniCtx.lookup(sharQueueJNDI);
			    session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);            	
			    QueueReceiver recv = session.createReceiver(que);
			    recv.setMessageListener(this);
			    conn.start(); 
		    } catch(Exception e){e.printStackTrace();}
			
		}

		public void onMessage(Message responseMessage){
			System.out.println("Sharp Simulator receives the message....");
			TextMessage msg = null;
			String responseStr = null;
					
			
			try{
				if(responseMessage instanceof TextMessage) {
					msg = (TextMessage)responseMessage;
					responseStr = msg.getText();
					System.out.println("-------> "+responseStr+ " picked up");
					
				} else if(responseMessage instanceof BytesMessage){
					System.out.println("Sharp Simulator identifies this as byte message....");
					BytesMessage bytesMsg = (BytesMessage) responseMessage;
					SharpSimulator sharpSimulator = new SharpSimulator();
					FullfillmentHelper ff = new FullfillmentHelper();
					byte b[]=sharpSimulator.getResponse(ff.getBytesFromBytesMessage(bytesMsg));
					System.out.println("Sharp Simlulator Response: "+new String(b));
				}
			}catch(JMSException jmse){
				jmse.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}

		}

	
		 public void stops()
	        throws JMSException
	    {
	        conn.stop();
	        session.close();
	        conn.close();
	    }
		
	 public static void main(String args[]) 
	    throws Exception
	{
		 SharpSimulatorListener sharpSimluatorListener = new SharpSimulatorListener();
		 sharpSimluatorListener.init();
		 System.out.println("Sharp Simulator Listener Waiting for messages....");
	}

	}

	

