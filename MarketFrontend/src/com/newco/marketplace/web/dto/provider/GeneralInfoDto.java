package com.newco.marketplace.web.dto.provider;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.provider.StateZipcodeDTO;
import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.web.dto.SerializedBaseDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author KSudhanshu
 *
 * $Revision: 1.21 $ $Author: glacy $ $Date: 2008/04/26 01:13:55 $
 */

/*
 * Maintenance History: See bottom of file
 */
@XStreamAlias("GeneralInfo")
public class GeneralInfoDto extends SerializedBaseDTO{

	private static final long serialVersionUID = -8511399934440196593L;
	@XStreamAlias("VendorId")
	private String vendorId;
	@XStreamAlias("ResourceId")
	private String resourceId;
	@XStreamAlias("ContactId")
	private int contactId;
	@XStreamAlias("LocationId")
	private int locationId;
	@XStreamAlias("MarketPlaceInd")
	private String marketPlaceInd;
	@XStreamAlias("FirstName")
	private String firstName;
	@XStreamAlias("MiddleName")
	private String middleName;
	@XStreamAlias("LastName")
	private String lastName;
	@XStreamAlias("Ssn")
	private String ssn;
	@XStreamAlias("SsnValInd")
	private String ssnValInd;
	@XStreamAlias("Suffix")
	private String suffix;
	@XStreamAlias("SsnLeft")
	private String ssnLeft;
	@XStreamAlias("SsnMiddle")
	private String ssnMiddle;
	@XStreamAlias("SsnRight")
	private String ssnRight;
	@XStreamAlias("DispAddStreet1")
	private String dispAddStreet1;
	@XStreamAlias("DispAddStreet2")
	private String dispAddStreet2;
	@XStreamAlias("DispAddApt")
	private String dispAddApt;
	@XStreamAlias("DispAddCity")
	private String dispAddCity;
	@XStreamAlias("DispAddState")
	private String dispAddState;
	@XStreamAlias("DispAddZip")
	private String dispAddZip;
	@XStreamAlias("DispAddGeographicRange")
	private String dispAddGeographicRange;
	@XStreamAlias("HourlyRate")
	private String hourlyRate;
	@XStreamAlias("MonStart")
	private String monStart;
	@XStreamAlias("MonEnd")
	private String monEnd;
	@XStreamAlias("TueStart")
	private String tueStart;
	@XStreamAlias("TueEnd")
	private String tueEnd;
	@XStreamAlias("WedStart")
	private String wedStart;
	@XStreamAlias("WedEnd")
	private String wedEnd;
	@XStreamAlias("ThuStart")
	private String thuStart;
	@XStreamAlias("ThuEnd")
	private String thuEnd;
	@XStreamAlias("FriStart")
	private String friStart;
	@XStreamAlias("FriEnd")
	private String friEnd;
	@XStreamAlias("SatStart")
	private String satStart;
	@XStreamAlias("SatEnd")
	private String satEnd;
	@XStreamAlias("SunStart")
	private String sunStart;
	@XStreamAlias("SunEnd")
	private String sunEnd;
	@XStreamAlias("mon24Ind")
	private String mon24Ind;
	@XStreamAlias("MonNaInd")
	private String monNaInd;
	@XStreamAlias("Tue24Ind")
	private String tue24Ind;
	@XStreamAlias("TueNaInd")
	private String tueNaInd;
	@XStreamAlias("Wed24Ind")
	private String wed24Ind;
	@XStreamAlias("WedNaInd")
	private String wedNaInd;
	@XStreamAlias("Thu24Ind")
	private String thu24Ind;
	@XStreamAlias("ThuNaInd")
	private String thuNaInd;
	@XStreamAlias("Fri24Ind")
	private String fri24Ind;
	@XStreamAlias("FriNaInd")
	private String friNaInd;
	@XStreamAlias("Sat24Ind")
	private String sat24Ind;
	@XStreamAlias("SatNaInd")
	private String satNaInd;
	@XStreamAlias("Sun24Ind")
	private String sun24Ind;
	@XStreamAlias("SunNaInd")
	private String sunNaInd;
	@XStreamAlias("UserName")
	private String userName;
	@XStreamAlias("OtherJobTitle")
	private String otherJobTitle;
	@XStreamAlias("FullResourceName")
	private String fullResoueceName;
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
	@XStreamAlias("UserExistInd")
	private int userExistInd;
	@XStreamAlias("ActiveInactiveInd")
	private int activeInactiveInd;
	@XStreamAlias("SproInd")
	private int sproInd;

	//Added to find the User is Service Pro or Not
	@XStreamAlias("PrimaryIndicator")
	private int primaryIndicator;
	@XStreamAlias("LockedSsn")
	private boolean lockedSsn;
	@XStreamAlias("FlagAdmin")
	private boolean flagAdmin;
	@XStreamAlias("UserNameAdmin")
	private String userNameAdmin;
	@XStreamAlias("IsNotRemoveUser")
	private int isNotRemoveUser;
	@XStreamAlias("SsnOldValue")
	private String ssnOldValue="";
	private boolean popNovalue=true;
	private boolean editUserName = false;
	private String action;
	private boolean userNameExists = false;  //Added as per JIRA#SL-20083
	
	private String[] coverageZip;
	private String zipcodeConfirmed;
	private List<StateZipcodeDTO> stateZipcodeDTO;
	private boolean zipcodesUpated = false;
	private String zipcodesCovered="";
	private List<StateZipcodeVO> stateZipCodeList;
	private String selectedlicenseCheckbox;
	private String mapboxUrl;

	private boolean bgComplete = false;
	
	//R16.0 SL-20968
	private Integer oldDispatcherInd;
		
	public boolean isBgComplete() {
		return bgComplete;
	}

	public void setBgComplete(boolean bgComplete) {
		this.bgComplete = bgComplete;
	}

	/**
	 * @return the popNovalue
	 */
	public boolean isPopNovalue() {
		return popNovalue;
	}

	/**
	 * @param popNovalue the popNovalue to set
	 */
	public void setPopNovalue(boolean popNovalue) {
		this.popNovalue = popNovalue;
	}

	/**
	 * @return the ssnOldValue
	 */
	public String getSsnOldValue() {
		return ssnOldValue;
	}

	/**
	 * @param ssnOldValue the ssnOldValue to set
	 */
	public void setSsnOldValue(String ssnOldValue) {
		this.ssnOldValue = ssnOldValue;
	}

	// form display data
	@XStreamOmitField
	private List geographicalRange = new ArrayList();
	@XStreamOmitField
	private List scheduleTimeList = new ArrayList();
	@XStreamAlias("StateType")
	private String stateType;//MTedder@covansys.com
	@XStreamOmitField
	private List stateTypeList = new ArrayList();//MTedder@covansys.com
	
	private boolean isNull(String str) {
		if (str == null || "".equals(str))
			return true;
		return false;
	}

	public String getVendorId() {
		if (isNull(vendorId))
			return null;
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getResourceId() {
		if (isNull(resourceId))
			return null;
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

	public String getSsnLeft() {
		return ssnLeft;
	}

	public void setSsnLeft(String ssnLeft) {
		this.ssnLeft = ssnLeft;
	}

	public String getSsnMiddle() {
		return ssnMiddle;
	}

	public void setSsnMiddle(String ssnMiddle) {
		this.ssnMiddle = ssnMiddle;
	}

	public String getSsnRight() {
		return ssnRight;
	}

	public void setSsnRight(String ssnRight) {
		this.ssnRight = ssnRight;
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

	public String getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(String hourlyRate) {
		this.hourlyRate = hourlyRate;
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
	 * @param ownerInd
	 *            the ownerInd to set
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
	 * @param dispatchInd
	 *            the dispatchInd to set
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
	 * @param managerInd
	 *            the managerInd to set
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
	 * @param resourceInd
	 *            the resourceInd to set
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
	 * @param adminInd
	 *            the adminInd to set
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
	 * @param otherInd
	 *            the otherInd to set
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
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the ssnValInd
	 */
	public String getSsnValInd() {
		return ssnValInd;
	}

	/**
	 * @param ssnValInd the ssnValInd to set
	 */
	public void setSsnValInd(String ssnValInd) {
		this.ssnValInd = ssnValInd;
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

	public boolean isLockedSsn() {
		return lockedSsn;
	}

	public void setLockedSsn(boolean lockedSsn) {
		this.lockedSsn = lockedSsn;
	}

	public int getPrimaryIndicator() {
		return primaryIndicator;
	}

	public void setPrimaryIndicator(int primaryIndicator) {
		this.primaryIndicator = primaryIndicator;
	}

	public String getFullResoueceName() {
		return fullResoueceName;
	}

	public void setFullResoueceName(String fullResoueceName) {
		this.fullResoueceName = fullResoueceName;
	}

	public boolean isFlagAdmin() {
		return flagAdmin;
	}

	public void setFlagAdmin(boolean flagAdmin) {
		this.flagAdmin = flagAdmin;
	}

	public String getUserNameAdmin() {
		return userNameAdmin;
	}

	public void setUserNameAdmin(String userNameAdmin) {
		this.userNameAdmin = userNameAdmin;
	}

	

	public int getIsNotRemoveUser() {
		return isNotRemoveUser;
	}

	public void setIsNotRemoveUser(int isNotRemoveUser) {
		this.isNotRemoveUser = isNotRemoveUser;
	}

	public boolean isEditUserName() {
		return editUserName;
	}

	public void setEditUserName(boolean editUserName) {
		this.editUserName = editUserName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	public boolean isUserNameExists() {
		return userNameExists;
	}

	public void setUserNameExists(boolean userNameExists) {
		this.userNameExists = userNameExists;
	}

	public Integer getOldDispatcherInd() {
		return oldDispatcherInd;
	}

	public void setOldDispatcherInd(Integer oldDispatcherInd) {
		this.oldDispatcherInd = oldDispatcherInd;
	}

	public String[] getCoverageZip() {
		return coverageZip;
	}

	public void setCoverageZip(String[] coverageZip) {
		this.coverageZip = coverageZip;
	}

	public String getZipcodeConfirmed() {
		return zipcodeConfirmed;
	}

	public void setZipcodeConfirmed(String zipcodeConfirmed) {
		this.zipcodeConfirmed = zipcodeConfirmed;
	}

	public List<StateZipcodeDTO> getStateZipcodeDTO() {
		return stateZipcodeDTO;
	}

	public void setStateZipcodeDTO(List<StateZipcodeDTO> stateZipcodeDTO) {
		this.stateZipcodeDTO = stateZipcodeDTO;
	}

	public boolean isZipcodesUpated() {
		return zipcodesUpated;
	}

	public void setZipcodesUpated(boolean zipcodesUpated) {
		this.zipcodesUpated = zipcodesUpated;
	}

	public String getZipcodesCovered() {
		return zipcodesCovered;
	}

	public void setZipcodesCovered(String zipcodesCovered) {
		this.zipcodesCovered = zipcodesCovered;
	}

	public List<StateZipcodeVO> getStateZipCodeList() {
		return stateZipCodeList;
	}

	public void setStateZipCodeList(List<StateZipcodeVO> stateZipCodeList) {
		this.stateZipCodeList = stateZipCodeList;
	}

	public String getSelectedlicenseCheckbox() {
		return selectedlicenseCheckbox;
	}

	public void setSelectedlicenseCheckbox(String selectedlicenseCheckbox) {
		this.selectedlicenseCheckbox = selectedlicenseCheckbox;
	}

	public String getMapboxUrl() {
		return mapboxUrl;
	}

	public void setMapboxUrl(String mapboxUrl) {
		this.mapboxUrl = mapboxUrl;
	}
	
	
	
}
/*
 * Maintenance History
 * $Log: GeneralInfoDto.java,v $
 * Revision 1.21  2008/04/26 01:13:55  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.17.12.1  2008/04/23 11:41:52  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.20  2008/04/23 05:19:51  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.18  2008/04/15 17:57:58  glacy
 * Shyam: Merged I18_THREE_ENH branch
 *
 * Revision 1.17.24.1  2008/04/11 03:28:39  araveen
 * Made changes for RemoveUserButton Sears00049387
 *
 * Revision 1.17  2008/02/14 23:44:51  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.16  2008/02/11 17:35:51  mhaye05
 * removed statesList attributes
 *
 */