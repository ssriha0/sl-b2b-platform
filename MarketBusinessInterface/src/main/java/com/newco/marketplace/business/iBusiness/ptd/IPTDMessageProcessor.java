package com.newco.marketplace.business.iBusiness.ptd;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.ptd.PTDFullfillmentEntryVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IPTDMessageProcessor {
	public String createPTDFailureReportEmail(ArrayList<PTDFullfillmentEntryVO> feVOs);
	public ArrayList<PTDFullfillmentEntryVO> retrieveSLNonReconciledTransactions(String ptdTime);
}
