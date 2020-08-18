package com.newco.marketplace.api.mobile.services.v3_0;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ValidateSecQuestAnsRequest;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ValidateSecQuestAnsResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.vo.mobile.v2_0.SecQuestAnsRequestVO;


@APIRequestClass(ValidateSecQuestAnsRequest.class)
@APIResponseClass(ValidateSecQuestAnsResponse.class)
public class ValidateSecQuestAnsService  extends BaseService{
	
	private static final Logger LOGGER = Logger.getLogger(ValidateSecQuestAnsService.class);
	private MobileGenericValidator mobileGenericValidator;
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;
	
	
	@Override
	public IAPIResponse execute(final APIRequestVO apiVO) {
		ValidateSecQuestAnsResponse validateSecQuestAnsResponse = null;
		final ValidateSecQuestAnsRequest validateSecQuestAnsRequest = (ValidateSecQuestAnsRequest) apiVO.getRequestFromPostPut();
		
		Results results = null;
		try{
			SecQuestAnsRequestVO  secQuestAnsRequestVO = mobileGenericMapper.mapSecQuestAnsRequest(validateSecQuestAnsRequest);

			validateSecQuestAnsResponse = mobileGenericValidator.validateSecQuestAnsRequest(secQuestAnsRequestVO);
			if(null == validateSecQuestAnsResponse){
				if(null!= secQuestAnsRequestVO){
					//mapping the correct parameters after validation
					secQuestAnsRequestVO.setUserName(secQuestAnsRequestVO.getUserNameFromDB());
					secQuestAnsRequestVO.setEmail(secQuestAnsRequestVO.getEmailFromDB());

					if(!secQuestAnsRequestVO.isAdditionalVerification()){
						secQuestAnsRequestVO = mobileGenericBO.validateSecQuestAnsRequest(secQuestAnsRequestVO);
					}
					else{
						secQuestAnsRequestVO = mobileGenericBO.validateAdditionalVerification(secQuestAnsRequestVO);
					}
					validateSecQuestAnsResponse = mobileGenericMapper.validateSecQuestAnsResponse(secQuestAnsRequestVO);
					
				}
			}
		}catch (Exception ex) {
			LOGGER.info("Severe Exception occured while ValidateSecQuestAnsService");
			ex.printStackTrace();
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			validateSecQuestAnsResponse = new ValidateSecQuestAnsResponse();
			validateSecQuestAnsResponse.setResults(results);
		}

		return validateSecQuestAnsResponse;
	}

	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}

	public void setMobileGenericValidator(
			final MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(final IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(final MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}

}
