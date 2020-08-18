/**
 * 
 */
package com.newco.marketplace.util;

/**
 * @author sahmad7
 *
 */
public class ActivityCheckList {
	
	private boolean isBusinessInformationAllowed;
	private boolean isOwnershipAllowed;
	private boolean isCredentialsAllowed;
	private boolean isWarrantyAllowed;
	private boolean isFinancialAllowed;
	private boolean isW9Allowed;
	private boolean isInsuranceAllowed;
	private boolean isTermsAndConditionsAllowed;
	private boolean isTeamMemberAllowed;
	private boolean isContactInformationAllowed;
	private boolean isSkillsAllowed;
	private boolean isTeamMemberCredentialsAllowed;
	private boolean isCoverageAreaAllowed;
	private boolean isScheduleAllowed;
	private boolean isQualificationsAllowed;
	private boolean isBusinessInformationEmpty;
	private boolean isBusinessInformationHalfEmpty;
	private boolean isBusinessInformationFull;
	private String currentActivity = "";
	
	public String getCurrentActivity() {
		return currentActivity;
	}
	public void setCurrentActivity(String currentActivity) {
		this.currentActivity = currentActivity;
	}
	public boolean getIsBusinessInformationAllowed() {
		return isBusinessInformationAllowed;
	}
	public void setBusinessInformationAllowed(boolean isBusinessInformationAllowed) {
		this.isBusinessInformationAllowed = isBusinessInformationAllowed;
	}
	public boolean getIsContactInformationAllowed() {
		return isContactInformationAllowed;
	}
	public void setContactInformationAllowed(boolean isContactInformationAllowed) {
		this.isContactInformationAllowed = isContactInformationAllowed;
	}
	public boolean getIsCoverageAreaAllowed() {
		return isCoverageAreaAllowed;
	}
	public void setCoverageAreaAllowed(boolean isCoverageAreaAllowed) {
		this.isCoverageAreaAllowed = isCoverageAreaAllowed;
	}
	public boolean getIsCredentialsAllowed() {
		return isCredentialsAllowed;
	}
	public void setCredentialsAllowed(boolean isCredentialsAllowed) {
		this.isCredentialsAllowed = isCredentialsAllowed;
	}
	public boolean getIsFinancialAllowed() {
		return isFinancialAllowed;
	}
	public void setFinancialAllowed(boolean isFinancialAllowed) {
		this.isFinancialAllowed = isFinancialAllowed;
	}
	public boolean getIsInsuranceAllowed() {
		return isInsuranceAllowed;
	}
	public void setInsuranceAllowed(boolean isInsuranceAllowed) {
		this.isInsuranceAllowed = isInsuranceAllowed;
	}
	public boolean getIsOwnershipAllowed() {
		return isOwnershipAllowed;
	}
	public void setOwnershipAllowed(boolean isOwnershipAllowed) {
		this.isOwnershipAllowed = isOwnershipAllowed;
	}
	public boolean getIsQualificationsAllowed() {
		return isQualificationsAllowed;
	}
	public void setQualificationsAllowed(boolean isQualificationsAllowed) {
		this.isQualificationsAllowed = isQualificationsAllowed;
	}
	public boolean getIsScheduleAllowed() {
		return isScheduleAllowed;
	}
	public void setScheduleAllowed(boolean isScheduleAllowed) {
		this.isScheduleAllowed = isScheduleAllowed;
	}
	public boolean getIsSkillsAllowed() {
		return isSkillsAllowed;
	}
	public void setSkillsAllowed(boolean isSkillsAllowed) {
		this.isSkillsAllowed = isSkillsAllowed;
	}
	public boolean getIsTeamMemberAllowed() {
		return isTeamMemberAllowed;
	}
	public void setTeamMemberAllowed(boolean isTeamMemberAllowed) {
		this.isTeamMemberAllowed = isTeamMemberAllowed;
	}
	public boolean getIsTeamMemberCredentialsAllowed() {
		return isTeamMemberCredentialsAllowed;
	}
	public void setTeamMemberCredentialsAllowed(
			boolean isTeamMemberCredentialsAllowed) {
		this.isTeamMemberCredentialsAllowed = isTeamMemberCredentialsAllowed;
	}
	public boolean getIsTermsAndConditionsAllowed() {
		return isTermsAndConditionsAllowed;
	}
	public void setTermsAndConditionsAllowed(boolean isTermsAndConditionsAllowed) {
		this.isTermsAndConditionsAllowed = isTermsAndConditionsAllowed;
	}
	public boolean getIsW9Allowed() {
		return isW9Allowed;
	}
	public void setW9Allowed(boolean isW9Allowed) {
		this.isW9Allowed = isW9Allowed;
	}
	public boolean getIsWarrantyAllowed() {
		return isWarrantyAllowed;
	}
	public void setWarrantyAllowed(boolean isWarrantyAllowed) {
		this.isWarrantyAllowed = isWarrantyAllowed;
	}
	public boolean getIsBusinessInformationEmpty() {
		return isBusinessInformationEmpty;
	}
	public void setBusinessInformationEmpty(boolean isBusinessInformationEmpty) {
		this.isBusinessInformationEmpty = isBusinessInformationEmpty;
	}
	public boolean getIsBusinessInformationFull() {
		return isBusinessInformationFull;
	}
	public void setBusinessInformationFull(boolean isBusinessInformationFull) {
		this.isBusinessInformationFull = isBusinessInformationFull;
	}
	public boolean getIsBusinessInformationHalfEmpty() {
		return isBusinessInformationHalfEmpty;
	}
	public void setBusinessInformationHalfEmpty(
			boolean isBusinessInformationHalfEmpty) {
		this.isBusinessInformationHalfEmpty = isBusinessInformationHalfEmpty;
	}


}
