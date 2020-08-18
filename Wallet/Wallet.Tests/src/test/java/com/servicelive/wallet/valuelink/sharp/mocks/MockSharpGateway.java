package com.servicelive.wallet.valuelink.sharp.mocks;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Queue;

import com.servicelive.wallet.valuelink.sharp.ISharpCallback;
import com.servicelive.wallet.valuelink.sharp.ISharpGateway;

// TODO: Auto-generated Javadoc
/**
 * Class MockSharpGateway.
 */
public class MockSharpGateway implements ISharpGateway {

	/** messages. */
	private static List<String> messages = new ArrayList<String>();
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.ISharpGateway#addDelegate(com.servicelive.wallet.valuelink.sharp.SharpGateway.ISharpCallback)
	 */
	public void addDelegate(ISharpCallback delegate) {

	// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.ISharpGateway#handleMessage(byte[])
	 */
	public void handleMessage(byte[] message) {

	// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.ISharpGateway#sendMessage(byte[])
	 */
	public void sendMessage(byte[] message) {
		messages.add(String.valueOf(message));
	}

	/**
	 * getMessages.
	 * 
	 * @return List<String>
	 */
	public List<String> getMessages(){
		return messages;
	}
	
	/**
	 * resetMessages.
	 * 
	 * @return void
	 */
	public void resetMessages(){
		messages.clear();
	}

	public void setDelegate(ISharpCallback delegate) {
		// TODO Auto-generated method stub
		
	}

	public Queue getReplyTo() {
		// TODO Auto-generated method stub
		return null;
	}
}
