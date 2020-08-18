package com.newco.marketplace.api.mobile.services.v3_0;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceRequest;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceResponse;
import com.newco.marketplace.api.mobile.beans.vo.ForgetUnamePwdVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;


@APIRequestClass(ForgetUnamePwdServiceRequest.class)
@APIResponseClass(ForgetUnamePwdServiceResponse.class)
public class ForgetUnamePwdService  extends BaseService{
	
	private static final Logger LOGGER = Logger.getLogger(ForgetUnamePwdService.class);
	private MobileGenericValidator mobileGenericValidator;
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;
	
	
	@Override
	public IAPIResponse execute(final APIRequestVO apiVO) {

		ForgetUnamePwdServiceResponse response = new ForgetUnamePwdServiceResponse();
		final ForgetUnamePwdServiceRequest request = (ForgetUnamePwdServiceRequest) apiVO.getRequestFromPostPut();
		
		Results results = null;
		try{
			//validate
			response = mobileGenericValidator.validateForgetUnamePwdServiceRequest(request, response);
			results = response.getResults();
			if(null!= results && null != results.getError() && results.getError().size()>0){
				response.setResults(results);
			}
			else {
				//Request to VO mapper
				ForgetUnamePwdVO forgetUnamePwdVO = mobileGenericMapper.mapForgetUnamePwdService(request);
				
				// 1.	Forgot Password with emailId and userName input to get the corresponding security question of the userName.
				if(PublicMobileAPIConstant.REQUEST_FOR_PASSWORD.equalsIgnoreCase(forgetUnamePwdVO.getRequestFor())){
					
					//load user profile
					//forgetUnamePwdVO = mobileGenericBO.loadUserProfile(forgetUnamePwdVO);
					//validate 
					response = mobileGenericValidator.validateForgetUnamePwdServiceForPassword(forgetUnamePwdVO, response);
					
					results = response.getResults();
					if(null!= results && null != results.getError() && results.getError().size()>0){
						response.setResults(results);
						if (null!= results.getError().get(0) 
								&& null!= results.getError().get(0).getCode() 
								&& ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP.getCode().equalsIgnoreCase(results.getError().get(0).getCode())){
									response = mobileGenericMapper.mapUserId(response, forgetUnamePwdVO);
								}
					}else{
						//in case of pwd_ind=1 generate temp pwd and send reset mail
						//in case of pwd_ind=0, means security question exists, return it
						forgetUnamePwdVO = mobileGenericBO.getResponseForPasswordReset(forgetUnamePwdVO);
						response = mobileGenericMapper.mapUserDetailsListResponse(response, forgetUnamePwdVO);
					
					}
					
				}else if(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_EMAIL.equalsIgnoreCase(forgetUnamePwdVO.getRequestFor())){//Forget Username input
					//response = mobileGenericValidator.validateForgetUnamePwdService(forgetUnamePwdVO, response);
					
						// 2.	Forgot UserName with emailId input to return the user list. 
						// If in this case only one user exists, then return the security question of the corresponding userId.
						if(StringUtils.isNotBlank(forgetUnamePwdVO.getEmail()) 
								&& StringUtils.isBlank(forgetUnamePwdVO.getUserId())){
							forgetUnamePwdVO = mobileGenericBO.getResponseForForgetUserNameForEmail(forgetUnamePwdVO);
							response = mobileGenericMapper.mapUserDetailsListResponse(response, forgetUnamePwdVO);
							
						}
				}
						// 3.	Forgot UserName with emailId, userId input to return the security question of the corresponding userId. 
						// This is a second hit to the API with requestFor=UserName, only required in case of Multiple users exists for same email id.
				else if(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_USERID.equalsIgnoreCase(forgetUnamePwdVO.getRequestFor())){
							forgetUnamePwdVO = mobileGenericBO.getResponseForForgetUserName(forgetUnamePwdVO);
							response = mobileGenericMapper.mapUserDetailsListResponse(response, forgetUnamePwdVO);
						}
				
				}
		}catch (Exception ex) {
			LOGGER.error("Severe Exception occured while forget username service 1 ");
			LOGGER.error(ex.getMessage());
			ex.printStackTrace();
			results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
		}

		return response;
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
