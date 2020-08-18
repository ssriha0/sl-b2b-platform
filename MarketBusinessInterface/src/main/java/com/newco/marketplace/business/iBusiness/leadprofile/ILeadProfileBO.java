package com.newco.marketplace.business.iBusiness.leadprofile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.vo.leadprofile.LeadInsertFilterResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProjectVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;

public interface ILeadProfileBO {

	 //This method will create the LeadProfile for a Provider Firm in SL and call the LMS system for registration.
	public LeadProfileCreationResponseVO createLeadProfile(LeadProfileCreationRequestVO request) throws Exception;
	
	//This method will create the Billing Account for a Provider Firm in the LMS system.
	public LeadProfileBillingResponseVO createLeadProfileBilling(LeadProfileBillingRequestVO request) throws Exception;
	
	//This method will fetch the LMS project details present in SL database.
	public ProjectDetailsList getleadProjectTypeAndRates(String vendorId) throws Exception;
	
	public boolean validateProviderFirmLeadEligibility(String providerFirmId) throws Exception;
	public LeadServicePriceVO validateProjectType(String providerFirmId,String projectType) throws Exception;
	
	public int createCRMProfile(String request) throws Exception;

	//check whether firm has a partnerId
	public String validateLeadPartnerId(String firmId) throws Exception;

	//method to create filters set details
	public LeadInsertFilterResponseVO insertFilterSet(LeadProfileCreationRequestVO leadProfileCreationRequestVO,Map<String, List<String>> projectLists) throws Exception;

	//check whether vendorId exists
	public boolean validateVendor(String vendorId)throws Exception;

	//to get the price details of a vendor for a projectId and categoryId
	public LeadServicePriceVO getPriceDetails(LeadProjectVO projectVO) throws Exception;
	//method to update crm status of vendor hdr
	public void updateCrmStatus(String crmStatus,Integer vendorId)throws Exception;
	
	public boolean validateIfFilterPresent(String providerFirmId) throws Exception;

	public LeadProfileCreationRequestVO validateFilterPresent(String vendorId) throws Exception;

	//to get the price details of a package Id 
	public HashMap<String,Object> getPackagePriceDetails(String packageId) throws Exception;
	
	
	public void updateVendorStatus(Map<Integer,Integer> vendorIdMap) throws Exception;

	/**
	 * @return
	 */
	public boolean getInsideSalesApiInvocationSwitch() throws Exception;

	/**
	 * @param insideSalesRequestJSON
	 * @param string 
	 * @return
	 * @throws Exception 
	 */
	public String createInsideSalesProfile(String insideSalesRequestJSON, String insideSalesLoginRequestJSON) throws Exception;

	/**
	 * @param isResponse
	 * @param vendorId
	 */
	public void updateInsideSalesResponse(String isResponse,
			Integer vendorId,Integer crmStatus)throws Exception;
	
	
	public String getState(String stateCd);
	
	

}
