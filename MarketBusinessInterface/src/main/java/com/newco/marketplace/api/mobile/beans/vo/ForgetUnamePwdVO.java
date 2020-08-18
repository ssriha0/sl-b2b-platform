/**
 * 
 */
package com.newco.marketplace.api.mobile.beans.vo;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.api.beans.Results;
/**
 * @author Karthik_Hariharan01
 *
 */
public class ForgetUnamePwdVO {

	private String requestFor;
	private String email;
	private String userName;
	private String userFirstName;
	private String userId;
	
	private String questionId;
	private String questionTxt;
	private String questionTxtAnswer;
	private int pwdInd;
	private String zip;
	private String businessName;
	private String phoneNo;
	private int lockedInd;
	private Date modifiedDate;
	
	private boolean userProfileExists = false;
	
	private Results results;
	
	private List<UserDetailVO> userDetailVOs;
	
	private boolean multipleUserExists=false;
	private int verificationCount;
	
	/**
	 * @return the requestFor
	 */
	public String getRequestFor() {
		return requestFor;
	}

	/**
	 * @param requestFor the requestFor to set
	 */
	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}

	/**
	 * @param userFirstName the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the questionId
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the questionTxt
	 */
	public String getQuestionTxt() {
		return questionTxt;
	}

	/**
	 * @param questionTxt the questionTxt to set
	 */
	public void setQuestionTxt(String questionTxt) {
		this.questionTxt = questionTxt;
	}

	/**
	 * @return the questionTxtAnswer
	 */
	public String getQuestionTxtAnswer() {
		return questionTxtAnswer;
	}

	/**
	 * @param questionTxtAnswer the questionTxtAnswer to set
	 */
	public void setQuestionTxtAnswer(String questionTxtAnswer) {
		this.questionTxtAnswer = questionTxtAnswer;
	}

	/**
	 * @return the pwdInd
	 */
	public int getPwdInd() {
		return pwdInd;
	}

	/**
	 * @param pwdInd the pwdInd to set
	 */
	public void setPwdInd(int pwdInd) {
		this.pwdInd = pwdInd;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the lockedInd
	 */
	public int getLockedInd() {
		return lockedInd;
	}

	/**
	 * @param lockedInd the lockedInd to set
	 */
	public void setLockedInd(int lockedInd) {
		this.lockedInd = lockedInd;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the userProfileExists
	 */
	public boolean isUserProfileExists() {
		return userProfileExists;
	}

	/**
	 * @param userProfileExists the userProfileExists to set
	 */
	public void setUserProfileExists(boolean userProfileExists) {
		this.userProfileExists = userProfileExists;
	}

	/**
	 * @return the results
	 */
	public Results getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(Results results) {
		this.results = results;
	}

	/**
	 * @return the userDetailVOs
	 */
	public List<UserDetailVO> getUserDetailVOs() {
		return userDetailVOs;
	}

	/**
	 * @param userDetailVOs the userDetailVOs to set
	 */
	public void setUserDetailVOs(List<UserDetailVO> userDetailVOs) {
		this.userDetailVOs = userDetailVOs;
	}

	/**
	 * @return the multipleUserExists
	 */
	public boolean isMultipleUserExists() {
		return multipleUserExists;
	}

	/**
	 * @param multipleUserExists the multipleUserExists to set
	 */
	public void setMultipleUserExists(boolean multipleUserExists) {
		this.multipleUserExists = multipleUserExists;
	}

	public int getVerificationCount() {
		return verificationCount;
	}

	public void setVerificationCount(int verificationCount) {
		this.verificationCount = verificationCount;
	}
	
	
}
