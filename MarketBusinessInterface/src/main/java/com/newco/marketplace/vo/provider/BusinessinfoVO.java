package com.newco.marketplace.vo.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author KSudhanshu
 *
 */
public class BusinessinfoVO extends SerializableBaseVO{
	
	/**
	 * /**
	 * Variables for "Business Information"
	 */
	 
	private static final long serialVersionUID = -3477661255006531502L;
	private String vendorId;
	private String taxpayerid=null;
	 
	private String dunsNo= null;
	private String busStructure; 
	private Date busStartDt;
	private String isForeignOwned; 
	private String foreignOwnedPct;
	private String companySize;
	private String noServicePros; 
	private String description = null;
	private String annualSalesRevenue;
	private List mapBusStructure = new ArrayList();
	private List mapCompanySize =new ArrayList();
	private List mapForeignOwnedPct =new ArrayList();
	private List mapAnnualSalesRevenue =new ArrayList();
	private Date joinDate;
	
	 private String businessName = null;
	 private String dbaName = null;
	 private String businessPhone = null;
     private String businessFax = null;
     private String busPhoneExtn = null;
     private String einNo = null;
     private String webAddress = null;
     private String primaryIndustry = null;
     private List primaryIndList = new ArrayList();
     
     /** 
      * Variables for "Business Address"
      */
     private String businessStreet1 = null;
     private String businessStreet2 = null;
     private String businessCity = null;
     private String businessState = null;
     private String businessZip = null;
     private String businessAprt = null;
     
     /**
      *	Variables for "Mailing Address" 
      */
     private boolean mailAddressChk = false;
     private String mailingStreet1 = null;
     private String mailingStreet2 = null;
     private String mailingCity = null;
     private String mailingState = null;
     private String mailingZip = null;
     private String mailingAprt = null;
     
     /**
      * variables for "Primary Contact Information - Administrator"
      */

     private String roleWithinCom = null;
     private String jobTitle = null;
     private String firstName = null;
     private String middleName = null;
     private String lastName = null;
     private String nameSuffix = null;
     private String email = null;
     private String confirmEmail = null;
     private String altEmail = null;
     private String confAltEmail = null;
     private int ownerInd;
 	 private int dispatchInd;
 	 private int managerInd;
 	 private int adminInd;
 	 private int otherInd;
 	 private int sproInd;
 	 private String locnId;
 	 private String locnIdB;
 	private String resID;
 	private String stateType = null;
	private List stateTypeList = new ArrayList();
 
	// Changes Starts for Admin name change
	private List providerList = new ArrayList();
	private List<String> resourceIdList= new ArrayList<String>();
	private String newAdminResourceId;
	private String oldAdminResourceId;
	private String newAdminName;
	private String modifiedBy;
	// Changes Ends for Admin name change

	private String firmType;
	
	public String getStateType() {
		return stateType;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

	public List getStateTypeList() {
		return stateTypeList;
	}

	public void setStateTypeList(List stateTypeList) {
		this.stateTypeList = stateTypeList;
	}

	public String getEinNo() {
		return einNo;
	}

	public void setEinNo(String einNo) {
		this.einNo = einNo;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getTaxpayerid() {
		if(isNull(taxpayerid))
			taxpayerid= "";
		return taxpayerid;
	}

	public void setTaxpayerid(String taxpayerid) {
		this.taxpayerid = taxpayerid;
	}

	/**
	 * @return the vendorId
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getDunsNo() {
		if(isNull(dunsNo))
			dunsNo= "";

		return dunsNo;
	}

	public void setDunsNo(String dunsNo) {
		this.dunsNo = dunsNo;
	}

	/**
	 * @return the busStructure
	 */
	public String getBusStructure() {
		return busStructure;
	}

	/**
	 * @param busStructure the busStructure to set
	 */
	public void setBusStructure(String busStructure) {
		this.busStructure = busStructure;
	}

	

	/**
	 * @return the isForeignOwned
	 */
	public String getIsForeignOwned() {
		return isForeignOwned;
	}

	/**
	 * @param isForeignOwned the isForeignOwned to set
	 */
	public void setIsForeignOwned(String isForeignOwned) {
		this.isForeignOwned = isForeignOwned;
	}

	/**
	 * @return the foreignOwnedPct
	 */
	public String getForeignOwnedPct() {
		return foreignOwnedPct;
	}

	/**
	 * @param foreignOwnedPct the foreignOwnedPct to set
	 */
	public void setForeignOwnedPct(String foreignOwnedPct) {
		this.foreignOwnedPct = foreignOwnedPct;
	}

	/**
	 * @return the companySize
	 */
	public String getCompanySize() {
		return companySize;
	}

	/**
	 * @param companySize the companySize to set
	 */
	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	/**
	 * @return the noServicePros
	 */
	public String getNoServicePros() {
		return noServicePros;
	}

	/**
	 * @param noServicePros the noServicePros to set
	 */
	public void setNoServicePros(String noServicePros) {
		this.noServicePros = noServicePros;
	}

	/**
	 * @return the annualSalesRevenue
	 */
	public String getAnnualSalesRevenue() {
		return annualSalesRevenue;
	}

	/**
	 * @param annualSalesRevenue the annualSalesRevenue to set
	 */
	public void setAnnualSalesRevenue(String annualSalesRevenue) {
		this.annualSalesRevenue = annualSalesRevenue;
	}

	public String getDescription() {
		if(isNull(description))
			description= "";
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List getMapBusStructure() {
		return mapBusStructure;
	}

	public void setMapBusStructure(List mapBusStructure) {
		this.mapBusStructure = mapBusStructure;
	}

	public List getMapCompanySize() {
		return mapCompanySize;
	}

	public void setMapCompanySize(List mapCompanySize) {
		this.mapCompanySize = mapCompanySize;
	}

	public List getMapForeignOwnedPct() {
		return mapForeignOwnedPct;
	}

	public void setMapForeignOwnedPct(List mapForeignOwnedPct) {
		this.mapForeignOwnedPct = mapForeignOwnedPct;
	}

	public List getMapAnnualSalesRevenue() {
		return mapAnnualSalesRevenue;
	}

	public void setMapAnnualSalesRevenue(List mapAnnualSalesRevenue) {
		this.mapAnnualSalesRevenue = mapAnnualSalesRevenue;
	}
	/**
	 * @param value
	 * @return
	 */
	private boolean isNull(String value) {
		return (value == null);
	}

	public Date getBusStartDt() {
		return busStartDt;
	}

	public void setBusStartDt(Date busStartDt) {
		this.busStartDt = busStartDt;
	}
	// added as part of enhancement
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getBusinessFax() {
		return businessFax;
	}

	public void setBusinessFax(String businessFax) {
		this.businessFax = businessFax;
	}

	public String getBusPhoneExtn() {
		return busPhoneExtn;
	}

	public void setBusPhoneExtn(String busPhoneExtn) {
		this.busPhoneExtn = busPhoneExtn;
	}

	

	public String getDbaName() {
		return dbaName;
	}

	public void setDbaName(String dbaName) {
		this.dbaName = dbaName;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	public List getPrimaryIndList() {
		return primaryIndList;
	}

	public void setPrimaryIndList(List primaryIndList) {
		this.primaryIndList = primaryIndList;
	}

	public String getBusinessStreet1()
    {
	return businessStreet1;
    }
    
    public void setBusinessStreet1(String businessStreet1)
    {
	this.businessStreet1 = businessStreet1;
    }
    
    public String getBusinessStreet2()
    {
	return businessStreet2;
    }
    
    public void setBusinessStreet2(String businessStreet2)
    {
	this.businessStreet2 = businessStreet2;
    }
    
    public String getBusinessCity()
    {
	return businessCity;
    }
    
    public void setBusinessCity(String businessCity)
    {
	this.businessCity = businessCity;
    }
    
    public String getBusinessState()
    {
	return businessState;
    }
    
    public void setBusinessState(String businessState)
    {
	this.businessState = businessState;
    }
    
    public String getBusinessZip()
    {
	return businessZip;
    }
    
    public void setBusinessZip(String businessZip)
    {
	this.businessZip = businessZip;
    }
    
    public String getBusinessAprt() {
		return businessAprt;
	}

	public void setBusinessAprt(String businessAprt) {
		this.businessAprt = businessAprt;
	}

	public boolean isMailAddressChk() {
		return mailAddressChk;
	}

	public void setMailAddressChk(boolean mailAddressChk) {
		this.mailAddressChk = mailAddressChk;
	}

	public String getMailingStreet1() {
		return mailingStreet1;
	}

	public void setMailingStreet1(String mailingStreet1) {
		this.mailingStreet1 = mailingStreet1;
	}

	public String getMailingStreet2() {
		return mailingStreet2;
	}

	public void setMailingStreet2(String mailingStreet2) {
		this.mailingStreet2 = mailingStreet2;
	}

	public String getMailingCity() {
		return mailingCity;
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

	public String getMailingState() {
		return mailingState;
	}

	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}

	public String getMailingZip() {
		return mailingZip;
	}

	public void setMailingZip(String mailingZip) {
		this.mailingZip = mailingZip;
	}

	public String getMailingAprt() {
		return mailingAprt;
	}

	public void setMailingAprt(String mailingAprt) {
		this.mailingAprt = mailingAprt;
	}

	public String getRoleWithinCom() {
		return roleWithinCom;
	}

	public void setRoleWithinCom(String roleWithinCom) {
		this.roleWithinCom = roleWithinCom;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getConfAltEmail() {
		return confAltEmail;
	}

	public void setConfAltEmail(String confAltEmail) {
		this.confAltEmail = confAltEmail;
	}

	public int getOwnerInd() {
		return ownerInd;
	}

	public void setOwnerInd(int ownerInd) {
		this.ownerInd = ownerInd;
	}

	public int getDispatchInd() {
		return dispatchInd;
	}

	public void setDispatchInd(int dispatchInd) {
		this.dispatchInd = dispatchInd;
	}

	public int getManagerInd() {
		return managerInd;
	}

	public void setManagerInd(int managerInd) {
		this.managerInd = managerInd;
	}

	public int getAdminInd() {
		return adminInd;
	}

	public void setAdminInd(int adminInd) {
		this.adminInd = adminInd;
	}

	public int getOtherInd() {
		return otherInd;
	}

	public void setOtherInd(int otherInd) {
		this.otherInd = otherInd;
	}

	public int getSproInd() {
		return sproInd;
	}

	public void setSproInd(int sproInd) {
		this.sproInd = sproInd;
	}

	

	public String getLocnIdB() {
		return locnIdB;
	}

	public void setLocnIdB(String locnIdB) {
		this.locnIdB = locnIdB;
	}

	public String getLocnId() {
		return locnId;
	}

	public void setLocnId(String locnId) {
		this.locnId = locnId;
	}

	public String getResID() {
		return resID;
	}

	public void setResID(String resID) {
		this.resID = resID;
	}

	public List getProviderList() {
		return providerList;
	}

	public void setProviderList(List providerList) {
		this.providerList = providerList;
	}

	public List<String> getResourceIdList() {
		return resourceIdList;
	}

	public void setResourceIdList(List<String> resourceIdList) {
		this.resourceIdList = resourceIdList;
	}

	public String getNewAdminResourceId() {
		return newAdminResourceId;
	}

	public void setNewAdminResourceId(String newAdminResourceId) {
		this.newAdminResourceId = newAdminResourceId;
	}

	public String getOldAdminResourceId() {
		return oldAdminResourceId;
	}

	public void setOldAdminResourceId(String oldAdminResourceId) {
		this.oldAdminResourceId = oldAdminResourceId;
	}

	public String getNewAdminName() {
		return newAdminName;
	}

	public void setNewAdminName(String newAdminName) {
		this.newAdminName = newAdminName;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getFirmType() {
		return firmType;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}

	
	// added as part of enhancement
}
