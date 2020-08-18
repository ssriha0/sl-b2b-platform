package com.newco.marketplace.dto.vo.permission;

import java.util.List;

import com.newco.marketplace.dto.vo.ActivityVO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * $Revision$ $Author$ $Date$
 */
public class UserVO extends SerializableBaseVO{
	
	private static final long serialVersionUID = -1032319574561911457L;

	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String jobTitle;
	private String userName;
	private String password;
	private String email;
	private Double maxSpendLimitPerSO;
	private boolean editable;
	private String businessPhone;
	private String phoneExt;
	private String businessFax;
	private String mobilePhone;
	private String altEmail;
	private List<UserRoleVO> userRoles; //one or more
	private List<ActivityVO> userActivities;
	private Integer terms_cond_id;
	private String terms_cond_content;
	private Integer term_cond_ind;
	private int roleTypeId;
	private Integer contactId;
	private Integer buyerId;
	private Integer editable_int;
	private Integer resourceId;
	private String companyName;
	private Integer buyerAdminId;
	private Integer companyId;
	private String modifiedBy;
	private Integer loggedInBefore; //Flag to check if user has ever logged in.
	private String zip;
	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<UserRoleVO> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<UserRoleVO> userRoles) {
		this.userRoles = userRoles;
	}
	public List<ActivityVO> getUserActivities() {
		return userActivities;
	}
	public void setUserActivities(List<ActivityVO> userActivities) {
		this.userActivities = userActivities;
	}
	public Double getMaxSpendLimitPerSO() {
		return maxSpendLimitPerSO;
	}
	public void setMaxSpendLimitPerSO(Double maxSpendLimitPerSO) {
		this.maxSpendLimitPerSO = maxSpendLimitPerSO;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getPhoneExt() {
		return phoneExt;
	}
	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getAltEmail() {
		return altEmail;
	}
	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}
	/**
	 * @return the terms_cond_id
	 */
	public Integer getTerms_cond_id() {
		return terms_cond_id;
	}
	/**
	 * @param terms_cond_id the terms_cond_id to set
	 */
	public void setTerms_cond_id(Integer terms_cond_id) {
		this.terms_cond_id = terms_cond_id;
	}
	/**
	 * @return the terms_con_content
	 */
	public String getTerms_cond_content() {
		return terms_cond_content;
	}
	/**
	 * @param terms_con_content the terms_con_content to set
	 */
	public void setTerms_cond_content(String terms_cond_content) {
		this.terms_cond_content = terms_cond_content;
	}
	public void setEditable_int(Integer editable_int) {
		if (editable_int.intValue() == 1 ) {
			this.editable = true;
		} else {
			this.editable = false;
		}
	}
	/**
	 * @return the roleTypeId
	 */
	public int getRoleTypeId() {
		return roleTypeId;
	}
	/**
	 * @param roleTypeId the roleTypeId to set
	 */
	public void setRoleTypeId(int roleTypeId) {
		this.roleTypeId = roleTypeId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the term_cond_ind
	 */
	public Integer getTerm_cond_ind() {
		return term_cond_ind;
	}
	/**
	 * @param term_cond_ind the term_cond_ind to set
	 */
	public void setTerm_cond_ind(Integer term_cond_ind) {
		this.term_cond_ind = term_cond_ind;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the buyerAdminId
	 */
	public Integer getBuyerAdminId() {
		return buyerAdminId;
	}
	/**
	 * @param buyerAdminId the buyerAdminId to set
	 */
	public void setBuyerAdminId(Integer buyerAdminId) {
		this.buyerAdminId = buyerAdminId;
	}
	
	/**
	 * @return the businessFax
	 */
	public String getBusinessFax() {
		return businessFax;
	}
	
	/**
	 * @param businessFax the businessFax to set
	 */
	public void setBusinessFax(String businessFax) {
		this.businessFax = businessFax;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getLoggedInBefore() {
		return loggedInBefore;
	}
	public void setLoggedInBefore(Integer loggedInBefore) {
		this.loggedInBefore = loggedInBefore;
	}
	
	public boolean isNeverLoggedIn() {
		if (loggedInBefore > 0)
			return false;
		return true;
	}

}
