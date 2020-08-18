package com.newco.marketplace.web.dto.provider;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.web.dto.SerializedBaseDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author KSudhanshu
 *
 */
@XStreamAlias("Provider")
public class BusinessinfoDto extends SerializedBaseDTO{

	/**
	 * variables added for "Business Information"
	 */
	private static final long serialVersionUID = 2117744531722369816L;
	@XStreamAlias("CompanyId")
	private String vendorId;
	@XStreamAlias("TaxPayerId")
	private String taxpayerid;
	private String confirmTaxpayerid;
	@XStreamAlias("DunsNo")
	private String dunsNo= null;
	@XStreamAlias("BusStructure")
	private String busStructure;
	@XStreamAlias("BusStartDt")
	private String busStartDt;
	@XStreamAlias("IsForeignOwned")
	private String isForeignOwned;
	@XStreamAlias("ForeignOwnedPct")
	private String foreignOwnedPct;
	@XStreamAlias("CompanySize")
	private String companySize;
	@XStreamAlias("AnnualSalesRevenue")
	private String annualSalesRevenue;
	@XStreamAlias("NoServicePros")
	private String noServicePros;
	@XStreamAlias("Description")
	private String description;
	@XStreamOmitField
	private List mapBusStructure = new ArrayList();
	@XStreamOmitField
	private List mapCompanySize =new ArrayList();
	@XStreamOmitField
	private List mapForeignOwnedPct =new ArrayList();
	@XStreamOmitField
	private List mapAnnualSalesRevenue =new ArrayList();
	
	@XStreamAlias("BusPhone")
	private String businessPhone;
	@XStreamAlias("BusFax")
	private String businessFax;
	@XStreamAlias("BusExtn")
	private String busPhoneExtn;
    
	@XStreamAlias("MainBusPhone1")
    private String mainBusiPhoneNo1;
	@XStreamAlias("MainBusPhone2")
    private String mainBusiPhoneNo2;
	@XStreamAlias("MainBusPhone3")
    private String mainBusiPhoneNo3;

	@XStreamAlias("BusFax1")
    private String businessFax1;
	@XStreamAlias("BusFax2")
    private String businessFax2;
	@XStreamAlias("BusFax3")
	private String businessFax3;
    
	@XStreamAlias("BusName")
	private String businessName;
	@XStreamAlias("DbaName")
	private String dbaName;
	@XStreamAlias("EinNo")
	private String einNo;
	@XStreamAlias("WebAddress")
	private String webAddress;
	@XStreamOmitField
	private List primaryIndList = new ArrayList();
	@XStreamAlias("PrimaryIndustry")
	private String primaryIndustry;
	
	/**
     * Variables for "Business Address"
     */
	@XStreamAlias("BusStreet1")
    private String businessStreet1;
	@XStreamAlias("BusStreet2")
    private String businessStreet2;
	@XStreamAlias("BusCity")
    private String businessCity;
	@XStreamAlias("BusState")
    private String businessState;
	@XStreamAlias("BusZip")
    private String businessZip;
	@XStreamAlias("BusApt")
    private String businessAprt;
    
    /**
     *	Variables for "Mailing Address" 
     */
	@XStreamAlias("MailAddressChk")
    private boolean mailAddressChk;
	@XStreamAlias("MailStreet1")
    private String mailingStreet1;
	@XStreamAlias("MailStreet2")
    private String mailingStreet2;
	@XStreamAlias("MailCity")
    private String mailingCity;
	@XStreamAlias("MailState")
    private String mailingState;
	@XStreamAlias("MailZip")
    private String mailingZip;
	@XStreamAlias("MailApt")
    private String mailingAprt;
	
    /**
     * variables for "Primary Contact Information - Administrator"
     */

	@XStreamAlias("Role")
    private String roleWithinCom;
	@XStreamAlias("JobTitle")
    private String jobTitle;
	@XStreamAlias("FirstName")
    private String firstName;
	@XStreamAlias("MiddleName")
    private String middleName;
	@XStreamAlias("LastName")
    private String lastName;
	@XStreamAlias("Suffix")
    private String nameSuffix;
	@XStreamAlias("Email")
    private String email;
    @XStreamOmitField
    private String confirmEmail;
    @XStreamAlias("AltEmail")
    private String altEmail;
    @XStreamOmitField
    private String confAltEmail;
    @XStreamOmitField
    private List roleWithinCompany =  new ArrayList();
    @XStreamAlias("OwnerInd")
    private int ownerInd;
    @XStreamAlias("DispatchInd")
	private int dispatchInd;
    @XStreamAlias("ManagerInd")
	private int managerInd;
    @XStreamAlias("ResourceInd")
	private int resourceInd;
    @XStreamAlias("AdminInd")
	private int adminInd;
    @XStreamAlias("OtherInd")
	private int otherInd;
    @XStreamAlias("SproInd")
	private int sproInd;
    @XStreamAlias("LocnId")
	private String locnId;
    @XStreamAlias("LocIdb")
 	private String  locnIdB;
    @XStreamAlias("ResId")
 	private String resID;
    private String action;
    
 //  Changes Starts for Admin name change
    @XStreamOmitField
	private List providerList = new ArrayList();
	@XStreamOmitField
	private List<String> resourceIdList= new ArrayList<String>();
	@XStreamOmitField
	private String newAdminResourceId;
	@XStreamOmitField
	private String oldAdminResourceId;
	@XStreamOmitField
	private String modifiedBy;
	@XStreamOmitField
	private String newAdminName;
	// Changes Ends for Admin name change
	
    public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@XStreamAlias("StateType")
	private String stateType;
	@XStreamOmitField
	private List stateTypeList = new ArrayList();

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

	public int getSproInd() {
		return sproInd;
	}

	public void setSproInd(int sproInd) {
		this.sproInd = sproInd;
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

	public int getResourceInd() {
		return resourceInd;
	}

	public void setResourceInd(int resourceInd) {
		this.resourceInd = resourceInd;
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

	public List getRoleWithinCompany() {
		return roleWithinCompany;
	}

	public void setRoleWithinCompany(List roleWithinCompany) {
		this.roleWithinCompany = roleWithinCompany;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	// added as part of enhancement
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
	 * @return the busStartDt
	 */
	public String getBusStartDt() {
		return busStartDt;
	}

	/**
	 * @param busStartDt the busStartDt to set
	 */
	public void setBusStartDt(String busStartDt) {
		this.busStartDt = busStartDt;
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

	public String getDescription() {
		if(isNull(description))
			description= "";
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConfirmTaxpayerid() {
		if(isNull(confirmTaxpayerid))
			confirmTaxpayerid= "";
		return confirmTaxpayerid;
	}

	public void setConfirmTaxpayerid(String confirmTaxpayerid) {
		
		this.confirmTaxpayerid = confirmTaxpayerid;
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



	public String getDbaName() {
		return dbaName;
	}

	public void setDbaName(String dbaName) {
		this.dbaName = dbaName;
	}
	public String getMainBusiPhoneNo1()
    {
	return mainBusiPhoneNo1;
    }
    
    public void setMainBusiPhoneNo1(String mainBusiPhoneNo1)
    {
	this.mainBusiPhoneNo1 = mainBusiPhoneNo1;
    }
    
    public String getMainBusiPhoneNo2()
    {
	return mainBusiPhoneNo2;
    }
    
    public void setMainBusiPhoneNo2(String mainBusiPhoneNo2)
    {
	this.mainBusiPhoneNo2 = mainBusiPhoneNo2;
    }
    
    public String getMainBusiPhoneNo3()
    {
	return mainBusiPhoneNo3;
    }
    
    public void setMainBusiPhoneNo3(String mainBusiPhoneNo3)
    {
	this.mainBusiPhoneNo3 = mainBusiPhoneNo3;
    }
    
   
    
	public String getBusinessFax1() {
		return businessFax1;
	}

	public void setBusinessFax1(String businessFax1) {
		this.businessFax1 = businessFax1;
	}
	
	public String getBusinessFax2() {
		return businessFax2;
	}

	public void setBusinessFax2(String businessFax2) {
		this.businessFax2 = businessFax2;
	}
	
	public String getBusinessFax3() {
		return businessFax3;
	}

	public void setBusinessFax3(String businessFax3) {
		this.businessFax3 = businessFax3;
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

	public String getEinNo() {
		return einNo;
	}

	public void setEinNo(String einNo) {
		this.einNo = einNo;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public List getPrimaryIndList() {
		return primaryIndList;
	}

	public void setPrimaryIndList(List primaryIndList) {
		this.primaryIndList = primaryIndList;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	public String getBusinessStreet1() {
		return businessStreet1;
	}

	public void setBusinessStreet1(String businessStreet1) {
		this.businessStreet1 = businessStreet1;
	}

	public String getBusinessStreet2() {
		return businessStreet2;
	}

	public void setBusinessStreet2(String businessStreet2) {
		this.businessStreet2 = businessStreet2;
	}

	public String getBusinessCity() {
		return businessCity;
	}

	public void setBusinessCity(String businessCity) {
		this.businessCity = businessCity;
	}

	public String getBusinessState() {
		return businessState;
	}

	public void setBusinessState(String businessState) {
		this.businessState = businessState;
	}

	public String getBusinessZip() {
		return businessZip;
	}

	public void setBusinessZip(String businessZip) {
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
	// added as part of enhancement

	

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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getNewAdminName() {
		return newAdminName;
	}

	public void setNewAdminName(String newAdminName) {
		this.newAdminName = newAdminName;
	}

	
	

}
