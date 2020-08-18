package com.newco.marketplace.business.iBusiness.nachaadmin;

import com.newco.marketplace.exception.BusinessServiceException;

public interface INachaAdminTool {
	
	public void resetFDRejectedBatch(Integer processLogId, String processOwner) throws BusinessServiceException;
	public void resetOrginationFileRec(Integer ledgerEntryId) throws BusinessServiceException;

}
