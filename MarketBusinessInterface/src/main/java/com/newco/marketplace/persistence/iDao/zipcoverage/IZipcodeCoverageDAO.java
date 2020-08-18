package com.newco.marketplace.persistence.iDao.zipcoverage;

import java.util.List;

import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.dto.vo.zipcoverage.BuyerSpnListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ProviderListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.QuestionsAnswersDTO;
import com.newco.marketplace.dto.vo.zipcoverage.StateNameDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ZipcodeDTO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IZipcodeCoverageDAO {

	public List<String> getSelectedZipCodesByFirmIdAndFilter(Integer firmId,String stateCode,String zipCode,Integer providerId,
			Integer spnId,Integer spnProId,String spnStateCode, String spnZipCode) throws  DataServiceException;
	public List<StateNameDTO> getStateNames(String vendorId) throws DataServiceException;
	public List<ZipcodeDTO> getZipCodes(String vendorId, String stateCode) throws DataServiceException;
	public List<ProviderListDTO> getServiceProviders(String vendorId, String stateCode,String zipCode) throws DataServiceException;
	public List<BuyerSpnListDTO> getBuyerSpnDetails(String vendorId) throws DataServiceException;
	public List<ProviderListDTO> getBuyerSpnServiceProviders(String vendorId, String spnId) throws DataServiceException;
	public List<QuestionsAnswersDTO> getFaqAndAnswers(String faqCategory) throws DataServiceException;
	public List<StateNameDTO> getSpnStateNames(String vendorId,String spnId, String providerId) throws DataServiceException;
	public List<ZipcodeDTO> getSpnZipCodes(String vendorId, String spnId, String providerId, String stateCode) throws DataServiceException;
	public List<StateZipcodeVO> getOutOfStateDetails(String resourceId) throws DataServiceException;
	public List<String> getSelectedZipCodesByFirmIdAndResourceId(Integer firmId,Integer providerId) throws  DataServiceException;
}
