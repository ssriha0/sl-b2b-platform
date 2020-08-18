package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;



public class BuyerLeadManagementTabDTO extends SerializedBaseDTO{
	
	private static final long serialVersionUID = 2650172279841075524L;
	
	private String aaData[][];
	private String aaNoteData[][];
	private Integer sEcho=1;
	private String iTotalRecords="2";
	private String iTotalDisplayRecords="2";
	
	private String firstName;
	private String lastName;
	private String shopYourWayId;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private String phoneNo;
	private String email;
	private String reward;
	private String leadId;
	private String addNoteSubject;
	private String addNoteMessage;
	private Boolean checkPrivate=false;
	private Boolean emailAlertInd=false;
	private Boolean checkSupport=false;
	private Boolean checkAllProviders=false;

	private String phoneNo1;
	private String phoneNo2;
	private String phoneNo3;
	
	
	private List<Integer> providerList=new ArrayList<Integer>(); 
	private Integer reasonCode=0;
	private String comments="";
	private boolean revokePointsInd=false;
	private int status=0;
	private boolean chkAllProviderInd=false;
	private String returnResult;
	private Integer failureIndicator=0;
	private String reasonCodeDesc="";
	Integer addOrRevoke = 0;
	Integer pointsToAddOrRevoke = 0;
	Integer customPointsToAddOrRevoke=0;

	
	public String getAddNoteSubject() {
		return addNoteSubject;
	}
	public void setAddNoteSubject(String addNoteSubject) {
		this.addNoteSubject = addNoteSubject;
	}
	public String getAddNoteMessage() {
		return addNoteMessage;
	}
	public void setAddNoteMessage(String addNoteMessage) {
		this.addNoteMessage = addNoteMessage;
	}
	public Boolean getCheckPrivate() {
		return checkPrivate;
	}
	public void setCheckPrivate(Boolean checkPrivate) {
		this.checkPrivate = checkPrivate;
	}
	public Boolean getEmailAlertInd() {
		return emailAlertInd;
	}
	public void setEmailAlertInd(Boolean emailAlertInd) {
		this.emailAlertInd = emailAlertInd;
	}
	public Boolean getCheckSupport() {
		return checkSupport;
	}
	public void setCheckSupport(Boolean checkSupport) {
		this.checkSupport = checkSupport;
	}
	public Boolean getCheckAllProviders() {
		return checkAllProviders;
	}
	public void setCheckAllProviders(Boolean checkAllProviders) {
		this.checkAllProviders = checkAllProviders;
	}
	public String[][] getAaData() {
		return aaData;
	}
	public void setAaData(String[][] aaData) {
		this.aaData = aaData;
	}
	public Integer getsEcho() {
		return sEcho;
	}
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	public String getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(String iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public String getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(String iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
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
	public String getShopYourWayId() {
		return shopYourWayId;
	}
	public void setShopYourWayId(String shopYourWayId) {
		this.shopYourWayId = shopYourWayId;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String[][] getAaNoteData() {
		return aaNoteData;
	}
	public void setAaNoteData(String[][] aaNoteData) {
		this.aaNoteData = aaNoteData;
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	
	
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public List<Integer> getProviderList() {
		return providerList;
	}
	public void setProviderList(List<Integer> providerList) {
		this.providerList = providerList;
	}
	public Integer getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	public boolean isRevokePointsInd() {
		return revokePointsInd;
	}
	public void setRevokePointsInd(boolean revokePointsInd) {
		this.revokePointsInd = revokePointsInd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isChkAllProviderInd() {
		return chkAllProviderInd;
	}
	public void setChkAllProviderInd(boolean chkAllProviderInd) {
		this.chkAllProviderInd = chkAllProviderInd;
	}
	public String getReturnResult() {
		return returnResult;
	}
	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getReasonCodeDesc() {
		return reasonCodeDesc;
	}
	public void setReasonCodeDesc(String reasonCodeDesc) {
		this.reasonCodeDesc = reasonCodeDesc;
	}
	public Integer getAddOrRevoke() {
		return addOrRevoke;
	}
	public void setAddOrRevoke(Integer addOrRevoke) {
		this.addOrRevoke = addOrRevoke;
	}
	public Integer getPointsToAddOrRevoke() {
		return pointsToAddOrRevoke;
	}
	public void setPointsToAddOrRevoke(Integer pointsToAddOrRevoke) {
		this.pointsToAddOrRevoke = pointsToAddOrRevoke;
	}
	public Integer getCustomPointsToAddOrRevoke() {
		return customPointsToAddOrRevoke;
	}
	public void setCustomPointsToAddOrRevoke(Integer customPointsToAddOrRevoke) {
		this.customPointsToAddOrRevoke = customPointsToAddOrRevoke;
	}
	public Integer getFailureIndicator() {
		return failureIndicator;
	}
	public void setFailureIndicator(Integer failureIndicator) {
		this.failureIndicator = failureIndicator;
	}

	public String getPhoneNo1() {
		return phoneNo1;
	}
	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}
	public String getPhoneNo2() {
		return phoneNo2;
	}
	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}
	public String getPhoneNo3() {
		return phoneNo3;
	}
	public void setPhoneNo3(String phoneNo3) {
		this.phoneNo3 = phoneNo3;
	}
	
	
	

	
	
	
}