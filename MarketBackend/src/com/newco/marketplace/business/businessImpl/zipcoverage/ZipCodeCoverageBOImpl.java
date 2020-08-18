package com.newco.marketplace.business.businessImpl.zipcoverage;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.zipcoverage.IZipCodeCoverageBO;
import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.dto.vo.zipcoverage.BuyerSpnListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ProviderListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.QuestionsAnswersDTO;
import com.newco.marketplace.dto.vo.zipcoverage.StateNameDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ZipcodeDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.zipcoverage.IZipcodeCoverageDAO;
import com.sears.os.business.ABaseBO;

public class ZipCodeCoverageBOImpl extends ABaseBO implements IZipCodeCoverageBO{
	
	 private static final Logger logger = Logger.getLogger(ZipCodeCoverageBOImpl.class
	            .getName());

	 private IZipcodeCoverageDAO zipcodeCoverageDAO;
	 
	public String[] getSelectedZipCodesByFirmIdAndFilter(Integer firmId,String stateCode,String zipCode,Integer providerId,
			Integer spnId,Integer spnProId,String spnStateCode, String spnZipCode) throws BusinessServiceException {
		logger.debug("start of getSelectedZipCodesByFirmIdAndFilter of ZipCodeCoverageBOImpl");
		String[] ZipCodes=null;
		List<String> zipCodeList=null;
        try {
        	zipCodeList=getZipcodeCoverageDAO().getSelectedZipCodesByFirmIdAndFilter(firmId, stateCode, zipCode, providerId, spnId, spnProId, spnStateCode, spnZipCode);
        	if(zipCodeList!=null & zipCodeList.size()>0){
        		ZipCodes=new String[zipCodeList.size()];
        		for(int i=0; i<zipCodeList.size(); i++) {
        			ZipCodes[i] = zipCodeList.get(i);
        	      }
				
			}
        }catch (DataServiceException e) {
			logger.error("Error in getSelectedZipCodesByFirmIdAndFilter of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
        logger.debug(" end of getSelectedZipCodesByFirmIdAndFilter of ZipCodeCoverageBOImpl");
        return ZipCodes;

	}
	public List<StateNameDTO> getStateNames(String vendorId)throws BusinessServiceException {
		List<StateNameDTO> stateList=null;
		try {				
			stateList = getZipcodeCoverageDAO().getStateNames(vendorId);
		} catch (Exception e) {
			logger.error("Error in getStateNames of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return stateList;
	}
	public List<ZipcodeDTO> getZipCodes(String vendorId, String stateCode) throws BusinessServiceException {
		List<ZipcodeDTO> zipList=null;
		try {				
			zipList = getZipcodeCoverageDAO().getZipCodes(vendorId, stateCode);
		} catch (Exception e) {
			logger.error("Error in getZipCodes of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return zipList;
	}
	
	public List<ProviderListDTO> getServiceProviders(String vendorId, String stateCode,String zipCode) throws BusinessServiceException {
		List<ProviderListDTO> providerList=null;
		try {				
			providerList = getZipcodeCoverageDAO().getServiceProviders(vendorId, stateCode, zipCode);
		} catch (Exception e) {
			logger.error("Error in getServiceProviders of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return providerList;
	}
	
	public List<BuyerSpnListDTO> getBuyerSpnDetails(String vendorId) throws BusinessServiceException{
		List<BuyerSpnListDTO> spnList=null;
		try {				
			spnList = getZipcodeCoverageDAO().getBuyerSpnDetails(vendorId);
		} catch (Exception e) {
			logger.error("Error in getBuyerSpnDetails of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return spnList;
	}
	
	public List<ProviderListDTO> getBuyerSpnServiceProviders(String vendorId, String spnId) throws BusinessServiceException{
		List<ProviderListDTO> spnProviderList=null;
		try {				
			spnProviderList = getZipcodeCoverageDAO().getBuyerSpnServiceProviders(vendorId, spnId);
		} catch (Exception e) {
			logger.error("Error in getBuyerSpnServiceProviders of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return spnProviderList;
	}
	
	public List<QuestionsAnswersDTO> getFaqAndAnswers(String faqCategory) throws BusinessServiceException {
		List<QuestionsAnswersDTO> faqList=null;
		try {				
			faqList = getZipcodeCoverageDAO().getFaqAndAnswers(faqCategory);
		} catch (Exception e) {
			logger.error("Error in getFaqAndAnswers of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return faqList;
	}
	
	public List<StateNameDTO> getSpnStateNames(String vendorId,String spnId, String providerId) throws BusinessServiceException{
		List<StateNameDTO> stateList=null;
		try {				
			stateList = getZipcodeCoverageDAO().getSpnStateNames(vendorId, spnId, providerId);
		} catch (Exception e) {
			logger.error("Error in getSpnStateNames of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return stateList;
	}
	
	public List<ZipcodeDTO> getSpnZipCodes(String vendorId, String spnId, String providerId, String stateCode) throws BusinessServiceException {
		List<ZipcodeDTO> zipList=null;
		try {				
			zipList = getZipcodeCoverageDAO().getSpnZipCodes(vendorId, spnId, providerId, stateCode);
		} catch (Exception e) {
			logger.error("Error in getSpnZipCodes of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return zipList;
	}
	
	public List<StateZipcodeVO> getOutOfStateDetails(String resourceId) throws BusinessServiceException {
		List<StateZipcodeVO> stateList=null;
		try {				
			stateList = getZipcodeCoverageDAO().getOutOfStateDetails(resourceId);
		} catch (Exception e) {
			logger.error("Error in getOutOfStateDetails of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return stateList;
	}
	
	public String[] getSelectedZipCodesByFirmIdAndResourceId(Integer firmId,Integer providerId) throws BusinessServiceException {
		logger.debug("start of getSelectedZipCodesByFirmIdAndResourceId of ZipCodeCoverageBOImpl");
		String[] ZipCodes=null;
		List<String> zipCodeList=null;
        try {
        	zipCodeList=getZipcodeCoverageDAO().getSelectedZipCodesByFirmIdAndResourceId(firmId, providerId);
        	if(zipCodeList!=null & zipCodeList.size()>0){
        		ZipCodes=new String[zipCodeList.size()];
        		for(int i=0; i<zipCodeList.size(); i++) {
        			ZipCodes[i] = zipCodeList.get(i);
        	      }
				
			}
        }catch (DataServiceException e) {
			logger.error("Error in getSelectedZipCodesByFirmIdAndResourceId of ZipCodeCoverageBOImpl :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
        logger.debug(" end of getSelectedZipCodesByFirmIdAndResourceId of ZipCodeCoverageBOImpl");
        return ZipCodes;

	}
	

	public IZipcodeCoverageDAO getZipcodeCoverageDAO() {
		return zipcodeCoverageDAO;
	}

	public void setZipcodeCoverageDAO(IZipcodeCoverageDAO zipcodeCoverageDAO) {
		this.zipcodeCoverageDAO = zipcodeCoverageDAO;
	}
	
	

}
