package com.newco.marketplace.api.services.leadsdetailmanagement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.EmailAlertToType;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteRequest;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.APIValidatationException;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.servicelive.common.properties.IApplicationProperties;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.leadsmanagement.LeadManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
	
public class AddNotesForLeadService extends SOBaseService{
	private Logger logger = Logger.getLogger(AddNotesForLeadService.class);
	private ILeadProcessingBO leadProcessingBO;	
    private IApplicationProperties applicationProperties;

	private LeadManagementMapper leadManagementMapper;
	public ILeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(ILeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	public LeadManagementMapper getLeadManagementMapper() {
		return leadManagementMapper;
	}

	public void setLeadManagementMapper(LeadManagementMapper leadManagementMapper) {
		this.leadManagementMapper = leadManagementMapper;
	}

	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	boolean errorOccured = false;
	
	/**
	 * Constructor
	 */

	public AddNotesForLeadService() {
		super(PublicAPIConstant.LEAD_ADDNOTE_REQUEST_XSD, 
			PublicAPIConstant.LEAD_ADDNOTE_RESPONSE_XSD,
			  PublicAPIConstant.LEAD_ADDNOTE_RESPONSE_NAMESPACE,
			  PublicAPIConstant.LEAD_RESOURCES_SCHEMAS_V1_0,
			  PublicAPIConstant.LEAD_ADDNOTE_RESPONSE_SCHEMALOCATION,
			  LeadAddNoteRequest.class, 
			  LeadAddNoteResponse.class);
		this.errorOccured = false;
		
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of AddNotesForLeadService");
		LeadAddNoteResponse leadAddNoteResponse =new LeadAddNoteResponse();
		LeadAddNoteRequest leadAddNoteRequest = (LeadAddNoteRequest) apiVO
													.getRequestFromPostPut();
		Integer vendorBuyerId = null;
		vendorBuyerId = leadAddNoteRequest.getVendorBuyerId();
		Integer vendorBuyerResourceId = null;
		vendorBuyerResourceId = leadAddNoteRequest.getVendorBuyerResourceId();
		String leadId = leadAddNoteRequest.getLeadId();
		if (errorOccured) {
			errorOccured = false;
		}
		
		//va;idate the request parameters
		leadAddNoteResponse = validate(leadAddNoteRequest, leadAddNoteResponse);

		if (errorOccured) {
			return leadAddNoteResponse;
		}
		
		if(leadAddNoteRequest.getLeadNote()==null){
			leadAddNoteResponse.setResults(Results.getError(ResultsCode.EMPTY_LEAD_NOTES.getMessage(), 
					ResultsCode.EMPTY_LEAD_NOTES.getCode()));
			errorOccured = true;
			return leadAddNoteResponse;
		}
		else if(StringUtils.isEmpty(leadAddNoteRequest.getLeadNote().getNoteCategory())){
			leadAddNoteResponse.setResults(Results.getError(ResultsCode.EMPTY_LEAD_NOTE_CATEGORY.getMessage(), 
					ResultsCode.EMPTY_LEAD_NOTE_CATEGORY.getCode()));
			errorOccured = true;
			return leadAddNoteResponse;
		}
		
		else if(StringUtils.isEmpty(leadAddNoteRequest.getLeadNote().getNoteBody())){
			leadAddNoteResponse.setResults(Results.getError(ResultsCode.EMPTY_LEAD_NOTE_BODY.getMessage(), 
					ResultsCode.EMPTY_LEAD_NOTE_BODY.getCode()));
			errorOccured = true;
			return leadAddNoteResponse;
		}
		
		SecurityContext securityContext = null;
		try {
			if(("Provider").equals(leadAddNoteRequest.getRole())){
				securityContext = getSecurityContextForVendor(vendorBuyerResourceId);
			}
			else{
				securityContext = getSecurityContextForBuyer(vendorBuyerResourceId);
			}
		} catch (NumberFormatException nme) {
			logger.error("AddNotesForLeadService.execute(): Number Format Exception "
					+ "occurred for Firm Id:"+vendorBuyerId);
		} catch (Exception exception) {
			logger.error("AddNotesForLeadService.execute(): Exception occurred while "
					+ "accessing security context using Firm Id:"+vendorBuyerId);
		}
		/*LoginCredentialVO lvRoles = securityContext.getRoles();
		Integer  resourceId =new Integer( leadAddNoteRequest.getIdentification().getId());
		String createdBy = lvRoles.getLastName() + ", "+ 
		lvRoles.getFirstName();
		String modifiedBy = lvRoles.getUsername();
			*/
		//map the request to VO
		SLLeadNotesVO leadNotesVO = leadManagementMapper.mapSLLeadAddNoteRequest(leadAddNoteRequest,securityContext);
		
		try{
			if(leadNotesVO != null){
				String emailToAddresses ="";
				if(("Buyer").equals(leadAddNoteRequest.getRole()) && (leadAddNoteRequest.getEmailAlert()!=null && leadAddNoteRequest.getEmailAlert().getEmailAlertInd())){
					if(leadAddNoteRequest.getEmailAlert()!=null && leadAddNoteRequest.getEmailAlert().getEmailAlertTos()!=null){
						String alertToType = leadAddNoteRequest.getEmailAlert().getEmailAlertTos();
						if((NewServiceConstants.SL_SUPPORT).equals(alertToType)){
							try {
								emailToAddresses = (String)applicationProperties.getPropertyFromDB(NewServiceConstants.SL_SUPPORT_MAIL_ID);
							} catch (Exception e) {
								logger.info("Exception in getting spn Id from Application properties");
							}		
						}
						if((leadAddNoteRequest.getLeadNote().getNoteType().equals("PUBLIC"))&&((NewServiceConstants.ALL_PROVIDERS).equals(alertToType))){
							List<String> emailIds = new ArrayList<String>();
							try {
								emailIds = leadProcessingBO.getProviderEmailIdsForLead(leadAddNoteRequest.getLeadId());
							} catch (BusinessServiceException e) {
								logger.info("Exception in sendConfirmationMailToCustomer.getProviderEmailIdsForLead in sendConfirmationMailforAddNotes due to "+e.getMessage());

							}
							if(null!=emailIds){
								for(String emailId : emailIds){
									if(StringUtils.isNotEmpty(emailId)){
										emailToAddresses = emailId;
									}
								}
							}
						}
						if((leadAddNoteRequest.getLeadNote().getNoteType().equals("PUBLIC"))&&((NewServiceConstants.ADD_NOTE_ALERT_BOTH).equals(alertToType))){
							List<String> emailIds = new ArrayList<String>();
							try {
								emailToAddresses = (String)applicationProperties.getPropertyFromDB(NewServiceConstants.SL_SUPPORT_MAIL_ID);
								emailIds = leadProcessingBO.getProviderEmailIdsForLead(leadAddNoteRequest.getLeadId());
								if(null!=emailIds){
									for(String emailId : emailIds){
										if(StringUtils.isNotEmpty(emailId)){
											emailToAddresses = emailToAddresses+";"+emailId;
										}
									}
								}
							} catch (BusinessServiceException e) {
								logger.info("Exception in sendConfirmationMailToCustomer.getProviderEmailIdsForLead in sendConfirmationMailforAddNotes due to "+e.getMessage());
							}
						}
					}

				}
				if(StringUtils.isNotBlank(emailToAddresses)){
					leadNotesVO.setAlertSendTo(emailToAddresses);
				}
				Integer leadNoteId =null;
				if(leadAddNoteRequest.getLeadNoteId()!=null && leadNotesVO.getRoleId().intValue()==1){
					leadNoteId=leadAddNoteRequest.getLeadNoteId();
					leadNotesVO.setLeadNoteId(leadAddNoteRequest.getLeadNoteId());
					leadProcessingBO.updateLeadNotes(leadNotesVO);
					leadAddNoteResponse.setResults(Results.getSuccess("Notes is updated successfully"));
}
				else{
					leadNoteId = leadProcessingBO.saveLeadNotes(leadNotesVO);
					leadAddNoteResponse.setResults(Results.getSuccess("Notes is saved successfully"));
				}
				
				leadAddNoteResponse.setLeadNoteId(leadNoteId);
				if(StringUtils.isNotBlank(emailToAddresses)){
					//Changes for R14_0 B2C BLM/PLM
		            //all reference to the lms_lead_id is removed
		            //Instead the sl_lead_id will be used for validation
					//String lmsLeadId=leadProcessingBO.validateLeadId(leadId);
					String lmsLeadId=leadProcessingBO.validateSlleadForPost(leadId);
					//Changes for BLM/PLM--END
					leadAddNoteRequest.setLmsLeadId(lmsLeadId);
					leadProcessingBO.sendConfirmationMailforAddNotes(leadAddNoteRequest,emailToAddresses);
				}
			}
		}
		catch (Exception exception) {
			logger.error("AddNotesForLeadService.execute(): Exception occurred while "
					+ "saving notes");
			leadAddNoteResponse.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			return leadAddNoteResponse;
		}
		leadAddNoteResponse.setLeadId(leadId);

		logger.info("Leaving execute method of AddNotesForLeadService");
		return leadAddNoteResponse;
	}

	private Boolean validateLeadNoteCategoryId(String noteCategory) throws BusinessServiceException {
		Boolean isValidCategory = false;
		isValidCategory = leadProcessingBO.validateNoteCategory(noteCategory);
		return isValidCategory;
	}
	
	private Boolean validateLeadNoteIdForLeadId(Integer noteId,String leadId, String role, Integer firmId) throws BusinessServiceException {
		Boolean isValidCategory = false;
		Integer roles =null;
		if(("Provider").equals(role)){
			roles = 1;
		}
		else{
			roles=3;
		}

		isValidCategory = leadProcessingBO.validateLeadNoteIdForLeadId(noteId,leadId,roles,firmId);
		return isValidCategory;
	}

	private LeadAddNoteResponse validate(LeadAddNoteRequest leadAddNoteRequest,
			LeadAddNoteResponse leadAddNoteResponse) {

		boolean firmValid= false;
		boolean leadValid=false;
		//for validating firm id for given lead
		boolean match=false;
		try{
			if(null!=leadAddNoteRequest.getVendorBuyerId()){
				//for validating firm id for given lead
				if(("Provider").equals(leadAddNoteRequest.getRole())){
					String firm = leadAddNoteRequest.getVendorBuyerId().toString();	
					//Changes for BLM/PLM--START
					//firmValid= leadProcessingBO.validateFirmId(firm);
					String firmIdReturned= leadProcessingBO.toValidateFirmId(firm);
					if (!StringUtils.isBlank(firmIdReturned) && firmIdReturned.equals(firm)) {
						firmValid = true;
					}
					//Changes for BLM/PLM--END
					if(!firmValid){
						leadAddNoteResponse.setResults(Results.getError(ResultsCode.FIRM_NOT_FOUND.getMessage(), 
								ResultsCode.FIRM_NOT_FOUND.getCode()));
						errorOccured = true;
						return leadAddNoteResponse;
					}
					if(!validateProviderResource(leadAddNoteRequest)){
						leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_PROVIDER_RESOURCE_ID.getMessage(), 
								ResultsCode.INVALID_PROVIDER_RESOURCE_ID.getCode()));
						errorOccured = true;
						return leadAddNoteResponse;
					}
					if(!validateProviderLink(leadAddNoteRequest)){
						leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_PROVIDER_RESOURCE_ID.getMessage(), 
								ResultsCode.INVALID_PROVIDER_RESOURCE_ID.getCode()));
						errorOccured = true;
						return leadAddNoteResponse;
					}
					
				}
				else{
						String buyer = leadAddNoteRequest.getVendorBuyerId().toString();	
						Map<String, String> reqMap = new HashMap<String, String>();
						reqMap.put(PublicAPIConstant.BUYER_ID,buyer);
						reqMap.put(PublicAPIConstant.SL_LEAD_ID,leadAddNoteRequest.getLeadId());
						firmValid= leadProcessingBO.validateBuyerForLead(reqMap);
						if(!firmValid){
							leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_BUYER.getMessage(), 
									ResultsCode.INVALID_BUYER.getCode()));
							errorOccured = true;
							return leadAddNoteResponse;
						}
						if(!validateBuyerLink(leadAddNoteRequest)){
							leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_BUYER_RESOURCE_ID.getMessage(), 
									ResultsCode.INVALID_BUYER_RESOURCE_ID.getCode()));
							errorOccured = true;
							return leadAddNoteResponse;
						}
				}
			}
			else{
				if(("Provider").equals(leadAddNoteRequest.getRole())){
					leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_VENDOR.getMessage(), 
						ResultsCode.INVALID_VENDOR.getCode()));
				errorOccured = true;
				}
				else{
					leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_BUYER_ID.getMessage(), 
							ResultsCode.INVALID_BUYER_ID.getCode()));
					errorOccured = true;
				}
				return leadAddNoteResponse;
			}
			if(!StringUtils.isBlank(leadAddNoteRequest.getLeadId())){
				if(leadProcessingBO.validateSlLeadId(leadAddNoteRequest.getLeadId())){
					leadValid = true;
				}
				if(!leadValid){
					leadAddNoteResponse.setResults(Results.getError(ResultsCode.LEAD_NOT_FOUND.getMessage(), 
							ResultsCode.LEAD_NOT_FOUND.getCode()));
					errorOccured = true;
					return leadAddNoteResponse;
				}
			}
			else{
				leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_LEAD_ID.getMessage(), 
						ResultsCode.INVALID_LEAD_ID.getCode()));
				errorOccured = true;
				return leadAddNoteResponse;
			}
			Map<String, String> reqMap = new HashMap<String, String>();
			if(("Provider").equals(leadAddNoteRequest.getRole())){
				String leadStatus = leadProcessingBO.validateSlLeadIdAndStatus(leadAddNoteRequest.getLeadId());
					if(("unmatched").equals(leadStatus)){
						leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_LEAD_STATE.getMessage(), 
								ResultsCode.INVALID_LEAD_STATE.getCode()));
						errorOccured = true;
						return leadAddNoteResponse;
													
				}
				else{
					String firm = leadAddNoteRequest.getVendorBuyerId().toString();
					reqMap.put(PublicAPIConstant.FIRM_ID,firm);
					reqMap.put(PublicAPIConstant.SL_LEAD_ID,leadAddNoteRequest.getLeadId());
					match=leadProcessingBO.validateFirmForLead(reqMap);
					if(!match){
					leadAddNoteResponse.setResults(Results.getError(ResultsCode.UNMATCHED1.getMessage(), 
							ResultsCode.UNMATCHED1.getCode()));
					errorOccured = true;
					return leadAddNoteResponse;	
					}

				}
			}
			if(("Provider").equals(leadAddNoteRequest.getRole())&& leadAddNoteRequest.getLeadNoteId()!=null && !validateLeadNoteIdForLeadId(leadAddNoteRequest.getLeadNoteId(),leadAddNoteRequest.getLeadId(),leadAddNoteRequest.getRole(),leadAddNoteRequest.getVendorBuyerId())){
				leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_LEAD_NOTE_ID.getMessage(), 
						ResultsCode.INVALID_LEAD_NOTE_ID.getCode()));
				errorOccured = true;
				return leadAddNoteResponse;
			}
			if(("Buyer").equals(leadAddNoteRequest.getRole()) && leadAddNoteRequest.getLeadNote()!=null && !validateLeadNoteCategoryId(leadAddNoteRequest.getLeadNote().getNoteCategory())){
				leadAddNoteResponse.setResults(Results.getError(ResultsCode.INVALID_LEAD_NOTE_CATEGORY.getMessage(), 
						ResultsCode.INVALID_LEAD_NOTE_CATEGORY.getCode()));
				errorOccured = true;
				return leadAddNoteResponse;
			}
			
			
			
		}
		catch(Exception e){
			logger.error("AddNotesForLeadService validate exception detail: " + e.getMessage());
			leadAddNoteResponse.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			errorOccured = true;
		}
		return leadAddNoteResponse;

	}
	private boolean validateProviderResource(
			LeadAddNoteRequest leadAddNoteRequest) {
		SecurityContext securityContext = BaseService.getSecurityContextForVendor(leadAddNoteRequest.getVendorBuyerResourceId());
		if(securityContext == null){
			return false;
		}
		return true;

	}
	private boolean validateBuyerResource(
			LeadAddNoteRequest leadAddNoteRequest) {
		SecurityContext securityContext = BaseService.getSecurityContextForBuyer(leadAddNoteRequest.getVendorBuyerResourceId());
		if(securityContext == null){
			return false;
		}
		return true;

	}

	private boolean validateProviderLink(LeadAddNoteRequest leadAddNoteRequest){
		Integer receivedProviderResourceId = leadAddNoteRequest.getVendorBuyerResourceId();
		SecurityContext securityContext = BaseService.getSecurityContextForVendor(receivedProviderResourceId);
		Integer vendorId = securityContext.getCompanyId();	
		Integer receivedProviderId =  leadAddNoteRequest.getVendorBuyerId();
		
		if (receivedProviderId.intValue() != vendorId.intValue()) {
			return false;
		}		
		return true;
	}
	
	private boolean validateBuyerLink(LeadAddNoteRequest leadAddNoteRequest){
		Integer receivedBuyerResourceId = leadAddNoteRequest.getVendorBuyerResourceId();
		SecurityContext securityContext = BaseService.getSecurityContextForBuyer(receivedBuyerResourceId);
		if(securityContext == null){
			return false;
		}
		Integer buyerId = securityContext.getCompanyId();	
		Integer receivedBuyerId =  leadAddNoteRequest.getVendorBuyerId();
		
		if (receivedBuyerId.intValue() != buyerId.intValue()) {
				return false;
			}		
		return true;
	}
	
	/*private SONote createNote(SOAddNoteRequest request, SecurityContext security){
		
		SONote note = new SONote();
		note.setSubject(request.getLeadNote().getSubject());
		note.setNote(request.getLeadNote().getNoteBody());
		if(request.getLeadNote().isSupportInd()){ //Buyer is requesting support!
			note.setNoteTypeId(PublicAPIConstant.NOTE_TYPE_SUPPORT_ONE);
		} else { //actual buyer is creating the note
			note.setNoteTypeId(PublicAPIConstant.NOTE_TYPE_GENERAL_TWO);
			note.setSendEmail(true);
		}
        note.setCreatedByName(security.getRoles().getLastName() + ", "+ 
        		security.getRoles().getFirstName());
        note.setEntityId(security.getVendBuyerResId().longValue());
        note.setRoleId(security.getRoleId());
        note.setPrivate(request.getLeadNote().isPrivateInd());

		note.setModifiedBy(security.getUsername());
		return note;
	}*/
	/**
	 * This method dispatches the add note service order request.
	 * 
	 * @param APIRequestVO apiVO
	 * @return IAPIResponse
	 */
	public IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		return null;
	}
	

	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}

}
