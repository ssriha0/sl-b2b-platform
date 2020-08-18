package com.newco.marketplace.business.iBusiness.tier.performance;

import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IDeletePerformanceHistoryService {
	public void deleteProviderPerfHistory() throws BusinessServiceException;
	public void deleteFirmPerfHistory() throws BusinessServiceException;
	public void deleteSPNCriteriaHistory() throws BusinessServiceException;
	public void deleteSPNHdrCriteriaHistory() throws BusinessServiceException;
	public void deleteSPNFirmHistory() throws BusinessServiceException;
	public void deleteSPNProviderHistory() throws BusinessServiceException;
}
