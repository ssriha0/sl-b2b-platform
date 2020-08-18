package com.servicelive.spn.buyer.auditor;

public class SPNApplicantCounts
{
	private int newApplicantsCount=0;
	private int reApplicantsCount=0;
	
	// Code Added for Jira SL-19384
	// Variable for count of 'Membership Under Review' status 
	private int membershipUnderReviewCount=0;

	/**
	 * @return the newApplicantsCount
	 */
	public int getNewApplicantsCount() {
		return newApplicantsCount;
	}
	/**
	 * @param newApplicantsCount the newApplicantsCount to set
	 */
	public void setNewApplicantsCount(int newApplicantsCount) {
		this.newApplicantsCount = newApplicantsCount;
	}
	/**
	 * @return the reApplicantsCount
	 */
	public int getReApplicantsCount() {
		return reApplicantsCount;
	}
	/**
	 * @param reApplicantsCount the reApplicantsCount to set
	 */
	public void setReApplicantsCount(int reApplicantsCount) {
		this.reApplicantsCount = reApplicantsCount;
	}
	
	
	public int getMembershipUnderReviewCount() {
		return membershipUnderReviewCount;
	}
	public void setMembershipUnderReviewCount(int membershipUnderReviewCount) {
		this.membershipUnderReviewCount = membershipUnderReviewCount;
	}
	
}
