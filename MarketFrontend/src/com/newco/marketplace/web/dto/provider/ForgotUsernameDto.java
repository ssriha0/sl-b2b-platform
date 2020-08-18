package com.newco.marketplace.web.dto.provider;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.vo.provider.LostUsernameVO;

public class ForgotUsernameDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2571616653543390641L;
	private String email;
	private String userName;
	private String questionId;
	private String questionTxt;//MTedder@covansys.com
	private String questionTxtAnswer;//MTedder@covansys.com - users secret question answer
	private int roleId = -1;
	private List<LostUsernameVO> listUsers;
	private boolean success;
	private int pwdInd=-1;
	private String businessName;
	private String zip;
	private String phoneNo;
	private int lockedInd;
	private boolean detailedProfile;
	private Date modifiedDate;
	
	
	//author - bnatara
	private String resourceId;
	
	public int getPwdInd() {
		return pwdInd;
	}
	public void setPwdInd(int pwdInd) {
		this.pwdInd = pwdInd;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public List<LostUsernameVO> getListUsers() {
		return listUsers;
	}
	public void setListUsers(List<LostUsernameVO> listUsers) {
		this.listUsers = listUsers;
	}
	/**
	 * //MTedder@covansys.com
	 * @return the questionTxt
	 */
	public String getQuestionTxt() {
		return questionTxt;
	}
	/**
	 * //MTedder@covansys.com
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
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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
	public int getLockedInd() {
		return lockedInd;
	}
	public void setLockedInd(int lockedInd) {
		this.lockedInd = lockedInd;
	}
	public boolean isDetailedProfile() {
		return detailedProfile;
	}
	public void setDetailedProfile(boolean detailedProfile) {
		this.detailedProfile = detailedProfile;
	}
	
	public boolean isProfileLocked() {		
		if (lockedInd == 0) 
			return false;
		
		return true;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
