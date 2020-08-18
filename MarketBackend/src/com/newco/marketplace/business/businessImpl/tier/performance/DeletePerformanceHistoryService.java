package com.newco.marketplace.business.businessImpl.tier.performance;

import com.newco.marketplace.business.iBusiness.tier.performance.IDeletePerformanceHistoryService;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.tier.performance.DeletePerformanceHistoryDao;

public class DeletePerformanceHistoryService implements IDeletePerformanceHistoryService {
	
	private DeletePerformanceHistoryDao deletePerformanceHistoryDao;
	
	public void deleteProviderPerfHistory() throws BusinessServiceException{
		try{
			deletePerformanceHistoryDao.deleteProviderPerfHistory();
			
		}catch(DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public void deleteFirmPerfHistory() throws BusinessServiceException{
		try{
			deletePerformanceHistoryDao.deleteFirmPerfHistory();
			
		}catch(DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public void deleteSPNCriteriaHistory() throws BusinessServiceException{
		try{
			deletePerformanceHistoryDao.deleteSPNCriteriaHistory();
			
		}catch(DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public void deleteSPNHdrCriteriaHistory() throws BusinessServiceException{
		try{
			deletePerformanceHistoryDao.deleteSPNHdrCriteriaHistory();
			
		}catch(DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public void deleteSPNFirmHistory() throws BusinessServiceException{
		try{
			deletePerformanceHistoryDao.deleteSPNFirmHistory();
			
		}catch(DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public void deleteSPNProviderHistory() throws BusinessServiceException{
		try{
			deletePerformanceHistoryDao.deleteSPNProviderHistory();
			
		}catch(DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public DeletePerformanceHistoryDao getDeletePerformanceHistoryDao() {
		return deletePerformanceHistoryDao;
	}
	public void setDeletePerformanceHistoryDao(
			DeletePerformanceHistoryDao deletePerformanceHistoryDao) {
		this.deletePerformanceHistoryDao = deletePerformanceHistoryDao;
	}
}
