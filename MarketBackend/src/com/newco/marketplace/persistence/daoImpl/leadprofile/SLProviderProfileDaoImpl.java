package com.newco.marketplace.persistence.daoImpl.leadprofile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.leadprofile.ILeadDao;
import com.newco.marketplace.vo.leadprofile.GetLeadProjectTypeResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadInsertFilterResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProjectVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;
import com.sears.os.dao.impl.ABaseImplDao;


public class SLProviderProfileDaoImpl extends ABaseImplDao implements ILeadDao {

	private static final Logger logger = Logger.getLogger(SLProviderProfileDaoImpl.class);

	public ProjectDetailsList getleadProjectTypeAndRates(String vendorId)	throws Exception {
		List<GetLeadProjectTypeResponseVO> projectList = new ArrayList<GetLeadProjectTypeResponseVO>();

		
		ProjectDetailsList projectDetailsList  = new ProjectDetailsList();
		List<GetLeadProjectTypeResponseVO> projects = new ArrayList<GetLeadProjectTypeResponseVO>();
		projectList = (List<GetLeadProjectTypeResponseVO>)queryForList("lead_profile.getProjectTypesAndRates", vendorId);
		
		
		if(null != projectList && !projectList.isEmpty()){
			Integer mappingId = 0;
			for(GetLeadProjectTypeResponseVO project : projectList){
				if(null != project && mappingId.intValue() != project.getMappingId().intValue()){
					projects.add(project);
				}
				mappingId = project.getMappingId();
			}
		}
		
		
		/**
		 * Map (Category id, list of List<GetLeadProjectTypeResponseVO>)
		 * 
		 * 
		*/
		TreeMap<Integer,List<GetLeadProjectTypeResponseVO>> hm = new TreeMap<Integer,List<GetLeadProjectTypeResponseVO>>();
		int categoryId = 0;
		int tempCategoryId=0;
		int countCategory = 0;
		List<GetLeadProjectTypeResponseVO> projectCatList = new ArrayList<GetLeadProjectTypeResponseVO>();
		for(GetLeadProjectTypeResponseVO project : projects){
			
			if(null != project && null != project.getCategoryID()){
				categoryId = project.getCategoryID();
				if(countCategory == 0){
					tempCategoryId = categoryId;
				}
				countCategory = countCategory + 1;
				if(tempCategoryId == categoryId){
					projectCatList.add(project);
				}else{
					countCategory = 0;
					List<GetLeadProjectTypeResponseVO> newProjectCatList = new ArrayList<GetLeadProjectTypeResponseVO>();
					newProjectCatList.addAll(projectCatList);
					hm.put(tempCategoryId, newProjectCatList);
					projectCatList.clear();
					projectCatList.add(project);
				}
			}
			
		}
		
		if(null != hm && hm.size() > 0){
			Integer lastEntry=hm.lastKey();
			logger.info("Last Entry in list - Category Id :" + lastEntry);
			if(null != projectCatList && projectCatList.size() > 0 ){
				GetLeadProjectTypeResponseVO lastCategoryList=projectCatList.get(projectCatList.size() - 1);
				if(lastEntry != lastCategoryList.getCategoryID())
				{   logger.info("Entry Not Added in list :" + lastCategoryList.getCategoryID()); 
					hm.put(lastCategoryList.getCategoryID(), projectCatList);
				}
			}	
		}else{
			logger.info("Only 1 Category Exists");
			if(null != projectCatList && projectCatList.size() > 0 ){
				GetLeadProjectTypeResponseVO lastCategoryList=projectCatList.get(projectCatList.size() - 1);
				if(null != lastCategoryList.getCategoryID())
				{
					hm.put(lastCategoryList.getCategoryID(), projectCatList);
				}
			}	
		}

		List<GetLeadProjectTypeResponseVO> projectLists = new ArrayList<GetLeadProjectTypeResponseVO>();
		PriceComparator priceComparator = new PriceComparator();
		//TODO : Sort on map
		for (Map.Entry<Integer,List<GetLeadProjectTypeResponseVO>> entry : hm.entrySet())
		{
			logger.info("Key :     = " + entry.getKey());
			logger.info("List Size = " + entry.getValue().size());
			List<GetLeadProjectTypeResponseVO> listOfObj = new ArrayList<GetLeadProjectTypeResponseVO>();
			listOfObj = entry.getValue();
			if(entry.getValue().size() > 1 ){
				Collections.sort(listOfObj,priceComparator);
			}
			projectLists.addAll(listOfObj);
		}
		
		projectDetailsList.setProject(projectLists);
		return projectDetailsList;
	}


	public LeadProfileCreationResponseVO createLeadProfile(
			LeadProfileCreationRequestVO leadProfileCreationRequestVO)
			throws Exception {
		// TODO Auto-generated method stub
		/*
		 * Logic to insert data into the ServiceLive Lead related tables should go here
		 */
		LeadProfileCreationResponseVO leadProfileCreationResponseVO=new LeadProfileCreationResponseVO();
		try{
			// TODO This should be the name of the provider firm or the firm ID
			leadProfileCreationRequestVO.setCreatedBy(leadProfileCreationRequestVO.getProviderFirmId());
			leadProfileCreationRequestVO.setModifiedBy(leadProfileCreationRequestVO.getProviderFirmId());
			insert("insert_lead_registration_data.insert",leadProfileCreationRequestVO);
			
			//Setting lead registration success code once lead data persisted in the Db
			leadProfileCreationResponseVO.setResult(ResultsCode.LEAD_REGISTRATION_SUCCESS.getMessage());
			leadProfileCreationResponseVO.setStatus(ResultsCode.LEAD_REGISTRATION_SUCCESS.getCode());
		}
		catch (Exception ex) {
			logger.info("Error while persisting data into the SL Database for Lead");
			throw new Exception(ex.getMessage(), ex);
		}
		return leadProfileCreationResponseVO;
	}


	public LeadProfileBillingResponseVO createLeadProfileBilling(
			LeadProfileBillingRequestVO leadProfileBillingRequestVO)
			throws Exception {
		// TODO Auto-generated method stub
		/*
		 * Logic to update the vendor_hdr with the t&c indicator & billing account created indicator should go here
		 */
		return null;
	}
	@SuppressWarnings("unchecked")
	public boolean validateProviderFirmLeadEligibility(String providerFirmId) throws DataServiceException 
	{
		return false;
	}
	
	public LeadInsertFilterResponseVO insertFilterSet(LeadProfileCreationRequestVO leadProfileCreationRequestVO,Map<String, List<String>> projectLists)throws DataServiceException{
		LeadInsertFilterResponseVO responseVO = new LeadInsertFilterResponseVO();
		try{
			List<LeadServicePriceVO> priceVO = leadProfileCreationRequestVO.getLeadServicePrice();
			insert("lead_profile.insert_lead_project_type_price_data", priceVO);
			//insert terms&cond ind
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("vendorId",leadProfileCreationRequestVO.getProviderFirmId());
			params.put("tc",leadProfileCreationRequestVO.getTc().toString());
			insert("lead_profile.insert_tc_ind", params);
			//SL-19293 Inserting T&C indicator change in vendor_profile_level_history table.
			String userName = (String) queryForObject("get_username_for_vendor_id.query",Integer.valueOf(leadProfileCreationRequestVO.getProviderFirmId()));
			params.put("actionName", OrderConstants.LEAD_TC_ACTION_NAME);
			params.put("userName", userName);
			insert("lead_profile.insert_tc_history", params);
			//--end--
			responseVO.setResult(ResultsCode.FILTER_CREATION_SUCCESS.getMessage());
			responseVO.setStatus(ResultsCode.FILTER_CREATION_SUCCESS.getCode());
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return responseVO;
	}


	public LeadProfileCreationRequestVO getProfileDetails(String vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public void updateFilterSetId(Integer filterSetId, String partnerId, String filterType,String wfStates)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public boolean updateLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return true;
	}


	public void updatePartnerId(String partnerId, String vendorId,String wfStatus)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public LeadServicePriceVO validateProjectType(String providerFirmId,
			String projectType) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public int doCrmRegistration(String request) throws DataServiceException {
		// TODO Auto-generated method stub
		return 0;
	}


	public String validateLeadPartnerId(String firmId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean validateVendor(String vendorId) throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}


	public LeadServicePriceVO getPriceDetails(LeadProjectVO projectVO) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	public void updateCrmStatus(String crmStatus,Integer providerFirmId)throws DataServiceException
	{

	}


	public void updateLMSError(String vendorId, String errors)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public void updateLMSStatus(String vendorId, String status,
			String statusReason) throws DataServiceException {
		// TODO Auto-generated method stub
		
	}


	public LeadProfileCreationRequestVO getFilterDetails(String providerFirmId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean validateIfFilterPresent(String providerFirmId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	public HashMap<String, Object> getPackagePriceDetails(String packageId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

	public String getVendorIdFromPartnerId(String partnerId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public LeadProfileCreationRequestVO validateFilterPresent(String vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> getLookUpvalues(String commaSeperatedIds, Integer type)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteFilterForVendor(String vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		logger.info("DeleteFilterForVendor = " + vendorId);
		// TODO Auto-generated method stub
		try{
 		 delete("lead_profile.delete_lead_project_type_price_data_for_vendor", vendorId);
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		
		
	}
	
	public void updateVendorStatus(Map<Integer,Integer> vendorIdMap)throws DataServiceException{
		
	}


	public boolean getInsideSalesApiInvocationSwitch()
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}


	public String createInsideSalesProfile(String request,String insideSalesLoginRequestJSON)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public void updateInsideSalesResponse(String isResponse,
			Integer providerFirmId,Integer crmStatus) throws DataServiceException{
	}
	public String getState(String stateCd)
	{
		return null;	
	}

	
	
}
