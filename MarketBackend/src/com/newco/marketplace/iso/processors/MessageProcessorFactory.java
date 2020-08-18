package com.newco.marketplace.iso.processors;

import com.newco.marketplace.interfaces.FullfillmentConstants;

public class MessageProcessorFactory {

	static MessageProcessorFactory _INSTANCE_MESSAGE_FACTORY = null; 
	private MessageProcessorFactory(){
		_INSTANCE_MESSAGE_FACTORY = new MessageProcessorFactory();
		
	}
	public static MessageProcessorFactory getInstance(){
		if (_INSTANCE_MESSAGE_FACTORY ==null){
			_INSTANCE_MESSAGE_FACTORY = new MessageProcessorFactory();
			return _INSTANCE_MESSAGE_FACTORY;
		}
		else 
		{
			return _INSTANCE_MESSAGE_FACTORY;
		}
		
	}
	public IsoMessageProcessor getMyProcessor(String messageTypeIdentifier) {
		if (messageTypeIdentifier.equals(FullfillmentConstants.SHARP_REQUEST_MTI)) {
			return new IsoRequestProcessor();
		}
		if (messageTypeIdentifier.equals(FullfillmentConstants.SHARP_RESPONSE_MTI)) {
			return new IsoResponseProcessor();
		}
		else 
		{
			return null;
		}
	}
	
}
