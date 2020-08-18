package com.newco.marketplace.api.beans.apiUserProfileAuth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.userProfile.IUserProfileBO;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.CryptoUtil.CannotPerformOperationException;
import com.newco.marketplace.utils.CryptoUtil.InvalidHashException;
import com.newco.marketplace.vo.apiUserProfile.FirmDetailsVO;
import com.newco.marketplace.vo.apiUserProfile.LocationDetails;
import com.newco.marketplace.vo.apiUserProfile.LocationResponseDate;
import com.newco.marketplace.vo.apiUserProfile.UserDetails;
import com.newco.marketplace.vo.apiUserProfile.UserProfileData;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;

public class UserAuthenticationService {

	private Logger logger = Logger.getLogger(UserAuthenticationService.class);
	private IUserProfileBO userAuthProfileBO;
	private boolean errorOccured = false;

	public UserAuthenticationResponse execute(LoginRequest loginRequest) throws Exception{
		logger.info("Entering UserAuthenticationService.execute() method");
		UserAuthenticationResponse userAuthenticationResponse = new UserAuthenticationResponse();
		String userName = "";
		String password = "";
		
		if(null != loginRequest ){
			if(errorOccured){
				errorOccured = false;
			}
			Login login = new Login();
			login 	 = loginRequest.getLogin();
			if(null != login){
				userName = login.getUserName();
				password = login.getPassword();
			}	
			try{
			    //Method to validate UserName and Password.Check whether user name and password is blank.
				if(validateUserCredentials(userName,password)){
					UserProfileData userProfileData = null;
					UserDetails userDetails = null;
					//Check whether username and password is valid by checking 
					userProfileData = authenticateUser(userName.trim(),password.trim());
					if(null != userProfileData){
						//Check for User role type and set to response
						if(null != userProfileData.getContactId()){
							//Populate Contacts and location status
							userProfileData = populateDataForUser(userAuthenticationResponse,userProfileData);
							
							userDetails = new UserDetails();
							userDetails = populateUserResponse(userProfileData);
							
							userAuthenticationResponse.setMessage(PublicAPIConstant.API_RESULT_SUCCESS);
							userAuthenticationResponse.setUserDetails(userDetails);
							
						}
					}else{
						//Error Message : Not a valid User name and password
						userAuthenticationResponse.setMessage(PublicAPIConstant.API_RESULT_FAILURE);
						userAuthenticationResponse.setResults(Results.getError(ResultsCode.AUTHENTICATION_ERROR_CODE.getMessage(), 
								ResultsCode.AUTHENTICATION_ERROR_CODE.getCode()));
					}
				}else{
					//Error Message : User name and Password cannot be blank
					userAuthenticationResponse.setMessage(PublicAPIConstant.API_RESULT_FAILURE);
					userAuthenticationResponse.setResults(Results.getError(ResultsCode.INVALID_USER_CREDENTIALS.getMessage(), 
							ResultsCode.INVALID_USER_CREDENTIALS.getCode()));
				}
			}catch(Exception e){
				throw e;
			}
		}else{
			userAuthenticationResponse.setMessage(PublicAPIConstant.API_RESULT_FAILURE);
			userAuthenticationResponse.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		return userAuthenticationResponse;
	}
	private UserDetails populateUserResponse(UserProfileData userProfileData) throws Exception {
		UserDetails userDetails = null;
		
		if(null != userProfileData ){
			userDetails = new UserDetails();
			if(!StringUtils.isBlank(userProfileData.getUserName())){
				userDetails.setUserName(userProfileData.getUserName());
			}
			if(!StringUtils.isBlank(userProfileData.getUserRoleType())){
				userDetails.setUserRoleType(userProfileData.getUserRoleType());
			}
			if(!StringUtils.isBlank(userProfileData.getFirstName())){
				userDetails.setFirstName(userProfileData.getFirstName());
			}
			if(!StringUtils.isBlank(userProfileData.getLastName())){
				userDetails.setLastName(userProfileData.getLastName());
			}
			if(!StringUtils.isBlank(userProfileData.getPhoneNo())){
				userDetails.setPhoneNo(userProfileData.getPhoneNo());
			}
			if(!StringUtils.isBlank(userProfileData.getEmail())){
				userDetails.setEmail(userProfileData.getEmail());
			}
			if(!StringUtils.isBlank(userProfileData.getEmailAlt())){
				userDetails.setEmailAlt(userProfileData.getEmailAlt());
			}
			if(null != userProfileData.getListOflocations() && userProfileData.getListOflocations().size() > 0){
				LocationDetails locationDetails = new LocationDetails();
				locationDetails.setLocation(userProfileData.getListOflocations());
				userDetails.setListOflocations(locationDetails);
			}
			
			if(!StringUtils.isBlank(userProfileData.getCompanyName())){
				userDetails.setCompanyName(userProfileData.getCompanyName());
			}
			if(null != userProfileData.getFirmId()){
				userDetails.setFirmId(userProfileData.getFirmId());
			}
			if(null != userProfileData.getResourceId()){
				userDetails.setResourceId(userProfileData.getResourceId());
			}
			if(!StringUtils.isBlank(userProfileData.getPrimaryIndustry())){
				userDetails.setPrimaryIndustry(userProfileData.getPrimaryIndustry());
			}
			if(null != userProfileData.getLeadPartnerId()){
				userDetails.setLeadPartnerId(userProfileData.getLeadPartnerId());
			}
			if(null != userProfileData.getLeadStatus()){
				userDetails.setLeadStatus(userProfileData.getLeadStatus());
			}
			if(!StringUtils.isBlank(userProfileData.getLeadStatus())){
				userDetails.setLeadStatus(userProfileData.getLeadStatus());
			}
			if(!StringUtils.isBlank(userProfileData.getInLaunchMarket())){
				userDetails.setInLaunchMarket(userProfileData.getInLaunchMarket());
			}
			
		}
		return userDetails;
	}
	
	private UserProfileData populateDataForUser(UserAuthenticationResponse userAuthenticationResponse, UserProfileData userProfileData) throws Exception {
		
		if(userProfileData.getRoleId() == PublicAPIConstant.API_PROVIDER_ROLE_ID){
			List<LocationResponseDate>  locationList = new ArrayList<LocationResponseDate>(); 
			locationList = userAuthProfileBO.getLocationDetails(userProfileData.getContactId());
			logger.info("Location List : " + locationList.size() );
			if(locationList.size() > 0){
				userProfileData.setListOflocations(locationList);
				boolean flag = false;
				for(LocationResponseDate locationResponseDate : locationList){
					if(flag == false){
						if(locationResponseDate.getLocType().equals(PublicAPIConstant.BUSINESS_ADDRESS)){
							if(!StringUtils.isBlank(locationResponseDate.getState())){
								if(userAuthProfileBO.lauchMarketCheck(locationResponseDate.getState()) == PublicAPIConstant.LAUNCH_MARKET_TRUE_IND){
									userProfileData.setInLaunchMarket(PublicAPIConstant.LAUNCH_MARKET_TRUE);
									logger.info("Location Zip : " + locationResponseDate.getZip());
									flag = true;
								}else{
									userProfileData.setInLaunchMarket(PublicAPIConstant.LAUNCH_MARKET_FALSE);
								}
							}
						}	
					}	
				}	
				
			}
			FirmDetailsVO firmDetailsVO = userAuthProfileBO.getFirmDetails(userProfileData.getContactId());
			
			if(null != firmDetailsVO){
				if(null != firmDetailsVO.getVendorId() &&  firmDetailsVO.getVendorId() != 0){
					userProfileData.setFirmId(firmDetailsVO.getVendorId());
					LeadProfileDetailsVO leadProfileDetailsVO = userAuthProfileBO.getLeadDetails(firmDetailsVO.getVendorId());
					if(null != leadProfileDetailsVO){
						if(!StringUtils.isBlank(leadProfileDetailsVO.getLeadPartnerId())){
							userProfileData.setLeadPartnerId(leadProfileDetailsVO.getLeadPartnerId());
						}
						if(!StringUtils.isBlank(leadProfileDetailsVO.getLeadStatusDesc())){
							userProfileData.setLeadStatus(leadProfileDetailsVO.getLeadStatusDesc());
						}
						
					}
				}
				if(!StringUtils.isBlank(firmDetailsVO.getBusinessName())){
					userProfileData.setCompanyName(firmDetailsVO.getBusinessName());
				}
				if(null != firmDetailsVO.getResourceId()){
					userProfileData.setResourceId(firmDetailsVO.getResourceId());
				}
				if(!StringUtils.isBlank(firmDetailsVO.getPrimaryIndustryDesc())){
					userProfileData.setPrimaryIndustry(firmDetailsVO.getPrimaryIndustryDesc());
				}
			}
		}
		return userProfileData;
	}
	
	private boolean validateUserCredentials(String userName,String password){
		
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
			return false;			
		}
		return true;
	}
	
	public UserProfileData authenticateUser(String userName,String credential) throws Exception{
		
		UserProfileData userProfileData = userAuthProfileBO.findById(userName);
		if(userProfileData != null && !StringUtils.isBlank(userProfileData.getUserName()) &&  !StringUtils.isBlank(userProfileData.getPassword()) ) {
			 return validateCredentials(userName,userProfileData.getUserName(),credential,userProfileData.getPassword() )? userProfileData : null;
		}
		return null;
	
	}
	protected boolean validateCredentials(String inputUserName,String expectedUserName,String inputPassword,
			String expectedPassword) {
		
		if (StringUtils.isBlank(inputUserName) || StringUtils.isBlank(expectedUserName)) {
			return false;
		}
		if (StringUtils.isBlank(inputPassword) || StringUtils.isBlank(expectedPassword)) {
			return false;
		}
		try {
			if((expectedUserName != null && expectedUserName.trim().equals(inputUserName)) && CryptoUtil.verifyPassword(inputPassword, expectedPassword)){
				return true;
			}
		} catch (CannotPerformOperationException e) {
			e.printStackTrace();
		} catch (InvalidHashException e) {
			e.printStackTrace();
		}
		return false; 

	}
	
	/**
	 * @return the userDao
	 */
	public IUserProfileBO getUserAuthProfileBO() {
		return userAuthProfileBO;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserAuthProfileBO(IUserProfileBO userAuthProfileBO) {
		this.userAuthProfileBO = userAuthProfileBO;
	}



}
