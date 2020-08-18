package com.newco.marketplace.web.delegatesImpl.zipcoverage;

import java.util.List;

import com.newco.marketplace.business.iBusiness.zipcoverage.IZipCodeCoverageBO;
import com.newco.marketplace.dto.vo.zipcoverage.BuyerSpnListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ProviderListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.QuestionsAnswersDTO;
import com.newco.marketplace.dto.vo.zipcoverage.StateNameDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ZipcodeDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.zipcoverage.IZipCoverageDelegate;

public class ZipCoverageDelegatesImpl implements IZipCoverageDelegate{
	
	private IZipCodeCoverageBO zipCodeCoverageBO;
	
	public String[] getSelectedZipCodesByFirmIdAndFilter(Integer firmId,String stateCode,String zipCode,Integer providerId,
			Integer spnId,Integer spnProId,String spnStateCode, String spnZipCode) throws BusinessServiceException{
		return zipCodeCoverageBO.getSelectedZipCodesByFirmIdAndFilter(firmId, stateCode, zipCode, providerId, 
				spnId, spnProId, spnStateCode, spnZipCode);
	}
	
	public List<StateNameDTO> getStateNames(String vendorId)throws BusinessServiceException{
		return zipCodeCoverageBO.getStateNames(vendorId);
	}
	
	public List<ZipcodeDTO> getZipCodes(String vendorId, String stateCode) throws BusinessServiceException {
		return zipCodeCoverageBO.getZipCodes(vendorId, stateCode);
	}
	
	public List<ProviderListDTO> getServiceProviders(String vendorId, String stateCode,String zipCode) throws BusinessServiceException{
		return zipCodeCoverageBO.getServiceProviders(vendorId, stateCode, zipCode);
	}
	
	public List<BuyerSpnListDTO> getBuyerSpnDetails(String vendorId) throws BusinessServiceException{
		return zipCodeCoverageBO.getBuyerSpnDetails(vendorId);
	}
	
	public List<ProviderListDTO> getBuyerSpnServiceProviders(String vendorId, String spnId) throws BusinessServiceException {
		return zipCodeCoverageBO.getBuyerSpnServiceProviders(vendorId, spnId);
	}
	
	public List<QuestionsAnswersDTO> getFaqAndAnswers(String faqCategory) throws BusinessServiceException{
		return zipCodeCoverageBO.getFaqAndAnswers(faqCategory);
	}
	
	public List<StateNameDTO> getSpnStateNames(String vendorId,String spnId, String providerId) throws BusinessServiceException{
		return zipCodeCoverageBO.getSpnStateNames(vendorId, spnId, providerId);
	}
	
	public List<ZipcodeDTO> getSpnZipCodes(String vendorId, String spnId, String providerId, String stateCode) throws BusinessServiceException{
		return zipCodeCoverageBO.getSpnZipCodes(vendorId, spnId, providerId, stateCode);
	}

	public IZipCodeCoverageBO getZipCodeCoverageBO() {
		return zipCodeCoverageBO;
	}

	public void setZipCodeCoverageBO(IZipCodeCoverageBO zipCodeCoverageBO) {
		this.zipCodeCoverageBO = zipCodeCoverageBO;
	}
	
	

}
