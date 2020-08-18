/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 22-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.beans.so.addNote.v1_1.SOAddNoteRequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOAddNoteMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class is a service class for adding notes to a service order
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOAddNoteService extends SOBaseService{
	private Logger logger = Logger.getLogger(SOAddNoteService.class);
	private IServiceOrderBO serviceOrderBO;
	ProcessResponse processResponse = new ProcessResponse();
	private SOAddNoteMapper addNoteMapper;
	private INotificationService notificationService;
	
	//to identify whether note is added
	private boolean successFlag = false;

	
	/**
	 * Constructor
	 */

	public SOAddNoteService() {
		super(PublicAPIConstant.ADDNOTE_XSD, 
			PublicAPIConstant.SORESPONSE_XSD,
			  PublicAPIConstant.SORESPONSE_NAMESPACE,
			  PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
			  PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
			  SOAddNoteRequest.class, 
			  SOAddNoteResponse.class);
		
	}

	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		logger.info("Entering executeOrderFulfillmentService method of SOAddNoteService");
		
		/**
		 * The code change has been made under 
		 * JIRA Ticket No.- SL-17573
		 */
		
		String reqType=null;
		SecurityContext securityContext=null;

		String soId = apiVO.getSOId();
		SOAddNoteRequest request = (SOAddNoteRequest) apiVO.getRequestFromPostPut();

		reqType= request.getIdentification().getType();
		
		if(reqType.equals(PublicAPIConstant.BUYER_RESOURCE_ID)){
			Integer buyerResourceId =null;
			if(apiVO.getBuyerResourceId()!=null){
				buyerResourceId = apiVO.getBuyerResourceId();
			}
			else{
				buyerResourceId = request.getIdentification().getId();
			}
			securityContext = getSecurityContextForBuyer(buyerResourceId);

		}else{
			securityContext = getSecCtxtForBuyerAdmin(apiVO.getBuyerIdInteger());
		}
		
		SONote note = createNote(request, securityContext);
		OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.ADD_NOTE, note, OFMapper.createBuyerIdentFromSecCtx(securityContext));
		
		SOAddNoteResponse noteResponse = createResponse(response);
		//For InHome outbound notification
		if(successFlag){
			InHomeSODetailsVO inHomeSODetails = new InHomeSODetailsVO();
			inHomeSODetails.setSoId(soId);
			try{
				//check for notification flag & inactive ind
				boolean isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(apiVO.getBuyerIdInteger(),soId);
				if(isEligibleForNPSNotification){
					
					InHomeSODetailsVO result = notificationService.getSoDetailsForNotes(inHomeSODetails);
					inHomeSODetails.setNoteTypeId(note.isPrivate()? 1 : 0);
					inHomeSODetails.setRoleId(note.getRoleId());
					inHomeSODetails.setSubjMessage(note.getSubject() + InHomeNPSConstants.SEPERATOR + note.getNote());
					inHomeSODetails.setCreatedBy(note.getCreatedByName());
					inHomeSODetails.setEmpId(result.getVendorId());
					
					notificationService.insertNotification(inHomeSODetails);
				}
				
			}catch (Exception e) {
				logger.error("Exception in SOAddNoteService.executeOrderFulfillmentService() due to " + e);
			}
		}
		logger.info("Leaving executeOrderFulfillmentService");
		return noteResponse;
	}
	
	private SOAddNoteResponse createResponse(OrderFulfillmentResponse response) {
		logger.info("Entering createResponse method of SOAddNoteService");
		SOAddNoteResponse addNoteResponse = new SOAddNoteResponse();
		Results results = new Results();
		if (response.isError()) {
			logger.info("AddNote operation failed. Setting result and message as Failure");
			results= Results.getError(response.getErrorMessage(), ResultsCode.FAILURE.getCode());
		}else{
			results=Results.getSuccess(ResultsCode.NOTE_ADDED.getMessage());
			successFlag = true;
		}
		addNoteResponse.setResults(results);
		logger.info("Leaving createResponse");
		return addNoteResponse;
	}

	private SONote createNote(SOAddNoteRequest request, SecurityContext security){
		
		SONote note = new SONote();
		note.setSubject(request.getNoteType().getSubject());
		note.setNote(request.getNoteType().getNoteBody());
		if(request.getNoteType().isSupportInd()){ //Buyer is requesting support!
			note.setNoteTypeId(PublicAPIConstant.NOTE_TYPE_SUPPORT_ONE);
		} else { //actual buyer is creating the note
			note.setNoteTypeId(PublicAPIConstant.NOTE_TYPE_GENERAL_TWO);
			note.setSendEmail(true);
		}
        note.setCreatedByName(security.getRoles().getLastName() + ", "+ 
        		security.getRoles().getFirstName());
        note.setEntityId(security.getVendBuyerResId().longValue());
        note.setRoleId(security.getRoleId());
        note.setPrivate(request.getNoteType().isPrivateInd());

		note.setModifiedBy(security.getUsername());
		return note;
	}
	/**
	 * This method dispatches the add note service order request.
	 * 
	 * @param APIRequestVO apiVO
	 * @return IAPIResponse
	 */
	public IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		logger.info("Entering execute method of SOAddNoteService");
		SOAddNoteResponse addNoteResponse =new SOAddNoteResponse();
		Results results=null;
		ArrayList<Object> argumentList = new ArrayList<Object>();
		SOAddNoteRequest soAddNoteRequest = (SOAddNoteRequest) apiVO
													.getRequestFromPostPut();
		String soId = apiVO.getSOId();
		Integer buyerId = apiVO.getBuyerIdInteger();
		SecurityContext securityContext = null;
		try {
			securityContext = getSecurityContextForBuyerAdmin(buyerId);
		} catch (NumberFormatException nme) {
			logger.error("SOAddNoteService.execute(): Number Format Exception "
					+ "occurred for Buyer Id:"+buyerId);
		} catch (Exception exception) {
			logger.error("SOAddNoteService.execute(): Exception occurred while "
					+ "accessing security context using Buyer Id:"+buyerId);
		}
		
		try{
			LoginCredentialVO lvRoles = securityContext.getRoles();
			Integer  resourceId =new Integer( soAddNoteRequest.getIdentification().getId());
			if (securityContext.isSlAdminInd()) {
				lvRoles.setRoleName(OrderConstants.NEWCO_ADMIN);
				lvRoles.setCompanyId(
						LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);

				// If the admin-role id is not set, use the role id
				if (securityContext.getAdminRoleId() == -1) {
					lvRoles.setRoleId(securityContext.getRoleId());
				} else {
					resourceId = securityContext.getAdminResId();
					lvRoles.setRoleId(securityContext.getAdminRoleId());
				}
				lvRoles.setVendBuyerResId(securityContext.getAdminResId());
			}
			String createdBy = lvRoles.getLastName() + ", "+ 
			lvRoles.getFirstName();
			String modifiedBy = lvRoles.getUsername();
			// Resource Id is set as Entity Id
			Integer entityId = resourceId;
			String privateInd = null;

			boolean isEmptyNoteAllowed = false;
			boolean isEmailToBeSent = true;
			if (soAddNoteRequest.getNoteType().isPrivateInd()) {
				privateInd = PublicAPIConstant.ONE;
				isEmailToBeSent = false;
			} else {
				privateInd = PublicAPIConstant.ZERO;}
			// Note Type is set as 2 for General Notes and 1 for Support
			int noteType ;
			if (soAddNoteRequest.getNoteType().isSupportInd()) {
				noteType = PublicAPIConstant.NOTE_TYPE_SUPPORT_ONE;
				//set privateInd to zero in case of Support note
				privateInd = PublicAPIConstant.ZERO;
				processResponse = serviceOrderBO.processSupportAddNote(
						resourceId,	lvRoles.getRoleId(), soId,
						soAddNoteRequest.getNoteType().getSubject(),
						soAddNoteRequest.getNoteType().getNoteBody(),
						noteType, createdBy, modifiedBy, entityId, 
						privateInd,	isEmailToBeSent, securityContext);
			} 
			else {
				noteType = PublicAPIConstant.NOTE_TYPE_GENERAL_TWO;				
				logger.info("Going to invoke serviceOrderBO." +
				"processAddNote()");
				processResponse = serviceOrderBO.processAddNote(resourceId,
						lvRoles.getRoleId(), soId, soAddNoteRequest
						.getNoteType().getSubject(),
						soAddNoteRequest.getNoteType().getNoteBody(),
						noteType, createdBy, modifiedBy, entityId,
						privateInd,	isEmailToBeSent, isEmptyNoteAllowed,
						securityContext);
			}
			addNoteResponse = addNoteMapper.mapAddNoteResponse(
					processResponse);
			
			}
			catch(Exception ex){
				logger.error("SOAddNoteService-->execute()--> Exception-->"
						+ ex.getMessage(), ex);
				results = Results.getError(ex.getMessage(),
						ResultsCode.GENERIC_ERROR.getCode());
				addNoteResponse.setResults(results);
			}
				
			
		
		logger.info("Leaving execute method of SOAddNoteService");
		return addNoteResponse;
	}
	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setAddNoteMapper(SOAddNoteMapper addNoteMapper) {
		this.addNoteMapper = addNoteMapper;
	}

	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

}
