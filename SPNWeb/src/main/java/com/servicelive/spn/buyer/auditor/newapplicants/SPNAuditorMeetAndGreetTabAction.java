package com.servicelive.spn.buyer.auditor.newapplicants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.domain.lookup.LookupSPNMeetAndGreetState;
import com.servicelive.domain.spn.network.SPNMeetAndGreetState;
import com.servicelive.domain.spn.network.SPNMeetAndGreetStatePk;
import com.servicelive.domain.spn.network.SPNUploadedDocumentState;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.auditor.ServiceProviderService;
import com.servicelive.spn.services.auditor.UploadedDocumentStateService;

/**
 * 
 * 
 *
 */
public class SPNAuditorMeetAndGreetTabAction extends SPNBaseAction
implements ModelDriven<SPNMeetAndGreetTabModel>
{

	private static final long serialVersionUID = 0L;
	private SPNMeetAndGreetTabModel model = new SPNMeetAndGreetTabModel();

	private ServiceProviderService serviceProviderService;
	private UploadedDocumentStateService uploadedDocumentStateService;
	private LookupService lookupService;
	
	private Integer networkId;
	private Integer providerFirmId;
	
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String viewTabAjax() throws Exception
	{
		
		initData();
		
		
		return SUCCESS;
	}
	
	private void initData()
	{
		initMeetAndGreetData();
		initDropdowns();
	}

	private void initMeetAndGreetData()
	{
		// Set model with empty list as default
		getModel().setMeetList(null);
		
		// Need to get 3 parameters for Service call.  If any of them fail, just return.
		Integer buyerId=null;
		//Integer networkId=null;
		//Integer providerFirmId=null;
		
		User user = getLoggedInUser();
		if(user == null)
		{
			return;
		}
		buyerId = user.getUserId();
		
		networkId = getNetworkIdFromParameter();
		if(networkId == null)
			return;
		
		providerFirmId = getProviderFirmIdFromParameter();
		if(providerFirmId == null)
		{
			return;
		}
		
		// Last sanity check.
		if(buyerId == null || networkId == null || providerFirmId == null)
		{
			return;
		}
		
		// Need to find out if all docs are approved for 'Send Notification' button logic
		List<SPNUploadedDocumentState> docStates = uploadedDocumentStateService.findBy(networkId, providerFirmId);		
		boolean allDocsApproved=true;
		for(SPNUploadedDocumentState docState : docStates)
		{
			if(!docState.getSpnDocumentState().getId().equals("DOC APPROVED"))
			{
				allDocsApproved = false;
				break;
			}
		}
		
		
		List<SPNMeetAndGreetState> meetGreetStates = this.getMeetGreetStates(buyerId, networkId, providerFirmId);		
		boolean meetApproved = true;		
		// Convert raw state data to DTO, if there is at least one meetngreet state.
		if(meetGreetStates != null)
		{
			SPNMeetGreetRowDTO dto;
			
			if(isMeetAndGreetRequiredAndDeclined(networkId, providerFirmId))
			{
				meetApproved = false;
			}
			
			if(getModel().getMeetList() == null)
			{
				getModel().setMeetList(new ArrayList<SPNMeetGreetRowDTO>());
			}
			
			// The first state might be uninitialized. That state is a placeholder
			// for the front end, so that it can display a row with a message saying there
			// is meetngreet for the current network.
			for(SPNMeetAndGreetState state : meetGreetStates)
			{
				if(state == null)
					continue;

				dto = new SPNMeetGreetRowDTO();			
				
				if(state.getMeetAndGreetStatePk() != null)
				{				
					dto.setNetworkId(state.getMeetAndGreetStatePk().getSpnHeader().getSpnId() + "");
					dto.setComments(state.getComments());
					dto.setDate(state.getMeetAndGreetDate());
					dto.setNetworkTitle(state.getMeetAndGreetStatePk().getSpnHeader().getSpnName());
					dto.setName(state.getMeetAndGreetPerson());
					dto.setStatus(state.getState().getDescription());
				}
								
				getModel().getMeetList().add(dto);
			}
	
			// Want to represent no meetngreet as null
			if(getModel().getMeetList() != null && getModel().getMeetList().size() == 0)
			{
				getModel().setMeetList(null);
			}
			
			getRequest().setAttribute("meetApproved", Boolean.valueOf(meetApproved));
			getRequest().setAttribute("allDocsApproved", Boolean.valueOf(allDocsApproved));
			
		}
		
		
	}	
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String buttonSubmitAddAction() throws Exception
	{
		
		
		// Get the comments
		String comments = "";		
		if(getRequest().getParameter("comments") != null)
		{
			comments = getRequest().getParameter("comments");
		}
		
		// Get Date
		if(getRequest().getParameter("date") != null)
		{
			if(isInteger(getRequest().getParameter("date")))
			{
				//FIXME This is probably not meant to be blank
			}
		}
		
		// Get Action
		LookupSPNMeetAndGreetState selectedMeetAndGreetState = null;		
		if(getRequest().getParameter("action") != null)
		{
			String action = getRequest().getParameter("action");									
			
			List<LookupSPNMeetAndGreetState> meetAndGreetStates = lookupService.getAllSPNMeetAndGreetStates();
				
			for(LookupSPNMeetAndGreetState state : meetAndGreetStates)
			{
				if(state.getId().equals(action))
				{
					selectedMeetAndGreetState = state;
					break;
				}
			}			
		}
		
		String name = getRequest().getParameter("name");
		
		String dateStr = getRequest().getParameter("date");
		//date maybe null
		Date date = CalendarUtil.parseDate(dateStr, "yyyy-MM-dd");

		// Get username.  Used for 'modified  by' column.
		String username ="";
		User user = getLoggedInUser();
		if(user != null)
		{
			username = user.getUsername();
		}		
		
		// Get the networkId/spnId
		networkId = getNetworkIdFromParameter();
		if(networkId != null)
		{
			setNetworkId(networkId);
			getModel().setNetworkId(String.valueOf(networkId));
		}
		
		// Get the providerFirmId
		providerFirmId = getProviderFirmIdFromParameter();		
		if(providerFirmId != null)
		{
			getModel().setProviderFirmId(String.valueOf(providerFirmId));
		}

		if(selectedMeetAndGreetState != null)
		{
			if(comments == null || comments == "")
			{
				getModel().setErrorMessage("Please enter a comment when you enter a new Action"); // TODO use ActionError list
			}
		}
		
		// UPDATE MEET AND GREET HERE.
		
		if(selectedMeetAndGreetState != null)
		{
			SPNMeetAndGreetState meetAndGreetState =  getMeetAndGreetStateService().find(networkId, providerFirmId);
			if(meetAndGreetState == null) {
				meetAndGreetState = new SPNMeetAndGreetState();
				meetAndGreetState.setMeetAndGreetStatePk(new SPNMeetAndGreetStatePk(networkId, providerFirmId));
			}
			meetAndGreetState.setComments(comments);
			meetAndGreetState.setModifiedBy(username);
			meetAndGreetState.setModifiedDate(new Date());
			meetAndGreetState.setState(selectedMeetAndGreetState);
			meetAndGreetState.setMeetAndGreetPerson(name);
			meetAndGreetState.setMeetAndGreetDate(date);
			
			getMeetAndGreetStateService().update(meetAndGreetState);
		}
		
		
		return SUCCESS;
	}
	
	
	
	
	private void initDropdowns()
	{
		
		List<LookupSPNMeetAndGreetState> meetAndGreetStates = lookupService.getAllSPNMeetAndGreetStates();
		
		getModel().setActionList(meetAndGreetStates);
	}
	
	
	
	

	public SPNMeetAndGreetTabModel getModel()
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
	 * @return Integer
	 */
	public Integer getNetworkId()
	{
		return networkId;
	}
	/**
	 * 
	 * @param networkId
	 */
	public void setNetworkId(Integer networkId)
	{
		this.networkId = networkId;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getProviderFirmId()
	{
		return providerFirmId;
	}
	/**
	 * 
	 * @param providerFirmId
	 */
	public void setProviderFirmId(Integer providerFirmId)
	{
		this.providerFirmId = providerFirmId;
	}

		
}
