package com.newco.marketplace.dto.vo.providerSearch;

import java.util.Date;

import com.newco.marketplace.vo.MPBaseVO;

/**
 * @author zizrale
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History: See bottom of file
 */
public class ProviderResultForAdminVO extends MPBaseVO{

	private static final long serialVersionUID = -7977383859281624055L;
	private Integer resourceId;
	private Integer vendorId;
	private String city;
	private String state;
	private String zip;
	private String soId;
	//private String userName;
	private String businessName;
	private String vendorStatus;
	private String phone;
	private String email;
	private int primaryInd = -1;
	private String memberName;
	private String primaryIndustry;
	private String lastActivity;
	private String sortColumnName;
	private String sortOrder;
	private Integer marketId;
	private Integer district;
	private Integer region;
	private Integer providerStatusId;
	private Integer resourcePrimarySkill;
	private Integer auditable;
	private Integer resourceBackgroundStatusId;
	private Integer selectProviderNetworkId;
	private String marketName;
	private Date lastActivityDate;
	
	private String companyCity;
	private String companyState;
	private String resourceStatus;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
//	public String getUserName() {
//		return userName;
//	}
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorStatus() {
		return vendorStatus;
	}
	public void setVendorStatus(String vendorStatus) {
		this.vendorStatus = vendorStatus;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	@Override
	public String toString() {
	    return ("Not implemented");
	}
	public int getPrimaryInd() {
		return primaryInd;
	}
	public void setPrimaryInd(int primaryInd) {
		this.primaryInd = primaryInd;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public Integer getRegion() {
		return region;
	}
	public void setRegion(Integer region) {
		this.region = region;
	}
	public Integer getProviderStatusId() {
		return providerStatusId;
	}
	public void setProviderStatusId(Integer providerStatusId) {
		this.providerStatusId = providerStatusId;
	}
	public Integer getResourcePrimarySkill() {
		return resourcePrimarySkill;
	}
	public void setResourcePrimarySkill(Integer resourcePrimarySkill) {
		this.resourcePrimarySkill = resourcePrimarySkill;
	}
	public Integer getAuditable() {
		return auditable;
	}
	public void setAuditable(Integer auditable) {
		this.auditable = auditable;
	}
	public Integer getResourceBackgroundStatusId() {
		return resourceBackgroundStatusId;
	}
	public void setResourceBackgroundStatusId(Integer resourceBackgroundStatusId) {
		this.resourceBackgroundStatusId = resourceBackgroundStatusId;
	}
	public Integer getSelectProviderNetworkId() {
		return selectProviderNetworkId;
	}
	public void setSelectProviderNetworkId(Integer selectProviderNetworkId) {
		this.selectProviderNetworkId = selectProviderNetworkId;
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
	public String getResourceStatus() {
		return resourceStatus;
	}
	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	
}
/*
 * Maintenance History
 * $Log: ProviderResultForAdminVO.java,v $
 * Revision 1.13  2008/04/26 00:40:10  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.11.6.1  2008/04/23 11:41:13  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.12  2008/04/23 05:17:03  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.11  2008/02/26 18:21:08  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.10.2.1  2008/02/21 21:58:41  mhaye05
 * added logic to all for the display of tech name and status on the service live admin provider search pages
 *
 * Revision 1.10  2008/02/15 17:18:04  mhaye05
 * updated sl admin search to ensure that the company city and state are displayed
 *
 * Revision 1.9  2008/02/14 23:44:28  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.8  2008/02/01 19:29:28  mhaye05
 * added lastActivityDate
 *
 * Revision 1.7  2008/02/01 18:19:48  mhaye05
 * add new attributes
 *
 */