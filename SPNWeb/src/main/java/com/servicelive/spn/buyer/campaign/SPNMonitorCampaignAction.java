package com.servicelive.spn.buyer.campaign;

import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_AUTO_LIABILITY_AMT;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_AUTO_LIABILITY_VERIFIED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMMERCIAL_LIABILITY_AMT;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMMERCIAL_LIABILITY_VERIFIED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMPANY_CRED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMPANY_CRED_CATEGORY;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_COMPANY_SIZE;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_LANGUAGE;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MARKET;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MARKET_ALL;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MEETING_REQUIRED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MINIMUM_RATING;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MINIMUM_RATING_NOTRATED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_MINIMUM_SO_COMPLETED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_SALES_VOLUME;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_SP_CRED;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_SP_CRED_CATEGORY;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_STATE;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_STATE_ALL;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_WC_LIABILITY_VERIFIED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_ENTITY_CAMPAIGN;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_CAMPAIGN_APPROVED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_CAMPAIGN_INACTIVE;
import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_PRIMARY_INDUSTRY;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN_AS_BUYER;
import static com.servicelive.spn.constants.SPNActionConstants.ROLE_ID_PROVIDER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.lookup.LookupCompanySize;
import com.servicelive.domain.lookup.LookupLanguage;
import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.domain.lookup.LookupPrimaryIndustry;
import com.servicelive.domain.lookup.LookupResourceCredentialCategory;
import com.servicelive.domain.lookup.LookupResourceCredentialType;
import com.servicelive.domain.lookup.LookupSPNApprovalCriteria;
import com.servicelive.domain.lookup.LookupSalesVolume;
import com.servicelive.domain.lookup.LookupVendorCredentialCategory;
import com.servicelive.domain.lookup.LookupVendorCredentialType;
import com.servicelive.domain.spn.campaign.CampaignApprovalCriteria;
import com.servicelive.domain.spn.campaign.CampaignHeader;
import com.servicelive.domain.spn.campaign.CampaignProviderFirm;
import com.servicelive.domain.spn.detached.CampaignMonitorRowVO;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.buyer.common.SPNCreateAction;
import com.servicelive.spn.buyer.common.SPNLookupVO;
import com.servicelive.spn.common.detached.CampaignHistoryVO;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.campaign.CampaignSummaryServices;
import com.servicelive.spn.services.campaign.CreateCampaignServices;
import com.servicelive.spn.services.network.CreateNetworkServices;
import com.servicelive.spn.services.workflow.WorkFlowPersistService;
/**
 * 
 *
 *
 */
public class SPNMonitorCampaignAction extends SPNCreateAction implements ModelDriven<SPNMonitorCampaignModel>
{

	private static final long serialVersionUID = -8747224232309485329L;
	private SPNMonitorCampaignModel model = new SPNMonitorCampaignModel();
	
	private Integer SPN_MONITOR_MAX_ROWS_DEFAULT = Integer.valueOf(30);
	
	private Integer campaignCountLimit = null;
	private Integer campaignCountAll;
	
	private Integer activeCount;
	private Integer inactiveCount;
	private Integer pendingCount;
	private Boolean showDisplayOptions = Boolean.FALSE;
	private String viewString;
	private Integer showCount;
	
		
	@Override
	public String display()
	{
		logger.info("inside display");
		if(campaignSummaryServices != null)
		{
			String buyerIdStr = getRequest().getParameter("buyerId");
			Integer buyerIdInt = null;
			
			if(buyerIdStr != null)
			{
				buyerIdInt = Integer.valueOf(buyerIdStr);
			}
			else
			{
				buyerIdInt = getCurrentBuyerId();
			}
			
			
			if(buyerIdInt != null)
			{
				List<CampaignMonitorRowVO> list;
				try {
					Integer count = campaignSummaryServices.getCampaignCount(buyerIdInt, Boolean.FALSE);
					setCampaignCountAll(count);
					if(count.intValue() > SPN_MONITOR_MAX_ROWS_DEFAULT.intValue())
					{
						setCampaignCountLimit(SPN_MONITOR_MAX_ROWS_DEFAULT);
						setShowDisplayOptions(Boolean.TRUE);
					}					
					else
					{
						setCampaignCountLimit(null);
						setShowDisplayOptions(Boolean.FALSE);
					}
					
					setViewString("View All");
					
					setActiveCount(campaignSummaryServices.getActiveCampaignCount(buyerIdInt, Boolean.FALSE));
					setInactiveCount(campaignSummaryServices.getInactiveCampaignCount(buyerIdInt, Boolean.FALSE));
					setPendingCount(campaignSummaryServices.getPendingCampaignCount(buyerIdInt, Boolean.FALSE));
					
					setShowCount(SPN_MONITOR_MAX_ROWS_DEFAULT);
					
					list = campaignSummaryServices.getCampaignMonitorRows(buyerIdInt, getCampaignCountLimit());
					if(null != list){
						for(CampaignMonitorRowVO c : list){
							if(c.getCampaignId().intValue()==2561){
								logger.info("status of campaign="+c.getWfStatus());
							}
						}
					}
				} catch (Exception e) {
					logger.error("Error getting campaing information.", e);
					return ERROR;
				}
				getModel().setCampaignList(list);								
			}
			else
			{
				logger.info("SPNMonitorCampaignAction() - display() 'buyerId' is not available");				
			}			
		}
		
		initTabDisplay();
		initLookups();
		return SUCCESS;
	}
	
	private void initLookups()
	{
		SPNLookupVO lookupsVO = new SPNLookupVO();
		lookupsVO.setAllMarkets(lookupService.getAllMarkets());
		lookupsVO.setAllStates(lookupService.findStatesNotBlackedOut());
		getModel().setLookupsVO(lookupsVO);
		/*try
		{
			// So far we only have logic for the 'Release Tiers' tab
			boolean showTab = doesBuyerHaveAtLeastOneNetwork();
			getRequest().setAttribute("showReleaseTiersTab", Boolean.valueOf(showTab));
		}
		catch(Exception e)
		{
			logger.error(e);
		}*/
	}
	
	public String viewCampaignTableAjax() throws Exception
	{
		String buyerIdStr = getRequest().getParameter("buyerId");
		Integer buyerIdInt = null;
		
		if(buyerIdStr != null)
		{
			buyerIdInt = Integer.valueOf(buyerIdStr);
		}
		else
		{
			buyerIdInt = getCurrentBuyerId();
		}
		
		Integer count = campaignSummaryServices.getCampaignCount(buyerIdInt, Boolean.FALSE);
		setCampaignCountAll(count);
		if(count.intValue() > SPN_MONITOR_MAX_ROWS_DEFAULT.intValue())
		{
			setShowDisplayOptions(Boolean.TRUE);
		}
		else
		{
			setShowDisplayOptions(Boolean.FALSE);
		}
		
		
		
		String limitRows = getRequest().getParameter("limitRows");
		if(limitRows != null)
		{
			setCampaignCountLimit(SPN_MONITOR_MAX_ROWS_DEFAULT);
			setViewString("View All");
			setShowCount(SPN_MONITOR_MAX_ROWS_DEFAULT);
		}
		else
		{
			setCampaignCountLimit(null);
			setViewString("View 30");
			setShowCount(count);
		}
		
		setActiveCount(campaignSummaryServices.getActiveCampaignCount(buyerIdInt, Boolean.FALSE));
		setInactiveCount(campaignSummaryServices.getInactiveCampaignCount(buyerIdInt, Boolean.FALSE));
		setPendingCount(campaignSummaryServices.getPendingCampaignCount(buyerIdInt, Boolean.FALSE));
		
		
		if(buyerIdInt != null)
		{
			List<CampaignMonitorRowVO> list;
			try {
				list = campaignSummaryServices.getCampaignMonitorRows(buyerIdInt, getCampaignCountLimit());
			} catch (Exception e) {
				logger.error("Error getting campaing information.", e);
				return ERROR;
			}
			getModel().setCampaignList(list);								
		}
		else
		{
			logger.info("SPNMonitorCampaignAction() - display() 'buyerId' is not available");				
		}			
		
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String approveCampaign() throws Exception {
		logger.info("inside approveCampaign");
		int campaignId = model.getCampaignId().intValue();
		String comments = model.getApproveComments();
		
		spnWorkflowSerivce.signal(WF_ENTITY_CAMPAIGN,WF_STATUS_CAMPAIGN_APPROVED,Integer.valueOf(campaignId),comments,getCurrentBuyerResourceUserName());
		this.display();
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String stopCampaign() throws Exception {
		int campaignId = getCampaignIdParameter();
		spnWorkflowSerivce.signal(WF_ENTITY_CAMPAIGN,WF_STATUS_CAMPAIGN_INACTIVE,Integer.valueOf(campaignId),getCurrentBuyerResourceUserName());
		this.display();
		return SUCCESS;
	}
	/**
	 * 
	 * @return String
	 * @throws Exception 
	 */
	public String displayTabCampaignDetailsAjax() throws Exception
	{
		int campaignId = getCampaignIdParameter();
		CampaignHeader campaignHeader = null;
		String action = "criteria";
		try
		{
			campaignHeader = createCampaignServices.getCampaign(Integer.valueOf(campaignId));
			
			// First pass at trying to get Campaign Data to the front end page.
			if(campaignHeader != null)
			{
				List<CampaignApprovalCriteria> list = campaignHeader.getApprovalCriterias();
				
				// Campaign by Criteria
				if(list != null && list.size() > 0)
				{
					Map<String,LookupSPNApprovalCriteria> approvalCriteriasMap = lookupService.getAllApprovalCriteriasMap();
					setCredentials(list, approvalCriteriasMap);
					setFirmCredentials(list, approvalCriteriasMap);
					setMarkets(list, approvalCriteriasMap);
					setStates(list, approvalCriteriasMap);
					setMeetingRequired(list, approvalCriteriasMap);
					setMinimumRatings(list, approvalCriteriasMap);
					setMinimumServiceOrders(list, approvalCriteriasMap);				
					setLanguages(list, approvalCriteriasMap);
					
					setAutoLiability(list, approvalCriteriasMap);
					setCommercialGeneralLiability(list, approvalCriteriasMap);
					setWorkersCompensation(list, approvalCriteriasMap);
					
					setCompanySize(list, approvalCriteriasMap);
					setSalesVolumn(list,approvalCriteriasMap);
					setCampaignCreatedBy(campaignHeader.getModifiedBy());
					//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
					//set the primary industry value for view mode.
					setPrimaryIndustry(list, approvalCriteriasMap);
					action = "criteria";
				}
				// Campaign by Providers
				else
				{
					setInvitedProviderFirms(campaignHeader);					
					action = "providers";
				}
			}
			
			getRequest().setAttribute("campaignHeader", campaignHeader);
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
		
		if(campaignHeader != null)
		{
			getRequest().setAttribute("campaignHeader", campaignHeader);
		}
		
		return action;
	}
	
	
	private void setInvitedProviderFirms(CampaignHeader campaignHeader)
	{
		List<LabelValueBean> providerList = new ArrayList<LabelValueBean>();
		
		for(CampaignProviderFirm cpf : campaignHeader.getProviderFirms())
		{
			LabelValueBean providerDTO = new LabelValueBean(cpf.getProviderFirm().getBusinessName(), cpf.getProviderFirm().getId() + "");
			providerList.add(providerDTO);
		}
		
		
		getRequest().setAttribute("providerList", providerList);
	}
	
	private void setCampaignCreatedBy(String modifiedBy) {
		 //String displayString = "";
		 
		 getRequest().setAttribute("createdBy", modifiedBy);
		
	}
	private void setCompanySize(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap) {
		String displayString = "";
		
		LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_COMPANY_SIZE);
		List<LookupCompanySize> allCompanySize = lookupService.getAllCompanySizes();
		for(CampaignApprovalCriteria criteria : list)
		{					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
			{
				Integer companySizeId = new Integer(criteria.getValue());
				String companySizeDescr = getCompanySizeDescription(companySizeId, allCompanySize); 
				
				if(displayString == "")
					displayString += companySizeDescr;
				else
					displayString += ", " + companySizeDescr;
			}
			
		}
		getRequest().setAttribute("companySize", displayString);	
		
	}
	
	private String getCompanySizeDescription(Integer companySizeId,
			List<LookupCompanySize> allCompanySize) {
		String compSizeDescr = "";
		
		for(LookupCompanySize compSize : allCompanySize ){
			if(compSize.getId().intValue() == companySizeId.intValue()){
				compSizeDescr = compSize.getDescription();
			}
		}
		return compSizeDescr;
		
	}
	private void setSalesVolumn(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap) {
		String displayString = "";
		
		LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_SALES_VOLUME);
		List<LookupSalesVolume> allSalesVolumn = lookupService.getAllSalesVolumes();
		for(CampaignApprovalCriteria criteria : list)
		{					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
			{
				Integer salesVolId = new Integer(criteria.getValue());
				String salesVolumnDescr = getSalesVolDescription(salesVolId, allSalesVolumn); 
				
				if(displayString == "")
					displayString += salesVolumnDescr;
				else
					displayString += ", " + salesVolumnDescr;
			}
			
		}
		getRequest().setAttribute("salesVolumn", displayString);	
		
	}
	
	
	private String getSalesVolDescription(Integer salesVolId,
			List<LookupSalesVolume> allSalesVolumn) {
		String salesVolDescr = "";
		
		for(LookupSalesVolume salesVol : allSalesVolumn ){
			if(salesVol.getId().intValue() == salesVolId.intValue()){
				salesVolDescr = salesVol.getDescription();
			}
		}
		return salesVolDescr;
	}

	private void setCredentials(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap) {
		String displayString = "";
		
		LookupSPNApprovalCriteria approvalCriteriaCat = approvalCriteriaMap.get(CRITERIA_SP_CRED_CATEGORY);
		List<LookupResourceCredentialCategory> credentialList = lookupService.getAllResourceCredentialCategories();
		
		
		for(CampaignApprovalCriteria criteria : list){					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteriaCat)
			{
				Integer categoryId = new Integer(criteria.getValue());
				String credentialDescr = getCredentialDescription(categoryId, credentialList);
				
				if(displayString.equals(""))
					displayString += credentialDescr;
				else
					displayString += ", " + credentialDescr;
			}
			
		}
		
		String credWithNochildStr = getCredDescWithNoChildSelected(list, approvalCriteriaMap, credentialList);
		if(credWithNochildStr.length()>0){
			displayString += ", " + credWithNochildStr;
		}
		
		getRequest().setAttribute("credentials", displayString);	
		
	}
	
	private String getCredDescWithNoChildSelected(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap,
			List<LookupResourceCredentialCategory> allCredentialList){
		String displayString = "";
		LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_SP_CRED);
		List<LookupResourceCredentialType> allParentCredList = lookupService.getAllResourceCredentialTypes();
		for(CampaignApprovalCriteria criteria : list)
		{					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
			{
				Integer categoryId = new Integer(criteria.getValue());
				boolean childCredNotSelected = isChildCredSelected(categoryId,list,allCredentialList,approvalCriteriaMap);
				if (!childCredNotSelected){
					String credentialDescr = getDescForCategory(categoryId,allParentCredList);
					
					if(displayString == "")
						displayString += credentialDescr;
					else
						displayString += ", " + credentialDescr;
					
				}
				
			}
			
		}
				
		return displayString;
		
	}
	
	private String getDescForCategory(Integer categoryId,
			List<LookupResourceCredentialType> allParentCredList) {
		String credentialDescr = "";
				
		for(LookupResourceCredentialType cred : allParentCredList ){
			if(cred.getId().intValue() == categoryId.intValue()){
				credentialDescr = cred.getDescription();
				
			}
		}
		return credentialDescr;
		
	}
	
	private boolean isChildCredSelected(Integer categoryId,
			List<CampaignApprovalCriteria> list, List<LookupResourceCredentialCategory> allCredentialList, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap) {
		boolean isChildCredSelected = false;
		LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_SP_CRED_CATEGORY);
		Integer selectedCred = null;
		Integer parentCred = null;
		
		for(CampaignApprovalCriteria criteria : list){					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria) {
				selectedCred = new Integer(criteria.getValue());
				for(LookupResourceCredentialCategory cred : allCredentialList ){
					if(cred.getId().intValue() == selectedCred.intValue()){
						parentCred = cred.getCredentialCategory().getId();
						if(parentCred.intValue() == categoryId.intValue()){
							isChildCredSelected = true;
							return isChildCredSelected;
						}
						
						
					}
				}
				
			}
		}
		return isChildCredSelected;
	}
	private String getCredentialDescription(Integer marketId,
			List<LookupResourceCredentialCategory> credentialList) {
		
		String marketDescr = "";
		String parentDescr = ""; 
		
		for(LookupResourceCredentialCategory market : credentialList ){
			if(market.getId().intValue() == marketId.intValue()){
				marketDescr = market.getDescription();
				parentDescr = market.getCredentialCategory().getDescription();
				marketDescr = parentDescr + " >" +marketDescr + " " ;
			}
		}
		return marketDescr;
		
	}
	private void setFirmCredentials(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap) {
		String displayString = "";
		
		LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_COMPANY_CRED_CATEGORY);
		List<LookupVendorCredentialCategory> credentialList = lookupService.getAllVendorCredentialCategories();
		
		for(CampaignApprovalCriteria criteria : list)
		{					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
			{
				Integer categoryId = new Integer(criteria.getValue());
				String credentialDescr = getFirmCredentialDescription(categoryId, credentialList);
				
				if(displayString == "")
					displayString += credentialDescr;
				else
					displayString += ", " + credentialDescr;
			}
			
		}
		String credWithNochildStr = getFirmCredDescWithNoChildSelected(list, approvalCriteriaMap, credentialList);
		if(credWithNochildStr.length()>0){
			displayString += ", " + credWithNochildStr;
		}
		
		getRequest().setAttribute("firmCredentials", displayString);	
		
	}

	private String getFirmCredDescWithNoChildSelected(
			List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap, 
			List<LookupVendorCredentialCategory> allCredentialList) {

		String displayString = "";
		LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_COMPANY_CRED);//lookupService.getApprovalCriteriaForDescription(CRITERIA_COMPANY_CRED);
		List<LookupVendorCredentialType> allParentCredList = lookupService.getAllVendorCredentialTypes();
		for(CampaignApprovalCriteria criteria : list)
		{					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
			{
				Integer categoryId = new Integer(criteria.getValue());
				boolean childCredNotSelected = isChildVendorCredSelected(categoryId,list,allCredentialList,approvalCriteriaMap);
				if (!childCredNotSelected){
					String credentialDescr = getDescForVendorCategory(categoryId,allParentCredList);
					
					if(displayString == "")
						displayString += credentialDescr;
					else
						displayString += ", " + credentialDescr;
					
				}
				
			}
			
		}
				
		return displayString;

	}
	
	private String getDescForVendorCategory(Integer categoryId,
			List<LookupVendorCredentialType> allParentCredList) {

		String credentialDescr = "";
		for(LookupVendorCredentialType cred : allParentCredList ){
			if(cred.getId().intValue() == categoryId.intValue()){
				credentialDescr = cred.getDescription();
			}
		}
		return credentialDescr;
	
	}
	
	private boolean isChildVendorCredSelected(Integer categoryId,
			List<CampaignApprovalCriteria> list,
			List<LookupVendorCredentialCategory> allCredentialList, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap) {
		
		boolean isChildCredSelected = false;
		LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_COMPANY_CRED_CATEGORY);
		Integer selectedCred = null;
		Integer parentCred = null;
		
		for(CampaignApprovalCriteria criteria : list){					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria) {
				selectedCred = new Integer(criteria.getValue());
				for(LookupVendorCredentialCategory cred : allCredentialList ){
					if(cred.getId().intValue() == selectedCred.intValue()){
						parentCred = cred.getCredentialCategory().getId();
						if(parentCred.intValue() == categoryId.intValue()){
							isChildCredSelected = true;
							return isChildCredSelected;
						}
					}
				}
			}
		}
		return isChildCredSelected;
	}
	
	private String getFirmCredentialDescription(Integer credentialId,
			List<LookupVendorCredentialCategory> credentialList) {
		
		String categoryDescr = "";
		String parentDescr = ""; 
		
		for(LookupVendorCredentialCategory vendorCredential : credentialList ){
			if(vendorCredential.getId().intValue() == credentialId.intValue()){
				categoryDescr = vendorCredential.getDescription();
				parentDescr = vendorCredential.getCredentialCategory().getDescription();
				categoryDescr = parentDescr + " >" + categoryDescr + " " ;
			}
		}
		return categoryDescr;
		
	}
	
	private void setStates(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
		String displayString = "";
		
		LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_STATE);

		boolean foundAll=false;
		LookupSPNApprovalCriteria allStatesCriteria = approvalCriteriaMap.get(CRITERIA_STATE_ALL);
		for(CampaignApprovalCriteria criteria : list)
		{					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == allStatesCriteria)
			{
				foundAll = true;
				break;
			}
		}		
		if(foundAll)
		{
			displayString = "All States";
		}
		else
		{
			for(CampaignApprovalCriteria criteria : list)
			{					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
				{
					if(displayString == "")
						displayString += criteria.getValue();
					else
						displayString += ", " + criteria.getValue();
				}				
			}
		}
		getRequest().setAttribute("states", displayString);		
	}

	private void setMarkets(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
		String displayString = "";
		
		boolean foundAllMarketsSelected=false;
		LookupSPNApprovalCriteria allStatesCriteria = approvalCriteriaMap.get(CRITERIA_MARKET_ALL);
		for(CampaignApprovalCriteria criteria : list)
		{					
			if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == allStatesCriteria)
			{
				foundAllMarketsSelected = true;
				break;
			}
		}		
		if(foundAllMarketsSelected)
		{
			displayString = "All Markets";
		}
		else
		{
			LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_MARKET);
			List<LookupMarket> allMarkets = lookupService.getAllMarkets();
			for(CampaignApprovalCriteria criteria : list)
			{					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
				{
					Integer marketId = new Integer(criteria.getValue());
					String marketDescr = getMarketsDescription(marketId, allMarkets); 
					if(displayString == "")
						displayString += marketDescr;
					else
						displayString += ", " + marketDescr;
				}
				
			}
		}
		getRequest().setAttribute("markets", displayString);
		
	}
	
	private String getMarketsDescription(Integer marketId,
			List<LookupMarket> allMarkets) {
		
		String marketDescr = "";
		for(LookupMarket market : allMarkets ){
			if(market.getId().intValue() == marketId.intValue()){
				marketDescr = market.getDescription();
			}
		}
		return marketDescr;
	}
	/**
	 * 
	 * @param list
	 * @param approvalCriteriaMap 
	 */
	public void setMinimumRatings(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
			LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_MINIMUM_RATING);
			LookupSPNApprovalCriteria approvalCriteriaNotRated = approvalCriteriaMap.get(CRITERIA_MINIMUM_RATING_NOTRATED);
			String displayString = "N/A";
			String notRatedMinRating = "";
			for(CampaignApprovalCriteria criteria : list){					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria){
					Integer minimumStars = new Integer(criteria.getValue());
					if(minimumStars.intValue() > 0)	{
						displayString = "minimum " + minimumStars + " stars";
						break;
					}
				}
			}
			
			for(CampaignApprovalCriteria criteria : list) {					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteriaNotRated){
					notRatedMinRating = "Not Rated";
					if(!"N/A".equals(displayString)){
						displayString = displayString + ", " + notRatedMinRating;
					}else{
						displayString = notRatedMinRating;
					}
					break;
				}
		
			}

			getRequest().setAttribute("minimumRating", displayString);
	}
	
/*	private String getMinimumRatingDescription(Integer minRatingId,
			List<LookupMinimumRating> allMinimunRatings) {
		
		String minRatingDescr = "";
		
		for(LookupMinimumRating minRating : allMinimunRatings ){
			if(minRating.getId().intValue() == minRatingId.intValue()){
				minRatingDescr = minRating.getDescription();
			}
		}
		return minRatingDescr;
	}*/
	/**
	 * 
	 * @param list
	 * @param approvalCriteriaMap 
	 */
	public void setMinimumServiceOrders(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
			LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_MINIMUM_SO_COMPLETED);
			
			for(CampaignApprovalCriteria criteria : list)
			{					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
				{
					getRequest().setAttribute("minimumServiceOrdersCompleted", criteria.getValue());
					return;
				}
			}		
	}
	/**
	 * R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	 * method sets the primary industry value to be displayed in view mode
	 * If more than one is there, then split it with comma
	 * if none are there, then show the string "None selected"
	 * This is cloned from the existing functionality setLanguages()
	 * @param CampaignApprovalCriteria list
	 * @param approvalCriteriaMap 
	 */
	public void setPrimaryIndustry(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
			LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_PRIMARY_INDUSTRY);
			String displayString = "";
			List<LookupPrimaryIndustry> allPrimaryIndustry = lookupService.getPrimaryIndustry();
			for(CampaignApprovalCriteria criteria : list)
			{					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
				{
					Integer primaryIndustryId = new Integer(criteria.getValue());
					String primaryIndustryDesc = getPrimaryIndustryDescription(primaryIndustryId, allPrimaryIndustry);
					if(displayString.equals(""))
						displayString += primaryIndustryDesc;
					else
						displayString += ", " + primaryIndustryDesc;
				}
			}
			if(StringUtils.isEmpty(displayString)){
				displayString = "None Selected";
			}
			getRequest().setAttribute("primaryIndustry", displayString);			
	}

	/**
	 * R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	 * Method finds the description of the associated primary industries.
	 * This is cloned from the existing functionality getLanguageDescription()
	 * @param primaryIndustryId
	 * @param allPrimaryIndustry
	 * @return
	 */
	private String getPrimaryIndustryDescription(Integer primaryIndustryId,
			List<LookupPrimaryIndustry> allPrimaryIndustry) {
		String primaryIndustryDescr = "";
		
		for(LookupPrimaryIndustry primaryIndustry : allPrimaryIndustry ){
			if(primaryIndustry.getId().intValue() == primaryIndustryId.intValue()){
				primaryIndustryDescr = primaryIndustry.getDescription();
			}
		}
		return primaryIndustryDescr;
	}
	
	/**
	 * 
	 * @param list
	 * @param approvalCriteriaMap 
	 */
	public void setLanguages(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
			LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_LANGUAGE);
			String displayString = "";
			List<LookupLanguage> allLanguages = lookupService.getLanguages();
			for(CampaignApprovalCriteria criteria : list)
			{					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
				{
					Integer langId = new Integer(criteria.getValue());
					String langDesc = getLanguageDescription(langId, allLanguages);
					if(displayString.equals(""))
						displayString += langDesc;
					else
						displayString += ", " + langDesc;
				}
			}
			getRequest().setAttribute("languages", displayString);			
	}
	
	private String getLanguageDescription(Integer langId,
			List<LookupLanguage> allLanguages) {
		String languageDescr = "";
		
		for(LookupLanguage lang : allLanguages ){
			if(lang.getId().intValue() == langId.intValue()){
				languageDescr = lang.getDescription();
			}
		}
		return languageDescr;
	}
	/**
	 * 
	 * @param list
	 * @param approvalCriteriaMap 
	 */
	public void setMeetingRequired(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
			LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_MEETING_REQUIRED);
			
			for(CampaignApprovalCriteria criteria : list)
			{					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
				{
					getRequest().setAttribute("meetingRequired", "Required");
					return;
				}
			}
			
			getRequest().setAttribute("meetingRequired", "Not Required");			
	}
	/**
	 * 
	 * @param list
	 * @param approvalCriteriaMap 
	 */
	public void setAutoLiability(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
			LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_AUTO_LIABILITY_AMT);
			boolean found=false;
			
			for(CampaignApprovalCriteria criteria : list)
			{					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
				{
					getRequest().setAttribute("autoLiabilitySelected", Boolean.TRUE);
					getRequest().setAttribute("autoLiabilityAmount", criteria.getValue());
					found = true;
					break;
				}
			}
			
			if(found)
			{
				approvalCriteria = approvalCriteriaMap.get(CRITERIA_AUTO_LIABILITY_VERIFIED);
				for(CampaignApprovalCriteria criteria : list)
				{					
					if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
					{
						getRequest().setAttribute("autoLiabilityVerified", Boolean.TRUE);						
					}
				}
			}
	}
	
	/**
	 * 
	 * @param list
	 * @param approvalCriteriaMap 
	 */
	public void setCommercialGeneralLiability(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
			LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_COMMERCIAL_LIABILITY_AMT);
			boolean found=false;
			
			for(CampaignApprovalCriteria criteria : list)
			{					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
				{
					getRequest().setAttribute("generalLiabilitySelected", Boolean.TRUE);
					getRequest().setAttribute("generalLiabilityAmount", criteria.getValue());
					found = true;
					break;
				}
			}
			
			if(found)
			{
				approvalCriteria = approvalCriteriaMap.get(CRITERIA_COMMERCIAL_LIABILITY_VERIFIED);
				for(CampaignApprovalCriteria criteria : list)
				{					
					if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
					{
						getRequest().setAttribute("generalLiabilityVerified", Boolean.TRUE);						
					}
				}
			}
	}
	
	/**
	 * There is some missing criteria types in my opinion.  Need additional flag. And possibly WC amount
	 * @param list
	 * @param approvalCriteriaMap 
	 */
	public void setWorkersCompensation(List<CampaignApprovalCriteria> list, Map<String,LookupSPNApprovalCriteria> approvalCriteriaMap)
	{
			LookupSPNApprovalCriteria approvalCriteria = approvalCriteriaMap.get(CRITERIA_WC_LIABILITY_VERIFIED);
			
			for(CampaignApprovalCriteria criteria : list)
			{					
				if(criteria.getCriteriaId() != null && criteria.getCriteriaId() == approvalCriteria)
				{
					getRequest().setAttribute("workersCompensation", Boolean.TRUE);
					break;
				}
			}
	}
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String displayTabCampaignHistoryAjax() throws Exception
	{
		int campaignId = getCampaignIdParameter();
		List<CampaignHistoryVO> campaignHistoryRows = campaignSummaryServices.getCampaignHistory(Integer.valueOf(campaignId));
				
		getRequest().setAttribute("campaignHistoryRows", campaignHistoryRows);
		
		return SUCCESS;
	}
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String displayTabNetworkDetailsAjax() throws Exception
	{
		int networkId = getNetworkIdParameter();
		//R11.0 SL_19387 Setting the background check feature set
		User loggedInUser = getLoggedInUser();
		
		if (loggedInUser == null)
		{
			return NOT_LOGGED_IN;
		}
		else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
		{
			return NOT_LOGGED_IN_AS_BUYER;
		}
		Integer buyerId = loggedInUser.getUserId();
		if(buyerId != null){
			model.setRecertificationBuyerFeatureInd(createSPNServices.getBuyerFeatureSet(buyerId,"BACKGROUND_CHECK_RECERTIFICATION"));
		}
		return super.displayTabNetworkDetailsAjax(networkId);
	}

	private int getCampaignIdParameter()
	{
		String param = getRequest().getParameter("campaignId");
		if(param == null)
			return -1;
		
		if(param == "")
			return -1;
		
		int campaignId = -1;
		try
		{
			campaignId = Integer.parseInt(param);
		}
		catch(Exception e)
		{
			return -1;
		}
		
		return campaignId;
	}

	private int getNetworkIdParameter()
	{
		String param = getRequest().getParameter("networkId");
		if(param == null)
			return -1;
		
		if(param == "")
			return -1;
		
		int campaignId = -1;
		try
		{
			campaignId = Integer.parseInt(param);
		}
		catch(Exception e)
		{
			return -1;
		}
		
		return campaignId;
	}
	
	
	
	@Override
	public void prepare() throws Exception
	{
		// do nothing
	}

	public SPNMonitorCampaignModel getModel()
	{
		return model;
	}

	@Override
	public CampaignSummaryServices getCampaignSummaryServices()
	{
		return campaignSummaryServices;
	}

	@Override
	public void setCampaignSummaryServices(CampaignSummaryServices campaignSummaryServices)
	{
		this.campaignSummaryServices = campaignSummaryServices;
	}

	@Override
	public CreateNetworkServices getCreateSPNServices()
	{
		return createSPNServices;
	}

	@Override
	public void setCreateSPNServices(CreateNetworkServices createSPNServices)
	{
		this.createSPNServices = createSPNServices;
	}

	@Override
	public CreateCampaignServices getCreateCampaignServices()
	{
		return createCampaignServices;
	}

	@Override
	public void setCreateCampaignServices(CreateCampaignServices createCampaignServices)
	{
		this.createCampaignServices = createCampaignServices;
	}

	@Override
	public LookupService getLookupService()
	{
		return lookupService;
	}

	@Override
	public void setLookupService(LookupService lookupService)
	{
		this.lookupService = lookupService;
	}

	/**
	 * @param spnWorkflowSerivce the spnWorkflowSerivce to set
	 */
	@Override
	public void setSpnWorkflowSerivce(WorkFlowPersistService spnWorkflowSerivce) {
		this.spnWorkflowSerivce = spnWorkflowSerivce;
	}


	public Integer getCampaignCountLimit()
	{
		return campaignCountLimit;
	}


	public void setCampaignCountLimit(Integer campaignCountLimit)
	{
		this.campaignCountLimit = campaignCountLimit;
	}


	public Integer getCampaignCountAll()
	{
		return campaignCountAll;
	}


	public void setCampaignCountAll(Integer campaignCountAll)
	{
		this.campaignCountAll = campaignCountAll;
	}


	public Integer getActiveCount()
	{
		return activeCount;
	}


	public void setActiveCount(Integer activeCount)
	{
		this.activeCount = activeCount;
	}


	public Integer getInactiveCount()
	{
		return inactiveCount;
	}


	public void setInactiveCount(Integer inactiveCount)
	{
		this.inactiveCount = inactiveCount;
	}


	public Boolean getShowDisplayOptions() {
		return showDisplayOptions;
	}


	public void setShowDisplayOptions(Boolean showDisplayOptions) {
		this.showDisplayOptions = showDisplayOptions;
	}


	public String getViewString() {
		return viewString;
	}


	public void setViewString(String viewString) {
		this.viewString = viewString;
	}


	public Integer getShowCount() {
		return showCount;
	}


	public void setShowCount(Integer showCount) {
		this.showCount = showCount;
	}


	public Integer getPendingCount() {
		return pendingCount;
	}


	public void setPendingCount(Integer pendingCount) {
		this.pendingCount = pendingCount;
	}



}
