/**
 * 
 */
package com.servicelive.spn.mapper;

import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_AUTO_LIABILITY_AMT;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_AUTO_LIABILITY_VERIFIED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_CATEGORY;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMMERCIAL_LIABILITY_AMT;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMMERCIAL_LIABILITY_VERIFIED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMPANY_CRED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMPANY_CRED_CATEGORY;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMPANY_SIZE;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_LANGUAGE;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MAIN_SERVICES;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MARKET;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MEETING_REQUIRED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MINIMUM_RATING;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MINIMUM_SO_COMPLETED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_SALES_VOLUME;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_SKILLS;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_SP_CRED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_SP_CRED_CATEGORY;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_STATE;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_SUB_CATEGORY;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_VALUE_TRUE;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_WC_LIABILITY_VERIFIED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_WC_LIABILITY_SELECTED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MINIMUM_RATING_NOTRATED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MARKET_ALL;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_STATE_ALL;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_PRIMARY_INDUSTRY;
//R11.0 SL-19387
//import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_BACKGROUND_CHECK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.ApprovalCriteria;
import com.servicelive.domain.spn.campaign.CampaignApprovalCriteria;
import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.domain.lookup.LookupSPNApprovalCriteria;
import com.servicelive.domain.spn.network.SPNApprovalCriteria;
import com.servicelive.spn.services.LookupService;

/**
 * @author Mahmud Khair
 *
 */
public class ApprovalCriteriaMapper {
	
	private LookupService lookupService;
	private ApprovalModel approvalModel;
	private String userName;
	private Map<String,LookupSPNApprovalCriteria> approvalCriteriasMap;
   
	/**
	 * 
	 * @return List
	 */
	public List<CampaignApprovalCriteria> mapCampaignApprovalModelToApprovalCriterias(){
		List<CampaignApprovalCriteria> campaignApprovalCriterias = mapApprovalModelToApprovalCriterias(CampaignApprovalCriteria.class, userName);
		return campaignApprovalCriterias;
	}
	/**
	 * 
	 * @return List
	 */
	public List<SPNApprovalCriteria> mapSPNApprovalModelToApprovalCriterias(){
		List<SPNApprovalCriteria> sPNApprovalCriterias = mapApprovalModelToApprovalCriterias(SPNApprovalCriteria.class, userName);
		return sPNApprovalCriterias;
	}
	
	
	
	private <T extends ApprovalCriteria> List<T> mapApprovalModelToApprovalCriterias(Class<T> clazz, String pUserName)
	{
		String userName2 = pUserName;
		approvalCriteriasMap = lookupService.getAllApprovalCriteriasMap();
		// for testing purpose need to be removed.
		/*if(null!=approvalCriteriasMap){
			try{
				  System.out.println("Criteria map before:"+approvalCriteriasMap);
				  approvalCriteriasMap.remove("BackgroundCheck");
				  System.out.println("Criteria map after:"+approvalCriteriasMap);
	  		}
			catch(Exception e){
				System.out.println("exceptionbbchk"+e);
			}
		}*/
		//System.out.println("Approval criteria Map value:"+approvalCriteriasMap.get(CRITERIA_BACKGROUND_CHECK));
		List<T> approvalCriterias = new ArrayList<T>();
		approvalCriterias.addAll(getApprovalCriteriaMainServices(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaSub1(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaSub2(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaSkills(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaLanguages(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaMinCompletedServiceOrders(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaVehicleLiability(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaWorkersComp(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaCommercialGenLiability(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaMarkets(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaStates(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaMinimumRatings(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaSalesVolume(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaCompanySize(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaVendorCredTypes(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaVendorCredCategories(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaResCredTypes(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaResCredCategories(clazz, userName2));
		approvalCriterias.addAll(getApprovalCriteriaMeetingRequired(clazz, userName2));
		//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
		//set the selected primary industry values to approvalCriteria list, 
		//to save it to spnet_campaign_invitation_criteria table
		approvalCriterias.addAll(getApprovalCriteriaPrimaryIndustry(clazz, userName2));
		//R11.0 SL-19387
		approvalCriterias.addAll(getApprovalCriteriaBackgroundCheck(clazz, userName2));
		return approvalCriterias;
	}
	//R11.0 SL-19387
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaBackgroundCheck(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		
		Boolean recertificationReq = approvalModel.getRecertificationInd();
				
		if (recertificationReq != null && recertificationReq.booleanValue())
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get("Background check - 2 year recertification"),CRITERIA_VALUE_TRUE);
			acList.add(ac);
		}
		
		return acList;
		
	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaVendorCredTypes(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedVendorCredTypes = approvalModel.getSelectedVendorCredTypes();
		for (Integer selectedVendorCredType : selectedVendorCredTypes)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_COMPANY_CRED), selectedVendorCredType);
			acList.add(ac);
		}
		return acList;
	}

	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaVendorCredCategories(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedVendorCredCategories = approvalModel.getSelectedVendorCredCategories();
		for (Integer selectedVendorCredCategory : selectedVendorCredCategories)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_COMPANY_CRED_CATEGORY), selectedVendorCredCategory);
			acList.add(ac);
		}
		
		return acList;
	}

	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaResCredTypes(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedResCredTypes = approvalModel.getSelectedResCredTypes();
		for (Integer selectedResCredType : selectedResCredTypes)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_SP_CRED), selectedResCredType);
			acList.add(ac);
		}
		
		return acList;
	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaResCredCategories(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedResCredCategories = approvalModel.getSelectedResCredCategories();
		for (Integer selectedResCredCategory : selectedResCredCategories)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_SP_CRED_CATEGORY), selectedResCredCategory);
			acList.add(ac);
		}
		
		return acList;
	}

	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaCompanySize(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		Integer companySizeId = approvalModel.getSelectedCompanySize();
		if(companySizeId!= null && companySizeId.intValue() != -1){
			T approvalCrieteria = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_COMPANY_SIZE), companySizeId);
			acList.add(approvalCrieteria);
		}
		
		return acList;
	}

	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaSalesVolume(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		Integer salesVolumeId = approvalModel.getSelectedSalesVolume();
		if(salesVolumeId!= null && salesVolumeId.intValue() != -1){
			T approvalCrieteria = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_SALES_VOLUME), salesVolumeId);
			acList.add(approvalCrieteria);
		}
		
		return acList;
	}

	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaMinimumRatings(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		if(approvalModel.getIsNotRated()!= null && approvalModel.getIsNotRated().booleanValue()){
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_MINIMUM_RATING_NOTRATED),approvalModel.getIsNotRated());
			acList.add(ac);
		}
		
		
		if(approvalModel.getSelectedMinimumRating() != null && approvalModel.getSelectedMinimumRating().intValue() > 0){
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_MINIMUM_RATING),approvalModel.getSelectedMinimumRating());
			acList.add(ac);
		}
		
		return acList;
	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaMarkets(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedMarkets = approvalModel.getSelectedMarkets();
		if(approvalModel.getIsAllMarketsSelected()!= null && approvalModel.getIsAllMarketsSelected().booleanValue()){
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_MARKET_ALL), approvalModel.getIsAllMarketsSelected());
			acList.add(ac);
		}else{
			for (Integer currentMarket : selectedMarkets){
				T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_MARKET), currentMarket);
				acList.add(ac);
			}
		}
		
		return acList;
	}


	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaStates(Class<T> clazz, String pUserName) {
		List<T> acList = new ArrayList<T>();
		List<String> selectedStates = approvalModel.getSelectedStates();
		if(approvalModel.getIsAllStatesSelected()!= null && approvalModel.getIsAllStatesSelected().booleanValue()){
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_STATE_ALL), approvalModel.getIsAllStatesSelected());
			acList.add(ac);
		}else{
			for (String currentState : selectedStates)
			{
				T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_STATE), currentState);
				acList.add(ac);
			}
		}
		
		
		return acList;
	}

	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaMainServices(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedServices = approvalModel.getSelectedMainServices();
		for (Integer currentService: selectedServices)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_MAIN_SERVICES), currentService);
			acList.add(ac);
		}
		
		return acList;

	}
	/**
	 * R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	 * method gets the selected primary industry values from model,
	 * and sets it to the ApprovalCriteria list for saving to spnet_campaign_invitation_criteria table
	 * @param clazz
	 * @param pUserName
	 * @return acList
	 */
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaPrimaryIndustry(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedServices = approvalModel.getSelectedPrimaryIndustry();
		for (Integer currentService: selectedServices)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_PRIMARY_INDUSTRY), currentService);
			acList.add(ac);
		}
		
		return acList;

	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaSub1(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedSub1 = approvalModel.getSelectedSubServices1();
		for (Integer currentService: selectedSub1)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_CATEGORY), currentService);
			acList.add(ac);
		}
		
		return acList;

	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaSub2(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedSub2 = approvalModel.getSelectedSubServices2();
		for (Integer currentService: selectedSub2)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_SUB_CATEGORY), currentService);
			acList.add(ac);
		}
		
		return acList;

	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaSkills(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedSkills = approvalModel.getSelectedSkills();
		for (Integer currentSkill:selectedSkills)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_SKILLS), currentSkill);
			acList.add(ac);
		}
		
		return acList;

	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaLanguages(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		List<Integer> selectedLanguages = approvalModel.getSelectedLanguages();
		for (Integer currentSkill : selectedLanguages)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_LANGUAGE), currentSkill);
			acList.add(ac);
		}
		
		return acList;
	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaMinCompletedServiceOrders(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		String minCompletedServiceOrders = approvalModel.getMinimumCompletedServiceOrders();
		if (minCompletedServiceOrders != null && minCompletedServiceOrders.length() != 0)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_MINIMUM_SO_COMPLETED), minCompletedServiceOrders);
			acList.add(ac);
		}
		
		return acList;
	}
	
	private String stripCommaForBigDecimal(String amt)
	{
		if (amt != null)
		{
			amt = amt.replaceAll(",", "");
		}
		
		return amt;
	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaVehicleLiability(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		Boolean vehicleLiabilitySelected = approvalModel.getVehicleLiabilitySelected();
		String vehicleLiabilityAmt = approvalModel.getVehicleLiabilityAmt();
		vehicleLiabilityAmt = stripCommaForBigDecimal(vehicleLiabilityAmt);
		Boolean vehicleLiabilityVerified = approvalModel.getVehicleLiabilityVerified();
		
		if (vehicleLiabilitySelected != null && vehicleLiabilitySelected.booleanValue() == true && !"".equals(vehicleLiabilityAmt) && vehicleLiabilityAmt != null)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_AUTO_LIABILITY_AMT), vehicleLiabilityAmt);
			acList.add(ac);
		}
		
		if (vehicleLiabilitySelected != null && vehicleLiabilitySelected.booleanValue() == true && vehicleLiabilityVerified != null && vehicleLiabilityVerified.booleanValue() == true)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_AUTO_LIABILITY_VERIFIED), CRITERIA_VALUE_TRUE);
			acList.add(ac);
		}
		
		return acList;
	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaWorkersComp(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		Boolean workersCompSelected = approvalModel.getWorkersCompensationSelected();
		Boolean workersCompVerified = approvalModel.getWorkersCompensationVerified();
		if(workersCompSelected != null && workersCompSelected.booleanValue() == true) {
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_WC_LIABILITY_SELECTED), CRITERIA_VALUE_TRUE);
			acList.add(ac);
		}
		if (workersCompSelected != null && workersCompSelected.booleanValue() == true && workersCompVerified != null && workersCompVerified.booleanValue() == true)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_WC_LIABILITY_VERIFIED), CRITERIA_VALUE_TRUE);
			acList.add(ac);
		}
		
		return acList;
	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaCommercialGenLiability(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		
		Boolean comGenLibSelected = approvalModel.getCommercialGeneralLiabilitySelected();
		String comGenLibAmt = approvalModel.getCommercialGeneralLiabilityAmt();
		comGenLibAmt = stripCommaForBigDecimal(comGenLibAmt);
		Boolean comGenLibVerified = approvalModel.getCommercialGeneralLiabilityVerified();
		
		if (comGenLibSelected != null && comGenLibSelected.booleanValue() == true && !"".equals(comGenLibAmt) && comGenLibAmt != null)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_COMMERCIAL_LIABILITY_AMT), comGenLibAmt);
			acList.add(ac);
		}
		
		if (comGenLibSelected != null && comGenLibSelected.booleanValue() == true && comGenLibVerified != null && comGenLibVerified.booleanValue() == true)
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_COMMERCIAL_LIABILITY_VERIFIED),CRITERIA_VALUE_TRUE);
			acList.add(ac);
		}
		
		return acList;
	}
	
	private <T extends ApprovalCriteria> List<T> getApprovalCriteriaMeetingRequired(Class<T> clazz, String pUserName)
	{
		List<T> acList = new ArrayList<T>();
		
		Boolean meetingReq = approvalModel.getMeetingRequired();
				
		if (meetingReq != null && meetingReq.booleanValue())
		{
			T ac = create(clazz, pUserName, approvalCriteriasMap.get(CRITERIA_MEETING_REQUIRED),CRITERIA_VALUE_TRUE);
			acList.add(ac);
		}
		
		return acList;
	}
	
	private <T extends ApprovalCriteria> T create(Class<T> clazz, String pUserName, LookupSPNApprovalCriteria criteria, Object value) {
		try {
			T ac = clazz.newInstance();
			//System.out.println("Background check id value"+criteria.getId());
			//System.out.println("Background check descr value"+criteria.getDescription());
			ac.setCriteriaId(criteria);
			
			if(value != null) {
				ac.setValue(value.toString());
			}
			ac.setModifiedBy(pUserName);
			return ac; 
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}

	/**
	 * @param lookupService the lookupService to set
	 */
	public void setLookupService(LookupService lookupService) {
		this.lookupService = lookupService;
	}

	/**
	 * @return the approvalModel
	 */
	public ApprovalModel getApprovalModel() {
		return approvalModel;
	}

	/**
	 * @param approvalModel the approvalModel to set
	 */
	public void setApprovalModel(ApprovalModel approvalModel) {
		this.approvalModel = approvalModel;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
