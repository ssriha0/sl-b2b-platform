package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;

import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.ReassignSORequest;
import com.newco.marketplace.api.beans.so.ReassignSOResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import org.apache.commons.lang.StringUtils;

/**
 * @author ndixit
 *
 */
@Namespace("http://www.servicelive.com/namespaces/reassignso")
@APIRequestClass(ReassignSORequest.class)
@APIResponseClass(ReassignSOResponse.class)
public class SOReassignService extends BaseService{
	private Logger logger = Logger.getLogger(SOReassignService.class);
	private IServiceOrderBO serviceOrderBO;
	
	public SOReassignService(){
		super.addMoreClass(Contacts.class);
		super.addMoreClass(Contact.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		SoLoggingVo soLoggingVO = new SoLoggingVo();
		ServiceOrderNote soNote = new ServiceOrderNote();
		ReassignSORequest request  = (ReassignSORequest) apiVO.getRequestFromPostPut();
		Integer providerId = apiVO.getProviderIdInteger();
		String soId = apiVO.getSOId();
		SecurityContext securityContext = null;
		Integer loggedInResource =  0;
		if(null != request && null != request.getIdentification()){
			loggedInResource = request.getIdentification().getId();
		}
		securityContext = getSecurityContextForVendor(loggedInResource);
		Results results = null;
		Contact soContact = null;
		Contact soCurrentContact = null;
		String reassignReason = request.getReassignComment();
		Integer newResourceId=request.getResourceId();
		ProcessResponse success = null;
		try{
			Integer companyId = (Integer) securityContext.getCompanyId();
			ArrayList<Contact> routedContactList = serviceOrderBO.getRoutedResources(soId,companyId.toString());
			
			ServiceOrder serviceOrder=serviceOrderBO.getServiceOrder(soId);
			List<RoutedProvider> routedResources=serviceOrder.getRoutedResources();
			Integer resourceId=serviceOrder.getAcceptedResourceId();
			boolean isAllowed=false;
			if(routedContactList!=null && newResourceId!=null)
			{
				for(int i=0; i<routedContactList.size(); i++)
				{
					soContact = (Contact) routedContactList.get(i);
					if(soContact != null && soContact.getResourceId().equals(newResourceId)){
						isAllowed=true;
						break;
					}
						
				}
			}
			if(routedResources!=null && resourceId!=null)
			{
				for(int i=0; i<routedResources.size(); i++)
				{
					soCurrentContact = (Contact) routedResources.get(i).getProviderContact();
					if(soCurrentContact != null && routedResources.get(i).getResourceId().equals(resourceId))
						break;
				}
			}
			Integer role = securityContext.getRoleId();
			Integer entityId = securityContext.getVendBuyerResId();
			String userFirstName=  securityContext.getRoles().getFirstName();
			String userLastName =  securityContext.getRoles().getLastName();
			if(isAllowed==false){
				results = Results.getError(ResultsCode.SO_REASSIGN_WRONG_RESOURCE.getMessage(), ResultsCode.SO_REASSIGN_WRONG_RESOURCE.getCode());
				return new ReassignSOResponse(results);
			}
			if(reassignReason==null||StringUtils.isEmpty(reassignReason)){
				results = Results.getError(ResultsCode.SO_REASSIGN_COMMENTS_EMPTY.getMessage(), ResultsCode.SO_REASSIGN_COMMENTS_EMPTY.getCode());
				return new ReassignSOResponse(results);
			}
			soLoggingVO.setFirstName(soContact.getFirstName().trim());
			soLoggingVO.setLastName(soContact.getLastName().trim());
			soLoggingVO.setEmail(soContact.getEmail().trim());
			soLoggingVO.setServiceOrderNo(soId);
			soLoggingVO.setContactId(soContact.getContactId());
			soLoggingVO.setOldValue(resourceId.toString());
			soLoggingVO.setNewValue(newResourceId.toString());
			if(null!=soCurrentContact){
				soLoggingVO.setComment("Has been reassigned from " + soCurrentContact.getFirstName() + " " + soCurrentContact.getLastName()+ "(" + soLoggingVO.getOldValue() + ") to " + soContact.getFirstName() +" " + soContact.getLastName() + 
					"(" +  soLoggingVO.getNewValue() + "). Reason for Reassigning is: " + reassignReason);
			}
			soLoggingVO.setCreatedDate(Calendar.getInstance().getTime());
			soLoggingVO.setModifiedDate(Calendar.getInstance().getTime());
			soLoggingVO.setCreatedByName(userFirstName+" "+userLastName);
			soLoggingVO.setModifiedBy(securityContext.getUsername());
			soLoggingVO.setRoleId(role);
			soLoggingVO.setEntityId(entityId);
			soLoggingVO.setValueName(Constants.REASSIGNMENT_NOTE_SUBJECT);
			soLoggingVO.setActionId(Integer.parseInt(Constants.SO_ACTION.SERVICE_ORDER_REASSIGN));
			
			
			soNote.setSoId(soLoggingVO.getServiceOrderNo());
			soNote.setCreatedDate(soLoggingVO.getCreatedDate());
			soNote.setSubject(Constants.REASSIGNMENT_NOTE_SUBJECT);
			soNote.setRoleId(soLoggingVO.getRoleId());
			soNote.setNote(soLoggingVO.getComment());
			soNote.setCreatedByName(userLastName+", "+userFirstName);
			soNote.setModifiedBy(securityContext.getUsername());
			soNote.setModifiedDate(soLoggingVO.getModifiedDate());
			soNote.setNoteTypeId(new Integer(2));
			soNote.setEntityId(soLoggingVO.getEntityId());
			soNote.setPrivateId(new Integer(0));
			
			success = serviceOrderBO.saveReassignSO(soLoggingVO,soNote,reassignReason, securityContext);
		} catch (BusinessServiceException bse) {
			logger.error("Requested reassign unsuccessful", bse);
			results = Results.getError("Error reassigning SO", ResultsCode.SO_RELEASE_ERROR_PROBLEMSTATUS.getCode());
		}
		
		if(success.getCode().equals(ServiceConstants.VALID_RC)){
			results = Results.getSuccess();
		}else{
			results = Results.getError("Error in reassigning SO", ResultsCode.SO_RELEASE_ERROR.getCode());
		}
		logger.info("Exiting execute method");	
		return new ReassignSOResponse(results);
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}


}
