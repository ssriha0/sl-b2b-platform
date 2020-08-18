package com.newco.marketplace.business.businessImpl.leadprofile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Errors;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.business.iBusiness.leadprofile.ILeadProfileBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.daoImpl.leadsmanagement.LeadManagementDaoFactory;
import com.newco.marketplace.vo.leadprofile.LeadInsertFilterResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProjectVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;

public class LeadProfileBOImpl implements ILeadProfileBO{
	

	private static final Logger logger = Logger.getLogger(LeadProfileBOImpl.class);

	
	private LeadManagementDaoFactory leadManagementDaoFactory;
	
	private int LMSDAO = 1;
	private int CRMDAO = 2;
	private int SLDAO = 3;
	private int SLLEADDAO = 4;
	private int ISDAO = 5;

	/**
	 * create the Lead Profile for a Provider firm in SL and call LMS for registration.
	 * 
	 * @param createLeadProfileRequest
	 * @return
	 * @throws BusinessServiceException
	 */
	
	public LeadProfileCreationResponseVO createLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO) throws Exception{
		LeadProfileCreationResponseVO leadProfileCreationResponseVO=new LeadProfileCreationResponseVO();
		try {
				//save the Lead Profile information in SL database			
			leadProfileCreationResponseVO=getLeadManagementDaoFactory().getLeadDAO(SLDAO).createLeadProfile(leadProfileCreationRequestVO);
			//condition to check successful persistence of data into SL database
			if(null!=leadProfileCreationResponseVO && leadProfileCreationResponseVO.getStatus().equalsIgnoreCase(ResultsCode.LEAD_REGISTRATION_SUCCESS.getCode()))
			{	
				LeadProfileCreationRequestVO detailsVO = getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getProfileDetails(leadProfileCreationRequestVO.getProviderFirmId());
				logger.info("detailsVO::"+detailsVO);
				if(null!=detailsVO){
					copyDetailsToRequet(detailsVO, leadProfileCreationRequestVO);
					//call the LMS API for creating lead profile
					leadProfileCreationResponseVO=getLeadManagementDaoFactory().getLeadDAO(LMSDAO).createLeadProfile(leadProfileCreationRequestVO);
				}else{
					List <String> errorList =  new ArrayList<String>();
					errorList.add(ResultsCode.INCOMPLETE_SL_PROFILE.getMessage());
					Errors errors = new Errors();
					errors.setError(errorList);
					leadProfileCreationResponseVO.setErrors(errors);
					leadProfileCreationResponseVO.setResult(ResultsCode.INCOMPLETE_SL_PROFILE.getMessage());
					leadProfileCreationResponseVO.setStatus(ResultsCode.INCOMPLETE_SL_PROFILE.getCode());
				}
				
			}
			
		} //catch (DataServiceException e) {
		catch (Exception e) {
			logger.info("Exception while creating the Lead Profile"+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
				
		return leadProfileCreationResponseVO;
	}

	public LeadProfileBillingResponseVO createLeadProfileBilling(LeadProfileBillingRequestVO leadProfileBillingRequestVO) throws Exception{
		LeadProfileBillingResponseVO leadProfileBillingResponseVO=new LeadProfileBillingResponseVO();
		try {
			//call the LMS API for creating lead profile Billing account
			
			leadProfileBillingResponseVO=getLeadManagementDaoFactory().getLeadDAO(LMSDAO).createLeadProfileBilling(leadProfileBillingRequestVO);
		} //catch (DataServiceException e) {
		catch (Exception e) {
			logger.info("Exception while creating the lead billing account"+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return leadProfileBillingResponseVO;
		
	}
	
	
	public void updateVendorStatus(Map<Integer,Integer> vendorIdMap) throws Exception {
		
		getLeadManagementDaoFactory().getLeadDAO(LMSDAO).updateVendorStatus(vendorIdMap);
	}
	
	
	public ProjectDetailsList getleadProjectTypeAndRates(String vendorId) throws Exception{
		ProjectDetailsList projectList  = null;	
		try {
			//fetch Lead Project-type details from SL database
			projectList = getLeadManagementDaoFactory().getLeadDAO(SLDAO).getleadProjectTypeAndRates(vendorId);
			
		} //catch (DataServiceException e) {
		catch (Exception e) {
			logger.info("Exception while fetching the Lead project type details"+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return projectList;
	}
	
	//insert filter set list
	public LeadInsertFilterResponseVO insertFilterSet(LeadProfileCreationRequestVO leadProfileCreationRequestVO,
			Map<String, List<String>> projectLists) throws Exception{
		LeadInsertFilterResponseVO responseVO = new LeadInsertFilterResponseVO();
		try {
			//save the filter set information in SL database
			getLeadManagementDaoFactory().getLeadDAO(SLDAO).deleteFilterForVendor(leadProfileCreationRequestVO.getProviderFirmId());
			responseVO = getLeadManagementDaoFactory().getLeadDAO(SLDAO).insertFilterSet(leadProfileCreationRequestVO,projectLists);
			//call the LMS API for creating lead profile
			if(null != responseVO && responseVO.getStatus().equalsIgnoreCase(ResultsCode.FILTER_CREATION_SUCCESS.getCode())){
				//Get firm deatils
				LeadProfileCreationRequestVO firmDetailsVO = getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getProfileDetails(leadProfileCreationRequestVO.getProviderFirmId());
				// Get the basic details
				LeadProfileCreationRequestVO detailsVO = getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
						getFilterDetails(leadProfileCreationRequestVO.getProviderFirmId());
				copyDetailsToRequet(firmDetailsVO, leadProfileCreationRequestVO);
				copyDetailsToRequestVO(detailsVO, leadProfileCreationRequestVO);
				responseVO = getLeadManagementDaoFactory().getLeadDAO(LMSDAO).insertFilterSet(leadProfileCreationRequestVO,projectLists);	
			}
			else{
				List <String> errorList =  new ArrayList<String>();
				errorList.add(ResultsCode.FILTER_CREATION_FAILURE.getMessage());
				Errors errors = new Errors();
				errors.setError(errorList);
				responseVO.setErrors(errors);
				responseVO.setResult(ResultsCode.FILTER_CREATION_FAILURE.getMessage());
				responseVO.setStatus(ResultsCode.FILTER_CREATION_FAILURE.getCode());
			}
		} 
		catch (Exception e) {
			logger.info("Exception while creating the Lead Profile"+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return responseVO;
	}
	

	//Method validating eligibility of provider firm
	public boolean validateProviderFirmLeadEligibility(String providerFirmId) throws Exception
	{
		return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).validateProviderFirmLeadEligibility(providerFirmId);
	}
	
    //Method validating the project type associated with provider firm
	public LeadServicePriceVO validateProjectType(String providerFirmId,String projectType) throws Exception
	{
		return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).validateProjectType(providerFirmId,projectType);
	}
	
	//check whether firm has a partnerId
	public String validateLeadPartnerId(String firmId) throws Exception{
		return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).validateLeadPartnerId(firmId);
	}
	
	//check whether vendorId exists
	public boolean validateVendor(String vendorId) throws Exception{
		return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).validateVendor(vendorId);
	}
	
	//to get the price details of a vendor for a projectId and categoryId
	public LeadServicePriceVO getPriceDetails(LeadProjectVO projectVO) throws Exception{
		return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getPriceDetails(projectVO);
	}
	//to get the price details of a package Id 
	public HashMap<String,Object> getPackagePriceDetails(String packageId) throws Exception
	{
		return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getPackagePriceDetails(packageId);	
	}
	//Method to update the crm status 
	public void updateCrmStatus(String crmStatus,Integer providerFirmId)throws Exception
	{
		getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).updateCrmStatus(crmStatus,providerFirmId);
	}
	//Method to update the is status 
		public void updateInsideSalesResponse(String isResponse,Integer providerFirmId,Integer crmStatus)throws Exception
		{
			getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).updateInsideSalesResponse(isResponse,providerFirmId,crmStatus); 
		}
		// method to get the state description
		public String getState(String stateCd)
		{
		try {
			return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO)
					.getState(stateCd);
		} catch (Exception e) {
			logger.error("Exception while fetching the state description"
					+ e.getStackTrace());
			return null;
		}
		
		}
	//method to set the details fetched from db in the request for create lead
	private void copyDetailsToRequet(LeadProfileCreationRequestVO detailsVO, LeadProfileCreationRequestVO leadProfileCreationRequestVO) {
		if(null != detailsVO && null != leadProfileCreationRequestVO){
			leadProfileCreationRequestVO.setCompanyName(detailsVO.getCompanyName());
			leadProfileCreationRequestVO.setFirstName(detailsVO.getFirstName());
			leadProfileCreationRequestVO.setLastName(detailsVO.getLastName());
			leadProfileCreationRequestVO.setAddress(detailsVO.getAddress());
			leadProfileCreationRequestVO.setStreet2(detailsVO.getStreet2());
			leadProfileCreationRequestVO.setCity(detailsVO.getCity());
			leadProfileCreationRequestVO.setState(detailsVO.getState());
			leadProfileCreationRequestVO.setCountry(detailsVO.getCountry());
			leadProfileCreationRequestVO.setZip(detailsVO.getZip());
			leadProfileCreationRequestVO.setContactEmail(detailsVO.getContactEmail());
			leadProfileCreationRequestVO.setFax(detailsVO.getFax());
		}
	}
	//method to set the details fetched from db in the request for filter set
	private void copyDetailsToRequestVO(LeadProfileCreationRequestVO detailsVO,	LeadProfileCreationRequestVO leadProfileCreationRequestVO) {
		if(null != detailsVO && null != leadProfileCreationRequestVO){
			leadProfileCreationRequestVO.setLeadDailyLimit(detailsVO.getLeadDailyLimit());
			leadProfileCreationRequestVO.setLeadMonthlyLimit(detailsVO.getLeadMonthlyLimit());
			leadProfileCreationRequestVO.setMondayInd(detailsVO.getMondayInd());
			leadProfileCreationRequestVO.setTuesndayInd(detailsVO.getTuesndayInd());
			leadProfileCreationRequestVO.setWednesdayInd(detailsVO.getWednesdayInd());
			leadProfileCreationRequestVO.setThursdayInd(detailsVO.getThursdayInd());
			leadProfileCreationRequestVO.setFridayInd(detailsVO.getFridayInd());
			leadProfileCreationRequestVO.setSaturdayInd(detailsVO.getSaturdayInd());
			leadProfileCreationRequestVO.setSundayInd(detailsVO.getSundayInd());
			leadProfileCreationRequestVO.setServiceTime(detailsVO.getServiceTime());
			leadProfileCreationRequestVO.setLocationType(detailsVO.getLocationType());
			leadProfileCreationRequestVO.setPartnerId(detailsVO.getPartnerId());
			leadProfileCreationRequestVO.setSkill(detailsVO.getSkill());
			leadProfileCreationRequestVO.setUrgencyServices(detailsVO.getUrgencyServices());
			leadProfileCreationRequestVO.setCoverageInMiles(detailsVO.getCoverageInMiles());
			leadProfileCreationRequestVO.setIsLicensingRequired(detailsVO.getIsLicensingRequired());
			leadProfileCreationRequestVO.setLicensingStates(detailsVO.getLicensingStates());
			leadProfileCreationRequestVO.setLmsPassword(detailsVO.getLmsPassword());
			leadProfileCreationRequestVO.setLeadEmailId(detailsVO.getLeadEmailId());
			leadProfileCreationRequestVO.setLeadPhoneNo(detailsVO.getLeadPhoneNo());
			leadProfileCreationRequestVO.setLeadSmsNo(detailsVO.getLeadSmsNo());
			leadProfileCreationRequestVO.setLeadPackageID(detailsVO.getLeadPackageID());
			leadProfileCreationRequestVO.setLeadPackageDesc(detailsVO.getLeadPackageDesc());
			leadProfileCreationRequestVO.setLeadMonthlyLimit(detailsVO.getLeadMonthlyLimit());
			leadProfileCreationRequestVO.setLeadDailyLimit(detailsVO.getLeadDailyLimit());
			leadProfileCreationRequestVO.setMonthlyBudget(detailsVO.getMonthlyBudget());
		}
		
	}

	public LeadManagementDaoFactory getLeadManagementDaoFactory() {
		return leadManagementDaoFactory;
	}

	public void setLeadManagementDaoFactory(
			LeadManagementDaoFactory leadManagementDaoFactory) {
		this.leadManagementDaoFactory = leadManagementDaoFactory;
	}

	public int createCRMProfile(String request) throws Exception {
		return getLeadManagementDaoFactory().getLeadDAO(CRMDAO).doCrmRegistration(request);
	}
	
	public boolean validateIfFilterPresent(String providerFirmId) throws Exception{
		return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).validateIfFilterPresent(providerFirmId);
	}
	
	public LeadProfileCreationRequestVO validateFilterPresent(String vendorId) throws Exception{
		return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).validateFilterPresent(vendorId);
	}

	/**
	 * @return
	 */
	public boolean getInsideSalesApiInvocationSwitch() throws Exception{
		return getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getInsideSalesApiInvocationSwitch();
	}

	/**
	 * @param insideSalesRequestJSON
	 * @return
	 */
	public String createInsideSalesProfile(String insideSalesRequestJSON, String insideSalesLoginRequestJSON) throws Exception{
		
		return getLeadManagementDaoFactory().getLeadDAO(ISDAO).createInsideSalesProfile(insideSalesRequestJSON,insideSalesLoginRequestJSON);

	}
	
}
