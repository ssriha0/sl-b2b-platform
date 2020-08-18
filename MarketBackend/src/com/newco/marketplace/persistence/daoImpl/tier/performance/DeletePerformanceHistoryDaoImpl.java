package com.newco.marketplace.persistence.daoImpl.tier.performance;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.tier.performance.DeletePerformanceHistoryDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class DeletePerformanceHistoryDaoImpl extends ABaseImplDao implements DeletePerformanceHistoryDao{
	public void deleteProviderPerfHistory() throws DataServiceException{
		try{
			delete("performance.deleteProviderPerfHistory", null);
		}catch(Exception e){
			
		}
	}
	public void deleteFirmPerfHistory() throws DataServiceException{
		try{
			delete("performance.deleteFirmPerfHistory", null);
		}catch(Exception e){
			
		}
	}
	public void deleteSPNCriteriaHistory() throws DataServiceException{
		try{
			delete("performance.deleteSPNCriteriaHistory", null);
		}catch(Exception e){
			
		}
	}
	public void deleteSPNHdrCriteriaHistory() throws DataServiceException{
		try{
			delete("performance.deleteSPNHdrCriteriaHistory", null);
		}catch(Exception e){
			
		}
	}
	public void deleteSPNFirmHistory() throws DataServiceException{
		try{
			delete("performance.deleteSPNFirmHistory", null);
		}catch(Exception e){
			
		}
	}
	public void deleteSPNProviderHistory() throws DataServiceException{
		try{
			delete("performance.deleteSPNProviderHistory", null);
		}catch(Exception e){
			
		}
	}
}
