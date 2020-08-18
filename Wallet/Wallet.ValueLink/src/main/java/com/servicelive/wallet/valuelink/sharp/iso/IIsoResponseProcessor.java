package com.servicelive.wallet.valuelink.sharp.iso;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IIsoResponseProcessor.
 */
public interface IIsoResponseProcessor {

	/**
	 * processResponse.
	 * 
	 * @param responseByteMessage 
	 * 
	 * @return IsoMessageVO
	 * 
	 * @throws StringParseException 
	 * @throws SLBusinessServiceException 
	 */
	public IsoMessageVO processResponse(byte[] responseByteMessage) throws SLBusinessServiceException;

}
