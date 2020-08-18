package com.newco.marketplace.api.mobile.services;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.GetProviderSOListResponse;
import com.newco.marketplace.api.mobile.beans.authenticateUser.LoginProviderRequest;
import com.newco.marketplace.api.mobile.beans.authenticateUser.LoginProviderResponse;
import com.newco.marketplace.api.mobile.beans.authenticateUser.UserProviderDetails;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.AuthenticateUserMapper;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.vo.mobile.UserProfileData;
import com.newco.marketplace.vo.provider.LoginVO;

@APIRequestClass(LoginProviderRequest.class)
@APIResponseClass(LoginProviderResponse.class)
public class LoginProviderService extends BaseService {

	/**
	 * Constructor
	 */

	public LoginProviderService() {
//		super(PublicMobileAPIConstant.PRO_LOGIN_REQ_XSD,
//				PublicMobileAPIConstant.PRO_LOGIN_REP_XSD,
//				PublicMobileAPIConstant.MOBILE_SERVICES_NAMESPACE,
//				PublicMobileAPIConstant.MOBILE_SERVICES_SCHEMA,
//				PublicMobileAPIConstant.LOGIN_PROVIDER_REP_SCHEMALOCATION,
//				LoginProviderRequest.class, LoginProviderResponse.class);
		 super();
	}

	private Logger logger = Logger.getLogger(LoginProviderService.class);
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

		LoginProviderResponse loginProviderResponse = new LoginProviderResponse();
		
		// Return the error message if this version of the API is no 
		// longer used
		String discontinueOldVersion = PublicMobileAPIConstant.FALSE;
		
		try {
			discontinueOldVersion = authenticateUserBO.
				discontinueOldVersion(PublicMobileAPIConstant.DISCONTINUE_OLD_VERSION_OF_MOBILE_APP);
			
			if(null!= discontinueOldVersion && !StringUtils.isBlank(discontinueOldVersion) &&
					StringUtils.equalsIgnoreCase(discontinueOldVersion,PublicMobileAPIConstant.TRUE)){
				loginProviderResponse.setResults(Results.getError(
						ResultsCode.INVLID_APP_VERSION.getMessage(),
						ResultsCode.INVLID_APP_VERSION.getCode()));
				return loginProviderResponse;
			}	
		} catch (Exception e) {
			logger.error("LoginProviderService-->execute()--> Exception-->"
					+ e.getMessage(), e);
			loginProviderResponse.setResults(Results.getError(
					ResultsCode.UNABLE_TO_PROCESS_REQUEST.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS_REQUEST.getCode()));
		}
		

		LoginProviderRequest loginProviderRequest = (LoginProviderRequest) apiVO
				.getRequestFromPostPut();

		String userName = "";
		String password = "";
		String deviceId = "";
		LoginVO objLoginVO = new LoginVO();
		boolean updateLoginInd = false;

		if (null != loginProviderRequest
				&& null != loginProviderRequest.getUsername()
				&& null != loginProviderRequest.getPassword()
				&& StringUtils.isNotBlank(loginProviderRequest.getDeviceId())) {
			userName = loginProviderRequest.getUsername();
			password = loginProviderRequest.getPassword();
			deviceId = loginProviderRequest.getDeviceId();
			try {

				// Method to validate UserName and Password.Check whether user
				// name and password is blank.

				Integer validFlag = authenticateUserBO.validateUserCredentials(
						userName, password);
				if (null != validFlag && validFlag.intValue() == 0) {
					UserProfileData userProfileData = new UserProfileData();
					UserProviderDetails userDetails = new UserProviderDetails();
					// Check whether username and password is valid
					userProfileData = authenticateUserBO.authenticateUser(
							userName.trim(), password);
					if (null != userProfileData
							&& !userProfileData.isFlagForUsername()) {

						// Check for User role type
						if (userProfileData.getUserRoleType()
								.equals("Provider")) {

							objLoginVO.setUsername(userName);
							LoginVO loginVO = authenticateUserBO
									.getLoginDetails(userName);

							// Checking whether User Account is Locked or not
							if (null != loginVO
									&& null != loginVO.getLockedInd()
									&& !loginVO.getLockedInd().equals("1")) {

								if (null != userProfileData.getContactId()) {
									// Populate Contacts and location status
									userProfileData = authenticateUserMapper
											.populateDataForUser(
													loginProviderResponse,
													userProfileData);

									userDetails = authenticateUserMapper
											.populateUserResponse(userProfileData);

									// To set resource level
									Integer resourceLevel = authenticateUserBO
											.getResourceLevel(userName);
									if (null != resourceLevel) {
										userDetails
												.setResourceLevel(resourceLevel);
									}

									// To produce and persist unique token
									String token = authenticateUserBO
											.generateUniqueToken(deviceId
													.trim(), userProfileData
													.getResourceId(),null,null);
									if (null != token) {
										userDetails.setOuthToken(token);
									}

									// Updating successful login attempt
									objLoginVO.setUsername(userName);
									objLoginVO.setLockedInd("0");
									objLoginVO.setUnsuccessLoginInd("0");

									updateLoginInd = authenticateUserBO
											.updateLoginDetails(objLoginVO);

									// Success
									loginProviderResponse
											.setResults(Results
													.getSuccess("User Logged in Successfully"));
									loginProviderResponse
											.setUserDetails(userDetails);

								}
							} else {
								loginProviderResponse
										.setResults(Results
												.getError(
														ResultsCode.LOGIN_ATTEMPTS_EXCEEDED_PROVIDER
																.getMessage(),
														ResultsCode.LOGIN_ATTEMPTS_EXCEEDED_PROVIDER
																.getCode()));
							}

						} else {
							// Error Message : User is not a Provider
							loginProviderResponse
									.setResults(Results
											.getError(
													ResultsCode.INVALID_USERNAME_PASSWORD_PROVIDER
															.getMessage(),
													ResultsCode.INVALID_USERNAME_PASSWORD_PROVIDER
															.getCode()));
						}

					} else if (null != userProfileData
							&& userProfileData.isFlagForUsername()) {
						// Updating Unsuccessful login attempt when
						// Username is valid but Password is Invalid
						objLoginVO.setUsername(userName);
						LoginVO LoginVO = authenticateUserBO
								.getLoginDetails(userName);
						if (null != LoginVO
								&& null != LoginVO.getUnsuccessLoginInd()) {
							unsuccessLoginInd = Integer.parseInt(LoginVO
									.getUnsuccessLoginInd());
						}

						unsuccessLoginInd++;

						if (unsuccessLoginInd >= authenticateUserBO
								.getMaxLoginAttempts()) {
							objLoginVO.setLockedInd("1");
							loginProviderResponse
									.setResults(Results
											.getError(
													ResultsCode.LOGIN_ATTEMPTS_EXCEEDED_PROVIDER
															.getMessage(),
													ResultsCode.LOGIN_ATTEMPTS_EXCEEDED_PROVIDER
															.getCode()));
						} else {
							objLoginVO.setLockedInd("0");

							// Error Message : Not a valid User name and
							// password
							loginProviderResponse
									.setResults(Results
											.getError(
													ResultsCode.INVALID_USERNAME_PASSWORD_PROVIDER
															.getMessage(),
													ResultsCode.INVALID_USERNAME_PASSWORD_PROVIDER
															.getCode()));
						}
						objLoginVO.setUnsuccessLoginInd(Integer
								.toString(unsuccessLoginInd));

						updateLoginInd = authenticateUserBO
								.updateLoginDetails(objLoginVO);

					} else {// Error Message : Not a valid User name and
							// password
						loginProviderResponse.setResults(Results.getError(
								ResultsCode.INVALID_USERNAME_PASSWORD_PROVIDER
										.getMessage(),
								ResultsCode.INVALID_USERNAME_PASSWORD_PROVIDER
										.getCode()));

					}
				}

				else if (null != validFlag && validFlag == 1) {
					// Error Message : User name and Password both are blank
					loginProviderResponse.setResults(Results.getError(
							ResultsCode.BLANK_USERNAME_PROVIDER.getMessage(),
							ResultsCode.BLANK_USERNAME_PROVIDER.getCode()));
				} else if (null != validFlag && validFlag == 2) {
					// Error Message : User name is blank
					loginProviderResponse.setResults(Results.getError(
							ResultsCode.BLANK_USERNAME_PROVIDER.getMessage(),
							ResultsCode.BLANK_USERNAME_PROVIDER.getCode()));
				} else if (null != validFlag && validFlag == 3) {

					// Error Message : Password is blank
					loginProviderResponse.setResults(Results.getError(
							ResultsCode.BLANK_PASSWORD_PROVIDER.getMessage(),
							ResultsCode.BLANK_PASSWORD_PROVIDER.getCode()));

				}
			} catch (Exception e) {
				logger.error("LoginProviderService-->execute()--> Exception-->"
						+ e.getMessage(), e);
				loginProviderResponse.setResults(Results.getError(
						ResultsCode.UNABLE_TO_PROCESS_REQUEST.getMessage(),
						ResultsCode.UNABLE_TO_PROCESS_REQUEST.getCode()));
			}

		} else {
			loginProviderResponse.setResults(Results.getError(
					ResultsCode.UNABLE_TO_PROCESS_REQUEST.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS_REQUEST.getCode()));
		}

		return loginProviderResponse;
	}

	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}

	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}

}
