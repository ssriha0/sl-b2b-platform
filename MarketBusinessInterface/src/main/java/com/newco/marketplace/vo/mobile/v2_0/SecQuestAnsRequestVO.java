package com.newco.marketplace.vo.mobile.v2_0;

import java.util.Date;

import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;


/**
 * This is a VO class for storing request information for 
 * the ValidateSecQuestAnsRequest
 * @author Infosys
 *
 */
public class SecQuestAnsRequestVO {
	
	private String requestFor;
	
	private String email;
	
	private String emailFromDB;
	
	private String userName;
	
	private String userFirstName;

	private Integer userId;

	private Integer questionId;

	//answer received in API request
	private String userAnswer;
	
	private String userZipCode;
	
	private String userPhoneNumber;
	
	private String userCompanyName;

	private String questionTxt;
	
	// asnwer saved in DB
	private String questionTxtAnswer;
	private int pwdInd;
	
	private String zip;
	private String businessName;
	private String phoneNo;
	private int lockedInd;
	private Date modifiedDate;
	
	private boolean userProfileExists = false;
	private boolean detailedProfile;
	private boolean isSuccess;
	private Results error;
	private Results success;
	private Integer userIdFromDB;
	private Integer verificationAttemptCount;
	
	

	
	public Integer getVerificationAttemptCount() {
		return verificationAttemptCount;
	}

	public void setVerificationAttemptCount(Integer verificationAttemptCount) {
		this.verificationAttemptCount = verificationAttemptCount;
	}

	public Results getSuccess() {
		return success;
	}

	public void setSuccess(Results success) {
		this.success = success;
	}

	private Integer actualQuestionId;
	private boolean additionalVerification;

	
	private String userNameFromDB;
	private Integer roleId;
	private boolean commercialUser;

	

	
	
	public String getEmailFromDB() {
		return emailFromDB;
	}

	public void setEmailFromDB(String emailFromDB) {
		this.emailFromDB = emailFromDB;
	}

	public boolean isCommercialUser() {
		return commercialUser;
	}

	public void setCommercialUser(boolean commercialUser) {
		this.commercialUser = commercialUser;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserNameFromDB() {
		return userNameFromDB;
	}

	public void setUserNameFromDB(String userNameFromDB) {
		this.userNameFromDB = userNameFromDB;
	}

	public boolean isAdditionalVerification() {
		return additionalVerification;
	}

	public void setAdditionalVerification(boolean additionalVerification) {
		this.additionalVerification = additionalVerification;
	}

	public Integer getActualQuestionId() {
		return actualQuestionId;
	}

	public void setActualQuestionId(Integer actualQuestionId) {
		this.actualQuestionId = actualQuestionId;
	}

	public Results getError() {
		return error;
	}

	public void setError(Results error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isDetailedProfile() {
		return detailedProfile;
	}

	public void setDetailedProfile(boolean detailedProfile) {
		this.detailedProfile = detailedProfile;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getLockedInd() {
		return lockedInd;
	}

	public void setLockedInd(int lockedInd) {
		this.lockedInd = lockedInd;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isUserProfileExists() {
		return userProfileExists;
	}

	public void setUserProfileExists(boolean userProfileExists) {
		this.userProfileExists = userProfileExists;
	}

	public String getQuestionTxt() {
		return questionTxt;
	}

	public void setQuestionTxt(String questionTxt) {
		this.questionTxt = questionTxt;
	}

	public String getQuestionTxtAnswer() {
		return questionTxtAnswer;
	}

	public void setQuestionTxtAnswer(String questionTxtAnswer) {
		this.questionTxtAnswer = questionTxtAnswer;
	}

	public int getPwdInd() {
		return pwdInd;
	}

	public void setPwdInd(int pwdInd) {
		this.pwdInd = pwdInd;
	}


	
	public String getRequestFor() {
		return requestFor;
	}

	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public String getUserZipCode() {
		return userZipCode;
	}

	public void setUserZipCode(String userZipCode) {
		this.userZipCode = userZipCode;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserCompanyName() {
		return userCompanyName;
	}

	public void setUserCompanyName(String userCompanyName) {
		this.userCompanyName = userCompanyName;
	}

	public Integer getUserIdFromDB() {
		return userIdFromDB;
	}

	public void setUserIdFromDB(Integer userIdFromDB) {
		this.userIdFromDB = userIdFromDB;
	}
	
	
}
