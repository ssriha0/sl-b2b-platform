/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	pgangra   SHC				1.0
 * 
 * 
 */

package com.newco.marketplace.api.services.login;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.login.LoginFailureResult;
import com.newco.marketplace.api.beans.login.LoginRequest;
import com.newco.marketplace.api.beans.login.LoginResponse;
import com.newco.marketplace.api.beans.login.LoginResult;
import com.newco.marketplace.api.beans.login.SimpleBuyer;
import com.newco.marketplace.api.beans.login.SimpleBuyerLoginResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.provider.ILoginBO;
import com.newco.marketplace.business.iBusiness.provider.ITeamCredentialBO;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.common.LoginUserProfile;
import com.newco.marketplace.vo.provider.LoginVO;
import com.newco.marketplace.vo.provider.VendorResource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This class is responsible for creating new BuyerAccount.
 * 
 * @author infosys
 * 
 */
public class LoginService extends BaseService {
	private ILoginBO loginBO;
	private Logger logger = Logger.getLogger(LoginService.class);
	private ITeamCredentialBO teamCredentialBO;
	private String b2cURL;
	private String slProxyHost;
	private int slProxyPort;
	private boolean proxyEnabled;
	
	/**
	 * This method is for LoginAPI.
	 * 
	 */
	public LoginService() {
		super(PublicAPIConstant.LOGIN_REQUEST_XSD,
				PublicAPIConstant.LOGIN_RESPONSE_XSD,
				PublicAPIConstant.BuyerAccount.NAMESPACE,
				PublicAPIConstant.BuyerAccount.RESOURCES_SCHEMAS,
				PublicAPIConstant.LOGIN_SCHEMALOCATION, LoginRequest.class,
				LoginResponse.class);
	}

	/**
	 * Subclass needs to implement API specific logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		LoginRequest request = null;
		LoginResponse response = new LoginResponse();
		LoginUserProfile loginUserProfile = null;
		LoginVO loginVO = new LoginVO();
		int loginResponse = -1;
		
		request = (LoginRequest) apiVO.getRequestFromPostPut();
		
		//R11.2 SL-19704
		if(null != request && null != request.getLogin() && null != request.getLogin().getUserName()){
			request.getLogin().setUserName(request.getLogin().getUserName().trim());
		}
		
		if (!StringUtils.isEmpty(request.getLogin().getUserName())) {
			if (!StringUtils.isEmpty(request.getLogin().getPassword())) {
				if (!request.getLogin().getType().equalsIgnoreCase("simple")) {
					boolean tempPasswordStatus = loginBO
							.getTempPasswordIndicator(request.getLogin()
									.getUserName());
					// implemented as per the business logic found in
					// LoginAction.java
					if (tempPasswordStatus) {
						loginVO.setPassword(CryptoUtil.generateHash(request
								.getLogin().getPassword().trim()));
					} else {
						loginVO.setPassword(CryptoUtil.generateHash(request
								.getLogin().getPassword().trim()));
					}
					loginVO.setUsername(request.getLogin().getUserName());
					try {
						loginResponse = loginBO.validatePassword(loginVO);
						if (loginResponse == 0) {
							
							if (request.getLogin().getType().equals("pro")) {
								loginUserProfile = loginBO
										.getLoginInfoForProvider(request
												.getLogin().getUserName());
    							VendorResource vendorResource=new VendorResource();
								vendorResource.setResourceId(Integer.parseInt(loginUserProfile.getResourceId()));
								vendorResource=teamCredentialBO.queryResourceById(vendorResource);
								if(null!=vendorResource.getPictureUrl())
								loginUserProfile.setProfilePic(vendorResource.getPictureUrl());
								
							} else {
								loginUserProfile = loginBO
										.getLoginInfoForBuyer(request
												.getLogin().getUserName());
							}
							response = mapLoginValues(loginUserProfile,
									response);

							response.setResults(Results.getSuccess());
						} else if (loginResponse == -1) {
							// invalid password
							response
									.setResults(Results.getError(
											ResultsCode.LOGIN_INCORRECT_USER
													.getMessage(),
											ResultsCode.LOGIN_INCORRECT_USER
													.getCode()));
						} else if (loginResponse == 2) {
							// account locked
							response.setResults(Results.getError(
									ResultsCode.LOGIN_USER_ACCOUNT_LOCKED
											.getMessage(),
									ResultsCode.LOGIN_USER_ACCOUNT_LOCKED
											.getCode()));
						} else if (loginResponse == 1) {
							// temp password wrong
							response
									.setResults(Results.getError(
											ResultsCode.LOGIN_INCORRECT_USER
													.getMessage(),
											ResultsCode.LOGIN_INCORRECT_USER
													.getCode()));
						} else if (loginResponse == -2) {
							// user not verified
							response.setResults(Results.getError(
									ResultsCode.LOGIN_USER_NOT_VERIFIED
											.getMessage(),
									ResultsCode.LOGIN_USER_NOT_VERIFIED
											.getCode()));
						} else {
							// all other cases
							response.setResults(Results.getError(
									ResultsCode.LOGIN_USER_NOT_VERIFIED
											.getMessage(),
									ResultsCode.LOGIN_USER_NOT_VERIFIED
											.getCode()));
						}
					} catch (Exception e) {
						logger.error("Exception in login occured", e);
						response.setResults(Results.getError(
								ResultsCode.LOGIN_INCORRECT_USER.getMessage(),
								ResultsCode.LOGIN_INCORRECT_USER.getCode()));
					}
				} else {
					  // Create an instance of HttpClient.
				    HttpClient client = new HttpClient();
				    String userName =request.getLogin().getUserName().trim();
					String password = request.getLogin().getPassword().trim();
					
					String url = getB2cURL().trim()+"/home/verifyBuyerCredentials.action?username=" + userName + "&password=" + password;
				    // Create a method instance.
				    GetMethod method = new GetMethod(url);
				    
				    // Provide custom retry handler is necessary
				    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
				    		new DefaultHttpMethodRetryHandler(3, false));
				    try {
				    	
				    	   int statusCode;
				    	   
				    	   if(isProxyEnabled()){
					    	   HostConfiguration hostconfig  = new HostConfiguration();    		
					    	   hostconfig.setProxy(getSlProxyHost().trim(),getSlProxyPort());
				    		   statusCode = client.executeMethod(hostconfig, method);
				    	   }else{
				    		   statusCode = client.executeMethod(method);
				    	   }

				      if (statusCode != HttpStatus.SC_OK) {
				        logger.error("Method failed: " + method.getStatusLine());
				      }

				      // Read the response body.
				      byte[] responseBody = method.getResponseBody();

				      // Deal with the response.
				      // Use caution: ensure correct character encoding and is not binary data

				     String xml=new String(responseBody).trim();
				     if(null!=xml)
				     {
				     	XStream xstream1 = new XStream(new DomDriver());
						Class<?>[] createClasses = new Class[] { SimpleBuyerLoginResponse.class,
								SimpleBuyer.class, LoginResult.class};
						xstream1.processAnnotations(createClasses);
						xstream1.alias("buyer", SimpleBuyer.class);
						xstream1.alias("verifyUserCredentialsResponse", SimpleBuyerLoginResponse.class);
						xstream1.alias("result", LoginResult.class);
						xstream1.alias("failure", LoginFailureResult.class);
						SimpleBuyerLoginResponse userCredentialsResponse = (SimpleBuyerLoginResponse) xstream1.fromXML(xml);
					if(null==userCredentialsResponse.getResults().getFailure())
					{
					response=mapLoginResponse(userCredentialsResponse);
					}
					else
					{
						if(PublicAPIConstant.ERRORCODE_USER_NOT_VERIFIED.
								equals(userCredentialsResponse.getResults().getFailure().getNumber()))
						{
							response.setResults(Results.getError(
									ResultsCode.LOGIN_USER_NOT_VERIFIED.getMessage(),
									ResultsCode.LOGIN_USER_NOT_VERIFIED.getCode()));
						}
						else if(PublicAPIConstant.ERRORCODE_USER_ACCOUNT_LOCKED.
								equals(userCredentialsResponse.getResults().getFailure().getNumber()))
						{
							response.setResults(Results.getError(
									ResultsCode.LOGIN_USER_ACCOUNT_LOCKED.getMessage(),
									ResultsCode.LOGIN_USER_ACCOUNT_LOCKED.getCode()));
						}else if(PublicAPIConstant.ERRORCODE_INCORRECT_USER.
								equals(userCredentialsResponse.getResults().getFailure().getNumber()))
						{
							response.setResults(Results.getError(
									ResultsCode.LOGIN_INCORRECT_USER.getMessage(),
									ResultsCode.LOGIN_INCORRECT_USER.getCode()));
						}else if(PublicAPIConstant.ERRORCODE_BUYER_NOT_ASSOCIATED.
								equals(userCredentialsResponse.getResults().getFailure().getNumber()))
						{
							response.setResults(Results.getError(
									ResultsCode.LOGIN_BUYER_NOT_ASSOCIATED.getMessage(),
									ResultsCode.LOGIN_BUYER_NOT_ASSOCIATED.getCode()));
						}
							
						
					}
				     }
				     else {
							response.setResults(Results.getError(
									ResultsCode.LOGIN_INCORRECT_USER.getMessage(),
									ResultsCode.LOGIN_INCORRECT_USER.getCode()));
						}
					  } catch (Exception e) {
						  logger.error("Exception in login occured", e);
							response.setResults(Results.getError(
									ResultsCode.LOGIN_INCORRECT_USER.getMessage(),
									ResultsCode.LOGIN_INCORRECT_USER.getCode()));
					    } 
					    finally {
					      // Release the connection.
					      method.releaseConnection();
					    }  
				}
			} else {
				response.setResults(Results.getError(
						ResultsCode.LOGIN_INCORRECT_USER.getMessage(),
						ResultsCode.LOGIN_INCORRECT_USER.getCode()));
			}
		} else {
			response.setResults(Results.getError(
					ResultsCode.LOGIN_INCORRECT_USER.getMessage(),
					ResultsCode.LOGIN_INCORRECT_USER.getCode()));
		}
		return response;
	}

	private LoginResponse mapLoginValues(LoginUserProfile loginUserProfile,
			LoginResponse response) {

		response.setEntityId(loginUserProfile.getEntityId());
		response.setResourceId(loginUserProfile.getResourceId());
		response.setFirstName(loginUserProfile.getFirstName());
		response.setLastname(loginUserProfile.getLastName());
		response.setProfilePic(loginUserProfile.getProfilePic());
		return response;
	}
	
	public LoginResponse mapLoginResponse(SimpleBuyerLoginResponse simpleBuyerLoginResponse)
	{
		LoginResponse response=new LoginResponse();
		response.setFirstName(simpleBuyerLoginResponse.getBuyer().getFirstName());
		response.setProfilePic(simpleBuyerLoginResponse.getBuyer().getProfilePicUuid());
		response.setResourceId(simpleBuyerLoginResponse.getBuyer().getBuyerResourceId());
		response.setEntityId(simpleBuyerLoginResponse.getBuyer().getGlobalBuyerId());
		response.setLastname(simpleBuyerLoginResponse.getBuyer().getLastName());
		response.setResults(Results.getSuccess());
		
		return response;
	}

	public ILoginBO getLoginBO() {
		return loginBO;
	}

	public void setLoginBO(ILoginBO loginBO) {
		this.loginBO = loginBO;
	}

	public ITeamCredentialBO getTeamCredentialBO() {
		return teamCredentialBO;
	}

	public void setTeamCredentialBO(ITeamCredentialBO teamCredentialBO) {
		this.teamCredentialBO = teamCredentialBO;
	}

	public String getB2cURL() {
		return b2cURL;
	}

	public void setB2cURL(String b2curl) {
		b2cURL = b2curl;
	}

	public String getSlProxyHost() {
		return slProxyHost;
	}

	public void setSlProxyHost(String slProxyHost) {
		this.slProxyHost = slProxyHost;
	}

	public int getSlProxyPort() {
		return slProxyPort;
	}

	public void setSlProxyPort(int slProxyPort) {
		this.slProxyPort = slProxyPort;
	}

	public boolean isProxyEnabled() {
		return proxyEnabled;
	}

	public void setProxyEnabled(boolean isProxyEnabled) {
		this.proxyEnabled = isProxyEnabled;
	}
	
}
