package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("primaryContactInformation")
@XmlAccessorType(XmlAccessType.FIELD)
public class PrimaryContactInformation {

	@XStreamAlias("roleWithinCompany")
	private String roleWithinCompany;
	
	@XStreamAlias("jobTitle")
	private String jobTitle;
	
	@XStreamAlias("firstName")
	private String firstName;
	
	@XStreamAlias("middleName")
	private String middleName;
	
	@XStreamAlias("lastName")
	private String lastName;
	
	@XStreamAlias("suffix")
	private String suffix;
	
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("alternateEmail")
	private String alternateEmail;
	
	public String getRoleWithinCompany() {
		return roleWithinCompany;
	}
	public void setRoleWithinCompany(String roleWithinCompany) {
		this.roleWithinCompany = roleWithinCompany;
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
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAlternateEmail() {
		return alternateEmail;
	}
	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}
	
}
