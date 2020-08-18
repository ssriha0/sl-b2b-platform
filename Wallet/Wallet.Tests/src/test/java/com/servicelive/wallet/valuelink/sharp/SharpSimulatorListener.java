package com.servicelive.wallet.valuelink.sharp;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving sharpSimulator events.
 * The class that is interested in processing a sharpSimulator
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSharpSimulatorListener<code> method. When
 * the sharpSimulator event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see SharpSimulatorEvent
 */
public class SharpSimulatorListener { // implements MessageListener

	/** logger. */
 private static final Logger logger = Logger.getLogger(SharpSimulatorListener.class);

	/** sharQueueJNDI. */
	private static final String sharQueueJNDI = "queue/SLRequestQueue";

	/** conn. */
	QueueConnection conn;

	/** que. */
	Queue que;

	/** session. */
	QueueSession session;

	// /**
	// * This method sends a hardcoded NMR response
	// *
	// * @return byte[]
	// */
	//
	// public byte[] getResponse(byte[] requestByteMessage) {
	// String myString = new String(requestByteMessage);
	// String messageIdentifier = getResponeMessageIdentifier(myString);
	// if (messageIdentifier
	// .equals(FullfillmentConstants.SHARP_HEARTBEAT_REQUEST)) {
	// return respondNMR(requestByteMessage);
	// } else if (messageIdentifier
	// .equals(FullfillmentConstants.ACTIVATION_RELOAD_REQUEST)) {
	// return respondActivationRequest(requestByteMessage);
	// } else if (messageIdentifier
	// .equals(FullfillmentConstants.REDEMPTION_REQUEST)) {
	// return respondRedemptionRequest(requestByteMessage);
	// }
	// return null;
	//
	// }
	//
	// public byte[] respondNMR() {
	// ApplicationContext applicationContext = getAppContext();
	// IsoRequestProcessor isoRequestProcessor = (IsoRequestProcessor) applicationContext
	// .getBean("isoRequestProcessor");
	// byte[] responseBytes = null;
	// try {
	// IsoMessageVO message = new IsoMessageVO();
	// //message.setMessageIdentifier(FullfillmentConstants.SHARP_HEARTBEAT_RESPONSE);
	// message.setStanId("1234");
	// message.setActionCode("009");
	// message.setTimeStamp(getTimeStamp());
	// responseBytes = isoRequestProcessor.processRequest(message);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return responseBytes;
	// }
	//
	// /**
	// * This method sends a NMR response for the request
	// *
	// * @param responseByteMessage
	// * @return
	// */
	// public byte[] respondNMR(byte[] requestByteMessage) {
	// ApplicationContext applicationContext = getAppContext();
	// // 1. Call the IsoResponseProcessor ( just to reuse existing component )
	// // passing the bytes.
	// // This will construct a FulfillmentEntryVO (request) object
	// IsoResponseProcessor isoResponseProcessor = (IsoResponseProcessor) applicationContext
	// .getBean("isoResponseProcessor");
	// IsoMessageVO message = null;
	// try {
	// message = isoResponseProcessor
	// .processResponse(requestByteMessage);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// // 2. Extract needed information from FulfillmentEntryVO object
	//
	// // 3. Construct another FullfillmentEntryVO (response) object
	// // 4. Call the IsoRequestProcessor passing the FullfillmentEntryVO
	// // (response) object. This returns bytes[]
	// // 5. Post this message into the queue.
	//
	// // 2.
	// IsoRequestProcessor isoRequestProcessor = (IsoRequestProcessor) applicationContext
	// .getBean("isoRequestProcessor");
	// byte[] responseBytes = null;
	// try {
	// IsoMessageVO msg = new IsoMessageVO();
	// //msg.setMessageIdentifier(FullfillmentConstants.SHARP_HEARTBEAT_RESPONSE);
	// msg.setStanId(message
	// .getStanId());
	// msg.setActionCode("009");
	// msg.setTimeStamp(getTimeStamp());
	// responseBytes = isoRequestProcessor.processRequest(msg);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return responseBytes;
	// }
	//
	// public byte[] respondActivationRequest(byte[] requestByteMessage) {
	// ApplicationContext applicationContext = getAppContext();
	// // 1. Call the IsoResponseProcessor ( just to reuse existing component )
	// // passing the bytes.
	// // This will construct a FulfillmentEntryVO (request) object
	// IsoResponseProcessor isoResponseProcessor = (IsoResponseProcessor) applicationContext
	// .getBean("isoResponseProcessor");
	// IsoMessageVO message = null;
	// try {
	// message = isoResponseProcessor
	// .processResponse(requestByteMessage);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// IsoRequestProcessor isoRequestProcessor = (IsoRequestProcessor) applicationContext
	// .getBean("isoRequestProcessor");
	// byte[] responseBytes = null;
	// try {
	// IsoMessageVO response = new IsoMessageVO();
	// // Hard coded
	//
	// if (message.getPrimaryAccountNumber().toString()
	// .equals(FullfillmentConstants.SHARP_DUMMY_PAN_NUMBER)) {
	// response.setPrimaryAccountNumber(new Long(
	// "123" + System.currentTimeMillis()));
	// } else {
	// response.setPrimaryAccountNumber(message
	// .getPrimaryAccountNumber());
	//
	// }
	//
	// //response.setMessageIdentifier(FullfillmentConstants.ACTIVATION_RELOAD_RESPONSE);
	// response.setStanId(message.getStanId());
	// response.setTransAmount(new Double(00.00));
	// response.setTimeStamp(getTimeStamp());
	// response.setRetrievalRefId(message.getStanId());
	// response.setAuthorizationId("028888");
	// response.setFullfillmentEntryId(message
	// .getFullfillmentEntryId());
	// response.setFullfillmentGroupId(message
	// .getFullfillmentGroupId());
	// response.setPromoCode(message
	// .getPromoCode());
	// response.setLedgerEntityId(message
	// .getLedgerEntityId());
	// response.setAdditionalAmount("0001C000000000000");
	// response.setAdditionalResponse("APPROVED ACTIVATED      ");
	//
	// responseBytes = isoRequestProcessor.processRequest(response);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return responseBytes;
	// }
	//
	// public byte[] respondRedemptionRequest(byte[] requestByteMessage) {
	// ApplicationContext applicationContext = getAppContext();
	// IsoResponseProcessor isoResponseProcessor = (IsoResponseProcessor) applicationContext
	// .getBean("isoResponseProcessor");
	// IsoMessageVO message = null;
	// try {
	// message = isoResponseProcessor
	// .processResponse(requestByteMessage);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// IsoRequestProcessor isoRequestProcessor = (IsoRequestProcessor) applicationContext
	// .getBean("isoRequestProcessor");
	// byte[] responseBytes = null;
	// try {
	// IsoMessageVO response = new IsoMessageVO();
	// // Hard coded
	// if (message.getPrimaryAccountNumber().equals(
	// FullfillmentConstants.SHARP_DUMMY_PAN_NUMBER)) {
	// response.setPrimaryAccountNumber(new Long(
	// "123" + System.currentTimeMillis()));
	// } else {
	// response.setPrimaryAccountNumber(message
	// .getPrimaryAccountNumber());
	//
	// }
	// //response.setMessageIdentifier(FullfillmentConstants.REDEMPTION_RESPONSE);
	// response.setStanId(message
	// .getStanId());
	// response.setTransAmount(new Double(00.00));
	// response.setTimeStamp(getTimeStamp());
	// response.setRetrievalRefId(message.getStanId());
	// response.setAuthorizationId("028888");
	// response.setFullfillmentEntryId(message
	// .getFullfillmentEntryId());
	// response.setFullfillmentGroupId(message
	// .getFullfillmentGroupId());
	// response.setPromoCode(message
	// .getPromoCode());
	// response.setLedgerEntityId(message
	// .getLedgerEntityId());
	// response.setStoreNo("000");
	// responseBytes = isoRequestProcessor.processRequest(response);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return responseBytes;
	// }
	//	
	// private static ApplicationContext context;
	//	
	// public ApplicationContext getAppContext()
	// {
	// if (context == null) context = new ClassPathXmlApplicationContext(
	// "com/servicelive/wallet/valuelink/testApplicationContext.xml");
	// return context;
	// }
	//
	// public void init() {
	//
	// Properties props = new Properties();
	// props.setProperty("java.naming.factory.initial",
	// "org.jnp.interfaces.NamingContextFactory");
	// props.setProperty("java.naming.provider.url", "localhost:1099");
	// props.setProperty("java.naming.factory.url.pkgs",
	// "org.jboss.naming:org.jnp.interfaces");
	// try {
	// Context iniCtx = new InitialContext(props);
	// Object tmp = iniCtx.lookup("ConnectionFactory");
	// QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
	// conn = qcf.createQueueConnection();
	// que = (Queue) iniCtx.lookup(sharQueueJNDI);
	// session = conn.createQueueSession(false,
	// QueueSession.AUTO_ACKNOWLEDGE);
	// QueueReceiver recv = session.createReceiver(que);
	// recv.setMessageListener(this);
	// conn.start();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// public void onMessage(Message responseMessage) {
	// System.out.println("Sharp Simulator receives the message....");
	// TextMessage msg = null;
	// String responseStr = null;
	//
	// try {
	// if (responseMessage instanceof TextMessage) {
	// msg = (TextMessage) responseMessage;
	// responseStr = msg.getText();
	// System.out.println("-------> " + responseStr + " picked up");
	//
	// } else if (responseMessage instanceof BytesMessage) {
	// System.out
	// .println("Sharp Simulator identifies this as byte message....");
	// BytesMessage bytesMsg = (BytesMessage) responseMessage;
	// SharpSimulator sharpSimulator = new SharpSimulator();
	// FullfillmentHelper ff = new FullfillmentHelper();
	// byte b[] = sharpSimulator.getResponse(ff
	// .getBytesFromBytesMessage(bytesMsg));
	// System.out.println("Sharp Simlulator Response: "
	// + new String(b));
	// }
	// } catch (JMSException jmse) {
	// jmse.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// public void stops() throws JMSException {
	// conn.stop();
	// session.close();
	// conn.close();
	// }
	//	
	//
	//	
	//
	// public static void main(String args[]) throws Exception {
	// SharpSimulatorListener sharpSimluatorListener = new SharpSimulatorListener();
	// sharpSimluatorListener.init();
	// System.out.println("Sharp Simulator Listener Waiting for messages....");
	// }

}
