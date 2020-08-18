package com.newco.marketplace.persistence.daoImpl.leadprofile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.leadprofile.ILeadDao;
import com.newco.marketplace.vo.leadprofile.LeadInsertFilterResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProjectVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;
import com.sears.os.dao.impl.ABaseImplDao;


public class SLLeadDaoImpl extends ABaseImplDao implements ILeadDao {

	private static final Logger logger = Logger.getLogger(SLLeadDaoImpl.class);
	/*
	 * Logic to update data into the ServiceLive Lead related tables should go here
	 * 
	 */
	public boolean updateLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO) throws DataServiceException 
	{
		try{
			
			logger.info("Inside1 SLLeadDaoImpl.updateLeadProfile()");
			//update lms_billing_account_ind & lead_t_c_ind
			update("update_billing_ind.query",leadProfileCreationRequestVO);
			logger.info("Inside2 SLLeadDaoImpl.updateLeadProfile()");
			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return true;
	}
	
	public boolean validateProviderFirmLeadEligibility(String providerFirmId) throws DataServiceException 
	{
		//Provider firm present in vendor_lead_profile table 
		boolean leadAvailableInd=true;		
		try {
			
			Integer leadInfo= (Integer) queryForObject("lead_availability_indicator.query", providerFirmId);
			logger.info("validateProviderFirmLeadEligibility leadInfo :" + leadInfo);
			if(null!= leadInfo && leadInfo != 0 ){
				leadAvailableInd = false;	
				logger.info("validateProviderFirmLeadEligibility return false:");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		logger.info("validateProviderFirmLeadEligibility return :" + leadAvailableInd);
		return leadAvailableInd;
	}

	public LeadServicePriceVO validateProjectType(String providerFirmId,String projectType) throws DataServiceException 
	{
		//Validate the price type using the provider firm id and  state, if project valid set the price data into the object
		LeadServicePriceVO leadServicePriceVO=new LeadServicePriceVO();
		try {
			HashMap<String,String> leadInfo = new HashMap<String, String>();
			leadInfo.put("providerFirmId",providerFirmId);
			leadInfo.put("projectType",projectType);
			leadServicePriceVO= (LeadServicePriceVO) queryForObject("lead_project_type_indicator.query", leadInfo);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return leadServicePriceVO;
	}


	public void updatePartnerId(String partnerId, String vendorId,String wfStatus) throws DataServiceException
	{
		try{
			HashMap<String,String> params = new HashMap<String, String>();
			params.put("partnerId", partnerId);
			params.put("vendorId", vendorId);
			params.put("wfStatus", wfStatus);
			update("lead_profile.update_partnerId", params);
			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
	}


	public void updateFilterSetId(Integer filterSetId, String partnerId, String filterType,String wfStates) throws DataServiceException
	{
		try{
			HashMap<String,String> params = new HashMap<String, String>();
			params.put("filterSetId", filterSetId.toString());
			params.put("partnerId", partnerId);
			params.put("filterType", filterType);
			params.put("wfStatus", wfStates);
			update("lead_profile.update_filterSetId", params);
			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		
	}


	public LeadProfileCreationRequestVO getProfileDetails(String vendorId) throws DataServiceException
	{
		LeadProfileCreationRequestVO requestVO = null;
		try{
			requestVO = (LeadProfileCreationRequestVO) queryForObject("getProfileDetails.query", vendorId);
			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return requestVO;
	}
	
	public String validateLeadPartnerId(String vendorId)throws DataServiceException{
		String partnerId = null;
		try{
			Integer id = (Integer)queryForObject("lead_profile.getPartnerIdForFirm", vendorId);
			if(null != id){
				partnerId = id.toString();
			}
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return partnerId;
	}
	//Method to update crm status in the vendor hdr table 
	public void updateCrmStatus(String crmStatus,Integer providerFirmId)throws DataServiceException
	{
		try{
			HashMap<String,Object> params = new HashMap<String, Object>();
			params.put("crmStatus", Integer.parseInt(crmStatus));
			params.put("vendorId", providerFirmId);
			update("update_crmStatus.update", params);
			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
	}
	
	public boolean validateVendor(String vendorId) throws DataServiceException {
		boolean isValid = false;
		try{
			Integer count = (Integer)queryForObject("lead_profile.checkVendorId", vendorId);
			if(count.intValue()>0){
				isValid = true;
			}
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return isValid;
	}
	
	public LeadServicePriceVO getPriceDetails(LeadProjectVO projectVO)throws DataServiceException{
		LeadServicePriceVO leadServicePriceVO = null;
		try {
			leadServicePriceVO = (LeadServicePriceVO) queryForObject("lead_project_type_indicator.query", projectVO);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return leadServicePriceVO;
	}

	//to get the price details of a package Id 
	public HashMap<String,Object> getPackagePriceDetails(String packageId) throws Exception
	{
		HashMap<String,Object> packageDetails = new HashMap<String, Object>();	
		try {
			packageDetails = (HashMap<String, Object>) queryForObject("lead_package_datails.query", packageId);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return packageDetails;
	}	
	public LeadProfileCreationResponseVO createLeadProfile(
			LeadProfileCreationRequestVO leadProfileCreationRequestVO)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public LeadProfileBillingResponseVO createLeadProfileBilling(
			LeadProfileBillingRequestVO leadProfileBillingRequestVO)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ProjectDetailsList getleadProjectTypeAndRates(
			String vendorId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public int doCrmRegistration(String request) throws DataServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public LeadInsertFilterResponseVO insertFilterSet(
			LeadProfileCreationRequestVO leadProfileCreationRequestVO,Map<String, List<String>> projectLists)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Update the errors from LMS
	 */
	public void updateLMSError(String vendorId, String errors)
			throws DataServiceException {	
		try{
			HashMap<String,String> params = new HashMap<String, String>();
			params.put("errors", errors);
			params.put("vendorId", vendorId);		
			update("lead_profile.update_lms_error", params);			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
	}

	/**
	 * Update LMS status in to SL DB
	 */
	public void updateLMSStatus(String vendorId, String status,
			String statusReason) throws DataServiceException {
		try{
			HashMap<String,String> params = new HashMap<String, String>();
			params.put("status", status);
			params.put("vendorId", vendorId);
			params.put("statusReason", statusReason);
			update("lead_profile.update_lms_status", params);			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		
	}

	public LeadProfileCreationRequestVO getFilterDetails(String vendorId) throws DataServiceException {
		LeadProfileCreationRequestVO requestVO = null;
		try{
			requestVO = (LeadProfileCreationRequestVO) queryForObject("getFilterDetails.query", vendorId);
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return requestVO;
	}
	
	public boolean validateIfFilterPresent(String providerFirmId)
			throws DataServiceException {		
		boolean filterAvailable=false;		
		LeadProfileCreationRequestVO requestVO = null;
		try {
			requestVO = (LeadProfileCreationRequestVO) queryForObject("getFilters.query", providerFirmId);
			if(null!=requestVO && (null!=requestVO.getCompFilterId() || null!=requestVO.getExclFilterId())){
				filterAvailable = true;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return filterAvailable;
	}

	public String getVendorIdFromPartnerId(String partnerId)
			throws DataServiceException {
		String vendor_Id = null;
		try{
			Integer id = (Integer)queryForObject("lead_profile.getVendorIdFormPartnerId", partnerId);
			if(null != id){
				vendor_Id = id.toString();
			}
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return vendor_Id;
	}
	
	public LeadProfileCreationRequestVO validateFilterPresent(String vendorId) throws DataServiceException{
		LeadProfileCreationRequestVO requestVO = null;
		try {
			requestVO = (LeadProfileCreationRequestVO) queryForObject("getFilters.query", vendorId);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return requestVO;
	}
	

	/**
	 * Get look up data
	 */
	@SuppressWarnings("unchecked")
	public List<String> getLookUpvalues(String commaSeperatedIds, Integer type)
			throws DataServiceException {
		/* 
		 * type 1 is lu_lead_service_urgency
		 * type 2 is lu_lead_skills 
		 */
		List<String> result = null;
		
		String[] parametersArray = StringUtils.split(commaSeperatedIds, ',');
		List<String> parameters = Arrays.asList(parametersArray);
		
		logger.info("parameters:::"+parameters.size());
		
		try {
			if(type == 1){
				result = (List<String>)queryForList("getServiceurgency.query", parameters);				
			} else if (type == 2){
				result=   (List<String>)queryForList("getSkill.query", parameters);
			}
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return result;
	}

	public void deleteFilterForVendor(String vendorId)
			throws DataServiceException {
		
	}
	
	
	public void updateVendorStatus(Map<Integer,Integer> vendorIdMap)throws DataServiceException{
		
	}

	public boolean getInsideSalesApiInvocationSwitch()
			throws DataServiceException {
		try{
			String isSwitch= (String) queryForObject("inside_sales_api_invoke_switch.query", ProviderConstants.INSIDE_SALES_SWITCH);
			return Boolean.parseBoolean(isSwitch);
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
	}

	public String createInsideSalesProfile(String request,String insideSalesLoginRequestJSON)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateInsideSalesResponse(String isResponse,
			Integer providerFirmId,Integer crmStatus)throws DataServiceException {
		try{
			HashMap<String,Object> params = new HashMap<String, Object>();
			params.put("isResponse", isResponse);
			params.put("vendorId", providerFirmId);
			params.put("crmStatus", crmStatus);
			update("update_isResponse.update", params);
			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
	}

	public String getState(String businessState){
		String stateCd=null;
		try{
			stateCd=(String)queryForObject("getStateName.query",businessState);
		}
		catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return stateCd;
	}

	
	
	
}
