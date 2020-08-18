/**
 * 
 */
package com.newco.marketplace.dto.vo.buyersearch;

import java.util.Date;

import com.newco.marketplace.vo.MPBaseVO;

/**
 * @author HOZA
 *
 */
public class BuyerResultForAdminVO extends MPBaseVO {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = -1053619698091091549L;
	private Integer buyer_id;
	private Integer resourceId;
	private Integer roleId;
	private String city;
	private String state;
	private String zip;
	private String soId;
	private String username;
	private String businessName;
	private String phone;
	private String email;
	private String primaryIndustry;
	private String lastActivity;
	private String sortColumnName;
	private String sortOrder;
	private Integer marketId;
	private String marketName;
	private Date lastActivityDate;
	private String companyCity;
	private String companyState;
	private String fundingType;
	private String contactfirstName;
	private String contactLastName;
	

	public Integer getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(Integer buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	public String getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(String lastActivity) {
		this.lastActivity = lastActivity;
	}

	public String getSortColumnName() {
		return sortColumnName;
	}

	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Date getLastActivityDate() {
		return lastActivityDate;
	}

	public void setLastActivityDate(Date lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	public String getCompanyCity() {
		return companyCity;
	}

	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}

	public String getCompanyState() {
		return companyState;
	}

	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}

	

	/**
	 * 
	 */
	public BuyerResultForAdminVO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sears.os.vo.ABaseVO#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Buyer VO Buyer Id = "+ this.getBuyer_id();
	}

	public String getFundingType() {
		return fundingType;
	}

	public void setFundingType(String fundingType) {
		this.fundingType = fundingType;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	public String getContactfirstName() {
		return contactfirstName;
	}

	public void setContactfirstName(String contactfirstName) {
		this.contactfirstName = contactfirstName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

}
