package com.newco.marketplace.web.delegates.zipcoverage;

import java.util.List;

import com.newco.marketplace.dto.vo.zipcoverage.BuyerSpnListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ProviderListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.QuestionsAnswersDTO;
import com.newco.marketplace.dto.vo.zipcoverage.StateNameDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ZipcodeDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IZipCoverageDelegate {
	public String[] getSelectedZipCodesByFirmIdAndFilter(Integer firmId,String stateCode,String zipCode,Integer providerId,
			Integer spnId,Integer spnProId,String spnStateCode, String spnZipCode) throws BusinessServiceException;
	public List<StateNameDTO> getStateNames(String vendorId)throws BusinessServiceException;
	public List<ZipcodeDTO> getZipCodes(String vendorId, String stateCode) throws BusinessServiceException;
	public List<ProviderListDTO> getServiceProviders(String vendorId, String stateCode,String zipCode) throws BusinessServiceException;
	public List<BuyerSpnListDTO> getBuyerSpnDetails(String vendorId) throws BusinessServiceException;
	public List<ProviderListDTO> getBuyerSpnServiceProviders(String vendorId, String spnId) throws BusinessServiceException;
	public List<QuestionsAnswersDTO> getFaqAndAnswers(String faqCategory) throws BusinessServiceException;
	public List<StateNameDTO> getSpnStateNames(String vendorId,String spnId, String providerId) throws BusinessServiceException;
	public List<ZipcodeDTO> getSpnZipCodes(String vendorId, String spnId, String providerId, String stateCode) throws BusinessServiceException;
}
