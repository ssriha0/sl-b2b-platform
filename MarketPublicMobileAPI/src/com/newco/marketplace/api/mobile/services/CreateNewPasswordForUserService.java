package com.newco.marketplace.api.mobile.services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.createNewPassword.CreateNewPasswordRequest;
import com.newco.marketplace.api.mobile.beans.createNewPassword.CreateNewPasswordResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.vo.mobile.UserProfileData;
import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.vo.provider.LoginVO;


@APIRequestClass(CreateNewPasswordRequest.class)
@APIResponseClass(CreateNewPasswordResponse.class)
public class CreateNewPasswordForUserService  extends BaseService{

	private static final Logger logger = Logger.getLogger(CreateNewPasswordForUserService.class.getName());
	private IAuthenticateUserBO authenticateUserBO;
	private IMobileSOManagementBO mobileSOManagementBO;

	public CreateNewPasswordForUserService() {
		super();

	}
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		
		CreateNewPasswordResponse createNewPasswordResponse = new CreateNewPasswordResponse();
		// TODO Auto-generated method stub
		// To validate if the resource Id is valid
		//if a firmId corresponding to the resourceId is obtained, then the resourceId is valid.
		Results result = null;
		try{
			if (null == apiVO.getProviderResourceId() || 0 == apiVO.getProviderResourceId()) {
				logger.info("Provider Resource Id is blank."	);
				result = Results.getError(ResultsCode.SO_SEARCH_INVALID_PROVIDER.getMessage(),ResultsCode.SO_SEARCH_INVALID_PROVIDER.getCode());
				createNewPasswordResponse.setResults(result);
				return createNewPasswordResponse;
			}
			logger.info("Provider Resource Id : " + apiVO.getProviderResourceId());
			Integer firmId = mobileSOManagementBO.validateProviderId(apiVO.getProviderResourceId().toString());
			if (null == firmId) {
				logger.info("Firm Id is blank."	);
				result = Results.getError(ResultsCode.SO_SEARCH_INVALID_PROVIDER.getMessage(),ResultsCode.SO_SEARCH_INVALID_PROVIDER.getCode());
				createNewPasswordResponse.setResults(result);
				return createNewPasswordResponse;
			}
			logger.info("Firm Id : " +	firmId);
			CreateNewPasswordRequest createNewPasswordRequest = (CreateNewPasswordRequest) apiVO.getRequestFromPostPut();
			//TODO : Code to get check the user Id passed and  userId fetched from provider resource Id to match
			//Fetch the current password and userId from the provider resource ID if valid
			SecurityContext securityContext = null;
			securityContext = getSecurityContextForVendor(apiVO.getProviderResourceId());
			
			//Validation on the request
			if(null != createNewPasswordRequest){
				//resourceId = soAddNoteRequest.getProviderId();
				logger.info("Request not null.");
							
				if(StringUtils.isBlank(createNewPasswordRequest.getUserId())){
					result= Results.getError(ResultsCode.INVALID_USERID.getMessage(),ResultsCode.INVALID_USERID.getCode());
					createNewPasswordResponse.setResults(result);
					return createNewPasswordResponse;
				}
				if(!StringUtils.isBlank(createNewPasswordRequest.getUserId())){
					if(!createNewPasswordRequest.getUserId().trim().equals(securityContext.getUsername())){
						result= Results.getError(ResultsCode.INVALID_USERID.getMessage(),ResultsCode.INVALID_USERID.getCode());
						createNewPasswordResponse.setResults(result);
						return createNewPasswordResponse;
					}
					
				}
				LoginVO loginVO = authenticateUserBO.getLoginDetails(createNewPasswordRequest.getUserId());
				// Checking whether User Account is Locked or not
				if (null != loginVO && null != loginVO.getLockedInd() && loginVO.getLockedInd().trim().equals("1")) {
					logger.info("Get Locked Ind : " + loginVO.getLockedInd());
					result= Results.getError(ResultsCode.LOGIN_ATTEMPTS_EXCEEDED_PROVIDER.getMessage(),ResultsCode.LOGIN_ATTEMPTS_EXCEEDED_PROVIDER.getCode());
					createNewPasswordResponse.setResults(result);
					return createNewPasswordResponse;
				}
				
				// Checking whether User Account is Locked or not
				//if(StringUtils.isBlank(createNewPasswordRequest.getCurrentPassword())){
				if(null == createNewPasswordRequest.getCurrentPassword()){
					result= Results.getError(ResultsCode.INVALID_CURRENT_PASSWORD.getMessage(),ResultsCode.INVALID_CURRENT_PASSWORD.getCode());
					createNewPasswordResponse.setResults(result);
					return createNewPasswordResponse;
				}
				/*Can be removed if not required*/
				//if(!StringUtils.isBlank(createNewPasswordRequest.getCurrentPassword())){
				if(null != createNewPasswordRequest.getCurrentPassword()){
					//Write the code to check the current password with DB
					UserProfileData userProfileData = authenticateUserBO.getProviderUserProfileByUserName(createNewPasswordRequest.getUserId().trim());
					if(null != userProfileData){
						String encryptedCurrentPasswordPassed = encrypt(createNewPasswordRequest.getCurrentPassword());
						if(!encryptedCurrentPasswordPassed.equals(userProfileData.getPassword())){
						//if(!createNewPasswordRequest.getCurrentPassword().trim().equals(userProfileData.getPassword())){
							result= Results.getError(ResultsCode.INVALID_CURRENT_PASSWORD.getMessage(),ResultsCode.INVALID_CURRENT_PASSWORD.getCode());
							createNewPasswordResponse.setResults(result);
							return createNewPasswordResponse;
						}
					}else{
						result= Results.getError(ResultsCode.INVALID_CURRENT_PASSWORD.getMessage(),ResultsCode.INVALID_CURRENT_PASSWORD.getCode());
						createNewPasswordResponse.setResults(result);
						return createNewPasswordResponse;
					}
				}
				/**/
				//if(StringUtils.isBlank(createNewPasswordRequest.getPassword())){
				if(null == createNewPasswordRequest.getPassword()){
					result= Results.getError(ResultsCode.INVALID_PASSWORD.getMessage(),ResultsCode.INVALID_PASSWORD.getCode());
					createNewPasswordResponse.setResults(result);
					return createNewPasswordResponse;
				}
				//Passwords must be at least 8 characters long and must have a combination of letters with numbers or 
				//letters with special characters such as - # @ ! + / ..
				if(null != createNewPasswordRequest.getPassword() && createNewPasswordRequest.getPassword().length() < 8 ){
					result= Results.getError(ResultsCode.INVALID_PASSWORD_LENGTH.getMessage(),ResultsCode.INVALID_PASSWORD_LENGTH.getCode());
					createNewPasswordResponse.setResults(result);
					return createNewPasswordResponse;
				}
				if(null != createNewPasswordRequest.getPassword()){
					if(!validPasswordCriteria(createNewPasswordRequest.getPassword())){
						result= Results.getError(ResultsCode.INVALID_PASSWORD_CRITERIA.getMessage(),ResultsCode.INVALID_PASSWORD_CRITERIA.getCode());
						createNewPasswordResponse.setResults(result);
						return createNewPasswordResponse;
					}	
				}
								
				if(null == createNewPasswordRequest.getConfirmPassword()){
					result= Results.getError(ResultsCode.INVALID_CONFIRM_PASSWORD.getMessage(),ResultsCode.INVALID_CONFIRM_PASSWORD.getCode());
					createNewPasswordResponse.setResults(result);
					return createNewPasswordResponse;
				}
				if(!createNewPasswordRequest.getPassword().equals(createNewPasswordRequest.getConfirmPassword())){
					result= Results.getError(ResultsCode.INVALID_PASSWORD_AND_CONFIRMPASSWORD.getMessage(),ResultsCode.INVALID_PASSWORD_AND_CONFIRMPASSWORD.getCode());
					createNewPasswordResponse.setResults(result);
					return createNewPasswordResponse;
				}
				if(null == createNewPasswordRequest.getSecurityQuestionId() || createNewPasswordRequest.getSecurityQuestionId() == 0){
					result= Results.getError(ResultsCode.INVALID_SECURITY_QUESTION.getMessage(),ResultsCode.INVALID_SECURITY_QUESTION.getCode());
					createNewPasswordResponse.setResults(result);
					return createNewPasswordResponse;
				}
				if(StringUtils.isBlank(createNewPasswordRequest.getSecurityAnswer())){
					result= Results.getError(ResultsCode.INVALID_SECURITY_ANSWER.getMessage(),ResultsCode.INVALID_SECURITY_ANSWER.getCode());
					createNewPasswordResponse.setResults(result);
					return createNewPasswordResponse;
				}
				if(null != createNewPasswordRequest.getPassword()){
					//Code to check whether Password should be same as the last four passwords
					//Convert Password and ConfirmPassword in SHA
					//CryptoUtil cryptoUtil = new CryptoUtil();
					//String encryptedPassword = cryptoUtil.encrypt(createNewPasswordRequest.getPassword());
					
					String encryptedPassword = encrypt(createNewPasswordRequest.getPassword());
					logger.info("EncryptedPassword : " + encryptedPassword);
					
					ChangePasswordVO changePasswordVO = new ChangePasswordVO();
					
					if(!StringUtils.isBlank(encryptedPassword)){
						changePasswordVO.setUserName(createNewPasswordRequest.getUserId().trim());
						changePasswordVO.setPassword(encryptedPassword);
						changePasswordVO.setSecretQuestion(createNewPasswordRequest.getSecurityQuestionId().toString());
						changePasswordVO.setSecretAnswer(createNewPasswordRequest.getSecurityAnswer().trim());

						if(!authenticateUserBO.updatePassword(changePasswordVO)){
							result= Results.getError(ResultsCode.INVALID_PASSWORD_CANNOT_BESAME.getMessage(),ResultsCode.INVALID_PASSWORD_CANNOT_BESAME.getCode());
							createNewPasswordResponse.setResults(result);
							return createNewPasswordResponse;
						}
					}else{
						result= Results.getError(ResultsCode.CREATE_NEW_PASSWORD_FAILED.getMessage(),ResultsCode.CREATE_NEW_PASSWORD_FAILED.getCode());
						createNewPasswordResponse.setResults(result);
						return createNewPasswordResponse;
					}
				}
				Results results=Results.getSuccess(ResultsCode.CREATE_NEW_PASSWORD_SUCCESS.getMessage());
				createNewPasswordResponse.setResults(results);
					
			}
			else{
				createNewPasswordResponse.setResults(Results.getError(ResultsCode.CREATE_NEW_PASSWORD_FAILED.getMessage(),ResultsCode.CREATE_NEW_PASSWORD_FAILED.getCode()));
			}
		}
		catch(Exception ex){
			Results results= Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			createNewPasswordResponse.setResults(results);								
		}	
		return createNewPasswordResponse;
	
	}
	private boolean validPasswordCriteria(String password) {
		// TODO Auto-generated method stub
		Pattern letter = Pattern.compile("[a-zA-z]");  
		Pattern digit = Pattern.compile("[0-9]");  
		Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");  

		Matcher hasLetter = letter.matcher(password);  
		Matcher hasDigit = digit.matcher(password);  
		Matcher hasSpecial = special.matcher(password);  
		boolean flagLetter = hasLetter.find();
		boolean flagDigit  = hasDigit.find();
		boolean flagSpecial = hasSpecial.find();

		if((flagLetter &&  flagDigit) || (flagLetter &&  flagSpecial)){
			return true;
		}else{
			return false;
		}
	}
	private String encrypt(String plaintext)  {

		MessageDigest md = null;
	    try {
	    	md = MessageDigest.getInstance("SHA-384"); // step 2
	    }
	    catch(NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	    	//throw new DataServiceException("Error creating MD5 Hash");
	    }
	    try {
	      md.update(plaintext.getBytes("UTF-8")); // step 3
	    }
	    catch(UnsupportedEncodingException e) {
			e.printStackTrace();
	    	//throw new DataServiceException("Error creating MD5 Hash");
	    }

	    byte raw[] = md.digest(); // step 4
	    String hash = (new BASE64Encoder()).encode(raw); // step 5
	    return hash; // step 6
	}
	
	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}
	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}
	public IMobileSOManagementBO getMobileSOManagementBO() {
		return mobileSOManagementBO;
	}
	public void setMobileSOManagementBO(IMobileSOManagementBO mobileSOManagementBO) {
		this.mobileSOManagementBO = mobileSOManagementBO;
	}



}
