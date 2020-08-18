package com.servicelive.spn.buyer.campaign;

import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_CAMPAIGN_PENDING;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN_AS_BUYER;
import static com.servicelive.spn.constants.SPNActionConstants.ROLE_ID_PROVIDER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.velocity.util.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.spn.campaign.CampaignHeader;
import com.servicelive.domain.spn.campaign.CampaignProviderFirm;
import com.servicelive.domain.spn.campaign.CampaignSPN;
import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.domain.spn.detached.ProviderMatchingCountsVO;
import com.servicelive.domain.spn.network.ReleaseTiersVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.buyer.common.SPNCreateAction;
import com.servicelive.spn.buyer.common.SPNLookupVO;
import com.servicelive.spn.buyer.network.SPNCreateNetworkAction;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.constants.SPNActionConstants;
import com.servicelive.spn.services.buyer.ProviderFirmService;
import com.servicelive.spn.services.providermatch.ApprovalCriteriaHelper;
import com.servicelive.spn.services.providermatch.ProviderMatchForApprovalServices;

/**
 *
 *
 *
 */
@Validation
public class SPNCreateCampaignAction extends SPNCreateAction
implements ModelDriven<SPNCreateCampaignModel>
{

	private static final long serialVersionUID = -917491066328837072L;
	private static final String INVITE_BY_CRITERIA_TAB = "showInviteByCriteriaTab";
	private static final String INVITE_BY_PROVIDER_FIRMS_TAB = "showInviteByProvidersTab";
	private SPNCreateCampaignModel model = new SPNCreateCampaignModel();
	private Integer campaignId;
	private ProviderMatchForApprovalServices providerMatchService;
	private ProviderFirmService providerFirmService;

	@Override
	public String display()
	{
		System.out.println("Entering display method of  SPNCreateCampaignAction");
		try
		{
			User loggedInUser = getLoggedInUser();

			if (loggedInUser == null)
			{
				return NOT_LOGGED_IN;
			}
			else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
			{
				return NOT_LOGGED_IN_AS_BUYER;
			}
			//R11.0 SL_19387 Setting the background check feature set
			Integer buyerId = loggedInUser.getUserId();
			if(buyerId != null){
				model.setRecertificationBuyerFeatureInd(createSPNServices.getBuyerFeatureSet(buyerId,"BACKGROUND_CHECK_RECERTIFICATION"));
			}
			// This is the default setting.  These may be overwritten in loadSelectedCampaign()
			getRequest().setAttribute(INVITE_BY_CRITERIA_TAB, Boolean.TRUE);
			getRequest().setAttribute(INVITE_BY_PROVIDER_FIRMS_TAB, Boolean.FALSE);
			
			String paramCampaignId = getRequest().getParameter("campaignHeader.campaignId");
			if (paramCampaignId != null)
			{
				System.out.println("Going to loadSelectedCampaign method of  SPNCreateCampaignAction");
				if (loadSelectedCampaign() == SUCCESS)
				{
					campaignId = Integer.valueOf(paramCampaignId);
					model.getCampaignHeader().setCampaignId(campaignId);
					System.out.println("Leaving loadSelectedCampaign method of  SPNCreateCampaignAction");
					return SUCCESS;
				}
				return ERROR;
			}

			initModelFromSession();
			System.out.println("Going to initPage method of  SPNCreateCampaignAction from display");
			initPage();
			System.out.println("Leaving initPage method of  SPNCreateCampaignAction from display");

		}
		catch (Exception e)
		{
			logger.error("Error Loading page.", e);
			return ERROR;
		}

		initTabDisplay();
		System.out.println("Leaving display method of  SPNCreateCampaignAction");
		return SUCCESS;
	}

	

	private void initModelFromSession() throws Exception
	{
		if(this.vf != null && vf.equalsIgnoreCase("true"))
		{
			if(ActionContext.getContext().getSession().get(SPNActionConstants.SPN_CAMPAIGNMODEL_IN_REQUEST)  != null)
			{
				SPNCreateCampaignModel mymodel =  (SPNCreateCampaignModel)ActionContext.getContext().getSession().get(SPNActionConstants.SPN_CAMPAIGNMODEL_IN_REQUEST);
				this.setActionErrors(mymodel.getActionErrors());
				this.setFieldErrors(mymodel.getFieldErrors());

				getModel().setSpnHeader(mymodel.getSpnHeader());
				getModel().setEndDate(mymodel.getEndDate());
				getModel().setStartDate(mymodel.getStartDate());
				getModel().setCampaignHeader(mymodel.getCampaignHeader());
				getModel().setApprovalItems(mymodel.getApprovalItems());
				getModel().setProvidersMatchingSPN(mymodel.getProvidersMatchingSPN());
				getModel().setProvidersMatchingCampaign(mymodel.getProvidersMatchingCampaign());
			}
		}
		else
		{
			// Remove any object
			if(ActionContext.getContext().getSession().get(SPNActionConstants.SPN_CAMPAIGNMODEL_IN_REQUEST)  != null)
			{
				ActionContext.getContext().getSession().remove(SPNActionConstants.SPN_CAMPAIGNMODEL_IN_REQUEST);
			}
		}
	}


	/**
	 * Initialize the page with SPN list, saved campaing list and all the drop downs
	 * @throws Exception
	 */
	private void initPage() throws Exception {
		getModel().setSpnList(spnBuyerServices.getSPNListForBuyer(buyerId));
		getModel().setCampaignList(campaignSummaryServices.getSaveCampaignListForCriteriaCampaign(buyerId));
		initDropdowns();

		//Invite Specific Provider Subtab
		getRequest().getSession().setAttribute(SPNInviteProviderAction.TMP_PROVIDER_FIRMS, null);		
	}


	/* (non-Javadoc)
	 * @see com.servicelive.spn.buyer.common.SPNCreateAction#prepare()
	 */
	@Override
	public void prepare() throws Exception
	{
		buyerId = getCurrentBuyerId();
		super.setModel(getModel());
	}

	/**
	 * Load the selected campaign and it's approval criterias
	 * @return String
	 */
	public String loadSelectedSpn()
	{
		SPNHeader selectedSpn;
		try {
			if (model.getSpnHeader().getSpnId().intValue() == -1) {
				return display();
			}
			System.out.println("Going to getNetwork method of  createSPNServices from loadSelectedSpn");
			selectedSpn = createSPNServices.getNetwork(model.getSpnHeader().getSpnId());
			System.out.println("Leaving getNetwork method of  createSPNServices from loadSelectedSpn");
			model.setSpnHeader(selectedSpn);
			getRequest().getSession().setAttribute("campaignSpnId", model.getSpnHeader().getSpnId());

			System.out.println("Going to getApprovalModelFromCriteria method of  ApprovalCriteriaHelper from loadSelectedSpn");
			ApprovalModel approvalItms = ApprovalCriteriaHelper.getApprovalModelFromCriteria(selectedSpn.getApprovalCriterias());
			approvalItms = getDocumentList(selectedSpn, approvalItms);
			System.out.println("Leaving getApprovalModelFromCriteria method of  ApprovalCriteriaHelper from loadSelectedSpn");

			model.setApprovalItems(approvalItms);
			model.setSpnApprovalItems(approvalItms);						
			System.out.println("Going to initViewSelectedDropDownsForCampaignMonitor from loadSelectedSpn");
			SPNLookupVO selectedLookupsVO = super.initViewSelectedDropDownsForCampaignMonitor(approvalItms);
			model.setSelectedApprovalItemsVO(selectedLookupsVO);
			System.out.println("Leaving initViewSelectedDropDownsForCampaignMonitor from loadSelectedSpn");
			System.out.println("Going to initPage method from loadSelectedSpn");
			initPage();
			System.out.println("Leaving initPage method from loadSelectedSpn");
			
			getRequest().getSession().setAttribute("campainSpnId", selectedSpn.getSpnId());			
			return SUCCESS;
		} catch (Exception e) {
			logger.error("Error finding SPN for the spn ID=" + model.getSpnHeader().getSpnId(), e);
			return ERROR;
		}

	}

	/**
	 * Load the selected campaign, spn related to selected campaign and campaign approval information
	 * @return String
	 */
	public String loadSelectedCampaign()
	{
		CampaignHeader selectedCampaign;
		try {

			selectedCampaign = createCampaignServices.getCampaign(model.getCampaignHeader().getCampaignId());
			
			boolean setCampaignIdToNull = false;
			// Campaign by Criteria
			if(selectedCampaign.getApprovalCriterias() != null && selectedCampaign.getApprovalCriterias().size() > 0)
			{			
				model.setStartDate(CalendarUtil.formatDate(selectedCampaign.getStartDate(), "yyyy-MM-dd"));
				model.setEndDate(CalendarUtil.formatDate(selectedCampaign.getEndDate(), "yyyy-MM-dd"));
				model.setCampaignHeader(selectedCampaign);
				model.setSpnHeader(selectedCampaign.getCampaignSPN().get(0).getSpn());
			    // Loading these up as zero.  Will use an Ajax call upon page load/ready to reload these.
			    ProviderMatchingCountsVO resCounts = new ProviderMatchingCountsVO();//providerMatchService.getProviderCountsForSPN(model.getSpnHeader().getSpnId());
			    resCounts.setProviderCounts(new Long(0));
			    resCounts.setProviderFirmCounts(new Long(0));
				model.setProvidersMatchingSPN(resCounts);
				ApprovalModel approvalItems = ApprovalCriteriaHelper.getApprovalModelFromCriteria(selectedCampaign.getApprovalCriterias());
	
				SPNHeader selectedSpn = selectedCampaign.getCampaignSPN().get(0).getSpn();
				ApprovalModel spnApprovalItems = ApprovalCriteriaHelper.getApprovalModelFromCriteria(selectedSpn.getApprovalCriterias());
	
				SPNLookupVO selectedLookupsVO = super.initViewSelectedDropDownsForCampaignMonitor(spnApprovalItems);
				model.setSelectedApprovalItemsVO(selectedLookupsVO);
				model.setSpnApprovalItems(spnApprovalItems);
	
				approvalItems = getDocumentList(selectedSpn, approvalItems);
				model.setApprovalItems(approvalItems);				
				System.out.println("Calling initPage method of  SPNCreateCampaignAction from Campaign by Criteria");
				initPage();
				System.out.println("Leaving initPage method of  SPNCreateCampaignAction from Campaign by Criteria");
				campaignId = model.getCampaignHeader().getCampaignId();
				setCampaignIdToNull = true;

				getRequest().setAttribute(INVITE_BY_CRITERIA_TAB, Boolean.TRUE);
				getRequest().setAttribute(INVITE_BY_PROVIDER_FIRMS_TAB, Boolean.FALSE);
			}
			// Campaign by Invite
			else 
			{
				System.out.println("Calling initPage method of  SPNCreateCampaignAction from Campaign by firm");
				initPage();
				System.out.println("Leaving initPage method of  SPNCreateCampaignAction from Campaign by firm");
				model.setStartDate2(CalendarUtil.formatDate(selectedCampaign.getStartDate(), "yyyy-MM-dd"));
				model.setEndDate2(CalendarUtil.formatDate(selectedCampaign.getEndDate(), "yyyy-MM-dd"));
				model.setInviteCampaignName(selectedCampaign.getCampaignName());
				model.setSpnHeader(selectedCampaign.getCampaignSPN().get(0).getSpn());
				model.setCampaignHeader(selectedCampaign);
				model.getCampaignHeader().setCampaignId(selectedCampaign.getCampaignId());
				model.setCampaignId(selectedCampaign.getCampaignId());
				
				// Invited Providers
				List<ProviderFirmSearchResultDTO> results = getProviderFirmDTOs(selectedCampaign.getProviderFirms());
				model.setProviderFirmList(results);
				getRequest().getSession().setAttribute(SPNInviteProviderAction.TMP_PROVIDER_FIRMS, results);
				getRequest().setAttribute("showDeleteButton", Boolean.TRUE);
				getRequest().setAttribute(INVITE_BY_CRITERIA_TAB, Boolean.FALSE);				
				getRequest().setAttribute(INVITE_BY_PROVIDER_FIRMS_TAB, Boolean.TRUE);				
			}
			
			// Get Tiered Routing Information
			SPNHeader selectedSpn = selectedCampaign.getCampaignSPN().get(0).getSpn();
			List<ReleaseTiersVO> releaseTiers = getCreateSPNServices().getReleaseTiers(selectedSpn.getSpnId());
			if(releaseTiers != null && releaseTiers.size()> 0 )
				Collections.sort(releaseTiers);
			model.setReleaseTiers(releaseTiers);
			
			if(setCampaignIdToNull) {
				model.getCampaignHeader().setCampaignId(null);
			}
			
			return SUCCESS;
		} catch (Exception e) {
			logger.error("Error finding campaign for the campaign ID=" + model.getCampaignHeader().getCampaignId(), e);
			return ERROR;
		}

	}
	
	private <T extends CampaignProviderFirm> List<ProviderFirmSearchResultDTO> getProviderFirmDTOs( List<T> providerFirms)
	{
		List<ProviderFirmSearchResultDTO> list = new ArrayList<ProviderFirmSearchResultDTO>();
		for(T cpf : providerFirms)
		{
			ProviderFirmSearchResultDTO dto = new ProviderFirmSearchResultDTO(cpf.getProviderFirm().getId(), cpf.getProviderFirm().getBusinessName(), cpf.getProviderFirm().getServiceLiveWorkFlowState().getDescription());
			list.add(dto);			
		}
		
		return list;
	}

	/**
	 * Load the spn list in the page
	 * @return String
	 */
	public String loadSpnList()
	{
		CampaignHeader selectedCampaign;
		try {
			selectedCampaign = createCampaignServices.getCampaign(model.getCampaignHeader().getCampaignId());
			model.setSpnList(spnBuyerServices.getSPNListForBuyer(buyerId));
			model.setSpnHeader(selectedCampaign.getCampaignSPN().get(0).getSpn());
			return SUCCESS;
		} catch (Exception e) {
			logger.error("Error finding spn list.", e);
			return ERROR;
		}

	}

	/**
	 * Update the right side panel information
	 * @return String
	 */
	public String updateRightSideAjax()
	{
		// Yes, I know this is duplicated in loadSelectedSpn(). First pass testing
		try {
			SPNHeader selectedSpn = model.getSpnHeader();
			User loggedInUser = getLoggedInUser();

			if (loggedInUser == null)
			{
				return NOT_LOGGED_IN;
			}
			else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
			{
				return NOT_LOGGED_IN_AS_BUYER;
			}
			//R11.0 SL_19387 Setting the background check feature set
			Integer buyerId = loggedInUser.getUserId();
			if(buyerId != null){
				model.setRecertificationBuyerFeatureInd(createSPNServices.getBuyerFeatureSet(buyerId,"BACKGROUND_CHECK_RECERTIFICATION"));
			}
			if (selectedSpn != null)
			{
				loadSelectedSpn();
				model.setApprovalItems(getDocumentList(model.getSpnHeader(), model.getApprovalItems()));
				SPNCreateNetworkAction.initViewServicesAndSkills(model.getApprovalItems(), lookupService);
				
			/*	List<ReleaseTiersVO> releaseTiers = getCreateSPNServices().getReleaseTiers(selectedSpn.getSpnId());
				if(releaseTiers != null && releaseTiers.size()> 0 )
					Collections.sort(releaseTiers);
				model.setReleaseTiers(releaseTiers);*/
				displayTabRoutingTiersAjaxs(selectedSpn.getSpnId());
				
				getRequest().getSession().setAttribute("campainSpnId", selectedSpn.getSpnId());
				return SUCCESS;
			}
			return loadSelectedCampaign();
		} catch (Exception e) {
			logger.error("Error finding SPN for the spn ID" + model.getSpnHeader().getSpnId(), e);
			return ERROR;
		}

	}
	
	
	public void displayTabRoutingTiersAjaxs(int networkId) 
	{

		if(createSPNServices != null && networkId > 0)
		{
			List<ReleaseTiersVO> releaseTiers;
			try {
				releaseTiers = createSPNServices.getReleaseTiers(Integer.valueOf(networkId));
			
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
			getRequest().setAttribute("hideEditLink", getRequest().getAttribute("hideEditLink"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

	/**
	 *
	 * @return String
	 */
	public String getSPNCountsAjax()
	{
		// Yes, I know this is duplicated in loadSelectedSpn(). First pass testing
		try {

			Integer	spnIdInt = getModel().getSpnHeader().getSpnId();
			
			ProviderMatchingCountsVO resCounts = providerMatchService.getProviderCountsForSPN(spnIdInt);
			getModel().setProvidersMatchingSPN(resCounts);
			getRequest().setAttribute("providerCount", resCounts.getProviderCounts());
			getRequest().setAttribute("providerFirmCount", resCounts.getProviderFirmCounts());
			getRequest().setAttribute("providerMatchingCounts", resCounts);

			return SUCCESS;
		} catch (Exception e) {
			logger.error("Error finding SPN for the spn ID" + model.getSpnHeader().getSpnId(), e);
			return ERROR;
		}
	}

	/**
	 *
	 * @return String
	 */
	public String getCampaignCountsAjax()
	{
		// Yes, I know this is duplicated in loadSelectedSpn(). First pass testing
		try {
			//ProviderMatchApprovalCriteriaVO providerMatchApprovalCriteriaVO = new ProviderMatchApprovalCriteriaVO();
			List<ProviderMatchingCountsVO> list = providerMatchService.getProvidersCountVOsForInvitationCriteria(ApprovalCriteriaHelper.getCriteriaVOFromModel(model.getApprovalItems()));
			ProviderMatchingCountsVO resCounts = new ProviderMatchingCountsVO();
			if (list != null && list.size() > 0 )
			{
				resCounts = list.get(0);
			}
			model.setProvidersMatchingCampaign(resCounts);
			

			return SUCCESS;
		} catch (Exception e) {
			logger.error("Error finding SPN for the spn ID" + model.getSpnHeader().getSpnId(), e);
			return ERROR;
		}
	}

	/**
	 * Save
	 * @return String
	 */
	public String saveAndDone()
	{
		try {
			
			long startTime = System.currentTimeMillis();
			CampaignHeader campaignHeader = model.getCampaignHeader();
			logger.info("time for campaignHeader::"+(System.currentTimeMillis() - startTime));
			//Set the basic information
			campaignHeader.setStartDate(CalendarUtil.parseDate(model.getStartDate(), "yyyy-MM-dd"));
			campaignHeader.setEndDate(CalendarUtil.parseDate(model.getEndDate(), "yyyy-MM-dd"));
			campaignHeader.setModifiedBy(getCurrentBuyerResourceUserName());

			//Set the approval criteria
			long tmpTime = System.currentTimeMillis();
			setApprovalCriteriaOnCampaignHeader(campaignHeader);
			logger.info("time for setApprovalCriteriaOnCampaignHeader::"+(System.currentTimeMillis() - tmpTime));

			//Set the SPN
			tmpTime = System.currentTimeMillis();
			if (campaignHeader.getCampaignSPN().size() == 0) {
				CampaignSPN campaignspnSPN = new CampaignSPN();
				campaignspnSPN.setModifiedBy(getCurrentBuyerResourceUserName());
				long tmpTime1 = System.currentTimeMillis();
				campaignspnSPN.setSpn(createSPNServices.getNetwork(model.getSpnHeader().getSpnId()));
				logger.info("time for campaignspnSPN.setSpn::"+(System.currentTimeMillis() - tmpTime1));
				campaignHeader.getCampaignSPN().add(campaignspnSPN);
			}
			logger.info("time for setApprovalCriteriaOnCampaignHeader::"+(System.currentTimeMillis() - tmpTime));

			// Save the Campaign
			tmpTime = System.currentTimeMillis();
			createCampaignServices.saveCampaign(campaignHeader);
			logger.info("time for createCampaignServices.save::"+(System.currentTimeMillis() - tmpTime));
			Integer campaignId2 = campaignHeader.getCampaignId();// model.getSpnHeader().getSpnId();
			String username = getCurrentBuyerResourceUserName();
			tmpTime = System.currentTimeMillis();
			spnWorkflowSerivce.signal(SPNBackendConstants.WF_ENTITY_CAMPAIGN,WF_STATUS_CAMPAIGN_PENDING,campaignId2,username,getListOfCampaignPendingEmails(campaignHeader) );
			logger.info("time for spnWorkflowSerivce.signal::"+(System.currentTimeMillis() - tmpTime));
			logger.info("total time::"+(System.currentTimeMillis() - startTime));

	    } catch (Exception e) {
			logger.error("Error saving campaign.", e);
			return ERROR;
		}
		return SUCCESS;
	}

	
	private void setApprovalCriteriaOnCampaignHeader(CampaignHeader campaignHeader) throws Exception
	{
		approvalCriteriaMapper.setApprovalModel(model.getApprovalItems());
		approvalCriteriaMapper.setUserName(getCurrentBuyerResourceUserName());
		campaignHeader.setApprovalCriterias(
				approvalCriteriaMapper.mapCampaignApprovalModelToApprovalCriterias());
	}


	@SuppressWarnings("unchecked")
	@Override
	public void validate()
	{
		super.validate();
		

		if(getModel().getSpnHeader() == null || getModel().getSpnHeader().getSpnId() == null || getModel().getSpnHeader().getSpnId().equals(Integer.valueOf(0)))
		{
			this.addFieldError("selectedSpn", "A Select Provider Network should be selected from the dropdown menu");
		}

		//Integer inviteBy = getModel().getInviteByDropdownSelection();		
		//if(inviteBy.intValue() == INVITE_BY_CRITERIA.intValue())
		{
			//validateMinimumCompletedOrders();
			validateInsurance(getModel().getApprovalItems());
			validateServicesAndSkillsPanel();
			validateStartEndCampaignDate();
			validateStatesMarketsSelected();
		}

		boolean addRequestAttrib = false;
		// In case validation fails store model to
		if(getActionErrors().size() > 0 )
		{
			model.getActionErrors().addAll(getActionErrors());
			addRequestAttrib = true;
		}
		// do something for Filed Error
		if(getFieldErrors().size() > 0 )
		{
			model.setFieldErrors(getFieldErrors());
			addRequestAttrib = true;
		}

		if(addRequestAttrib)
		{
			ActionContext.getContext().getSession().put(SPNActionConstants.SPN_CAMPAIGNMODEL_IN_REQUEST, model);
		}
		//In case of no errors remoe any request attribute available
		else
		{
			if(ActionContext.getContext().getSession().get(SPNActionConstants.SPN_CAMPAIGNMODEL_IN_REQUEST)  != null)
			{
				ActionContext.getContext().getSession().remove(SPNActionConstants.SPN_CAMPAIGNMODEL_IN_REQUEST);
			}
		}
	}


	private void validateStatesMarketsSelected() {
		if (getModel().getApprovalItems() == null)
			return;

		ApprovalModel approvalModel = getModel().getApprovalItems();
		if( (approvalModel.getSelectedStates() == null || approvalModel.getSelectedStates().size() == 0 )
				&& (!approvalModel.getIsAllStatesSelected().booleanValue() )
			&& (approvalModel.getSelectedMarkets() == null || approvalModel.getSelectedMarkets().size() == 0 )
				&& (!approvalModel.getIsAllMarketsSelected().booleanValue() )	){

			addFieldError("states",this.getText("errors.campaign.marketstate.selection.required"));
		}



	}


	private void validateStartEndCampaignDate() {
		if (getModel() == null) {
			return;
		}

		try{
			Date startDate = CalendarUtil.parseDate(model.getStartDate(), "yyyy-MM-dd");
			Date todayAtMidnight =  CalendarUtil.getTodayAtMidnight();

			if(startDate.before(todayAtMidnight)){
				addFieldError("startDate",  this.getText("errors.campaign.startdate.valid"));
			}

			Date endDate = CalendarUtil.parseDate(model.getEndDate(), "yyyy-MM-dd");
			if(endDate.before(todayAtMidnight)){
				addFieldError("endDate",  this.getText("errors.campaign.enddate.valid"));
			}
		}catch(Exception e){
			return;
		}

	}

	private void validateServicesAndSkillsPanel()
	{
		if (getModel().getApprovalItems() == null)
			return;


		ApprovalModel approvalModel = getModel().getApprovalItems();
		//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
		//Validation to check if Primary Industry check box is checked and no value is selected
		if(null != approvalModel.getIsPrimaryIndustryEnabled() && approvalModel.getIsPrimaryIndustryEnabled() == true ){
			if(null == approvalModel.getSelectedPrimaryIndustry() || approvalModel.getSelectedPrimaryIndustry().size() == 0 ){
				addFieldError("approvalItems.selectedPrimaryIndustry", "Please select Primary Industry");
			}
		}
		if(approvalModel.getSelectedMainServices() != null && approvalModel.getSelectedMainServices().size() == 0)
		{
			addFieldError("approvalItems.selectedMainServices", "Main Services field requires at least one checked item");
		}
		if(approvalModel.getSelectedMainServices() != null && approvalModel.getSelectedSkills().size() == 0)
		{
			addFieldError("approvalItems.selectedSkills", "Skills field requires at least one checked item");
		}

	}



	/**
	 * @return the model
	 */
	public SPNCreateCampaignModel getModel() {

		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(SPNCreateCampaignModel model) {
		this.model = model;
	}


	/**
	 * @param providerMatchService the providerMatchService to set
	 */
	public void setProviderMatchService(
			ProviderMatchForApprovalServices providerMatchService) {
		this.providerMatchService = providerMatchService;
	}


	public ProviderFirmService getProviderFirmService()
	{
		return providerFirmService;
	}


	public void setProviderFirmService(ProviderFirmService providerFirmService)
	{
		this.providerFirmService = providerFirmService;
	}


}
