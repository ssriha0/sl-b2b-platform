package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.ReasonCode;
import com.newco.marketplace.api.mobile.beans.ReasonCodesList;
import com.newco.marketplace.api.mobile.beans.GetReasonCodes.GetReasonCodesResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.reasonCode.ReasonCodeVO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;

/**
 * This class would act as a Servicer class for Get Reason Codes
 * @author Infosys
 * @version 1.0
 */



@APIResponseClass(GetReasonCodesResponse.class)
public class GetReasonCodesService extends BaseService{
	private Logger logger = Logger.getLogger(GetReasonCodesService.class);
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericValidator mobileGenericValidator;
	private MobileGenericMapper mobileGenericMapper;
	
	/**
	 * This method handle the main logic for fetching reasonCodes.
	 * @param apiVO
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		//Declaring variables
		GetReasonCodesResponse response = new GetReasonCodesResponse();
		List<ReasonCode> reasonCodeList = new ArrayList<ReasonCode>();
		List<String> reasonLists = new ArrayList<String>();
		List<String> reasonListsDB = new ArrayList<String>();
		boolean isValidreasonCodes = false;
		String reasonCodeType = null;
		List<String> invalidReasonCodeTypes =null;
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		//Getting reasonCode type passed as a query @param in API request
		if(null!= requestMap){
			reasonCodeType =requestMap.get(PublicMobileAPIConstant.SO_REASONTYPE_LIST);
		}
		try{
			/* Fetching all reasonCode type to use 
			    1) the provided reasonCode type is empty/not provided reasonCode type
			    2) Validating if reasonCode type is not  empty */
			reasonListsDB = mobileGenericBO.getResonCodeType();
	        // Map resaonCode Types to list.
			reasonLists = mobileGenericMapper.mapReasonCodesTypeToList(reasonCodeType);
			if(reasonLists.isEmpty()){
				//Throw exception if reasonCode type is not present in DB.
				if(null==reasonListsDB || reasonListsDB.isEmpty()){
					throw new Exception("There is no reasonType is present in Database");
				}else{
					reasonLists.addAll(reasonListsDB);
					isValidreasonCodes = true;
				}
				
			}else{
				//validate the provided reasonCode type is valid or not.validating against entry in lu_mobile_reasoncode_types.
				invalidReasonCodeTypes = mobileGenericValidator.validateGetReasonCodes(reasonLists,reasonListsDB);
			}
			if(null == invalidReasonCodeTypes || invalidReasonCodeTypes.isEmpty()){
			   // write code for fetching reasoncodes
				for (String reasonType : reasonLists) {
				  //Fetching reasonCodes types for the reasonCode type.
				  List<ReasonCodeVO>codesList= mobileGenericBO.getProviderReasoncodes(reasonType);
				  //Mapping reasonCodes to response.
				  ReasonCode reasonCode = mobileGenericMapper.mapReasonCode(codesList,reasonType);
				  reasonCodeList.add(reasonCode);
				 }
				//Creating success response
				response = mobileGenericMapper.createReasonCodeSuccessResponse(reasonCodeList);
			}
			else{
				//Creating error response.
				ArrayList<Object> errorArgumentList = new ArrayList<Object>();
				String errorReasons = invalidReasonCodeTypes.toString();
				errorArgumentList.add(errorReasons);
				Results results = Results.getError(
							ResultsCode.INVALID_REASONCODE_TYPE
							.getMessage(errorArgumentList),
							ResultsCode.INVALID_REASONCODE_TYPE
							.getCode());
				response.setResults(results);
				}
	     }	
		catch (Exception e) {
			logger.error("Exception in fetching reasonCodes-->" + e.getMessage(), e);
			//Creating error response.
			response = mobileGenericMapper.createErrorResponseReasonCode(ResultsCode.INTERNAL_SERVER_ERROR);
		}
		return response;
		
	}
	
	
	

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}

	public void setMobileGenericValidator(
			MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}
	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}
}