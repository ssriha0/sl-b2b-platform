/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 03-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addNote.v1_1.SOAddNoteRequest;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOAddNoteMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestMessageVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * This class is a service class for adding notes to a service order
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOAddProviderNoteService extends BaseService{
	private Logger logger = Logger.getLogger(SOAddProviderNoteService.class);
	private IServiceOrderBO serviceOrderBO;
	ProcessResponse processResponse = new ProcessResponse();
	private SOAddNoteMapper addNoteMapper;
	private INotificationService notificationService;
	private IBuyerOutBoundNotificationService buyerOutBoundNotificationService;
	private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
	private IRelayServiceNotification relayNotificationService;



	/**
	 * Constructor
	 */

	public SOAddProviderNoteService() {
		super(PublicAPIConstant.PRO_ADDNOTE_XSD, 
				PublicAPIConstant.SOPROVIDERRESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_PROVIDER_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				PublicAPIConstant.SOPRORESPONSE_SCHEMALOCATION,
				SOAddNoteRequest.class, 
				SOAddNoteResponse.class);
	}
	/**
	 * This method dispatches the add note service order request.
	 * 
	 * @param APIRequestVO apiVO
	 * @return IAPIResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of SOAddProviderNoteService");
		SOAddNoteResponse addNoteResponse =new SOAddNoteResponse();
		Results results=null;
		ArrayList<Object> argumentList = new ArrayList<Object>();
		SOAddNoteRequest soAddNoteRequest = (SOAddNoteRequest) apiVO
				.getRequestFromPostPut();
		String soId = (String) apiVO.getSOId();
		String providerId = (String) apiVO.getProviderId();
		SecurityContext securityContext = null;
		Integer resourceId = 0;
		if(null != soAddNoteRequest && null != soAddNoteRequest.getIdentification()){
			resourceId = soAddNoteRequest.getIdentification().getId();
		}
		try {
			//securityContext = getSecurityContextForVendorAdmin(new Integer(providerId));
			securityContext = getSecurityContextForVendor(resourceId);
		} catch (NumberFormatException nme) {
			logger.error("SOAddNoteService.execute(): Number Format Exception "
					+ "occurred for resourceId:");
		} catch (Exception exception) {
			logger.error("SOAddNoteService.execute(): Exception occurred while "
					+ "accessing security context using resourceId");
		}
		if (securityContext == null) {
			logger.error("SOAddNoteService.execute(): SecurityContext is null");
			results = Results.getError(ResultsCode.INVALID_RESOURCE_ID
					.getMessage(), ResultsCode.INVALID_RESOURCE_ID.getCode());
			addNoteResponse.setResults(results);

		} else {
			// Getting service order details
			try{
				ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
				if (null != serviceOrder) {
					LoginCredentialVO lvRoles = securityContext.getRoles();
					//Integer resourceId = securityContext.getVendBuyerResId();
					if (securityContext.isSlAdminInd()) {
						lvRoles.setRoleName(OrderConstants.NEWCO_ADMIN);
						lvRoles.setCompanyId(
								LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);

						// If the admin-role id is not set, use the role id
						if (securityContext.getAdminRoleId() == -1) {
							resourceId = securityContext.getVendBuyerResId();
							lvRoles.setRoleId(securityContext.getRoleId());
						} else {
							resourceId = securityContext.getAdminResId();
							lvRoles.setRoleId(securityContext.getAdminRoleId());
						}
						lvRoles.setVendBuyerResId(securityContext.getAdminResId());
					}
					String createdBy = lvRoles.getLastName() + ", " +
							""+ lvRoles.getFirstName();
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
						logger.info("Going to invoke serviceOrderBO." +
								"processSupportAddNote()");
						processResponse = serviceOrderBO.processSupportAddNote(
								resourceId,	lvRoles.getRoleId(), soId,
								soAddNoteRequest.getNoteType().getSubject(),
								soAddNoteRequest.getNoteType().getNoteBody(),
								noteType, createdBy, modifiedBy, entityId,
								privateInd,	isEmailToBeSent, securityContext);
					} else {
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
					addNoteResponse = addNoteMapper.mapAddNoteResponse(processResponse);
					//For InHome outbound notification
					if(null != processResponse && ServiceConstants.VALID_RC.equalsIgnoreCase(processResponse.getCode())){
						Integer buyerId=buyerOutBoundNotificationService.getBuyerIdForSo(soId);
						//This will check buyerId of service order and insert into notification table
							if(PublicAPIConstant.SEARS_BUYER.equals(buyerId)){
								//Public Note check
								try{
									if(privateInd == PublicAPIConstant.ZERO){
										  RequestMessageVO  soNote=new RequestMessageVO();
								    	  soNote.setSoId(soId);
								    	  soNote.setServiceOrderTxtDS(soAddNoteRequest.getNoteType().getSubject().trim()+" "+
								    			  soAddNoteRequest.getNoteType().getNoteBody().trim());
								    	  RequestMsgBody requestMsgBody= buyerOutBoundNotificationService.getNPSNotificationRequestForNotes(soNote);
								    	   if(null!=requestMsgBody){
								    	          BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody,soId);
								    	          if(null!=failoverVO){
								    	          buyerOutBoundNotificationJMSService.callJMSService(failoverVO);}
								    	   }
									}
								}catch (Exception e) {
									logger.error("Exception in SOAddNoteService.executeOrderFulfillmentService() while adding note for choice due to " + e);
								}
							}else{
						InHomeSODetailsVO inHomeSODetails = new InHomeSODetailsVO();
						inHomeSODetails.setSoId(soId);
						try{
							InHomeSODetailsVO result = notificationService.getSoDetailsForNotes(inHomeSODetails);
							if(null != result){
								//check for notification flag & inactive ind
								boolean isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(result.getBuyerId(),soId);
								if(isEligibleForNPSNotification){
									inHomeSODetails.setNoteTypeId(Integer.parseInt(privateInd));
									inHomeSODetails.setRoleId(lvRoles.getRoleId());
									inHomeSODetails.setSubjMessage(soAddNoteRequest.getNoteType().getSubject() + 
											InHomeNPSConstants.SEPERATOR + soAddNoteRequest.getNoteType().getNoteBody());
									inHomeSODetails.setCreatedBy(createdBy);
									inHomeSODetails.setEmpId(result.getVendorId());
									
									notificationService.insertNotification(inHomeSODetails);
								}
							}
						}catch (Exception e) {
							logger.error("Exception in SOAddNoteService.executeOrderFulfillmentService() due to " + e);
						}
						
						
						//Relay Buyer Notification for public note
						logger.info("privateInd"+privateInd);
						if( StringUtils.isNotBlank(privateInd) && PublicAPIConstant.ZERO.equals(privateInd) ){
						boolean	relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId);
							if(relayServicesNotifyFlag){
								relayNotificationService.sentNotificationRelayServices("public_note_added_by_provider",soId);
							}				
						}	
						
						
					}
				  }
				} else {
					logger.info("No Service Order exists for this soId");
					argumentList.add("(" + PublicAPIConstant.SO_ID + ")");
					results = Results.getError(	ResultsCode.INVALID_OR_MISSING_PARAM
							.getMessage(argumentList),
							ResultsCode.INVALID_OR_MISSING_PARAM.getCode());
					addNoteResponse.setResults(results);
				}
			}
			catch(Exception ex){
				logger.error("SOAddNoteService-->execute()--> Exception-->"
						+ ex.getMessage(), ex);
				results = Results.getError(ex.getMessage(),
						ResultsCode.GENERIC_ERROR.getCode());
				addNoteResponse.setResults(results);
			}

		}
		logger.info("Leaving execute method of SOAddProviderNoteService");
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
	public IBuyerOutBoundNotificationService getBuyerOutBoundNotificationService() {
		return buyerOutBoundNotificationService;
	}
	public void setBuyerOutBoundNotificationService(
			IBuyerOutBoundNotificationService buyerOutBoundNotificationService) {
		this.buyerOutBoundNotificationService = buyerOutBoundNotificationService;
	}
	public IBuyerOutBoundNotificationJMSService getBuyerOutBoundNotificationJMSService() {
		return buyerOutBoundNotificationJMSService;
	}
	public void setBuyerOutBoundNotificationJMSService(
			IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService) {
		this.buyerOutBoundNotificationJMSService = buyerOutBoundNotificationJMSService;
	}
	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}
	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
	

}