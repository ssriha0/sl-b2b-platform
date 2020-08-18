package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.assignSO.AssignVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.assign.AssignServiceOrderRequest;
import com.newco.marketplace.api.mobile.beans.assign.AssignServiceOrderResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.exception.AssignOrderException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/23
 * for validating phase 2 mobile APIs
 *
 */
@APIRequestClass(AssignServiceOrderRequest.class)
@APIResponseClass(AssignServiceOrderResponse.class)
public class AssignOrReassignSOService extends BaseService {
	private static final Logger LOGGER = Logger.getLogger(AssignOrReassignSOService.class);
	private MobileGenericValidator mobileGenericValidator;
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;

	/**
	 * Method to assign or reassign a provider for an so
	 * @param apiVO
	 * @return IAPIResponse
	 */
	@Override
	public IAPIResponse execute(final APIRequestVO apiVO) {
		
		AssignServiceOrderResponse assignServiceOrderResponse = new AssignServiceOrderResponse();
		AssignServiceOrderRequest assignServiceOrderRequest =(AssignServiceOrderRequest)apiVO.getRequestFromPostPut();
		Results results = null;
		AssignVO assignVO = null;	
		ProcessResponse processResp = new ProcessResponse();
		Integer urlResourceId = apiVO.getProviderResourceId();
		Integer roleId = apiVO.getRoleId();
		Integer firmResourceId = apiVO.getProviderResourceId();
		assignServiceOrderRequest.setFirmId(apiVO.getProviderId());
		assignServiceOrderRequest.setSoId(apiVO.getSOId());
		SecurityContext securityContext = getSecurityContextForVendor(firmResourceId);
		try {
			assignVO = mobileGenericMapper.mapAssignSORequest(assignServiceOrderRequest,roleId,urlResourceId);
			List<ErrorResult> validationErrors = mobileGenericValidator.validateAssignOrReassignSO(assignVO);
			
			if(!validationErrors.isEmpty()){				
				results = new Results();
				results.setError(validationErrors);
				}
			else{
				processResp = mobileGenericBO.assignOrReassignServiceOrder(securityContext,assignVO);
				
				assignServiceOrderResponse = mobileGenericMapper.mapAssignSOProviderResponse(processResp);
			}
		}
		  catch (AssignOrderException exc) {
				results = Results.getError(ResultsCode.ASSIGN_SO_ERROR_ALREADY_ASSIGNED.getMessage(),ResultsCode.ASSIGN_SO_ERROR_ALREADY_ASSIGNED.getCode());
				LOGGER.error("Exception in assign/reassign method in AssignOrderException"+exc.getMessage());	
		  }
		 catch (BusinessServiceException ex) {
			 ex.printStackTrace();
			LOGGER.error("Exception is SO Assign Provider"+ex.getMessage());	
			LOGGER.error("Exception in assign/reassign method where so_id" +apiVO.getSOId());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Exception in assign/reassign method "+ex.getMessage());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}		
		if(null != results){
			assignServiceOrderResponse.setResults(results);
		}
		return assignServiceOrderResponse;
	}	

	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}

	public void setMobileGenericValidator(MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}
	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}
}