package com.newco.marketplace.api.utils.mappers.so;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addEstimate.AddEstimateRequest;
import com.newco.marketplace.api.beans.so.addEstimate.AddEstimateResponse;
import com.newco.marketplace.api.beans.so.addEstimate.EstimatePart;
import com.newco.marketplace.api.beans.so.addEstimate.EstimateTask;
import com.newco.marketplace.api.beans.so.addEstimate.GetEstimateDetailsResponse;
import com.newco.marketplace.api.beans.so.addEstimate.OtherEstimateService;
import com.newco.marketplace.api.beans.so.addEstimate.Pricing;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.EstimateTaskVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.utils.DateUtils;


public class SODetailsRetrieveMapper {
	private Logger LOGGER = Logger.getLogger(SODetailsRetrieveMapper.class);

	private ILookupBO lookupBO;
	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}
	


	/**@Description : Mapping request details to VO for persistence
	 * @param addEstimateRequest
	 * @param estimationId
	 * @param apiVO
	 * @param userName 
	 * @return
	 */
	public EstimateVO mapAddEditEstimate(AddEstimateRequest addEstimateRequest,Integer estimationId, APIRequestVO apiVO, String userName) {
		EstimateVO estimateVO = new EstimateVO();
		mapEstimationDetails(estimateVO,addEstimateRequest,apiVO,estimationId,userName);
		mapTasksAndParts(estimateVO,addEstimateRequest,estimationId);
		//SL-21385-Adding Other Services as part of R16_2_1
		mapOtherEstimateSevices(estimateVO,addEstimateRequest,estimationId);
		mapEstimatePrice(estimateVO,addEstimateRequest);
		return estimateVO; 
	}

   
	

	/**@Description : Mapping request details to Vo for persistence in so_estimation table
	 * @param estimateVO
	 * @param addEstimateRequest
	 * @param apiVO
	 * @param userName 
	 */
	private void mapEstimationDetails(EstimateVO estimateVO,AddEstimateRequest addEstimateRequest, APIRequestVO apiVO,Integer estimationId, String userName) {
		estimateVO.setSoId(apiVO.getSOId());
		estimateVO.setEstimationId(estimationId);
		estimateVO.setEstimationRefNo(addEstimateRequest.getEstimationRefNo());
		estimateVO.setLogoDocumentId(Integer.valueOf(addEstimateRequest.getLogoDocumentId()));
		estimateVO.setComments(addEstimateRequest.getComments());
		if(StringUtils.isNotBlank(apiVO.getProviderId())&& StringUtils.isNumeric(apiVO.getProviderId())){
		  estimateVO.setVendorId(Integer.parseInt(apiVO.getProviderId()));
		}
		estimateVO.setResourceId(addEstimateRequest.getIdentification().getId());						
		if(null==estimationId){
			estimateVO.setCreatedBy(userName);
			//Setting status as NEW/DRAFT based on isDraftEstimateFlag
			if((StringUtils.isNotBlank(addEstimateRequest.getIsDraftEstimate()) 
					&& StringUtils.equalsIgnoreCase(PublicAPIConstant.DRAFT_STATUS_YES, addEstimateRequest.getIsDraftEstimate()))){
				  estimateVO.setStatus(PublicAPIConstant.DRAFT_CAPS);
			  }else{
				  estimateVO.setStatus(PublicAPIConstant.NEW);
			  }
		}
		estimateVO.setModifiedBy(userName);
		estimateVO.setIsDraftEstimate(addEstimateRequest.getIsDraftEstimate());
		Date estimateDate = DateUtils.parseDate(addEstimateRequest.getEstimationDate());
		LOGGER.info("Estimation Date after Formatting:"+ estimateDate);
		estimateVO.setEstimationDate(estimateDate);
		if(null!=addEstimateRequest.getEstimationExpiryDate()){
		Date estimateExpiryDate = DateUtils.parseDate(addEstimateRequest.getEstimationExpiryDate());
		estimateVO.setEstimationExpiryDate(estimateExpiryDate);
		}
		
	}


	/**@Description : Mapping tasks/parts details in estimation request to vo  for persistence in so_estimation_items table
	 * @param addEstimateRequest
	 * @param estimateVO
	 * @param estimationId
	 */
	private void mapTasksAndParts( EstimateVO estimateVO,AddEstimateRequest addEstimateRequest,Integer estimationId) {
		List<EstimateTaskVO> estimateTasks = null;
		List<EstimateTaskVO> estimateParts = null;
		//Mapping Task Details 
		if(null!= addEstimateRequest && null!= addEstimateRequest.getEstimateTasks() &&
				null!= addEstimateRequest.getEstimateTasks().getEstimateTask() 
				    && !addEstimateRequest.getEstimateTasks().getEstimateTask().isEmpty()){
			estimateTasks = new ArrayList<EstimateTaskVO>();
			for(EstimateTask requestTask : addEstimateRequest.getEstimateTasks().getEstimateTask()){
				if(null!= requestTask){
					EstimateTaskVO taskVO = new EstimateTaskVO();
					taskVO.setEstimationId(estimationId);
					if(StringUtils.isNotBlank(requestTask.getTaskSeqNumber())){
					  taskVO.setTaskSeqNumber(Integer.parseInt(requestTask.getTaskSeqNumber()));
					}
					taskVO.setTaskName(requestTask.getTaskName());
					taskVO.setTaskType(PublicAPIConstant.LABOR_TASK);
					taskVO.setDescription(requestTask.getDescription());
					if(StringUtils.isNotBlank(requestTask.getUnitPrice())){
					   taskVO.setUnitPrice(Double.parseDouble(requestTask.getUnitPrice()));
					}
					if(StringUtils.isNotBlank(requestTask.getQuantity())){
					   taskVO.setQuantity(Integer.parseInt(requestTask.getQuantity()));
					}
					if(StringUtils.isNotBlank(requestTask.getTotalPrice())){
					   taskVO.setTotalPrice(Double.parseDouble(requestTask.getTotalPrice()));
					}
					taskVO.setAdditionalDetails(requestTask.getAdditionalDetails());
					estimateTasks.add(taskVO);
				}
			}
			
		}
		//Mapping Parts Details
		if(null!= addEstimateRequest && null!= addEstimateRequest.getEstimateParts() &&
				null!= addEstimateRequest.getEstimateParts().getEstimatePart()
				    && !addEstimateRequest.getEstimateParts().getEstimatePart().isEmpty()){
			estimateParts = new ArrayList<EstimateTaskVO>();
			for(EstimatePart requestPart : addEstimateRequest.getEstimateParts().getEstimatePart()){
				if(null!= requestPart){
					EstimateTaskVO taskVO = new EstimateTaskVO();
					taskVO.setEstimationId(estimationId);
					if(StringUtils.isNotBlank(requestPart.getPartSeqNumber())){
					  taskVO.setPartSeqNumber(Integer.parseInt(requestPart.getPartSeqNumber()));
					}
					taskVO.setPartNumber(requestPart.getPartNumber());
					taskVO.setPartName(requestPart.getPartName());
					taskVO.setTaskType(PublicAPIConstant.PARTS_TASK);
					taskVO.setDescription(requestPart.getDescription());
					if(StringUtils.isNotBlank(requestPart.getUnitPrice())){
					  taskVO.setUnitPrice(Double.parseDouble(requestPart.getUnitPrice()));
					}
					if(StringUtils.isNotBlank(requestPart.getQuantity())){
					taskVO.setQuantity(Integer.parseInt(requestPart.getQuantity()));
					}
					if(StringUtils.isNotBlank(requestPart.getTotalPrice())){
					  taskVO.setTotalPrice(Double.parseDouble(requestPart.getTotalPrice()));
					}
					taskVO.setAdditionalDetails(requestPart.getAdditionalDetails());
					estimateParts.add(taskVO);
				}
			}
		 }
			estimateVO.setEstimateTasks(estimateTasks);
			estimateVO.setEstimateParts(estimateParts);
			
		}
	/**@Description: Mapping pricing details from request to VO
	 * @param estimateVO
	 * @param addEstimateRequest
	 */
	private void mapEstimatePrice(EstimateVO estimateVO,AddEstimateRequest addEstimateRequest) {
		Pricing requestPrice = addEstimateRequest.getPricing();
		if(null!=requestPrice){
			estimateVO.setTotalLaborPrice(Double.parseDouble(requestPrice.getTotalLaborPrice()));
			if(StringUtils.isNotBlank(requestPrice.getTotalPartsPrice())){
			   estimateVO.setTotalPartsPrice(Double.parseDouble(requestPrice.getTotalPartsPrice()));
			}else{
			   estimateVO.setTotalPartsPrice(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			//SL-21385 :Adding Other Services as part of R16_2_1 -- start
			if(StringUtils.isNotBlank(requestPrice.getTotalOtherServicePrice())){
				estimateVO.setTotalOtherServicePrice(Double.parseDouble(requestPrice.getTotalOtherServicePrice()));	   
			}else{
				estimateVO.setTotalOtherServicePrice(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			//SL-21385 -- end
			estimateVO.setDiscountType(requestPrice.getDiscountType());
			if(StringUtils.isNotBlank(requestPrice.getDiscountedPercentage())){
				estimateVO.setDiscountedPercentage(Double.parseDouble(requestPrice.getDiscountedPercentage()));
			}
			if(StringUtils.isNotBlank(requestPrice.getDiscountedAmount())){
			    estimateVO.setDiscountedAmount(Double.parseDouble(requestPrice.getDiscountedAmount()));
			}
			if(StringUtils.isNotBlank(requestPrice.getTaxRate())){
			    estimateVO.setTaxRate(Double.parseDouble(requestPrice.getTaxRate()));
			}
			estimateVO.setTaxType(requestPrice.getTaxType());
			if(StringUtils.isNotBlank(requestPrice.getTaxPrice())){
			    estimateVO.setTaxPrice(Double.parseDouble(requestPrice.getTaxPrice()));
			}
			if(StringUtils.isNotBlank(requestPrice.getTotalPrice())){
			    estimateVO.setTotalPrice(Double.parseDouble(requestPrice.getTotalPrice()));
			}
			//SL-21934
			if(StringUtils.isNotBlank(requestPrice.getLaborDiscountType())){
			    estimateVO.setLaborDiscountType(requestPrice.getLaborDiscountType());
			}
			if(StringUtils.isNotBlank(requestPrice.getLaborDiscountedPercentage())){
			    estimateVO.setLaborDiscountedPercentage(Double.parseDouble(requestPrice.getLaborDiscountedPercentage()));
			}else{
				 estimateVO.setLaborDiscountedPercentage(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getLaborDiscountedAmount())){
			    estimateVO.setLaborDiscountedAmount(Double.parseDouble(requestPrice.getLaborDiscountedAmount()));
			}else{
				 estimateVO.setLaborDiscountedAmount(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getLaborTaxRate())){
			    estimateVO.setLaborTaxRate(Double.parseDouble(requestPrice.getLaborTaxRate()));
			}else{
				 estimateVO.setLaborTaxRate(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getLaborTaxPrice())){
			    estimateVO.setLaborTaxPrice(Double.parseDouble(requestPrice.getLaborTaxPrice()));
			}else{
				 estimateVO.setLaborTaxPrice(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsDiscountType())){
			    estimateVO.setPartsDiscountType(requestPrice.getPartsDiscountType());
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsDiscountedPercentage())){
			    estimateVO.setPartsDiscountedPercentage(Double.parseDouble(requestPrice.getPartsDiscountedPercentage()));
			}else{
				 estimateVO.setPartsDiscountedPercentage(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsDiscountedAmount())){
			    estimateVO.setPartsDiscountedAmount(Double.parseDouble(requestPrice.getPartsDiscountedAmount()));
			}else{
				 estimateVO.setPartsDiscountedAmount(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsTaxRate())){
			    estimateVO.setPartsTaxRate(Double.parseDouble(requestPrice.getPartsTaxRate()));
			}else{
				 estimateVO.setPartsTaxRate(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsTaxPrice())){
			    estimateVO.setPartsTaxPrice(Double.parseDouble(requestPrice.getPartsTaxPrice()));
			}else{
				 estimateVO.setPartsTaxPrice(Double.parseDouble(PublicAPIConstant.ZERO_PRICE));
			}
			
		}
		
	}

	
	
	private void mapOtherEstimateSevices(EstimateVO estimateVO,AddEstimateRequest addEstimateRequest, Integer estimationId) {
		List<EstimateTaskVO> otherEstimateServices=null;
		
		//Mapping Other Service Details 
		if(null!= addEstimateRequest && null!= addEstimateRequest.getOtherEstimateServices() &&
				null!= addEstimateRequest.getOtherEstimateServices().getOtherEstimateService()
				    && !addEstimateRequest.getOtherEstimateServices().getOtherEstimateService().isEmpty()){
			  
			otherEstimateServices = new ArrayList<EstimateTaskVO>();
			for(OtherEstimateService otherEstimateService : addEstimateRequest.getOtherEstimateServices().getOtherEstimateService()){
				if(null!= otherEstimateService){
					EstimateTaskVO taskVO = new EstimateTaskVO();
					taskVO.setEstimationId(estimationId);
					if(StringUtils.isNotBlank(otherEstimateService.getOtherServiceSeqNumber())){
					  taskVO.setOtherServiceSeqNumber(Integer.parseInt(otherEstimateService.getOtherServiceSeqNumber()));
					}
					taskVO.setOtherServiceName(otherEstimateService.getOtherServiceName());
					taskVO.setOtherServiceType(otherEstimateService.getOtherServiceType());
					taskVO.setDescription(otherEstimateService.getDescription());
					if(StringUtils.isNotBlank(otherEstimateService.getUnitPrice())){
					   taskVO.setUnitPrice(Double.parseDouble(otherEstimateService.getUnitPrice()));
					}
					if(StringUtils.isNotBlank(otherEstimateService.getQuantity())){
					   taskVO.setQuantity(Integer.parseInt(otherEstimateService.getQuantity()));
					}
					if(StringUtils.isNotBlank(otherEstimateService.getTotalPrice())){
					   taskVO.setTotalPrice(Double.parseDouble(otherEstimateService.getTotalPrice()));
					}
					taskVO.setAdditionalDetails(otherEstimateService.getAdditionalDetails());
					otherEstimateServices.add(taskVO);
				}
			}
			
		}
		estimateVO.setEstimateOtherEstimateServices(otherEstimateServices);
	}

	/**@Description: Setting the Success Response for Add/Edit Estimate 
	 * @param addEditestimationId
	 * @return
	 */
	public AddEstimateResponse createSuccessResponse(Integer addEditestimationId) {
		AddEstimateResponse response = new AddEstimateResponse();
		response.setEstimationId(addEditestimationId);
		Results results = Results.getSuccess(ResultsCode.ADD_EDIT_ESTIMATE.getMessage());
		response.setResults(results);
		return response;
	}

	/**@Description: Setting the Error Response for Add/Edit Estimate 
	 * @param addEditestimationId
	 * @return
	 */
	public AddEstimateResponse createErrorResponse(String message,String code) {
		AddEstimateResponse response = new AddEstimateResponse();
		Results results = Results.getError(message, code);
		response.setResults(results);		
		return response;
	}
	
	
	
	
	
	public AddEstimateResponse createErrorResponseForNoChange() {
		AddEstimateResponse response = new AddEstimateResponse();
		Results results = Results.getError(ResultsCode.NO_ESTIMATE_CHANGE.getMessage(),ResultsCode.NO_ESTIMATE_CHANGE.getCode());
		response.setResults(results);		
		return response;
	}
	
	
	
	public GetEstimateDetailsResponse setErrorResponse(String message,String code) {
		GetEstimateDetailsResponse response = new GetEstimateDetailsResponse();
		Results results = Results.getError(message, code);
		response.setResults(results);		
		return response;
	}


	





}