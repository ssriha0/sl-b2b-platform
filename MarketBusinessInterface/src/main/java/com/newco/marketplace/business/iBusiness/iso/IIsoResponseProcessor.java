package com.newco.marketplace.business.iBusiness.iso;

import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.exception.StringParseException;

public interface IIsoResponseProcessor {

	public FullfillmentEntryVO processResponse(byte[] responseByteMessage)  throws StringParseException;
	
}
