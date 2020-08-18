/* This class is used as a listener for the notification queue
 * 
 */

package com.newco.marketplace.business.businessImpl.buyerOutBoundNotification.mdb;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundAPIService;

public class BuyerOutBoundNotificationMDB implements MessageListener {
	private static final long serialVersionUID = 267666215497057405L;
	public static final String SEPERATOR = "SOID:";
	private static BuyerOutBoundNotificationMDB instance;
	
	private IBuyerOutBoundAPIService buyerOutBoundAPIService;

	private BuyerOutBoundNotificationMDB() {
	}

	public static synchronized BuyerOutBoundNotificationMDB getInstance() {
		if (instance == null) {
			instance = new BuyerOutBoundNotificationMDB();
		}
		return instance;
	}

	public void onMessage(Message msg) {
		TextMessage tm = (TextMessage) msg;
		try {
			String recivedMessage = tm.getText();
			
			// Ack the sender
			tm.acknowledge();
			// Message is an XML with sequence number
			// separated using ||
			String split ="SOID:";
			String message[] = recivedMessage.split(split);
			String messageXML = message[0];
			String soId = message[1];
			System.out.println("JMS onMessage, recv text=" + tm.getText());
			System.out.println("JMS onMessage, messageXML=" + messageXML);
			System.out.println("JMS onMessage, seqNo=" + soId);
			buyerOutBoundAPIService.callAPIService(messageXML, soId);
			
			// TODO Call the API with the XML as the input

			// TODO Update the database based on the
			// response

		} catch (Throwable t) {

			t.printStackTrace();

		}

	}

	
	public IBuyerOutBoundAPIService getBuyerOutBoundAPIService() {
		return buyerOutBoundAPIService;
	}

	public void setBuyerOutBoundAPIService(IBuyerOutBoundAPIService buyerOutBoundAPIService) {
		this.buyerOutBoundAPIService = buyerOutBoundAPIService;

	}

}
