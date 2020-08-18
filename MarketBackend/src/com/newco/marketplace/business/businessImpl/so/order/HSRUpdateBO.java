package com.newco.marketplace.business.businessImpl.so.order;

import java.util.Date;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.SecurityUtil;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class HSRUpdateBO extends BaseUpdateBO implements IProBuyerUpdateBO{

	public ProcessResponse updateServiceOrder(ServiceOrder matchingSO,
			ServiceOrder updatedSO, Map<String, Object> buyerParameters,
			SecurityContext securityContext) throws BusinessServiceException {
		
		// First: Add message notes if they exist
		if (updatedSO.getSoNotes() != null && !updatedSO.getSoNotes().isEmpty()) {
			addNotes(updatedSO.getSoNotes(), matchingSO.getSoId(), matchingSO.getBuyerResourceId());
		}
		
		// Process client status
		String clientStatus = (String)buyerParameters.get(OrderConstants.CommonProBuyerParameters.CLIENT_STATUS);
		ProcessResponse response = new ProcessResponse();

		if (clientStatus != null && clientStatus.length() > 0) {
			response = processClientStatus(matchingSO, updatedSO, securityContext, clientStatus, response);
		}
		
		if (response.getCode() == null || response.getCode().equals("") || !response.isError()) {
			response.setCode(ServiceConstants.VALID_RC);
			response.setObj(matchingSO.getSoId());
		}
		
		return response;
	}
	
	protected ProcessResponse processClientStatus(ServiceOrder so, ServiceOrder updateServiceOrder, SecurityContext securityContext, String clientStatus, ProcessResponse response) throws BusinessServiceException {
		String notes = "";
		String commonNotes = "";
    	String needsAttentionNotesText = OrderConstants.HSR_UPDATE_NEED_ATTENTION_NOTES;
		String subject = OrderConstants.HSR_UPDATE_NOTES_SUBJECT;
		
		Integer notePrivateInd = Integer.valueOf(OrderConstants.SO_NOTE_PUBLIC_ACCESS);
		ProcessResponse pr = new ProcessResponse();
		
		try {
			if(updateServiceOrder.getBuyerSpecificFields()!= null){
				String modifyingUnitId = updateServiceOrder.getBuyerSpecificFields().get(OrderConstants.HSR_MODIFYING_UNIT_ID) ;
				String clientStatusCode = updateServiceOrder.getBuyerSpecificFields().get(OrderConstants.HSR_CLIENT_STATUS_CODE) ;
				commonNotes = modifyingUnitId + " - " + clientStatusCode + " - " ;
			}
	
			//State of the service live service order
			if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
						|| so.getWfStateId() == OrderConstants.CLOSED_STATUS || so.getWfStateId() == OrderConstants.COMPLETED_STATUS
						|| so.getWfStateId() == OrderConstants.DELETED_STATUS || so.getWfStateId() == OrderConstants.DELETED_STATUS	) {
					notes = commonNotes + OrderConstants.HSR_UPDATE_NOT_PROCESSED_NOTES ;
					addNote(subject, notes, so.getSoId(), so.getBuyerResourceId(), notePrivateInd);
					return response;
			} 
			if(!clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)){
					serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.NEEDS_ATTENTION_SUBSTATUS, securityContext);
					notes = commonNotes + needsAttentionNotesText;
					addNote(subject, notes, so.getSoId(), so.getBuyerResourceId(), notePrivateInd);
					return response;	
			} 
					
			switch (so.getWfStateId().intValue()) {
					case OrderConstants.DRAFT_STATUS:
					       if ( clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)) {
								SecurityContext systemSecurityContext = SecurityUtil.getSystemSecurityContext();
								response = serviceOrderBO.deleteDraftSO(so.getSoId(), systemSecurityContext);
								notes = OrderConstants.HSR_UPDATE_DELETED_NOTES;
							}
							break;
					case OrderConstants.ROUTED_STATUS:
							if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)) {
								response = serviceOrderBO.processVoidSOForWS(so.getBuyer().getBuyerId(), so.getSoId(), securityContext);
						        notes = OrderConstants.HSR_UPDATE_VOIDED_NOTES;
					    	}
							break;
					case OrderConstants.ACCEPTED_STATUS:
							if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)) {
								String cancelComment = "Update file came in for cancellation in Accepted status";
								String buyerName = so.getBuyer().getBuyerContact().getLastName() + " " + so.getBuyer().getBuyerContact().getFirstName(); 
								response = serviceOrderBO.processCancelSOInAccepted(so.getBuyer().getBuyerId(), so.getSoId(), cancelComment, buyerName, securityContext);
								notes = OrderConstants.HSR_UPDATE_CANCELLED_NOTES;
							}
							break;
					case OrderConstants.ACTIVE_STATUS:
							if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)) {
								response = serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.NEEDS_ATTENTION_SUBSTATUS, securityContext);
									notes = needsAttentionNotesText;
							}
							break;
					case OrderConstants.PROBLEM_STATUS:
							if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)) {
								response = serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.NEEDS_ATTENTION_SUBSTATUS, securityContext);
									notes = needsAttentionNotesText;
							}
							break;	
					case OrderConstants.EXPIRED_STATUS:
							if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)) {
								response = serviceOrderBO.processVoidSOForWS(so.getBuyer().getBuyerId(), so.getSoId(), securityContext);
								   notes = OrderConstants.HSR_UPDATE_VOIDED_NOTES;
							}
							break;	
					}
					notes = commonNotes + notes;		
					this.addNote(subject, notes, so.getSoId(), so.getBuyerResourceId(), notePrivateInd);
		} catch (RuntimeException e) {
			ProcessResponse exceptionres =  new ProcessResponse();
			exceptionres.setMessage("Error occuured in HSRUpdateBO.processClientSattus" + e.getMessage());
			exceptionres.setCode(USER_ERROR_RC);
			response = rollupResponse(response, exceptionres);
		}
		return response;
	}


}
