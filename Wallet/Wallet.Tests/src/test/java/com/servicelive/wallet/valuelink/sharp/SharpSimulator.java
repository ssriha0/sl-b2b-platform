package com.servicelive.wallet.valuelink.sharp;

import javax.jms.Queue;

import org.springframework.jms.core.support.JmsGatewaySupport;

// TODO: Auto-generated Javadoc
/**
 * Class SharpSimulator.
 */
public class SharpSimulator extends JmsGatewaySupport implements ISharpGateway {

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

	// TODO Auto-generated method stub

	}

	public void setDelegate(ISharpCallback delegate) {
		// TODO Auto-generated method stub
		
	}

	public Queue getReplyTo() {
		// TODO Auto-generated method stub
		return null;
	}

}
