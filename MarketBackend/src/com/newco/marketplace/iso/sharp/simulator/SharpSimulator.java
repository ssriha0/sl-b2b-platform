package com.newco.marketplace.iso.sharp.simulator;
import org.springframework.context.ApplicationContext;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.iso.processors.IsoMessageProcessor;
import com.newco.marketplace.iso.processors.IsoRequestProcessor;
import com.newco.marketplace.iso.processors.IsoResponseProcessor;

public class SharpSimulator extends IsoMessageProcessor{

	
/**
 * This method sends a hardcoded NMR response 
 * @return byte[]
 */	

public byte[] getResponse(byte[] requestByteMessage){
	String myString = new String(requestByteMessage);
	String messageIdentifier = getResponeMessageIdentifier(myString) ;
	if(messageIdentifier.equals(FullfillmentConstants.SHARP_HEARTBEAT_REQUEST)) {
		return respondNMR(requestByteMessage);
	}
	else if (messageIdentifier.equals(FullfillmentConstants.ACTIVATION_RELOAD_REQUEST)){
		return respondActivationRequest(requestByteMessage);
	}
	else if (messageIdentifier.equals(FullfillmentConstants.REDEMPTION_REQUEST)){
		return respondRedemptionRequest(requestByteMessage);
	}
	return null;
	
}


public byte[] respondNMR() {
	ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
	IsoRequestProcessor isoRequestProcessor = (IsoRequestProcessor) applicationContext.getBean("isoRequestProcessor");
	byte[] responseBytes =null;
	try {
		FullfillmentEntryVO responseFullfillmentEntryVO = new FullfillmentEntryVO();
		responseFullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.SHARP_HEARTBEAT_RESPONSE);
		responseFullfillmentEntryVO.setStanId("1234");
		responseFullfillmentEntryVO.setActionCode("009");
		responseFullfillmentEntryVO.setTimeStamp(getTimeStamp());
		responseBytes = isoRequestProcessor.processRequest(responseFullfillmentEntryVO);
		
		}
		catch(Exception e){e.printStackTrace();}
		
		return responseBytes;
}
/**
 * This method sends a NMR response for the request
 * @param responseByteMessage
 * @return
 */	
public byte[] respondNMR(byte[] requestByteMessage) {
	ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
	//1. Call the IsoResponseProcessor ( just to reuse existing component ) passing the bytes. 
	//   This will construct a FulfillmentEntryVO (request) object 
	IsoResponseProcessor isoResponseProcessor = (IsoResponseProcessor) applicationContext.getBean("isoResponseProcessor");
	FullfillmentEntryVO requestFulfillmentEntryVO = null;
	try {
		requestFulfillmentEntryVO = isoResponseProcessor.processResponse(requestByteMessage);
	} catch(Exception e){e.printStackTrace();}
	//2. Extract needed information from FulfillmentEntryVO object
	
	//3. Construct another FullfillmentEntryVO (response) object
	//4. Call the IsoRequestProcessor passing the FullfillmentEntryVO (response) object. This returns bytes[]
	//5. Post this message into the queue. 
	
	//2.  
	IsoRequestProcessor isoRequestProcessor = (IsoRequestProcessor) applicationContext.getBean("isoRequestProcessor");
	byte[] responseBytes =null;
	try {
		FullfillmentEntryVO responseFullfillmentEntryVO = new FullfillmentEntryVO();
		responseFullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.SHARP_HEARTBEAT_RESPONSE);
		responseFullfillmentEntryVO.setStanId(requestFulfillmentEntryVO.getStanId());
		responseFullfillmentEntryVO.setActionCode("009");
		responseFullfillmentEntryVO.setTimeStamp(getTimeStamp());
		responseBytes = isoRequestProcessor.processRequest(responseFullfillmentEntryVO);
		
		}
		catch(Exception e){e.printStackTrace();}
		
		return responseBytes;
	}



public byte[] respondActivationRequest(byte[] requestByteMessage) {
	ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
	//1. Call the IsoResponseProcessor ( just to reuse existing component ) passing the bytes. 
	//   This will construct a FulfillmentEntryVO (request) object 
	IsoResponseProcessor isoResponseProcessor = (IsoResponseProcessor) applicationContext.getBean("isoResponseProcessor");
	FullfillmentEntryVO requestFulfillmentEntryVO = null;
	try {
		requestFulfillmentEntryVO = isoResponseProcessor.processResponse(requestByteMessage);
	} catch(Exception e){e.printStackTrace();}

	
	IsoRequestProcessor isoRequestProcessor = (IsoRequestProcessor) applicationContext.getBean("isoRequestProcessor");
	byte[] responseBytes =null;
	try {
		FullfillmentEntryVO responseFullfillmentEntryVO = new FullfillmentEntryVO();
		//Hard coded 

		if (requestFulfillmentEntryVO.getPrimaryAccountNumber().toString().equals(FullfillmentConstants.SHARP_DUMMY_PAN_NUMBER)){ 
			responseFullfillmentEntryVO.setPrimaryAccountNumber(new Long("123" + System.currentTimeMillis()));
		}
		else {
			responseFullfillmentEntryVO.setPrimaryAccountNumber(requestFulfillmentEntryVO.getPrimaryAccountNumber());

		}
		
		responseFullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.ACTIVATION_RELOAD_RESPONSE);
		responseFullfillmentEntryVO.setStanId(requestFulfillmentEntryVO.getStanId());
		responseFullfillmentEntryVO.setTransAmount(new Double(00.00));
		//responseFullfillmentEntryVO.setActionCode("000");
		responseFullfillmentEntryVO.setTimeStamp(getTimeStamp());
		responseFullfillmentEntryVO.setRetrievalRefId(requestFulfillmentEntryVO.getStanId());
		responseFullfillmentEntryVO.setAuthorizationId("028888");
		responseFullfillmentEntryVO.setFullfillmentEntryId(requestFulfillmentEntryVO.getFullfillmentEntryId());
		responseFullfillmentEntryVO.setFullfillmentGroupId(requestFulfillmentEntryVO.getFullfillmentGroupId());
		responseFullfillmentEntryVO.setPromoCode(requestFulfillmentEntryVO.getPromoCode());
		responseFullfillmentEntryVO.setLedgerEntityId(requestFulfillmentEntryVO.getLedgerEntityId());
		responseFullfillmentEntryVO.setAdditionalAmount("0001C000000000000");
		responseFullfillmentEntryVO.setAdditionalResponse("APPROVED ACTIVATED      ");

		responseBytes = isoRequestProcessor.processRequest(responseFullfillmentEntryVO);
		
		
		
		}
		catch(Exception e){e.printStackTrace();}
		
		return responseBytes;
	}

public byte[] respondRedemptionRequest(byte[] requestByteMessage) {
	ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
	IsoResponseProcessor isoResponseProcessor = (IsoResponseProcessor) applicationContext.getBean("isoResponseProcessor");
	FullfillmentEntryVO requestFulfillmentEntryVO = null;
	try {
		requestFulfillmentEntryVO = isoResponseProcessor.processResponse(requestByteMessage);
	} catch(Exception e){e.printStackTrace();}

	
	IsoRequestProcessor isoRequestProcessor = (IsoRequestProcessor) applicationContext.getBean("isoRequestProcessor");
	byte[] responseBytes =null;
	try {
		FullfillmentEntryVO responseFullfillmentEntryVO = new FullfillmentEntryVO();
		//Hard coded 
		if (requestFulfillmentEntryVO.getPrimaryAccountNumber().equals(FullfillmentConstants.SHARP_DUMMY_PAN_NUMBER)){ 
			responseFullfillmentEntryVO.setPrimaryAccountNumber(new Long("123" + System.currentTimeMillis()));
		}
		else {
			responseFullfillmentEntryVO.setPrimaryAccountNumber(requestFulfillmentEntryVO.getPrimaryAccountNumber());

		}
		responseFullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.REDEMPTION_RESPONSE);
		responseFullfillmentEntryVO.setStanId(requestFulfillmentEntryVO.getStanId());
		responseFullfillmentEntryVO.setTransAmount(new Double(00.00));
		//responseFullfillmentEntryVO.setActionCode("000");
		responseFullfillmentEntryVO.setTimeStamp(getTimeStamp());
		responseFullfillmentEntryVO.setRetrievalRefId(requestFulfillmentEntryVO.getStanId());
		responseFullfillmentEntryVO.setAuthorizationId("028888");
		responseFullfillmentEntryVO.setFullfillmentEntryId(requestFulfillmentEntryVO.getFullfillmentEntryId());
		responseFullfillmentEntryVO.setFullfillmentGroupId(requestFulfillmentEntryVO.getFullfillmentGroupId());
		responseFullfillmentEntryVO.setPromoCode(requestFulfillmentEntryVO.getPromoCode());
		responseFullfillmentEntryVO.setLedgerEntityId(requestFulfillmentEntryVO.getLedgerEntityId());
		responseBytes = isoRequestProcessor.processRequest(responseFullfillmentEntryVO);
		
		
		}
		catch(Exception e){e.printStackTrace();}
		
		return responseBytes;
	}

}
