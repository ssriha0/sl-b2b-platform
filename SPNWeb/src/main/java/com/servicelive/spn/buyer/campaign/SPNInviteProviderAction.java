package com.servicelive.spn.buyer.campaign;

import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_CAMPAIGN_PENDING;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.spn.campaign.CampaignHeader;
import com.servicelive.domain.spn.campaign.CampaignProviderFirm;
import com.servicelive.domain.spn.campaign.CampaignSPN;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.spn.buyer.common.SPNCreateAction;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.services.buyer.ProviderFirmService;
import com.servicelive.spn.services.interfaces.IProviderFirmStateService;
import com.servicelive.spn.services.providermatch.ProviderMatchForApprovalServices;
import com.servicelive.spn.services.campaign.CampaignInvitationServices;

/**
 * 
 *
 *
 */
public class SPNInviteProviderAction extends SPNCreateAction
	implements Preparable, ModelDriven<SPNCreateCampaignModel>
{

	private static final long serialVersionUID = -2433000435687193753L;
	public static String TMP_PROVIDER_FIRMS = "TMP_PROVIDER_FIRMS_MAP";
	private SPNCreateCampaignModel model = new SPNCreateCampaignModel();
	private ProviderMatchForApprovalServices providerMatchService;
	private ProviderFirmService providerFirmService;
	private IProviderFirmStateService providerFirmStateService;
	private CampaignInvitationServices campaignInvitationServices;
	
	
	
	public CampaignInvitationServices getCampaignInvitationServices() {
		return campaignInvitationServices;
	}

	public void setCampaignInvitationServices(
			CampaignInvitationServices campaignInvitationServices) {
		this.campaignInvitationServices = campaignInvitationServices;
	}

	/**
	 * 
	 * @return String
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public String saveAndDone() throws Exception
	{
		model = getModel();
		
		
		CampaignHeader campaignHeader = getCampaignHdrFromModel();
		
		updateCampaignHdrWithSPNDetails(campaignHeader);
		

		ProviderFirmSearchResultDTO tmpFirm;
		for(String tmpId : getModel().getProviderCheckboxList())
		{
			if(tmpId != null && StringUtils.isNumeric(tmpId))
			{
				Integer providerFirmId = Integer.valueOf(tmpId);
				tmpFirm = new ProviderFirmSearchResultDTO(providerFirmId, null, null);
				tmpFirm.setChecked("true");
				model.getProviderFirmList().add(tmpFirm);			
			}
		}
		
		
		setProviderFirmsOnCampaignHeader(campaignHeader);		
		saveCampaignWithWorkflowStatus(campaignHeader);
		
		
		
		return SUCCESS;
	}

	/**
	 * @param campaignHeader
	 * @throws Exception
	 */
	private void saveCampaignWithWorkflowStatus(CampaignHeader campaignHeader)
			throws Exception {
		createCampaignServices.saveCampaign(campaignHeader);
		Integer campaignId2 = campaignHeader.getCampaignId();// model.getSpnHeader().getSpnId();
		String username = getCurrentBuyerResourceUserName();
		spnWorkflowSerivce.signal(SPNBackendConstants.WF_ENTITY_CAMPAIGN,WF_STATUS_CAMPAIGN_PENDING,campaignId2,username,getListOfCampaignPendingEmails(campaignHeader) );
	}

	/**
	 * @param campaignHeader
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void updateCampaignHdrWithSPNDetails(CampaignHeader campaignHeader)
			throws NumberFormatException, Exception {
		
		model = getModel();
		Integer spnId = null;
		//if(!StringUtils.isBlank(model.getSpnHeader().getSpnId() + ""))
		//Integer spnId = (Integer)getRequest().getSession().getAttribute("campaignSpnId");
		if(model.getSpnHeader().getSpnId() != null  && (!"-1".equals(model.getSpnHeader().getSpnId())) ) {
			spnId = model.getSpnHeader().getSpnId();
		}else if ( StringUtils.isNotBlank(model.getSpnId()) && (!"9999".equals(model.getSpnId())) ){
			spnId = new Integer(model.getSpnId());
		}
		else
		{
			spnId = (Integer)getRequest().getSession().getAttribute("campainSpnId");
		}
		
	
		if(spnId != null && spnId.intValue() != -1)
		{
			List<CampaignSPN> campaignSPNList = new ArrayList<CampaignSPN>();
			CampaignSPN campaignSPN = new CampaignSPN();			
			campaignSPN.setSpn(createSPNServices.getNetwork(spnId));
			campaignSPN.setModifiedBy(this.getCurrentBuyerResourceUserName());
			campaignSPN.setModifiedDate(new Date());
			campaignSPNList.add(campaignSPN);
			campaignHeader.getCampaignSPN().add(campaignSPN);
		}
		
	}

	/**
	 * @return
	 * @throws ParseException
	 */
	private CampaignHeader getCampaignHdrFromModel() throws ParseException {
		CampaignHeader campaignHeader = new CampaignHeader();
		
		model = getModel();
		campaignHeader.setCampaignName(model.getInviteCampaignName());
		campaignHeader.setStartDate(CalendarUtil.parseDate(model.getStartDate2(), "yyyy-MM-dd"));
		campaignHeader.setEndDate(CalendarUtil.parseDate(model.getEndDate2(), "yyyy-MM-dd"));
		campaignHeader.setModifiedBy(this.getCurrentBuyerResourceUserName());
		campaignHeader.setModifiedDate(new Date());
		campaignHeader.setCreatedDate(new Date());
		campaignHeader.setCampaignId(model.getCampaignId());
		return campaignHeader;
	}

	@Override
	public String display()
	{
		return SUCCESS;
	}
	
	@Override
	public void prepare() throws Exception
	{
		// do nothing
	}

	
	public String deleteAllAjax() throws Exception
	{
		getRequest().getSession().setAttribute(TMP_PROVIDER_FIRMS, null);
		getModel().setProviderFirmList(new ArrayList<ProviderFirmSearchResultDTO>());
		return SUCCESS;
	}	
	
	@SuppressWarnings("unchecked")
	@Transactional ( propagation = Propagation.REQUIRED)
	public String loadProviderSearchResultsAjax() throws Exception
	{
		// Get the spn/networkId
		String spnIdStr = getModel().getSpnId();
		Integer spnId = null;
		boolean showSuccessMessage = true;
		if(StringUtils.isNotBlank(spnIdStr) && StringUtils.isNotEmpty(spnIdStr)){
			spnId = Integer.valueOf(spnIdStr);
		} 
				//SL-20063
		if(spnId == null || spnId.intValue() == -1){
			String errorString ="Please select an SPN";
			getRequest().setAttribute("errorString", errorString);
			showSuccessMessage = false;
		}
		
		// Get the ';' separated list of provider IDs to find.
		String providerIdList = getRequest().getParameter("providerIdList");
		providerIdList.replaceAll(" ", "");
		
		//R10.3 SL-19857: START
		//When clicking on ADD button without providing any firm id system should display 
		//Error message like "Please provide at least one firm id"
		
		//scope of this boolean is to show/not show the delete button
		if(StringUtils.isEmpty(providerIdList)){
			//Fix for SL-20156. Code committed by rjose3.
			String errorString ="Please provide at least one Firm Id";
			getRequest().setAttribute("errorString", errorString);
			showSuccessMessage = false;
		}
		//R10.3 SL-19857: END
		
		// The commented code would never be executed
		//if(providerIdList == null)
		//{
		//	getModel().setProviderFirmList((List<ProviderFirmSearchResultDTO>)getRequest().getSession().getAttribute(TMP_PROVIDER_FIRMS));
		//	return SUCCESS;
		//}
		
		
		// Parse the string into individual IDs
		StringTokenizer tokenizer = new StringTokenizer(providerIdList, ";");

		List<ProviderFirmSearchResultDTO> resultList = new ArrayList<ProviderFirmSearchResultDTO>();
		ProviderFirmSearchResultDTO dto;
		ProviderFirm providerFirm = null;
		Integer id = null;
		

		// Need to pass a list of successfully found firms back to front-end.
		// Hashmap is to insure unique entries
		Map<Integer, String> searchMap = new HashMap<Integer, String>();
		
		List<String> failedProviderFirmIds = new ArrayList<String>();
		List<String> duplicateProviderFirmIds = new ArrayList<String>();
		List<LabelValueBean> alreadyInvitedProviderFirmIds = new ArrayList<LabelValueBean>();
		String providerFirmId = null;
		
		// Load any existing firms from session
		if(getRequest().getSession().getAttribute(TMP_PROVIDER_FIRMS) != null)
		{
			resultList = (List<ProviderFirmSearchResultDTO>)getRequest().getSession().getAttribute(TMP_PROVIDER_FIRMS);
			for(ProviderFirmSearchResultDTO tmpDto : resultList)
			{
				if(searchMap.get(tmpDto.getFirmId()) == null)
				{
					searchMap.put(tmpDto.getFirmId(), tmpDto.getFirmId() + "");
				}
			}
		}
		//SL-19857 issue fix for only ';' entered.
		boolean hasSemiColonOnly = true;
		// Create a provider result for each providerFirm found.
		while(tokenizer.hasMoreTokens())
		{
			hasSemiColonOnly = false;
			providerFirmId = tokenizer.nextToken().trim();
			if (providerFirmId.length() == 0)
				continue;
			try {
				id = Integer.valueOf(providerFirmId);
			} catch (Exception e) {
				failedProviderFirmIds.add(providerFirmId);
				continue;
			}

			providerFirm = providerFirmService.getProviderFirmForId(id);
			
			
			
			if (providerFirm != null) {
				if (searchMap.get(providerFirm.getId()) == null)
				{
					// Test for already invited to campaign
					if(spnId != null && spnId.intValue() != -1)
					{
					   SPNProviderFirmState providerFirmState = getProviderFirmStateService().findProviderFirmStateByprimaryKey(spnId, providerFirm.getId());
					   String campaignStatus = campaignInvitationServices.checkProvFirmCampaignStatus(providerFirm.getId(), spnId);
					   if(providerFirmState != null &&(WF_STATUS_PF_SPN_APPLICANT.equals(providerFirmState.getWfState().getId()) 
								|| WF_STATUS_PF_SPN_MEMBER.equals(providerFirmState.getWfState().getId())) || campaignStatus.equals("false"))
						{
							String status = "{status} status";
							
							if(providerFirmState.getProviderFirmStatePk() != null)
							{
								status = (String)providerFirmState.getWfState().getDescription();
							}
							alreadyInvitedProviderFirmIds.add(new LabelValueBean(providerFirm.getId() + "", status));
						}
						else{
							// Create the row of data to be displayed
							dto = new ProviderFirmSearchResultDTO(providerFirm.getId(),
									providerFirm.getBusinessName(), providerFirm
											.getServiceLiveWorkFlowState().getWfState());
							resultList.add(dto);
							// getModel().getProviderFirmList().add(dto);

							// Create an entry for the map, to keep the entries unique
							searchMap.put(providerFirm.getId(), providerFirm.getId()
									+ "");							
						}
						
					}
					
					
				} else // Already found
				{
					duplicateProviderFirmIds.add(providerFirm.getId() + "");
				}
			} else // Send an error message to front-end
			{
				failedProviderFirmIds.add(providerFirmId);
			}
		}
		
		//SL-19857 issue fix for only ';' entered.
		//SL-20059 R10_3 issue fix
		if(!StringUtils.isEmpty(providerIdList) && hasSemiColonOnly){
			String errorString = "No Provider Firms match "+providerIdList+".  Please check the information and try again.";
			showSuccessMessage = false;
			getRequest().setAttribute("errorString", errorString);
		}
		// Create the error string if necessary
		if(failedProviderFirmIds.size() > 0)
		{
			String errorString = "No Provider Firms match ";
			for(int i=0 ; i<failedProviderFirmIds.size() ; i++)
			{
				if(i==0)
				{
					errorString += failedProviderFirmIds.get(i);
				}
				else
				{
					errorString += " or "  + failedProviderFirmIds.get(i);
				}
			}
			errorString +=".  Please check the information and try again.";
			//dont show success message if "No Provider Firms match <Firm id input value>.  Please check the information and try again. " message populates
			//fixed as part of R10.3 SL-19857
			showSuccessMessage = false;
			getRequest().setAttribute("errorString", errorString);
		}

		String duplicateString = null;

		// Create the duplicate warning string if necessary
		if(duplicateProviderFirmIds.size() > 0)
		{
			duplicateString = "ProviderFirm ";
			for(int i=0 ; i<duplicateProviderFirmIds.size() ; i++)
			{
				if(i==0)
				{
					duplicateString += duplicateProviderFirmIds.get(i);
				}
				else
				{
					duplicateString += " and "  + duplicateProviderFirmIds.get(i);
				}
			}
			duplicateString +=" already has/have already been added to the list below.";
		}

		// Already invited warning string
		if(alreadyInvitedProviderFirmIds.size() > 0)
		{
			//dont show success message if "Provider Firm <Firm id> already has a <state> status with this network " message populates
			//fixed as part of R10.3 SL-19857
			showSuccessMessage = false;
			getRequest().setAttribute("alreadyInvitedProviderFirmIds", alreadyInvitedProviderFirmIds);
		}
		
		// R10.3 SL-19857 don't show delete button if showSuccessMessage is false
		if(showSuccessMessage == true){
			getRequest().setAttribute("showDeleteButton", Boolean.TRUE);
		}else{
			getRequest().setAttribute("showDeleteButton", Boolean.FALSE);
		}
		getRequest().setAttribute("duplicateString", duplicateString);
		getRequest().getSession().setAttribute(TMP_PROVIDER_FIRMS, resultList);
		getModel().setProviderFirmList(resultList);


		return SUCCESS;
	}
	
	
	private void setProviderFirmsOnCampaignHeader(CampaignHeader campaignHeader) throws Exception
	{
		model = getModel();
		

		Integer firmId;
		String checked;
		for(ProviderFirmSearchResultDTO dto :  model.getProviderFirmList())
		{
			checked = dto.getChecked();
			if (checked != null && checked.equalsIgnoreCase("true"))
			{
				firmId = dto.getFirmId();
				if(firmId != null && firmId.intValue() > 0)
				{
					CampaignProviderFirm cpf = new CampaignProviderFirm();
					ProviderFirm providerFirm = providerFirmService.getProviderFirmForId(firmId);
					cpf.setProviderFirm(providerFirm);
					cpf.setCampaign(campaignHeader);
					campaignHeader.getProviderFirms().add(cpf);
				}
			}
		}
	}
	
	
	
	public SPNCreateCampaignModel getModel()
	{
		return model;
	}

	public ProviderMatchForApprovalServices getProviderMatchService() {
		return providerMatchService;
	}

	public void setProviderMatchService(
			ProviderMatchForApprovalServices providerMatchService) {
		this.providerMatchService = providerMatchService;
	}

	public ProviderFirmService getProviderFirmService() {
		return providerFirmService;
	}

	public void setProviderFirmService(ProviderFirmService providerFirmService) {
		this.providerFirmService = providerFirmService;
	}

	public void setModel(SPNCreateCampaignModel model) {
		this.model = model;
	}

	@Override
	public IProviderFirmStateService getProviderFirmStateService() {
		return providerFirmStateService;
	}

	@Override
	public void setProviderFirmStateService(
			IProviderFirmStateService providerFirmStateService) {
		this.providerFirmStateService = providerFirmStateService;
	}


	
}
