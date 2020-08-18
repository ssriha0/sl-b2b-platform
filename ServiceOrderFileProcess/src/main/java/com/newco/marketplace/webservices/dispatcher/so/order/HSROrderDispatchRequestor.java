
package com.newco.marketplace.webservices.dispatcher.so.order;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.newco.marketplace.webservices.util.WSConstants;

/**
 * @author yshulm0
 *
 */
public class HSROrderDispatchRequestor extends BaseOrderCreator  implements IOrderDispatchRequestor{

	private static final long serialVersionUID = 6759123552332812740L;
	private static final Logger logger = Logger.getLogger(HSROrderDispatchRequestor.class);
	
	public CreateDraftResponse createDraft(CreateDraftRequest request) {
		return super.processOMSCreateDraft(request, false);
	}
	
	public CreateDraftResponse updateOrder(CreateDraftRequest request) {
		CreateDraftResponse serviceResponse = new CreateDraftResponse();
		ServiceOrder so = null;
		ProcessResponse busProcessResponse = new ProcessResponse();
		String externalServiceOrderID = null;
		try {
			so = OrderDispatcherHelper.adaptRequest(request);
			// Set Buyer Resource Id and Buyer Contact Id in the ServiceOrder from SecurityContext
			SecurityContext securityContext = createSecurityContext(request.getUserId(), request.getPassword());
			if (securityContext.getCompanyId() != null) {
				ServiceOrderUtil.enrichSecurityContext(securityContext, securityContext.getCompanyId());
			}
			
			externalServiceOrderID = getSoIdentifier(request);
			IServiceOrderBO orderBusinessBean = (IServiceOrderBO) getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);

			ServiceOrder savedOrder = orderBusinessBean.getByCustomReferenceValue(externalServiceOrderID, request.getBuyerId());
			if(savedOrder == null){
				throw new Exception("No order found to update");
			}
			savedOrder.setExternalSoId(externalServiceOrderID);
		
			busProcessResponse = orderBusinessBean.updateServiceOrder(savedOrder, so, securityContext, request.getClientStatus(), request.getTemplateName());
			if (busProcessResponse != null && busProcessResponse.isSuccess() && busProcessResponse.getObj() == null)
				busProcessResponse.setObj(savedOrder.getSoId());
	
		} catch (Exception ex) {
			logger.error("HSROrderDispatchRequestor.updateOrder() in HSROrderDispatcher", ex);
			String errorMessage = "Error occured in HSROrderDispatchRequestor.UpdateOrder due to " + ex.getMessage();
			processBusinessError(serviceResponse, null, null, errorMessage, false, WSConstants.WSProcessStatus.FAILURE);
			return serviceResponse;
		}

		if (busProcessResponse != null && busProcessResponse.isSuccess()) {
			String soId = (String) busProcessResponse.getObj();
			logger.info("~~~~~~~~~~~~ [ORDERSTRING][SO_ID] returned from UpdateOrder of HSRDispatcher = [" + externalServiceOrderID + "][" + soId + "]");
			serviceResponse.setSLServiceOrderId(soId);
			//serviceResponse.setTemplateName(request.getTemplateName());
			//serviceResponse.setAutoRoute(request.getAutoRoute());
			serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.SUCCESS);
		} else if (busProcessResponse == null || busProcessResponse.isError()) {
			StringBuilder sbErrors = new StringBuilder("");
			for (WSErrorInfo error : serviceResponse.getErrorList()) {
				sbErrors.append(error.getMessage()).append(",");
			}
			logger.error(sbErrors.toString(), new Exception("Error while updating the SO in HSRDipatcher: " + externalServiceOrderID));
			serviceResponse.setSLServiceOrderId(WSConstants.FAILED_SERVICE_ORDER_NO);
			serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.FAILURE);
		}
		CreateDraftResponse obj = (CreateDraftResponse) processResponse(serviceResponse);
		logger.info("Ending CreateDraftResponse");
		return obj;
	}

	/* get so Identifer (OrderNo+UnitNumber) for the update request came in */
	private String getSoIdentifier(CreateDraftRequest request) throws Exception {
		String externalSOId = StringUtils.EMPTY;
		// Custom refs are in a list and the keys must match the buyer
		try {
			if (null != request.getCustomRef() && request.getCustomRef().size() > 0) {

				List<BuyerReferenceVO> reflist = super.getBuyerReferenceTypes(request.getBuyerId());
				for (BuyerReferenceVO refVO : reflist) {
					// see if it is the so identifier
					if (refVO.isSoIdentifier()) {
						for (CustomRef cs : request.getCustomRef()) {
							if (refVO.getReferenceType().equalsIgnoreCase(cs.getKey())) {
								externalSOId = cs.getValue();
								return externalSOId;
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			String message = "Erro occured in HSROrderDispatchRequestor.getSoIdentifer ";
			throw new Exception(message); 
		}
		return externalSOId;
	}
}
