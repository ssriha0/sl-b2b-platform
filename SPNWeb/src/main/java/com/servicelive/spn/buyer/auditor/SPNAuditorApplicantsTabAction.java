package com.servicelive.spn.buyer.auditor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.spn.detached.SPNAuditorSearchExpandCriteriaVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNMeetAndGreetState;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.domain.spn.network.SPNUploadedDocumentState;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.auditor.vo.DocumentExpirationDetailsVO;
import com.servicelive.spn.buyer.auditor.newapplicants.SPNNewApplicantsTabModel;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.auditor.ServiceProviderService;
import com.servicelive.spn.services.auditor.UploadedDocumentStateService;
import com.servicelive.spn.services.buyer.SPNBuyerServices;
import com.servicelive.spn.services.workflow.StateFlowService;

/**
 * 
 */
public class SPNAuditorApplicantsTabAction extends SPNBaseAction
implements ModelDriven<SPNNewApplicantsTabModel>
{

	private static final long serialVersionUID = -737871852810790504L;

	private SPNNewApplicantsTabModel model = new SPNNewApplicantsTabModel();
	
	
	private ServiceProviderService serviceProviderService;
	private UploadedDocumentStateService uploadedDocumentStateService;
	private LookupService lookupService;
	private StateFlowService stateFlowService;
	protected SPNBuyerServices spnBuyerServices;
	
	// Use these 3 data members to pass parameters to Struts action
	private Integer networkId;
	private Integer providerFirmId;
	private String errorMessage;
	private String errorMessageDoc;
	private String errorMessageMG;
	
	private static final String PROVIDER_SPN = "providerSPN";
	private static final String REQUIREMENT_TYPES = "requirementTypes";
	private static final String EXPIRATION_DETAILS = "expirationDetails";
	
	String auditorTab;
	
	public String getAuditorTab() {
		return auditorTab;
	}

	public void setAuditorTab(String auditorTab) {
		this.auditorTab = auditorTab;
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String displayNewApplicant() throws Exception
	{
		
		initData(SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT);		
		

		getRequest().setAttribute("tab", "newapplicants");
		
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String displayReApplicant() throws Exception
	{
		
		initData(SPNBackendConstants.WF_STATUS_PF_SPN_REAPPLICANT);
		
		getRequest().setAttribute("tab", "reapplicants");
		
		return SUCCESS;
	}
	
	// Code Added for Jira SL-19384
	// To display information in new tab 'Membership Under Review'

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String displayMembershipUnderReview() throws Exception
	{
		
		initData(SPNBackendConstants.WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW);
		
		getRequest().setAttribute("tab", "membership");
		
		return SUCCESS;
	}

	private void initData(String wfState) throws Exception
	{
		
		User user = getLoggedInUser();
		if(user == null){
			return;
		}

		Integer buyerId = user.getUserId();
		
		loadSpnList();
		
		
		// Counts
		initApplicantCounts();
		
		// See if somebody has passed a networkId and providerFirmId
		// to use for loading up a specific Applcant.
		networkId = getNetworkIdFromParameter();
		providerFirmId = getProviderFirmIdFromParameter();		
		if(networkId != null && providerFirmId != null)
		{
			initApplicantById(networkId, providerFirmId);
			
			String errorMessage2 = getRequest().getParameter("errorMessage");
			if(errorMessage2 != null) {
				addActionMessage(errorMessage2);
			}
		}
		else
		{
			// Pick the first Provider Applicant		
			initFirstAvailableApplicant(buyerId, user.getUsername(), wfState);
			networkId = getModel().getNetworkId();
			providerFirmId = getModel().getProviderFirmId();
		}
		
		// Handle the display of the 'Release and Get Next' link
		// Do not display link if there are no applicants left in the queue		
		handleReleasGetNextLink(wfState);		
		
		// Set attributes that determine if 'Send Notification' button is shown
		initApprovedAttributes(networkId, providerFirmId);				
	}
	
	
	private void loadSpnList()
	{
		User user = getLoggedInUser();

		Integer buyerId = user.getUserId();
				
		try
		{
			List<SPNHeader> spnList = spnBuyerServices.getSPNListForBuyer(buyerId);
			getModel().setSpnList(spnList);
		}
		catch (Exception e)
		{
			logger.error("Error finding spn list.", e);
		}
		
	}
	

	/**
	 * 
	 * @param buyerId
	 * @param reviewedByUserName
	 * @param wfState
	 * @throws Exception
	 */
	public void initFirstAvailableApplicant(Integer buyerId, String reviewedByUserName, String wfState) throws Exception
	{
		LookupSPNWorkflowState state = new LookupSPNWorkflowState();
		state.setId(wfState);
		SPNProviderFirmState firmState = null;
		
		wfState.trim();
		
		// Check the appropriate applicant count.  If there is at least one in the queue
		// grab that applicant for display and decrement the count.
		if (wfState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT))
		{
			if (getModel().getNewApplicantsCount() > 0)
			{
				firmState = getProviderFirmStateService().getAndLockSPNProviderFirmState(buyerId, state, reviewedByUserName);				
				if (firmState != null)
				{
					getModel().setNewApplicantsCount(getModel().getNewApplicantsCount() - 1);
				}
			}
		}
		else if (wfState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_REAPPLICANT))
		{
			if (getModel().getReApplicantsCount() > 0)
			{
				firmState = getProviderFirmStateService().getAndLockSPNProviderFirmState(buyerId, state, reviewedByUserName);
				if (firmState != null)
				{
					getModel().setReApplicantsCount(getModel().getReApplicantsCount() - 1);
				}
			}
		}
		// Code Added for Jira SL-19384
		// For new tab 'Membership Under Review'
		else if (wfState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW))
		{
			if (getModel().getMembershipUnderReviewCount() > 0)
			{
				firmState = getProviderFirmStateService().getAndLockSPNProviderFirmState(buyerId, state, reviewedByUserName);
				if (firmState != null)
				{
					getModel().setMembershipUnderReviewCount(getModel().getMembershipUnderReviewCount() - 1);
				}
			}
		}


		// No applicant in appropriate queue, do not initialize anything else.
		if(firmState == null){
			return;
		}	
			
		Integer providerFirmId2 = firmState.getProviderFirmStatePk().getProviderFirm().getId();		
		//FIXME there might be multiple admins for a providerfirm
		ServiceProvider serviceProvider = serviceProviderService.findAdmin(providerFirmId2).iterator().next(); 
		
		model = initApplicantModel(firmState, getModel(), serviceProvider);

		Integer networkId2 = firmState.getProviderFirmStatePk().getSpnHeader().getSpnId();
		getRequest().setAttribute("networkId", networkId2);

		initDisplayTabAttributes(buyerId, networkId2, providerFirmId2);
		
		
		
	}

	
	private void handleReleasGetNextLink(String wfState)
	{
		
		if(wfState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT))
		{
			if(getModel().getNewApplicantsCount() > 0)
			{
				getRequest().setAttribute("showReleaseGetNextLink", Boolean.TRUE);
				getRequest().setAttribute("releaseGetNextAction", "spnAuditorApplicantsTab_displayNewApplicant.action");
			}
		}
		else if(wfState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_REAPPLICANT))
		{
			if(getModel().getReApplicantsCount() > 0)
			{
				getRequest().setAttribute("showReleaseGetNextLink", Boolean.TRUE);
				getRequest().setAttribute("releaseGetNextAction", "spnAuditorApplicantsTab_displayReApplicant.action");
			}			
		}
		// Code Added for Jira SL-19384
		// For new tab 'Membership Under Review'
		else if(wfState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW))
		{
			if(getModel().getMembershipUnderReviewCount() > 0)
			{
				getRequest().setAttribute("showReleaseGetNextLink", Boolean.TRUE);
				getRequest().setAttribute("releaseGetNextAction", "spnAuditorApplicantsTab_displayMembershipUnderReview.action");
			}			
		}
		else
		{			
			logger.error("initFirstAvailableApplicant() - Unknown workflow state parameter: '" + wfState + "'");
		}		
	}
	
	private void initDisplayTabAttributes(Integer buyerId, Integer networkId2, Integer providerFirmId2)
	{
		// Determine if we should show the 'Meet & Greet' tab at all.
		List<SPNMeetAndGreetState>  meetGreetStates = this.getMeetGreetStates(buyerId, networkId2, providerFirmId2);
		if(meetGreetStates != null && meetGreetStates.size() > 0 )
		{
			getRequest().setAttribute("displayMeetAndGreetTab", Boolean.TRUE);
		}
		
		// Determine if we should show the 'Documents' tab at all.
		List<SPNUploadedDocumentState> docStates = uploadedDocumentStateService.findBy(networkId2, providerFirmId2);
		if(docStates != null && docStates.size() > 0 )
		{
			getRequest().setAttribute("displayDocumentsTab", Boolean.TRUE);
		}		
	}
	
	
	/**
	 * 
	 * @return String
	 */
	public String getProviderFirmSPNLockAjax()  
	{
		User user = getLoggedInUser();
		if(user == null){
			return SUCCESS;
		}
		
		try {
			SPNAuditorSearchExpandCriteriaVO expandCriteriaVO = getModel().getExpandCriteriaVO();
			Date origModDate = CalendarUtil.parseDate(expandCriteriaVO.getOriginalModifiedDate(), "yyyy-MM-dd HH:mm:ss");
			
			SPNProviderFirmState spnPFState = getProviderFirmStateService().getAndLockSPNProviderFirmState(expandCriteriaVO.getSpnId(), expandCriteriaVO.getProviderFirmId(), user.getUsername(), origModDate);
			if (spnPFState == null)
			{
				getModel().getExpandCriteriaVO().setLockedRecord(Boolean.TRUE);
			}
			else
			{
				getModel().getExpandCriteriaVO().setLockedRecord(Boolean.FALSE);
				getModel().getExpandCriteriaVO().setOrginalModifiedDate_Date(spnPFState.getModifiedDate());
				SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
				getModel().getExpandCriteriaVO().setOriginalModifiedDate(dateFormatter.format(spnPFState.getModifiedDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	/**
	 * 
	 * @return String
	 */
	public String unlockProviderFirmSPNAjax()
	{
		SPNAuditorSearchExpandCriteriaVO expandCriteriaVO = getModel().getExpandCriteriaVO();
		try {
			SPNProviderFirmState spnProviderFirmState = getProviderFirmStateService().unlock(expandCriteriaVO.getSpnId(), expandCriteriaVO.getProviderFirmId());
			expandCriteriaVO.setOrginalModifiedDate_Date(spnProviderFirmState.getModifiedDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String displayApplicantPanelAjax() throws Exception 
	{
		SPNAuditorSearchExpandCriteriaVO expandCriteriaVO = getModel().getExpandCriteriaVO();
		initApplicantById(expandCriteriaVO.getSpnId(), expandCriteriaVO.getProviderFirmId());
		
		return SUCCESS;
	}

	/**fetches the document expiration details of a provider
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String displayExpirationPanelAjax() throws Exception 
	{
		SPNAuditorSearchExpandCriteriaVO expandCriteriaVO = getModel().getExpandCriteriaVO();
		Integer providerFirmId = null;
		Integer networkId = null;
		Integer spnId = null;
		Integer requirementType = null;
		if(null != expandCriteriaVO){
			providerFirmId = expandCriteriaVO.getProviderFirmId();
			networkId = expandCriteriaVO.getNetworkId();
			spnId = expandCriteriaVO.getSpnId();
			requirementType = expandCriteriaVO.getRequirementType(); 
		}	
		Integer buyerId = getCurrentBuyerId();
		
		ServiceProvider serviceProvider = serviceProviderService.findAdmin(providerFirmId).iterator().next();
		SPNProviderFirmState firmState = getProviderFirmStateService().findProviderFirmState(networkId, providerFirmId);
		SPNNewApplicantsTabModel model = getModel();
		if(null != serviceProvider){
			model.setBusinessName(serviceProvider.getProviderFirm().getBusinessName());
		}		
		model.setFirmID(providerFirmId + "");
		if(null != firmState){
			model.setNetworkName(firmState.getProviderFirmStatePk().getSpnHeader().getSpnName());
		}		
		model.setNetworkId(networkId);
		
		try{
			//fetches the list of spns of the provider for which notifications are send
			Map<String,Integer> providerSPN = serviceProviderService.getSPNForProvider(providerFirmId, buyerId, requirementType); 
			getRequest().setAttribute(PROVIDER_SPN,providerSPN);
			
			//fetches the list of credentials for which notifications are send
			Map<String,Integer> requirementTypes = serviceProviderService.getRequirementType(providerFirmId, buyerId, spnId);
			getRequest().setAttribute(REQUIREMENT_TYPES,requirementTypes);
			
			//fetches the details of expiration notifications
			List<DocumentExpirationDetailsVO> expirationDetails = serviceProviderService.getExpirationDetailsForProvider(providerFirmId, buyerId, spnId, requirementType);
			getRequest().setAttribute(EXPIRATION_DETAILS,expirationDetails);
			
		}catch(BusinessServiceException e){
			logger.error("Exception in SPNAuditorApplicantsTabAction displayExpirationPanelAjax() due to "+ e.getMessage());
		}		
		
		return SUCCESS;
	}

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @throws Exception
	 */
	public void initApplicantById(Integer spnId, Integer providerFirmId2) throws Exception
	{
		SPNProviderFirmState firmState = getProviderFirmStateService().findProviderFirmState(spnId, providerFirmId2);
		//FIXME there might be multiple admins for a providerfirm
		ServiceProvider serviceProvider = serviceProviderService.findAdmin(providerFirmId2).iterator().next();
	
		// Call method to show/hide 'Documents' and 'Meet & Greet' tab.
		User user = getLoggedInUser();
		Integer buyerId = user.getUserId();		
		initDisplayTabAttributes(buyerId, spnId, providerFirmId2);
		
		model = initApplicantModel(firmState, getModel(), serviceProvider);
		
		// Set attributes that determine if 'Send Notification' button is shown
		initApprovedAttributes(spnId, providerFirmId2);
		
		
	}
	
	// Set attributes that determine if 'Send Notification' button is shown
	// TODO - write a more concise call to get just the document states without all the extra stuff.
	private void initApprovedAttributes(Integer networkId2, Integer providerFirmId2)
	{
		
		boolean meetGreetApproved = true;
		boolean allDocsApproved = true;
		
		if(networkId2 != null && providerFirmId2 != null)
		{			
			// See if all docs are approved
			List<SPNUploadedDocumentState> docStates = uploadedDocumentStateService.findBy(networkId2, providerFirmId2);
			for(SPNUploadedDocumentState docState : docStates)
			{
				if(!docState.getSpnDocumentState().getId().equals("DOC APPROVED"))
				{
					allDocsApproved = false;
					break;
				}
			}

			// See if the Meet&Greet has been approved
			SPNMeetAndGreetState mgState = getMeetAndGreetStateService().find(networkId2, providerFirmId2);
			if(mgState != null && !mgState.getState().getId().equals("MEET APPROVED"))
			{
				meetGreetApproved = false;
			}
						
		}
		
		// If either documents or M&G is not all approved, show the 'send notification' button.
		getRequest().setAttribute("allDocsApproved", Boolean.valueOf(allDocsApproved));
		getRequest().setAttribute("meetApproved", Boolean.valueOf(meetGreetApproved));
		
		
	}
	
	private void initApplicantCounts()
	{
		User user = getLoggedInUser();
		if(user == null){
			return;
		}

		Integer buyerId = user.getUserId();
		
		SPNApplicantCounts counts = getApplicantCounts(buyerId);
		if(counts != null)
		{
			getModel().setNewApplicantsCount(counts.getNewApplicantsCount());
			getModel().setReApplicantsCount(counts.getReApplicantsCount());
			// Code Added for Jira SL-19384
			// For new tab 'Membership Under Review'
			getModel().setMembershipUnderReviewCount(counts.getMembershipUnderReviewCount());
		}
				
	}
	
	
	public String buttonSendNotificationAjax() throws Exception
	{
		networkId = getNetworkIdFromParameter();
		providerFirmId = getProviderFirmIdFromParameter();
		String modifiedBy = null;

		User user = getLoggedInUser();
		if(user != null) {
			modifiedBy = user.getUsername();
		}
		
		if(networkId != null && providerFirmId != null && modifiedBy != null)
		{
			String comment = null;
			
			String auditorTab = getRequest().getParameter("auditorTab");
			if("membership".equals(auditorTab)){
				this.auditorTab = "membership";
			}
			
			stateFlowService.signalSendNotification(networkId, providerFirmId, modifiedBy, comment);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String buttonAcceptAjax() throws Exception
	{
		networkId = getNetworkIdFromParameter();
		providerFirmId = getProviderFirmIdFromParameter();
		String modifiedBy = null;

		User user = getLoggedInUser();
		if(user != null) {
			modifiedBy = user.getUsername();
		}

		if(networkId == null || providerFirmId == null || modifiedBy == null){
			return "success";
		}
		
		// Check the meet&greet state for this spn, which is the first one returned from the method below
		boolean foundErrors = false;
		
		
		boolean isRequired = this.isMeetAndGreetRequiredForNetwork(networkId);
		
		if(isRequired)
		{
			SPNMeetAndGreetState tmpState = this.findMeetAndGreet(networkId, providerFirmId);
					
			if(tmpState == null)
			{
				foundErrors = true;
				errorMessage = "Meet & Greet must be approved before this Applicant can be Approved";
				errorMessageMG = "Meet & Greet must be approved before this Applicant can be Approved";
			}
			else
			{
				if(tmpState.getMeetAndGreetStatePk().getSpnHeader().getSpnId().intValue() == networkId.intValue())
				{
					// TODO make "MEET APPROVED" and "MEET NOT REQUIRED" constant
					if(!tmpState.getState().getId().equals("MEET APPROVED") && !tmpState.getState().getId().equals("MEET NOT REQUIRED"))
					{
						foundErrors = true;
						errorMessage = "Meet & Greet must be approved before this Applicant can be Approved";
						errorMessageMG = "Meet & Greet must be approved before this Applicant can be Approved";
					}
				}
			}
		}
		
		
		
		// Test Documents and Meet&Greet Info all approved here
		List<SPNUploadedDocumentState> docStates = uploadedDocumentStateService.findBy(networkId, providerFirmId);
		for(SPNUploadedDocumentState doc : docStates)
		{
			if(!doc.getSpnDocumentState().getId().equals(SPNBackendConstants.DOC_STATE_APPROVED))
			{
				foundErrors = true;
				errorMessage = "All documents must be 'Approved' before this Applicant can be Approved.";
				errorMessageDoc = "All documents must be 'Approved' before this Applicant can be Approved.";
			}
		}
		
		if(foundErrors) // at least one doc or the Meet&Greet failed
		{
			if (model.getExpandCriteriaVO() != null && model.getExpandCriteriaVO().getFromSearch().intValue() == 1)
			{
				/// send an ajax error back to search
				return "error_search_page";
			}

			SPNProviderFirmState firmState = getProviderFirmStateService().findProviderFirmState(networkId, providerFirmId);
			if(firmState != null)
			{
				if(firmState.getWfState().getId().equals(SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT))
				{
					return "error_newapplicant";
				}
				else if(firmState.getWfState().getId().equals(SPNBackendConstants.WF_STATUS_PF_SPN_REAPPLICANT))
				{
					return "error_reapplicant";
				}
				else if(firmState.getWfState().getId().equals(SPNBackendConstants.WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW))
				{
					return "error_membership";
				}
			}
		}
		String auditorTab = getRequest().getParameter("auditorTab");
		if("membership".equals(auditorTab)){
			this.auditorTab = "membership";
		}
		
		//If we get this far, then we can actually mark the Applicant as approved.
		stateFlowService.signalApproval(networkId, providerFirmId, modifiedBy);
		
		if (model.getExpandCriteriaVO() != null && model.getExpandCriteriaVO().getFromSearch().intValue() == 1)
		{
			return "error_search_page";
		}
		return "success";		
	}
	
	// Code Added for Jira SL-19384
	// Action method for new button 'Membership Under Review'
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String buttonMembershipUnderReviewAjax() throws Exception
	{
		networkId = getNetworkIdFromParameter();
		providerFirmId = getProviderFirmIdFromParameter();
		String modifiedBy = null;

		User user = getLoggedInUser();
		if(user != null) {
			modifiedBy = user.getUsername();
		}

		if(networkId == null || providerFirmId == null || modifiedBy == null){
			return "success";
		}
		
		stateFlowService.signalMemberShipUnderReview(networkId, providerFirmId, modifiedBy);
		
		if (model.getExpandCriteriaVO() != null && model.getExpandCriteriaVO().getFromSearch().intValue() == 1)
		{
			return "error_search_page";
		}
		return "success";		
	}
	
	
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String buttonDeclineAjax() throws Exception
	{
		Integer networkId2 = getNetworkIdFromParameter();
		Integer providerFirmId2 = getProviderFirmIdFromParameter();
		String modifiedBy = null;
		String comment = getRequest().getParameter("comment");

		User user = getLoggedInUser();
		if(user != null){
			modifiedBy = user.getUsername();
		}
		
		if(networkId2 == null || providerFirmId2 == null || modifiedBy == null || comment == null) {
			return "success";
		}
		
		String auditorTab = getRequest().getParameter("auditorTab");
		if("membership".equals(auditorTab)){
			this.auditorTab = "membership";
		}
		// Make the backend/DB changes with the next line.
		stateFlowService.signalDeclined(networkId2, providerFirmId2, modifiedBy, comment);
		return "success";
	}
	


	public SPNNewApplicantsTabModel getModel()
	{
		return model;
	}







	/**
	 * 
	 * @return ServiceProviderService
	 */
	public ServiceProviderService getServiceProviderService()
	{
		return serviceProviderService;
	}


	/**
	 * 
	 * @param serviceProviderService
	 */
	public void setServiceProviderService(ServiceProviderService serviceProviderService)
	{
		this.serviceProviderService = serviceProviderService;
	}


	/**
	 * 
	 * @return UploadedDocumentStateService
	 */
	public UploadedDocumentStateService getUploadedDocumentStateService()
	{
		return uploadedDocumentStateService;
	}

	/**
	 * 
	 * @param uploadedDocumentStateService
	 */
	public void setUploadedDocumentStateService(UploadedDocumentStateService uploadedDocumentStateService)
	{
		this.uploadedDocumentStateService = uploadedDocumentStateService;
	}


	/**
	 * 
	 * @return LookupService
	 */
	public LookupService getLookupService()
	{
		return lookupService;
	}


	/**
	 * 
	 * @param lookupService
	 */
	public void setLookupService(LookupService lookupService)
	{
		this.lookupService = lookupService;
	}

	/**
	 * 
	 * @return StateFlowService
	 */
	public StateFlowService getStateFlowService()
	{
		return stateFlowService;
	}

	/**
	 * 
	 * @param stateFlowService
	 */
	public void setStateFlowService(StateFlowService stateFlowService)
	{
		this.stateFlowService = stateFlowService;
	}

	/**
	 * 
	 * @return SPNBuyerServices
	 */
	public SPNBuyerServices getSpnBuyerServices()
	{
		return spnBuyerServices;
	}
	/**
	 * 
	 * @param spnBuyerServices
	 */
	public void setSpnBuyerServices(SPNBuyerServices spnBuyerServices)
	{
		this.spnBuyerServices = spnBuyerServices;
	}

	/**
	 * @return the networkId
	 */
	public Integer getNetworkId()
	{
		return networkId;
	}

	/**
	 * @param networkId the networkId to set
	 */
	public void setNetworkId(Integer networkId)
	{
		this.networkId = networkId;
	}

	/**
	 * @return the providerFirmId
	 */
	public Integer getProviderFirmId()
	{
		return providerFirmId;
	}

	/**
	 * @param providerFirmId the providerFirmId to set
	 */
	public void setProviderFirmId(Integer providerFirmId)
	{
		this.providerFirmId = providerFirmId;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getErrorMessageDoc() {
		return errorMessageDoc;
	}

	public void setErrorMessageDoc(String errorMessageDoc) {
		this.errorMessageDoc = errorMessageDoc;
	}

	public String getErrorMessageMG() {
		return errorMessageMG;
	}

	public void setErrorMessageMG(String errorMessageMG) {
		this.errorMessageMG = errorMessageMG;
	}

	
}
