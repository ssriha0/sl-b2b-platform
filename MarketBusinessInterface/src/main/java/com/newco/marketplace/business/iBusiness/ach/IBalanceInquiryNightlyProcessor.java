package com.newco.marketplace.business.iBusiness.ach;

import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IBalanceInquiryNightlyProcessor {
	public void sendBalanceInquiryMsgs() throws BusinessServiceException;
}
