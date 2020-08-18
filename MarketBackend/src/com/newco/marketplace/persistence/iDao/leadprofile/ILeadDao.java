package com.newco.marketplace.persistence.iDao.leadprofile;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.leadprofile.LeadInsertFilterResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProjectVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;

public interface ILeadDao {
	
	public LeadProfileCreationResponseVO createLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO) throws Exception ;
	public LeadProfileBillingResponseVO createLeadProfileBilling(LeadProfileBillingRequestVO leadProfileBillingRequestVO) throws Exception ;
	public ProjectDetailsList getleadProjectTypeAndRates(String vendorId) throws Exception ;
	
	public boolean updateLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO) throws DataServiceException;
	public boolean validateProviderFirmLeadEligibility(String providerFirmId) throws DataServiceException;
	public int doCrmRegistration(String request) throws DataServiceException;
	public LeadServicePriceVO validateProjectType(String providerFirmId,String projectType) throws DataServiceException;
	public void updatePartnerId(String partnerId, String vendorId,String wfStatus) throws DataServiceException;
	public void updateFilterSetId(Integer filterSetId, String partnerId, String filterType,String wfStates) throws DataServiceException;
	public LeadProfileCreationRequestVO getProfileDetails(String vendorId) throws DataServiceException;
	public String validateLeadPartnerId(String firmId) throws DataServiceException;
	public String getVendorIdFromPartnerId(String partnerId)throws DataServiceException;
	public LeadInsertFilterResponseVO insertFilterSet(LeadProfileCreationRequestVO leadProfileCreationRequestVO,Map<String, List<String>> projectLists)throws DataServiceException;
	public boolean validateVendor(String vendorId)throws DataServiceException;
	public LeadServicePriceVO getPriceDetails(LeadProjectVO projectVO)throws DataServiceException;
	public void updateCrmStatus(String crmStatus,Integer providerFirmId)throws DataServiceException;
	public void updateLMSError(String vendorId,String errors) throws DataServiceException;
	public void updateLMSStatus(String vendorId,String status,String statusReason) throws DataServiceException;
	public LeadProfileCreationRequestVO getFilterDetails(String providerFirmId) throws DataServiceException;
	//to get the price details of a package Id 
	public HashMap<String,Object> getPackagePriceDetails(String packageId) throws Exception;
	public boolean validateIfFilterPresent(String providerFirmId) throws DataServiceException;
	public LeadProfileCreationRequestVO validateFilterPresent(String vendorId) throws DataServiceException;
	
	public List<String> getLookUpvalues(String commaSeperatedIds,Integer type) throws DataServiceException;
	
	public void deleteFilterForVendor(String vendorId) throws DataServiceException;
	
	
	public void updateVendorStatus(Map<Integer,Integer> vendorIdMap)throws DataServiceException;
	
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public boolean getInsideSalesApiInvocationSwitch()throws DataServiceException;
	public String createInsideSalesProfile(String request,String insideSalesLoginRequestJSON) throws DataServiceException;
	/**
	 * @param isResponse
	 * @param providerFirmId
	 * @throws DataServiceException 
	 */
	public void updateInsideSalesResponse(String isResponse,
			Integer providerFirmId,Integer crmStatus) throws DataServiceException;
	
	public String getState(String businessState);

	

}
