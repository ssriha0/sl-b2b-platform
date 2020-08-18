package com.newco.marketplace.business.iBusiness.iso;

import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.iso.IsoMessageTemplateVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.StringParseException;
import com.newco.marketplace.exception.UnknownMessageTypeException;

public interface IIsoRequestProcessor {
	//public String processRequest(FullfillmentEntryVO fullfillmentEntryVO) throws UnknownMessageTypeException, StringParseException,DataServiceException;
	public byte[] processRequest(FullfillmentEntryVO fullfillmentEntryVO) throws UnknownMessageTypeException, StringParseException,DataServiceException,Exception;

	public void processSpecificData(FullfillmentEntryVO fullfillmentEntryVO,IsoMessageTemplateVO isoMessagetemplate);
	
	
}
