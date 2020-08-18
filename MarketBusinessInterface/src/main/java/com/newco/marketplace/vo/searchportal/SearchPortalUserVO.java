/**
 *
 */
package com.newco.marketplace.vo.searchportal;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.vo.MPBaseVO;

/**
 * @author hoza
 *
 */
public class SearchPortalUserVO extends MPBaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 869967314963773810L;

	private Integer userId;
	private Integer companyId;
	private String userName;
	private String businessName;
	private String adminName;
	private String fnameOrLname; //This is the filteriing field
	private String firstName;
	private String middleName;	//SL-18330
	private String lastName;
	private String adminFName;
	private String adminLName;
	private Date signUpDate;
	private Date fromSignUpDate ;
	private Date toSignUpDate;
	private Date lastActivityDate;
	private Integer roleTypeId;
	private String roleType;


	/* (non-Javadoc)
	 * @see com.sears.os.vo.ABaseVO#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	 * @return the adminName
	 */
	public String getAdminName() {
		return adminName;
	}

	/**
	 * @param adminName the adminName to set
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * @return the fnameOrLname
	 */
	public String getFnameOrLname() {
		return fnameOrLname;
	}

	/**
	 * @param fnameOrLname the fnameOrLname to set
	 */
	public void setFnameOrLname(String fnameOrLname) {
		this.fnameOrLname = fnameOrLname;
	}

	/**
	 * @return the signUpDate
	 */
	public Date getSignUpDate() {
		return signUpDate;
	}

	/**
	 * @param signUpDate the signUpDate to set
	 */
	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}



	/**
	 * @return the lastActivityDate
	 */
	public Date getLastActivityDate() {
		return lastActivityDate;
	}

	/**
	 * @param lastActivityDate the lastActivityDate to set
	 */
	public void setLastActivityDate(Date lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	/**
	 * @return the roleTypeId
	 */
	public Integer getRoleTypeId() {
		return roleTypeId;
	}

	/**
	 * @param roleTypeId the roleTypeId to set
	 */
	public void setRoleTypeId(Integer roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}	
	
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the adminFName
	 */
	public String getAdminFName() {
		return adminFName;
	}

	/**
	 * @param adminFName the adminFName to set
	 */
	public void setAdminFName(String adminFName) {
		this.adminFName = adminFName;
	}

	/**
	 * @return the adminLName
	 */
	public String getAdminLName() {
		return adminLName;
	}

	/**
	 * @param adminLName the adminLName to set
	 */
	public void setAdminLName(String adminLName) {
		this.adminLName = adminLName;
	}

	/**
	 * @return the fromSignUpDate
	 */
	public Date getFromSignUpDate() {
		return fromSignUpDate;
	}

	/**
	 * @param fromSignUpDate the fromSignUpDate to set
	 */
	public void setFromSignUpDate(Date fromSignUpDate) {
		this.fromSignUpDate = fromSignUpDate;
	}

	/**
	 * @return the toSignUpDate
	 */
	public Date getToSignUpDate() {
		return toSignUpDate;
	}

	/**
	 * @param toSignUpDate the toSignUpDate to set
	 */
	public void setToSignUpDate(Date toSignUpDate) {
		this.toSignUpDate = toSignUpDate;
	}

	public boolean isFilterEmpty() {
		boolean result = false;
		if(
				( userId == null )
				&&
				(companyId == null)
				 &&
				( StringUtils.isBlank(userName))
				&&
				( StringUtils.isBlank(businessName))
				&&
				( StringUtils.isBlank(fnameOrLname))
				&&
				( roleTypeId == null )
				&&
				( fromSignUpDate == null )
				&&
				( toSignUpDate == null )

				){
			result = true;
		}
	return result;
	}
	

	public String getBusinessNameFormated() {
		return formatString(this.businessName);
	}
	
	public String formatString (String str) {
		StringBuilder bld  = new StringBuilder();		
		String []val = str.split(" ");
		int index = 0;

		String longStr = val[index];

		while (val.length > index) {			
			if (longStr.length() > 20) {
				bld.append(longStr.substring(0, 19));
				bld.append(" ");
				longStr = longStr.substring(20, longStr.length());
			} else {				
				bld.append(longStr);
				index ++ ;				
				if (index < val.length) {
					bld.append(" ");
					longStr = val[index];
				}
			}
		}		
		return bld.toString().trim();
	}
	
}
