/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.mobile.services.v3_1;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.updateTimeWindow.UpdateTimeWindowVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.updateTimeWindow.UpdateTimeWindowResponse;
import com.newco.marketplace.api.mobile.beans.updateTimeWindow.UpdatetimeWindowRequest;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
/**
 * This class would act as a Service Edit Appointment Time 
 * 
 * @author Infosys
 * @version 1.0
 */
@APIRequestClass(UpdatetimeWindowRequest.class)
@APIResponseClass(UpdateTimeWindowResponse.class)
public class UpdateTimeWindowService extends BaseService {
	private Logger LOGGER = Logger.getLogger(UpdateTimeWindowService.class);

	private NewMobileGenericValidator mobileValidator;
	private NewMobileGenericMapper mobileMapper;
	private INewMobileGenericBO newMobileBO;

	@Override
	public IAPIResponse execute(APIRequestVO apiVO){
		UpdateTimeWindowResponse response = new UpdateTimeWindowResponse();
		
		final UpdatetimeWindowRequest request = (UpdatetimeWindowRequest) apiVO.getRequestFromPostPut();
		Results results = null;
		UpdateTimeWindowVO timeWindowVO=null;
		SecurityContext securityContext =null;
		
		try{
			securityContext = getSecurityContextForVendor(apiVO.getProviderResourceId());
			
			
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request, apiVO,securityContext);
			if(null == timeWindowVO){
				throw new Exception("Request is null, cannot proceed");
			}
			//validate request
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);
			if(null== response.getResults()){
				timeWindowVO =  newMobileBO.fetchServiceDatesAndTimeWndw(timeWindowVO.getSoId(), timeWindowVO);
				//calculate the new date& time to save and map it to timeWindowVO
				timeWindowVO = mobileMapper.mapDateAndTimeToSave(timeWindowVO);
				// business validations
				response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO, true);
				//If there are no errors,proceed 
				if(null== response.getResults()){
					//business logic
					//get scheduleVO for reusing existing BO call
					UpdateScheduleVO scheduleVO = mobileMapper.getVOForUpdateTime(timeWindowVO);
					//get historyVO for insertion to history table
					ProviderHistoryVO hisVO = mobileMapper.getHistoryVO(timeWindowVO);
					newMobileBO.updateTimeWindow(scheduleVO,hisVO);
					results = Results.getSuccess(ResultsCode.v3_1_TIME_WINDOW_SUCCESS_MSG.getCode(),ResultsCode.v3_1_TIME_WINDOW_SUCCESS_MSG.getMessage());
					response.setResults(results);
				}
			}
			
		}catch (Exception e) {
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