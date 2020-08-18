package com.newco.marketplace.api.mobile.services.v3_1;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsRequest;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;

@APIRequestClass(UpdateScheduleDetailsRequest.class)
@APIResponseClass(UpdateScheduleDetailsResponse.class)
public class UpdateScheduleDetailsService extends BaseService {
	private Logger LOGGER = Logger.getLogger(UpdateScheduleDetailsService.class);

	private NewMobileGenericValidator mobileValidator;
	private NewMobileGenericMapper mobileMapper;
	private INewMobileGenericBO newMobileBO;
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {

		UpdateScheduleDetailsResponse response = new UpdateScheduleDetailsResponse();
		final UpdateScheduleDetailsRequest updateScheduleDetailsRequest = (UpdateScheduleDetailsRequest) apiVO.getRequestFromPostPut();

		Results results = null;
		UpdateScheduleVO updateScheduleVO = null;
		SecurityContext securityContext =null;
		try{
			securityContext = getSecurityContextForVendor(apiVO.getProviderResourceId());
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(updateScheduleDetailsRequest, apiVO, securityContext);
			if(null == updateScheduleVO){
				throw new Exception("Request is null, cannot proceed");
			}
			//validate request
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
			if(null== response.getResults()){
				//map time details for customer response reason = 3
				if(updateScheduleVO.getCustomerAvailableFlag() 
						&& MPConstants.UPDATE_SERVICE_WINDOW.equals(updateScheduleVO.getCustAvailableRespCode())){
					UpdateApptTimeVO apptTimeVO = newMobileBO.fetchServiceDatesAndTimeWndw(updateScheduleVO.getSoId());
					updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(updateScheduleDetailsRequest, updateScheduleVO, apptTimeVO);
					//validate time window
					response = mobileValidator.validateUpdateScheduleRequestForTime(updateScheduleVO);
				}
				
				if(null== response.getResults()){
					if(updateScheduleVO.getCustomerAvailableFlag() 
							&& MPConstants.UPDATE_SERVICE_WINDOW.equals(updateScheduleVO.getCustAvailableRespCode())){
						updateScheduleVO = mobileMapper.mapDateAndTimeAfterConversion(updateScheduleVO);
					}
					newMobileBO.updateScheduleDetails(updateScheduleVO);
					if(updateScheduleVO.getSource().equals(MPConstants.PRE_CALL)){
						results = Results.getSuccess(ResultsCode.V3_1_PRE_CALL_SUCCESS_MSG.getCode(),ResultsCode.V3_1_PRE_CALL_SUCCESS_MSG.getMessage());
					}else{
						results = Results.getSuccess(ResultsCode.V3_1_CONFIRM_APPT_SUCCESS_MSG.getCode(),ResultsCode.V3_1_CONFIRM_APPT_SUCCESS_MSG.getMessage());
					}
					response.setResults(results);
				}
			}
		} catch(Exception e){
			LOGGER.error("Exception in Update Time window Service"+ apiVO.getSOId()+ e.getMessage());
			e.printStackTrace();
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
		}
		return response;
	}

	public NewMobileGenericValidator getMobileValidator() {
		return mobileValidator;
	}

	public void setMobileValidator(NewMobileGenericValidator mobileValidator) {
		this.mobileValidator = mobileValidator;
	}


	public NewMobileGenericMapper getMobileMapper() {
		return mobileMapper;
	}


	public void setMobileMapper(NewMobileGenericMapper mobileMapper) {
		this.mobileMapper = mobileMapper;
	}

	public INewMobileGenericBO getNewMobileBO() {
		return newMobileBO;
	}

	public void setNewMobileBO(INewMobileGenericBO newMobileBO) {
		this.newMobileBO = newMobileBO;
	}

}
