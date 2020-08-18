package com.servicelive.spn.buyer.auditor.newapplicants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.lookup.LookupSPNDocumentState;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.domain.spn.network.SPNUploadedDocumentState;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.auditor.ServiceProviderService;
import com.servicelive.spn.services.auditor.UploadedDocumentStateService;

/**
 * 
 * 
 *
 */
public class SPNAuditorDocumentsTabAction extends SPNBaseAction
implements ModelDriven<SPNDocumentsTabModel>
{
	private static final long serialVersionUID = 0L;
	private SPNDocumentsTabModel model = new SPNDocumentsTabModel();

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
		initDropdownLists();		
		initDocuments();
		initErrorMessage();
		
		return SUCCESS;
	}

	public SPNDocumentsTabModel getModel()
	{
		return model;
	}
	
	
	
	private void initDocuments() throws Exception
	{
		
		User user = getLoggedInUser();
		if(user == null)
			return;
		
		
		
		LookupSPNWorkflowState state = new LookupSPNWorkflowState();
		state.setId(SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT);

		
		List<SPNDocumentRowDTO> documents = new ArrayList<SPNDocumentRowDTO>();
		
		providerFirmId = getProviderFirmIdFromParameter();
		if(providerFirmId == null)
		{
			getModel().setDocuments(documents);
			return;
		}
		
		networkId = getNetworkIdFromParameter();
		if(networkId == null)
		{
			getModel().setDocuments(documents);			
			return;
		}
		
		
		SPNProviderFirmState firmState =  getProviderFirmStateService().findProviderFirmState(networkId, providerFirmId);
		if(firmState != null)
		{
			providerFirmId = firmState.getProviderFirmStatePk().getProviderFirm().getId();
			
			// Documents
			List<SPNUploadedDocumentState> docStates = uploadedDocumentStateService.findBy(networkId, providerFirmId);
			
			SPNDocumentRowDTO docRow;
			boolean allDocsApproved=true;
			for(SPNUploadedDocumentState docState : docStates)
			{
				docRow = new SPNDocumentRowDTO();
				docRow.setId(docState.getSpnUploadedDocumentStatePk().getProviderFirmDocument().getDocumentId());
				docRow.setTitle(docState.getSpnUploadedDocumentStatePk().getBuyerDocument().getTitle());
				//docRow.setTitle(docState.getSpnUploadedDocumentStatePk().getProviderFirmDocument().getDocumentFileName());
				docRow.setFilename(docState.getSpnUploadedDocumentStatePk().getProviderFirmDocument().getDocumentFileName());
				docRow.setLastAuditorName(docState.getAuditedBy());
				docRow.setLastAuditDate(docState.getAuditedDate());
				docRow.setStatus(docState.getSpnDocumentState().getDescription().toString());
				if(!docState.getSpnDocumentState().getId().equals("DOC APPROVED"))
				{
					allDocsApproved = false;
				}
				docRow.setNumPages(docState.getPageNo());
				docRow.setComments(docState.getComments());
				docRow.setAction((String)docState.getSpnDocumentState().getDescription());
				
				documents.add(docRow);
			}

			// Need to find out Meet&Greet state for 'Send Notification' button logic
			boolean meetApproved = true;
			if(isMeetAndGreetRequiredAndDeclined(networkId, providerFirmId))
			{
				meetApproved = false;
			}
			
			
			getRequest().setAttribute("allDocsApproved", Boolean.valueOf(allDocsApproved));
			getRequest().setAttribute("meetApproved", Boolean.valueOf(meetApproved));
			
		}
		
		if(documents.size() > 0)
		{
			getModel().setDocuments(documents);
		}
		else
		{
			getModel().setDocuments(null);
		}		
		
	}
	
	private void initErrorMessage()
	{
		String errorMessage = getRequest().getParameter("errorMessage");
		if(errorMessage != null && errorMessage != "")
		{
			getModel().setErrorMessage(errorMessage);
		}
		else
		{
			getModel().setErrorMessage(null);
		}
	}
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String buttonSubmitAddAction() throws Exception
	{
		String comments = "";
		int numPagesInt = 0;
		String action = "DOC PENDING APPROVAL";
		LookupSPNDocumentState selectedDocumentState = null;
		
		
		// Get the comments
		if(getRequest().getParameter("comments") != null)
			comments = getRequest().getParameter("comments");
		
		
		// Get # pages
		if(getRequest().getParameter("numPages") != null)
		{
			if(isInteger(getRequest().getParameter("numPages")))
			{
				numPagesInt = Integer.parseInt(getRequest().getParameter("numPages"));				
			}
			else
			{
				numPagesInt = -1;				
			}
		}
		
		// Get Action
		if(getRequest().getParameter("action") != null)
		{
			action = getRequest().getParameter("action");									
			
			List<LookupSPNDocumentState> docStates = lookupService.getActionableSPNDocumentStates();
				
			for(LookupSPNDocumentState docState : docStates)
			{
				if(docState.getId().equals(action))
				{
					selectedDocumentState = docState;
					break;
				}
			}
			
			//action = getRequest().getParameter("action");
		}
		
		String selectedDocStr = getRequest().getParameter("docId");
		Integer selectedDocInt = null;
		if(selectedDocStr != null && selectedDocStr != "")
		{
			selectedDocInt = Integer.valueOf(selectedDocStr);
		}
		

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
			getModel().setNetworkId(networkId + "");
		}
		
		// Get the providerFirmId
		providerFirmId = getProviderFirmIdFromParameter();		
		if(providerFirmId != null)
		{
			setProviderFirmId(providerFirmId);
			getModel().setProviderFirmId(providerFirmId + "");
		}

		if(selectedDocumentState != null)
		{
			if(comments == null || comments == "")
			{
				getModel().setErrorMessage("Please enter comments"); // TODO use ActionError list
			}
		}
		
		// Only update if action is selected and comments were entered		
		if(selectedDocumentState != null && (comments != null && comments != "") && selectedDocInt != null && selectedDocInt.intValue() > 0)
		{
			List<SPNUploadedDocumentState> docStates = uploadedDocumentStateService.findBy(networkId, providerFirmId);
			Integer tmpDocId;
			for(SPNUploadedDocumentState docState : docStates)
			{
				tmpDocId = docState.getSpnUploadedDocumentStatePk().getProviderFirmDocument().getDocumentId();
				if(tmpDocId.intValue() == selectedDocInt.intValue())
				{
					docState.setSpnDocumentState(selectedDocumentState);
					
					docState.setAuditedDate(new Date());
					docState.setAuditedBy(username);
					docState.setComments(comments);
					docState.setModifiedBy(username);
					docState.setModifiedDate(new Date());
					if(numPagesInt > 0) {
						docState.setPageNo(Integer.valueOf(numPagesInt));
					} else {
						docState.setPageNo(null);
					}
					uploadedDocumentStateService.update(docState);
				}
			}
		}
		
		
		return SUCCESS;
	}
	
	
	private void initDropdownLists()
	{
		model = getModel();
		
		// Action dropdowns
		List<LookupSPNDocumentState> actionDropdowns = lookupService.getActionableSPNDocumentStates();
		
		model.setActionList(actionDropdowns);
	}
	
	// Load a document to the browser
	public String loadDocumentAjax() throws Exception
	{
		
		String documentID = getRequest().getParameter("docId");
		if (documentID == null || documentID.equals(""))
			return SUCCESS;

		Integer documentIDInt = Integer.valueOf(documentID);
		
		// This is a generic call that should be used for SPN document loads
		loadDocument(documentIDInt);
		
		return SUCCESS;
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
	 * @param model
	 */
	public void setModel(SPNDocumentsTabModel model)
	{
		this.model = model;
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
