package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.LookupVO;

/**
 * $Revision: 1.28 $ $Author: awadhwa $ $Date: 2008/05/30 00:14:16 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class AdminSearchFormDTO extends SerializedBaseDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6172936746775478617L;

	private List<IError> errors = new ArrayList<IError>();


	
	// Radio button values
	private String buyerProviderSelection = "0"; // buyer or provider
	private String searchBySelection = "0"; // buyer or provider
	private String auditableItemsSelection = "0"; // yes(1) or no(0)

	// Dropdown/combobox values.
	private List<LookupVO> marketList = null;
	private List<LookupVO> districtList = null;
	private List<LookupVO> regionList = null;
	private List<LookupVO> providerFirmStatusList = null;
	private List<LookupVO> primarySkillList = null;
	private List<LookupVO> auditableItemsList = null;
	private List<LookupVO> backgroundStatusCheckList = null;
	private List<LookupVO> selectProviderNetworkList = null;

	private List<LookupVO> searchByList = null; // Radio button selection
	
	
	private String companyId; 
	private String userId;
	private String city;
	private String state1;
	private String state2;
	private String zipPart1;
	private String zipPart2;
	private String orderNumber;
	private String businessName;
	private String username;
	private String buyerName;
	private String phone;
	private String email;
	private String buyerId;
	private String buyer_username;
	private String buyer_city;
	private String buyer_zip;
	private String buyer_state;
	private String buyer_phone;
	private String buyer_email;
	private String buyer_orderNumber;
	private String buyer_businessName;
	// resource id reference to the buyer
	private String buyer_resourceId;
	
	
	private String market="-1";
	private String district="-1";
	private String region="-1";
	private String providerFirmStatus="-1";
	private String primarySkill="-1";
	private String auditableItems;
	private String backgroundCheckStatus="-1";
	private String selectProviderNetwork="-1";
	
	private String companyCity;
	private String companyState;
	
	private Integer hidVendorId = -1;
	private Integer hidPrimaryInd = -1;
	private Integer hidResourceId = -1;
	private String hidUserName;
	private String hidMemberName;
	private String hidCompanyName;
	private String hidBuyerAdmin;
	private String hidBuyerCityState;
	
	/*
	 * Added to get the Buyer Id
	 */
	private Integer hidBuyerId = -1;
	private Integer hidRoleId = -1;
	
	public Integer getHidBuyerId() {
		return hidBuyerId;
	}

	public void setHidBuyerId(Integer hidBuyerId) {
		this.hidBuyerId = hidBuyerId;
	}

	public String getHidUserName() {
		return hidUserName;
	}

	public void setHidUserName(String hidUserName) {
		this.hidUserName = hidUserName;
	}

	public Integer getHidVendorId() {
		return hidVendorId;
	}

	public void setHidVendorId(Integer hidVendorId) {
		this.hidVendorId = hidVendorId;
	}

	public Integer getHidPrimaryInd() {
		return hidPrimaryInd;
	}

	public void setHidPrimaryInd(Integer hidPrimaryInd) {
		this.hidPrimaryInd = hidPrimaryInd;
	}

	public Integer getHidResourceId() {
		return hidResourceId;
	}

	public void setHidResourceId(Integer hidResourceId) {
		this.hidResourceId = hidResourceId;
	}

	public AdminSearchFormDTO()
	{
	}
	
	public void clear()
	{
			companyId = "";
			userId = "";
			city = "";
			state1 = "";
			state2 = "";
			zipPart1 = "";
			zipPart2 = "";
			orderNumber = "";
			businessName = "";
			username = "";
			phone = "";
			email = "";
			market="-1";
			district="-1";
			region="-1";
			providerFirmStatus="-1";
			primarySkill="-1";
			auditableItems="";
			backgroundCheckStatus="-1";
			selectProviderNetwork="-1";
			buyerId = "";
			buyer_orderNumber = "";
			buyer_businessName = "";
			 buyer_username = "";
			 buyer_city = "";
			 buyer_zip = "";
			 buyer_state = "";
			 buyer_phone= "";
			buyer_email= "";
	}
	
	
	public String getCompanyId()
	{
		return companyId;
	}
	public void setCompanyId(String companyId)
	{
		this.companyId = companyId;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getOrderNumber()
	{
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	public String getBusinessName()
	{
		return businessName;
	}
	public void setBusinessName(String businessName)
	{
		this.businessName = businessName;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getZipPart1()
	{
		return zipPart1;
	}
	public void setZipPart1(String zipPart1)
	{
		this.zipPart1 = zipPart1;
	}
	public String getZipPart2()
	{
		return zipPart2;
	}
	public void setZipPart2(String zipPart2)
	{
		this.zipPart2 = zipPart2;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getHidMemberName() {
		return hidMemberName;
	}

	public void setHidMemberName(String hidMemberName) {
		this.hidMemberName = hidMemberName;
	}

	public String getHidCompanyName() {
		return hidCompanyName;
	}

	public void setHidCompanyName(String hidCompanyName) {
		this.hidCompanyName = hidCompanyName;
	}

	public List<LookupVO> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<LookupVO> districtList) {
		this.districtList = districtList;
	}

	public List<LookupVO> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<LookupVO> regionList) {
		this.regionList = regionList;
	}

	public List<LookupVO> getProviderFirmStatusList() {
		return providerFirmStatusList;
	}

	public void setProviderFirmStatusList(
			List<LookupVO> providerFirmStatusList) {
		this.providerFirmStatusList = providerFirmStatusList;
	}

	public List<LookupVO> getPrimarySkillList() {
		return primarySkillList;
	}

	public void setPrimarySkillList(List<LookupVO> primarySkillList) {
		this.primarySkillList = primarySkillList;
	}

	public List<LookupVO> getAuditableItemsList() {
		return auditableItemsList;
	}

	public void setAuditableItemsList(List<LookupVO> auditableItemsList) {
		this.auditableItemsList = auditableItemsList;
	}

	public List<LookupVO> getBackgroundStatusCheckList() {
		return backgroundStatusCheckList;
	}

	public void setBackgroundStatusCheckList(
			List<LookupVO> backgroundStatusCheckList) {
		this.backgroundStatusCheckList = backgroundStatusCheckList;
	}

	public List<LookupVO> getSelectProviderNetworkList() {
		return selectProviderNetworkList;
	}

	public void setSelectProviderNetworkList(
			List<LookupVO> selectProviderNetworkList) {
		this.selectProviderNetworkList = selectProviderNetworkList;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}


	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProviderFirmStatus() {
		return providerFirmStatus;
	}

	public void setProviderFirmStatus(String providerFirmStatus) {
		this.providerFirmStatus = providerFirmStatus;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	public String getAuditableItems() {
		return auditableItems;
	}

	public void setAuditableItems(String auditableItems) {
		this.auditableItems = auditableItems;
	}

	public String getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}

	public void setBackgroundCheckStatus(String backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}

	public String getSelectProviderNetwork() {
		return selectProviderNetwork;
	}

	public void setSelectProviderNetwork(String selectProviderNetwork) {
		this.selectProviderNetwork = selectProviderNetwork;
	}

	public List<LookupVO> getMarketList() {
		return marketList;
	}

	public void setMarketList(List<LookupVO> marketList) {
		this.marketList = marketList;
	}

	public String getSearchBySelection() {
		return searchBySelection;
	}

	public void setSearchBySelection(String searchBySelection) {
		this.searchBySelection = searchBySelection;
	}

	public String getBuyerProviderSelection() {
		return buyerProviderSelection;
	}

	public void setBuyerProviderSelection(String buyerProviderSelection) {
		this.buyerProviderSelection = buyerProviderSelection;
	}

	public List<LookupVO> getSearchByList() {
		return searchByList;
	}

	public void setSearchByList(List<LookupVO> searchByList) {
		this.searchByList = searchByList;
	}

	public String getState1() {
		return state1;
	}

	public void setState1(String state1) {
		this.state1 = state1;
	}

	public String getState2() {
		return state2;
	}

	public void setState2(String state2) {
		this.state2 = state2;
	}

	public List<IError> getErrors() {
		return errors;
	}

	public void setErrors(List<IError> errors) {
		this.errors = errors;
	}

	public String getAuditableItemsSelection() {
		return auditableItemsSelection;
	}

	public void setAuditableItemsSelection(String auditableItemSelection) {
		this.auditableItemsSelection = auditableItemSelection;
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

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyer_username() {
		return buyer_username;
	}

	public void setBuyer_username(String buyer_username) {
		this.buyer_username = buyer_username;
	}

	public String getBuyer_city() {
		return buyer_city;
	}

	public void setBuyer_city(String buyer_city) {
		this.buyer_city = buyer_city;
	}

	public String getBuyer_zip() {
		return buyer_zip;
	}

	public void setBuyer_zip(String buyer_zip) {
		this.buyer_zip = buyer_zip;
	}

	public String getBuyer_state() {
		return buyer_state;
	}

	public void setBuyer_state(String buyer_state) {
		this.buyer_state = buyer_state;
	}

	public String getBuyer_phone() {
		return buyer_phone;
	}

	public void setBuyer_phone(String buyer_phone) {
		this.buyer_phone = buyer_phone;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getBuyer_orderNumber() {
		return buyer_orderNumber;
	}

	public void setBuyer_orderNumber(String buyer_orderNumber) {
		this.buyer_orderNumber = buyer_orderNumber;
	}

	public String getHidBuyerAdmin() {
		return hidBuyerAdmin;
	}

	public void setHidBuyerAdmin(String hidBuyerAdmin) {
		this.hidBuyerAdmin = hidBuyerAdmin;
	}

	public String getHidBuyerCityState() {
		return hidBuyerCityState;
	}

	public void setHidBuyerCityState(String hidBuyerCityState) {
		this.hidBuyerCityState = hidBuyerCityState;
	}

	public String getBuyer_businessName() {
		return buyer_businessName;
	}

	public void setBuyer_businessName(String buyer_businessName) {
		this.buyer_businessName = buyer_businessName;
	}

	public Integer getHidRoleId() {
		return hidRoleId;
	}

	public void setHidRoleId(Integer hidRoleId) {
		this.hidRoleId = hidRoleId;
	}

	public String getBuyer_resourceId() {
		return buyer_resourceId;
	}

	public void setBuyer_resourceId(String buyer_resourceId) {
		this.buyer_resourceId = buyer_resourceId;
	}

		
	
}
/*
 * Maintenance History
 * $Log: AdminSearchFormDTO.java,v $
 * Revision 1.28  2008/05/30 00:14:16  awadhwa
 * Fix for business field not clearing on click of clear button
 *
 * Revision 1.27  2008/05/30 00:06:55  awadhwa
 * Added hidRoleId
 *
 * Revision 1.26  2008/05/21 23:33:02  akashya
 * I21 Merged
 *
 * Revision 1.25.6.1  2008/05/13 18:50:50  hoza
 * Admin buyer search frontend
 *
 * Revision 1.25  2008/04/26 01:13:45  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.23.12.1  2008/04/23 11:41:30  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.24  2008/04/23 05:19:45  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.23  2008/02/15 17:18:05  mhaye05
 * updated sl admin search to ensure that the company city and state are displayed
 *
 * Revision 1.22  2008/02/14 23:44:48  mhaye05
 * Merged Feb4_release branch into head
 *
 */