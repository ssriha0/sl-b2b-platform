package com.newco.marketplace.test;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CreateReqMsg {
	private static QueueConnection conn;
	private static Queue que;
	public static void main(String[] args) {
		try {
			sendRecvAsync("2ISOGCR12101111111111111111102222222222100000444444444444555555666666666666777777777777888888999aaaaaaaa08bbbbbbbb0130000000002244061PC00001234AN000123456789123LA000000987654321FG0000009876543213");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendRecvAsync(String text)throws JMSException
	{
		System.out.println("Begin sendRecvAsync");
		try {
			intializeJMS();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueueSession session;
		
		session = conn.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE);
		conn.start();
		
		try{	
		QueueSender send = session.createSender(que);
		TextMessage tm = session.createTextMessage(text);
		send.send(tm);
		System.out.println("sendRecvAsync, sent text=" + tm.getText());
		send.close();
		System.out.println("End sendRecvAsync");
		}catch(JMSException jmse){
			throw jmse;
		}
	}

	private static void intializeJMS() throws NamingException, JMSException{
		
		Properties props = new Properties();

        props.setProperty( "java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory" );

        props.setProperty( "java.naming.provider.url", "localhost:1099" );

        props.setProperty( "java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces" );
        try{
	        Context iniCtx = new InitialContext( props );
			Object tmp = iniCtx.lookup("ConnectionFactory");
			QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
			conn = qcf.createQueueConnection();
			que = (Queue) iniCtx.lookup("queue/SLResponseQueue");
        }catch(NamingException ne){
        	throw ne;
        }catch(JMSException jmse){
        	throw jmse;
        }
	}
}
