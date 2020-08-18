package com.newco.marketplace.persistence.iDao.tier.performance;

import com.newco.marketplace.exception.core.DataServiceException;

public interface DeletePerformanceHistoryDao {
	public void deleteProviderPerfHistory() throws DataServiceException;
	public void deleteFirmPerfHistory() throws DataServiceException;
	public void deleteSPNCriteriaHistory() throws DataServiceException;
	public void deleteSPNHdrCriteriaHistory() throws DataServiceException;
	public void deleteSPNFirmHistory() throws DataServiceException;
	public void deleteSPNProviderHistory() throws DataServiceException;
}
