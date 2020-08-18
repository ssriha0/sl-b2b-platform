package com.servicelive.spn.buyer.auditor.newapplicants;

import java.util.List;

import com.servicelive.spn.core.SPNBaseModel;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.common.ProviderLocationDetails;
import com.servicelive.domain.spn.detached.SPNAuditorSearchExpandCriteriaVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.common.Location;

/**
 * 
 *
 */
public class SPNNewApplicantsTabModel extends SPNBaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String businessName;
	private String firmID;
	private String dba;
	private String networkName;
	private String spnStatus;
	
	private Contact contact;
	
	//SL-19381
	//Code added to display contact information of a firm
	private ProviderLocationDetails providerLocationDetails;
	
	
	private String slStatus;
	private String invitedDate;
	private String appliedDate;
	
	private int newApplicantsCount;
	private int reApplicantsCount;
	
	// Code Added for Jira SL-19384
	// Variable for count of 'Membership Under Review' status 
	private int membershipUnderReviewCount;
	
	
	protected SPNAuditorSearchExpandCriteriaVO expandCriteriaVO;
	
	private Integer networkId;
	private Integer providerFirmId;
	private Integer providerFirmAdminId;
	
	// Right Side 'View SPN Criteria'
	private List<SPNHeader> spnList;	
	
	
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
	 * @return the firmID
	 */
	public String getFirmID() {
		return firmID;
	}
	/**
	 * @param firmID the firmID to set
	 */
	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}
	/**
	 * @return the dba
	 */
	public String getDba() {
		return dba;
	}
	/**
	 * @param dba the dba to set
	 */
	public void setDba(String dba) {
		this.dba = dba;
	}
	/**
	 * @return the networkName
	 */
	public String getNetworkName() {
		return networkName;
	}
	/**
	 * @param networkName the networkName to set
	 */
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	/**
	 * @return the slStatus
	 */
	public String getSlStatus() {
		return slStatus;
	}
	/**
	 * @param slStatus the slStatus to set
	 */
	public void setSlStatus(String slStatus) {
		this.slStatus = slStatus;
	}
	/**
	 * @return the invitedDate
	 */
	public String getInvitedDate() {
		return invitedDate;
	}
	/**
	 * @param invitedDate the invitedDate to set
	 */
	public void setInvitedDate(String invitedDate) {
		this.invitedDate = invitedDate;
	}
	/**
	 * @return the appliedDate
	 */
	public String getAppliedDate() {
		return appliedDate;
	}
	/**
	 * @param appliedDate the appliedDate to set
	 */
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	/**
	 * 
	 * @return Contact
	 */
	public Contact getContact()
	{
		return contact;
	}
	/**
	 * 
	 * @param contact
	 */
	public void setContact(Contact contact)
	{
		this.contact = contact;
	}
	/**
	 * 
	 * @return int
	 */
	public int getNewApplicantsCount()
	{
		return newApplicantsCount;
	}
	/**
	 * 
	 * @param newApplicantsCount
	 */
	public void setNewApplicantsCount(int newApplicantsCount)
	{
		this.newApplicantsCount = newApplicantsCount;
	}
	/**
	 * 
	 * @return int
	 */
	public int getReApplicantsCount()
	{
		return reApplicantsCount;
	}
	/**
	 * 
	 * @param reApplicantsCount
	 */
	public void setReApplicantsCount(int reApplicantsCount)
	{
		this.reApplicantsCount = reApplicantsCount;
	}
	/**
	 * 
	 * @return SPNAuditorSearchExpandCriteriaVO
	 */
	public SPNAuditorSearchExpandCriteriaVO getExpandCriteriaVO()
	{
		return expandCriteriaVO;
	}
	/**
	 * 
	 * @param expandCriteriaVO
	 */
	public void setExpandCriteriaVO(SPNAuditorSearchExpandCriteriaVO expandCriteriaVO)
	{
		this.expandCriteriaVO = expandCriteriaVO;
	}
	/**
	 * 
	 * @return String
	 */
	public String getSpnStatus() {
		return spnStatus;
	}
	/**
	 * 
	 * @param spnStatus
	 */
	public void setSpnStatus(String spnStatus) {
		this.spnStatus = spnStatus;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getNetworkId()
	{
		return networkId;
	}
	/**
	 * 
	 * @param networkId
	 */
	public void setNetworkId(Integer networkId)
	{
		this.networkId = networkId;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getProviderFirmId()
	{
		return providerFirmId;
	}
	/**
	 * 
	 * @param providerFirmId
	 */
	public void setProviderFirmId(Integer providerFirmId)
	{
		this.providerFirmId = providerFirmId;
	}
	/**
	 * 
	 * @return List
	 */
	public List<SPNHeader> getSpnList()
	{
		return spnList;
	}
	/**
	 * 
	 * @param spnList
	 */
	public void setSpnList(List<SPNHeader> spnList)
	{
		this.spnList = spnList;
	}
	/**
	 * @return the providerFirmAdminId
	 */
	public Integer getProviderFirmAdminId() {
		return providerFirmAdminId;
	}
	/**
	 * @param providerFirmAdminId the providerFirmAdminId to set
	 */
	public void setProviderFirmAdminId(Integer providerFirmAdminId) {
		this.providerFirmAdminId = providerFirmAdminId;
	}

	public int getMembershipUnderReviewCount() {
		return membershipUnderReviewCount;
	}
	public void setMembershipUnderReviewCount(int membershipUnderReviewCount) {
		this.membershipUnderReviewCount = membershipUnderReviewCount;
	}
	
	public ProviderLocationDetails getProviderLocationDetails() {
		return providerLocationDetails;
	}
	public void setProviderLocationDetails(
			ProviderLocationDetails providerLocationDetails) {
		this.providerLocationDetails = providerLocationDetails;
	}
	
}
