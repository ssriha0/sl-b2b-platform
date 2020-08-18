package com.newco.marketplace.api.mobile.services.v2_0;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.authenticateMobileVersionCheck.v2_0.AuthenticateMobileVersionRequest;
import com.newco.marketplace.api.mobile.beans.authenticateMobileVersionCheck.v2_0.AuthenticateMobileVersionResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.AuthenticateUserMapper;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.mobile.AppVersionData;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceDataList;

@APIRequestClass(AuthenticateMobileVersionRequest.class)
@APIResponseClass(AuthenticateMobileVersionResponse.class)
public class AuthenticateMobileVersionService extends BaseService {

	/**
	 * Constructor
	 */

	public AuthenticateMobileVersionService() {
		 super();
	}

	private Logger logger = Logger.getLogger(AuthenticateMobileVersionService.class);
	private IAuthenticateUserBO authenticateUserBO;
	int unsuccessLoginInd = 0;

	private AuthenticateUserMapper authenticateUserMapper;

	public AuthenticateUserMapper getAuthenticateUserMapper() {
		return authenticateUserMapper;
	}

	public void setAuthenticateUserMapper(
			AuthenticateUserMapper authenticateUserMapper) {
		this.authenticateUserMapper = authenticateUserMapper;
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		AuthenticateMobileVersionRequest authenticateMobileVersionRequest= (AuthenticateMobileVersionRequest)apiVO.getRequestFromPostPut();
		AuthenticateMobileVersionResponse authenticateMobileVersionResponse = new AuthenticateMobileVersionResponse();
		String deviceOS = "";
		String currentAppVersion = "";
		List<Result> successMsg = new ArrayList<Result>();
		Results results = new Results();
		
		String doVersionValidation = PublicMobileAPIConstant.FALSE;
		try {
			doVersionValidation = authenticateUserBO.
					discontinueOldVersion(PublicMobileAPIConstant.DO_VERSION_VALIDATION_OF_MOBILE_APP);
			
			
			if(null!= doVersionValidation && !StringUtils.isBlank(doVersionValidation) &&
					StringUtils.equalsIgnoreCase(doVersionValidation,PublicMobileAPIConstant.TRUE)){
				
				
				String currentVersion = authenticateMobileVersionRequest.getCurrentAppVersion();
				
				
				// Make sure the request has the required information to do the validation
				// Assumption : Version will be like 2.0.0. i.e total 5 characters
				// TODO : Create a regex for 2.0.0
				if(StringUtils.isNotBlank(authenticateMobileVersionRequest.getDeviceOS()) && 
						StringUtils.isNotBlank(currentVersion) && currentVersion.length() <= 5){
					
					deviceOS = authenticateMobileVersionRequest.getDeviceOS();
					currentAppVersion = authenticateMobileVersionRequest.getCurrentAppVersion();
					
					// Validate the current version of the app
					AppVersionData appVersionData= authenticateUserBO.validateAppVersion
						(deviceOS, currentAppVersion);
					if(null!=appVersionData){
						String baseAppVersion = appVersionData.getBaseAppVersion();
						String latestAppVersion = appVersionData.getLatestAppVersion();						
						int baseAppVersionInt = Integer.parseInt( baseAppVersion.replaceAll( "\\.", "" ) );
						int latestAppVersionInt = Integer.parseInt( latestAppVersion.replaceAll( "\\.", "" ) );
						int currentAppVersionInt = Integer.parseInt( currentAppVersion.replaceAll( "\\.", "" ) );
						//checking if current app version with base version and latest version
						if(currentAppVersionInt< baseAppVersionInt){
							successMsg=addSuccess(successMsg, ResultsCode.INVALID_APP_VERSION.getCode(),
									ResultsCode.INVALID_APP_VERSION.getMessage());
							results.setResult(successMsg);						
						}else if(currentAppVersionInt<latestAppVersionInt){
							successMsg=addSuccess(successMsg, ResultsCode.NOT_LATEST_APP_VERSION.getCode(),
									ResultsCode.NOT_LATEST_APP_VERSION.getMessage());
							results.setResult(successMsg);
						}else{
							successMsg=addSuccess(successMsg, ResultsCode.LATEST_APP_VERSION.getCode(),
									ResultsCode.LATEST_APP_VERSION.getMessage());
							results.setResult(successMsg);						
						}
						authenticateMobileVersionResponse.setResults(results);
						return authenticateMobileVersionResponse;
					}else{
						successMsg=addSuccess(successMsg, ResultsCode.INVALID_APP_VERSION.getCode(),
								ResultsCode.INVALID_APP_VERSION.getMessage());
						results.setResult(successMsg);	
						authenticateMobileVersionResponse.setResults(results);
						return authenticateMobileVersionResponse;
					}
					
				}else{	
					logger.info("Mobile app versions provided in the request is not valid.");
					successMsg=addSuccess(successMsg, ResultsCode.INVALID_APP_VERSION.getCode(),
							ResultsCode.INVALID_APP_VERSION.getMessage());
					results.setResult(successMsg);	
					authenticateMobileVersionResponse.setResults(results);
					return authenticateMobileVersionResponse;
				}
			}else{
				
				logger.info("Mobile app versions provided in the request is not valid.");
				successMsg=addSuccess(successMsg, ResultsCode.LATEST_APP_VERSION.getCode(),
						ResultsCode.LATEST_APP_VERSION.getMessage());
				results.setResult(successMsg);
				authenticateMobileVersionResponse.setResults(results);
				return authenticateMobileVersionResponse;
			}
		} catch (Exception e1) {
			logger.error("AuthenticateMobileVersionService-->execute()--> Exception-->Validate version"
					+ e1.getMessage(), e1);
			authenticateMobileVersionResponse.setResults(Results.getError(
					ResultsCode.UNABLE_TO_PROCESS_REQUEST.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS_REQUEST.getCode()));
			return authenticateMobileVersionResponse;
		}
		
		//return authenticateMobileVersionResponse;
	}
	
	
	
	public MobileDeviceDataList fetchMobileDevices(String deviceName){
		
		try {
			MobileDeviceDataList  mobileDeviceDataList =authenticateUserBO.fetchMobileDevices(deviceName);
			return mobileDeviceDataList;
		} catch (BusinessServiceException e) {
			logger.info("Exception while fetching the device data" + e);
		}
		
		return null;
	}
	
	
	

	public void updateMobileDeviceAppVersion(MobileDeviceDataList mobileDeviceDataList){
		
		try {
			authenticateUserBO.updateMobileDeviceAppVersion(mobileDeviceDataList);
			
		} catch (BusinessServiceException e) {
			logger.info("Exception while updating the app version" + e);
		}
		
		
	}
	private List<Result> addSuccess(List<Result> sucesses, String code,
			String message) {
		Result result = new Result();
		result.setCode(code);
		result.setMessage(message);
		sucesses.add(result);
		return sucesses;
	}

	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}

	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}

}
