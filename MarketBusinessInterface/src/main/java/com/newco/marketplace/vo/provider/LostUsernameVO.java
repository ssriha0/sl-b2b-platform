package com.newco.marketplace.vo.provider;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;


public class LostUsernameVO extends SerializableBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2935847459340709440L;
	private String userName;
    private String businessName;
    private String emailAddress;
    private int userId;    
	private String questionId;//MTedder@covansys.com
	private String questionTxt;//MTedder@covansys.com
	private String questionTxtAnswer;//MTedder@covansys.com - users secret question answer
	private String password;
	private boolean successAns;
	private int pwdInd=-1;
	private String firstName;
	private String lastName;
	private String resourceId;
	private String zip;
	private String phoneNo;
	private String phoneNoBiz; //business phone number
	private int lockedInd;
	private boolean detailedProfile;
	private Date modifiedDate;
	private String deepLinkUrl;

	public String getDeepLinkUrl() {
		return deepLinkUrl;
	}
	public void setDeepLinkUrl(String deepLinkUrl) {
		this.deepLinkUrl = deepLinkUrl;
	}
	public int getLockedInd() {
		return lockedInd;
	}
	public void setLockedInd(int lockedInd) {
		this.lockedInd = lockedInd;
	}
	public int getPwdInd() {
		return pwdInd;
	}
	public void setPwdInd(int pwdInd) {
		this.pwdInd = pwdInd;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the emailAddress
	 */
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
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
	public boolean getSuccessAns() {
		return successAns;
	}
	public void setSuccessAns(boolean successAns) {
		this.successAns = successAns;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public boolean isDetailedProfile() {
		return detailedProfile;
	}
	public void setDetailedProfile(boolean detailedProfile) {
		this.detailedProfile = detailedProfile;
	}
	
	// Used by JSP file
	public String getDisplayName() {
		return firstName + " " + lastName + " (ID#" + resourceId +")";
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getPhoneNoBiz() {
		return phoneNoBiz;
	}
	public void setPhoneNoBiz(String phoneNoBiz) {
		this.phoneNoBiz = phoneNoBiz;
	}
    
}// LostUserNameVO
