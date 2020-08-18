package com.servicelive.wallet.valuelink.sharp;

import com.servicelive.common.exception.SLBusinessServiceException;

/**
 * Interface ISharpCallback.
 */
public interface ISharpCallback {

	/**
	 * handleMessage.
	 * 
	 * @param message 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	void handleMessage(byte[] message) throws SLBusinessServiceException;
}