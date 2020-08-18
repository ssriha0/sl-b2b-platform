package com.newco.marketplace.api.mobile.services;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.common.APIRequestVO;
import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.api.mobile.beans.addNotes.AddNoteRequest;
import com.newco.marketplace.api.mobile.beans.addNotes.AddNoteResponse;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestMessageVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.utils.CreditCardValidatonUtil;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;


@APIRequestClass(AddNoteRequest.class)
@APIResponseClass(AddNoteResponse.class)
public class ProviderAddNoteService extends BaseService {


	private static final Logger logger = Logger.getLogger(ProviderAddNoteService.class.getName());
	private IMobileSOActionsBO mobileSOActionsBO;
	private INotificationService notificationService;
	private IBuyerOutBoundNotificationService buyerOutBoundNotificationService;
	private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
	private IAuthenticateUserBO authenticateUserBO;
	private IMobileGenericBO mobileGenericBO;
	private IRelayServiceNotification relayNotificationService;


	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}


	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}

	

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}


	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}


	public ProviderAddNoteService() {
		super();
		/*super(
				PublicMobileAPIConstant.ADD_NOTE_RESQUEST_XSD,
				PublicMobileAPIConstant.ADD_NOTE_RESPONSE_XSD,
				PublicMobileAPIConstant.MOBILE_SERVICES_NAMESPACE,
				PublicMobileAPIConstant.MOBILE_SERVICES_SCHEMA,
				PublicMobileAPIConstant.ADD_NOTE_RESPONSE_SCHEMALOCATION ,
				AddNoteRequest.class,AddNoteResponse.class);*/
	}


	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of SOAddNoteService");
		AddNoteResponse addNoteResponse =new AddNoteResponse();
		Results results=null;
		Integer resourceId = 0;
		AddNoteRequest soAddNoteRequest = (AddNoteRequest) apiVO.getRequestFromPostPut();
		String soId = (String) apiVO.getSOId();
		resourceId = apiVO.getProviderResourceId();

		SecurityContext securityContext = null;

		if(null!= soAddNoteRequest){
			//resourceId = soAddNoteRequest.getProviderId();
			try{
				Integer providerId = validateProviderId(resourceId.toString());
				boolean serviceOrderId = isValidServiceOrder(soId);
				boolean providerForSO = isAuthorizedInViewSODetails(soId,resourceId.toString());

				if(null == providerId){
					Results result= Results.getError(ResultsCode.INVALID_RESOURCE_ID.getMessage(),ResultsCode.INVALID_RESOURCE_ID.getCode());
					addNoteResponse.setResults(result);
					return addNoteResponse;
				}	
				if(!serviceOrderId){
					Results result = Results.getError(ResultsCode.INVALID_SO_ID.getMessage(),ResultsCode.INVALID_SO_ID.getCode());
					addNoteResponse.setResults(result);
					return addNoteResponse;
				}
				
				if (soAddNoteRequest.getNoteType() != null){
					String subject = soAddNoteRequest.getNoteType().getSubject();
					String message = soAddNoteRequest.getNoteType().getNoteBody();
					if (CreditCardValidatonUtil.validateCCNumbers(subject)){
						Results result = Results.getError(ResultsCode.ADD_NOTE_CC_SUBJECT.getMessage(),ResultsCode.ADD_NOTE_CC_SUBJECT.getCode());
						addNoteResponse.setResults(result);
						return addNoteResponse;
					}else if(CreditCardValidatonUtil.validateCCNumbers(message)){
						Results result = Results.getError(ResultsCode.ADD_NOTE_CC_MESSAGE.getMessage(),ResultsCode.ADD_NOTE_CC_MESSAGE.getCode());
						addNoteResponse.setResults(result);
						return addNoteResponse;
					}
				}
				
				results = validateProviderForSO(soId,resourceId,providerId,providerForSO);
				if(null != results){
					addNoteResponse.setResults(results);	
					return addNoteResponse;
				}
				securityContext = getSecurityContextForVendor(resourceId);
				LoginCredentialVO lvRoles = securityContext.getRoles();
				if (securityContext.isSlAdminInd()) {
					lvRoles.setRoleName(PublicMobileAPIConstant.NEWCO_ADMIN);
					lvRoles.setCompanyId(PublicMobileAPIConstant.ENTITY_ID_SERVICELIVE_OPERATION);
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
				String createdBy = lvRoles.getLastName() + ", " +""+ lvRoles.getFirstName();
				String modifiedBy = lvRoles.getUsername();
				Integer entityId = resourceId;
				String privateInd = null;

				if(soAddNoteRequest.getNoteType().isPrivateInd()){
					privateInd = PublicMobileAPIConstant.PRIVATE;
				}else{
					privateInd = PublicMobileAPIConstant.PUBLIC;
				}
				// Note Type is set as 2 for General Notes and 1 for Support
				int noteType ;									
				if (soAddNoteRequest.getNoteType().isSupportInd()){
					noteType = PublicMobileAPIConstant.NOTE_TYPE_SUPPORT_ONE;
					//set privateInd to zero in case of Support note
					privateInd = PublicMobileAPIConstant.PUBLIC;
				} else {
					noteType = PublicMobileAPIConstant.NOTE_TYPE_GENERAL_TWO;				
				}								
				mobileSOActionsBO.insertAddNote(resourceId,lvRoles.getRoleId(),soId,soAddNoteRequest.getNoteType().getSubject(),
						soAddNoteRequest.getNoteType().getNoteBody(),noteType, createdBy, modifiedBy, entityId,privateInd);
				addNoteResponse.setSoId(soId);
				results=Results.getSuccess(ResultsCode.ADD_NOTE_SUCCESS.getMessage());
				addNoteResponse.setResults(results);
				createdBy = lvRoles.getFirstName() +" "+lvRoles.getLastName();
				historyLogging(resourceId,lvRoles.getRoleId(),soId,createdBy, modifiedBy,entityId);
				//Choice web service invocation
				if (StringUtils.isNotBlank(soAddNoteRequest.getNoteType()
						.getSubject().trim().concat(soAddNoteRequest
								.getNoteType().getNoteBody().trim()))) {
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
					}
				}
				//InHome Notification
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
					logger.error("Exception in mobile ProviderAddNoteService inhome notification due to " + e);
				}
				//Relay Buyer Notification for public note
				logger.info("privateInd"+privateInd);
				if( StringUtils.isNotBlank(privateInd) && PublicMobileAPIConstant.PUBLIC.equals(privateInd) ){
				Integer buyerId=buyerOutBoundNotificationService.getBuyerIdForSo(soId);
				boolean	relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId);
					if(relayServicesNotifyFlag){
						relayNotificationService.sentNotificationRelayServices("public_note_added_by_provider",soId);
					}				
				}											
			} 
			catch(Exception ex){
				Results result= Results.getError(ResultsCode.ADD_NOTE_FAILED.getMessage(),ResultsCode.ADD_NOTE_FAILED.getCode());
				addNoteResponse.setResults(result);								
			}	
		}

		else {
			addNoteResponse.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		return addNoteResponse;
	}


	/**
	 * @param soId
	 * @param providerId
	 * @param firmId
	 * @param providerForSO
	 * @return
	 * method to validate provider permission based on role
	 */
	private Results validateProviderForSO(String soId, Integer providerId, Integer firmId, boolean providerForSO) {
		
		 
		Results result = null;
		try{
			Integer resourceRoleLevel = authenticateUserBO.getRoleOfResource(null, providerId);
			if(null!=firmId && null !=providerId){
				SoDetailsVO detailsVO = new SoDetailsVO();
			 detailsVO.setSoId(soId);
			 detailsVO.setFirmId(firmId.toString());
			 detailsVO.setProviderId(providerId);
			 detailsVO.setRoleId(resourceRoleLevel);
			 boolean authSuccess = mobileGenericBO.isAuthorizedToViewBeyondPosted(detailsVO);
			 if(!authSuccess){
				 result = Results.getError(ResultsCode.PERMISSION_ERROR.getMessage(),
							ResultsCode.PERMISSION_ERROR.getCode());	
			 }
			}
			List<Integer> roleIdValues = Arrays.asList(
					PublicMobileAPIConstant.ROLE_LEVEL_ONE,
					PublicMobileAPIConstant.ROLE_LEVEL_TWO,
					PublicMobileAPIConstant.ROLE_LEVEL_THREE);
			if (null == resourceRoleLevel || !roleIdValues.contains(resourceRoleLevel)) {
				result = Results.getError(ResultsCode.INVALID_ROLE.getMessage(),ResultsCode.INVALID_ROLE.getCode());
			}
		}
		catch(Exception e){
			logger.error("Exception inside validateProviderForSO inside Add Note");
			e.printStackTrace();
		}
		
		
		return result;
	}


	private void historyLogging(Integer resourceId, Integer roleId,
			String soId,String createdBy, String modifiedBy, Integer entityId) {
		try{
			Date d=new Date(System.currentTimeMillis());
			Timestamp date = new Timestamp(d.getTime());
			ProviderHistoryVO hisVO=new ProviderHistoryVO();
			hisVO.setSoId(soId);
			hisVO.setActionId(MPConstants.NOTE_ACTION_ID);
			hisVO.setDescription(MPConstants.NOTE_DESCRIPTION);
			hisVO.setCreatedDate(date);
			hisVO.setModifiedDate(date);
			hisVO.setCreatedBy(createdBy);
			hisVO.setRoleId(roleId);
			hisVO.setModifiedBy(modifiedBy);
			hisVO.setEnitityId(entityId);
			mobileSOActionsBO.historyLogging(hisVO);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->historyLogging()");

		}

	}

	public INotificationService getNotificationService() {
		return notificationService;
	}


	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}


	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
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
