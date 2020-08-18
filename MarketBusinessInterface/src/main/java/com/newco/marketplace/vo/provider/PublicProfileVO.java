/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.dto.vo.survey.CustomerFeedbackVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingByQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.sears.os.vo.SerializableBaseVO;


/**
 * @author 
 */
public class PublicProfileVO extends SerializableBaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1732200384685052252L;
	private Integer vendorId= -1;
	private Integer resourceId = -1;
	private Date busStartDt;
	private String companySize;
	private String userId = null;
	private String dispAddStreet1;
	private String dispAddStreet2;
	private String dispAddApt;
	private String dispAddCity;
	private String dispAddState;
	private String dispAddZip;
	private String email;
	private String phoneNumber;
	private String alternatePhone;
	private String phoneNumberExt;
	private String dispAddGeographicRange;
	private String firstName;
	private String lastName;
	private Double historicRating;
	private Integer ratingNumber;
	
	@SuppressWarnings("unchecked")
	private List credentialsList;
	
	private List languageList;
	private List resourceSkillList;
	
	 private ArrayList skillTreeList;
	 private ArrayList serviceList;
	
	
	private int wfStateId = -1;
	private String credType;
	private String issuerName;
	private Date expirationDate;
	
	private Date joinDate;
	private Date bgChkAppDate;
	private Date bgChkReqDate;
	private Date bgVerificationDate; 
	
	//Added for User Complete Profile
	private String userName;
	private String otherJobTitle;
	private String serviceLiveStatus;
	private int ownerInd;
	private int dispatchInd;
	private int managerInd;
	private int resourceInd;
	private int adminInd;
	private int otherInd;
	private String ssnLast4;
	private String marketPlaceInd;
	private String slStatus;
	private List<ProviderActivityVO> activityList = new ArrayList();
	//Resource Schedule
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
	private double hourlyRate;
	private List scheduleTimeList =  new ArrayList();
	
	private BusinessinfoVO businessinfoVO;
	private GeneralInfoVO generalInfoVO; 
	private MarketPlaceVO marketPlaceVO;
	private GeneralInfoVO generalScheduleInfoVO;
	
	
	private boolean completeFlag = false;
	private int sproInd;
	private List<CustomerFeedbackVO> feedbacks = new ArrayList<CustomerFeedbackVO>();
	
	private CompanyProfileVO parentVendor;
	
	private com.newco.marketplace.dto.vo.LocationVO providerLatitueLongi;
	
	private List<SurveyRatingByQuestionVO> surveyratings;

	public List<SurveyRatingByQuestionVO> getSurveyratings() {
		return surveyratings;
	}

	public void setSurveyratings(List<SurveyRatingByQuestionVO> surveyratings) {
		this.surveyratings = surveyratings;
	}

	public GeneralInfoVO getGeneralInfoVO() {
		return generalInfoVO;
	}

	public void setGeneralInfoVO(GeneralInfoVO generalInfoVO) {
		this.generalInfoVO = generalInfoVO;
	}

	public BusinessinfoVO getBusinessinfoVO() {
		return businessinfoVO;
	}

	public void setBusinessinfoVO(BusinessinfoVO businessinfoVO) {
		this.businessinfoVO = businessinfoVO;
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

	public Date getBusStartDt() {
		return busStartDt;
	}

	public void setBusStartDt(Date busStartDt) {
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

	public void setFirstName(String pFirstName) {
		String firstName = pFirstName;
		if(firstName != null) {
			firstName = firstName.trim();
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String pLastName) {
		String lastName = pLastName;
		if(lastName != null) {
			lastName = lastName.trim();
		}
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

	public Date getBgChkAppDate() {
		return bgChkAppDate;
	}

	public void setBgChkAppDate(Date bgChkAppDate) {
		this.bgChkAppDate = bgChkAppDate;
	}

	public Date getBgChkReqDate() {
		return bgChkReqDate;
	}

	public void setBgChkReqDate(Date bgChkReqDate) {
		this.bgChkReqDate = bgChkReqDate;
	}

	public List getResourceSkillList() {
		return resourceSkillList;
	}

	public void setResourceSkillList(List resourceSkillList) {
		this.resourceSkillList = resourceSkillList;
	}

	public ArrayList getSkillTreeList() {
		return skillTreeList;
	}

	public void setSkillTreeList(ArrayList skillTreeList) {
		this.skillTreeList = skillTreeList;
	}

	public ArrayList getServiceList() {
		return serviceList;
	}

	public void setServiceList(ArrayList serviceList) {
		this.serviceList = serviceList;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
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

	public MarketPlaceVO getMarketPlaceVO() {
		return marketPlaceVO;
	}

	public void setMarketPlaceVO(MarketPlaceVO marketPlaceVO) {
		this.marketPlaceVO = marketPlaceVO;
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

	public GeneralInfoVO getGeneralScheduleInfoVO() {
		return generalScheduleInfoVO;
	}

	public void setGeneralScheduleInfoVO(GeneralInfoVO generalScheduleInfoVO) {
		this.generalScheduleInfoVO = generalScheduleInfoVO;
	}

	public String getServiceLiveStatus() {
		return serviceLiveStatus;
	}

	public void setServiceLiveStatus(String serviceLiveStatus) {
		this.serviceLiveStatus = serviceLiveStatus;
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

	public CompanyProfileVO getParentVendor() {
		return parentVendor;
	}

	public void setParentVendor(CompanyProfileVO parentVendor) {
		this.parentVendor = parentVendor;
	}

	public com.newco.marketplace.dto.vo.LocationVO getProviderLatitueLongi() {
		return providerLatitueLongi;
	}

	public void setProviderLatitueLongi(com.newco.marketplace.dto.vo.LocationVO providerLatitueLongi) {
		this.providerLatitueLongi = providerLatitueLongi;
	}

	/**
	 * @return the feedbacks
	 */
	public List<CustomerFeedbackVO> getFeedbacks() {
		return feedbacks;
	}

	/**
	 * @param feedbacks the feedbacks to set
	 */
	public void setFeedbacks(List<CustomerFeedbackVO> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
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

	public String getSlStatus() {
		return slStatus;
	}

	public void setSlStatus(String slStatus) {
		this.slStatus = slStatus;
	}

	public String getDispAddApt() {
		return dispAddApt;
	}

	public void setDispAddApt(String dispAddApt) {
		this.dispAddApt = dispAddApt;
	}

	public String getPhoneNumberExt() {
		return phoneNumberExt;
	}

	public void setPhoneNumberExt(String phoneNumberExt) {
		this.phoneNumberExt = phoneNumberExt;
	}

	public Double getHistoricRating() {
		return historicRating;
	}

	public void setHistoricRating(Double historicRating) {
		this.historicRating = historicRating;
	}

	public Integer getRatingNumber() {
		return ratingNumber;
	}

	public void setRatingNumber(Integer ratingNumber) {
		this.ratingNumber = ratingNumber;
	}
	public Date getBgVerificationDate() {
		return bgVerificationDate;
	}

	public void setBgVerificationDate(Date bgVerificationDate) {
		this.bgVerificationDate = bgVerificationDate;
	}
	
	
}
