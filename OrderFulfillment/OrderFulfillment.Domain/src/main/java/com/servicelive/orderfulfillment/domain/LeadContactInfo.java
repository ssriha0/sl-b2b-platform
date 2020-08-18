package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "lead_contact_information")
@XmlRootElement()
public class LeadContactInfo extends LeadChild {
	private static final long serialVersionUID = 9180446939179439349L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lead_contact_id")
	private Integer leadContactInfoId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "mi")
	private String mi;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "email")
	private String email;

	@Column(name = "street_1")
	private String street1;

	@Column(name = "street_2")
	private String street2;

	@Column(name = "apt_no")
	private String aptNo;

	@Column(name = "city")
	private String city;

	@Column(name = "state_cd")
	private String stateCd;

	@Column(name = "zip")
	private String zip;

	@Column(name = "zip4")
	private String zip4;

	@Column(name = "swyr_mem_id")
	private String swyrMemId;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "contact_type")
	private Integer contactType;

	@Column(name = "swyr_reward")
	private Integer swyrReward;

	public Integer getLeadContactInfoId() {
		return leadContactInfoId;
	}

	public void setLeadContactInfoId(Integer leadContactInfoId) {
		this.leadContactInfoId = leadContactInfoId;
	}

	public Integer getSwyrReward() {
		return swyrReward;
	}

	public void setSwyrReward(Integer swyrReward) {
		this.swyrReward = swyrReward;
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

	public String getMi() {
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
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

	public String getAptNo() {
		return aptNo;
	}

	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateCd() {
		return stateCd;
	}

	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZip4() {
		return zip4;
	}

	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}

	public String getSwyrMemId() {
		return swyrMemId;
	}

	public void setSwyrMemId(String swyrMemId) {
		this.swyrMemId = swyrMemId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getContactType() {
		return contactType;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}

}
