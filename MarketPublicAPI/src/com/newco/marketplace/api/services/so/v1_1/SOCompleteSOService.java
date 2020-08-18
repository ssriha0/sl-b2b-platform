package com.newco.marketplace.api.services.so.v1_1;

import java.util.List;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.complete.CompleteSORequest;
import com.newco.marketplace.api.beans.so.complete.SOCompleteSOResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOCompleteMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class SOCompleteSOService extends BaseService{
	
	private IServiceOrderBO serviceOrderBO;
	private SOCompleteMapper soCompleteSOMapper;
	ProcessResponse processResponse= new ProcessResponse();
	
	public SOCompleteSOService() {
		super(PublicAPIConstant.PRO_COMPLETE_XSD,
				PublicAPIConstant.SOPROVIDERRESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_PROVIDER_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				PublicAPIConstant.SOPRORESPONSE_SCHEMALOCATION,
				CompleteSORequest.class, SOCompleteSOResponse.class);
	}

	
	
	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		SecurityContext securityContext = null;
		Results results = null;
		SOCompleteSOResponse soCompleteResponse = new SOCompleteSOResponse();
		
		//logger.info("Entering SOCompleteSOService.execute()");
		String providerId = (String) apiVO
				.getProviderId();
		String soId = (String) apiVO.getSOId();
		if (null != providerId && null != soId) {
			try {
				securityContext = getSecurityContextForVendorAdmin(new Integer(providerId));
			} catch (NumberFormatException nme) {
				/*logger.error("CompleteRequestService.execute(): "
						+ "Number Format Exception occurred for resourceId:"
						+ resourceId, nme);*/
			} catch (Exception exception) {
				/*logger.error("Exception occurred while accessing security "
						+ "context using resourceId", exception);*/
			}
				try {
					
				ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
							
				CompleteSORequest completeSORequest = (CompleteSORequest) apiVO
					.getRequestFromPostPut();
				String resolutionDesc = completeSORequest.getResolutionDesc();
				double partsFinalPrice = Double.parseDouble(completeSORequest.getPartsFinalPrice());
				double laborFinalPrice = Double.parseDouble(completeSORequest.getLaborFinalPrice());
				if(serviceOrder.getSpendLimitParts()<partsFinalPrice){
					results = Results
					.getError(
							ResultsCode.COMPLETE_SO_PARTS_ERROR
									.getMessage(),
							ResultsCode.COMPLETE_SO_PARTS_ERROR
									.getCode());
					soCompleteResponse.setResults(results);
				}
				else if(serviceOrder.getSpendLimitLabor()<laborFinalPrice){
					results = Results
					.getError(
							ResultsCode.COMPLETE_SO_LABOR_ERROR
									.getMessage(),
							ResultsCode.COMPLETE_SO_LABOR_ERROR
									.getCode());
					soCompleteResponse.setResults(results);
				}else {
				List<Part> partList = soCompleteSOMapper.mapPartsRequest(completeSORequest.getParts());
				List<BuyerReferenceVO> buyerRefVOList = soCompleteSOMapper.mapBuyerRef(completeSORequest.getBuyerRefs());
				processResponse = serviceOrderBO.processCompleteSO(soId, resolutionDesc, serviceOrder.getAcceptedResourceId(), partsFinalPrice, laborFinalPrice, partList, buyerRefVOList, securityContext);
				soCompleteResponse = soCompleteSOMapper.mapCompleteSOResponse(
						processResponse); }
				
				} catch (NumberFormatException e) {
					//logger
					//e.printStackTrace();
					results = Results.getError(e.getMessage(),
							ResultsCode.GENERIC_ERROR.getCode());
					soCompleteResponse.setResults(results);
				} catch (BusinessServiceException e) {
					//e.printStackTrace();
					//logger
					results = Results.getError(e.getMessage(),
							ResultsCode.GENERIC_ERROR.getCode());
					soCompleteResponse.setResults(results);
				}catch(Exception ex){
					//logger.error("SOCompleteSOService-->execute()--> Exception-->"
					//		+ ex.getMessage(), ex);
					results = Results.getError(ex.getMessage(),
							ResultsCode.GENERIC_ERROR.getCode());
					soCompleteResponse.setResults(results);
				}
			
		
	}
		return soCompleteResponse;
}



	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}



	public void setSoCompleteSOMapper(SOCompleteMapper soCompleteSOMapper) {
		this.soCompleteSOMapper = soCompleteSOMapper;
	}



	public void setProcessResponse(ProcessResponse processResponse) {
		this.processResponse = processResponse;
	}
}
