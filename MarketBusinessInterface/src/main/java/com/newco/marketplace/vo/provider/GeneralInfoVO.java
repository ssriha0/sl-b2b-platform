/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * @author KSudhanshu
 *
 */
public class GeneralInfoVO extends SerializableBaseVO{

	private static final long serialVersionUID = -1068802051433595232L;
	private String vendorId;
	private String resourceId;
	private int contactId;
	private int locationId;
	private String marketPlaceInd;
	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String ssn;
	private String ssnLast4;
	private String dispAddStreet1;
	private String dispAddStreet2;
	private String dispAddApt;
	private String dispAddCity;
	private String dispAddState;
	private String dispAddZip;
	private String email;
	private String altemail;
	private String smsAddress;
	private String phoneNumber;
	private String mobileNumber;
	private String alternatePhone;
	private String phoneNumberExt;
	private String dispAddGeographicRange;
	private double hourlyRate;
	private Date monStart;
	private Date monEnd;
	private Date tueStart;
	private Date tueEnd;
	private Date wedStart;
	private Date wedEnd;
	private Date thuStart;
	private Date thuEnd;
	private Date friStart;
	private Date friEnd;
	private Date satStart;
	private Date satEnd;
	private Date sunStart;
	private Date sunEnd;
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
	private String userName;
	private String otherJobTitle;
	private int ownerInd;
	private int dispatchInd;
	private int managerInd;
	private int resourceInd;
	private int adminInd;
	private int otherInd;
	private int userExistInd;
	private int activeInactiveInd;
	private String plusOneKey;
	private int sproInd;
	private int locnTypeId;
	private int bckStateId;
	private Integer totalSoCompleted;
	private String slStatus; 
	private String timeZoneType;
	
	//Added to find the User is Service Pro or Not
	private int primaryInd;
	
	private String userNameAdmin;
	
	// form display data
	//private List statesList =  new ArrayList();
	private List geographicalRange =  new ArrayList();
	private List scheduleTimeList =  new ArrayList();
	
	private String stateType = null;//MTedder@covansys.com
	private List stateTypeList = new ArrayList();//MTedder@covansys.com
	
	private boolean userNameExists = false;  //Added as per JIRA#SL-20083

	//SL-19667   for bc_check_id
	private Integer bcCheckId;
	
	private Integer bcStateId;
    private String changedComment; 
	private String displayInd="Y";
	private boolean bgComplete = false;
	
	//Background Check Details
	private Integer backgroundCheckStatusId = null;
	private String backgroundVerificationDate = null;
	private String backgroundReVerificationDate = null;
	private String backGroundRequestedDate =null;
	private String bgRequestType ="N";
	
	private Integer secondaryContact1 = -1;
	private Integer primaryContact = 1;
	private String smsNo;
	private String resourceType;
	private String subContractorCrewId;
	
	private String[] coverageZip;
	private boolean zipcodesUpated = true;
	private String zipcodeConfirmed;
	private List<StateZipcodeVO> stateZipcodeVO;
	
	public boolean isBgComplete() {
		return bgComplete;
	}
	public void setBgComplete(boolean bgComplete) {
		this.bgComplete = bgComplete;
	}
	public String getDisplayInd() {
		return displayInd;
	}
	public void setDisplayInd(String displayInd) {
		this.displayInd = displayInd;
	}
	public String getChangedComment() {
		return changedComment;
	}
	public void setChangedComment(String changedComment) {
		this.changedComment = changedComment;
	}
	public Integer getBcStateId() {
		return bcStateId;
	}
	public void setBcStateId(Integer bcStateId) {
		this.bcStateId = bcStateId;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getMarketPlaceInd() {
		return marketPlaceInd;
	}
	public void setMarketPlaceInd(String marketPlaceInd) {
		this.marketPlaceInd = marketPlaceInd;
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
	
	public String getSsn() {
		return ssn;
	}
	
	public double getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getDispAddStreet1() {
		return dispAddStreet1;
	}
	public void setDispAddStreet1(String dispAddStreet1) {
		this.dispAddStreet1 = dispAddStreet1;
	}
	public String getDispAddStreet2() {
		return dispAddStreet2;
	}
	public void setDispAddStreet2(String dispAddStreet2) {
		this.dispAddStreet2 = dispAddStreet2;
	}
	public String getDispAddApt() {
		return dispAddApt;
	}
	public void setDispAddApt(String dispAddApt) {
		this.dispAddApt = dispAddApt;
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
	
	public Date getMonStart() {
		return monStart;
	}
	public void setMonStart(Date monStart) {
		this.monStart = monStart;
	}
	public Date getMonEnd() {
		return monEnd;
	}
	public void setMonEnd(Date monEnd) {
		this.monEnd = monEnd;
	}
	public Date getTueStart() {
		return tueStart;
	}
	public void setTueStart(Date tueStart) {
		this.tueStart = tueStart;
	}
	public Date getTueEnd() {
		return tueEnd;
	}
	public void setTueEnd(Date tueEnd) {
		this.tueEnd = tueEnd;
	}
	public Date getWedStart() {
		return wedStart;
	}
	public void setWedStart(Date wedStart) {
		this.wedStart = wedStart;
	}
	public Date getWedEnd() {
		return wedEnd;
	}
	public void setWedEnd(Date wedEnd) {
		this.wedEnd = wedEnd;
	}
	public Date getThuStart() {
		return thuStart;
	}
	public void setThuStart(Date thuStart) {
		this.thuStart = thuStart;
	}
	public Date getThuEnd() {
		return thuEnd;
	}
	public void setThuEnd(Date thuEnd) {
		this.thuEnd = thuEnd;
	}
	public Date getFriStart() {
		return friStart;
	}
	public void setFriStart(Date friStart) {
		this.friStart = friStart;
	}
	public Date getFriEnd() {
		return friEnd;
	}
	public void setFriEnd(Date friEnd) {
		this.friEnd = friEnd;
	}
	public Date getSatStart() {
		return satStart;
	}
	public void setSatStart(Date satStart) {
		this.satStart = satStart;
	}
	public Date getSatEnd() {
		return satEnd;
	}
	public void setSatEnd(Date satEnd) {
		this.satEnd = satEnd;
	}
	public Date getSunStart() {
		return sunStart;
	}
	public void setSunStart(Date sunStart) {
		this.sunStart = sunStart;
	}
	public Date getSunEnd() {
		return sunEnd;
	}
	public void setSunEnd(Date sunEnd) {
		this.sunEnd = sunEnd;
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
//	public List getStatesList() {
//		return statesList;
//	}
//	public void setStatesList(List statesList) {
//		this.statesList = statesList;
//	}
	
	public List getGeographicalRange() {
		return geographicalRange;
	}
	public void setGeographicalRange(List geographicalRange) {
		this.geographicalRange = geographicalRange;
	}
	
	public List getScheduleTimeList() {
		return scheduleTimeList;
	}
	public void setScheduleTimeList(List scheduleTimeList) {
		this.scheduleTimeList = scheduleTimeList;
	}
	
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
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
	/**
	 * @return the ownerInd
	 */
	public int getOwnerInd() {
		return ownerInd;
	}
	/**
	 * @param ownerInd the ownerInd to set
	 */
	public void setOwnerInd(int ownerInd) {
		this.ownerInd = ownerInd;
	}
	/**
	 * @return the dispatchInd
	 */
	public int getDispatchInd() {
		return dispatchInd;
	}
	/**
	 * @param dispatchInd the dispatchInd to set
	 */
	public void setDispatchInd(int dispatchInd) {
		this.dispatchInd = dispatchInd;
	}
	/**
	 * @return the managerInd
	 */
	public int getManagerInd() {
		return managerInd;
	}
	/**
	 * @param managerInd the managerInd to set
	 */
	public void setManagerInd(int managerInd) {
		this.managerInd = managerInd;
	}
	/**
	 * @return the resourceInd
	 */
	public int getResourceInd() {
		return resourceInd;
	}
	/**
	 * @param resourceInd the resourceInd to set
	 */
	public void setResourceInd(int resourceInd) {
		this.resourceInd = resourceInd;
	}
	/**
	 * @return the adminInd
	 */
	public int getAdminInd() {
		return adminInd;
	}
	/**
	 * @param adminInd the adminInd to set
	 */
	public void setAdminInd(int adminInd) {
		this.adminInd = adminInd;
	}
	/**
	 * @return the otherInd
	 */
	public int getOtherInd() {
		return otherInd;
	}
	/**
	 * @param otherInd the otherInd to set
	 */
	public void setOtherInd(int otherInd) {
		this.otherInd = otherInd;
	}
	/**
	 * @return the userExistInd
	 */
	public int getUserExistInd() {
		return userExistInd;
	}
	/**
	 * @param userExistInd the userExistInd to set
	 */
	public void setUserExistInd(int userExistInd) {
		this.userExistInd = userExistInd;
	}
	public int getActiveInactiveInd() {
		return activeInactiveInd;
	}
	public void setActiveInactiveInd(int activeInactiveInd) {
		this.activeInactiveInd = activeInactiveInd;
	}
	/**
	 * @return the ssnLast4
	 */
	public String getSsnLast4() {
		return ssnLast4;
	}
	/**
	 * @param ssnLast4 the ssnLast4 to set
	 */
	public void setSsnLast4(String ssnLast4) {
		this.ssnLast4 = ssnLast4;
	}
	/**
	 * @return
	 */
	public String getPlusOneKey() {
		return plusOneKey;
	}
	/**
	 * @param plusOneKey
	 */
	public void setPlusOneKey(String plusOneKey) {
		this.plusOneKey = plusOneKey;
	}
	/**
	 * @return the stateType
	 */
	public String getStateType() {
		return stateType;
	}
	/**
	 * @param stateType the stateType to set
	 */
	public void setStateType(String stateType) {
		this.stateType = stateType;
	}
	/**
	 * @return the stateTypeList
	 */
	public List getStateTypeList() {
		return stateTypeList;
	}
	/**
	 * @param stateTypeList the stateTypeList to set
	 */
	public void setStateTypeList(List stateTypeList) {
		this.stateTypeList = stateTypeList;
	}
	public int getSproInd() {
		return sproInd;
	}
	public void setSproInd(int sproInd) {
		this.sproInd = sproInd;
	}
	public int getBckStateId() {
		return bckStateId;
	}
	public void setBckStateId(int bckStateId) {
		this.bckStateId = bckStateId;
	}
	public int getPrimaryInd() {
		return primaryInd;
	}
	public void setPrimaryInd(int primaryInd) {
		this.primaryInd = primaryInd;
	}
	public int getLocnTypeId() {
		return locnTypeId;
	}
	public void setLocnTypeId(int locnTypeId) {
		this.locnTypeId = locnTypeId;
	}
	public String getUserNameAdmin() {
		return userNameAdmin;
	}
	public void setUserNameAdmin(String userNameAdmin) {
		this.userNameAdmin = userNameAdmin;
	}
	public Integer getTotalSoCompleted() {
		return totalSoCompleted;
	}
	public void setTotalSoCompleted(Integer totalSoCompleted) {
		this.totalSoCompleted = totalSoCompleted;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAlternatePhone() {
		return alternatePhone;
	}
	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSlStatus() {
		return slStatus;
	}
	public void setSlStatus(String slStatus) {
		this.slStatus = slStatus;
	}
	public String getPhoneNumberExt() {
		return phoneNumberExt;
	}
	public void setPhoneNumberExt(String phoneNumberExt) {
		this.phoneNumberExt = phoneNumberExt;
	}
	public boolean isUserNameExists() {
		return userNameExists;
	}
	public void setUserNameExists(boolean userNameExists) {
		this.userNameExists = userNameExists;
	}

	public Integer getBcCheckId() {
		return bcCheckId;
	}
	public void setBcCheckId(Integer bcCheckId) {
		this.bcCheckId = bcCheckId;
	}
	public Integer getBackgroundCheckStatusId() {
		return backgroundCheckStatusId;
	}
	public void setBackgroundCheckStatusId(Integer backgroundCheckStatusId) {
		this.backgroundCheckStatusId = backgroundCheckStatusId;
	}
	public String getBackgroundVerificationDate() {
		return backgroundVerificationDate;
	}
	public void setBackgroundVerificationDate(String backgroundVerificationDate) {
		this.backgroundVerificationDate = backgroundVerificationDate;
	}
	public String getBgRequestType() {
		return bgRequestType;
	}
	public void setBgRequestType(String bgRequestType) {
		this.bgRequestType = bgRequestType;
	}
	public String getBackgroundReVerificationDate() {
		return backgroundReVerificationDate;
	}
	public void setBackgroundReVerificationDate(String backgroundReVerificationDate) {
		this.backgroundReVerificationDate = backgroundReVerificationDate;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAltemail() {
		return altemail;
	}
	public void setAltemail(String altemail) {
		this.altemail = altemail;
	}
	public String getSmsAddress() {
		return smsAddress;
	}
	public void setSmsAddress(String smsAddress) {
		this.smsAddress = smsAddress;
	}
	public Integer getSecondaryContact1() {
		return secondaryContact1;
	}
	public void setSecondaryContact1(Integer secondaryContact1) {
		this.secondaryContact1 = secondaryContact1;
	}
	public Integer getPrimaryContact() {
		return primaryContact;
	}
	public void setPrimaryContact(Integer primaryContact) {
		this.primaryContact = primaryContact;
	}
	public String getSmsNo() {
		return smsNo;
	}
	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getSubContractorCrewId() {
		return subContractorCrewId;
	}
	public void setSubContractorCrewId(String subContractorCrewId) {
		this.subContractorCrewId = subContractorCrewId;
	}
	public String getBackGroundRequestedDate() {
		return backGroundRequestedDate;
	}
	public void setBackGroundRequestedDate(String backGroundRequestedDate) {
		this.backGroundRequestedDate = backGroundRequestedDate;
	}
	public String getTimeZoneType() {
		return timeZoneType;
	}
	public void setTimeZoneType(String timeZoneType) {
		this.timeZoneType = timeZoneType;
	}
	public String[] getCoverageZip() {
		return coverageZip;
	}
	public void setCoverageZip(String[] coverageZip) {
		this.coverageZip = coverageZip;
	}
	public boolean isZipcodesUpated() {
		return zipcodesUpated;
	}
	public void setZipcodesUpated(boolean zipcodesUpated) {
		this.zipcodesUpated = zipcodesUpated;
	}
	public String getZipcodeConfirmed() {
		return zipcodeConfirmed;
	}
	public void setZipcodeConfirmed(String zipcodeConfirmed) {
		this.zipcodeConfirmed = zipcodeConfirmed;
	}
	public List<StateZipcodeVO> getStateZipcodeVO() {
		return stateZipcodeVO;
	}
	public void setStateZipcodeVO(List<StateZipcodeVO> stateZipcodeVO) {
		this.stateZipcodeVO = stateZipcodeVO;
	}

	
	
}
