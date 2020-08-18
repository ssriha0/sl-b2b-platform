package com.newco.marketplace.web.dto.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.vo.provider.ProviderActivityVO;

/**
 * @author 
 *
 */
public class PublicProfileDto extends BaseDto {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 43117460153308013L;
	private Integer vendorId= -1;
	private Integer resourceId = -1;
	private String busStartDt;
	private String companySize;
	private String userId;
	private String dispAddCity;
	private String dispAddState;
	private String dispAddZip;
	private String dispAddGeographicRange;
	private String firstName;
	private String lastName;
	@SuppressWarnings("unchecked")
	private List credentialsList;
	private List languageList;
	private List resourceSkillList;
	private boolean completeFlag;
	
	private int wfStateId = -1;
	private String credType;
	private String issuerName;
	private Date expirationDate;
	
	private String joinDate;
	private String bgChkAppDate;
	private String bgChkReqDate;
	//Added for User Complete Profile
	private String userName;
	private String otherJobTitle;
	private int ownerInd;
	private int dispatchInd;
	private int managerInd;
	private int resourceInd;
	private int adminInd;
	private int otherInd;
	private String ssnLast4;
	private String marketPlaceInd;
	private List<ProviderActivityVO> activityList = new ArrayList();
	//Resource Schedule
	private String monStart;
	private String monEnd;
	private String tueStart;
	private String tueEnd;
	private String wedStart;
	private String wedEnd;
	private String thuStart;
	private String thuEnd;
	private String friStart;
	private String friEnd;
	private String satStart;
	private String satEnd;
	private String sunStart;
	private String sunEnd;
	private String mon24Ind;
	private String monNaInd;
	private String tue24Ind;
	private String tueNaInd;
	private String wed24Ind;
	private String wedNaInd;
	private String thu24Ind;
	private String thuNaInd;
	private String fri24Ind;
	private String friNaInd;
	private String sat24Ind;
	private String satNaInd;
	private String sun24Ind;
	private String sunNaInd;
	private double hourlyRate;
	private List scheduleTimeList =  new ArrayList();
	//Added for Contacts
	private String businessPhone;
	private String businessPhone1;
	private String businessPhone2;
	private String businessPhone3;
	private String businessExtn;
	private String smsAddress1; 
	private String smsAddress2; 
	private String smsAddress3; 
	private String mobilePhone1;
	private String mobilePhone2;
	private String mobilePhone3;
	private String email;
	private String altEmail;
	private String serviceLiveStatus;
	private int sproInd;
	private String role;
	
	public BusinessinfoDto businessinfoDto;
	public GeneralInfoDto generalInfoDto;
	public BusinessinfoDto getBusinessinfoDto() {
		return businessinfoDto;
	}
	public void setBusinessinfoDto(BusinessinfoDto businessinfoDto) {
		this.businessinfoDto = businessinfoDto;
	}
	public GeneralInfoDto getGeneralInfoDto() {
		return generalInfoDto;
	}
	public void setGeneralInfoDto(GeneralInfoDto generalInfoDto) {
		this.generalInfoDto = generalInfoDto;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getBusStartDt() {
		return busStartDt;
	}
	public void setBusStartDt(String busStartDt) {
		this.busStartDt = busStartDt;
	}
	public String getCompanySize() {
		return companySize;
	}
	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDispAddCity() {
		return dispAddCity;
	}
	public void setDispAddCity(String dispAddCity) {
		this.dispAddCity = dispAddCity;
	}
	public String getDispAddState() {
		return dispAddState;
	}
	public void setDispAddState(String dispAddState) {
		this.dispAddState = dispAddState;
	}
	public String getDispAddZip() {
		return dispAddZip;
	}
	public void setDispAddZip(String dispAddZip) {
		this.dispAddZip = dispAddZip;
	}
	public String getDispAddGeographicRange() {
		return dispAddGeographicRange;
	}
	public void setDispAddGeographicRange(String dispAddGeographicRange) {
		this.dispAddGeographicRange = dispAddGeographicRange;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List getCredentialsList() {
		return credentialsList;
	}
	public void setCredentialsList(List credentialsList) {
		this.credentialsList = credentialsList;
	}
	public int getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(int wfStateId) {
		this.wfStateId = wfStateId;
	}
	public String getCredType() {
		return credType;
	}
	public void setCredType(String credType) {
		this.credType = credType;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public List getLanguageList() {
		return languageList;
	}
	public void setLanguageList(List languageList) {
		this.languageList = languageList;
	}
	public String getBgChkAppDate() {
		return bgChkAppDate;
	}
	public void setBgChkAppDate(String bgChkAppDate) {
		this.bgChkAppDate = bgChkAppDate;
	}
	public String getBgChkReqDate() {
		return bgChkReqDate;
	}
	public void setBgChkReqDate(String bgChkReqDate) {
		this.bgChkReqDate = bgChkReqDate;
	}
	public List getResourceSkillList() {
		return resourceSkillList;
	}
	public void setResourceSkillList(List resourceSkillList) {
		this.resourceSkillList = resourceSkillList;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOtherJobTitle() {
		return otherJobTitle;
	}
	public void setOtherJobTitle(String otherJobTitle) {
		this.otherJobTitle = otherJobTitle;
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
	public String getSsnLast4() {
		return ssnLast4;
	}
	public void setSsnLast4(String ssnLast4) {
		this.ssnLast4 = ssnLast4;
	}
	
	public String getMon24Ind() {
		return mon24Ind;
	}
	public void setMon24Ind(String mon24Ind) {
		this.mon24Ind = mon24Ind;
	}
	public String getMonNaInd() {
		return monNaInd;
	}
	public void setMonNaInd(String monNaInd) {
		this.monNaInd = monNaInd;
	}
	public String getTue24Ind() {
		return tue24Ind;
	}
	public void setTue24Ind(String tue24Ind) {
		this.tue24Ind = tue24Ind;
	}
	public String getTueNaInd() {
		return tueNaInd;
	}
	public void setTueNaInd(String tueNaInd) {
		this.tueNaInd = tueNaInd;
	}
	public String getWed24Ind() {
		return wed24Ind;
	}
	public void setWed24Ind(String wed24Ind) {
		this.wed24Ind = wed24Ind;
	}
	public String getWedNaInd() {
		return wedNaInd;
	}
	public void setWedNaInd(String wedNaInd) {
		this.wedNaInd = wedNaInd;
	}
	public String getThu24Ind() {
		return thu24Ind;
	}
	public void setThu24Ind(String thu24Ind) {
		this.thu24Ind = thu24Ind;
	}
	public String getThuNaInd() {
		return thuNaInd;
	}
	public void setThuNaInd(String thuNaInd) {
		this.thuNaInd = thuNaInd;
	}
	public String getFri24Ind() {
		return fri24Ind;
	}
	public void setFri24Ind(String fri24Ind) {
		this.fri24Ind = fri24Ind;
	}
	public String getFriNaInd() {
		return friNaInd;
	}
	public void setFriNaInd(String friNaInd) {
		this.friNaInd = friNaInd;
	}
	public String getSat24Ind() {
		return sat24Ind;
	}
	public void setSat24Ind(String sat24Ind) {
		this.sat24Ind = sat24Ind;
	}
	public String getSatNaInd() {
		return satNaInd;
	}
	public void setSatNaInd(String satNaInd) {
		this.satNaInd = satNaInd;
	}
	public String getSun24Ind() {
		return sun24Ind;
	}
	public void setSun24Ind(String sun24Ind) {
		this.sun24Ind = sun24Ind;
	}
	public String getSunNaInd() {
		return sunNaInd;
	}
	public void setSunNaInd(String sunNaInd) {
		this.sunNaInd = sunNaInd;
	}
	public double getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAltEmail() {
		return altEmail;
	}
	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}
	public String getMarketPlaceInd() {
		return marketPlaceInd;
	}
	public void setMarketPlaceInd(String marketPlaceInd) {
		this.marketPlaceInd = marketPlaceInd;
	}
	public List getScheduleTimeList() {
		return scheduleTimeList;
	}
	public void setScheduleTimeList(List scheduleTimeList) {
		this.scheduleTimeList = scheduleTimeList;
	}
	public List<ProviderActivityVO> getActivityList() {
		return activityList;
	}
	public void setActivityList(List<ProviderActivityVO> activityList) {
		this.activityList = activityList;
	}
	public String getBusinessExtn() {
		return businessExtn;
	}
	public void setBusinessExtn(String businessExtn) {
		this.businessExtn = businessExtn;
	}
	public String getServiceLiveStatus() {
		return serviceLiveStatus;
	}
	public void setServiceLiveStatus(String serviceLiveStatus) {
		this.serviceLiveStatus = serviceLiveStatus;
	}
	public String getMonStart() {
		return monStart;
	}
	public void setMonStart(String monStart) {
		this.monStart = monStart;
	}
	public String getMonEnd() {
		return monEnd;
	}
	public void setMonEnd(String monEnd) {
		this.monEnd = monEnd;
	}
	public String getTueStart() {
		return tueStart;
	}
	public void setTueStart(String tueStart) {
		this.tueStart = tueStart;
	}
	public String getTueEnd() {
		return tueEnd;
	}
	public void setTueEnd(String tueEnd) {
		this.tueEnd = tueEnd;
	}
	public String getWedStart() {
		return wedStart;
	}
	public void setWedStart(String wedStart) {
		this.wedStart = wedStart;
	}
	public String getWedEnd() {
		return wedEnd;
	}
	public void setWedEnd(String wedEnd) {
		this.wedEnd = wedEnd;
	}
	public String getThuStart() {
		return thuStart;
	}
	public void setThuStart(String thuStart) {
		this.thuStart = thuStart;
	}
	public String getThuEnd() {
		return thuEnd;
	}
	public void setThuEnd(String thuEnd) {
		this.thuEnd = thuEnd;
	}
	public String getFriStart() {
		return friStart;
	}
	public void setFriStart(String friStart) {
		this.friStart = friStart;
	}
	public String getFriEnd() {
		return friEnd;
	}
	public void setFriEnd(String friEnd) {
		this.friEnd = friEnd;
	}
	public String getSatStart() {
		return satStart;
	}
	public void setSatStart(String satStart) {
		this.satStart = satStart;
	}
	public String getSatEnd() {
		return satEnd;
	}
	public void setSatEnd(String satEnd) {
		this.satEnd = satEnd;
	}
	public String getSunStart() {
		return sunStart;
	}
	public void setSunStart(String sunStart) {
		this.sunStart = sunStart;
	}
	public String getSunEnd() {
		return sunEnd;
	}
	public void setSunEnd(String sunEnd) {
		this.sunEnd = sunEnd;
	}
	public String getBusinessPhone1() {
		return businessPhone1;
	}
	public void setBusinessPhone1(String businessPhone1) {
		this.businessPhone1 = businessPhone1;
	}
	public String getBusinessPhone2() {
		return businessPhone2;
	}
	public void setBusinessPhone2(String businessPhone2) {
		this.businessPhone2 = businessPhone2;
	}
	public String getBusinessPhone3() {
		return businessPhone3;
	}
	public void setBusinessPhone3(String businessPhone3) {
		this.businessPhone3 = businessPhone3;
	}
	public String getMobilePhone1() {
		return mobilePhone1;
	}
	public void setMobilePhone1(String mobilePhone1) {
		this.mobilePhone1 = mobilePhone1;
	}
	public String getMobilePhone2() {
		return mobilePhone2;
	}
	public void setMobilePhone2(String mobilePhone2) {
		this.mobilePhone2 = mobilePhone2;
	}
	public String getMobilePhone3() {
		return mobilePhone3;
	}
	public void setMobilePhone3(String mobilePhone3) {
		this.mobilePhone3 = mobilePhone3;
	}
	public String getSmsAddress1() {
		return smsAddress1;
	}
	public void setSmsAddress1(String smsAddress1) {
		this.smsAddress1 = smsAddress1;
	}
	public String getSmsAddress2() {
		return smsAddress2;
	}
	public void setSmsAddress2(String smsAddress2) {
		this.smsAddress2 = smsAddress2;
	}
	public String getSmsAddress3() {
		return smsAddress3;
	}
	public void setSmsAddress3(String smsAddress3) {
		this.smsAddress3 = smsAddress3;
	}
	public boolean isCompleteFlag() {
		return completeFlag;
	}
	public void setCompleteFlag(boolean completeFlag) {
		this.completeFlag = completeFlag;
	}
	public int getSproInd() {
		return sproInd;
	}
	public void setSproInd(int sproInd) {
		this.sproInd = sproInd;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	


}
