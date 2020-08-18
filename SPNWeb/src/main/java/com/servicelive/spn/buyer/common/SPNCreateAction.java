package com.servicelive.spn.buyer.common;

import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_CAMPAIGN_PENDING;
import static com.servicelive.spn.constants.SPNActionConstants.RESULT_RESOURCE_CRED_TYPES_WITH_CATEGORIES;
import static com.servicelive.spn.constants.SPNActionConstants.RESULT_SERVICES_WITH_SKILLS;
import static com.servicelive.spn.constants.SPNActionConstants.RESULT_VENDOR_CRED_TYPES_WITH_CATEGORIES;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.validator.routines.AbstractNumberValidator;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.velocity.util.StringUtils;

import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.lookup.LookupLanguage;
import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.domain.lookup.LookupSkills;
import com.servicelive.domain.spn.campaign.CampaignHeader;
import com.servicelive.domain.spn.campaign.CampaignSPN;
import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.domain.spn.detached.Email;
import com.servicelive.domain.spn.detached.LookupVO;
import com.servicelive.domain.spn.detached.SPNCreateNetworkDisplayDocumentsVO;
import com.servicelive.domain.spn.network.ReleaseTiersVO;
import com.servicelive.domain.spn.network.SPNBuyer;
import com.servicelive.domain.spn.network.SPNDocument;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.buyer.network.SPNCreateNetworkAction;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.common.util.UiUtil;
import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.spn.mapper.ApprovalCriteriaMapper;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.buyer.SPNBuyerServices;
import com.servicelive.spn.services.campaign.CampaignSummaryServices;
import com.servicelive.spn.services.campaign.CreateCampaignServices;
import com.servicelive.spn.services.network.CreateNetworkServices;
import com.servicelive.spn.services.providermatch.ApprovalCriteriaHelper;
import com.servicelive.spn.services.workflow.EmailTask;
import com.servicelive.spn.services.workflow.WorkFlowPersistService;

/**
 * 
 * 
 *
 */
public abstract class SPNCreateAction extends SPNBaseAction
implements Preparable
{
	private static final long serialVersionUID = -6807400514136818993L;
	protected LookupService lookupService;
	protected ApprovalCriteriaMapper approvalCriteriaMapper;
	protected SPNBuyerServices spnBuyerServices;
	protected CreateNetworkServices createSPNServices;
	protected WorkFlowPersistService spnWorkflowSerivce;
	protected CreateCampaignServices createCampaignServices;
	protected CampaignSummaryServices campaignSummaryServices;
	protected EmailTask emailTask;
	protected Integer buyerId;
	protected String vf = "false";
	protected SPNCreateModel model = null;
	
	private String HIDE_EDIT_LINK = "hideEditLink";
		
	/**
	 * 
	 * @return String
	 */
	public abstract String display();
		
	public abstract void prepare() throws Exception;
		
		
	private void getMainServicesWithSkills()
	{
		List<LookupVO> mainServicesWithSkills = new ArrayList<LookupVO>();
		if (model.getApprovalItems() == null)
		{
			return;
		}
		
		List<Integer> selectedMainServices = model.getApprovalItems().getSelectedMainServices();
		if (selectedMainServices.size() > 0 && selectedMainServices.get(0) != null)
		{
			mainServicesWithSkills = lookupService.getSkillsConcatinatedWithServices(selectedMainServices);
		}
		
		if (model.getLookupsVO() == null)
		{
			SPNLookupVO lookupsVO = new SPNLookupVO();
			model.setLookupsVO(lookupsVO);
		}
		model.getLookupsVO().setMainServicesWithSkills(mainServicesWithSkills);
		
		return;
	}	
	/**
	 * 
	 * @return String
	 */
	public String getMainServicesWithSkillsAjax()
	{
		getMainServicesWithSkills();
		
		return RESULT_SERVICES_WITH_SKILLS;
	}
	/**
	 * 
	 * @return String
	 */
	public String getVendorCredTypesWithCategoriesAjax()
	{
		getVendorCredCategoriesWithTypes();
		
		return RESULT_VENDOR_CRED_TYPES_WITH_CATEGORIES;
	}
	
	private void getVendorCredCategoriesWithTypes() {
		if (model.getApprovalItems() == null)
		{
			return;
		}
		List<Integer> selectedVendorCredTypes = model.getApprovalItems().getSelectedVendorCredTypes();
		if (model.getLookupsVO() == null)
		{
			SPNLookupVO lookupsVO = new SPNLookupVO();
			model.setLookupsVO(lookupsVO);
		}
		model.getLookupsVO().setVendorCredCategoriesWithTypes(
				lookupService.getVendorCredCatgoriesConcatinatedWithTypes(selectedVendorCredTypes));
	}
	/**
	 * 
	 * @return String
	 */
	public String getResCredTypesWithCategoriesAjax()
	{
		getResCredCategoriesWithTypes();
		
		return RESULT_RESOURCE_CRED_TYPES_WITH_CATEGORIES;
	}
	
	private void getResCredCategoriesWithTypes() {
		if (model.getApprovalItems() == null)
		{
			return;
		}
		List<Integer> selectedResCredTypes = model.getApprovalItems().getSelectedResCredTypes();
		if (model.getLookupsVO() == null)
		{
			SPNLookupVO lookupsVO = new SPNLookupVO();
			model.setLookupsVO(lookupsVO);
		}
		model.getLookupsVO().setResCredCategoriesWithTypes(
				lookupService.getResourceCredCatgoriesConcatinatedWithTypes(selectedResCredTypes));
	}
	/**
	 * 
	 * @param parentId
	 * @return List
	 */
	public List<LookupSkills> getSubCategories(Integer parentId)
	{
		List<LookupSkills> tmpList = lookupService.getSkillsSubCategoriesFromParent(parentId);
		
		return tmpList;
	}
	
	private void getSubCategories()
	{
		//Main verticle interger Id --> Map 0 "Main Service" - the first LookupSkills description holds the title
		//								Map 1 "CATEGORY",ArrayList<LookupSkills>
		//								Map 2 "SUNCATEGORY", ArrayList
		Map<Integer, Map<String,List<LookupSkills>>> map = new TreeMap<Integer, Map<String,List<LookupSkills>>>();
		
		if (model.getApprovalItems() == null)
		{
			return;
		}
		
		List<Integer> selectedMainServices = model.getApprovalItems().getSelectedMainServices();
		List<Integer> selectedSubCategories = model.getApprovalItems().getSelectedSubServices1();
		for (int x = 0; x < selectedMainServices.size(); x++)
		{
			Map<String,List<LookupSkills>> subCategories = new TreeMap<String,List<LookupSkills>>();
			List<LookupSkills> subCats = lookupService.getSkillsSubCategoriesFromParent(selectedMainServices.get(x));
			
			List<LookupSkills> mainServiceNameList = new ArrayList<LookupSkills>();
			LookupSkills mainServiceNameLS = new LookupSkills();
			mainServiceNameLS.setId(Integer.valueOf(0));
			mainServiceNameLS.setDescription(lookupService.getSkillFromSkillId(selectedMainServices.get(x)).getDescription());
			mainServiceNameList.add(mainServiceNameLS);
			//added for sorting
			Collections.sort(mainServiceNameList,new Comparator<LookupSkills>() {
                public int compare(LookupSkills o1, LookupSkills o2) {
                	if(null==o1.getDescription() && null !=o2.getDescription()){
                		return 1;
                	}else if(null!=o1.getDescription() && null ==o2.getDescription()){
                		return -1;
                	}else if(null==o1.getDescription() && null ==o2.getDescription()){
                		return 0;
                	}else{
                		return o1.getDescription().trim().compareToIgnoreCase(o2.getDescription().trim());
                	}
				}
			});
			subCategories.put("0", mainServiceNameList);
			subCategories.put("1", subCats);
			List<LookupSkills> subCats2 = new ArrayList<LookupSkills>();
			for (int y = 0; y < subCats.size(); y++)
			{
				if (selectedSubCategories.contains(subCats.get(y).getId()))
				{
					subCats2.addAll(lookupService.getSkillsSubCategoriesFromParent(subCats.get(y).getId()));
					//List<LookupSkills> subCats2 = lookupService.getSkillsSubCategoriesFromParent(selectedSubCategories.get(y));
					//subCategories.put("2", subCats2);
				}
			}
			subCategories.put("2", subCats2);
			map.put(Integer.valueOf(x), subCategories);
		}
		
		if (model.getLookupsVO() == null)
		{
			SPNLookupVO lookupsVO = new SPNLookupVO();
			model.setLookupsVO(lookupsVO);
		}
		
		model.getLookupsVO().setSubServicesMap(map);
		
		return;
	}
	

	/**
	 * 
	 * @return String
	 */
	public String getSubCategoriesAjax()
	{
		getSubCategories();
		
		return "subCategories";
	}
	/**
	 * 
	 * @return String
	 */
	public String getSub2CategoriesAjax()
	{
		if (model.getLookupsVO() == null)
		{
			SPNLookupVO lookupsVO = new SPNLookupVO();
			model.setLookupsVO(lookupsVO);
		}
		
		if (model.getApprovalItems() != null && model.getApprovalItems().getSelectedSubServices1() != null && model.getApprovalItems().getSelectedSubServices1().size() == 1 && model.getApprovalItems().getSelectedSubServices1().get(0).intValue() == -1)
		{
			return "subServices2";
		}
		else if (model.getApprovalItems() != null && model.getApprovalItems().getSelectedSubServices1() != null && model.getApprovalItems().getSelectedSubServices1().size() > 0)
		{
			List<LookupSkills> totalList = new ArrayList<LookupSkills>();
			for (int x = 0; x < model.getApprovalItems().getSelectedSubServices1().size(); x++)
			{
				List<LookupSkills> subServices2 = getSubCategories(model.getApprovalItems().getSelectedSubServices1().get(x));
				totalList.addAll(subServices2);
			}
			//added for sorting...
			Collections.sort(totalList,new Comparator<LookupSkills>() {
            public int compare(LookupSkills o1, LookupSkills o2) {
	            	if(null==o1.getDescription() && null !=o2.getDescription()){
	            		return 1;
	            	}else if(null!=o1.getDescription() && null ==o2.getDescription()){
	            		return -1;
	            	}else if(null==o1.getDescription() && null ==o2.getDescription()){
	            		return 0;
	            	}else{
	            		return o1.getDescription().trim().compareToIgnoreCase(o2.getDescription().trim());
	            	}
				}
			});
			model.getLookupsVO().setSubServices2(totalList);
			return "subServices2";
		}
		
		return ERROR;
	}
	
	protected void initDropdowns()
	{
		SPNLookupVO lookupsVO = new SPNLookupVO();
		lookupsVO.setAllMainServices(lookupService.getSkillsMainSkills());
		lookupsVO.setAllMinimumRatings(lookupService.getAllMimimumRating());
		lookupsVO.setAllLanguages(lookupService.getLanguages());
		lookupsVO.setAllMarkets(lookupService.getAllMarkets());
		lookupsVO.setAllStates(lookupService.findStatesNotBlackedOut());
		lookupsVO.setAllCompanySizes(lookupService.getAllCompanySizes());
		lookupsVO.setAllSalesVolumes(lookupService.getAllSalesVolumes());
		lookupsVO.setVendorCredTypesList(lookupService.getAllVendorCredentialTypes());
		lookupsVO.setResCredTypesList(lookupService.getAllResourceCredentialTypes());
		lookupsVO.setDocTypesList(lookupService.getDocumentTypes());
		//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
		//fetch the primary industry list and set it to lookupsVO to init the primary industry drop down
		lookupsVO.setPrimaryIndustry(lookupService.getPrimaryIndustry());
		model.setLookupsVO(lookupsVO);
		getMainServicesWithSkills();
		getSubCategories();
		getVendorCredCategoriesWithTypes();
		getResCredCategoriesWithTypes();
		
	}

	protected void validateInsurance(ApprovalModel approvalModel)
	{
		BigDecimalValidator validator = new BigDecimalValidator(true,AbstractNumberValidator.STANDARD_FORMAT, true) {
			private static final long serialVersionUID = 20090113L;
		};
		
		if (approvalModel == null)
			return;

		if (approvalModel.getVehicleLiabilitySelected().booleanValue())
		{
			BigDecimal amount = validator.validate(approvalModel.getVehicleLiabilityAmt());
			if (amount == null) {
				this.addFieldError("Vehicle Liability Amount", this.getText("errors.common.vehicle.liability.format"));
			}
			
			if (amount != null && amount.doubleValue() <= 0.0) {
				this.addFieldError("Vehicle Liability Amount", this.getText("errors.common.vehicle.liability.low.limit"));
			}
			if (amount != null && amount.doubleValue() > 10000000.0) {
				this.addFieldError("Vehicle Liability Amount", this.getText("errors.common.vehicle.liability.high.limit"));
			}
		}
		if (approvalModel.getCommercialGeneralLiabilitySelected().booleanValue())
		{
			BigDecimal amount = validator.validate(approvalModel.getCommercialGeneralLiabilityAmt());
			if (amount == null) {
				this.addFieldError("Commercial General Liability Amount", this.getText("errors.common.commercial.liability.format"));
			}
			
			if (amount != null && amount.doubleValue() <= 0.0) {
				this.addFieldError("Commercial General Liability Amount", this.getText("errors.common.commercial.liability.low.limit"));
			}
			if (amount != null && amount.doubleValue() > 10000000.0) {
				this.addFieldError("Commercial General Liability Amount",this.getText("errors.common.commercial.liability.high.limit") );
			}
		}
		
	}
	
	
	/**
	 * 
	 * @param spnheader
	 * @param pApprovalItems
	 * @return ApprovalModel
	 */
	public ApprovalModel getDocumentList(SPNHeader spnheader, ApprovalModel pApprovalItems)
	{
		ApprovalModel approvalItems2 = pApprovalItems;
		if (approvalItems2 == null)
		{
			approvalItems2 = new ApprovalModel();
		}
		
		List<SPNDocument> spnDocuments = spnheader.getSpnDocuments();
		List<SPNCreateNetworkDisplayDocumentsVO> dispDocs = new ArrayList<SPNCreateNetworkDisplayDocumentsVO>();
		for (int x = 0; x < spnDocuments.size(); x++)
		{
			SPNDocument curDoc = spnDocuments.get(x);
			SPNCreateNetworkDisplayDocumentsVO tmpDispDoc = new SPNCreateNetworkDisplayDocumentsVO();
			tmpDispDoc.setSpnDocId(curDoc.getId());
			tmpDispDoc.setTitle(curDoc.getDocument().getTitle());
			tmpDispDoc.setType((String)curDoc.getDocTypeId().getDescription());
			tmpDispDoc.setDescription(curDoc.getDocument().getDescr());
			tmpDispDoc.setTypeId((Integer)curDoc.getDocTypeId().getId());
			tmpDispDoc.setDocumentId(curDoc.getDocument().getDocumentId());
			
			dispDocs.add(tmpDispDoc);
		}
		approvalItems2.setSpnDocumentList(dispDocs);
		
		return approvalItems2;
	}

	protected void initViewSelectedDropDowns(ApprovalModel approvalModel)
	{
		SPNLookupVO lookupsVO = initViewSelectedDropDownsForCampaignMonitor(approvalModel);
		model.setLookupsVO(lookupsVO);
	
		
	}
	
	protected SPNLookupVO initViewSelectedDropDownsForCampaignMonitor(ApprovalModel approvalModel)
	{
		SPNLookupVO lookupsVO = new SPNLookupVO();
		//lookupsVO.setAllMinimumRatings(lookupService.getAllMimimumRating());
		lookupsVO.setAllLanguages(getSelectedLanguages(approvalModel.getSelectedLanguages()));
		
		// set selected Vendor Credential
		List<Integer> selectedVendorCredCategories = approvalModel.getSelectedVendorCredCategories();
		List<Integer> selectedVendorParentCredCategories = approvalModel.getSelectedVendorCredTypes();
		
		lookupsVO.setVendorCredCategoriesWithTypes(
				lookupService.getSelectedVendorCredCatgoriesConcatinatedWithTypes(selectedVendorCredCategories,selectedVendorParentCredCategories));
		
		
		// set selected Resource Credential
		List<Integer> selectedResourceCredCategry = approvalModel.getSelectedResCredCategories();
		List<Integer> selectedResourceParentCredCategories = approvalModel.getSelectedResCredTypes();
		
		lookupsVO.setResCredCategoriesWithTypes(
				lookupService.getSelectedResourceCredCatgoriesConcatinatedWithTypes(selectedResourceCredCategry,selectedResourceParentCredCategories));
				
		return lookupsVO;
		
		
	}
	
	private List<LookupLanguage> getSelectedLanguages(List<Integer> selectedLang){
		List<LookupLanguage> lookupSelectedLang = new ArrayList<LookupLanguage>();
		List<LookupLanguage> allLanguages = lookupService.getLanguages();
		for(LookupLanguage lang : allLanguages ){
			for(Integer langId : selectedLang){
				if(lang.getId().intValue() == langId.intValue()){
					lookupSelectedLang.add(lang);
				}
			}
		
		}
	
		return lookupSelectedLang;
	}
	
/*	private List<LookupMinimumRating> getSelectedMinRatings(List<Integer> selectedMinRating){
		List<LookupMinimumRating> lookupSelectedMinRating = new ArrayList<LookupMinimumRating>();
		List<LookupMinimumRating> allMinratings = lookupService.getAllMimimumRating();
		for(LookupMinimumRating minRating : allMinratings ){
			for(Integer minRatingId : selectedMinRating){
				if(minRating.getId().intValue() == minRatingId.intValue()){
					lookupSelectedMinRating.add(minRating);
				}
			}
		
		}
	
		return lookupSelectedMinRating;
	}*/
	
	
	/**
	 * 
	 * @param networkId
	 * @return String
	 * @throws Exception
	 */
	public String displayTabNetworkDetailsAjax(int networkId) throws Exception
	{

		if(createSPNServices != null && networkId > 0)
		{
			SPNHeader spnheader = createSPNServices.getNetwork(Integer.valueOf(networkId));
			if(spnheader == null)
			{
				spnheader = new SPNHeader();
			}
			getRequest().setAttribute("networkHeader", spnheader);
			ApprovalModel approvalItems = ApprovalCriteriaHelper.getApprovalModelFromCriteria(spnheader.getApprovalCriterias());
			//System.out.println("recertification value in spn create action:"+approvalItems.getRecertificationInd());
			approvalItems = getDocumentList(spnheader, approvalItems);
			
			//initViewServicesAndSkills(approvalItems); //- Original possibly, obsolete code.
			
			SPNCreateNetworkAction.initViewServicesAndSkills(approvalItems, lookupService);
			approvalItems = getDocumentList(spnheader, approvalItems);
			
			getRequest().setAttribute("approvalItems", approvalItems);
			SPNLookupVO lookupVO = initViewSelectedDropDownsForCampaignMonitor(approvalItems);
			if(spnheader.getExceptionsInd()){
				lookupVO = initExceptionsForCredentials(lookupVO,networkId);
			}
			getRequest().setAttribute("selectedApprovalItems", lookupVO);
			
						
		}
		
		
		return SUCCESS;
	}

	/**
	 * @param lookupVO
	 * @param networkId
	 * @return SPNLookupVO
	 * 
	 * method to fetch exception for credential (sl-18018)
	 * 
	 */
	private SPNLookupVO initExceptionsForCredentials(SPNLookupVO lookupVO,
			int networkId) {
		List<SPNExclusionsVO> vendorExclusions = new ArrayList<SPNExclusionsVO>();
		List<SPNExclusionsVO> resExclusions = new ArrayList<SPNExclusionsVO>();
		if(lookupVO.getVendorCredCategoriesWithTypes()!=null && lookupVO.getVendorCredCategoriesWithTypes().size()>0){
			vendorExclusions = lookupService.getExclusionsForCredentials(lookupVO.getVendorCredCategoriesWithTypes(),networkId,"vendor");
			lookupVO.setVendorExclusions(vendorExclusions);
			lookupVO= mapCredentialExceptions(lookupVO,"vendor");
		}
		else{
			lookupVO.setVendorExclusions(null);
		}
		if(lookupVO.getResCredCategoriesWithTypes()!=null && lookupVO.getResCredCategoriesWithTypes().size()>0){
			resExclusions = lookupService.getExclusionsForCredentials(lookupVO.getResCredCategoriesWithTypes(),networkId,"resource");
			lookupVO.setResExclusions(resExclusions);
			lookupVO= mapCredentialExceptions(lookupVO,"resource");
		}
		else{
			lookupVO.setResExclusions(null);
		}
		return lookupVO;
				
		
	}

	/**
	 * @param lookupVO
	 * @param string
	 * @return SPNLookupVO
	 * 
	 * method to map exceptions
	 * 
	 */
	private SPNLookupVO mapCredentialExceptions(SPNLookupVO lookupVO,
			String credentialType) {
		
		List<LookupVO> credentials = new ArrayList<LookupVO>();
		List<SPNExclusionsVO> exceptions = new ArrayList<SPNExclusionsVO>();
		if("vendor".equals(credentialType)){
			credentials = lookupVO.getVendorCredCategoriesWithTypes();
			exceptions = lookupVO.getVendorExclusions();
		}
		else if("resource".equals(credentialType)){
			credentials = lookupVO.getResCredCategoriesWithTypes();
			exceptions = lookupVO.getResExclusions();
		}
		for(LookupVO vo : credentials){
			if(vo!=null){
				List<Object> history = new ArrayList<Object>();
				if(vo.getDescription()!=null && vo.getId()!=null){
					String desc = (String)vo.getDescription();
					Integer id = (Integer)vo.getId();
					vo.setExceptionInd(false);
					for(SPNExclusionsVO exclusionsVO : exceptions){
						if(exclusionsVO!=null && exclusionsVO.getActiveInd()==true){
							vo.setExceptionInd(true);
							break;
						}
						}
					for(SPNExclusionsVO exclusionsVO : exceptions){
						if(exclusionsVO!=null){
							if(desc.contains(">")){
								if(null != exclusionsVO.getCredentialCategoryId() && exclusionsVO.getCredentialCategoryId().intValue()== id.intValue()){
									if(exclusionsVO.getExceptionTypeId().intValue() == 1){
										history.add("Allowed Grace Period : "+exclusionsVO.getExceptionValue()+" days");
									}
									else if(exclusionsVO.getExceptionTypeId().intValue() == 2){
										history.add("State(s) Exempted: "+exclusionsVO.getExceptionValue());
									}
								}
							}
							else{
								if(null == exclusionsVO.getCredentialCategoryId() && null != exclusionsVO.getCredentialTypeId() && exclusionsVO.getCredentialTypeId().intValue()== id.intValue()){
									if(exclusionsVO.getExceptionTypeId().intValue() == 1){
										history.add("Allowed Grace Period : "+exclusionsVO.getExceptionValue()+" days");
									}
									else if(exclusionsVO.getExceptionTypeId().intValue() == 2){
										history.add("State(s) Exempted: "+exclusionsVO.getExceptionValue());
									}
								}
							}
						}
					}
				}
				if(history!=null && history.size()>0){
					vo.setHistory(history);
				}
			}
		}
		return lookupVO;
	}

	public String displayTabRoutingTiersAjax(int networkId) throws Exception
	{

		if(createSPNServices != null && networkId > 0)
		{
			List<ReleaseTiersVO> releaseTiers = createSPNServices.getReleaseTiers(Integer.valueOf(networkId));
			SPNHeader spnheader = createSPNServices.getNetwork(Integer.valueOf(networkId));
			
			// Sort by tierId.  Stuff in DB not necessarily ordered by tier.
			Collections.sort(releaseTiers);
			
			getRequest().setAttribute("releaseTiers", releaseTiers);
			
			// Front end wants 1 or 0, not true or false.
			Integer marketplaceOverflow;
			if(spnheader.getMarketPlaceOverFlow().booleanValue()) {
				marketplaceOverflow = Integer.valueOf(1);
			} else {
				marketplaceOverflow = Integer.valueOf(0);
			}
			if(null!=spnheader.getCriteriaLevel()) {
				getRequest().setAttribute("perfCriteriaLevel",StringUtils.capitalizeFirstLetter((spnheader.getCriteriaLevel().toLowerCase())));
			}
			getRequest().setAttribute("marketplaceOverflow", marketplaceOverflow);
			
			// SPN Id
			getRequest().setAttribute("spnBeingEdited", spnheader.getSpnId());
			
			// Hide Edit Link - Pass this thru
			getRequest().setAttribute(HIDE_EDIT_LINK, getRequest().getAttribute(HIDE_EDIT_LINK));
			
		}
		
		
		return SUCCESS;
	}
	
	protected void initTabDisplay()
	{
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
	
	
	
	protected  List<Email> getListOfCampaignPendingEmails(CampaignHeader campaign) {

		CampaignSPN campaignSPN = campaign.getCampaignSPN().iterator().next();
		SPNBuyer buyer = campaignSPN.getSpn().getBuyer().iterator().next();
		Map<String, String> params = new HashMap<String, String>();
		params.put("spnName",campaignSPN.getSpn().getSpnName());
		params.put("campaignName", campaign.getCampaignName());
		params.put("buyerName", buyer.getBuyerId().getBusinessName());
		params.put("buyerId", String.valueOf(buyer.getBuyerId().getBuyerId()));
		params.put("campaignStartDate", CalendarUtil.formatDate(campaign.getStartDate(), "yyyy-MM-dd"));
		params.put("campaignEndDate",  CalendarUtil.formatDate(campaign.getEndDate(), "yyyy-MM-dd"));
		params.put("spnContactEmail", campaignSPN.getSpn().getContactEmail());
		params.put("spnContactName", campaignSPN.getSpn().getContactName());
		params.put("spnContactPhone", UiUtil.formatPhone(campaignSPN.getSpn().getContactPhone()));

		try {
			ApprovalModel approvalModel = ApprovalCriteriaHelper.getApprovalModelFromCriteria(campaign.getApprovalCriterias());
			params.put("dynamicMessage", createDynamicText(approvalModel));
		} catch (Exception e1) {
			//e1.printStackTrace();
			logger.error("couldn't create market or state list for the following reason",e1);
		}

		List<Email> emails = new ArrayList<Email>();
			try {
				emails =  emailTask.getTask(WF_STATUS_CAMPAIGN_PENDING, params, SPNBackendConstants.EMAIL_ACTION_CAMPAIGN_CREATED);
			} catch (Exception e) {
				logger.debug(e);
			}
		 return emails;
	}
	
	private String createDynamicText(ApprovalModel approvalModel) {
		StringBuilder sb = new StringBuilder();
		String marketLine = createMarketLine(approvalModel.getSelectedMarkets());
		if(marketLine != null && marketLine.trim().length() != 0) {
			sb.append("<li>");
			sb.append(marketLine);
			sb.append("</li>");

		}

		String stateLine = createStateLine(approvalModel.getSelectedStates());
		if(stateLine != null && stateLine.trim().length() != 0) {
			sb.append("<li>");
			sb.append(stateLine);
			sb.append("</li>");
		}

		return sb.toString();
	}

	private String createMarketLine(List<Integer> marketIds) {
		StringBuilder sb = new StringBuilder();
		List<String> markets = new ArrayList<String>();
		List<LookupMarket> lookupMarkets = lookupService.getAllMarkets();
		for(Integer marketId :marketIds) {
			for(LookupMarket lookupMarket:lookupMarkets) {
				if(lookupMarket.getId().equals(marketId)) {
					markets.add(lookupMarket.getDescription());
					break;
				}
			}
		}

		Collections.sort(markets);

		//Maybe want to sort the markets?

		for(String market : markets) {
			if(sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(market);
		}

		int size = markets.size();
		if(size == 0) {
			// don't append anything
		} else if(size == 1) {
			sb.insert(0, "Market : ");
		} else {
			sb.insert(0, "Markets : ");
		}

		return sb.toString();

	}

	private String createStateLine(List<String> states) {
		StringBuilder sb = new StringBuilder();

		Collections.sort(states);

		for(String state : states) {
			if(sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(state);
		}

		int size = states.size();
		if(size == 0) {
			// don't append anything
		} else if(size == 1) {
			sb.insert(0, "State : ");
		} else {
			sb.insert(0, "States : ");
		}

		return sb.toString();
	}

	
	/**
	 * @return the lookupService
	 */
	public LookupService getLookupService() {
		return lookupService;
	}

	/**
	 * @param lookupService the lookupService to set
	 */
	public void setLookupService(LookupService lookupService) {
		this.lookupService = lookupService;
	}

	/**
	 * @return the approvalCriteriaMapper
	 */
	public ApprovalCriteriaMapper getApprovalCriteriaMapper() {
		return approvalCriteriaMapper;
	}

	/**
	 * @param approvalCriteriaMapper the approvalCriteriaMapper to set
	 */
	public void setApprovalCriteriaMapper(
			ApprovalCriteriaMapper approvalCriteriaMapper) {
		this.approvalCriteriaMapper = approvalCriteriaMapper;
	}

	/**
	 * @return the spnBuyerServices
	 */
	public SPNBuyerServices getSpnBuyerServices() {
		return spnBuyerServices;
	}

	/**
	 * @param spnBuyerServices the spnBuyerServices to set
	 */
	public void setSpnBuyerServices(SPNBuyerServices spnBuyerServices) {
		this.spnBuyerServices = spnBuyerServices;
	}

	/**
	 * @return the createSPNServices
	 */
	public CreateNetworkServices getCreateSPNServices() {
		return createSPNServices;
	}

	/**
	 * @param createSPNServices the createSPNServices to set
	 */
	public void setCreateSPNServices(CreateNetworkServices createSPNServices) {
		this.createSPNServices = createSPNServices;
	}

	/**
	 * @return the spnWorkflowSerivce
	 */
	public WorkFlowPersistService getSpnWorkflowSerivce() {
		return spnWorkflowSerivce;
	}

	/**
	 * @param spnWorkflowSerivce the spnWorkflowSerivce to set
	 */
	public void setSpnWorkflowSerivce(WorkFlowPersistService spnWorkflowSerivce) {
		this.spnWorkflowSerivce = spnWorkflowSerivce;
	}

	/**
	 * @return the createCampaignServices
	 */
	public CreateCampaignServices getCreateCampaignServices() {
		return createCampaignServices;
	}

	/**
	 * @param createCampaignServices the createCampaignServices to set
	 */
	public void setCreateCampaignServices(
			CreateCampaignServices createCampaignServices) {
		this.createCampaignServices = createCampaignServices;
	}

	/**
	 * @return the campaignSummaryServices
	 */
	public CampaignSummaryServices getCampaignSummaryServices() {
		return campaignSummaryServices;
	}

	/**
	 * @param campaignSummaryServices the campaignSummaryServices to set
	 */
	public void setCampaignSummaryServices(
			CampaignSummaryServices campaignSummaryServices) {
		this.campaignSummaryServices = campaignSummaryServices;
	}

	/**
	 * @return the emailTask
	 */
	public EmailTask getEmailTask() {
		return emailTask;
	}

	/**
	 * @param emailTask the emailTask to set
	 */
	public void setEmailTask(EmailTask emailTask) {
		this.emailTask = emailTask;
	}

	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the vf
	 */
	public String getVf() {
		return vf;
	}

	/**
	 * @param vf the vf to set
	 */
	public void setVf(String vf) {
		this.vf = vf;
	}

	/**
	 * @return the model
	 */
	/*public SPNCreateModel getModel() {
		return model;
	}*/

	/**
	 * @param model the model to set
	 */
	public void setModel(SPNCreateModel model) {
		this.model = model;
	}
	
}
