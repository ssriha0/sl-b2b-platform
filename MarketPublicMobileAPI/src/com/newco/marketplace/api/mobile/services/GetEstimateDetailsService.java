package com.newco.marketplace.api.mobile.services;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.GetEstimateDetailsResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.SODetailsRetrieveMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.EstimateValidator;

@APIResponseClass(GetEstimateDetailsResponse.class)
public class GetEstimateDetailsService extends BaseService {

	private static final Logger LOGGER = Logger.getLogger(GetEstimateDetailsService.class);
	private IMobileGenericBO mobileGenericBO;
	private SODetailsRetrieveMapper detailsRetrieveMapper;
	private EstimateValidator estimateValidator;


	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {

		GetEstimateDetailsResponse estimateDetailsResponse = new GetEstimateDetailsResponse();
		Results results = new Results();

		/*Integer roleId = apiVO.getRoleId();
		Integer resourceId = apiVO.getProviderResourceId();
		Integer vendorId = Integer.parseInt(apiVO.getProviderId());*/

		EstimateVO estimateVO = new EstimateVO();
		Boolean isValidEstimateId = null;

		try {
			isValidEstimateId = estimateValidator.validateEstimationId(apiVO.getSOId(),apiVO.getEstimateId());
			if(null !=isValidEstimateId && isValidEstimateId){
				estimateVO = mobileGenericBO.getEstimateDetails(apiVO.getSOId(),apiVO.getEstimateId());

				if(null!=estimateVO){
					estimateDetailsResponse = detailsRetrieveMapper.mapEstimateDetails(estimateVO);
				}
				results = Results.getSuccess();
				 
				if(null != results){
					estimateDetailsResponse.setResults(results);
				}
			}
			else{
				estimateDetailsResponse = detailsRetrieveMapper.setErrorResponse(ResultsCode.INVALID_ESTIMATE_ID.getMessage(),ResultsCode.INVALID_ESTIMATE_ID.getCode());
			
			}
		}catch(BusinessServiceException ex){
			LOGGER.error("Exception in execute method of GetEstimateDetailsService-->"+ ex.getMessage());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		catch (Exception e) {
			LOGGER.error("Exception in execute method of GetEstimateDetailsService-->"+ e.getMessage());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		
		return estimateDetailsResponse;
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public SODetailsRetrieveMapper getDetailsRetrieveMapper() {
		return detailsRetrieveMapper;
	}

	public void setDetailsRetrieveMapper(
			SODetailsRetrieveMapper detailsRetrieveMapper) {
		this.detailsRetrieveMapper = detailsRetrieveMapper;
	}

	public EstimateValidator getEstimateValidator() {
		return estimateValidator;
	}

	public void setEstimateValidator(EstimateValidator estimateValidator) {
		this.estimateValidator = estimateValidator;
	}


}
