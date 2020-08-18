package com.newco.marketplace.persistence.iDao.leadprofile;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;


public interface ISLLeadDao {
	
	public boolean updateLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO) throws DataServiceException;
	public boolean validateProviderFirmLeadEligibility(String providerFirmId) throws DataServiceException;
	public LeadServicePriceVO validateProjectType(String providerFirmId,String projectType) throws DataServiceException;
	public void updatePartnerId(String partnerId, String vendorId) throws DataServiceException;
	public void updateFilterSetId(Integer filterSetId, String partnerId) throws DataServiceException;
	public LeadProfileCreationRequestVO getProfileDetails(String vendorId) throws DataServiceException;
	

}
