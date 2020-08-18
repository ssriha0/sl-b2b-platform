package com.servicelive.shipregistration.dao;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;

public interface ISHIPRegistrationDao {
	public Integer getSLIndustry(String productDesc) throws DataServiceException;;
	public List<String> getUsernameLike(String username) throws DataServiceException;;
	public void insertSubContractorInfo(
			ProviderRegistrationVO providerRegistrationVO)
	throws DataServiceException;
	public Integer getVendorIdForSubContractorForResource(Integer subContractorId)throws DataServiceException;
	public String getUserName(String username) throws DataServiceException;
	public Integer getVendorIdForFirm(Integer subContractorId) throws DataServiceException;
	public String getValidBusinessStateCd(String businessState) throws DataServiceException;
	public void insertInsuranceTypes(CredentialProfile credentialProfile)throws DataServiceException;
	public void updateInsuranceInd(Integer vendorId, int i)throws DataServiceException;
	public List<Integer> getVendorIdForProviderCrewId(ProviderRegistrationVO registrationVO)throws DataServiceException;
	
}
