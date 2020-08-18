package com.newco.marketplace.api.mobile.beans.getTeamMembers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("teamMemberDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamMemberDetail {

	@XStreamAlias("resourceId")
	private Integer	resourceId;
	@XStreamAlias("firstName")
	private String	firstName;
	@XStreamAlias("lastName")
	private String	lastName;
	@XStreamAlias("title")
	private String	title;
	@XStreamAlias("phoneNumber")
	private String	phoneNumber;
	@XStreamAlias("memberStatus")
	private String memberStatus;
	@XStreamAlias("backgroundCheckStatus")
	private String backgroundCheckStatus;
	@XStreamAlias("backgroundcheckRecertifyInd")
	private Boolean backgroundcheckRecertifyInd;
	@XStreamAlias("marketStatus")
	private String	marketStatus;
	@XStreamAlias("documentId")
	private Integer	documentId;

	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}
	public void setBackgroundCheckStatus(String backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}
	public String getMarketStatus() {
		return marketStatus;
	}
	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public Boolean getBackgroundcheckRecertifyInd() {
		return backgroundcheckRecertifyInd;
	}
	public void setBackgroundcheckRecertifyInd(Boolean backgroundcheckRecertifyInd) {
		this.backgroundcheckRecertifyInd = backgroundcheckRecertifyInd;
	}
}
