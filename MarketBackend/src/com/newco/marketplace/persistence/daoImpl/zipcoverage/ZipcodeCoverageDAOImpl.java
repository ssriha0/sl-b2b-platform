package com.newco.marketplace.persistence.daoImpl.zipcoverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.dto.vo.zipcoverage.BuyerSpnListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ProviderListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.QuestionsAnswersDTO;
import com.newco.marketplace.dto.vo.zipcoverage.StateNameDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ZipcodeDTO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.ZipCoverageConstants;
import com.newco.marketplace.persistence.iDao.zipcoverage.IZipcodeCoverageDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class ZipcodeCoverageDAOImpl extends ABaseImplDao implements IZipcodeCoverageDAO,ZipCoverageConstants{
	
	public List<String> getSelectedZipCodesByFirmIdAndFilter(Integer firmId,String stateCode,String zipCode,Integer providerId,
			Integer spnId,Integer spnProId,String spnStateCode, String spnZipCode) throws  DataServiceException{
		
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(FIRMID, firmId);
			if(stateCode!=null)
				params.put(STATECODE, stateCode);
			if(zipCode!=null)
				params.put(ZIPCODE, zipCode);
			if(providerId!=null)
				params.put(RESOURCEID, providerId);
			if(spnId!=null)
				params.put(BUYERSPNID, spnId);
			if(spnProId!=null){
				params.put(BUYERSPNPROVID, spnProId);
				params.put(RESOURCEID, spnProId);
			}
			if(spnStateCode!=null)
				params.put(STATECODE, spnStateCode);
			if(spnZipCode!=null)
				params.put(ZIPCODE, spnZipCode);
			
			
			List<String> zipcodeList=null;
			zipcodeList=(ArrayList<String>)getSqlMapClient().queryForList("zipcodeCoverage.zipcodecoverageByFirmIdAndFilter",params);
			return zipcodeList;
			} catch(Exception e) { 
				logger.debug("Exception in ZipcodeCoverageDAOImpl.getSelectedZipCodesByFirmIdAndFilter due to "+e.getMessage()); 
				throw new DataServiceException(e.getMessage(),e);
			}	
		
	}
	
	public List<StateNameDTO> getStateNames(String vendorId) throws DataServiceException{
		List<StateNameDTO> stateList=null;
		try {				
			stateList = queryForList("zipcodeCoverage.vendorResource.getState", vendorId);
		} catch (Exception ex) {
			logger.error("[ZipcodeCoverageDAOImpl.getStateNames - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return stateList;
	}	
	
	public List<ZipcodeDTO> getZipCodes(String vendorId, String stateCode) throws DataServiceException{
		List<ZipcodeDTO> zipList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(VENDORID, vendorId);
		params.put(STATECD, stateCode);
		try {				
			zipList = queryForList("zipcodeCoverage.vendorResource.getZip", params);
		} catch (Exception ex) {
			logger.error("[ZipcodeCoverageDAOImpl.getZipCodes - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return zipList;
	}	
	
	public List<ProviderListDTO> getServiceProviders(String vendorId, String stateCode,String zipCode) throws DataServiceException{
		List<ProviderListDTO> providerList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(VENDORID, vendorId);
		params.put(STATECD, stateCode);
		params.put(ZIP, zipCode);
		try {				
			providerList = queryForList("zipcodeCoverage.vendorResource.getServicePros", params);
		} catch (Exception ex) {
			logger.error("[ZipcodeCoverageDAOImpl.getServiceProviders - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return providerList;
	}	
	
	public List<BuyerSpnListDTO> getBuyerSpnDetails(String vendorId) throws DataServiceException {
		List<BuyerSpnListDTO> spnList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(VENDORID, vendorId);
		try {				
			spnList = queryForList("zipcodeCoverage.vendorResource.getBuyerSPN", params);
		} catch (Exception ex) {
			logger.error("[ZipcodeCoverageDAOImpl.getBuyerSpnDetails - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return spnList;
	}	
	
	public List<ProviderListDTO> getBuyerSpnServiceProviders(String vendorId, String spnId) throws DataServiceException {
		List<ProviderListDTO> spnProviderList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(VENDORID, vendorId);
		params.put(SPNID, spnId);
		try {				
			spnProviderList = queryForList("zipcodeCoverage.vendorResource.getBuyerSPNServicePros", params);
		} catch (Exception ex) {
			logger.error("[ZipcodeCoverageDAOImpl.getBuyerSpnServiceProviders - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return spnProviderList;
	}	
	
	public List<QuestionsAnswersDTO> getFaqAndAnswers(String faqCategory) throws DataServiceException {
		List<QuestionsAnswersDTO> faqList=null;
		try {				
			faqList = queryForList("zipcodeCoverage.getFaqAnswers", faqCategory);
		} catch (Exception ex) {
			logger.error("[ZipcodeCoverageDAOImpl.getFaqAndAnswers - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return faqList;
	}	
	
	public List<StateNameDTO> getSpnStateNames(String vendorId,String spnId, String providerId) throws DataServiceException{
		List<StateNameDTO> stateList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(VENDORID, vendorId);
		params.put(SPNID, spnId);
		if(providerId!=null)
			params.put(BUYERSPNPROVID, providerId);
		try {				
			stateList = queryForList("zipcodeCoverage.vendorResource.getSpnState", params);
		} catch (Exception ex) {
			logger.error("[ZipcodeCoverageDAOImpl.getSpnStateNames - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return stateList;
	}	
	
	public List<ZipcodeDTO> getSpnZipCodes(String vendorId, String spnId, String providerId, String stateCode) throws DataServiceException{
		List<ZipcodeDTO> zipList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(VENDORID, vendorId);
		params.put(SPNID, spnId);
		if(providerId!=null)
			params.put(BUYERSPNPROVID, providerId);
		if(stateCode!=null)
			params.put(STATECD, stateCode);
		try {				
			zipList = queryForList("zipcodeCoverage.vendorResource.getSpnZip", params);
		} catch (Exception ex) {
			logger.error("[ZipcodeCoverageDAOImpl.getSpnZipCodes - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return zipList;
	}	
	
	public List<StateZipcodeVO> getOutOfStateDetails(String resourceId) throws DataServiceException {
		List<StateZipcodeVO> stateNameList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(RESOURCEID, resourceId);
		try
		{
			stateNameList = queryForList("zipcodeCoverage.vendorResource.getutOfStates", params);
	   }catch (Exception ex) {
		     logger.info("General Exception @ZipcodeCoverageDAOImpl.getOutOfStateDetails() due to"+ex.getMessage());
		     throw new DataServiceException("Error", ex);
	  }
	   return stateNameList;
	}
	
	public List<String> getSelectedZipCodesByFirmIdAndResourceId(Integer firmId,Integer providerId) throws  DataServiceException{
		
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(FIRMID, firmId);
			params.put(RESOURCEID, providerId);	
			List<String> zipcodeList=null;
			zipcodeList=(ArrayList<String>)getSqlMapClient().queryForList("zipcodeCoverage.zipcodecoverageByFirmIdAndResourceId",params);
			return zipcodeList;
			} catch(Exception e) { 
				logger.debug("Exception in ZipcodeCoverageDAOImpl.getSelectedZipCodesByFirmIdAndResourceId due to "+e.getMessage()); 
				throw new DataServiceException(e.getMessage(),e);
			}	
		
	}

}
