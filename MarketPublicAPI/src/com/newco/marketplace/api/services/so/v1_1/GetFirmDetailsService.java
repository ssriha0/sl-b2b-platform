package com.newco.marketplace.api.services.so.v1_1;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.firmDetail.GetFirmDetailsRequest;
import com.newco.marketplace.api.beans.firmDetail.GetFirmDetailsResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.firm.validators.GetFirmValidator;
import com.newco.marketplace.api.utils.mappers.so.v1_1.FirmDetailsMapper;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.provider.FirmDetailRequestVO;
import com.newco.marketplace.dto.vo.provider.FirmDetailsResponseVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

@Namespace("http://www.servicelive.com/namespaces/getFirmDetailsService")
@APIRequestClass(GetFirmDetailsRequest.class)
@APIResponseClass(GetFirmDetailsResponse.class)
public class GetFirmDetailsService extends BaseService{
	private static Logger logger = Logger.getLogger(GetFirmDetailsService.class);

	private FirmDetailsMapper firmDetailsMapper;
	private GetFirmValidator firmValidator;
	private IServiceOrderBO serviceOrderBO;

	@SuppressWarnings("unchecked")
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		GetFirmDetailsRequest getFirmDetailRequest = (GetFirmDetailsRequest) apiVO.getRequestFromPostPut();
		GetFirmDetailsResponse response = new GetFirmDetailsResponse();
		FirmDetailRequestVO firmDetailRequestVO = null;
		FirmDetailsResponseVO firmDetailsResponseVO = null;
		String requestFilter="";
		Results results = null;
		Map<String, String> reqMap =  (Map<String, String>) apiVO.getProperty(PublicAPIConstant.REQUEST_MAP);
		try {
			//setting the response filters entered by the user
			if(null != reqMap && reqMap.containsKey(PublicAPIConstant.RESPONSE_FILTER)){
				requestFilter = reqMap.get(PublicAPIConstant.RESPONSE_FILTER);
			}
			//mapping the request parameters to the VO 
			firmDetailRequestVO = firmDetailsMapper.mapFirmDetailRequest(getFirmDetailRequest,requestFilter);
			//method to validate the filter criteria and firms
			if(null != firmDetailRequestVO){
				List<ErrorResult> validationErrors = firmValidator.validateFirmDetailsRequest(firmDetailRequestVO);
				if(!validationErrors.isEmpty()){				
					results = new Results();
					results.setError(validationErrors);
				}
				else{
					//method to fetch the firm details
					firmDetailsResponseVO = serviceOrderBO.getFirmDetails(firmDetailRequestVO);
					if(null != firmDetailsResponseVO){
						results = Results.getSuccess(ResultsCode.FIRM_DETAIL_SUCCESS.getMessage());
						// method to map the response to response object from VO
						response = firmDetailsMapper.mapFirmDetailsResponse(firmDetailsResponseVO,firmDetailRequestVO);
					}
				}
			}

		} 
		catch (BusinessServiceException e) {
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			e.printStackTrace();
			logger.error("Exception in Execute method of getFirmDetails"+ e.getMessage());
		}
		if(null != results){
			response.setResults(results);
		}
		return response;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}
	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
	public FirmDetailsMapper getFirmDetailsMapper() {
		return firmDetailsMapper;
	}
	public void setFirmDetailsMapper(FirmDetailsMapper firmDetailsMapper) {
		this.firmDetailsMapper = firmDetailsMapper;
	}
	public GetFirmValidator getFirmValidator() {
		return firmValidator;
	}
	public void setFirmValidator(GetFirmValidator firmValidator) {
		this.firmValidator = firmValidator;
	}

}
