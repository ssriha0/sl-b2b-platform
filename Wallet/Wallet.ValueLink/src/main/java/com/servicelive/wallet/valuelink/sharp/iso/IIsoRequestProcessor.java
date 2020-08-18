package com.servicelive.wallet.valuelink.sharp.iso;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageTemplateVO;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IIsoRequestProcessor.
 */
public interface IIsoRequestProcessor {

	/**
	 * processRequest.
	 * 
	 * @param message 
	 * 
	 * @return byte[]
	 * 
	 * @throws UnknownMessageTypeException 
	 * @throws StringParseException 
	 * @throws DataServiceException 
	 * @throws Exception 
	 */
	public byte[] processRequest(IsoMessageVO message) throws UnknownMessageTypeException, StringParseException, DataServiceException, Exception;

	/**
	 * processSpecificData.
	 * 
	 * @param message 
	 * @param isoMessagetemplate 
	 * 
	 * @return void
	 */
	public void processSpecificData(IsoMessageVO message, IsoMessageTemplateVO isoMessagetemplate);

}
